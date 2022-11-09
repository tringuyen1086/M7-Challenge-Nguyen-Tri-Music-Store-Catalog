package com.company.musicstorecatalog.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name="album")
public class Album implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "album_id")
    private Integer id;

    @NotNull
    private String title;

    @NotNull
    @Column(name="artist_id")
    private Integer artistId;

    @Column(name = "release_date")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;

    @NotNull
    @Column(name="label_id")
    private Integer labelId;

    @Column(name = "list_price")
    private double listPrice;

    public Album(){

    }
    public Album(Integer id, String title, Integer artistId, LocalDate releaseDate, Integer labelId, double listPrice) {
        this.id = id;
        this.title = title;
        this.artistId = artistId;
        this.releaseDate = releaseDate;
        this.labelId = labelId;
        this.listPrice = listPrice;
    }

    public Album(String title, Integer artistId, LocalDate releaseDate, Integer labelId, double listPrice) {
        this.title = title;
        this.artistId = artistId;
        this.releaseDate = releaseDate;
        this.labelId = labelId;
        this.listPrice = listPrice;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getArtistId() {
        return artistId;
    }

    public void setArtistId(Integer artistId) {
        this.artistId = artistId;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getLabelId() {
        return labelId;
    }

    public void setLabelId(Integer labelId) {
        this.labelId = labelId;
    }

    public double getListPrice() {
        return listPrice;
    }

    public void setListPrice(double listPrice) {
        this.listPrice = listPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Album album = (Album) o;
        return Double.compare(album.listPrice, listPrice) == 0 && Objects.equals(id, album.id) && Objects.equals(title, album.title) && Objects.equals(artistId, album.artistId) && Objects.equals(releaseDate, album.releaseDate) && Objects.equals(labelId, album.labelId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, artistId, releaseDate, labelId, listPrice);
    }

    @Override
    public String toString() {
        return "Album{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", artistId=" + artistId +
                ", releaseDate=" + releaseDate +
                ", labelId=" + labelId +
                ", listPrice=" + listPrice +
                '}';
    }
}
