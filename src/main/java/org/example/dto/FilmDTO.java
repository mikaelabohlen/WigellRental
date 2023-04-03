package org.example.dto;

import org.example.dao.FilmDAO;
import org.example.entities.Actor;
import org.example.entities.Category;
import org.example.entities.Film;
import org.example.entities.Language;
import org.example.enums.Rating;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;


public class FilmDTO {
    private Integer filmId;
    private String title;
    private String description;
    private Integer releaseYear;
    private LanguageDTO language;
    private LanguageDTO originalLanguage;
    private Integer rentalDuration;
    private BigDecimal rentalRate;
    private Integer length;
    private BigDecimal replacementCost;
    private Rating rating;
    private String specialFeatures;
    private Timestamp lastUpdate;
    private List<Actor> actors;
    private List<Category> categories;

    public FilmDTO(
            Integer filmId,
            String title,
            String description,
            Integer releaseYear,
            LanguageDTO language,
            LanguageDTO originalLanguage,
            Integer rentalDuration,
            BigDecimal rentalRate,
            Integer length,
            BigDecimal replacementCost,
            Rating rating,
            String specialFeatures,
            Timestamp lastUpdate,
            List<Actor> actors,
            List<Category> categories) {
        this.filmId = filmId;
        this.title = title;
        this.description = description;
        this.releaseYear = releaseYear;
        this.language = language;
        this.originalLanguage = originalLanguage;
        this.rentalDuration = rentalDuration;
        this.rentalRate = rentalRate;
        this.length = length;
        this.replacementCost = replacementCost;
        this.rating = rating;
        this.specialFeatures = specialFeatures;
        this.lastUpdate = lastUpdate;
        this.actors = actors;
        this.categories = categories;
    }

    public static FilmDTO fromEntity(Film film) {
        return new FilmDTO(
                film.getFilmId(),
                film.getTitle(),
                film.getDescription(),
                film.getReleaseYear(),
                LanguageDTO.fromEntity(film.getLanguage()),
                LanguageDTO.fromEntity(film.getOriginalLanguage()),
                film.getRentalDuration(),
                film.getRentalRate(),
                film.getLength(),
                film.getReplacementCost(),
                film.getRating(),
                film.getSpecialFeatures(),
                film.getLastUpdate(),
                film.getActors(),
                film.getCategories()
        );
    }
    public Film toEntity(){
        Film film = new Film();
        film.setFilmId(this.getFilmId());
        film.setTitle(this.getTitle());
        film.setDescription(this.getDescription());
        film.setReleaseYear(this.getReleaseYear());
        film.setLanguage(this.getLanguage().toEntity());
        film.setOriginalLanguage(this.getOriginalLanguage().toEntity());
        film.setRentalDuration(this.getRentalDuration());
        film.setRentalRate(this.getRentalRate());
        film.setLength(this.getLength());
        film.setReplacementCost(this.getReplacementCost());
        film.setRating(this.getRating());
        film.setSpecialFeatures(this.getSpecialFeatures());
        film.setLastUpdate(this.getLastUpdate());
        film.setActors(this.getActors());
        film.setCategories(this.getCategories());
        return film;
    }
    
    public Integer getFilmId() {
        return filmId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public LanguageDTO getLanguage() {
        return language;
    }

    public LanguageDTO getOriginalLanguage() {
        return originalLanguage;
    }

    public Integer getRentalDuration() {
        return rentalDuration;
    }

    public BigDecimal getRentalRate() {
        return rentalRate;
    }

    public Integer getLength() {
        return length;
    }

    public BigDecimal getReplacementCost() {
        return replacementCost;
    }

    public Rating getRating() {
        return rating;
    }

    public String getSpecialFeatures() {
        return specialFeatures;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public List<Category> getCategories() {
        return categories;
    }
}