package proyectoweb.sistemadecitas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import proyectoweb.sistemadecitas.model.Producto;
import proyectoweb.sistemadecitas.service.ProductoService;

@Controller
public class ProductoController {
    
     @Autowired
    private ProductoService productoService;

    @GetMapping("/paginaAdmin")  // Ruta para la página de inicio (index)
    public String paginaAdmin() {
        return "paginaAdmin";  // Vista que redirige a la página principal
    }

    @GetMapping("/catalogo")
    public String mostrarCarta(Model model){
        model.addAttribute("productos", productoService.obtenerTodosLosProductos());
        return "catalogo"; // Vista del catalogo
    }

    
    @GetMapping("/catalogoUsuario")
    public String mostrarCatalogo(Model model){
        model.addAttribute("productos", productoService.obtenerTodosLosProductos());
        return "producto"; // Vista del catalogo
    }

    // Editar un producto existente
    @GetMapping("/editarProducto/{id}")
    public String editarProducto(@PathVariable Long id, Model model){
        Producto producto = productoService.obtenerProductoPorId(id).orElse(null);
        if (producto == null) {
            return "error";  // Si no se encuentra el producto, redirige a una página de error
        }
        model.addAttribute("producto", producto);
        return "editarProducto"; // Vista para editar un producto
    }

    @GetMapping("/crearProducto")
    public String mostrarFormularioCrearProducto(Model model) {
        model.addAttribute("producto", new Producto()); // Creamos un objeto Producto vacío para el formulario
        return "crearProducto"; // Vista para crear un nuevo producto
    }

    // Guardar producto (crear o actualizar)
    @PostMapping("/guardarProducto")
    public String guardarProducto(@ModelAttribute Producto producto) {
        try {
            if (producto.getId() != null) {
                // Si tiene ID, es una actualización
                productoService.actualizarProducto(producto.getId(), producto);
            } else {
                // Si no tiene ID, es una creación
                productoService.guardarProducto(producto);
            }
            return "redirect:/catalogo"; // Redirigir al catálogo después de guardar
        } catch (Exception e) {
            e.printStackTrace();  // Log del error para depuración
            return "error";  // Vista personalizada de error
        }
    }

    // Eliminar producto
    @GetMapping("/eliminarProducto/{id}")
    public String eliminarProducto(@PathVariable Long id) {
        try {
            productoService.eliminarProducto(id);  // Llamamos al servicio para eliminar el producto
            return "redirect:/catalogo";  // Redirigir al catálogo después de eliminar
        } catch (Exception e) {
            e.printStackTrace();  // Log del error para depuración
            return "error";  // Vista personalizada de error
        }
    }
}
