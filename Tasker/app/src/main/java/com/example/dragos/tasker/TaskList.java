package com.example.dragos.tasker;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import Dao.AppDatabase;
import Domain.Task;
import Domain.TaskArray;

public class TaskList extends AppCompatActivity {

    TaskAdaptor adaptorT;
    @Override
    protected void onResume(){
        super.onResume();
        populate();
    }

    void populate(){
        List<String> name=new ArrayList<>();
        int n=TaskArray.getInstance().size();
        for(int i=0;i<n;i++)
            name.add(TaskArray.getInstance().get(i).getName());
        adaptorT=new TaskAdaptor(this,R.layout.activity_list_item,
                AppDatabase.getDatabase(getApplicationContext())
                .taskDao().getTaskByMananger(TaskArray.person.getId()));
        ListView l=(ListView)findViewById(R.id.TaskList);
        l.setAdapter(adaptorT);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        populate();
        ListView l=(ListView)findViewById(R.id.TaskList);
        Button b=(Button)findViewById(R.id.tochart);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(view.getContext(),Chart.class);
                startActivity(i);
            }
        });
    }
}
