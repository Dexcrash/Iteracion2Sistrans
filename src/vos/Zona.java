package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Zona {
	//// Atributos

	/**
	 * Nombre del video
	 */
	@JsonProperty(value="name")
	private String name;

	/**
	 * Metodo constructor de la clase video
	 * <b>post: </b> Crea el video con los valores que entran como parametro
	 * @param id - Id del video.
	 * @param name - Nombre del video. name != null
	 * @param duration - Duracion en minutos del video.
	 */
	public Zona( @JsonProperty(value="name")String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	/**
	 * Metodo setter del atributo name <b>post: </b> El nombre del video ha sido
	 * cambiado con el valor que entra como parametro
	 * @param name - Id del video
	 */
	public void setName(String name) {
		this.name = name;
	}
}
