package org.example.dao;

import org.example.entities.Film;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class FilmDAO extends AbstractDAO<Film>{
    public FilmDAO() {super(Film.class);}
}
