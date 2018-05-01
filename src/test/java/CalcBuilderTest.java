import com.homework.calculators.AbstractCalculator;
import com.homework.calculators.CalcBuilder;
import com.homework.calculators.LPCalculator;
import com.homework.calculators.MRCalculator;
import com.homework.constants.Vendors;
import org.junit.jupiter.api.Test;

import static junit.framework.TestCase.assertTrue;

class CalcBuilderTest {

    @Test
    void getVendorSpecific() {

        AbstractCalculator ac = CalcBuilder.getVendorSpecific(Vendors.MR, null);
        assertTrue("MR calculator instance", (ac instanceof MRCalculator));

        ac = CalcBuilder.getVendorSpecific(Vendors.LP, null);
        assertTrue("LP calculator instance", (ac instanceof LPCalculator));
    }
}