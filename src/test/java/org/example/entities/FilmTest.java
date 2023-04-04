package org.example.entities;

import com.vividsolutions.jts.util.Assert;
import org.example.dao.FilmDAO;
import org.example.dao.InventoryDAO;
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
        Film film = filmDAO.read(525);

        List<Inventory> inventories = new ArrayList<>(film.getInventories());

        for(int i = 0; i<inventories.size(); i++){
            System.out.println(inventories.get(i).getRental());
        }



//        while(interator.hasNext()){
//            System.out.println(interator.get);
//        }
//        Assert.equals(film.getInventories().getRental());
    }
}