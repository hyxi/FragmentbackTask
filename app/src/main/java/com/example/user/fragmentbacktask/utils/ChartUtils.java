package com.example.user.fragmentbacktask.utils;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.formatter.ValueFormatter;

/**
 * Created by huangyuxi on 2019-06-20
 * Title:
 */
public class ChartUtils {

    public static class SingleLoader {
        private static ChartUtils instance = new ChartUtils();
    }

    public static ChartUtils getInstance() {
        return SingleLoader.instance;
    }

    public void configLineChart(LineChart lineChart, boolean isShowLegend) {
        lineChart.setDrawGridBackground(false); // 边框
        lineChart.getAxisLeft().setDrawAxisLine(false); // 隐藏轴线
        lineChart.getAxisRight().setEnabled(false);
        lineChart.setScaleEnabled(false);
        lineChart.setPinchZoom(false);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setTextSize(10f);
        xAxis.setGranularity(1f);
        xAxis.setDrawGridLines(false);

        ValueFormatter xAxisFormat = new ValueFormatter() {

            @Override
            public String getFormattedValue(float value) {
                int index = (int) value;
                return String.valueOf(index);
            }
        };
        xAxis.setValueFormatter(xAxisFormat);

        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setAxisMinimum(0);
        leftAxis.setTextColor(0XFF969FA9);
        leftAxis.setTextSize(10f);

        Legend l = lineChart.getLegend();
        if (isShowLegend) {
            l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
            l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
            l.setDrawInside(false);
            l.setFormSize(10f);
            l.setFormLineWidth(2f);
            l.setForm(Legend.LegendForm.LINE);
            l.setTextSize(12f);
            l.setTextColor(0XFF181E26);
            l.setFormToTextSpace(6f);
            l.setXEntrySpace(10f);
        } else {
            l.setEnabled(false);
        }
    }

    public void configBarChart(BarChart barChart, boolean isShowLegend) {
        barChart.setDrawBarShadow(false);
        barChart.setDrawGridBackground(false); // 边框
        barChart.getAxisLeft().setDrawAxisLine(false); // 隐藏轴线

        barChart.getAxisRight().setEnabled(false);
        barChart.getDescription().setEnabled(false);
        barChart.setScaleEnabled(false);
        barChart.setPinchZoom(false);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
//        xAxis.setTextColor(COLOR_GRAY);
        xAxis.setTextSize(10f);
        xAxis.setGranularity(1f);
        xAxis.setDrawGridLines(false);

        ValueFormatter xAxisFormat = new ValueFormatter() {

            @Override
            public String getFormattedValue(float value) {
                int index = (int) value;
                return String.valueOf(index);
            }
        };
        xAxis.setValueFormatter(xAxisFormat);

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(30f);
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0);
        leftAxis.setTextColor(0XFF969FA9);
        leftAxis.setTextSize(10f);

        Legend l = barChart.getLegend();
        if (isShowLegend) {
            l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
            l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
            l.setDrawInside(false);
            l.setFormSize(6f);
            l.setTextSize(12f);
            l.setTextColor(0XFF181E26);
            l.setFormToTextSpace(6f);
            l.setXEntrySpace(10f);
        } else {
            l.setEnabled(false);
        }
    }

}
