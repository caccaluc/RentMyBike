package ch.zhaw.rentmybike.model.entities.dtos;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class CreateUserDTO {
    
    private String id;
    private String firstName; 
    private String lastName; 
    private String email; 
    private String licenseCode; 
    private String phoneNumber;
    private String addressId;
    private LocalDate birthdate; 
    private List<String> motorcycleIds;
}
