package dev.jeffery.movie_booking_project_backend.data;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "User")
@Data
@NoArgsConstructor
public class User {
    @Id
    private ObjectId id = new ObjectId();
    private String userID;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private accountStatus customerStatus;
    private String street;
    private String city;
    private String state;
    private String zipCode;
    private boolean promotions;
    public enum accountStatus{
        Active, Inactive, Suspended
    }

    public User(String userID, String password, String email, String firstName, String lastName,
                accountStatus customerStatus, String street, String city, String state, String zipCode, boolean promotions) {
        this.userID = userID;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.customerStatus = customerStatus;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.promotions = promotions;
    }

    public ObjectId getId() { return id; }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getUserID() { return userID; }
    public void setUserID(String userID) { this.userID = userID; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public accountStatus getCustomerStatus() { return customerStatus; }
    public void setCustomerStatus(accountStatus customerStatus) { this.customerStatus = customerStatus; }

    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getZipCode() { return zipCode; }
    public void setZipCode(String zipCode) { this.zipCode = zipCode; }

    public boolean getPromotions() { return promotions; }
    public void setPromotions(boolean promotions) { this.promotions = promotions; }
}
