package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.dao.*;

import org.example.entities.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private ActorDAO actorDAO;
    private AddressDAO addressDAO;
    private CityDAO cityDAO;
    private CustomerDAO customerDAO;
    private CategoryDAO categoryDAO;
    private FilmDAO filmDAO;
    private InventoryDAO inventoryDAO;
    private LanguageDAO languageDAO;
    private PaymentDAO paymentDAO;
    private RentalDAO rentalDAO;
    private StaffDAO staffDAO;
    private StoreDAO storeDAO;

    private Store activeStore;

    private List<Film> filmsInStore1;
    private List<Film> filmsInStore2;

    private ObservableList<Film> filmObservableList;
    private ObservableList<Language> languageObservableList;
    private ObservableList<Category> categoryObservableList;
    private ObservableList<Customer> customerObservableList;

    public Controller(ActorDAO actorDAO, AddressDAO addressDAO, CategoryDAO categoryDAO, CityDAO cityDAO, CustomerDAO customerDAO, FilmDAO filmDAO, InventoryDAO inventoryDAO, LanguageDAO languageDAO, PaymentDAO paymentDAO, RentalDAO rentalDAO, StaffDAO staffDAO, StoreDAO storeDAO) {
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
        this.categoryDAO = categoryDAO;


        filmObservableList = FXCollections.observableList(filmDAO.getAll());
        languageObservableList = FXCollections.observableList(languageDAO.getAll());
        categoryObservableList = FXCollections.observableList(categoryDAO.getAll());
        customerObservableList = FXCollections.observableList(customerDAO.getAll());



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

    public CategoryDAO getCategoryDAO() {
        return categoryDAO;
    }

    public void setCategoryDAO(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
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

    public List<Film> getAllFilms() {
        return filmDAO.getAll();
    }

    public ObservableList<Film> getFilmObservableList() {
        return filmObservableList;
    }

    public ObservableList<Language> getLanguageObservableList() {
        return languageObservableList;
    }

    public ObservableList<Category> getCategoryObservableList() {
        return categoryObservableList;
    }

    public ObservableList<Customer> getCustomerObservableList() {
        return customerObservableList;
    }

    public List<Film> getFilmsInStore1() {
        if(filmsInStore1==null) {
            filmsInStore1 = filmDAO.getAllFilmsByStore(storeDAO.read(1));
        }
        return filmsInStore1;
    }

    public List<Film> getFilmsInStore2() {
        if(filmsInStore2==null){
            filmsInStore2 = filmDAO.getAllFilmsByStore(storeDAO.read(2));
        }
        return filmsInStore2;
    }

    /*    public Inventory canFilmBeRented(Film selectedFilm, int storeId) {
        List<Inventory> inventories = inventoryDAO.getInventoriesForFilm(selectedFilm.getFilmId());
        for (Inventory inventoryItem : inventories) {
            System.out.println("InventoryId: "+inventoryItem.getInventoryId()+ "\n\n\n\n\n\n\n");
            if(storeId == inventoryItem.getStore().getStoreId()) {
                List<Rental> rentalsForInventory = rentalDAO.getRentalsForInventory(inventoryItem.getInventoryId());
                System.out.println(rentalsForInventory.size());
                if(rentalsForInventory.size() == 0){
                    return inventoryItem;
                }
                boolean currentlyRented = false;
                for (Rental rental : rentalsForInventory) {
                    System.out.println("Rental returnDate: "+rental.getReturnDate()+ "\n\n\n\n\n");
                    if(rental.getReturnDate() == null){
                        System.out.println("Denna är uthyrd");
                        currentlyRented = true;
                        break;
                    }
                }
                if(!currentlyRented) {
                    return inventoryItem;
                }
            }
        }
        System.out.println("No item available");
        return null;
    }

    //                List<Timestamp> timestamps = (List<Timestamp>) rental.getReturnDate();
//                for(Timestamp timestamp : timestamps){
//                    if(!(timestamp == null)&&storeId == inventoryItem.getStore().getStoreId()){
//                        currentlyRented = false;
//                        inventory = inventoryItem;
//                        break;
//                    }
//                }
    public void createRental(int inventoryId, int storeId, int customerId) {
        Rental rental = new Rental();
        rental.setRentalDate(LocalDateTime.now());
        rental.setInventory(inventoryDAO.read(inventoryId));
        rental.setCustomer(customerDAO.read(customerId));
        rental.setStaff(storeDAO.read(storeId).getManagerStaff());
        rental.setLastUpdate(new Timestamp(System.currentTimeMillis()));
        rentalDAO.create(rental); //kolla upp cascade... uppdateras alla som påverkas?
    }

    private Inventory createInventory(int filmId, int storeId) {
        Inventory inventory = new Inventory();
        inventory.setFilm(filmDAO.read(filmId));
        inventory.setStore(storeDAO.read(storeId));
        return inventory;
    }

    public void createNewFilm(Film film, List<Actor> actors) {
        filmDAO.create(film);
        connectActorWithFilm(film, actors);
    }

    void connectActorWithFilm(Film film, List<Actor> actors) {
        for (Actor actor : actors) {
            actor.addOneMovie(film);
            actorDAO.update(actor);
        }
    }*/

    public List<Actor> getActors(Film selectedFilm) {
        return actorDAO.getActorsForFilm(selectedFilm.getFilmId());
    }



    public void updateCustomer(Customer customer) {
        getCityDAO().update(customer.getAddress().city());
        getAddressDAO().update(customer.getAddress());
        getCustomerDAO().update(customer);
    }

    public void setActiveStore(String store) {
        if(store.equals("1")) {
            activeStore = storeDAO.read(1);
            System.out.println(activeStore.getStoreId());
        }
        if(store.equals("2")) {
            activeStore = storeDAO.read(2);
            System.out.println(activeStore.getStoreId());
        }
    }

    public Store getActiveStore() {
        return activeStore;
    }

    public void createNewCustomer(Customer customer) {
        //TODO inte klart
        getCityDAO().create(customer.getAddress().city());
        getAddressDAO().create(customer.getAddress());
        customer.setStore(activeStore);
        getCustomerDAO().create(customer);
    }

    public void deleteSelectedFilm(Film selectedItem) {
        //TODO funkar inte pga fk constraints
        filmDAO.delete(selectedItem.getFilmId());
    }

    public void updateSelectedFilm(Film selectedItem) {
        getFilmDAO().update(selectedItem);
    }

    public void addFilmToDatabase(Film film) {

        filmDAO.create(film);
    }

    public void updateFilmList() {
        filmObservableList = FXCollections.observableList(filmDAO.getAll());
    }

    public void updateCustomerList() {
        customerObservableList = FXCollections.observableList(customerDAO.getAll());
    }

    public void rentFilm(String customerId, Film selectedFilm) {
        //TODO denna är antagligen felande det läggs in i tabellen men kommer inte fram med getRentalsMetoden sen

        LocalDate date = LocalDate.now();

        Rental rental = new Rental();
        rental.setRentalDate(date.atStartOfDay());
        rental.setLastUpdate(new Timestamp(System.currentTimeMillis()));
        rental.setStaff(getStaffDAO().read(getActiveStore().getStoreId()));

        Customer customer = getCustomerDAO().read(Integer.parseInt(customerId));
        rental.setCustomer(customer);


        Inventory inventory = selectedFilm.getInventories().iterator().next();
        rental.setInventory(inventory);
        rentalDAO.create(rental);

    }

    public List<Film> getRentals(int customerId) {
        return filmDAO.getAllRentedFilmsForCustomer(customerId);
    }

//    public void rentFilm() {
//    }

}
