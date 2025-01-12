package eus.birt.dam.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="viaje")
public class Viaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="ciudadOrigen")
    private String ciudadOrigen;
    @Column(name="ciudadDestino")
    private String ciudadDestino;

    @Column(name="fechaHora")
    private LocalDateTime fechaHora;

    @Column(name="plazasDisponibles")
    private int plazasDisponibles;

    @ManyToOne
    @JoinColumn(name = "conductor_id")
    private Conductor conductor;

    @OneToMany(mappedBy = "viaje", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reserva> reservas;

	public Viaje() {
		
	}

	public Viaje(String ciudadOrigen, String ciudadDestino, LocalDateTime fechaHora, int plazasDisponibles,
			Conductor conductor, List<Reserva> reservas) {
		this.ciudadOrigen = ciudadOrigen;
		this.ciudadDestino = ciudadDestino;
		this.fechaHora = fechaHora;
		this.plazasDisponibles = plazasDisponibles;
		this.conductor = conductor;
		this.reservas = reservas;
	}

	public Viaje(String ciudadOrigen, String ciudadDestino, LocalDateTime fechaHora, int plazasDisponibles,
			Conductor conductor) {
		this.ciudadOrigen = ciudadOrigen;
		this.ciudadDestino = ciudadDestino;
		this.fechaHora = fechaHora;
		this.plazasDisponibles = plazasDisponibles;
		this.conductor = conductor;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCiudadOrigen() {
		return ciudadOrigen;
	}

	public void setCiudadOrigen(String ciudadOrigen) {
		this.ciudadOrigen = ciudadOrigen;
	}

	public String getCiudadDestino() {
		return ciudadDestino;
	}

	public void setCiudadDestino(String ciudadDestino) {
		this.ciudadDestino = ciudadDestino;
	}

	public LocalDateTime getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(LocalDateTime fechaHora) {
		this.fechaHora = fechaHora;
	}

	public int getPlazasDisponibles() {
		return plazasDisponibles;
	}

	public void setPlazasDisponibles(int plazasDisponibles) {
		this.plazasDisponibles = plazasDisponibles;
	}

	public Conductor getConductor() {
		return conductor;
	}

	public void setConductor(Conductor conductor) {
		this.conductor = conductor;
	}

	public List<Reserva> getReservas() {
		return reservas;
	}

	public void setReservas(List<Reserva> reservas) {
		this.reservas = reservas;
	}

	@Override
	public String toString() {
		return "ID: " + id + "\nOrigen: " + ciudadOrigen + "\nDestino: " + ciudadDestino
				+ "\nFecha y Hora: " + fechaHora + "\nPlazas Disponibles: " + plazasDisponibles 
				+ "\nConductor: " + conductor;
	}

}