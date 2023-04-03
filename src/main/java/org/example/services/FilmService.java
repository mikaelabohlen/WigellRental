package org.example.services;

import org.example.dao.FilmDAO;
import org.example.dto.FilmDTO;
import org.example.dto.LanguageDTO;
import org.example.entities.Film;
import org.example.entities.Language;

import java.util.ArrayList;
import java.util.List;

public class FilmService {
    FilmDAO filmDAO;

    public FilmService(FilmDAO filmDAO){
        this.filmDAO = filmDAO;
    }

//    public FilmDTO getFilm(){ //filmToDTO fiskar aldrig ut en enskild?
//        Film film = filmDAO.read()
//        return new FilmDTO(film.getFilmId(), film.getTitle(), film.getDescription(), film.getReleaseYear(), film.getLanguage(), film.getOriginalLanguage(), film.getRentalDuration(), film.getRentalRate(),
//                film.getLength(), film.getReplacementCost(), film.getRating(), film.getSpecialFeatures(), film.getLastUpdate(), film.getActors(), film.getCategories());
//    }

    public List<FilmDTO> getAllFilms(){ //filmsToDTOs
        List<Film> films = filmDAO.getAll();
        List<FilmDTO> filmDTOs = new ArrayList<>();
        for(Film film : films){
            FilmDTO filmDTO = FilmDTO.fromEntity(film);
            filmDTOs.add(filmDTO);
        }
        return filmDTOs;
    }

    public FilmDTO addFilm(FilmDTO filmDTO){  //filmDTOToFilm
        Film film = new Film();
        transferAttribute(filmDTO, film);
        filmDAO.create(film);
        return FilmDTO.fromEntity(film);
    }
    public void updateFilm(FilmDTO filmDTO){  //filmDTOToFilm
        Film film = filmDAO.read(filmDTO.getFilmId());
        transferAttribute(filmDTO, film);
        filmDAO.create(film);
        System.out.println(film.getFilmId());
    }

    private void transferAttribute(FilmDTO filmDTO, Film film) {
        film.setTitle(filmDTO.getTitle());
        film.setDescription(filmDTO.getDescription());
        film.setReleaseYear(filmDTO.getReleaseYear());
        film.setLanguage(getLanguage(filmDTO.getLanguage()));
        film.setOriginalLanguage(getLanguage(filmDTO.getOriginalLanguage()));
        film.setRentalDuration(filmDTO.getRentalDuration());
        film.setRentalRate(filmDTO.getRentalRate());
        film.setLength(filmDTO.getLength());
        film.setReplacementCost(filmDTO.getReplacementCost());
        film.setRating(filmDTO.getRating());
        film.setSpecialFeatures(filmDTO.getSpecialFeatures());
        film.setLastUpdate(filmDTO.getLastUpdate());
        film.setActors(filmDTO.getActors());
        film.setCategories(filmDTO.getCategories());
    }

    public Language getLanguage(LanguageDTO languageDTO){
        Language language = new Language();
        language.setLanguageId(languageDTO.getLanguageId());
        language.setName(languageDTO.getName());
        language.setLastUpdate(languageDTO.getLastUpdate());
        return language;
    }

}
