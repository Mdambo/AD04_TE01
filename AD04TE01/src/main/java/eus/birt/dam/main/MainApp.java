package eus.birt.dam.main;

import eus.birt.dam.domain.*;
import eus.birt.dam.service.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class MainApp {

    public static void main(String[] args) {
        // Crear la SessionFactory desde hibernate.cfg.xml
    	SessionFactory sessionFactory = new Configuration()
    	        .configure("Hibernate.cfg.xml")
    	        .addAnnotatedClass(Conductor.class)
    	        .addAnnotatedClass(Pasajero.class)
    	        .addAnnotatedClass(Viaje.class)
    	        .addAnnotatedClass(Reserva.class)
    	        .buildSessionFactory();

    	// Instanciar los servicios
        ConductorService conductorService = new ConductorService(sessionFactory);
        PasajeroService pasajeroService = new PasajeroService(sessionFactory);
        ViajeService viajeService = new ViajeService(sessionFactory);
        ReservaService reservaService = new ReservaService(sessionFactory);

        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("=== Menú de Gestión de Viajes Compartidos ===");
            System.out.println("1. Crear conductor");
            System.out.println("2. Crear viaje");
            System.out.println("3. Buscar viajes disponibles");
            System.out.println("4. Crear pasajero");
            System.out.println("5. Crear reserva");
            System.out.println("6. Cancelar reserva");
            System.out.println("7. Listar viajes");
            System.out.println("8. Salir");
            // Comprobar que la opción introducida es un entero
            System.out.print("Elige una opción: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Por favor, introduce un número entero válido.");
                scanner.next(); // Descartar la entrada no válida
                System.out.print("Elige una opción: ");
            }

            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir salto de línea

            switch (opcion) {
                case 1:
                    // Crear conductor
                    System.out.print("Introduce el nombre del conductor: ");
                    String nombreConductor = scanner.nextLine();
                    System.out.print("Introduce el vehículo del conductor: ");
                    String vehiculo = scanner.nextLine();
                    Conductor conductor = new Conductor(nombreConductor, vehiculo);
                    conductorService.crearConductor(conductor);
                    System.out.println("Conductor creado con éxito.");
                    break;

                case 2:
                    // Crear viaje
                    System.out.print("Introduce la ciudad de origen: ");
                    String origen = scanner.nextLine();
                    System.out.print("Introduce la ciudad de destino: ");
                    String destino = scanner.nextLine();
                    String fechaHora;
                    while (true) {
	                    System.out.print("Introduce la fecha y hora (yyyy-MM-ddTHH:mm): ");
	                    fechaHora = scanner.nextLine();
	                    if (esFormatoFechaHoraValido(fechaHora)) {
	                        break;
	                    } else {
	                        System.out.printf("La fecha y hora '%s' no es válida. Debe estar en el formato 'yyyy-MM-ddTHH:mm'.\n", fechaHora);
	                    }
	                }
                    LocalDateTime fechaViaje = LocalDateTime.parse(fechaHora);
                    System.out.print("Introduce el número de plazas disponibles: ");
                    int plazas = scanner.nextInt();
                    System.out.print("Introduce el ID del conductor: ");
                    int idConductor = scanner.nextInt();
                    scanner.nextLine(); // Consumir salto de línea

                    Conductor conductorViaje = conductorService.buscarConductorPorId(idConductor);
                    if (conductorViaje != null) {
                        Viaje viaje = new Viaje(origen, destino, fechaViaje, plazas, conductorViaje);
                        viajeService.crearViaje(viaje);
                        System.out.println("Viaje creado con éxito.");
                    } else {
                        System.out.println("Conductor no encontrado.");
                    }
                    break;

                case 3:
                    // Buscar viajes
                    System.out.println("Introduce la ciudad de origen:");
                    String ciudadOrigen = scanner.nextLine();
                    System.out.println("Introduce la ciudad de destino:");
                    String ciudadDestino = scanner.nextLine();
                    List<Viaje> viajesEncontrados = viajeService.buscarViajes(ciudadOrigen, ciudadDestino);
                    if (!viajesEncontrados.isEmpty()) {
                        System.out.println("Viajes disponibles:");
                        for (Viaje v : viajesEncontrados) {
                            System.out.println(v);
                        }
                    } else {
                        System.out.println("No se encontraron viajes disponibles.");
                    }
                    break;

                case 4:
                    // Crear pasajero
                    System.out.print("Introduce el nombre del pasajero: ");
                    String nombrePasajero = scanner.nextLine();
                    System.out.print("Introduce el email del pasajero: ");
                    String emailPasajero = scanner.nextLine();
                    Pasajero pasajero = new Pasajero(nombrePasajero, emailPasajero);
                    pasajeroService.crearPasajero(pasajero);
                    System.out.println("Pasajero creado con éxito.");
                    break;

                case 5:
                    // Crear reserva
                    System.out.print("Introduce el ID del pasajero: ");
                    int idPasajero = scanner.nextInt();
                    System.out.print("Introduce el ID del viaje: ");
                    int idViaje = scanner.nextInt();
                    System.out.print("Introduce el número de plazas a reservar: ");
                    int plazasReservadas = scanner.nextInt();
                    scanner.nextLine(); // Consumir salto de línea

                    Pasajero pasajeroReserva = pasajeroService.buscarPasajeroPorId(idPasajero);
                    Viaje viajeReserva = viajeService.buscarViajePorId(idViaje);

                    if (pasajeroReserva != null && viajeReserva != null && viajeReserva.getPlazasDisponibles() >= plazasReservadas) {
                    	reservaService.crearReserva(idPasajero, idViaje, plazasReservadas);
                        viajeReserva.setPlazasDisponibles(viajeReserva.getPlazasDisponibles() - plazasReservadas);
                        viajeService.actualizarViaje(viajeReserva);
                        System.out.println("Reserva creada con éxito.");
                    } else {
                        System.out.println("Error al crear la reserva. Verifica los datos.");
                    }
                    break;

                case 6:
                    // Cancelar reserva
                	System.out.println("Introduce el ID del pasajero:");
                	int pasajeroId = scanner.nextInt();
                	System.out.println("Introduce el ID del viaje:");
                	int viajeId = scanner.nextInt();

                	reservaService.cancelarReserva(pasajeroId, viajeId);
                	break;

                case 7:
                    // Listar viajes
                    System.out.println("Lista de viajes:");
                    List<Viaje> viajes = viajeService.listarViajes();
                    for (Viaje v : viajes) {
                        System.out.println(v);
                    }
                    break;

                case 8:
                    // Salir
                    System.out.println("¡Agur!");
                    break;

                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
                    break;
            }
        } while (opcion != 8);

        scanner.close();

        // Cerrar la SessionFactory al finalizar
        sessionFactory.close();
    }
    
    public static boolean esFormatoFechaHoraValido(String fechaHora) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            formatter.parse(fechaHora);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}