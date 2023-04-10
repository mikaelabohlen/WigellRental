package org.example.dao;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import org.example.entities.*;
import org.example.entities.Language;
import org.example.enums.Rating;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sun.reflect.LangReflectAccess;


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
public class FilmDAOTest {

    private FilmDAO filmDAO;
    private LanguageDAO languageDAO;

    private CategoryDAO categoryDAO;

    @BeforeEach
    void setup(){
        filmDAO = new FilmDAO();
        languageDAO = new LanguageDAO();
        categoryDAO = new CategoryDAO();
    }

    @Test
    void testCreate(){
        //langauge
        Language language = new Language();
        language.setName("English");
        System.out.println(language.getName());
        language.setLastUpdate(new Timestamp(System.currentTimeMillis()));
        languageDAO.create(language);
        //actors
        Actor actor1 = new Actor();
        actor1.setFirstName("Luca");
        actor1.setLastName("Kadir");
        actor1.setLastUpdate(new Timestamp(System.currentTimeMillis()));
        Actor actor2 = new Actor();
        actor2.setFirstName("Fai");
        actor2.setLastName("Zhou");
        actor2.setLastUpdate(new Timestamp(System.currentTimeMillis()));
        List <Actor> actors = new ArrayList<>();
        actors.add(actor1);
        actors.add(actor2);
        //category
        Category category = new Category();
        category.setName("Action");
        category.setLastUpdate(new Timestamp(System.currentTimeMillis()));
        List <Category> categories = new ArrayList<>();
        categories.add(category);
        categoryDAO.create(category);

//    Film film = new Film();
//    film.setTitle("Fool's Journey");
//    film.setReleaseYear(2023);
//    film.setLanguage(language);
//    film.setLength(140);
//    film.setRating(Rating.PG13);
//    film.setSpecialFeatures("Trailers");
//    film.setActors(actors);
//    film.setCategories(categories);
//    film.setRentalRate(new BigDecimal("9.99"));
//    film.setReplacementCost(new BigDecimal("19.99"));
//    film.setDescription("A young man sets out to right the world, a task only a Fool would be foolish enough to attempt.");
//    film.setLastUpdate(new Timestamp(System.currentTimeMillis()));
//        System.out.println(film);
//        filmDAO.create(film);
    }

    @Test
    void testRead(){
        Film film = filmDAO.read(1);
        System.out.println(film.getTitle());
    }

    @Test
    void testGetALl(){
        List<Film> all = filmDAO.getAll();
        for (Film film : all) {
            System.out.println(film.getTitle());
        }
    }


    @Test
    void testUpdate() {


    }


    @Test
    void testDelete() {


    }

}

