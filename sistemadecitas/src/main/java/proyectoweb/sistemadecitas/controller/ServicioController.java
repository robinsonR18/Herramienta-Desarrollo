package proyectoweb.sistemadecitas.controller;

import proyectoweb.sistemadecitas.model.Servicio;
import proyectoweb.sistemadecitas.service.ServicioService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/servicios")
    public String MostrarServicios(Model model) {
        List<Servicio> servicios = servicioService.ObtenerTodosLosServicios();
        model.addAttribute("servicios", servicios);
        return "servicios";
    }

    // Procesar el formulario para crear un servicio (POST)
    @PostMapping("/crearServicio")
    public String crearServicio(Servicio servicio, Model model) {
        // Guardar el nuevo servicio en la base de datos
        servicioService.crearServicio(servicio);

        // Agregar un mensaje de éxito
        model.addAttribute("mensaje", "Servicio creado con éxito");
        return "redirect:/crearServicio"; 
    }
}

