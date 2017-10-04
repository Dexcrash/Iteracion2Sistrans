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
	private Long entrada;

	/**
	 * Identificador del usuario
	 */
	@JsonProperty(value = "idplatoFuerte")
	private Long platoFuerte;

	/**
	 * Identificador del usuario
	 */
	@JsonProperty(value = "idacompa�amiento")
	private Long acompa�amiento;

	/**
	 * Identificador del usuario
	 */
	@JsonProperty(value = "idpostre")
	private Long postre;

	/**
	 * Correo electronico del usuario
	 */
	@JsonProperty(value = "idbebida")
	private Long bebida;

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
			@JsonProperty(value = "idacompa�amiento") Long acompa�amiento,
			@JsonProperty(value = "idplatoFuerte") Long platoFuerte, @JsonProperty(value = "idpostre") Long postre,
			@JsonProperty(value = "idbebida") Long bebida) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.precio = precio;
		this.costo = costo;
		this.idrestaurante = idrestaurante;
		this.entrada = entrada;
		this.platoFuerte = platoFuerte;
		this.acompa�amiento = acompa�amiento;
		this.postre = postre;
		this.bebida = bebida;
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

	public Long getEntrada() {
		return entrada;
	}

	public void setEntrada(Long entrada) {
		this.entrada = entrada;
	}

	public Long getPlatoFuerte() {
		return platoFuerte;
	}

	public void setPlatoFuerte(Long platoFuerte) {
		this.platoFuerte = platoFuerte;
	}

	public Long getAcompa�amiento() {
		return acompa�amiento;
	}

	public void setAcompa�amiento(Long acompa�amiento) {
		this.acompa�amiento = acompa�amiento;
	}

	public Long getPostre() {
		return postre;
	}

	public void setPostre(Long postre) {
		this.postre = postre;
	}

	public Long getBebida() {
		return bebida;
	}

	public void setBebida(Long bebida) {
		this.bebida = bebida;
	}

	public Long getRestaurate() {
		return idrestaurante;
	}

	public void setRestaurate(Long restaurate) {
		this.idrestaurante = restaurate;
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
