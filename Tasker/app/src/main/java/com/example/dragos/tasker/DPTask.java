package com.example.dragos.tasker;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import Domain.Task;
import Domain.TaskArray;

public class DPTask extends AppCompatActivity {

    Task t=null;
    DatabaseReference task;

    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dptask);
        dialog = new ProgressDialog(this); // this = YourActivity
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Loading. Please wait...");
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        task= TaskArray.getInstance().getReference("Tasks").child(getIntent().getStringExtra("EXTRA_ID"));
        task.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    t = dataSnapshot.getValue(Task.class);
                    final TextView name, description, date, employer;
                    name = (TextView) findViewById(R.id.DTaskNameText);
                    description = (TextView) findViewById(R.id.DDescriptionText);
                    date = (TextView) findViewById(R.id.DDeadlineText);
                    employer = (TextView) findViewById(R.id.DEmployerText);
                    name.setText(t.getName());
                    description.setText(t.getDescription());
                    date.setText(t.getDeadline());
                    String p;
                    TaskArray.getInstance().getReference("Persons").child(t.getIdp()).child("name")
                            .addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.exists()){
                                        employer.setText(dataSnapshot.getValue(String.class));
                                        dialog.dismiss();
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
