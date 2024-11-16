package ch.zhaw.rentmybike.model.entities;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Document("User")
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
    // 
    private Adress adress;
    @NonNull
    private Map<String, Map<String, Integer>> ratings = new HashMap<>();
    private List<String> motorcycleIds = new java.util.ArrayList<>();

    public enum UserState {
        NEW, // New user, not yet activated
        ACTIVE, // Active and ready to use the system
        DEACTIVATED // Deactivated 
    }
}
