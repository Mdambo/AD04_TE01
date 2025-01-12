package eus.birt.dam.service;

import eus.birt.dam.domain.Viaje;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class ViajeService {
    private final SessionFactory sessionFactory;

    public ViajeService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void crearViaje(Viaje viaje) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(viaje);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public Viaje buscarViajePorId(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Viaje.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void actualizarViaje(Viaje viaje) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(viaje);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public List<Viaje> buscarViajes(String origen, String destino) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Viaje WHERE ciudadOrigen = :ciudadOrigen AND ciudadDestino = :ciudadDestino", Viaje.class)
                    .setParameter("ciudadOrigen", origen)
                    .setParameter("ciudadDestino", destino)
                    .list();
        }
    }

    public List<Viaje> listarViajes() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Viaje", Viaje.class).list();
        }
    }
}