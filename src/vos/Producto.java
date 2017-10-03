package vos;


import java.sql.Time;
import java.time.LocalTime;

import org.codehaus.jackson.annotate.JsonProperty;

public class Producto {

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
	@JsonProperty(value = "tipo")
	private String tipo;

	/**
	 * Correo electronico del usuario
	 */
	@JsonProperty(value = "disponibles")
	private Integer disponibles;

	/**
	 * Rol del usuario
	 */
	@JsonProperty(value = "tiempoPreparacion")
	private Long tiempoPreparacion;

	/**
	 * Pass del usuario
	 */
	@JsonProperty(value = "precio")
	private Double precio;

	/**
	 * Pass del usuario
	 */
	@JsonProperty(value = "costo")
	private Double costo;
	
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
	 * Identificador del usuario
	 */
	@JsonProperty(value = "idRestaurante")
	private Long idRestaurante;

	/**
	 * Metodo constructor de la clase video
	 * <b>post: </b> Crea el video con los valores que entran como parametro
	 * @param id - Id del video.
	 * @param name - Nombre del video. name != null
	 * @param duration - Duracion en minutos del video.
	 */
	public Producto(@JsonProperty(value="id")Long id, @JsonProperty(value="nombre")String nombre,@JsonProperty(value="tipo")String tipo, 
			@JsonProperty(value = "disponibles") Integer disponibles, @JsonProperty(value="tiempoPreparacion")Long tiempoPreparacion, 
			@JsonProperty(value="precio")Double precio, @JsonProperty(value="costo")Double costo, 
			@JsonProperty(value="descripcion")String descripcion,@JsonProperty(value="traduccionDescripcion")String traduccion,
			@JsonProperty(value="idRestaurante")Long idRestaurante) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.tipo = tipo;
		this.tiempoPreparacion = tiempoPreparacion;
		this.disponibles= disponibles;
		this.precio = precio;
		this.costo = costo;
		this.descripcion = descripcion;
		this.traduccion = traduccion;
		this.idRestaurante = idRestaurante;
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


	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	public Integer getDisponibles() {
		return disponibles;
	}


	public void setDisponibles(Integer disponibles) {
		this.disponibles = disponibles;
	}


	public Long getTiempoPreparacion() {
		return tiempoPreparacion;
	}


	public void setTiempoPreparacion(Long tiempoPreparacion) {
		this.tiempoPreparacion = tiempoPreparacion;
	}


	public Double getPrecio() {
		return precio;
	}


	public void setPrecio(Double precio) {
		this.precio = precio;
	}


	public Double getCosto() {
		return costo;
	}


	public void setCosto(Double costo) {
		this.costo = costo;
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


	public Long getIdRestaurante() {
		return idRestaurante;
	}


	public void setIdRestaurante(Long idRestaurante) {
		this.idRestaurante = idRestaurante;
	}


	

}
