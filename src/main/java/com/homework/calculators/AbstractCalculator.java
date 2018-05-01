package com.homework.calculators;

import com.homework.TrxLine;

import java.util.Properties;

public abstract class AbstractCalculator {

    protected Properties props;

    public AbstractCalculator(Properties props) {
        this.props = props;
    }

    public abstract TrxLine calcPrice(TrxLine trx_line);
    public abstract TrxLine calcDiscount(TrxLine trx_line);
}
