package vos;

import java.time.LocalTime;

import org.codehaus.jackson.annotate.JsonProperty;

public class Categoria {


	//// Atributos

	/**
	 * Nombre del usuario
	 */
	@JsonProperty(value = "nombre")
	private String nombre;

	/**
	 * Identificador del usuario
	 */
	@JsonProperty(value = "descripcion")
	private String descripcion;


	/**
	 * Metodo constructor de la clase video
	 * <b>post: </b> Crea el video con los valores que entran como parametro
	 * @param id - Id del video.
	 * @param name - Nombre del video. name != null
	 * @param duration - Duracion en minutos del video.
	 */
	public Categoria(@JsonProperty(value="nombre")String nombre, @JsonProperty(value="descripcion")String descripcion) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;		
	}

	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
