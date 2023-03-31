package org.example.dao;
import org.example.entities.Store;
import org.hibernate.Session;
import org.hibernate.query.Query;
public class StoreDAO extends AbstractDAO<Store>{
    public StoreDAO(){ super(Store.class);}
}
