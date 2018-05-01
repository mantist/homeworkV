package com.homework.calculators;

import com.homework.constants.Vendors;

import java.util.HashMap;
import java.util.Properties;

public class CalcBuilder {
    private static CalcBuilder ourInstance = new CalcBuilder();

    private static HashMap<Vendors, AbstractCalculator> vendor_calcs =
            new HashMap<Vendors, AbstractCalculator>();

    private CalcBuilder() {
    }

    public static AbstractCalculator getVendorSpecific(Vendors vendor,
                                                       Properties props)
    {
        AbstractCalculator calculator;

        calculator = vendor_calcs.get(vendor);
        if (calculator == null){
            calculator = ourInstance.createVendorCalc(vendor, props);
            vendor_calcs.put(vendor, calculator);
        }

        return calculator;
    }

    private AbstractCalculator createVendorCalc(Vendors vendor, Properties props) {
        switch(vendor){
            case MR: return new MRCalculator(props);
            case LP: return new LPCalculator(props);
            default: return null;
        }
    }

    public static void refresh(){
        vendor_calcs.clear();
    }

}
