package proyectoweb.sistemadecitas.controller;

import proyectoweb.sistemadecitas.model.Reserva;
import proyectoweb.sistemadecitas.service.ReservaService;
import proyectoweb.sistemadecitas.service.ServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class ReservaController {

    @Autowired
    private ServicioService servicioService;

    @Autowired
    private ReservaService reservaService;

    // Mostrar el formulario para hacer una reserva (GET)
    @GetMapping("/reservar")
    public String mostrarFormularioReserva(Model model) {
        // Obtener la lista de servicios disponibles
        model.addAttribute("servicios", servicioService.ObtenerTodosLosServicios());
        model.addAttribute("reserva", new Reserva());  // Crear un objeto Reserva vacío
        return "crearReserva";  // Vista para crear una nueva reserva
    }

    // Procesar la reserva (POST)
    @PostMapping("/reservar")
    public String realizarReserva(Reserva reserva, Model model) {
        // Verificar si el servicio está disponible en la fecha y hora seleccionada, considerando la duración
        boolean disponible = reservaService.verificarDisponibilidad(reserva.getServicio(), reserva.getFechaHora());
    
        if (disponible) {
            // Realizar la reserva
            reservaService.realizarReserva(reserva.getServicio(), reserva.getFechaHora());
            model.addAttribute("mensaje", "Reserva realizada con éxito");
            return "reservar";  // Vista de éxito
        } else {
            // Mostrar mensaje de error si la reserva no es posible
            model.addAttribute("mensaje", "El servicio ya está reservado para esa fecha y hora.");
            model.addAttribute("servicios", servicioService.ObtenerTodosLosServicios());
            return "crearReserva";  // Volver al formulario de reserva
        }
    }
    
}
