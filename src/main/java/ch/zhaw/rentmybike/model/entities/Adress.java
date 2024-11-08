package ch.zhaw.rentmybike.model.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor

public class Adress {

  
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
    public void setId(String addressId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setId'");
    }


}
