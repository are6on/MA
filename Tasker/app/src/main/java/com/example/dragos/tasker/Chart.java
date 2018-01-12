package com.example.dragos.tasker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import Domain.Person;
import Domain.Task;
import Domain.TaskArray;

public class Chart extends AppCompatActivity {

    HorizontalBarChart mChart;
    private Query persons,nrtasks;
    List<Person> pers;
    ArrayList<BarEntry> yVals;
    ArrayList<String> xAxis;
    long max;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        mChart=(HorizontalBarChart)findViewById(R.id.chart1);
        persons= TaskArray.getInstance().getReference()
                .child("Persons").orderByChild("role").equalTo(1);
        persons.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Spinner spinner = (Spinner) findViewById(R.id.EmployerText);
                    pers=new LinkedList<>();
                    for (DataSnapshot p : dataSnapshot.getChildren()) {
                        pers.add(p.getValue(Person.class));
                    }
                    xAxis = new ArrayList<>();
                    for (Person o : pers)
                        xAxis.add(o.getName());
                    makeChart();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        nrtasks=TaskArray.getInstance().getReference("Tasks")
                .orderByChild("idm").equalTo(TaskArray.person.getId());
        nrtasks.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    max = 0;
                    List<Integer> nr=new ArrayList<>(pers.size());
                    for (DataSnapshot p : dataSnapshot.getChildren()) {
                        Task t=p.getValue(Task.class);
                        for(int i=0;i<pers.size();i++)
                            if(pers.get(i).getId()==t.getIdp()){
                                nr.set(i,nr.get(i)+1);
                            }
                    }
                    for(int i=0;i<nr.size();i++){
                        if (nr.get(i) > max)
                            max = nr.get(i);
                        yVals.add(new BarEntry(i, nr.get(i)));
                    }
                    makeChart();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void makeChart(){

        if(yVals!=null) {
            BarDataSet set = new BarDataSet(yVals, "number of current tasks");
            BarData data = new BarData(set);
            mChart.setData(data);
            mChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxis));
            mChart.getXAxis().setGranularity(1f);
            mChart.getAxisLeft().setGranularity(1.0f);
            mChart.getAxisLeft().setGranularityEnabled(true);
            mChart.getAxisRight().setEnabled(false);
            calculateMinMax(mChart, (int) max);
        }
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
