package org.example.dao;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import org.example.entities.*;
import org.example.entities.Film;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
public class InventoryDAOTest {

    private InventoryDAO inventoryDAO;

    @BeforeEach
    void setup(){
        inventoryDAO = new InventoryDAO();
    }

    @Test
    void testCreate(){

    }

    @Test
    void testRead(){
    Inventory inventory = inventoryDAO.read(1);
        System.out.println(inventory.getFilm());

    }

    @Test
    void testGetALl(){
        List<Inventory> all = inventoryDAO.getAll();
        List<Film> printedFilms = new ArrayList<>(); // To keep track of which films have already been printed
        for (Inventory inventory : all) {
            Film film = inventory.getFilm();
            if (!printedFilms.contains(film)) { // Only print the film if it hasn't been printed already
                System.out.println(film);
                printedFilms.add(film);
            }
        }
    }

    @Test
    void testUpdate() {

    }

    @Test
    void testDelete() {

    }

}
