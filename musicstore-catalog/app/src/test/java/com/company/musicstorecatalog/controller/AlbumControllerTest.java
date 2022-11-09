package com.company.musicstorecatalog.controller;

import com.company.musicstorecatalog.exception.BadIdException;
import com.company.musicstorecatalog.model.Album;
import com.company.musicstorecatalog.repository.AlbumRepository;
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

import java.time.LocalDate;
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
@WebMvcTest(AlbumController.class)
public class AlbumControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlbumRepository repo;

    private ObjectMapper mapper = new ObjectMapper();

    private Album helloAlbum;
    private String helloJson;
    private List<Album> allAlbums = new ArrayList<>();
    private String allAlbumsJson;

    @Before
    public void setup() throws Exception {
        helloAlbum = new Album(1,"Hello", 1, LocalDate.of(2015,10,23), 1, 29.99);
        helloJson = mapper.writeValueAsString(helloAlbum);

        Album album = new Album("World", 1, LocalDate.of(2015,10,23), 1, 29.99);
        album.setId(36);
        allAlbums.add(helloAlbum);
        allAlbums.add(album);

        allAlbumsJson = mapper.writeValueAsString(allAlbums);
    }

    @Test
    public void shouldCreateNewAlbumOnPostRequest() throws Exception {
        Album inputAlbum = new Album("World", 1, LocalDate.of(2015,10,23), 1, 29.99);
        inputAlbum.setId(36);
        String inputJson = mapper.writeValueAsString(inputAlbum);

        doReturn(helloAlbum).when(repo).save(inputAlbum);

        mockMvc.perform(post("/album")
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(helloJson));

    }

    @Test
    public void shouldReturnAlbumById() throws Exception {
        doReturn(Optional.of(helloAlbum)).when(repo).findById(1010);

        mockMvc.perform(get("/album/1010"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(helloJson));
    }

    @Test
    public void shouldThrowBadExceptionAlbumId() throws Exception {
        doThrow(new BadIdException()).when(repo).findById(2022);

        mockMvc.perform(get("/album/2022"))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    @Test
    public void shouldReturnAllAlbums() throws Exception {
        doReturn(allAlbums).when(repo).findAll();

        mockMvc.perform(get("/album"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(allAlbumsJson));
    }

    @Test
    public void shouldUpdateByIdAndReturn204StatusCode() throws Exception {
        Album outputAlbum = new Album();
        outputAlbum.setArtistId(1);
        outputAlbum.setListPrice(1);
        outputAlbum.setTitle("Your Power");
        outputAlbum.setReleaseDate(LocalDate.of(2021,04,29));
        outputAlbum.setId(88);

        String outputJson = mapper.writeValueAsString(outputAlbum);

        mockMvc.perform(put("/album/88")
                .content(outputJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldDeleteByIdAndReturn204StatusCode() throws Exception {
        mockMvc.perform(delete("/album/22")).andExpect(status().isNoContent());
    }

}