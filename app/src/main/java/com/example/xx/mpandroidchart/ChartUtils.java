package com.example.xx.mpandroidchart;

import android.graphics.Color;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * 日期：2017/12/5
 * 描述：图标的工具类
 *
 * @author XX
 */

public class ChartUtils {

    /**
     * 初始化图表
     *
     * @param chart 原始图表
     * @return 初始化后的图表
     */
    public static LineChart initChart(LineChart chart) {
        // 不显示数据描述
        chart.getDescription().setEnabled(false);
        // 没有数据的时候，显示“暂无数据”
        chart.setNoDataText("暂无数据");
        // 不显示表格颜色
        chart.setDrawGridBackground(false);
        // 不可以缩放
        chart.setScaleEnabled(false);
        // 不显示y轴右边的值
        chart.getAxisRight().setEnabled(false);
        // 不显示图例
        Legend legend = chart.getLegend();
        legend.setEnabled(false);
        // 向左偏移15dp，抵消y轴向右偏移的30dp
//        chart.setExtraLeftOffset(-15);

        XAxis xAxis = chart.getXAxis();
        // 显示x轴
        xAxis.setDrawAxisLine(true);
        // 不从x轴发出竖向直线
        xAxis.setDrawGridLines(false);
        // 设置x轴数据的位置
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setTextSize(12);
        xAxis.setGridColor(Color.BLACK);

        YAxis yAxis = chart.getAxisLeft();
        // 显示y轴
        yAxis.setDrawAxisLine(true);
        // 设置y轴数据的位置
        yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        // 不从y轴发出横向直线
        yAxis.setDrawGridLines(false);
        yAxis.setTextColor(Color.BLACK);
        yAxis.setTextSize(12);
        // y轴显示数据量
        yAxis.setLabelCount(5);
        // 设置y轴数据偏移量
        yAxis.setXOffset(20);
        yAxis.setYOffset(-3);

        chart.invalidate();
        return chart;
    }

    /**
     * 更新图表
     *
     * @param chart  图表
     * @param values 数据
     */
    public static void notifyDataSetChanged(LineChart chart, final List<Entry> values) {
        if (values == null || values.size() < 1) {
            return;
        }

        //经测试，该库只有在X轴数据大于等于3才能正常显示X轴
        //在等于2时，会自动填补一个数据，没有具体意义
        //在等于1时，没有什么意义，就不显示图表了
        XAxis xAxis = chart.getXAxis();
        if (values.size() > 1) {
            // X轴显示的数量
            if (values.size() < 7) {
                xAxis.setLabelCount(values.size() - 1);
            } else {
                xAxis.setLabelCount(6);
            }

            xAxis.setValueFormatter(new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    return xValuesProcess(values)[(int) value];
                }
            });

            chart.invalidate();
            setChartData(chart, values);
        }
    }

    /**
     * 处理折线图数据
     */
    public static List<Entry> judgeLineChatData(List<Entry> pointList) {
        List<Entry> values = new ArrayList<>();
        if (pointList == null || pointList.size() < 1) {
            return values;
        }

        for (int i = 0; i < pointList.size(); i++) {
            Entry point = pointList.get(i);
            values.add(new Entry(i, point.getY(), point.getData()));
        }

        return values;
    }

    /**
     * 设置图表数据
     *
     * @param chart  图表
     * @param values 数据
     */
    private static void setChartData(LineChart chart, List<Entry> values) {
        LineDataSet lineDataSet;

        if (chart.getData() != null && chart.getData().getDataSetCount() > 0) {
            lineDataSet = (LineDataSet) chart.getData().getDataSetByIndex(0);
            lineDataSet.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            lineDataSet = new LineDataSet(values, "");
            // 设置曲线颜色
            lineDataSet.setColor(R.color.colorPrimary);
            // 设置平滑曲线
            lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            // 不显示坐标点的小圆点
            lineDataSet.setDrawCircles(false);
            // 不显示坐标点的数据
            lineDataSet.setDrawValues(false);
            // 显示定位线
            lineDataSet.setHighlightEnabled(true);
            // 设置颜色填充
            lineDataSet.setDrawFilled(true);

            LineData data = new LineData(lineDataSet);
            chart.setData(data);
            chart.invalidate();
        }
    }

    /**
     * x轴数据处理
     *
     * @return x轴数据
     */
    private static String[] xValuesProcess(List<Entry> values) {
        String[] time = new String[values.size()];
        for (int i = 0; i < values.size(); i++) {
            Entry entry = values.get(i);
            time[i] = String.valueOf(entry.getData());
        }

        return time;
    }
}
