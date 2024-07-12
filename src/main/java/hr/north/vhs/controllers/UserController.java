package hr.north.vhs.controllers;

import hr.north.vhs.exceptions.UserNotFoundException;
import hr.north.vhs.models.Korisnik;
import hr.north.vhs.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public Iterable findAll() {
        return userRepository.findAll();
    }

    @GetMapping("/user/{userName}")
    public List findByTitle(@PathVariable String userName) {
        return userRepository.findByUserName(userName);
    }

    @GetMapping("/{id}")
    public Korisnik findOne(@PathVariable Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Korisnik create(@RequestBody Korisnik user) {
        return userRepository.save(user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        userRepository.deleteById(id);
    }
}
