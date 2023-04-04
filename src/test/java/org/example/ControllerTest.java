package org.example;

import org.example.dao.*;
import org.example.entities.Film;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class ControllerTest {
    private Controller controller;

    @BeforeEach
    void setUp() {
        controller = new Controller(new ActorDAO(), new AddressDAO(), new CityDAO(), new CustomerDAO(), new FilmDAO(), new InventoryDAO(), new PaymentDAO(), new RentalDAO(), new StaffDAO(), new StoreDAO());

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

}