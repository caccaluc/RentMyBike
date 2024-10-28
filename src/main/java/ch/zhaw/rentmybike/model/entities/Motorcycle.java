package ch.zhaw.rentmybike.model.entities;

import org.springframework.data.annotation.Id;

import com.mongodb.lang.NonNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Motorcycle {

    @Id
    private String id;
    @NonNull
    private String brand;
    @NonNull
    private String model;
    @NonNull
    private int year;
    @NonNull
    private String color;
    @NonNull
    private String licensePlate;
    @NonNull
    private int value;
    @NonNull
    private int ps;
    @NonNull
    private int km;
    @NonNull
    private String userId;

}
