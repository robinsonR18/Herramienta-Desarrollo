package proyectoweb.sistemadecitas.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;

import proyectoweb.sistemadecitas.model.Reserva;
import proyectoweb.sistemadecitas.model.Servicio;

public interface ReservaRepository extends JpaRepository<Reserva, Long>{

    // Buscar una reserva para un servicio y una fecha y hora específicas
    Reserva findByServicioAndFechaHora(Servicio servicio, LocalDateTime fechaHora);

    // Buscar si existe alguna reserva en el rango de tiempo basado en la duración del servicio
    Reserva findByServicioAndFechaHoraBetween(Servicio servicio, LocalDateTime start, LocalDateTime end);
    
    // Método adicional para verificar la disponibilidad del servicio en un rango de tiempo con duración
    default boolean checkDisponibilidad(Servicio servicio, LocalDateTime fechaHora) {
        // Calcular la hora final sumando la duración del servicio (en minutos)
        LocalDateTime fechaHoraFinal = fechaHora.plusMinutes(servicio.getDuracion());

        // Verificar si ya hay alguna reserva en ese rango
        Reserva reservaExistente = findByServicioAndFechaHoraBetween(servicio, fechaHora, fechaHoraFinal);

        // Si no se encuentra ninguna reserva en el rango, se puede hacer la nueva reserva
        return reservaExistente == null;
    }
}
