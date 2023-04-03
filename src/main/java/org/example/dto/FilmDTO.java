package org.example.dto;

import org.example.dao.FilmDAO;
import org.example.entities.Actor;
import org.example.entities.Category;
import org.example.entities.Language;
import org.example.enums.Rating;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;


public class FilmDTO {

    private int filmId;

    private String title;

    private String description;

    private int releaseYear;

    private Language language;

    private Language originalLanguage;

    private int rentalDuration;

    private BigDecimal rentalRate;

    private int length;

    private BigDecimal replacementCost;

    private Rating rating;

    private String specialFeatures;

    private Timestamp lastUpdate;

    private List<Actor> actors;

    private List<Category> categories;

    public FilmDTO(int filmId, String title, String description, int releaseYear, Language language, Language originalLanguage, int rentalDuration, BigDecimal rentalRate, int length, BigDecimal replacementCost, Rating rating, String specialFeatures, Timestamp lastUpdate, List<Actor> actors, List<Category> categories) {
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

    public int getFilmId() {
        return filmId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public Language getLanguage() {
        return language;
    }

    public Language getOriginalLanguage() {
        return originalLanguage;
    }

    public int getRentalDuration() {
        return rentalDuration;
    }

    public BigDecimal getRentalRate() {
        return rentalRate;
    }

    public int getLength() {
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