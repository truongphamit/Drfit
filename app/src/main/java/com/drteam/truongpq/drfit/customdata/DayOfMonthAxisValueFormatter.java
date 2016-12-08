package com.drteam.truongpq.drfit.customdata;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

/**
 * Created by Noah.TruongPQ on 12/7/2016.
 */

public class DayOfMonthAxisValueFormatter implements IAxisValueFormatter {
    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        int time = (int) value;
        return String.valueOf(time);
    }

    @Override
    public int getDecimalDigits() {
        return 0;
    }
}
