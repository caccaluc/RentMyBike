package ch.zhaw.rentmybike.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ch.zhaw.rentmybike.model.entities.User;

public interface UserRepository extends MongoRepository<User, String> {
    
    User findFirstByEmail(String email);
}
