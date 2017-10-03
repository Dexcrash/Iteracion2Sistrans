package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Menu {

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
	@JsonProperty(value = "entrada")
	private Long entrada;
	
	/**
	 * Identificador del usuario
	 */
	@JsonProperty(value = "platoFuerte")
	private Long platoFuerte;
	
	/**
	 * Identificador del usuario
	 */
	@JsonProperty(value = "acompañamiento")
	private Long acompañamiento;
	
	/**
	 * Identificador del usuario
	 */
	@JsonProperty(value = "postre")
	private Long postre;

	/**
	 * Correo electronico del usuario
	 */
	@JsonProperty(value = "bebida")
	private Long bebida;

	/**
	 * Rol del usuario
	 */
	@JsonProperty(value = "restaurante")
	private Long restaurate;

	
	/**
	 * Metodo constructor de la clase video
	 * <b>post: </b> Crea el video con los valores que entran como parametro
	 * @param id - Id del video.
	 * @param name - Nombre del video. name != null
	 * @param duration - Duracion en minutos del video.
	 */
	public Menu(@JsonProperty(value="id")Long id, @JsonProperty(value="nombre")String nombre, @JsonProperty(value = "entrada") Long entrada,
			@JsonProperty(value="platoFuerte")Long platoFuerte, @JsonProperty(value="acompañamiento")Long acompañamiento, 
			@JsonProperty(value="postre")Long postre, @JsonProperty(value="bebida")Long bebida){
		super();
		this.id = id;
		this.nombre = nombre;
		this.entrada = entrada;
		this.platoFuerte = platoFuerte;
		this.acompañamiento = acompañamiento;
		this.postre = postre;
		this.bebida = bebida;
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

	public Long getAcompañamiento() {
		return acompañamiento;
	}

	public void setAcompañamiento(Long acompañamiento) {
		this.acompañamiento = acompañamiento;
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
		return restaurate;
	}

	public void setRestaurate(Long restaurate) {
		this.restaurate = restaurate;
	}


	
	
}
