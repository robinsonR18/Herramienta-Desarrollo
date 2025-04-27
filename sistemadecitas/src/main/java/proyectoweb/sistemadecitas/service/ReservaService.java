package proyectoweb.sistemadecitas.service;

import proyectoweb.sistemadecitas.model.Reserva;
import proyectoweb.sistemadecitas.model.Servicio;
import proyectoweb.sistemadecitas.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    // Verificar si el servicio está disponible para la fecha y hora seleccionada, considerando la duración
    public boolean verificarDisponibilidad(Servicio servicio, LocalDateTime fechaHora) {
        // Calcular la hora final basándonos en la duración del servicio
        LocalDateTime fechaHoraFinal = fechaHora.plusMinutes(servicio.getDuracion());
    
        // Verificar si ya hay una reserva en ese rango de tiempo
        // Aquí se verifica si la nueva reserva se solapa con alguna existente
        Reserva reservaExistente = reservaRepository.findByServicioAndFechaHoraBetween(servicio, fechaHora, fechaHoraFinal);
    
        // Verifica que no haya reservas en el rango de tiempo (inicio y fin)
        if (reservaExistente != null) {
            return false;  // Si hay una reserva existente, significa que el horario está ocupado
        }
    
        // Además, verificamos si existe una reserva que se solape con el intervalo de tiempo de la nueva reserva.
        // La nueva reserva no debe solaparse con reservas que comiencen antes de la nueva reserva y terminen después de ella
        reservaExistente = reservaRepository.findByServicioAndFechaHoraBetween(servicio, fechaHora.minusMinutes(servicio.getDuracion()), fechaHoraFinal.plusMinutes(servicio.getDuracion()));
    
        return reservaExistente == null;  // Si es null, significa que el horario está disponible
    }
    
    

    // Realizar la reserva
    public Reserva realizarReserva(Servicio servicio, LocalDateTime fechaHora) {
        if (verificarDisponibilidad(servicio, fechaHora)) {
            Reserva nuevaReserva = new Reserva();
            nuevaReserva.setServicio(servicio);
            nuevaReserva.setFechaHora(fechaHora);
            return reservaRepository.save(nuevaReserva);  // Guardamos la nueva reserva en la base de datos
        } else {
            throw new RuntimeException("El servicio ya está reservado para esa fecha y hora.");
        }
    }
}
