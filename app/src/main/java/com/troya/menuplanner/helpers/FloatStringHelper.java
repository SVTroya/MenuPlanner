package com.troya.menuplanner.helpers;

import java.util.Locale;

public class FloatStringHelper {

    public static String getCorrectedValue (Float value) {
        String correctedAmount;
        if (value != null && value != 0) {
            if (value % 1 == 0) {
                correctedAmount = String.format(Locale.getDefault(), "%d", (long) value.floatValue());
            } else {
                correctedAmount = String.format(Locale.getDefault(), "%s", value);
            }
        }
        else {
            correctedAmount = "";
        }

        return correctedAmount;
    }
}
