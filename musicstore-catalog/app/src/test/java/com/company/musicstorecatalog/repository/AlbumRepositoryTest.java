package com.company.musicstorecatalog.repository;

import com.company.musicstorecatalog.model.Album;
import com.company.musicstorecatalog.model.Artist;
import com.company.musicstorecatalog.model.Label;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class AlbumRepositoryTest {

    @Autowired
    private AlbumRepository repo;
    @Autowired
    private LabelRepository labelRepo;
    @Autowired
    private ArtistRepository artistRepo;

    @Before
    public void setUp()throws Exception{

        Label label1 =new Label("Sony Music Entertainment","Sony.com");
        Label expectedLabel =new Label(1,"Sony Music Entertainment","Sony.com");
        labelRepo.save(label1);
        Artist artist1 =new Artist("Tri", "Tri2022","Tri2022");
        Artist expectedArtist =new Artist(1,"Tri", "Tri2022","Tri2022");
        artistRepo.save(artist1);


    }
    @Test
    public void shouldInteractWithDatabase(){
        // Arrange
        Album album =new Album("Hello",1, LocalDate.of(2015,10,23),1, 29.99);
        Album expectedAlbum =new Album(1,"Hello",1, LocalDate.of(2015,10,23),1, 29.99);
        repo.save(album);
        expectedAlbum.setId(album.getId());
        assertEquals(expectedAlbum, album);

        // Act
        List<Album> allTheAlbum = repo.findAll();

        // Assert
        assertEquals(1, allTheAlbum.size());

        // Act
        repo.deleteById(album.getId());

        allTheAlbum = repo.findAll();
        assertEquals(0, allTheAlbum.size());
    }

}