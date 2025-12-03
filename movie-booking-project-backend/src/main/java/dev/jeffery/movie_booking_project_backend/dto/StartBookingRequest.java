package dev.jeffery.movie_booking_project_backend.dto;

import java.util.List;

public class StartBookingRequest {

    private String showId;
    private List<TicketRequest> tickets;

    public String getShowId() {
        return showId;
    }

    public void setShowId(String showId) {
        this.showId = showId;
    }

    public List<TicketRequest> getTickets() {
        return tickets;
    }

    public void setTickets(List<TicketRequest> tickets) {
        this.tickets = tickets;
    }

    public static class TicketRequest {
        private String ageCategory;

        public String getAgeCategory() {
            return ageCategory;
        }

        public void setAgeCategory(String ageCategory) {
            this.ageCategory = ageCategory;
        }
    }
}

