package ch.zhaw.rentmybike.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ch.zhaw.rentmybike.model.entities.Adress;


public interface AdressRepository extends MongoRepository<Adress, String> {
}
