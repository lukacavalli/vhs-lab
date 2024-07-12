package hr.north.vhs.repos;

import hr.north.vhs.models.Person;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PersonRepository extends CrudRepository<Person, Long> {
    List<Person> findByUserName(String userName);
}
