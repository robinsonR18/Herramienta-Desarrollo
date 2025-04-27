package proyectoweb.sistemadecitas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import proyectoweb.sistemadecitas.model.Producto;
import proyectoweb.sistemadecitas.repository.ProductoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    //Leer todos los productos
    public List<Producto> obtenerTodosLosProductos() {
        return productoRepository.findAll();
    }

    //leer un producto por id
    public Optional<Producto> obtenerProductoPorId(Long id) {
        return productoRepository.findById(id);
    }

    //Crear o guardar un producto nuevo
    public Producto guardarProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    //Actualizar un producto existente
    public Producto actualizarProducto(Long id, Producto producto){
        if (productoRepository.existsById(id)){
            producto.setId(id);
            return productoRepository.save(producto);
        }
        throw new IllegalArgumentException("Producto no encontrado para actualizar");
    }

    //Eliminar un producto por su ID
    public void eliminarProducto(Long id) {
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);  // Elimina el producto si existe
        } else {
            throw new IllegalArgumentException("Producto no encontrado para eliminar");
        }
    }
}