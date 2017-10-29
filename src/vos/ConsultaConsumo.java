package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class ConsultaConsumo {

	@JsonProperty(value = "idPedido")
	private Long idPedido;

	@JsonProperty(value = "nombreMenu")
	private String nombreMenu;

	@JsonProperty(value = "nombreProducto")
	private String nombreProducto;

	public ConsultaConsumo(@JsonProperty(value = "idPedido") Long idPedido,
			@JsonProperty(value = "nombreMenu") String nombreMenu, @JsonProperty(value = "nombreProducto") String nombreProducto) {

		this.idPedido = idPedido;
		this.nombreMenu = nombreMenu;
		this.nombreProducto = nombreProducto;
	}

	public Long getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(Long idPedido) {
		this.idPedido = idPedido;
	}

	public String getNombreMenu() {
		return nombreMenu;
	}

	public void setNombreMenu(String nombreMenu) {
		this.nombreMenu = nombreMenu;
	}

	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}
	

}
