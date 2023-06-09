package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.dao.*;

import org.example.entities.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

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
    private List<Inventory> films;

    private ObservableList<Film> filmObservableList;
    private ObservableList<Language> languageObservableList;
    private ObservableList<Category> categoryObservableList;
    private ObservableList<Customer> customerObservableList;
    private ObservableList<Staff> staffObservableList;
    private ObservableList<Inventory> inventoryObservableList;
    private ObservableList<Store> storeObservableList;



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
        staffObservableList = FXCollections.observableList(staffDAO.getAll());
        films = new ArrayList<>();
        inventoryObservableList = FXCollections.observableList(films);
        storeObservableList = FXCollections.observableList(storeDAO.getAll());
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

    public ObservableList<Staff> getStaffObservableList() {
        return staffObservableList;
    }

    public ObservableList<Inventory> getInventoryObservableList() {
        return inventoryObservableList;
    }

    public ObservableList<Store> getStoreObservableList() {
        return storeObservableList;
    }

    public List<Inventory> getFilms() {
        return films;
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

    public void setActiveStore(String store) {
        if(store.equals("1")) {
            activeStore = storeDAO.read(1);
            getFilmsInStore1();
            filmObservableList = FXCollections.observableList(filmsInStore1);
            System.out.println(activeStore.getStoreId());
        }
        if(store.equals("2")) {
            activeStore = storeDAO.read(2);
            getFilmsInStore2();
            filmObservableList = FXCollections.observableList(filmsInStore2);
            System.out.println(activeStore.getStoreId());
        }
    }

    public Store getActiveStore() {
        return activeStore;
    }

    public boolean canFilmBeRented(Film selectedFilm, int storeId) {

        List<Inventory> inventories = inventoryDAO.getInventoriesForFilm(selectedFilm.getFilmId());
        for (Inventory inventoryItem : inventories) {
                if (inventoryObservableList.stream().noneMatch(inventory -> Objects.equals(inventory.getInventoryId(), inventoryItem.getInventoryId()))) {
                System.out.println("InventoryId: " + inventoryItem.getInventoryId() + "\n\n\n\n\n\n\n");
                if (storeId == inventoryItem.getStore().getStoreId()) {
                    List<Rental> rentalsForInventory = rentalDAO.getRentalsForInventory(inventoryItem.getInventoryId());
                    System.out.println(rentalsForInventory.size());
                    if (rentalsForInventory.size() == 0) {
                        films.add(inventoryItem);
                        inventoryObservableList = FXCollections.observableList(films);
                        return true;
                    }
                    boolean currentlyRented = false;
                    for (Rental rental : rentalsForInventory) {
                        System.out.println("Rental returnDate: " + rental.getReturnDate() + "\n\n\n\n\n");
                        if (rental.getReturnDate() == null) {
                            System.out.println("Denna är uthyrd");
                            currentlyRented = true;
                            break;
                        }
                    }
                    if (!currentlyRented) {
                        films.add(inventoryItem);
                        inventoryObservableList = FXCollections.observableList(films);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void createNewFilm(Film film, List<Actor> actors, int numOfFilm) {
        Film createdFilm = filmDAO.create(film);
        connectActorWithFilm(film, actors);
        createdFilm.setInventories(createInventory(createdFilm.getFilmId(), numOfFilm));
    }

    private Set<Inventory> createInventory(int filmId, int numOfFilms) {
        Set<Inventory> inventories = new HashSet<>();

        for(int i = 0; i<numOfFilms; i++){
            Inventory inventory = new Inventory();
            inventory.setFilm(filmDAO.read(filmId));
            inventory.setStore(activeStore);
            inventory.setLastUpdate(new Timestamp(System.currentTimeMillis()));
            Inventory createdInventory = inventoryDAO.create(inventory);
            inventories.add(createdInventory);
        }
        return inventories;
    }

    void connectActorWithFilm(Film film, List<Actor> actors) {
        for (Actor actor : actors) {
            if (film.getActors() == null || film.getActors().stream().filter(actor1 -> actor1.getId() == actor.getId()).count() == 0) {
                actor.addOneMovie(film);
                actorDAO.update(actor);
            }
        }
    }

    public List<Actor> getActors(Film selectedFilm) {
        return actorDAO.getActorsForFilm(selectedFilm.getFilmId());
    }

    public void updateCustomer(Customer customer) {
        getCityDAO().update(customer.getAddress().getCity());
        getAddressDAO().update(customer.getAddress());
        getCustomerDAO().update(customer);
    }

    public void createNewCustomer(Customer customer) {
        getCityDAO().create(customer.getAddress().getCity());
        getAddressDAO().create(customer.getAddress());
        customer.setStore(activeStore);
        getCustomerDAO().create(customer);
    }

    public void updateCustomerList() {
        customerObservableList = FXCollections.observableList(customerDAO.getAll());
    }

    public void deleteSelectedFilm(Film selectedFilm) {
        deleteFilmFromStore(selectedFilm);
    }

    public void deleteFilmFromStore(Film selectedFilm){
        List<Inventory> inventories = inventoryDAO.getInventoriesForFilm(selectedFilm.getFilmId());
        for(Inventory inventory : inventories){
            if(inventory.getStore().getStoreId() == activeStore.getStoreId()) {
                List<Rental> rentals = rentalDAO.getRentalsForInventory(inventory.getInventoryId());
                for(Rental rental : rentals){
                    rentalDAO.delete(rental.getRentalId());
                }
                inventoryDAO.delete(inventory.getInventoryId());
            }
        }
    }

    public void updateSelectedFilm(Film selectedItem, List<Actor> actors) {
        connectActorWithFilm(selectedItem, actors);
        getFilmDAO().update(selectedItem);
    }

    public void updateFilmList() {
        if(activeStore.getStoreId()==1) {
            activeStore = storeDAO.read(1);
            System.out.println("hej");
            getFilmsInStore1();
            filmObservableList = FXCollections.observableList(filmsInStore1);
            System.out.println(activeStore.getStoreId());
        }
        if(activeStore.getStoreId()==2) {
            activeStore = storeDAO.read(2);
            getFilmsInStore2();
            filmObservableList = FXCollections.observableList(filmsInStore2);
            System.out.println(activeStore.getStoreId());
        }
    }

    public ObservableList<Rental> getRentals(int customerId) {
        ObservableList<Rental> customerRentals;
        customerRentals = FXCollections.observableList(rentalDAO.getRentalsForCustomer(customerId));
       return customerRentals;

    }

    public void returnFilm(Rental selectedRental) {
        selectedRental.setReturnDate(LocalDateTime.now());
        rentalDAO.update(selectedRental);
    }

    public void createActor(Actor actor) {
        actorDAO.create(actor);
    }

    public Actor getOrCreateActor(String name) {
        Actor actor = actorDAO.findActorByName(name);
        if(actor == null){
            String[] names = name.split(" ");
            String firstName = names[0];
            String lastName = names[1];
            Actor newActor = new Actor();
            newActor.setFirstName(firstName);
            newActor.setLastName(lastName);
            newActor.setLastUpdate(new Timestamp(System.currentTimeMillis()));
            createActor(newActor);
            return actorDAO.read(newActor.getId());
        }else{
            return actor;
        }
    }

    public void deleteSelectedCustomer(Customer customer) {
        paymentDAO.deletePaymentsByCustomerId(customer.getCustomerId());
        rentalDAO.deleteRentalsByCustomerId(customer.getCustomerId());
        customerDAO.delete(customer.getCustomerId());
    }

    public void createStaff(Staff staff) {
        getCityDAO().create(staff.getAddress().getCity());
        getAddressDAO().create(staff.getAddress());
        staff.setStore(activeStore);
        staffDAO.create(staff);
    }

    public void handleRentals(int customerId) {
        Customer customer = customerDAO.read(customerId);
        for(Inventory inventory : inventoryObservableList){
            Rental rental = createRental(inventory,customer);
            BigDecimal price = inventory.getFilm().getRentalRate();
            createPayment(customer, rental, price);
        }

    }

    public Rental createRental(Inventory inventory, Customer customer) {
        Rental rental = new Rental();
        rental.setRentalDate(LocalDateTime.now());
        rental.setInventory(inventory);
        rental.setCustomer(customer);
        rental.setStaff(activeStore.getManagerStaff());
        rental.setLastUpdate(new Timestamp(System.currentTimeMillis()));
        return rentalDAO.create(rental); //kolla upp cascade... uppdateras alla som påverkas?
    }

    private void createPayment(Customer customer, Rental rental, BigDecimal rentalRate) {
        Payment payment = new Payment();
        payment.setCustomer(customer);
        payment.setStaff(activeStore.getManagerStaff());
        payment.setRental(rental);
        payment.setAmount(rentalRate);
        payment.setPaymentDate(LocalDateTime.now());
        payment.setLastUpdate(new Timestamp(System.currentTimeMillis()));
        paymentDAO.create(payment);
    }

    public void deleteSelectedStaff(Staff staff) {
        paymentDAO.deletePaymentsByStaffId(staff.getStaffId());
        rentalDAO.deleteRentalsByStaffId(staff.getStaffId());

        staffDAO.delete(staff.getStaffId());
    }

    public void updateStaff(Staff staff) {
        staffDAO.update(staff);
    }

    public void createNewStore(Store store) {
        getCityDAO().create(store.getAddress().getCity());
        getAddressDAO().create(store.getAddress());
        storeDAO.create(store);
        Staff staff = store.getManagerStaff();
        staff.setStore(store);
        updateStaff(staff);
    }

    public void deleteSelectedStore(Store store) {
        if(store != null){
            storeDAO.delete(store.getStoreId());
        }
    }
}
