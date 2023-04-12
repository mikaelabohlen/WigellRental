package org.example.dao;
import org.example.entities.Store;
import org.hibernate.Session;
import org.hibernate.query.Query;
public class StoreDAO extends AbstractDAO<Store>{
    public StoreDAO(){ super(Store.class);}

    public void  deleteStoreByStaffId(int staffId) {
        try (Session session = getSession()) {
            session.beginTransaction();
            Query query = session.createQuery("delete from Store where managerStaff.staffId = :staffId");
            query.setParameter("staffId", staffId);
            query.executeUpdate();
            session.getTransaction().commit();
        }
    }
}
