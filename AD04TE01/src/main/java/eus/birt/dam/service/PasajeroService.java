package eus.birt.dam.service;

import eus.birt.dam.domain.Pasajero;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class PasajeroService {
    private final SessionFactory sessionFactory;

    public PasajeroService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void crearPasajero(Pasajero pasajero) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(pasajero);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public Pasajero buscarPasajeroPorId(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Pasajero.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Pasajero> listarPasajeros() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Pasajero", Pasajero.class).list();
        }
    }
}