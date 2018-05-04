package com.homework.calculators;

import com.homework.TrxLine;
import com.homework.constants.Props;

import java.math.BigDecimal;
import java.util.Properties;

/**
 * MR specific calculator. Please keep simple rules when extending this class:
 * <ul>
 * <li>Each rule (price or discount) must be implemented by separate method
 * <li>Each rule (price or discount) should not interfere with other rules
 * <li>Method of each rule must be called by calcDiscount or calcPrice
 * </ul>
 * Possible improvement of design could be creating separate class for each
 * rule (sometimes good ideas come too late ;))
 */
public class MRCalculator extends AbstractCalculator {

  public MRCalculator(Properties props) {

    super(props);
  }

  @Override
  public TrxLine calcDiscount(TrxLine trx_line) {

    //No discounts for MR yet

    return trx_line;
  }

  @Override
  public TrxLine calcPrice(TrxLine trx_line) {

    switch (trx_line.getSize()) {
      case S:
        trx_line.setPrice(new BigDecimal(props.getProperty(Props.MR_S_PRICE)));
        break;
      case M:
        trx_line.setPrice(new BigDecimal(props.getProperty(Props.MR_M_PRICE)));
        break;
      case L:
        trx_line.setPrice(new BigDecimal(props.getProperty(Props.MR_L_PRICE)));
        break;
    }
    return (trx_line);
  }
}
