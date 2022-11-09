package com.company.musicstorecatalog.repository;

import com.company.musicstorecatalog.model.Artist;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ArtistRepositoryTest {

    @Autowired
    private ArtistRepository repo;
    @Before
    public void setUp()throws Exception{

    }
    @Test
    public void shouldInteractWithDatabase(){
        Artist artist =new Artist("Artist","Artist2022", "Artist2022");
        Artist expectedArtist =new Artist("Artist","Artist2022", "Artist2022");
        repo.save(artist);
        expectedArtist.setId(artist.getId());
        assertEquals(expectedArtist, artist);

        // Act
        List<Artist> allTheArtist = repo.findAll();

        // Assert
        assertEquals(1, allTheArtist.size());

        // Act
        repo.deleteById(artist.getId());

        allTheArtist = repo.findAll();
        assertEquals(0, allTheArtist.size());
    }

}