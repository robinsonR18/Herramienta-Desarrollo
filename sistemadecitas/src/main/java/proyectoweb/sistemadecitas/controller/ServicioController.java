package proyectoweb.sistemadecitas.controller;

import proyectoweb.sistemadecitas.model.Servicio;
import proyectoweb.sistemadecitas.service.ServicioService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ServicioController {

    @Autowired
    private ServicioService servicioService;

    // Mostrar formulario para crear un servicio (GET)
    @GetMapping("/crearServicio")
    public String mostrarFormularioCrearServicio(Model model) {
        model.addAttribute("servicio", new Servicio()); // Creamos un objeto Servicio vacío para el formulario
        return "crearServicio"; // Vista para crear un nuevo servicio (crearServicio.html)
    }

    @GetMapping("/verServicios")
    public String listaServicios(Model model) {
        List<Servicio> servicios = servicioService.ObtenerTodosLosServicios();
        model.addAttribute("servicios", servicios);
        return "verServicio";  // Nombre de la plantilla Thymeleaf
    }

        // Editar un servicio
    @GetMapping("/editarServicio/{id}")
    public String editarServicio(@PathVariable Long id, Model model) {
        Servicio servicio = servicioService.obtenerServicioPorId(id).orElse(null);  // Obtener servicio por ID
        if (servicio == null) {
            return "error";  // Si no se encuentra el servicio, redirigir a página de error
        }
        model.addAttribute("servicio", servicio);  // Añadir el servicio al modelo
        return "editarServicio";  // Vista para editar un servicio
    }

    @GetMapping("/servicios")
    public String MostrarServicios(Model model) {
        List<Servicio> servicios = servicioService.ObtenerTodosLosServicios();
        model.addAttribute("servicios", servicios);
        return "servicios";
    }

    // Procesar el formulario para crear un servicio (POST)
    @PostMapping("/crearServicio")
    public String crearServicio(Servicio servicio, Model model) {
        try {
            if (servicio.getId() != null) {
                // Si tiene ID, es una actualización
                servicioService.actualizarServicioo(servicio.getId(), servicio);
            } else {
                // Si no tiene ID, es una creación
                servicioService.crearServicio(servicio);
            }
            return "redirect:/verServicios"; // Redirigir al verServicios después de guardar
        } catch (Exception e) {
            e.printStackTrace();  // Log del error para depuración
            return "error";  // Vista personalizada de error
        }
    }
}

