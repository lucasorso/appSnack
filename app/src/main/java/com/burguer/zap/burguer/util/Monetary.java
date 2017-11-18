package com.burguer.zap.burguer.util;

import java.text.NumberFormat;

/**
 * Created by LucasOrso on 11/18/17.
 */

public class Monetary {

    public static String sDoubleToString(Double aValue) {
        NumberFormat lNumberFormat = NumberFormat.getCurrencyInstance();
        return lNumberFormat.format(aValue);
    }

    public static Double sStringWithCurrencyToDouble(String aValue) {
        String lValue = aValue.replaceAll("[^\\d]", "");
        Double lDoubleValue = Double.valueOf(lValue);
        return lDoubleValue / 100;
    }
}
