package hr.north.vhs.controllers;

import hr.north.vhs.exceptions.VHSIdMismatchException;
import hr.north.vhs.exceptions.VHSNotFoundException;
import hr.north.vhs.models.VHS;
import hr.north.vhs.repos.VHSRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vhs")
public class VHSController {
    @Autowired
    private VHSRepository vhsRepository;

    @GetMapping
    public Iterable<VHS> findAll() {
        return vhsRepository.findAll();
    }

    @GetMapping("/{id}")
    public VHS findOne(@PathVariable Long id) {
        return vhsRepository.findById(id).orElseThrow(() -> new VHSNotFoundException(id));
    }

    @GetMapping("/title/{title}")
    public List<VHS> findByTitle(@PathVariable String title) {
        List<VHS> vhsList = vhsRepository.findByTitle(title);
        if (vhsList.isEmpty())
            throw new VHSNotFoundException(0L);
        return vhsList;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VHS create(@RequestBody VHS vhs) {
        return vhsRepository.save(vhs);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        vhsRepository.findById(id).orElseThrow(() -> new VHSNotFoundException(id));
        vhsRepository.deleteById(id);
    }
    @PutMapping("/{id}")
    public VHS updateVHS(@RequestBody VHS vhs, @PathVariable Long id) {

        VHS existingVHS = vhsRepository.findById(id).orElseThrow(() -> new VHSNotFoundException(id));

        existingVHS.setTitle(vhs.getTitle());
        existingVHS.setGenre(vhs.getGenre());
        existingVHS.setReleaseYear(vhs.getReleaseYear());
        existingVHS.setDurationInMinutes(vhs.getDurationInMinutes());

        return vhsRepository.save(existingVHS);
    }
}
