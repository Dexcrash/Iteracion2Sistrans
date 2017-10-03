package vos;

import java.time.LocalTime;

import org.codehaus.jackson.annotate.JsonProperty;

public class Ingrediente {


	//// Atributos

	/**
	 * Nombre del usuario
	 */
	@JsonProperty(value = "id")
	private Long id;

	/**
	 * Identificador del usuario
	 */
	@JsonProperty(value = "nombre")
	private String nombre;

	/**
	 * Identificador del usuario
	 */
	@JsonProperty(value = "descripcion")
	private String descripcion;
	
	/**
	 * Identificador del usuario
	 */
	@JsonProperty(value = "traduccion")
	private String traduccion;


	/**
	 * Metodo constructor de la clase video
	 * <b>post: </b> Crea el video con los valores que entran como parametro
	 * @param id - Id del video.
	 * @param name - Nombre del video. name != null
	 * @param duration - Duracion en minutos del video.
	 */
	public Ingrediente(@JsonProperty(value="id")Long id, @JsonProperty(value="nombre")String nombre,		
			@JsonProperty(value="descripcion")String descripcion,@JsonProperty(value="traduccionDescripcion")String traduccion) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.traduccion = traduccion;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
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


	public String getTraduccion() {
		return traduccion;
	}


	public void setTraduccion(String traduccion) {
		this.traduccion = traduccion;
	}


}
