package com.example.xx.mpandroidchart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LineChart mLineChart = findViewById(R.id.line_chart);
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            entries.add(new Entry(i, (float) (Math.random() * 80), "第" + i));
        }

        ChartUtils.initChart(mLineChart);
        ChartUtils.notifyDataSetChanged(mLineChart, ChartUtils.judgeLineChatData(entries));
    }
}
