package dev.jeffery.movie_booking_project_backend.dto;

// Gets info from user
public class ConfirmCheckoutRequest {
    
    private String promotionCode;
    private String cardNumber;
    private String expirationDate;
    private String billingAddress;
    private String savedCardId; 
    private boolean saveCard;

    public String getPromotionCode() {
        return promotionCode;
    }

    public String getCardNumber() {
        return cardNumber;
    }
     public String getExpirationDate() {
        return expirationDate;
    }
     public String getBillingAddress() {
        return billingAddress;
    }
     public String getSavedCardId() {
        return savedCardId;
    }
    public void setPromotionCode(String promotionCode) {
        this.promotionCode = promotionCode;        
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

     public void setBillingAddress(String billingAddres) {
        this.billingAddress = billingAddres;
    }

     public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

     public void  setSavedCardId(String savedCardId) {
        this.savedCardId = savedCardId;
    }

    public boolean isSaveCard() { return saveCard; }
    public void setSaveCard(boolean saveCard) { this.saveCard = saveCard; }

    
    





}
