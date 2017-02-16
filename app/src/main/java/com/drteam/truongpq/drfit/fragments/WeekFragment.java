package com.drteam.truongpq.drfit.fragments;


import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.drteam.truongpq.drfit.R;
import com.drteam.truongpq.drfit.customdata.DayOfWeekAxisValueFormatter;
import com.drteam.truongpq.drfit.customdata.MyAxisValueFormatter;
import com.drteam.truongpq.drfit.customdata.MyMarkerView;
import com.drteam.truongpq.drfit.customdata.TimeAxisValueFormatter;
import com.drteam.truongpq.drfit.customdata.WeekMakerView;
import com.drteam.truongpq.drfit.customdata.XYMarkerView;
import com.drteam.truongpq.drfit.utilities.Utils;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeekFragment extends Fragment implements OnChartValueSelectedListener, OnChartGestureListener {
    private LineChart chart;
    private TextView tv_steps;
    private TextView tv_duration;
    private long firstOfWeek;
    private long lastOfWeek;

    public long getLastOfWeek() {
        return lastOfWeek;
    }

    public long getFirstOfWeek() {
        return firstOfWeek;
    }

    public WeekFragment() {
        // Required empty public constructor
    }

    public static WeekFragment newInstance(long date) {
        WeekFragment weekFragment = new WeekFragment();
        Bundle args = new Bundle();
        args.putLong("date", date);
        weekFragment.setArguments(args);
        return weekFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firstOfWeek = Utils.getFirstDayOfWeek(getArguments().getLong("date"));
        lastOfWeek = Utils.getLastDayOfWeek(getArguments().getLong("date"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_week, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        tv_steps = (TextView) view.findViewById(R.id.tv_steps);
        tv_duration = (TextView) view.findViewById(R.id.tv_duration);
        chart = (LineChart) view.findViewById(R.id.chart);
        chart.setOnChartGestureListener(this);
        chart.setOnChartValueSelectedListener(this);
        chart.setDrawGridBackground(false);

        // no description text
        chart.getDescription().setEnabled(false);

        // enable touch gestures
        chart.setTouchEnabled(true);

        // enable scaling and dragging
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        chart.setPinchZoom(true);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        MyMarkerView mv = new MyMarkerView(getActivity(), R.layout.custom_marker_view);
        mv.setChartView(chart); // For bounds control
        chart.setMarker(mv); // Set the marker to the chart

        // x-axis limit line
        LimitLine llXAxis = new LimitLine(10f, "Index 10");
        llXAxis.setLineWidth(4f);
        llXAxis.enableDashedLine(10f, 10f, 0f);
        llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        llXAxis.setTextSize(10f);

        IAxisValueFormatter xAxisFormatter = new DayOfWeekAxisValueFormatter();

        XAxis xAxis = chart.getXAxis();
        xAxis.setTextColor(Color.WHITE);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(1f);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(7);
        xAxis.setValueFormatter(xAxisFormatter);


        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        leftAxis.setAxisMaximum(10000f);
        leftAxis.setTextColor(Color.WHITE);
        leftAxis.setAxisMinimum(0f);
        //leftAxis.setYOffset(20f);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(false);

        // limit lines are drawn behind data (and not on top)
        leftAxis.setDrawLimitLinesBehindData(true);

        chart.getAxisRight().setEnabled(false);

        //mChart.getViewPortHandler().setMaximumScaleY(2f);
        //mChart.getViewPortHandler().setMaximumScaleX(2f);

        WeekMakerView weekMakerView = new WeekMakerView(getActivity(), xAxisFormatter);
        weekMakerView.setChartView(chart); // For bounds control
        chart.setMarker(weekMakerView); // Set the marker to the chart

        // add data
        setData(7, 10000);

        chart.animateX(1000);
        //mChart.invalidate();

        // get the legend (only possible after setting data)
        Legend l = chart.getLegend();

        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);

        // // dont forget to refresh the drawing
        // mChart.invalidate();
    }

    private void setData(int count, float range) {

        ArrayList<Entry> values = new ArrayList<>();
        long sum = 0;
        for (int i = 1; i <= count; i++) {
            int val = (int) (Math.random() * range) + 3;
            sum += val;
            values.add(new Entry(i, val));
        }

        tv_steps.setText(String.valueOf(sum));
        tv_duration.setText(String.valueOf(Math.round(sum * 0.0007)));

        LineDataSet set1;

        if (chart.getData() != null && chart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values, "DataSet 1");

            // set the line to be drawn like this "- - - - - -"
//            set1.enableDashedLine(10f, 5f, 0f);
//            set1.enableDashedHighlightLine(10f, 5f, 0f);
            set1.setColor(getActivity().getResources().getColor(android.R.color.holo_red_light));
            set1.setCircleColor(getActivity().getResources().getColor(android.R.color.holo_red_light));
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);
            set1.setDrawCircleHole(false);
            set1.setValueTextSize(9f);
            set1.setValueTextColor(Color.WHITE);
            set1.setDrawFilled(false);
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15.f);

//            if (com.github.mikephil.charting.utils.Utils.getSDKInt() >= 18) {
//                // fill drawable only supported on api level 18 and above
//                Drawable drawable = ContextCompat.getDrawable(getActivity(), R.drawable.fade_red);
//                set1.setFillDrawable(drawable);
//            }
//            else {
//                set1.setFillColor(Color.RED);
//            }

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1); // add the datasets

            // create a data object with the datasets
            LineData data = new LineData(dataSets);
            data.setDrawValues(false);

            // set data
            chart.setData(data);
        }
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartLongPressed(MotionEvent me) {

    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {

    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {

    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {

    }
}
