package ch.zhaw.rentmybike.model.dtos;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class CreateUserDTO {
    
    private String firstName; 
    private String lastName; 
    private String email; 
    private String licenseCode; 
    private String phoneNumber;
    private String country;
    private String city;            // Adressdaten einzeln geführt, damit der User nicht eine ID selbst eintippen muss (um Redundanzen zu vermeiden)
    private int postalCode;
    private String street;
    private int streetNumber;
    private LocalDate birthdate; 
    private List<String> motorcycleIds;
}
