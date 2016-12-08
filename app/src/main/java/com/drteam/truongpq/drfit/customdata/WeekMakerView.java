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

public class WeekMakerView extends MarkerView {
    private TextView tvContent;
    private IAxisValueFormatter xAxisValueFormatter;
    private DecimalFormat format;

    public WeekMakerView(Context context, IAxisValueFormatter xAxisValueFormatter) {
        super(context, R.layout.custom_marker_view);

        this.xAxisValueFormatter = xAxisValueFormatter;
        tvContent = (TextView) findViewById(R.id.tvContent);
        format = new DecimalFormat("###.0");
    }
    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {

        tvContent.setText(xAxisValueFormatter.getFormattedValue(e.getX(), null) + ", " + (int)e.getY() + " steps");

        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }

}
