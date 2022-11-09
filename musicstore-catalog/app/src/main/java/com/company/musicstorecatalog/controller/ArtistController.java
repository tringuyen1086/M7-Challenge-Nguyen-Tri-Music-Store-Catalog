package com.company.musicstorecatalog.controller;

import com.company.musicstorecatalog.exception.BadIdException;
import com.company.musicstorecatalog.model.Artist;
import com.company.musicstorecatalog.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/artist")
public class ArtistController {

    @Autowired
    private ArtistRepository artistRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Artist createArtist(@RequestBody @Valid Artist artist){
        return artistRepository.save(artist);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Artist> getAllArtists(){
        return artistRepository.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Artist getArtistById(@PathVariable int id){
        Optional<Artist> optionalArtist = artistRepository.findById(id);// what happen if we dont use optional; null has many reason like none, error
        if (optionalArtist.isPresent() == false){
            throw new RuntimeException("No Artist found with the id " + id);
        }
        return optionalArtist.get();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateArtist(@PathVariable int id, @RequestBody Artist artist){
        if (artist.getId() == null){
            artist.setId(id);
        } else if (artist.getId() != id) {
            throw new BadIdException("The id (" + id + ") in the path is " +
                    "not matching with the id (" + artist.getId() + ") in the body.");
        }
        artistRepository.save(artist);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArtist(@PathVariable("id") int id) {
        artistRepository.deleteById(id);
    }
}
