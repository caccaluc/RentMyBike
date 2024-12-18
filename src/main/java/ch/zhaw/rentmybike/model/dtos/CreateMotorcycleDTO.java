package ch.zhaw.rentmybike.model.dtos;


import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class CreateMotorcycleDTO {
    
    private String brand;
    private String model;
    private int year;
    private String color;
    private String licensePlate;
    private int value;
    private int ps;
    private int km;
    
}
