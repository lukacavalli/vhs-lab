package hr.north.vhs.controllers;

import hr.north.vhs.exceptions.PersonNotFoundException;
import hr.north.vhs.exceptions.UserNameAlreadyTakenException;
import hr.north.vhs.models.Person;
import hr.north.vhs.repos.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/persons")
public class PersonController {

    private static final Logger logger = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    private PersonRepository personRepository;

    @GetMapping
    public Iterable<Person> findAll() {
        logger.info("Fetching all persons");
        return personRepository.findAll();
    }

    @GetMapping("/person/{userName}")
    public Person findPersonByUserName(@PathVariable String userName) {
        logger.info("Fetching person with username: {}", userName);
        return personRepository.findByUserName(userName).orElseThrow(() -> {
            logger.error("Person not found with username: {}", userName);
            return new PersonNotFoundException(0L);
        });
    }

    @GetMapping("/{id}")
    public Person findPersonById(@PathVariable Long id) {
        logger.info("Fetching person with id: {}", id);
        return personRepository.findById(id).orElseThrow(() -> {
            logger.error("Person not found with id: {}", id);
            return new PersonNotFoundException(id);
        });
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Person create(@RequestBody Person user) {
        logger.info("Creating person with username: {}", user.getUserName());
        if (personRepository.existsByUserName(user.getUserName())) {
            logger.error("Username already taken: {}", user.getUserName());
            throw new UserNameAlreadyTakenException();
        }
        return personRepository.save(user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        logger.info("Deleting person with id: {}", id);
        personRepository.findById(id).orElseThrow(() -> {
            logger.error("Person not found with id: {}", id);
            return new PersonNotFoundException(id);
        });
        personRepository.deleteById(id);
        logger.info("Person with id: {} deleted successfully", id);
    }
}
