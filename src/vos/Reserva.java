/**-------------------------------------------------------------------
 * $Id$
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación
 *
 * Materia: Sistemas Transaccionales
 * Ejercicio: VideoAndes
 * Autor: Juan Felipe García - jf.garcia268@uniandes.edu.co
 * -------------------------------------------------------------------
 */
package vos;

import java.sql.Date;
import java.sql.Time;

import org.codehaus.jackson.annotate.*;

/**
 * Clase que representa un Video
 * @author Monitores 2017-20
 */
public class Reserva {

	//// Atributos

	/**
	 * Id del video
	 */
	@JsonProperty(value="fecha")
	private Date fecha;

	/**
	 * Nombre del video
	 */
	@JsonProperty(value="hora")
	private Time hora;

	/**
	 * Duracion en minutos del video
	 */
	@JsonProperty(value="numeroComensales")
	private Integer numeroComensales;
	
	/**
	 * Metodo constructor de la clase video
	 * <b>post: </b> Crea el video con los valores que entran como parametro
	 * @param id - Id del video.
	 * @param name - Nombre del video. name != null
	 * @param duration - Duracion en minutos del video.
	 */
	public Reserva(@JsonProperty(value="fecha")Date fecha, @JsonProperty(value="hora")Time hora,@JsonProperty(value="numeroComensales")Integer numeroComensales) {
		super();
		this.fecha = fecha;
		this.hora = hora;
		this.numeroComensales = numeroComensales;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Time getHora() {
		return hora;
	}

	public void setHora(Time hora) {
		this.hora = hora;
	}

	public Integer getNumeroComensales() {
		return numeroComensales;
	}

	public void setNumeroComensales(Integer numeroComensales) {
		this.numeroComensales = numeroComensales;
	}
	



}
