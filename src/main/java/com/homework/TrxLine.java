package com.homework;

import com.homework.constants.Size;
import com.homework.constants.Vendors;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Class for encapsulating all general knowledge about the line.
 */
public class TrxLine {

  private final String delimiter = " ";
  private final String originalLine;
  private LocalDate trx_dt;
  private Size size;
  private Vendors vendor;
  private boolean is_valid;
  private BigDecimal price = BigDecimal.valueOf(0); //Before discount
  private BigDecimal discount = BigDecimal.valueOf(0);

  public TrxLine(String s) {

    originalLine = s;

    String[] elements = s.split(delimiter);

    is_valid = true;

    if (elements.length != 3) {
      is_valid = false;
      return;
    }

    try {
      trx_dt = LocalDate.parse(elements[0]);
    } catch (DateTimeParseException e) {
      is_valid = false;
      return;
    }

    try {
      this.size = Size.valueOf(elements[1]);
    } catch (IllegalArgumentException e) {
      is_valid = false;
      return;
    }

    try {
      this.vendor = Vendors.valueOf(elements[2]);
    } catch (IllegalArgumentException e) {
      is_valid = false;
      return;
    }
  }

  public boolean isValid() {

    return is_valid;
  }

  public LocalDate getTrxDt() {

    return trx_dt;
  }

  public Size getSize() {

    return size;
  }

  public Vendors getVendor() {

    return vendor;
  }

  /**
   * @return price of line without discount amount deducted
   */
  public BigDecimal getPrice() {

    return price;
  }

  public void setPrice(BigDecimal price) {

    this.price = price;
  }

  /**
   * @return price of the line deducted by discount
   */
  public BigDecimal getEffectivePrice() {

    BigDecimal effective_price = price.subtract(discount);
    effective_price = effective_price.max(BigDecimal.ZERO);

    return effective_price;
  }

  /**
   * @return Whole amount of discount that was calculated for the line. Warning
   *         it can exceed price of the line!
   */
  public BigDecimal getDiscount() {

    return discount;
  }

  /**
   * @return Effective amount of discount. It can't be larger than line price.
   */
  public BigDecimal getEffectiveDiscount() {

    BigDecimal effective_discount = discount.min(price);

    return effective_discount;
  }

  /**
   * Overwrites amount of discount with input
   * @param discount New amount of discount
   */
  public void setDiscount(BigDecimal discount) {
    this.discount = discount;
  }

  /**
   * Adds amount to total discount
   * @param discount Amount to add
   */
  public void addDiscount(BigDecimal discount) {
    this.discount = this.discount.add(discount);
  }

  @Override
  public String toString() {

    String full_line;
    DecimalFormatSymbols other_symbols = new DecimalFormatSymbols();

    other_symbols.setDecimalSeparator('.');
    other_symbols.setGroupingSeparator(' ');
    DecimalFormat df = new DecimalFormat("0.00", other_symbols);

    if (this.is_valid) {
      full_line = String.join(delimiter,
                              originalLine,
                              df.format(getEffectivePrice()));
      if (discount.compareTo(BigDecimal.ZERO) > 0) {
        full_line = String.join(delimiter, full_line, df.format(discount));
      } else {
        full_line = String.join(delimiter, full_line, "-");
      }
    } else {
      full_line = String.join(delimiter, originalLine, "Ignored");
    }
    return full_line;
  }
}
