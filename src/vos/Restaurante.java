package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Restaurante {

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
	@JsonProperty(value = "representante")
	private String representante;

	/**
	 * Rol del usuario
	 */
	@JsonProperty(value = "paginaWeb")
	private String paginaWeb;

	/**
	 * Pass del usuario
	 */
	@JsonProperty(value = "balancePrecio")
	private Double balancePrecio;

	/**
	 * Pass del usuario
	 */
	@JsonProperty(value = "balanceCosto")
	private Double balanceCosto;

	/**
	 * Pass del usuario
	 */
	@JsonProperty(value = "usuario")
	private String usuario;
	
	/**
	 * Pass del usuario
	 */
	@JsonProperty(value = "nombreZona")
	private String nombreZona;
	
	
	/**
	 * Metodo constructor de la clase video
	 * <b>post: </b> Crea el video con los valores que entran como parametro
	 * @param id - Id del video.
	 * @param name - Nombre del video. name != null
	 * @param duration - Duracion en minutos del video.
	 */
	public Restaurante(@JsonProperty(value="id")Long id, @JsonProperty(value="nombre")String nombre,@JsonProperty(value="tipo")String tipo, 
			@JsonProperty(value = "representante") String representante, @JsonProperty(value="paginaWeb")String paginaWeb, 
			@JsonProperty(value="balancePrecio")Double balancePrecio, @JsonProperty(value="balanceCosto")Double balanceCosto,
			@JsonProperty(value="usuario")String usuario, @JsonProperty(value="nombreZona")String nombreZona ) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.representante= representante;
		this.paginaWeb = paginaWeb;
		this.balancePrecio = balancePrecio;
		this.balanceCosto = balanceCosto;
		this.usuario = usuario;
		this.nombreZona = nombreZona;
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

	public String getRepresentante() {
		return representante;
	}

	public void setRepresentante(String representante) {
		this.representante = representante;
	}

	public String getPaginaWeb() {
		return paginaWeb;
	}

	public void setPaginaWeb(String paginaWeb) {
		this.paginaWeb = paginaWeb;
	}

	public Double getBalancePrecio() {
		return balancePrecio;
	}

	public void setBalancePrecio(Double balancePrecio) {
		this.balancePrecio = balancePrecio;
	}

	public Double getBalanceCosto() {
		return balanceCosto;
	}

	public void setBalanceCosto(Double balanceCosto) {
		this.balanceCosto = balanceCosto;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getNombreZona() {
		return nombreZona;
	}

	public void setNombreZona(String nombreZona) {
		this.nombreZona = nombreZona;
	}

	
}
