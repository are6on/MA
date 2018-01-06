package com.example.dragos.tasker;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

import Dao.AppDatabase;
import Domain.Person;

public class Chart extends AppCompatActivity {

    HorizontalBarChart mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        mChart=(HorizontalBarChart)findViewById(R.id.chart1);

        ArrayList<BarEntry> yVals=new ArrayList<>();
        List<Person> pers= AppDatabase.getDatabase(getApplicationContext())
                .personDao().getPersons();
        long max=0;
        for(int i=0;i<pers.size();i++) {
            long n=AppDatabase.getDatabase(getApplicationContext()
            ).taskDao().getnroftasks(pers.get(i).getId());
            if(n>max)
                max=n;
            yVals.add(new BarEntry(i, n));
        }
        BarDataSet set=new BarDataSet(yVals,"number of current tasks");
        final ArrayList<String> xAxis=new ArrayList<>();
        for(Person o:pers)
            xAxis.add(o.getName());
        BarData data=new BarData(set);
        mChart.setData(data);
        mChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxis));
        mChart.getXAxis().setGranularity(1f);
        mChart.getAxisLeft().setGranularity(1.0f);
        mChart.getAxisLeft().setGranularityEnabled(true);
        mChart.getAxisRight().setEnabled(false);
        calculateMinMax(mChart,(int)max);
    }
    private void calculateMinMax(HorizontalBarChart chart, int labelCount) {
        float maxValue = chart.getData().getYMax();
        float minValue = chart.getData().getYMin();

        if ((maxValue - minValue) < labelCount) {
            float diff = labelCount - (maxValue - minValue);
            maxValue = maxValue + diff;
            chart.getAxisLeft().setAxisMaximum(maxValue-1+0.1f*labelCount);
            chart.getAxisLeft().setAxisMinimum(minValue-1);
        }
    }
}
