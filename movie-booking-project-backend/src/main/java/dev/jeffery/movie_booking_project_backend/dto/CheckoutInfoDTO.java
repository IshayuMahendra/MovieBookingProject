package dev.jeffery.movie_booking_project_backend.dto;

import java.util.List;

// To display initial info
public class CheckoutInfoDTO {

    private String email;
    private String bookingId;
    private double subtotal;
    private List<SavedCardDTO> savedCards;

    public CheckoutInfoDTO() {

    }

    public CheckoutInfoDTO (String email, String bookingId, double subtotal, List<SavedCardDTO> savedCards) {
        this.email = email;
        this.bookingId = bookingId;
        this.subtotal = subtotal;
        this.savedCards = savedCards;

    }

    public String getEmail() {
        return email;
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

    public void setEmail (String email) {
        this.email = email;
    }

    public void setBookingId (String bookingId) {
        this.bookingId = bookingId;
    }

    public void setSubtotal (double subtotal) {
        this.subtotal = subtotal;
    }

     public void setSavedCards(List<SavedCardDTO> savedCards) {
        this.savedCards = savedCards;
    }



}
