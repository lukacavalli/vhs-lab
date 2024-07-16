package hr.north.vhs.controllers;

import hr.north.vhs.exceptions.VHSNotFoundException;
import hr.north.vhs.models.VHS;
import hr.north.vhs.repos.VHSRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/vhs")
public class VHSController {

    private static final Logger logger = LoggerFactory.getLogger(VHSController.class);

    @Autowired
    private VHSRepository vhsRepository;

    @GetMapping
    public Iterable<VHS> findAll() {
        logger.info("Fetching all VHS tapes");
        return vhsRepository.findAll();
    }

    @GetMapping("/{id}")
    public VHS findVHSById(@PathVariable Long id) {
        logger.info("Fetching VHS tape with id: {}", id);
        return vhsRepository.findById(id).orElseThrow(() -> {
            logger.error("VHS tape not found with id: {}", id);
            return new VHSNotFoundException(id);
        });
    }

    @GetMapping("/title/{title}")
    public List<VHS> findByTitle(@PathVariable String title) {
        logger.info("Fetching VHS tapes with title: {}", title);
        List<VHS> vhsList = vhsRepository.findByTitle(title);
        if (vhsList.isEmpty()) {
            logger.error("No VHS tapes found with title: {}", title);
            throw new VHSNotFoundException(0L);
        }
        return vhsList;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VHS create(@Valid @RequestBody VHS vhs) {
        logger.info("Creating new VHS tape with title: {}", vhs.getTitle());
        vhs.setTaken(false);
        return vhsRepository.save(vhs);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : result.getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        logger.error("Title must not be null");
        return errors;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        logger.info("Deleting VHS tape with id: {}", id);
        vhsRepository.findById(id).orElseThrow(() -> {
            logger.error("VHS tape not found with id: {}", id);
            return new VHSNotFoundException(id);
        });
        vhsRepository.deleteById(id);
        logger.info("Deleted VHS tape with id: {}", id);
    }
    @PutMapping("/{id}")
    public VHS updateVHS(@RequestBody VHS vhs, @PathVariable Long id) {
        logger.info("Updating VHS tape with id: {}", id);

        VHS existingVHS = vhsRepository.findById(id).orElseThrow(() -> {
            logger.error("VHS tape not found with id: {}", id);
            return new VHSNotFoundException(id);
        });

        existingVHS.setTitle(vhs.getTitle());
        existingVHS.setGenre(vhs.getGenre());
        existingVHS.setReleaseYear(vhs.getReleaseYear());
        existingVHS.setDurationInMinutes(vhs.getDurationInMinutes());

        logger.info("Updated VHS tape with id: {}", id);
        return vhsRepository.save(existingVHS);
    }
}
