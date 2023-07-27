package edu.pe.idat.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "estado_trabajador")
public class EstadoTrabajador {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id_Estado_Trabajador")
	private int idEstadoTrabajador;
	
	@Column(name = "Nombre_Estado")
	private String nombreEstado;
	
	
	public int getIdEstadoTrabajador() {
		return idEstadoTrabajador;
	}
	
	public void setIdEstadoTrabajador(int idEstadoTrabajador) {
		this.idEstadoTrabajador = idEstadoTrabajador;
	}
	
	public String getNombreEstado() {
		return nombreEstado;
	}
	
	public void setNombreEstado(String nombreEstado) {
		this.nombreEstado = nombreEstado;
	}
}
