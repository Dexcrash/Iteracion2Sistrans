package vos;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class ConsultaZona {

	//// Atributos

	/**
	 * Nombre del video
	 */
	@JsonProperty(value = "nombrezona")
	private String nombrezona;

	@JsonProperty(value = "numeroespacios")
	private Long numeroespacios;

	@JsonProperty(value = "capacidadTotal")
	private Long capacidadTotal;

	@JsonProperty(value = "numeroespaciosParaDiscapacitados")
	private Long numeroespaciosParaDescapacitados;

	@JsonProperty(value = "numeroespaciosAbiertos")
	private Long numeroespaciosAbiertos;

	@JsonProperty(value = "idPedidoServido")
	private Long idPedidoServido;

	@JsonProperty(value = "fechaServicio")
	private Date fechaServicio;

	@JsonProperty(value = "nombreProducto")
	private String nombreProducto;

	@JsonProperty(value = "nombreRestaurante")
	private String nombreRestaurante;

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
	public ConsultaZona(@JsonProperty(value = "nombrezona") String nombrezona,
			@JsonProperty(value = "numeroespacios") Long numeroespacios,
			@JsonProperty(value = "capacidadTotal") Long capacidadTotal,
			@JsonProperty(value = "numeroespaciosParaDiscapacitados") Long numeroespaciosParaDescapacitados,
			@JsonProperty(value = "numeroespaciosAbiertos") Long numeroespaciosAbiertos,
			@JsonProperty(value = "idPedidoServido") Long idPedidoServido,
			@JsonProperty(value = "fechaServicio") Date fechaServicio,
			@JsonProperty(value = "nombreProducto") String nombreProducto,
			@JsonProperty(value = "nombreRestaurante") String nombreRestaurante) {
		this.nombrezona = nombrezona;
		this.numeroespacios = numeroespacios;
		this.capacidadTotal = capacidadTotal;
		this.numeroespaciosParaDescapacitados = numeroespaciosParaDescapacitados;
		this.numeroespaciosAbiertos = numeroespaciosAbiertos;
		this.idPedidoServido = idPedidoServido;
		this.fechaServicio = fechaServicio;
		this.nombreProducto = nombreProducto;
		this.nombreRestaurante = nombreRestaurante;
	}

	public String getNombrezona() {
		return nombrezona;
	}

	public void setNombrezona(String nombrezona) {
		this.nombrezona = nombrezona;
	}

	public Long getNumeroespacios() {
		return numeroespacios;
	}

	public void setNumeroespacios(Long numeroespacios) {
		this.numeroespacios = numeroespacios;
	}

	public Long getCapacidadTotal() {
		return capacidadTotal;
	}

	public void setCapacidadTotal(Long capacidadTotal) {
		this.capacidadTotal = capacidadTotal;
	}

	public Long getNumeroespaciosParaDescapacitados() {
		return numeroespaciosParaDescapacitados;
	}

	public void setNumeroespaciosParaDescapacitados(Long numeroespaciosParaDescapacitados) {
		this.numeroespaciosParaDescapacitados = numeroespaciosParaDescapacitados;
	}

	public Long getNumeroespaciosAbiertos() {
		return numeroespaciosAbiertos;
	}

	public void setNumeroespaciosAbiertos(Long numeroespaciosAbiertos) {
		this.numeroespaciosAbiertos = numeroespaciosAbiertos;
	}

	public Long getIdPedidoServido() {
		return idPedidoServido;
	}

	public void setIdPedidoServido(Long idPedidoServido) {
		this.idPedidoServido = idPedidoServido;
	}

	public Date getFechaServicio() {
		return fechaServicio;
	}

	public void setFechaServicio(Date fechaServicio) {
		this.fechaServicio = fechaServicio;
	}

	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	public String getNombreRestaurante() {
		return nombreRestaurante;
	}

	public void setNombreRestaurante(String nombreRestaurante) {
		this.nombreRestaurante = nombreRestaurante;
	}

}
