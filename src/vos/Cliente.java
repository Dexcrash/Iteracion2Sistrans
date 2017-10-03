package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Cliente extends Usuario{
	//// Atributos


	/**
	 * Metodo constructor de la clase video
	 * <b>post: </b> Crea el video con los valores que entran como parametro
	 * @param id - Id del video.
	 * @param name - Nombre del video. name != null
	 * @param duration - Duracion en minutos del video.
	 */
	public Cliente(@JsonProperty(value="rol")String rol, @JsonProperty(value="name")String name,@JsonProperty(value="identificacion")String identificacion, @JsonProperty(value = "correoElectronico") String correoElectronico) {
		super(rol, name, identificacion, correoElectronico, "141");
	}


	

}
