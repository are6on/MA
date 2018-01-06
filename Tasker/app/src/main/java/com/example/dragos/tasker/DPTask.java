package com.example.dragos.tasker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import Dao.AppDatabase;
import Domain.Person;
import Domain.Task;

public class DPTask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dptask);
        TextView name,description,date,employer;
        name=(TextView)findViewById(R.id.DTaskNameText);
        description=(TextView)findViewById(R.id.DDescriptionText);
        date=(TextView)findViewById(R.id.DDeadlineText);
        employer=(TextView)findViewById(R.id.DEmployerText);
        Task t= AppDatabase.getDatabase(getApplicationContext())
                .taskDao().getTask(Integer.parseInt(getIntent().getStringExtra("EXTRA_ID")))
                .get(0);
        name.setText(t.getName());
        description.setText(t.getDescription());
        date.setText(t.getDeadline());
        Person p=AppDatabase.getDatabase(getApplicationContext()).personDao()
                .getPerson(t.getIdp());
        employer.setText(p.getName());
    }
}
