package hr.north.vhs.repos;

import hr.north.vhs.models.Person;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PersonRepository extends CrudRepository<Person, Long> {
    Optional<Person> findByUserName(String userName);
    boolean existsByUserName(String userName);
}
