package vos;

import java.util.Date;
import org.codehaus.jackson.annotate.JsonProperty;

public class ConsultaCliente {

	@JsonProperty(value = "idcliente")
	private String idcliente;

	@JsonProperty(value = "dia")
	private String dia;

	@JsonProperty(value = "comprasEnDia")
	private Long comprasEnDia;

	@JsonProperty(value = "idPedido")
	private Long idPedido;

	@JsonProperty(value = "fechapedido")
	private Date fechapedido;

	@JsonProperty(value = "pedidoservido")
	private Long pedidoservido;

	@JsonProperty(value = "tipoPref")
	private String tipoPref;

	@JsonProperty(value = "valorPref")
	private String valorPref;

	@JsonProperty(value = "nombreCliente")
	private String nombreCliente;

	@JsonProperty(value = "correo")
	private String correo;

	@JsonProperty(value = "rol")
	private String rol;

	public ConsultaCliente(@JsonProperty(value = "idcliente") String idcliente, @JsonProperty(value = "dia") String dia,
			@JsonProperty(value = "comprasEnDia") Long comprasEnDia, @JsonProperty(value = "idPedido") Long idPedido,
			@JsonProperty(value = "fechapedido") Date fechapedido,
			@JsonProperty(value = "pedidoservido") Long pedidoservido,
			@JsonProperty(value = "tipoPref") String tipoPref, @JsonProperty(value = "valorPref") String valorPref,
			@JsonProperty(value = "nombreCliente") String nombreCliente, @JsonProperty(value = "correo") String correo,
			@JsonProperty(value = "rol") String rol) {
		this.idcliente = idcliente;
		this.dia = dia;
		this.comprasEnDia = comprasEnDia;
		this.idPedido = idPedido;
		this.fechapedido = fechapedido;
		this.pedidoservido = pedidoservido;
		this.tipoPref = tipoPref;
		this.valorPref = valorPref;
		this.nombreCliente = nombreCliente;
		this.correo = correo;
		this.rol = rol;

	}

	public String getIdcliente() {
		return idcliente;
	}

	public void setIdcliente(String idcliente) {
		this.idcliente = idcliente;
	}

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	public Long getComprasEnDia() {
		return comprasEnDia;
	}

	public void setComprasEnDia(Long comprasEnDia) {
		this.comprasEnDia = comprasEnDia;
	}

	public Long getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(Long idPedido) {
		this.idPedido = idPedido;
	}

	public Date getFechapedido() {
		return fechapedido;
	}

	public void setFechapedido(Date fechapedido) {
		this.fechapedido = fechapedido;
	}

	public Long getPedidoservido() {
		return pedidoservido;
	}

	public void setPedidoservido(Long pedidoservido) {
		this.pedidoservido = pedidoservido;
	}

	public String getTipoPref() {
		return tipoPref;
	}

	public void setTipoPref(String tipoPref) {
		this.tipoPref = tipoPref;
	}

	public String getValorPref() {
		return valorPref;
	}

	public void setValorPref(String valorPref) {
		this.valorPref = valorPref;
	}

	public String getNombreCliente() {
		return nombreCliente;
	}

	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}
}
