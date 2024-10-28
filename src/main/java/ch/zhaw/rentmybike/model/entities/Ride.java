package ch.zhaw.rentmybike.model.entities;

import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;

import com.mongodb.lang.NonNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class Ride {

    @Id
    private String id;
    @NonNull
    private User OwnerId;
    @NonNull
    private User RenterId;
    @NonNull
    private Motorcycle motorcycleId;
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
