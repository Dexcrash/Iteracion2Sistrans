package vos;

import java.sql.Date;
import java.sql.Time;

import org.codehaus.jackson.annotate.JsonProperty;

public class Pedido {

	//// Atributos

	/**
	 * Nombre del usuario
	 */
	@JsonProperty(value = "id")
	private Long id;

	/**
	 * Identificador del usuario
	 */
	@JsonProperty(value = "fecha")
	private Date fecha;
	
	/**
	 * Identificador del usuario
	 */
	@JsonProperty(value = "hora")
	private Time hora;

	/**
	 * Correo electronico del usuario
	 */
	@JsonProperty(value = "servido")
	private boolean servido;
	
	/**
	 * Metodo constructor de la clase video
	 * <b>post: </b> Crea el video con los valores que entran como parametro
	 * @param id - Id del video.
	 * @param name - Nombre del video. name != null
	 * @param duration - Duracion en minutos del video.
	 */
	public Pedido(@JsonProperty(value="id")Long id, @JsonProperty(value="fecha")Date fecha,@JsonProperty(value="hora")Time hora, 
			@JsonProperty(value = "servido") boolean servido) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.hora = hora;
		this.servido = servido;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Time getHora() {
		return hora;
	}

	public void setHora(Time hora) {
		this.hora = hora;
	}

	public boolean isServido() {
		return servido;
	}

	public void setServido(boolean servido) {
		this.servido = servido;
	}


	
}
