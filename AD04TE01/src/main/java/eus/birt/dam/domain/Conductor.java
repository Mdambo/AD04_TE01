package eus.birt.dam.domain;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name="conductor")
public class Conductor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="nombre")
    private String nombre;

    @Column(name="vehiculo")
    private String vehiculo;

    @OneToMany(mappedBy = "conductor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Viaje> viajes;
 
	public Conductor() {
		
	}

	public Conductor(String nombre, String vehiculo, List<Viaje> viajes) {
		this.nombre = nombre;
		this.vehiculo = vehiculo;
		this.viajes = viajes;
	}

	public Conductor(String nombre, String vehiculo) {
		this.nombre = nombre;
		this.vehiculo = vehiculo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(String vehiculo) {
		this.vehiculo = vehiculo;
	}

	public List<Viaje> getViajes() {
		return viajes;
	}

	public void setViajes(List<Viaje> viajes) {
		this.viajes = viajes;
	}

	@Override
	public String toString() {
		return nombre;
	}

}