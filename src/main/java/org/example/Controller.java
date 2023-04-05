package org.example;

import org.example.dao.*;
import org.example.entities.Actor;
import org.example.entities.Film;
import org.example.entities.Inventory;
import org.example.entities.Rental;
import org.example.enums.Rating;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private ActorDAO actorDAO;
    private AddressDAO addressDAO;
    private CityDAO cityDAO;
    private CustomerDAO customerDAO;
    private FilmDAO filmDAO;
    private InventoryDAO inventoryDAO;
    private LanguageDAO languageDAO;
    private PaymentDAO paymentDAO;
    private RentalDAO rentalDAO;
    private StaffDAO staffDAO;
    private StoreDAO storeDAO;

    public Controller(ActorDAO actorDAO, AddressDAO addressDAO, CityDAO cityDAO, CustomerDAO customerDAO, FilmDAO filmDAO, InventoryDAO inventoryDAO, LanguageDAO languageDAO, PaymentDAO paymentDAO, RentalDAO rentalDAO, StaffDAO staffDAO, StoreDAO storeDAO) {
        this.actorDAO = actorDAO;
        this.addressDAO = addressDAO;
        this.cityDAO = cityDAO;
        this.customerDAO = customerDAO;
        this.filmDAO = filmDAO;
        this.inventoryDAO = inventoryDAO;
        this.languageDAO = languageDAO;
        this.paymentDAO = paymentDAO;
        this.rentalDAO = rentalDAO;
        this.staffDAO = staffDAO;
        this.storeDAO = storeDAO;
    }

    public ActorDAO getActorDAO() {
        return actorDAO;
    }

    public void setActorDAO(ActorDAO actorDAO) {
        this.actorDAO = actorDAO;
    }

    public AddressDAO getAddressDAO() {
        return addressDAO;
    }

    public void setAddressDAO(AddressDAO addressDAO) {
        this.addressDAO = addressDAO;
    }

    public CityDAO getCityDAO() {
        return cityDAO;
    }

    public void setCityDAO(CityDAO cityDAO) {
        this.cityDAO = cityDAO;
    }

    public CustomerDAO getCustomerDAO() {
        return customerDAO;
    }

    public void setCustomerDAO(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    public FilmDAO getFilmDAO() {
        return filmDAO;
    }

    public void setFilmDAO(FilmDAO filmDAO) {
        this.filmDAO = filmDAO;
    }

    public InventoryDAO getInventoryDAO() {
        return inventoryDAO;
    }

    public void setInventoryDAO(InventoryDAO inventoryDAO) {
        this.inventoryDAO = inventoryDAO;
    }

    public LanguageDAO getLanguageDAO() {
        return languageDAO;
    }

    public void setLanguageDAO(LanguageDAO languageDAO) {
        this.languageDAO = languageDAO;
    }

    public PaymentDAO getPaymentDAO() {
        return paymentDAO;
    }

    public void setPaymentDAO(PaymentDAO paymentDAO) {
        this.paymentDAO = paymentDAO;
    }

    public RentalDAO getRentalDAO() {
        return rentalDAO;
    }

    public void setRentalDAO(RentalDAO rentalDAO) {
        this.rentalDAO = rentalDAO;
    }

    public StaffDAO getStaffDAO() {
        return staffDAO;
    }

    public void setStaffDAO(StaffDAO staffDAO) {
        this.staffDAO = staffDAO;
    }

    public StoreDAO getStoreDAO() {
        return storeDAO;
    }

    public void setStoreDAO(StoreDAO storeDAO) {
        this.storeDAO = storeDAO;
    }

    public List<Film> getAllFilms(){
        return filmDAO.getAll();
    }

    public void rentFilms(List<Film> filmsToRent, int storeId, int customerId){
        for(Film film: filmsToRent){
            List<Inventory> inventories = new ArrayList<>(film.getInventories());
            Inventory inventory = null;

            for(Inventory inventoryItem: inventories){
                boolean rented = inventoryItem.getRental().getReturnDate() == null;
                if(storeId == inventoryItem.getStore().getStoreId() && !rented){
                    inventory = inventoryItem;
                    break;
                }
            }

            Rental rental = new Rental();
            rental.setRentalDate(LocalDateTime.now());
            rental.setInventory(inventory);
            rental.setCustomer(customerDAO.read(customerId));
            rental.setStaff(storeDAO.read(storeId).getManagerStaff());
            rental.setLastUpdate(new Timestamp(System.currentTimeMillis()));

            rentalDAO.create(rental); //kolla upp cascade... uppdateras alla som påverkas?

        }
    }
    public void createNewFilm (Film film) {
/*        Film film = new Film();
        film.setTitle("Solen");
        film.setDescription("Bla bla bla...");
        film.setReleaseYear(2012);
        film.setLanguage(languageDAO.read(1));
        film.setOriginalLanguage(languageDAO.read(1));
        film.setRentalDuration(3);
        film.setRentalRate(new BigDecimal(3.44));
        film.setLength(34);
        film.setReplacementCost(new BigDecimal(3.44));
        film.setRating(Rating.NC17);
        film.setSpecialFeatures("Trailers");
        film.setLastUpdate(new Timestamp(System.currentTimeMillis()));*/

        filmDAO.create(film);
    }

    void connectionActorFilm(Film film, List<Actor> actorList) {
/*        Film film = filmDAO.read(1004);
        film.setRentalDuration(3);
        film.setRentalRate(new BigDecimal(4.99));
        filmDAO.update(film);*/

/*        Store store = controller.getStoreDAO().read(1);
        Inventory inventory = new Inventory();
        inventory.setFilm(film);
        inventory.setStore(store);
        inventory.setLastUpdate(new Timestamp(System.currentTimeMillis()));
        controller.getInventoryDAO().create(inventory);*/

        Actor actor1 = actorDAO.read(1);
        Actor actor2 = actorDAO.read(5);
        Actor actor3 = actorDAO.read(3);

        actor1.addOneMovie(film);
        actor2.addOneMovie(film);
        actor3.addOneMovie(film);

        actorDAO.update(actor1);
        actorDAO.update(actor2);
        actorDAO.update(actor3);
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

}
