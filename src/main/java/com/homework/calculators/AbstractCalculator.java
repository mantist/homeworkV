package com.homework.calculators;

import com.homework.TrxLine;

import java.util.Properties;

public abstract class AbstractCalculator {

  protected Properties props;

  public AbstractCalculator(Properties props) {
    this.props = props;
  }

  /**
   * Calculates transaction line price. Only this method must be called
   * for price calculation by PriceCalculator!
   * @param trx_line line for which price must be calculated
   * @return         trx_line updated with price
   */
  public abstract TrxLine calcPrice(TrxLine trx_line);

  /**
   * Calculates transaction line discount. Only this method must be
   * called for price calculation by PriceCalculator!
   * @param trx_line line for which discount must be calculated
   * @return         trx_line updated with discount
   */
  public abstract TrxLine calcDiscount(TrxLine trx_line);
}
