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
        Film film = filmDTO.toEntity();
        filmDAO.create(film);
        return FilmDTO.fromEntity(film);
    }
    public FilmDTO updateFilm(FilmDTO filmDTO){  //filmDTOToFilm
        Film film = filmDTO.toEntity();
        filmDAO.update(film);
        return FilmDTO.fromEntity(film);
    }

}
