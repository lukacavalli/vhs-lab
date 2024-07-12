package hr.north.vhs.controllers;

import hr.north.vhs.exceptions.PersonNotFoundException;
import hr.north.vhs.models.Person;
import hr.north.vhs.repos.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/persons")
public class PersonController {
    @Autowired
    private PersonRepository personRepository;

    @GetMapping
    public Iterable<Person> findAll() {
        return personRepository.findAll();
    }

    @GetMapping("/person/{userName}")
    public List<Person> findByTitle(@PathVariable String userName) {
        List<Person> listOfPersons = personRepository.findByUserName(userName);
        if (listOfPersons.isEmpty())
                throw new PersonNotFoundException(0L);
        return listOfPersons;
    }

    @GetMapping("/{id}")
    public Person findOne(@PathVariable Long id) {
        return personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Person create(@RequestBody Person user) {
        return personRepository.save(user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
        personRepository.deleteById(id);
    }
}
