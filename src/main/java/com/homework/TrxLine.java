package com.homework;

import com.homework.constants.Vendors;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class TrxLine {

    public enum Size{S, M, L}

    private LocalDate trx_dt;
    private Size size;
    private Vendors vendor;
    private boolean is_valid;
    private BigDecimal price    = BigDecimal.valueOf(0); //Before discount
    private BigDecimal discount = BigDecimal.valueOf(0);

    private final String delimiter = " ";
    private final String originalLine;

    public TrxLine(String s){

        originalLine = s;

        String[] elements = s.split(delimiter);

        is_valid = true;

        if (elements.length != 3){
            is_valid = false;
            return;
        }

        try{
            trx_dt = LocalDate.parse(elements[0]);
        }catch(DateTimeParseException e) {
            is_valid = false;
            return;
        }

        try{
            this.size = Size.valueOf(elements[1]);
        }catch (IllegalArgumentException e){
            is_valid = false;
            return;
        }

        try{
            this.vendor = Vendors.valueOf(elements[2]);
        }catch (IllegalArgumentException e){
            is_valid = false;
            return;
        }
    }

    public boolean isValid(){

        return is_valid;
    }

    public LocalDate getTrxDt(){

        return trx_dt;
    }

    public Size getSize(){

        return size;
    }

    public Vendors getVendor(){

        return vendor;
    }

    public BigDecimal getPrice(){

        return price;
    }

    public BigDecimal getFullPrice(){

        BigDecimal fullPrice = price.subtract(discount);
        fullPrice = fullPrice.max(BigDecimal.ZERO);

        return fullPrice;
    }

    public void setPrice(BigDecimal price){
        this.price = price;
    }

    public BigDecimal getDiscount(){

        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

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

        if (this.is_valid){
            full_line = String.join(delimiter, originalLine, df.format(getFullPrice()));
            if(discount.compareTo(BigDecimal.ZERO) > 0){
                full_line = String.join(delimiter, full_line, df.format(discount));
            }else{
                full_line = String.join(delimiter, full_line, "-");
            }
        }
        else {
            full_line = String.join(delimiter, originalLine, "Ignored");
        }
        return full_line;
    }
}
