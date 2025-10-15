package dev.jeffery.movie_booking_project_backend.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "User")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private ObjectId id;
    private String userID;
    private String password;
    private boolean isAdmin;
    private String email;
    private String firstName;
    private String lastName;
    private accountStatus customerStatus;
    private List<PaymentCard> cards;
    private String street;
    private String city;
    private String state;
    private String zipCode;
    public enum accountStatus{
        Active, Inactive, Suspended
    }

    public User(String userID, String password, boolean isAdmin, String email, String firstName, String lastName,
                accountStatus customerStatus, List<PaymentCard> cards, String street, String city, String state, String zipCode) {
        this.userID = userID;
        this.password = password;
        this.isAdmin = isAdmin;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.customerStatus = customerStatus;
        this.cards = cards;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }

    public ObjectId getId() { return id; }
    public void setId(ObjectId id) { this.id = id; }

    public String getUserID() { return userID; }
    public void setUserID(String userID) { this.userID = userID; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public boolean isAdmin() { return isAdmin; }
    public void setAdmin(boolean admin) { isAdmin = admin; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public accountStatus getCustomerStatus() { return customerStatus; }
    public void setCustomerStatus(accountStatus customerStatus) { this.customerStatus = customerStatus; }

    public List<PaymentCard> getCards() { return cards; }
    public void setCards(List<PaymentCard> cards) { this.cards = cards; }

    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getZipCode() { return zipCode; }
    public void setZipCode(String zipCode) { this.zipCode = zipCode; }
}
