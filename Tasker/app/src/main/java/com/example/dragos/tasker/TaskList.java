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
import android.widget.ListView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import Domain.Task;
import Domain.TaskArray;

public class TaskList extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @Override
    protected void onResume(){
        super.onResume();
        populate();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.i("linepassed",Integer.toString(i));
        Task t=TaskArray.getInstance().get(i);
        Intent intent = new Intent(this,ETaske.class);
        intent.putExtra("EXTRA_NAME",t.getName());
        intent.putExtra("EXTRA_DESCRIPTION",t.getDescription());
        intent.putExtra("EXTRA_INDEX",Integer.toString(i));
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        intent.putExtra("EXTRA_DATE",formatter.format(t.getDeadline()));
        intent.putExtra("EXTRA_IDE",Integer.toString(t.getIdp()));
        Log.i("linepassed","entering ETask");
        startActivity(intent);
    }
    private void readData(){
        try {
            FileInputStream f=openFileInput("tasks.txt");
            ObjectInputStream oi=new ObjectInputStream(f);
            Task e;
            while((e=(Task)oi.readObject())!=null)
            {
                TaskArray.getInstance().add(e);
            }

        } catch (java.io.IOException e) {
            Toast.makeText(TaskList.this, "Can't open file tasks.txt", Toast.LENGTH_SHORT).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    void populate(){
        List<String> name=new ArrayList<>();
        int n=TaskArray.getInstance().size();
        for(int i=0;i<n;i++)
            name.add(TaskArray.getInstance().get(i).getName());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, name);
        ListView l=(ListView)findViewById(R.id.TaskList);
        l.setAdapter(adapter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        //readData();
        populate();
        ListView l=(ListView)findViewById(R.id.TaskList);
        l.setOnItemClickListener(this);
    }
}
