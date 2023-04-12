package org.example.entities;

import com.vividsolutions.jts.util.Assert;
import org.example.dao.FilmDAO;
import org.example.dao.InventoryDAO;
import org.example.dao.LanguageDAO;
import org.junit.jupiter.api.Test;

import javax.persistence.criteria.CriteriaBuilder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FilmTest {

    @Test
    void getInventories() {
        FilmDAO filmDAO = new FilmDAO();
        Film film = filmDAO.read(1);
        Assert.equals(8, film.getInventories().size());

        FilmDAO filmDAO2 = new FilmDAO();
        Film film2 = filmDAO.read(2);
        Assert.equals(3, film2.getInventories().size());

    }
    @Test
    void getRentals(){
        FilmDAO filmDAO = new FilmDAO();
        Film film = filmDAO.read(698);

        List<Inventory> inventories = new ArrayList<>(film.getInventories());

        for (Inventory inventory : inventories) {
            System.out.println(inventory.getRentals());
        }

    }

    @Test
    void deleteFilm(){
        FilmDAO filmDAO = new FilmDAO();
        Film film = filmDAO.read(1000);
        //film.deleteAssociations();
        filmDAO.delete(film.getFilmId());

    }
/*    @Test
    void createNewFilm () {
        LanguageDAO languageDAO = new LanguageDAO();
        Film film = new Film();
        film.setTitle("Solen");
        film.setDescription("Bla bla bla...");
        film.setReleaseYear(2012);
        film.setLanguage(languageDAO.read);
    }*/
}