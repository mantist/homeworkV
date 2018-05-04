package com.homework.calculators;

import com.homework.TrxLine;

import java.util.Properties;


public class PriceCalculator {

  private Properties prop;

  private AbstractCalculator vendor_agnostic;

  /**
   * Class for managing processing of each line. This class should contain
   * only general transaction processing logic. Like creating specific
   * calculator, calling general pricing methods in determined order:
   *  <ul>
   *  <li>calculate price
   *  <li>calculate vendor specific discount
   *  <li>calculate vendor agnostic discount
   *  </ul>
   * @param prop pricing configuration
   */
  public PriceCalculator(Properties prop) {

    this.prop = prop;
    //For jUnit tests
    CalcBuilder.refresh();

    vendor_agnostic = new VendorAgnosticCalc(prop);
  }

  public TrxLine calculatePrice(TrxLine trx_line) {

    if (!trx_line.isValid()) return (trx_line);

    AbstractCalculator vendor_specific =
            CalcBuilder.getVendorSpecific(trx_line.getVendor(), prop);

    vendor_specific.calcPrice(trx_line);
    vendor_specific.calcDiscount(trx_line);

    vendor_agnostic.calcDiscount(trx_line);

    return (trx_line);
  }
}
