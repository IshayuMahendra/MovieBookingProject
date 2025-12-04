package dev.jeffery.movie_booking_project_backend.dto;

import java.util.List;

// To display initial info
public class CheckoutInfoDTO {

    private String userId;
    private String bookingId;
    private double subtotal;
    private List<SavedCardDTO> savedCards;

    public CheckoutInfoDTO() {

    }

    public CheckoutInfoDTO (String userId, String bookingId, double subtotal, List<SavedCardDTO> savedCards) {
        this.userId = userId;
        this.bookingId = bookingId;
        this.subtotal = subtotal;
        this.savedCards = savedCards;

    }

    public String getUserId() {
        return userId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public double getTotal() {
        return subtotal;
    }

    public List<SavedCardDTO> getSavedCards() {
        return savedCards;
    }

    public void setUserId (String userId) {
        this.userId = userId;
    }

    public void setBookingId (String bookingId) {
        this.bookingId = bookingId;
    }

    public void setTotal (double subtotal) {
        this.subtotal = subtotal;
    }

     public void setSavedCards(List<SavedCardDTO> savedCards) {
        this.savedCards = savedCards;
    }



}
