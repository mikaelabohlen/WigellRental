package org.example.dao;

import org.example.entities.Staff;
import org.example.entities.Store;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class StaffDAO extends AbstractDAO<Staff>{
    public StaffDAO(){ super(Staff.class);}
}
