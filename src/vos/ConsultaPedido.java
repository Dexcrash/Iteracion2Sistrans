package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class ConsultaPedido {

	@JsonProperty(value = "idRestaurante")
	private Long idRestaurante;

	@JsonProperty(value = "nombreRestaurante")
	private String nombreRestaurante;

	@JsonProperty(value = "rolCliente")
	private String rolCliente;

	@JsonProperty(value = "idProducto")
	private Long idProducto;

	@JsonProperty(value = "unidades")
	private Long unidades;

	@JsonProperty(value = "ingresos")
	private Long ingresos;

	public ConsultaPedido(@JsonProperty(value = "idRestaurante") Long idRestaurante,
			@JsonProperty(value = "nombreRestaurante") String nombreRestaurante,
			@JsonProperty(value = "rolCliente") String rolCliente, @JsonProperty(value = "idProducto") Long idProducto,
			@JsonProperty(value = "unidades") Long unidades, @JsonProperty(value = "ingresos") Long ingresos) {

		this.idRestaurante = idRestaurante;
		this.nombreRestaurante = nombreRestaurante;
		this.rolCliente = rolCliente;
		this.idProducto = idProducto;
		this.unidades = unidades;
		this.ingresos = ingresos;
	}

	public Long getIdRestaurante() {
		return idRestaurante;
	}

	public void setIdRestaurante(Long idRestaurante) {
		this.idRestaurante = idRestaurante;
	}

	public String getNombreRestaurante() {
		return nombreRestaurante;
	}

	public void setNombreRestaurante(String nombreRestaurante) {
		this.nombreRestaurante = nombreRestaurante;
	}

	public String getRolCliente() {
		return rolCliente;
	}

	public void setRolCliente(String rolCliente) {
		this.rolCliente = rolCliente;
	}

	public Long getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(Long idProducto) {
		this.idProducto = idProducto;
	}

	public Long getUnidades() {
		return unidades;
	}

	public void setUnidades(Long unidades) {
		this.unidades = unidades;
	}

	public Long getIngresos() {
		return ingresos;
	}

	public void setIngresos(Long ingresos) {
		this.ingresos = ingresos;
	}

}
