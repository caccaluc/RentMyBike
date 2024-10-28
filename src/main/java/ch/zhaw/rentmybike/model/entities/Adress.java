package ch.zhaw.rentmybike.model.entities;

import org.springframework.data.annotation.Id;

import com.mongodb.lang.NonNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class Adress {

    @Id
    private String id;
    @NonNull
    private String country;
    @NonNull
    private String city;
    @NonNull
    private int postalCode;
    @NonNull
    private String street;
    @NonNull
    private int streetNumber;


}
