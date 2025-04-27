package proyectoweb.sistemadecitas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class PaginaController {
    @GetMapping("/index")
    public String MostrarIndex() {
        return "index";
    }
    @GetMapping("#nosotros")
    public String MostrarNosotros() {
        return "nosotros";
    }
    
    @GetMapping("/contacto")
    public String MostrarContacto() {
        return "contacto";
    }

    
}
