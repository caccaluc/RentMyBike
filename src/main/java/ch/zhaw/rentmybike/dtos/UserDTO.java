package ch.zhaw.rentmybike.dtos;

import ch.zhaw.rentmybike.model.entities.User.UserState;

public class UserDTO {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private UserState state; // Status des Benutzers
    private String licenseCode; // Optional, nur für verifizierte Fahrer erforderlich
}
