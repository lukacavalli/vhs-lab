package hr.north.vhs.repos;

import hr.north.vhs.models.Korisnik;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<Korisnik, Long> {
    List<Korisnik> findByUserName(String userName);
}
