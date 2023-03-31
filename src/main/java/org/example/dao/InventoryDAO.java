package org.example.dao;

import org.example.entities.Inventory;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class InventoryDAO extends AbstractDAO<Inventory>{
    public InventoryDAO(){super(Inventory.class);}
}
