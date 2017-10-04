package vos;

import java.util.Date;
import org.codehaus.jackson.annotate.JsonProperty;

public class ConsultaFiltros {

	@JsonProperty(value = "idRestaurante")
	private Long idRestaurante;

	@JsonProperty(value = "categoria")
	private String categoria;

	@JsonProperty(value = "precios")
	private String precios;

	@JsonProperty(value = "fechas")
	private String fechas;

	public ConsultaFiltros(@JsonProperty(value = "idRestaurante") Long idRestaurante, @JsonProperty(value = "categoria") String categoria,
			@JsonProperty(value = "precios") String precios, @JsonProperty(value = "fechas") String fechas) {
		this.idRestaurante = idRestaurante;
		this.categoria = categoria;
		this.precios = precios;
		this.fechas = fechas;
	}

	public Long getIdRestaurante() {
		return idRestaurante;
	}

	public void setIdRestaurante(Long idRestaurante) {
		this.idRestaurante = idRestaurante;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getPrecios() {
		return precios;
	}

	public void setPrecios(String precios) {
		this.precios = precios;
	}

	public String getFechas() {
		return fechas;
	}

	public void setFechas(String fechas) {
		this.fechas = fechas;
	}

}
