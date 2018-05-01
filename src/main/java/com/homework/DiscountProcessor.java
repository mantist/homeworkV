package com.homework;

import com.homework.calculators.PriceCalculator;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class DiscountProcessor {

    private PriceCalculator price_calculator;

    public static void main(String[] args) {

        DiscountProcessor processor = new DiscountProcessor();

        Path path = Paths.get(args[0]);

        try {
            Files.lines(path).forEach(
                    processor::processLine
            );
        } catch (IOException e) {
            System.err.println("Error: Input file cannot be read");
            e.printStackTrace();
        }
    }

    public DiscountProcessor() {
        Properties props = getProps();
        price_calculator = new PriceCalculator(props);
    }

    private void processLine(String s){

        try {
            TrxLine trx_Line = new TrxLine(s);
            trx_Line = price_calculator.calculatePrice(trx_Line);
            System.out.println(trx_Line);
        } catch (Exception e) {
            System.err.println("Unknown error on processing line '" + s + "'");
            e.printStackTrace();
            System.exit(1);
        }
    }

    private Properties getProps() {
        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream("disc_config.properties");
            // load a properties file
            prop.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return(prop);
    }
}
