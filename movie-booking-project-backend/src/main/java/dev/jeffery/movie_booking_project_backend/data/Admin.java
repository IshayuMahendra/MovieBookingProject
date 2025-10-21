package dev.jeffery.movie_booking_project_backend.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.crypto.password.PasswordEncoder;

@Document(collection = "Admin")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin {
    @Id
    private ObjectId id;
    private String userID;
    private String password;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Admin(String userID, String password) {
        this.userID = userID;
        this.password = passwordEncoder.encode(password);
    }


    public String getUserID() { return userID; }
    public void setUserID(String userID) { this.userID = userID; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = passwordEncoder.encode(password); }
}
