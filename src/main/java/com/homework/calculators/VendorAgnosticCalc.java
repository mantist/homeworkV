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
 * Calculator for discount that can be applied to any vendor. Please keep in
 * mind simple rules when extending this class:
 * <ul>
 * <li>Each rule (price or discount) must be implemented by separate method
 * <li>Each rule (price or discount) should not interfere with other rules
 * <li>Method of each rule must be called by calcDiscount or calcPrice
 * </ul>
 */
public class VendorAgnosticCalc extends AbstractCalculator {

  private Map<String, BigDecimal> mnth_disc_sum = new HashMap<>();
  private final BigDecimal MONTH_DISC_SUM_LIM_VAL;
  private BigDecimal ALL_S_PRICE_VAL;
  private DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM");

  public VendorAgnosticCalc(Properties props) {
    super(props);

    MONTH_DISC_SUM_LIM_VAL =
                    new BigDecimal(props.getProperty(Props.TOT_MONTH_DISC_AMT));
  }

  @Override
  public TrxLine calcPrice(TrxLine trx_line) {

    // No price info
    return (trx_line);
  }

  @Override
  public TrxLine calcDiscount(TrxLine trx_line) {

    applyOneSPriceRule(trx_line);
    applyMonthDiscountLimitRule(trx_line);

    return (trx_line);
  }

  /**
   * Rule for ensuring that all S size transactions will have the same price
   * @param trx_line transaction line that needs to be evaluated
   * @return         transaction line updated with discount
   */
  protected TrxLine applyOneSPriceRule(TrxLine trx_line) {

    if (trx_line.getSize() != Size.S) return (trx_line);

    if (ALL_S_PRICE_VAL == null) {
      BigDecimal mr_s_price =
                            new BigDecimal(props.getProperty(Props.MR_S_PRICE));
      BigDecimal lp_s_price =
                            new BigDecimal(props.getProperty(Props.LP_S_PRICE));
      ALL_S_PRICE_VAL = mr_s_price.min(lp_s_price);
    }

    BigDecimal line_price = trx_line.getPrice();

    if (line_price.compareTo(ALL_S_PRICE_VAL) == 0) return (trx_line);

    BigDecimal disc_amt = trx_line.getPrice().subtract(ALL_S_PRICE_VAL);
    trx_line.addDiscount(disc_amt);

    return trx_line;
  }

  /**
   * Rule for ensuring that total amount of discount in a month will not exceed
   * the limit. It always must be applied last!
   * @param trx_line transaction line that needs to be evaluated
   * @return         transaction line updated with discount
   */
  protected TrxLine applyMonthDiscountLimitRule(TrxLine trx_line) {

    BigDecimal disc_amt_avail;
    BigDecimal tmp_disc_amt = trx_line.getEffectiveDiscount();
    String year_month = trx_line.getTrxDt().format(df);

    if (tmp_disc_amt.compareTo(BigDecimal.ZERO) == 0) return (trx_line);

    BigDecimal tmp_month_disc_amt =
            mnth_disc_sum.computeIfAbsent(year_month,
                                          k -> BigDecimal.valueOf(0, 0));
    disc_amt_avail = MONTH_DISC_SUM_LIM_VAL.subtract(tmp_month_disc_amt);

    if (disc_amt_avail.compareTo(tmp_disc_amt) == 1) {
      trx_line.setDiscount(tmp_disc_amt);
      tmp_month_disc_amt = tmp_month_disc_amt.add(tmp_disc_amt);
      mnth_disc_sum.put(year_month, tmp_month_disc_amt);
    } else {
      trx_line.setDiscount(disc_amt_avail);
      mnth_disc_sum.put(year_month, MONTH_DISC_SUM_LIM_VAL);
    }

    return trx_line;
  }
}
