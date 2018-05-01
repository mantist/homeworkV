import com.homework.TrxLine;
import com.homework.calculators.LPCalculator;
import com.homework.constants.Props;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

class LPCalculatorTest {

    private LPCalculator lp_calc;
    private Properties test_props;

    @BeforeEach
    void initCalc(){
        test_props = new Properties();
        test_props.setProperty(Props.LP_L_MONTH_FREE_NTH, "2");

        lp_calc = new LPCalculator(test_props);
    }

    @Test
    void calcSPrice() {

        test_props.setProperty(Props.LP_S_PRICE, "2");
        BigDecimal price = new BigDecimal(test_props.getProperty(Props.LP_S_PRICE));

        TrxLine tl = new TrxLine("2018-04-29 S LP");
        lp_calc.calcPrice(tl);
        assertEquals("LP S price", price, tl.getFullPrice());
    }

    @Test
    void calcMPrice() {

        test_props.setProperty(Props.LP_M_PRICE, "3");
        BigDecimal price = new BigDecimal(test_props.getProperty(Props.LP_M_PRICE));

        TrxLine tl = new TrxLine("2018-04-29 M LP");
        lp_calc.calcPrice(tl);
        assertEquals("LP M price", price, tl.getFullPrice());
    }

    @Test
    void calcLPrice() {

        test_props.setProperty(Props.LP_L_PRICE, "4");
        BigDecimal price = new BigDecimal(test_props.getProperty(Props.LP_L_PRICE));

        TrxLine tl = new TrxLine("2018-04-29 L LP");
        lp_calc.calcPrice(tl);
        assertEquals("LP L price", price, tl.getFullPrice());
    }

    @Test
    void applyNthLFree(){

        test_props.setProperty(Props.LP_L_MONTH_FREE_NTH, "2");

        BigDecimal lp_l_full = new BigDecimal("2");
        BigDecimal lp_m_full = new BigDecimal("1");

        TrxLine tl = new TrxLine("2018-04-29 L LP");
        tl.setPrice(lp_l_full);
        lp_calc.calcDiscount(tl);
        assertEquals("1 st. L LP delivery in 2018-04", lp_l_full, tl.getFullPrice());

        tl = new TrxLine("2017-04-29 L LP");
        tl.setPrice(lp_l_full);
        lp_calc.calcDiscount(tl);
        assertEquals("1 st. L LP delivery in 2017-04", lp_l_full, tl.getFullPrice());

        tl = new TrxLine("2018-04-29 M LP");
        tl.setPrice(lp_m_full);
        lp_calc.calcDiscount(tl);
        assertEquals("1 st. M LP delivery in 2018-04", lp_m_full, tl.getFullPrice());

        tl = new TrxLine("2018-04-28 L LP");
        tl.setPrice(lp_l_full);
        lp_calc.calcDiscount(tl);
        assertEquals("2 nd. L LP delivery in 2018-04", BigDecimal.ZERO, tl.getFullPrice());

        tl = new TrxLine("2017-04-27 L LP");
        tl.setPrice(lp_l_full);
        lp_calc.calcDiscount(tl);
        assertEquals("2 nd. L LP delivery in 2017-04", BigDecimal.ZERO, tl.getFullPrice());

        tl = new TrxLine("2018-04-27 M LP");
        tl.setPrice(lp_m_full);
        lp_calc.calcDiscount(tl);
        assertEquals("2 nd. M LP delivery in 2018-04", lp_m_full, tl.getFullPrice());

        tl = new TrxLine("2018-04-28 L LP");
        tl.setPrice(lp_l_full);
        lp_calc.calcDiscount(tl);
        assertEquals("3 rd. L LP delivery in 2018-04", lp_l_full, tl.getFullPrice());
    }
}