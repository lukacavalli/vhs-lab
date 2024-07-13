package hr.north.vhs.controllers;

import hr.north.vhs.exceptions.PersonNotFoundException;
import hr.north.vhs.exceptions.RentalNotFoundException;
import hr.north.vhs.exceptions.VHSAlreadyTakenException;
import hr.north.vhs.exceptions.VHSNotFoundException;
import hr.north.vhs.models.Person;
import hr.north.vhs.models.Rental;
import hr.north.vhs.models.VHS;
import hr.north.vhs.repos.PersonRepository;
import hr.north.vhs.repos.RentalRepository;
import hr.north.vhs.repos.VHSRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/rental")
public class RentalController {

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private VHSRepository vhsRepository;

    @Autowired
    private PersonRepository personRepository;

    @GetMapping
    public Iterable<Rental> findAll() {
        return rentalRepository.findAll();
    }

    @GetMapping("/{id}")
    public Rental findOne(@PathVariable Long id) {
        return rentalRepository.findById(id).orElseThrow(() -> new RentalNotFoundException(id));
    }

    @PostMapping("/{personId}/{vhsId}")
    public String create(@RequestBody Rental rental, @PathVariable Long personId, @PathVariable Long vhsId) {

        VHS vhs = vhsRepository.findById(vhsId).orElseThrow(() -> new VHSNotFoundException(vhsId));
        Person person = personRepository.findById(personId).orElseThrow(() -> new PersonNotFoundException(personId));

        if (vhs.isTaken())
            throw new VHSAlreadyTakenException(vhsId);

        rental.setPersonId(personId);
        rental.setVhsId(vhsId);
        rental.setUserName(person.getUserName());
        rental.setVhsTitle(vhs.getTitle());

        vhs.setTaken(true);

        rentalRepository.save(rental);

        return "Thanks " + person.getUserName() + " for renting the movie: " + vhs.getTitle() +
                "\nPlease return the movie in 7 days. After that the late fee is 2€ per day.";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {

        Rental rental = rentalRepository.findById(id).orElseThrow(() -> new RentalNotFoundException(id));
        VHS vhs = vhsRepository.findById(rental.getVhsId()).orElseThrow(() -> new VHSNotFoundException(rental.getVhsId()));
        Person person = personRepository.findById(rental.getPersonId()).orElseThrow(() -> new PersonNotFoundException(rental.getPersonId()));

        vhs.setTaken(false);

        long days = getDifferenceDays(rental.getCreationDate(), new Date());
        long overdueDays = Math.max(0, days - 7);
        long fee = overdueDays * 2;

        rentalRepository.deleteById(id);

        return "Thanks " + person.getUserName() + " for returning the movie: " + vhs.getTitle()
                + ". The late fees are: " + fee + "€.";
    }

    public static long getDifferenceDays(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }
}
