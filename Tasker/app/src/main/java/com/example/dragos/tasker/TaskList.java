package com.example.dragos.tasker;

import android.app.ProgressDialog;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import Domain.Person;
import Domain.Task;
import Domain.TaskArray;

public class TaskList extends AppCompatActivity{

    ArrayAdapter adaptorT;
    DatabaseReference mRef;
    private Query tasks;
    List<Task> list;
    Button toc;

    private ProgressDialog dialog;

    void populate(){

        ListView l=(ListView)findViewById(R.id.TaskList);
        if(TaskArray.person.getRole()==0) {
            Log.i("errror","admin");
            adaptorT = new TaskAdaptor(this, R.layout.activity_list_item,
                    list);
            Log.i("errror","5");
            l.setAdapter(adaptorT);
            Log.i("errror","6");
        }
        else{
            toc.setVisibility(View.GONE);
            Log.i("errror","emp");
            List<String> lp=new ArrayList<>();
            for(Task p:list)
                lp.add(p.getName());
            adaptorT=new ArrayAdapter(this,android.R.layout.simple_list_item_1,lp);
            Log.i("errror","7");
            l.setAdapter(adaptorT);
            Log.i("errror","8");
            final Context c=getApplicationContext();
            Log.i("errror","9");
            l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Log.i("errror","10");
                    Task t=list.get(i);
                    Log.i("errror","11");
                    Intent intent = new Intent(getApplicationContext(),DPTask.class);
                    intent.putExtra("EXTRA_ID",t.getIdt());
                    Log.i("linepassed","entering DPTask");
                    Log.i("errror","12");
                    startActivity(intent);
                    Log.i("errror","13");
                }
            });
        }
        dialog.dismiss();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        Log.i("errror","1");
        dialog = new ProgressDialog(this); // this = YourActivity
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Loading. Please wait...");
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        toc=(Button)findViewById(R.id.tochart);
        mRef=TaskArray.getInstance().getReference("Tasks");
        Log.i("errror","2");
        if(TaskArray.person.getRole()==0)
        tasks=mRef.orderByChild("idm").equalTo(TaskArray.person.getId());
        else
            tasks=mRef.orderByChild("idp").equalTo(TaskArray.person.getId());
        Log.i("errror","3");
        tasks.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list=new LinkedList<>();
                for (DataSnapshot p : dataSnapshot.getChildren()) {
                    Log.i("errror",p.getKey());
                    list.add(p.getValue(Task.class));
                }
                Log.i("errror","4");
                populate();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("errror","canceled");

            }
        });
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
