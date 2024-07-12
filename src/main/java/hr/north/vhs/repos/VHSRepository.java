package hr.north.vhs.repos;

import hr.north.vhs.models.VHS;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VHSRepository extends CrudRepository<VHS, Long> {
    List<VHS> findByTitle(String title);
}
