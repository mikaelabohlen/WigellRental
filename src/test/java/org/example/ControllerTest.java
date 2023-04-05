package org.example;

import org.example.dao.*;
import org.example.entities.Actor;
import org.example.entities.Film;
import org.example.entities.Inventory;
import org.example.entities.Store;
import org.example.enums.Rating;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

class ControllerTest {
    private Controller controller;

    @BeforeEach
    void setUp() {
        controller = new Controller(new ActorDAO(), new AddressDAO(), new CityDAO(), new CustomerDAO(), new FilmDAO(), new InventoryDAO(), new LanguageDAO(), new PaymentDAO(), new RentalDAO(), new StaffDAO(), new StoreDAO());

    }

    @Test
    void doesFilmGetRented() {
        Film film = controller.getFilmDAO().read(3);

//        System.out.println(film.getTotalStock(1));
//        System.out.println(film.getInStock(1));

        List<Film> filmsToRent = new ArrayList<>();
        filmsToRent.add(film);
        controller.rentFilms(filmsToRent, 1,1);

//        System.out.println(film.getTotalStock(1));
//        System.out.println(film.getInStock(1));
    }
    @Test
    void createNewFilm () {
        Film film = new Film();
        film.setTitle("Solen");
        film.setDescription("Bla bla bla...");
        film.setReleaseYear(2012);
        film.setLanguage(controller.getLanguageDAO().read(1));
        film.setOriginalLanguage(controller.getLanguageDAO().read(1));
        film.setRentalDuration(3);
        film.setRentalRate(new BigDecimal(3.44));
        film.setLength(34);
        film.setReplacementCost(new BigDecimal(3.44));
        film.setRating(Rating.NC17);
        film.setSpecialFeatures("Trailers");
        film.setLastUpdate(new Timestamp(System.currentTimeMillis()));

        controller.getFilmDAO().create(film);
    }

    @Test
    void connectionActorFilm() {
        Film film = controller.getFilmDAO().read(1001);
        film.setRentalDuration(3);
        film.setRentalRate(new BigDecimal(4.99));
        controller.getFilmDAO().update(film);

/*        Store store = controller.getStoreDAO().read(1);
        Inventory inventory = new Inventory();
        inventory.setFilm(film);
        inventory.setStore(store);
        inventory.setLastUpdate(new Timestamp(System.currentTimeMillis()));
        controller.getInventoryDAO().create(inventory);*/

        Actor actor1 = controller.getActorDAO().read(1);
        Actor actor2 = controller.getActorDAO().read(5);
        Actor actor3 = controller.getActorDAO().read(3);

        actor1.addOneMovie(film);
        actor2.addOneMovie(film);
        actor3.addOneMovie(film);

        controller.getActorDAO().update(actor1);
        controller.getActorDAO().update(actor2);
        controller.getActorDAO().update(actor3);
        /*List<Actor> actorList = new ArrayList<>();
        actorList.add(actor1);
        actorList.add(actor2);
        actorList.add(actor3);
        film.setActors(actorList);
        controller.getFilmDAO().update(film);*/



/*        Film film = controller.getFilmDAO().read(1);
        Actor actor = controller.getActorDAO().read(2);
        actor.addOneMovie(film);
        controller.getActorDAO().update(actor);
        controller.getFilmDAO().update(film);*/
    }
    @Test
    void createInventory(Film film, Store store) {
        Inventory inventory = new Inventory();
        inventory.setFilm(film);
        inventory.setStore(store);
        controller.getInventoryDAO().create(inventory);
    }

}