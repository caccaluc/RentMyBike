package ch.zhaw.rentmybike.model.DTO;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;


@Getter
@Setter
@NoArgsConstructor
public class CreateRideDTO {
    
    private String motorcycleId; 
    private String pickupAddressId; 
    private LocalDateTime startingTime; 
    private LocalDateTime endingTime; 
    private int price; 
}

// nonull nicht gesetzt da schon in Entity? kann ich das so machen?
// ausserdem will ich die UserID vom eingeloggten haben und diese nicht im DTO setzen, wie mache ich das?