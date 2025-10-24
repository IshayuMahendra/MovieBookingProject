package dev.jeffery.movie_booking_project_backend.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Theater")
@Data
@NoArgsConstructor
public class Theater {
    @Id
    private ObjectId id = new ObjectId();
    private String name;
    private String address;
    private String phoneNumber;
    private ObjectId cinemaID;

    public Theater(String name, String address, String phoneNumber, ObjectId cinemaID){
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.cinemaID = cinemaID;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public ObjectId getCinemaID() {
        return cinemaID;
    }
}
