package dev.jeffery.movie_booking_project_backend.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Ticket")
@Data
@NoArgsConstructor
public class Ticket {
    @Id
    private ObjectId id = new ObjectId();
    private TicketType ticketType;
    private double price;
    private ObjectId bookingID;
    private ObjectId seatID;
    private ObjectId showID;

    public Ticket(TicketType ticketType, ObjectId bookingID, ObjectId seatID, ObjectId showID){
        this.ticketType = ticketType;
        this.price = this.ticketType.getPrice();
        this.bookingID = bookingID;
        this.seatID = seatID;
        this.showID = showID;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public double getPrice() {
        return price;
    }

    public ObjectId getBookingID() {
        return bookingID;
    }

    public ObjectId getSeatID() {
        return seatID;
    }

    public ObjectId getShowID() {
        return showID;
    }

    public enum TicketType {
        ADULT(12.00),
        CHILD(8.00),
        SENIOR(9.50);

        private final double price;

        TicketType(double price) {
            this.price = price;
        }

        public double getPrice() {
            return price;
        }
    }
}
