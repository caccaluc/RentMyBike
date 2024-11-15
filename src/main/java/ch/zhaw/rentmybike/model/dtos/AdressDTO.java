package ch.zhaw.rentmybike.model.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor

// einzelne DTO um saubere Strukturierung zu gewährleisten
public class AdressDTO {
    private String country;
    private String city;
    private int postalCode;
    private String street;
    private int streetNumber;
}
