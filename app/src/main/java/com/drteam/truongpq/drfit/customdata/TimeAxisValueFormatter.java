package com.drteam.truongpq.drfit.customdata;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

/**
 * Created by truongpq on 09/11/2016.
 */

public class TimeAxisValueFormatter implements IAxisValueFormatter {
    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        int time = (int) value;
        return String.valueOf(time) + "h";
    }

    @Override
    public int getDecimalDigits() {
        return 0;
    }
}
