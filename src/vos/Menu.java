package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Menu {

	//// Atributos

	/**
	 * Nombre del usuario
	 */
	@JsonProperty(value = "id")
	private String id;

	/**
	 * Identificador del usuario
	 */
	@JsonProperty(value = "nombre")
	private String nombre;

	@JsonProperty(value = "costo")
	private Long costo;

	/**
	 * Rol del usuario
	 */
	@JsonProperty(value = "precio")
	private Long precio;

	
	/**
	 * Identificador del usuario
	 */
	@JsonProperty(value = "identrada")
	private Long identrada;

	/**
	 * Identificador del usuario
	 */
	@JsonProperty(value = "idplatoFuerte")
	private Long idplatoFuerte;

	/**
	 * Identificador del usuario
	 */
	@JsonProperty(value = "idacompañamiento")
	private Long idacompañamiento;

	/**
	 * Identificador del usuario
	 */
	@JsonProperty(value = "idpostre")
	private Long idpostre;

	/**
	 * Correo electronico del usuario
	 */
	@JsonProperty(value = "idbebida")
	private Long idbebida;

	/**
	 * Rol del usuario
	 */
	@JsonProperty(value = "idrestaurante")
	private Long idrestaurante;

	/**
	 * Rol del usuario
	 */

	/**
	 * Metodo constructor de la clase video <b>post: </b> Crea el video con los
	 * valores que entran como parametro
	 * 
	 * @param id
	 *            - Id del video.
	 * @param name
	 *            - Nombre del video. name != null
	 * @param duration
	 *            - Duracion en minutos del video.
	 */
	public Menu(@JsonProperty(value = "id") String id, @JsonProperty(value = "nombre") String nombre,
			@JsonProperty(value = "costo") Long costo, @JsonProperty(value = "precio") Long precio,
			@JsonProperty(value = "idrestaurante") Long idrestaurante, @JsonProperty(value = "identrada") Long entrada,
			@JsonProperty(value = "idacompañamiento") Long acompañamiento,
			@JsonProperty(value = "idplatoFuerte") Long platoFuerte, @JsonProperty(value = "idpostre") Long postre,
			@JsonProperty(value = "idbebida") Long bebida) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.precio = precio;
		this.costo = costo;
		this.idrestaurante = idrestaurante;
		this.identrada = entrada;
		this.idplatoFuerte = platoFuerte;
		this.idacompañamiento = acompañamiento;
		this.idpostre = postre;
		this.idbebida = bebida;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Long getIdentrada() {
		return identrada;
	}

	public void setIdentrada(Long entrada) {
		this.identrada = entrada;
	}

	public Long getIdplatoFuerte() {
		return idplatoFuerte;
	}

	public void setIdplatoFuerte(Long platoFuerte) {
		this.idplatoFuerte = platoFuerte;
	}

	public Long getIdacompañamiento() {
		return idacompañamiento;
	}

	public void setIdacompañamiento(Long acompañamiento) {
		this.idacompañamiento = acompañamiento;
	}

	public Long getIdpostre() {
		return idpostre;
	}

	public void setIdpostre(Long postre) {
		this.idpostre = postre;
	}

	public Long getIdbebida() {
		return idbebida;
	}

	public void setIdbebida(Long bebida) {
		this.idbebida = bebida;
	}

	public Long getCosto() {
		return costo;
	}

	public void setCosto(Long costo) {
		this.costo = costo;
	}

	public Long getPrecio() {
		return precio;
	}

	public void setPrecio(Long precio) {
		this.precio = precio;
	}

	public Long getIdrestaurante() {
		return idrestaurante;
	}

	public void setIdrestaurante(Long idrestaurante) {
		this.idrestaurante = idrestaurante;
	}


}
