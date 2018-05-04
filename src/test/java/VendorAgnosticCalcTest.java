package com.homework.calculators;

import com.homework.TrxLine;
import com.homework.calculators.VendorAgnosticCalc;
import com.homework.constants.Props;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

class VendorAgnosticCalcTest {

  VendorAgnosticCalc va_calc;
  private Properties test_props;

  @BeforeEach
  void initCalc() {

    test_props = new Properties();
    test_props.setProperty(Props.TOT_MONTH_DISC_AMT, "5.55");
    test_props.setProperty(Props.MR_S_PRICE, "1");
    test_props.setProperty(Props.LP_S_PRICE, "5");

    va_calc = new VendorAgnosticCalc(test_props);
  }

  @Test
  void applyOneSPriceRule() {

    BigDecimal min_s_price =
            new BigDecimal(test_props.getProperty(Props.MR_S_PRICE));
    BigDecimal lp_s_price =
            new BigDecimal(test_props.getProperty(Props.LP_S_PRICE));

    TrxLine tl = new TrxLine("2018-04-29 S LP");
    tl.setPrice(lp_s_price);
    va_calc.applyOneSPriceRule(tl);
    assertEquals("LP S discount",
                  new BigDecimal("4"),
                  tl.getDiscount());

    tl = new TrxLine("2018-04-29 S MR");
    tl.setPrice(min_s_price);
    va_calc.applyOneSPriceRule(tl);
    assertEquals("MR S no discount",
                  new BigDecimal("0"),
                  tl.getDiscount());
  }

  @Test
  void applyMonthDiscountLimitRule() {

    TrxLine tl = new TrxLine("2018-04-29 S MR");
    tl.setPrice(new BigDecimal("5"));
    tl.setDiscount(new BigDecimal("5"));

    va_calc.applyMonthDiscountLimitRule(tl);
    assertEquals("Discount bellow limit in 2018-04",
                  new BigDecimal("5"),
                  tl.getDiscount());

    tl = new TrxLine("2018-04-29 M MR");
    tl.setPrice(new BigDecimal("5"));
    tl.setDiscount(new BigDecimal("5"));

    va_calc.applyMonthDiscountLimitRule(tl);
    assertEquals("Discount hit limit in 2018-04",
                  new BigDecimal("0.55"),
                  tl.getDiscount());

    tl = new TrxLine("2018-04-29 L MR");
    tl.setPrice(new BigDecimal("5"));
    tl.setDiscount(new BigDecimal("5"));

    va_calc.applyMonthDiscountLimitRule(tl);
    assertEquals("No more discounts for 2018-04",
                  new BigDecimal("0.00"),
                  tl.getDiscount());

    tl = new TrxLine("2018-05-01 L MR");
    tl.setPrice(new BigDecimal("5"));
    tl.setDiscount(new BigDecimal("5"));

    va_calc.applyMonthDiscountLimitRule(tl);
    assertEquals("Discount bellow limit in 2018-05",
                  new BigDecimal("5"),
                  tl.getDiscount());
  }
}