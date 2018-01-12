package com.example.dragos.tasker;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import Domain.Person;
import Domain.TaskArray;

public class MainActivity extends AppCompatActivity {

    private Query user;
    private ProgressDialog dialog;
    private Button bc;

    public void toCreateTask(View v) {
        Intent i=new Intent(this,CreateTask.class);
        startActivity(i);
    }
    public void toTaskList(View v) {
        Intent i=new Intent(this,TaskList.class);
        startActivity(i);
    }
    public void toLogin(View v) {
        Intent i=new Intent(this,LoginActivity.class);
        startActivity(i);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseReference mRef=TaskArray.getInstance().getReference("Persons");
        bc=(Button)findViewById(R.id.buttonTaskCreate);
        Button bt=(Button)findViewById(R.id.buttonList);
    }

    @Override
    protected void onResume(){
        super.onResume();
        bc.setVisibility(View.VISIBLE);
        Log.i("login","resume");
        if(FirebaseAuth.getInstance().getCurrentUser()==null)
            toLogin(null);
        else if(TaskArray.person==null){
            dialog = new ProgressDialog(this); // this = YourActivity
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setMessage("Loading. Please wait...");
            dialog.setIndeterminate(true);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            final String mail=FirebaseAuth.getInstance().getCurrentUser().getEmail();
            user=TaskArray.getInstance().getReference("Persons");
            Log.i("login",FirebaseAuth.getInstance().getCurrentUser().getEmail());
            user.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()) {
                        for (DataSnapshot e : dataSnapshot.getChildren()) {
                            Person p = e.getValue(Person.class);
                            if (p.getAddress().equals(mail)) {
                                Log.i("login", "person");
                                TaskArray.person = p;
                                Log.i("login", p.getName());
                                break;
                            }
                        }
                        if (TaskArray.person.getRole() != 0)
                            bc.setVisibility(View.GONE);

                        Log.i("login", "what");
                        dialog.dismiss();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.i("login","comeon");
                    dialog.dismiss();
                }
            });
        }
        else
        if(TaskArray.person.getRole()!=0) {
            Log.i("login","hello");
            bc.setVisibility(View.GONE);
        }
    }
}