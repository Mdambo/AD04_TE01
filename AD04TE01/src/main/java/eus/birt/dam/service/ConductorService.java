package eus.birt.dam.service;

import eus.birt.dam.domain.Conductor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class ConductorService {
    private final SessionFactory sessionFactory;

    public ConductorService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void crearConductor(Conductor conductor) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(conductor);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public Conductor buscarConductorPorId(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Conductor.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Conductor> listarConductores() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM conductor", Conductor.class).list();
        }
    }
}