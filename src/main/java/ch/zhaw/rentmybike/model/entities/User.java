package ch.zhaw.rentmybike.model.entities;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;

import com.mongodb.lang.NonNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class User {

    
    @Id
    private String id;
    @NonNull
    private UserState state = UserState.NEW;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    private String email;
    private String licenceCode;
    private LocalDate birthdate;
    private String phoneNumber;
    @NonNull
    private double averageRating = 0.0;
    @NonNull
    private int numberOfRatings = 0;
    private String adressID;
    @NonNull
    private Map<String, Map<String, Integer>> ratings = new HashMap<>();
    private List<String> carIds = new java.util.ArrayList<>();

    public enum UserState {
        NEW, // New user, not yet activated
        PENDING, // Added user information, waiting for activation
        ACTIVE, // Active and ready to use the system
        UPDATED, // User information has been updated
        INCOMPLETE, // User information is incomplete (e.g. missing email)
        DEACTIVATED // Deactivated 
    }
}