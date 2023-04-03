package org.example.entities;

import org.example.enums.Rating;
import org.example.utils.RatingConverter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;


@Entity
public class Film {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "film_id")
    private Integer filmId;

    @Column(length = 128, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT default NULL") //Denna är datatype TEXT i databasen?
    private String description;

    @Column(name = "release_year", columnDefinition = "default NULL")
    private int releaseYear;

    @OneToOne
    @JoinColumn(name = "language_id", nullable = true) //Todo: nullable ska vara false, testar bara nu
    private Language language;

    @OneToOne
    @JoinColumn(name = "original_language_id", columnDefinition = "default NULL")
    private Language originalLanguage;

    @Column(name = "rental_duration", nullable = false, columnDefinition = "default 3")
    private int rentalDuration;

    @Column(name = "rental_rate", nullable = false, precision = 4, scale = 2, columnDefinition = "default 4.99")
    private BigDecimal rentalRate;

    @Column(columnDefinition = "default NULL")
    private int length;

    @Column(name = "replacement_cost", nullable = false, precision = 5, scale = 2, columnDefinition = "default 19.99")
    private BigDecimal replacementCost;

    @Convert(converter = RatingConverter.class)
    @Column(columnDefinition = "default 'G'")
    private Rating rating;

    @Column(name = "special_features", columnDefinition = "SET('Trailers','Commentaries','Deleted Scenes','Behind the Scenes') DEFAULT NULL")
    private String specialFeatures;

    @Column(name = "last_update", columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Timestamp lastUpdate;

    @ManyToMany(mappedBy = "films", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Actor> actors;

    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinTable(name = "film_category",
            joinColumns = {@JoinColumn (name = "film_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id")})
    private List<Category> categories;

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(Integer filmId) {
        this.filmId = filmId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Language getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(Language originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public int getRentalDuration() {
        return rentalDuration;
    }

    public void setRentalDuration(int rentalDuration) {
        this.rentalDuration = rentalDuration;
    }

    public BigDecimal getRentalRate() {
        return rentalRate;
    }

    public void setRentalRate(BigDecimal rentalRate) {
        this.rentalRate = rentalRate;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public BigDecimal getReplacementCost() {
        return replacementCost;
    }

    public void setReplacementCost(BigDecimal replacementCost) {
        this.replacementCost = replacementCost;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public String getSpecialFeatures() {
        return specialFeatures;
    }

    public void setSpecialFeatures(String specialFeatures) {
        this.specialFeatures = specialFeatures;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
