package vos;

import java.time.LocalTime;

import org.codehaus.jackson.annotate.JsonProperty;

public class ConsultaRentabilidadLocal {

	//// Atributos

	/**
	 * Nombre del usuario
	 */
	@JsonProperty(value = "perdidasTotales")
	private Long perdidasTotales;

	@JsonProperty(value = "gananciasTotales")
	private Long gananciasTotales;
	/**
	 * Identificador del usuario
	 */
	@JsonProperty(value = "balanceTotal")
	private Long balanceTotal;

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
	public ConsultaRentabilidadLocal(@JsonProperty(value = "perdidasTotales") Long perdidasTotales,
			@JsonProperty(value = "gananciasTotales") Long gananciasTotales,
			@JsonProperty(value = "balanceTotal") Long balanceTotal) {
		this.perdidasTotales = perdidasTotales;
		this.gananciasTotales = gananciasTotales;
		this.balanceTotal = balanceTotal;
	}

	public Long getPerdidasTotales() {
		return perdidasTotales;
	}

	public void setPerdidasTotales(Long perdidasTotales) {
		this.perdidasTotales = perdidasTotales;
	}

	public Long getGananciasTotales() {
		return gananciasTotales;
	}

	public void setGananciasTotales(Long gananciasTotales) {
		this.gananciasTotales = gananciasTotales;
	}

	public Long getBalanceTotal() {
		return balanceTotal;
	}

	public void setBalanceTotal(Long balanceTotal) {
		this.balanceTotal = balanceTotal;
	}

}
