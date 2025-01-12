package eus.birt.dam.service;

import eus.birt.dam.domain.Reserva;
import eus.birt.dam.domain.Viaje;
import eus.birt.dam.domain.Pasajero;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.List;

public class ReservaService {
    private final SessionFactory sessionFactory;

    public ReservaService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void crearReserva(int pasajeroId, int viajeId, int plazasReservadas) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            // Buscar el pasajero y el viaje en la base de datos
            Pasajero pasajero = session.get(Pasajero.class, pasajeroId);
            Viaje viaje = session.get(Viaje.class, viajeId);

            if (viaje != null && pasajero != null) {
                if (viaje.getPlazasDisponibles() >= plazasReservadas) {
                	// Obtener la fecha de hoy
                    LocalDate fechaHoy = LocalDate.now();

                    // Crear y guardar la nueva reserva
                    Reserva reserva = new Reserva(fechaHoy, plazasReservadas, pasajero, viaje);
                    session.persist(reserva);

                    // Reducir las plazas disponibles en el viaje
                    viaje.setPlazasDisponibles(viaje.getPlazasDisponibles() - plazasReservadas);
                    session.merge(viaje);

                    System.out.println("Reserva creada con éxito para el viaje en la fecha: " + viaje.getFechaHora());
                } else {
                    System.out.println("No hay suficientes plazas disponibles.");
                }
            } else {
                System.out.println("Pasajero o viaje no encontrado.");
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public Reserva buscarReservaPorId(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Reserva.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void cancelarReserva(int pasajeroId, int viajeId) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            // Buscar la reserva específica
            Reserva reserva = session.createQuery(
                    "FROM Reserva WHERE pasajero.id = :pasajeroId AND viaje.id = :viajeId", Reserva.class)
                    .setParameter("pasajeroId", pasajeroId)
                    .setParameter("viajeId", viajeId)
                    .uniqueResult();

            if (reserva != null) {
                int plazasADevolver = reserva.getNumeroPlazasReservadas();

                // Eliminar la reserva
                session.remove(reserva);

                // Ajustar las plazas del viaje
                Viaje viaje = reserva.getViaje();
                viaje.setPlazasDisponibles(viaje.getPlazasDisponibles() + plazasADevolver);
                session.merge(viaje);

                System.out.println("Reserva cancelada correctamente.");
            } else {
                System.out.println("No se encontró una reserva para los datos proporcionados.");
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public List<Reserva> listarReservas() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Reserva", Reserva.class).list();
        }
    }
}