package edu.mum.cs490.project.utils;

import java.text.DecimalFormat;

public class DoubleRoundUp {

    private static DecimalFormat df = new DecimalFormat("#.##");

    public static Double roundUp(Double d) {
        if (d != null) {
            return Double.valueOf(df.format(d));
        } else {
            return d;
        }
    }
}
