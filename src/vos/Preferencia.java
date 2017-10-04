package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Preferencia {
	//// Atributos

	/**
	 * Nombre del usuario
	 */
	@JsonProperty(value="tipo")
	private String tipo;

	/**
	 * Identificador del usuario
	 */
	@JsonProperty(value="valor")
	private String valor;	
	
	/**
	 * Identificador del usuario
	 */
	@JsonProperty(value="idCliente")
	private Long idCliente;	
	
	/**
	 * Metodo constructor de la clase video
	 * <b>post: </b> Crea el video con los valores que entran como parametro
	 * @param id - Id del video.
	 * @param name - Nombre del video. name != null
	 * @param duration - Duracion en minutos del video.
	 */
	public Preferencia(@JsonProperty(value="tipo")String tipo, @JsonProperty(value="valor")String valor,
			@JsonProperty(value="idCliente")Long idCliente) {
		super();
		this.tipo = tipo;
		this.valor = valor;
		this.idCliente = idCliente;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public Long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}

	

}
