package com.company.musicstorecatalog.controller;

import com.company.musicstorecatalog.exception.BadIdException;
import com.company.musicstorecatalog.model.Album;
import com.company.musicstorecatalog.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/album")
public class AlbumController {

    @Autowired
    private AlbumRepository albumRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Album createAlbum(@RequestBody @Valid Album album){

        return albumRepository.save(album);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Album> getAllAlbums(){
        return albumRepository.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Album getAlbumById(@PathVariable int id){
        Optional<Album> optionalAlbum = albumRepository.findById(id);
        if (optionalAlbum.isPresent() == false){
            throw new RuntimeException("No Album found with id " + id);
        }
        return optionalAlbum.get();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAlbum(@PathVariable int id, @RequestBody Album album){
        if (album.getId() == null){
            album.setId(id);
        } else if (album.getId() != id) {
            throw new BadIdException("The id (" + id + ") in the path is " +
                    "not matching with the id (" + album.getId() + ") in the body.");
        }
        albumRepository.save(album);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAlbum(@PathVariable("id") int id) {
        albumRepository.deleteById(id);
    }
}
