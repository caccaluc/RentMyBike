package ch.zhaw.rentmybike.model.entities;

import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@Document("Ride")
public class Ride {

    @Id
    private String id;
    @NonNull
    private String OwnerId;
    @NonNull
    private String RenterId;
    @NonNull
    private String motorcycleId;
    @NonNull
    private Adress pickupAdress;
    @NonNull
    private LocalDateTime startingTime;
    @NonNull
    private LocalDateTime endingTime;
    @NonNull
    private int price = 0;
    @NonNull
    private RideStatus status = RideStatus.AVAILABLE;
    

    public enum RideStatus {
        AVAILABLE,
        BOOKED,
        COMPLETED,
     }


}
