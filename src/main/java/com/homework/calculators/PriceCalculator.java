package com.homework.calculators;

import com.homework.TrxLine;

import java.util.Properties;


public class PriceCalculator {

    private Properties prop;

    private AbstractCalculator vendor_agnostic;

    public PriceCalculator(Properties prop) {

        this.prop = prop;

        //For jUnit tests
        CalcBuilder.refresh();

        vendor_agnostic = new VendorAgnosticCalc(prop);
    }

    public TrxLine calculatePrice(TrxLine trx_line) {

        if (!trx_line.isValid()) return(trx_line);

        AbstractCalculator vendor_specific =
                CalcBuilder.getVendorSpecific(trx_line.getVendor(), prop);

        vendor_specific.calcPrice(trx_line);
        vendor_specific.calcDiscount(trx_line);

        vendor_agnostic.calcDiscount(trx_line);

        return(trx_line);
    }


}
