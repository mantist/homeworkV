package com.homework.calculators;

import com.homework.TrxLine;
import com.homework.calculators.MRCalculator;
import com.homework.constants.Props;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

class MRCalculatorTest {

  private MRCalculator mr_calc;
  private Properties testProps;

  @BeforeEach
  void initCalc() {

    testProps = new Properties();
    mr_calc = new MRCalculator(testProps);
  }

  @Test
  void calcSPrice() {

    testProps.setProperty(Props.MR_S_PRICE, "2");
    BigDecimal price = new BigDecimal(testProps.getProperty(Props.MR_S_PRICE));

    TrxLine tl = new TrxLine("2018-04-29 S MR");
    mr_calc.calcPrice(tl);
    assertEquals("MR S price", price, tl.getEffectivePrice());
  }

  @Test
  void calcMPrice() {

    testProps.setProperty(Props.MR_M_PRICE, "3");
    BigDecimal price = new BigDecimal(testProps.getProperty(Props.MR_M_PRICE));

    TrxLine tl = new TrxLine("2018-04-29 M MR");
    mr_calc.calcPrice(tl);
    assertEquals("MR M price", price, tl.getEffectivePrice());
  }

  @Test
  void calcLPrice() {

    testProps.setProperty(Props.MR_L_PRICE, "4");
    BigDecimal price = new BigDecimal(testProps.getProperty(Props.MR_L_PRICE));

    TrxLine tl = new TrxLine("2018-04-29 L MR");
    mr_calc.calcPrice(tl);
    assertEquals("MR L price", price, tl.getEffectivePrice());
  }

}