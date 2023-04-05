package org.example.dao;

import org.example.entities.Actor;
import org.example.entities.Film;
import org.hibernate.Session;

import java.util.List;

public class FilmDAO extends AbstractDAO<Film>{
    public FilmDAO() {super(Film.class);}
}
