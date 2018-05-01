package com.homework.calculators;

import com.homework.TrxLine;
import com.homework.constants.Props;

import java.math.BigDecimal;
import java.util.Properties;

public class MRCalculator extends AbstractCalculator {

    public MRCalculator(Properties props) {

        super(props);
    }

    @Override
    public TrxLine calcDiscount(TrxLine trx_line) {

        //No discounts for MR yet

        return trx_line;
    }

    @Override
    public TrxLine calcPrice(TrxLine trx_line) {
        switch(trx_line.getSize()){
            case S: trx_line.setPrice(new BigDecimal(props.getProperty(Props.MR_S_PRICE)));
                break;
            case M: trx_line.setPrice(new BigDecimal(props.getProperty(Props.MR_M_PRICE)));
                break;
            case L: trx_line.setPrice(new BigDecimal(props.getProperty(Props.MR_L_PRICE)));
                break;
        }
        return(trx_line);
    }
}
