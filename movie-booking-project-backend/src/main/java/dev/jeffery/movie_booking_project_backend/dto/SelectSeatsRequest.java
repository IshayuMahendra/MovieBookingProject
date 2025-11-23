package dev.jeffery.movie_booking_project_backend.dto;

import java.util.List;

public class SelectSeatsRequest {

    private List<String> seatIds;

    public List<String> getSeatIds() {
        return seatIds;
    }

    public void setSeatIds(List<String> seatIds) {
        this.seatIds = seatIds;
    }
}

