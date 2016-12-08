package com.drteam.truongpq.drfit.customdata;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

/**
 * Created by Noah.TruongPQ on 12/7/2016.
 */

public class DayOfWeekAxisValueFormatter implements IAxisValueFormatter {
    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        int day = (int) value;
        switch (day) {
            case 2:
                return  "Mon";
            case 3:
                return  "Tue";
            case 4:
                return  "Wed";
            case 5:
                return "Thu";
            case 6:
                return  "Fri";
            case 7:
                return  "Sat";
            case 1:
                return  "Sun";
            default:
                return null;
        }
    }

    @Override
    public int getDecimalDigits() {
        return 0;
    }
}
