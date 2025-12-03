package dev.jeffery.movie_booking_project_backend.dto;

public class SavedCardDTO {
    private String cardId;
    private String maskedNumber;
    private String expirationDate;


    public SavedCardDTO () {

    }
    // Credit card info with digits hided.
    public  SavedCardDTO (String cardId, String maskedNumber, String expirationDate) {
        this.cardId = cardId;
        this.maskedNumber = maskedNumber;
        this.expirationDate = expirationDate;
    }

    public String getCardId() {
        return cardId;
    }
    
    public String getMaskedNumber() {
        return maskedNumber;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setCardId (String cardId) {
        this.cardId = cardId;

    }

    public void setMaskedNumber (String maskedNumber) {
        this.maskedNumber = maskedNumber;
    }

    public void setExpirationDate (String expirationDate) {
        this.expirationDate = expirationDate;
    }

}
