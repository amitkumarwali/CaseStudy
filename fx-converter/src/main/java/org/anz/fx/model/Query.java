package org.anz.fx.model;

import java.math.BigDecimal;

import org.anz.fx.model.prototype.QueryPrototype;

/**
 * @author amitkumar.wali
 *
 */
public class Query implements QueryPrototype {

	private String fromCurrency;
	private String toCurrency;
	private BigDecimal fromAmount;
	
	/**
	 * @param fromCurrency
	 * @param toCurrency
	 * @param fromAmount
	 */
	public Query(String fromCurrency, BigDecimal fromAmount, String toCurrency) {
		this.fromCurrency = fromCurrency.toUpperCase();
		this.toCurrency = toCurrency.toUpperCase();
		this.fromAmount = fromAmount;
	}

	/**
	 * @return the fromCurrency
	 */
	public String getFromCurrency() {
		return fromCurrency;
	}

	/**
	 * @param fromCurrency the fromCurrency to set
	 */
	public void setFromCurrency(String fromCurrency) {
		this.fromCurrency = fromCurrency;
	}

	/**
	 * @return the toCurrency
	 */
	public String getToCurrency() {
		return toCurrency;
	}

	/**
	 * @param toCurrency the toCurrency to set
	 */
	public void setToCurrency(String toCurrency) {
		this.toCurrency = toCurrency;
	}

	/**
	 * @return the fromAmount
	 */
	public BigDecimal getFromAmount() {
		return fromAmount;
	}

	/**
	 * @param fromAmount the fromAmount to set
	 */
	public void setFromAmount(BigDecimal fromAmount) {
		this.fromAmount = fromAmount;
	}

	/**
	 *
	 */
	@Override
	public String toString() {
		return "Query [fromCurrency=" + fromCurrency 
						+ ", toCurrency=" + toCurrency 
						+ ", fromAmount=" + fromAmount	+ "]";
	}
	
	/**
	 *
	 */
	@Override
	public QueryPrototype getClone() {
		return new Query(this.fromCurrency, this.fromAmount, this.toCurrency);
	}

}
