package com.company.musicstorecatalog.controller;

import com.company.musicstorecatalog.exception.BadIdException;
import com.company.musicstorecatalog.model.Track;
import com.company.musicstorecatalog.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/track")
public class TrackController {

    @Autowired
    private TrackRepository trackRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Track createTrack(@RequestBody @Valid Track track){
        return trackRepository.save(track);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Track> getAllTracks(){
        return trackRepository.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Track getArtistById(@PathVariable int id){
        Optional<Track> optionalTrack = trackRepository.findById(id);// what happen if we dont use optional; null has many reason like none, error
        if (optionalTrack.isPresent() == false){
            throw new RuntimeException("No Track found with the id " + id);
        }
        return optionalTrack.get();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTrack(@PathVariable int id, @RequestBody Track track){
        if (track.getId() == null){
            track.setId(id);
        } else if (track.getId() != id) {
            throw new BadIdException("The id (" + id + ") in the path is " +
                    "not matching with the id (" + track.getId() + ") in the body.");
        }
        trackRepository.save(track);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTrack(@PathVariable("id") int id) {
        trackRepository.deleteById(id);
    }
}
