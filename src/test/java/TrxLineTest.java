import com.homework.TrxLine;
import com.homework.constants.Vendors;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.*;

class TrxLineTest {

    @Test
    void parseValidTransactionLine() {
        TrxLine testLine = new TrxLine("2015-02-01 S MR");

        assertEquals("Valid line size", TrxLine.Size.S, testLine.getSize());
        assertEquals("Valid line date", LocalDate.parse("2015-02-01"), testLine.getTrxDt());
        assertEquals("Valid line vendor", Vendors.MR, testLine.getVendor());
        assertTrue("Valid line status", testLine.isValid());
    }

    @Test
    void parseInvalidLine() {
        TrxLine testLine = new TrxLine("2015-02-01SRM");

        assertFalse("Invalid line (incorrect structure) status", testLine.isValid());
    }

    @Test
    void parseInvalidProvider() {
        TrxLine testLine = new TrxLine("2015-02-01 S RM");

        assertFalse("Invalid line (unknown vendor) status", testLine.isValid());
    }

    @Test
    void parseInvalidSize() {
        TrxLine testLine = new TrxLine("2015-02-01 X MR");

        assertFalse("Invalid line (unknown size) status", testLine.isValid());
    }

    @Test
    void parseInvalidDate() {
        TrxLine testLine = new TrxLine("9999-99-99 S MR");

        assertFalse("Invalid line (wrong date) status", testLine.isValid());
    }

    @Test
    void getPriceAndDiscount() {
        TrxLine testLine = new TrxLine("2015-02-01 S MR");

        testLine.setPrice(new BigDecimal("1.97"));
        testLine.setDiscount(new BigDecimal("0.08"));
        assertEquals("Price before discount", new BigDecimal("1.97"), testLine.getPrice());
        assertEquals("Price after discount", new BigDecimal("1.89"), testLine.getFullPrice());
        assertEquals("Discount", new BigDecimal("0.08"), testLine.getDiscount());
    }

    @Test
    void toStringInvalidLine() {
        String str = "2015-02-01SRM";
        TrxLine testLine = new TrxLine(str);

        assertEquals("Invalid line output", str.concat(" Ignored"), testLine.toString());
    }

    @Test
    void toStringNoDiscountLine() {
        String str = "2015-02-01 S MR";
        TrxLine testLine = new TrxLine(str);

        testLine.setPrice(new BigDecimal("1"));

        assertEquals("No discount line output", str.concat(" 1.00 -"), testLine.toString());
    }

    @Test
    void toStringDiscountLine() {
        String str = "2015-02-01 S MR";
        TrxLine testLine = new TrxLine(str);

        testLine.setPrice(new BigDecimal("2"));
        testLine.setDiscount(new BigDecimal("2"));

        assertEquals("No discount line output", str.concat(" 0.00 2.00"), testLine.toString());
    }
}