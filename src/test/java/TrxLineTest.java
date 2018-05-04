package com.homework;

import com.homework.TrxLine;
import com.homework.constants.Size;
import com.homework.constants.Vendors;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.*;

class TrxLineTest {

  @Test
  void parseValidTransactionLine() {

    TrxLine tl = new TrxLine("2015-02-01 S MR");

    assertEquals("Valid line size", Size.S, tl.getSize());
    assertEquals("Valid line date",
                  LocalDate.parse("2015-02-01"),
                  tl.getTrxDt());
    assertEquals("Valid line vendor", Vendors.MR, tl.getVendor());
    assertTrue("Valid line status", tl.isValid());
  }

  @Test
  void parseInvalidLine() {

    TrxLine tl = new TrxLine("2015-02-01SRM");

    assertFalse("Invalid line (incorrect structure) status",
                tl.isValid());
  }

  @Test
  void parseInvalidProvider() {

    TrxLine tl = new TrxLine("2015-02-01 S RM");

    assertFalse("Invalid line (unknown vendor) status",
                tl.isValid());
  }

  @Test
  void parseInvalidSize() {

    TrxLine tl = new TrxLine("2015-02-01 X MR");

    assertFalse("Invalid line (unknown size) status", tl.isValid());
  }

  @Test
  void parseInvalidDate() {

    TrxLine tl = new TrxLine("9999-99-99 S MR");

    assertFalse("Invalid line (wrong date) status", tl.isValid());
  }

  @Test
  void getPriceAndDiscount() {

    TrxLine tl = new TrxLine("2015-02-01 S MR");

    tl.setPrice(new BigDecimal("1.97"));
    tl.setDiscount(new BigDecimal("0.08"));
    assertEquals("Price before discount",
                  new BigDecimal("1.97"),
                  tl.getPrice());
    assertEquals("Price after discount",
                  new BigDecimal("1.89"),
                  tl.getEffectivePrice());
    assertEquals("Discount",
                  new BigDecimal("0.08"),
                  tl.getDiscount());
    assertEquals("Effective discount bellow price",
                  new BigDecimal("0.08"),
                  tl.getEffectiveDiscount());

    tl.addDiscount(new BigDecimal("1.90"));
    assertEquals("Discount after addition",
                  new BigDecimal("1.98"),
                  tl.getDiscount());
    assertEquals("Effective discount equal to price",
                  tl.getPrice(),
                  tl.getEffectiveDiscount());
  }

  @Test
  void toStringInvalidLine() {

    String str = "2015-02-01SRM";
    TrxLine tl = new TrxLine(str);

    assertEquals("Invalid line output",
                  str.concat(" Ignored"),
                  tl.toString());
  }

  @Test
  void toStringNoDiscountLine() {

    String str = "2015-02-01 S MR";
    TrxLine tl = new TrxLine(str);

    tl.setPrice(new BigDecimal("1"));

    assertEquals("No discount line output",
                  str.concat(" 1.00 -"),
                  tl.toString());
  }

  @Test
  void toStringDiscountLine() {

    String str = "2015-02-01 S MR";
    TrxLine tl = new TrxLine(str);

    tl.setPrice(new BigDecimal("2"));
    tl.setDiscount(new BigDecimal("2"));

    assertEquals("No discount line output",
                  str.concat(" 0.00 2.00"),
                  tl.toString());
  }
}