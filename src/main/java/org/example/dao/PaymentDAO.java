package org.example.dao;

import org.example.entities.Payment;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class PaymentDAO extends AbstractDAO<Payment>{
    public PaymentDAO() {super(Payment.class);}
}
