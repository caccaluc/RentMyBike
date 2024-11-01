package ch.zhaw.rentmybike.model.entities.dtos;

import ch.zhaw.rentmybike.model.entities.User.UserState;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter 
@NoArgsConstructor
public class UserDTO {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private UserState state; // Status des Benutzers
    private String licenseCode; // Optional, nur für verifizierte Fahrer erforderlich
}
