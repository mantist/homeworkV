package com.homework.calculators;

import com.homework.TrxLine;
import com.homework.calculators.PriceCalculator;
import com.homework.constants.Props;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Properties;

import static org.junit.Assert.assertEquals;


class PriceCalculatorTest {

  @Test
  void calculatePrice() {
    // This should be considered as integration test checking whole
    // flow of line pricing

    Properties test_props = new Properties();

    test_props.setProperty(Props.TOT_MONTH_DISC_AMT, "5.55");
    test_props.setProperty(Props.LP_L_MONTH_FREE_NTH, "2");
    test_props.setProperty(Props.LP_L_PRICE, "6");

    PriceCalculator pc = new PriceCalculator(test_props);

    BigDecimal full_price =
            new BigDecimal(test_props.getProperty(Props.LP_L_PRICE));
    BigDecimal month_limit =
            new BigDecimal(test_props.getProperty(Props.TOT_MONTH_DISC_AMT));

    TrxLine tl = new TrxLine("2018-04-29 L LP");
    pc.calculatePrice(tl);
    assertEquals("1 st. L LP in month price before discount",
                  full_price,
                  tl.getPrice());
    assertEquals("1 st. L LP in month discount",
                  new BigDecimal("0"),
                  tl.getDiscount());

    tl = new TrxLine("2018-04-29 L LP");
    pc.calculatePrice(tl);
    assertEquals("2 nd. L LP in month price before discount",
                  full_price,
                  tl.getPrice());
    assertEquals("2 nd. L LP in month discount",
                  month_limit,
                  tl.getDiscount());
  }
}