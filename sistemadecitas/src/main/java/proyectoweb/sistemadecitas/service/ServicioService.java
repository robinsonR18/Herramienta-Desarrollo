package proyectoweb.sistemadecitas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import proyectoweb.sistemadecitas.model.Servicio;
import proyectoweb.sistemadecitas.repository.ServicioRepository;

@Service
public class ServicioService {
    @Autowired
    private ServicioRepository servicioRepository;

    //Obtener todos los servicios
    public List<Servicio> ObtenerTodosLosServicios(){
        return servicioRepository.findAll();
    }

    //Crear un nuevo servicio
    public Servicio crearServicio(Servicio servicio){
        return servicioRepository.save(servicio);
    }

    //Actualizar un producto existente
    public Servicio actualizarServicioo(Long id, Servicio servicio){
        if (servicioRepository.existsById(id)){
            servicio.setId(id);
            return servicioRepository.save(servicio);
        }
        throw new IllegalArgumentException("Servicio no encontrado para actualizar");
    }
}