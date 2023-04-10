package org.example.dao;

import org.example.entities.Payment;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class PaymentDAO extends AbstractDAO<Payment>{
    public PaymentDAO() {super(Payment.class);}

    public void deletePaymentsByCustomerId(int customerId) {
        try (Session session = getSession()) {
            session.beginTransaction();
            Query query = session.createQuery("delete from Payment where customer.customerId = :customerId");
            query.setParameter("customerId", customerId);
            query.executeUpdate();
            session.getTransaction().commit();
        }
    }
}
