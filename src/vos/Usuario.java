package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Usuario {
	//// Atributos

	/**
	 * Nombre del usuario
	 */
	@JsonProperty(value="name")
	private String name;

	/**
	 * Identificador del usuario
	 */
	@JsonProperty(value="identificacion")
	private String identificacion;

	/**
	 * Correo electronico del usuario
	 */
	@JsonProperty(value="correoEletronico")
	private String correoEletronico;

	/**
	 * Rol del usuario
	 */
	@JsonProperty(value="rol")
	private String rol;
	
	/**
	 * Pass del usuario
	 */
	@JsonProperty(value="contraseña")
	private String contraseña;
	
	
	/**
	 * Metodo constructor de la clase video
	 * <b>post: </b> Crea el video con los valores que entran como parametro
	 * @param id - Id del video.
	 * @param name - Nombre del video. name != null
	 * @param duration - Duracion en minutos del video.
	 */
	public Usuario(@JsonProperty(value="rol")String rol, @JsonProperty(value="name")String name,@JsonProperty(value="identificacion")String identificacion, @JsonProperty(value = "correoElectronico") String correoElectronico, @JsonProperty(value="contraseña")String contraseña) {
		super();
		this.name = name;
		this.identificacion = identificacion;
		this.correoEletronico = correoElectronico;
		this.rol = rol;
		this.contraseña = contraseña;
	}


	public String getRol() {
		return rol;
	}


	public void setRol(String rol) {
		this.rol = rol;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getIdentificacion() {
		return identificacion;
	}


	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}


	public String getCorreoEletronico() {
		return correoEletronico;
	}


	public void setCorreoEletronico(String correoEletronico) {
		this.correoEletronico = correoEletronico;
	}


	public String getContraseña() {
		return contraseña;
	}


	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	


}
