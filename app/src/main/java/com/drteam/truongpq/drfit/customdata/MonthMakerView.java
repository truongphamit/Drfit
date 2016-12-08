package com.drteam.truongpq.drfit.customdata;

import android.content.Context;
import android.widget.TextView;

import com.drteam.truongpq.drfit.R;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import java.text.DecimalFormat;

/**
 * Created by Noah.TruongPQ on 12/7/2016.
 */

public class MonthMakerView extends MarkerView {
    private TextView tvContent;
    private IAxisValueFormatter xAxisValueFormatter;
    private DecimalFormat format;

    public MonthMakerView(Context context, IAxisValueFormatter xAxisValueFormatter) {
        super(context, R.layout.custom_marker_view);

        this.xAxisValueFormatter = xAxisValueFormatter;
        tvContent = (TextView) findViewById(R.id.tvContent);
        format = new DecimalFormat("###.0");
    }
    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        String number = xAxisValueFormatter.getFormattedValue(e.getX(), null);
        switch (number) {
            case "1":
                number += "st";
                break;
            case "2":
                number += "nd";
                break;
            case "3":
                number += "rd";
                break;
            case "21":
                number += "st";
                break;
            case "22":
                number += "nd";
                break;
            case "23":
                number += "rd";
                break;
            case "31":
                number += "st";
                break;
            default:
                number += "th";
                break;
        }
        tvContent.setText(number + ", " + (int)e.getY() + " steps");

        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}
