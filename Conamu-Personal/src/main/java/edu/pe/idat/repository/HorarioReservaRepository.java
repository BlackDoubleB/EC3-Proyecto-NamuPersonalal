package edu.pe.idat.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.pe.idat.model.HorarioReserva;
import jakarta.transaction.Transactional;

public interface HorarioReservaRepository extends JpaRepository<HorarioReserva, Number> {

	@Transactional
	@Modifying
	@Query(value = "{call sp_create_horario_reserva(:fecha, :horario, :nro_mesas)}", nativeQuery = true)
	void create(@Param("fecha")String fecha,
			@Param("horario")String horario,
			@Param("nro_mesas")int mesas);
	
	List<HorarioReserva> findAllByFecha(Date fecha);
}
