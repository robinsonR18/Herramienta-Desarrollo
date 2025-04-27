package proyectoweb.sistemadecitas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import proyectoweb.sistemadecitas.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    
}
