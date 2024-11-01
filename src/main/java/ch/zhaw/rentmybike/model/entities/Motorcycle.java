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
@Document("Motorcycle")
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
// wäre cool mit zusätzlichen Bildern zu arbeiten, wie kann ich das machen?
// könnte ich zusätzliche Infos von einer API holen und diese in die Datenbank speichern? -> muss demnach auch als Attribut geführt sein.