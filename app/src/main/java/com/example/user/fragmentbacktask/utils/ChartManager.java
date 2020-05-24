package com.example.user.fragmentbacktask.utils;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.List;

/**
 * Created by huangyuxi on 2019-06-27
 * Title: 图标工具类
 */
public class ChartManager {
    private ChartManager() {
    }

    private static class SingleLoader {
        private static ChartManager instance = new ChartManager();
    }

    public static ChartManager getInstance() {
        return SingleLoader.instance;
    }

    /**
     * 组合图表设置
     * @param combChart 组合chart
     */
    public void configCombineChart(CombinedChart combChart) {
        combChart.getDescription().setEnabled(false);
        combChart.getLegend().setEnabled(false);
        combChart.setDrawGridBackground(false); // 边框
        combChart.getAxisLeft().setDrawAxisLine(false); // 隐藏轴线
        combChart.setScaleEnabled(false);
        combChart.setPinchZoom(false);

        YAxis leftAxis = combChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setTextSize(10f);
        leftAxis.setTextColor(0xff969FA9);
        leftAxis.setSpaceTop(30);
        leftAxis.setAxisMinimum(0f);

        YAxis rightAxis = combChart.getAxisRight();
        rightAxis.setGranularity(1);
        rightAxis.setDrawAxisLine(false);
        rightAxis.setDrawGridLines(false);
        rightAxis.setTextSize(10f);
        rightAxis.setSpaceTop(30);
        rightAxis.setTextColor(0xff969FA9);
        rightAxis.setAxisMinimum(0f);

        XAxis xAxis = combChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setAxisLineColor(0xff969FA9);
        xAxis.setAxisLineWidth(1);
        xAxis.setTextSize(10f);
        xAxis.setTextColor(0xff969FA9);

        combChart.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR,
                CombinedChart.DrawOrder.LINE
        });

    }

    /**
     *
     */
    public void configBarChart(BarChart barChart) {
        barChart.setDrawGridBackground(false); // 边框
        barChart.getAxisLeft().setDrawAxisLine(false); // 隐藏轴线
        barChart.getAxisRight().setEnabled(false);
        barChart.setScaleEnabled(false);
        barChart.setPinchZoom(false);
        barChart.getDescription().setEnabled(false);
        barChart.getLegend().setEnabled(false);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setTextSize(10f);
        xAxis.setTextColor(0xff8A97A6);
        xAxis.setGranularity(1f);
        xAxis.setAxisLineWidth(1.5f);
        xAxis.setAxisLineColor(0xff8A97A6);
        xAxis.setDrawGridLines(false);

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setAxisMinimum(0);
        leftAxis.setSpaceTop(30);
        leftAxis.setTextColor(0XFF969FA9);
        leftAxis.setTextSize(10f);
    }

    /**
     * 线形图配置
     * @param lineChart 线形图
     * @param isShowLegend 是否显示图例
     */
    public void configLineChart(LineChart lineChart, boolean isShowLegend) {
        lineChart.setDrawGridBackground(false); // 边框
        lineChart.getAxisLeft().setDrawAxisLine(false); // 隐藏轴线
        lineChart.getAxisRight().setEnabled(false);
        lineChart.setScaleEnabled(false);
        lineChart.setPinchZoom(false);
        lineChart.getDescription().setEnabled(false);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setTextSize(10f);
        xAxis.setTextColor(0xff8A97A6);
        xAxis.setGranularity(1f);
        xAxis.setAxisLineWidth(1.5f);
        xAxis.setAxisLineColor(0xff8A97A6);
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
        leftAxis.setSpaceTop(30);
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

    public LineDataSet lineDataSet(List<Entry> entries, String label) {
        LineDataSet lineDataSet = new LineDataSet(entries, label);
        lineDataSet.setLineWidth(2f);
        lineDataSet.setDrawCircles(false);
        lineDataSet.setDrawValues(false);
        lineDataSet.setHighLightColor(0xff8A97A6);
        lineDataSet.setHighlightLineWidth(1f);
        lineDataSet.setDrawHorizontalHighlightIndicator(false);
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        return lineDataSet;
    }

}
