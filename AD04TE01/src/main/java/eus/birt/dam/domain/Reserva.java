package eus.birt.dam.domain;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="reserva")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="fechaReserva")
    private LocalDate fechaReserva;

    @Column(name="numeroPlazasReservadas")
    private int numeroPlazasReservadas;

    @ManyToOne
    @JoinColumn(name = "pasajero_id")
    private Pasajero pasajero;

    @ManyToOne
    @JoinColumn(name = "viaje_id")
    private Viaje viaje; 
    
	public Reserva() {
		
	}

	public Reserva(LocalDate fechaReserva, int numeroPlazasReservadas, Pasajero pasajero, Viaje viaje) {
		this.fechaReserva = fechaReserva;
		this.numeroPlazasReservadas = numeroPlazasReservadas;
		this.pasajero = pasajero;
		this.viaje = viaje;
	}

	public Reserva(int numeroPlazasReservadas, Pasajero pasajero, Viaje viaje) {
		super();
		this.numeroPlazasReservadas = numeroPlazasReservadas;
		this.pasajero = pasajero;
		this.viaje = viaje;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getFechaReserva() {
		return fechaReserva;
	}

	public void setFechaReserva(LocalDate fechaReserva) {
		this.fechaReserva = fechaReserva;
	}

	public int getNumeroPlazasReservadas() {
		return numeroPlazasReservadas;
	}

	public void setNumeroPlazasReservadas(int numeroPlazasReservadas) {
		this.numeroPlazasReservadas = numeroPlazasReservadas;
	}

	public Pasajero getPasajero() {
		return pasajero;
	}

	public void setPasajero(Pasajero pasajero) {
		this.pasajero = pasajero;
	}

	public Viaje getViaje() {
		return viaje;
	}

	public void setViaje(Viaje viaje) {
		this.viaje = viaje;
	}

}