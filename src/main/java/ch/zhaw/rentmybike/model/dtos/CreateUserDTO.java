package ch.zhaw.rentmybike.model.dtos;

import java.time.LocalDate;
import java.util.List;

import ch.zhaw.rentmybike.model.entities.Adress;
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
    private Adress adress; 
    private LocalDate birthdate; 
    private List<String> motorcycleIds;
}
