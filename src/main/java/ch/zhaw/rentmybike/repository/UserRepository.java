package ch.zhaw.rentmybike.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import ch.zhaw.rentmybike.model.entities.User;
import ch.zhaw.rentmybike.model.entities.User.UserState;




public interface UserRepository extends MongoRepository<User, String> {
    
    User findFirstByEmail(String email);
    Page<User> findByState(UserState state, Pageable pageable);
    
}
