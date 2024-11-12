package ch.zhaw.rentmybike.model.dtos;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;


@Getter
@Setter
@NoArgsConstructor
public class CreateRideDTO {
    
    private String motorcycleId; 
    private String country;
    private String city;
    private int postalCode;
    private String street;
    private int streetNumber;
    private LocalDateTime startingTime; 
    private LocalDateTime endingTime; 
    private int price; 
}

