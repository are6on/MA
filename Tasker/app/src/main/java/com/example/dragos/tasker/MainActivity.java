package com.example.dragos.tasker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public void toCreateTask(View v) {
        Intent i=new Intent(this,CreateTask.class);
        startActivity(i);
    }
    public void toTaskList(View v) {
        Intent i=new Intent(this,TaskList.class);
        startActivity(i);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button bc=(Button)findViewById(R.id.buttonTaskCreate);
        Button bt=(Button)findViewById(R.id.buttonList);
    }
}