class LegacyAdaptor implements PaymentProcessor {
    private LegacyPaymentGateway legacyGateway;
    private static final int SUCCESS_CODE = 0;

    public LegacyAdaptor(LegacyPaymentGatwayFactory factory) {
        this.legacyGateway = factory.create();
    }

    @Override
    public PaymentResult process(PaymentRequest request) throws PaymentException {
        if (!request.getCurrency().equals("USD")) {
            throw new PaymentException("can only pay in USD");
        }
        int amountCents = (int) Math.round(request.getAmount() * 100);
        String cardNumber = request.getCardNumber();
        String cvv = request.getCvv();
        String expiryMMYY = "" + request.getExpiryMonth() + (request.getExpiryYear() % 100);
        int outputCode = legacyGateway.charge(int amountCents, String cardNumber, String cvv, String expiryMMYY);
        if (outputCode != 0) {
            throw new PaymentException("error charging card");
        }
        return new PaymentResult(SUCCESS_CODE, generateTransactionID(), "successfully completed transaction");
    }

    private int generateTransactionID() {
        //generates an id for the transaction
    }
}