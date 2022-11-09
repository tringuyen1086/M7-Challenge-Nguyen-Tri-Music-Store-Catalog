package com.company.musicstorecatalog.controller;

import com.company.musicstorecatalog.exception.BadIdException;
import com.company.musicstorecatalog.model.Label;
import com.company.musicstorecatalog.repository.LabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/label")
public class LabelController {

    @Autowired
    private LabelRepository labelRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Label createLabel(@RequestBody @Valid Label label){
        return labelRepository.save(label);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Label> getAllLabels(){
        return labelRepository.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Label getArtistById(@PathVariable int id){
        Optional<Label> optionalLabel = labelRepository.findById(id);// what happen if we dont use optional; null has many reason like none, error
        if (optionalLabel.isPresent() == false){
            throw new RuntimeException("No Label found with the id " + id);
        }
        return optionalLabel.get();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateLabel(@PathVariable int id, @RequestBody Label label){
        if (label.getId() == null){
            label.setId(id);
        } else if (label.getId() != id) {
            throw new BadIdException("The id (" + id + ") in the path is " +
                    "not matching with the id (" + label.getId() + ") in the body.");
        }
        labelRepository.save(label);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLabel(@PathVariable("id") int id) {
        labelRepository.deleteById(id);
    }

}
