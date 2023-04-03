package org.example.services;

import org.example.dao.FilmDAO;
import org.example.dto.FilmDTO;
import org.example.dto.LanguageDTO;
import org.example.entities.Language;
import org.example.enums.Rating;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

class FilmServiceTest {
    @Test
    void addFilm() {
        FilmService filmService = new FilmService(new FilmDAO());
        FilmDTO filmDTO = new FilmDTO(
                null,
                "Min testfilm",
                null,
                1,
                new LanguageDTO(1, null,new Timestamp(System.currentTimeMillis())),
                new LanguageDTO(1,null, new Timestamp(System.currentTimeMillis())),
                1, BigDecimal.valueOf(1.0),
                1, BigDecimal.valueOf(1.0),
                Rating.G,
                null,new Timestamp(System.currentTimeMillis()),
                null,
                null);
        FilmDTO filmDTOAdded = filmService.addFilm(filmDTO);
        System.out.println(filmDTOAdded.getFilmId());
    }
}