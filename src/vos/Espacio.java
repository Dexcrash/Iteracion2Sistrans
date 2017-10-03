package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Espacio {

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
	@JsonProperty(value = "capacidad")
	private Integer capacidad;

	/**
	 * Correo electronico del usuario
	 */
	@JsonProperty(value = "accesoEspecial")
	private boolean accesoEspecial;

	/**
	 * Rol del usuario
	 */
	@JsonProperty(value = "abierto")
	private boolean abierto;

	
	/**
	 * Metodo constructor de la clase video
	 * <b>post: </b> Crea el video con los valores que entran como parametro
	 * @param id - Id del video.
	 * @param name - Nombre del video. name != null
	 * @param duration - Duracion en minutos del video.
	 */
	public Espacio(@JsonProperty(value="id")Long id, @JsonProperty(value="nombre")String nombre,@JsonProperty(value="capacidad")Integer capacidad, 
			@JsonProperty(value = "accesoEspecial") boolean accesoEspecial, @JsonProperty(value="abeirto")boolean abierto) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.capacidad= capacidad;
		this.accesoEspecial = accesoEspecial;
		this.abierto = abierto;
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

	public Integer getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(Integer capacidad) {
		this.capacidad = capacidad;
	}

	public boolean isAccesoEspecial() {
		return accesoEspecial;
	}

	public void setAccesoEspecial(boolean accesoEspecial) {
		this.accesoEspecial = accesoEspecial;
	}

	public boolean isAbierto() {
		return abierto;
	}

	public void setAbierto(boolean abierto) {
		this.abierto = abierto;
	}

	
	
}
