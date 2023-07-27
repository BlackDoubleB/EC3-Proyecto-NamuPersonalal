package edu.pe.idat.request;

public class UsuarioUpdateRequest {

	private String idTrabajador;
	private String telefono;
	private String correo;
	private String estado;
	
	public String getIdTrabajador() {
		return idTrabajador;
	}
	public void setIdTrabajador(String idTrabajador) {
		this.idTrabajador = idTrabajador;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
}
