package org.anz.fx.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author amitkumar.wali
 *
 */
public class QueryOutput extends Query {

	private BigDecimal toAmount;
	private int precision;

	
	/**
	 * @param query
	 * @param toAmount
	 */
	public QueryOutput(Query query, BigDecimal toAmount, int precision) {
		super(query.getFromCurrency(), query.getFromAmount(), query.getToCurrency());
		this.toAmount = toAmount;
		this.precision = precision;
	}
	
	/**
	 * @return the toAmount
	 */
	public BigDecimal getToAmount() {
		return toAmount;
	}

	/**
	 * @param toAmount the toAmount to set
	 */
	public void setToAmount(BigDecimal toAmount) {
		this.toAmount = toAmount;
	}

	/**
	 * @return the precision
	 */
	public int getPrecision() {
		return precision;
	}
	
	
	/**
	 * @param precision the precision to set
	 */
	public void setPrecision(int precision) {
		this.precision = precision;
	}

	/**
	 *
	 */
	@Override
	public String toString() {
		return getFromCurrency() + " " 
				+ getFromAmount().setScale(getPrecision(), RoundingMode.HALF_UP) + " = " 
				+ getToCurrency() + " " 
				+ getToAmount().setScale(getPrecision(), RoundingMode.HALF_UP);
	}

}
