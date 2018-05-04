package com.homework.calculators;

import com.homework.TrxLine;
import com.homework.constants.Props;
import com.homework.constants.Size;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * LP specific calculator. Please keep simple rules when extending this class:
 * <ul>
 * <li>Each rule (price or discount) must be implemented by separate method
 * <li>Each rule (price or discount) should not interfere with other rules
 * <li>Method of each rule must be called by calcDiscount or calcPrice
 * </ul>
 * Possible improvement of design could be creating separate class for each
 * rule (sometimes good ideas come too late ;))
 */
public class LPCalculator extends AbstractCalculator {

  private DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM");
  private Map<String, Integer> mnth_lp_ship_cnt = new HashMap<>();
  private int LP_L_MONTH_FREE_NTH_VAL = -1;

  public LPCalculator(Properties props) {
    super(props);
  }

  @Override
  public TrxLine calcDiscount(TrxLine trx_line) {

    applyNthLFree(trx_line);

    return trx_line;
  }

  @Override
  public TrxLine calcPrice(TrxLine trx_line) {

    switch (trx_line.getSize()) {
      case S:
        trx_line.setPrice(new BigDecimal(props.getProperty(Props.LP_S_PRICE)));
        break;
      case M:
        trx_line.setPrice(new BigDecimal(props.getProperty(Props.LP_M_PRICE)));
        break;
      case L:
        trx_line.setPrice(new BigDecimal(props.getProperty(Props.LP_L_PRICE)));
        break;
    }

    return (trx_line);
  }

  /**
   * Rule for making N-th L size transaction free.
   * @param trx_line transaction line that needs to be evaluated
   */
  protected void applyNthLFree(TrxLine trx_line) {

    if (trx_line.getSize() != Size.L) return;

    if (LP_L_MONTH_FREE_NTH_VAL < 0) {
      LP_L_MONTH_FREE_NTH_VAL =
              Integer.parseInt(props.getProperty(Props.LP_L_MONTH_FREE_NTH));
    }

    String year_month = trx_line.getTrxDt().format(df);
    int mnth_ship_cnt = mnth_lp_ship_cnt.getOrDefault(year_month, 0);

    mnth_ship_cnt += 1;
    mnth_lp_ship_cnt.put(year_month, mnth_ship_cnt);

    if (mnth_ship_cnt == LP_L_MONTH_FREE_NTH_VAL) {
      trx_line.addDiscount(trx_line.getPrice());
    }
  }
}
