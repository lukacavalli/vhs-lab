package hr.north.vhs.repos;

import hr.north.vhs.models.Rental;
import org.springframework.data.repository.CrudRepository;

public interface RentalRepository extends CrudRepository<Rental, Long> {

}
