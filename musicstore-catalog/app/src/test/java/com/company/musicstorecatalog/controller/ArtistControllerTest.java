package com.company.musicstorecatalog.controller;

import com.company.musicstorecatalog.exception.BadIdException;
import com.company.musicstorecatalog.model.Artist;
import com.company.musicstorecatalog.repository.ArtistRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ArtistController.class)
public class ArtistControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArtistRepository repo;

    private ObjectMapper mapper = new ObjectMapper();

    private Artist helloArtist;
    private String helloJson;
    private List<Artist> allArtists = new ArrayList<>();
    private String allArtistsJson;

    @Before
    public void setup() throws Exception {
        helloArtist = new Artist(1,"Tri", "Tri2022", "Tri2022");
        helloJson = mapper.writeValueAsString(helloArtist);

        Artist artist = new Artist("Tri", "Tri2022", "Tri2022");
        artist.setId(36);
        allArtists.add(helloArtist);
        allArtists.add(artist);

        allArtistsJson = mapper.writeValueAsString(allArtists);
    }

    @Test
    public void shouldCreateNewArtistOnPostRequest() throws Exception {
        Artist inputArtist = new Artist("Tri", "Tri2022", "Tri2022");
        inputArtist.setId(36);
        String inputJson = mapper.writeValueAsString(inputArtist);

        doReturn(helloArtist).when(repo).save(inputArtist);

        mockMvc.perform(post("/artist")
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(helloJson));

    }

    @Test
    public void shouldReturnArtistById() throws Exception {
        doReturn(Optional.of(helloArtist)).when(repo).findById(1010);

        mockMvc.perform(get("/artist/1010"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(helloJson));

    }

    @Test
    public void shouldThrowBadExceptionArtistId() throws Exception {
        doThrow(new BadIdException()).when(repo).findById(2022);

        mockMvc.perform(get("/artist/2022"))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    @Test
    public void shouldReturnAllArtists() throws Exception {
        doReturn(allArtists).when(repo).findAll();

        mockMvc.perform(get("/artist"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(allArtistsJson)
                );
    }

    @Test
    public void shouldUpdateByIdAndReturn204StatusCode() throws Exception {
        Artist outputArtist = new Artist();
        outputArtist.setInstagram("tri2022");
        outputArtist.setName("tri");
        outputArtist.setTwitter("tri2022");
        outputArtist.setId(88);

        String outputJson = mapper.writeValueAsString(outputArtist);

        mockMvc.perform(put("/artist/88")
                .content(outputJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldDeleteByIdAndReturn204StatusCode() throws Exception {
        mockMvc.perform(delete("/artist/22")).andExpect(status().isNoContent());
    }


}