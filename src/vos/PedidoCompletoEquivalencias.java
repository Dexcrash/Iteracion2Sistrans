package vos;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class PedidoCompletoEquivalencias {

	/**
	 * Nombre del usuario
	 */
	@JsonProperty(value = "id")
	private Long id;

	/**
	 * Identificador del usuario
	 */
	@JsonProperty(value = "fecha")
	private String fecha;
	
	/**
	 * Correo electronico del usuario
	 */
	@JsonProperty(value = "servido")
	private Long servido;
	
	/**
	 * Nombre del usuario
	 */
	@JsonProperty(value = "idCliente")
	private Long idCliente;
	
	/**
	 * Correo electronico del usuario
	 */
	@JsonProperty(value = "idsMenu")
	private String idsMenu;
	
	/**
	 * Correo electronico del usuario
	 */
	@JsonProperty(value = "idsProducto")
	private String idsProducto;
	
	/**
	 * Metodo constructor de la clase video
	 * <b>post: </b> Crea el video con los valores que entran como parametro
	 * @param id - Id del video.
	 * @param name - Nombre del video. name != null
	 * @param duration - Duracion en minutos del video.
	 */
	public PedidoCompletoEquivalencias(@JsonProperty(value="id")Long id, @JsonProperty(value="fecha")String fecha, 
			@JsonProperty(value = "servido") Long servido,@JsonProperty(value="idCliente")Long idCliente,
			@JsonProperty(value = "idsMenu") String idsMenu, @JsonProperty(value = "idsProducto") String idsProducto) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.servido = servido;
		this.idCliente = idCliente;
		this.idsMenu = idsMenu;
		this.idsProducto = idsProducto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public Long getServido() {
		return servido;
	}

	public void setServido(Long servido) {
		this.servido = servido;
	}

	public String getIdsMenu() {
		return idsMenu;
	}

	public void setIdsMenu(String idsMenu) {
		this.idsMenu = idsMenu;
	}

	public Long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}

	public String getIdsProducto() {
		return idsProducto;
	}

	public void setIdsProducto(String idsProducto) {
		this.idsProducto = idsProducto;
	}


}
