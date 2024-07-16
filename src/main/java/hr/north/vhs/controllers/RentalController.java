package hr.north.vhs.controllers;

import hr.north.vhs.exceptions.*;
import hr.north.vhs.models.Person;
import hr.north.vhs.models.Rental;
import hr.north.vhs.models.VHS;
import hr.north.vhs.repos.PersonRepository;
import hr.north.vhs.repos.RentalRepository;
import hr.north.vhs.repos.VHSRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/rental")
public class RentalController {

    private static final Logger logger = LoggerFactory.getLogger(RentalController.class);

    private static final int ALLOWED_OVERDUE_DAYS = 7;

    private static final int FEE_MULTIPLIER = 2;

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private VHSRepository vhsRepository;

    @Autowired
    private PersonRepository personRepository;

    @GetMapping
    public Iterable<Rental> findAll() {
        logger.info("Fetching all rentals");
        return rentalRepository.findAll();
    }

    @GetMapping("/{id}")
    public Rental findRentalById(@PathVariable Long id) {
        logger.info("Fetching rental with id: {}", id);
        return rentalRepository.findById(id).orElseThrow(() -> {
            logger.error("Rental not found with id: {}", id);
            return new RentalNotFoundException(id);
        });
    }

    @PostMapping("/{personId}/{vhsId}")
    public String create(@RequestBody Rental rental, @PathVariable Long personId, @PathVariable Long vhsId) {

        logger.info("Creating rental for personId: {} and vhsId: {}", personId, vhsId);

        if (rental.getCreationDate() == null) {
            logger.error("Creation date must not be null");
            throw new CreationDateNullException();
        }

        VHS vhs = vhsRepository.findById(vhsId).orElseThrow(() -> {
            logger.error("VHS not found with id: {}", vhsId);
            return new VHSNotFoundException(vhsId);
        });

        Person person = personRepository.findById(personId).orElseThrow(() -> {
            logger.error("Person not found with id: {}", personId);
            return new PersonNotFoundException(personId);
        });

        if (vhs.isTaken()) {
            logger.error("VHS already taken with id: {}", vhsId);
            throw new VHSAlreadyTakenException(vhsId);
        }

        rental.setPersonId(personId);
        rental.setVhsId(vhsId);

        vhs.setTaken(true);

        vhsRepository.save(vhs);
        rentalRepository.save(rental);

        Date returnDate = new Date(rental.getCreationDate().getTime() + (1000 * 60 * 60 * 24 * ALLOWED_OVERDUE_DAYS));
        SimpleDateFormat formatter = new SimpleDateFormat("dd MM yyyy");
        String formattedDate = formatter.format(returnDate);

        logger.info("Rental created successfully for personId: {} and vhsId: {}", personId, vhsId);
        return "Thanks " + person.getUserName() + " for renting the movie " + vhs.getTitle() +
                ".\nPlease return the movie in " + ALLOWED_OVERDUE_DAYS + " days. (" + formattedDate + ") \nAfter that the late fee is "
                + FEE_MULTIPLIER + "€ per day.";
    }

    @PutMapping("/{id}")
    public String returnRental(@RequestBody Rental rentalBody, @PathVariable Long id) {

        logger.info("Updating rental with id: {}", id);

        if (rentalBody.getReturnDate() == null) {
            logger.error("Return date must not be null");
            throw new ReturnDateNullException();
        }

        Rental rental = rentalRepository.findById(id).orElseThrow(() -> {
            logger.error("Rental not found with id: {}", id);
            return new RentalNotFoundException(id);
        });

        VHS vhs = vhsRepository.findById(rental.getVhsId()).orElseThrow(() -> {
            logger.error("VHS not found with id: {}", rental.getVhsId());
            return new VHSNotFoundException(rental.getVhsId());
        });

        Person person = personRepository.findById(rental.getPersonId()).orElseThrow(() -> {
            logger.error("Person not found with id: {}", rental.getPersonId());
            return new PersonNotFoundException(rental.getPersonId());
        });

        rental.setReturnDate(rentalBody.getReturnDate());
        vhs.setTaken(false);
        vhsRepository.save(vhs);
        rentalRepository.save(rental);

        long fee = getFee(rental.getCreationDate(), rental.getReturnDate());

        logger.info("Rental updated successfully for id: {}. Late fee: {}€", id, fee);
        return "Thanks " + person.getUserName() + " for returning the movie " + vhs.getTitle()
                + ".\nThe late fees are: " + fee + "€.\nYou were late " + fee/FEE_MULTIPLIER + " days.";
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        logger.info("Deleting rental with id: {}", id);
        rentalRepository.findById(id).orElseThrow(() -> {
            logger.error("Rental not found with id: {}", id);
            return new RentalNotFoundException(id);
        });
        rentalRepository.deleteById(id);
        logger.info("Rental with id: {} deleted successfully", id);
    }

    /**
     * Returns 0 if the difference in days is less than ALLOWED_OVERDUE_DAYS, otherwise returns overdueDays * FEE_MULTIPLIER.
     */
    private static long getFee(Date d1, Date d2) {
        long differenceDays = getDifferenceDays(d1, d2);
        long overdueDays = Math.max(0, differenceDays - ALLOWED_OVERDUE_DAYS);
        return overdueDays * FEE_MULTIPLIER;
    }

    private static long getDifferenceDays(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        if (diff < 0) {
            logger.error("Return date must be after the creation date");
            throw new InvalidDateException();
        }
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }
}
