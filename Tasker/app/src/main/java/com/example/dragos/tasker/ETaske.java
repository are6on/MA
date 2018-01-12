package com.example.dragos.tasker;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import Domain.Person;
import Domain.Task;
import Domain.TaskArray;

public class ETaske extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    public static int id=1;
    public static List<Person> employers;
    public static Task task;
    private DatabaseReference mref;
    private Query persons,ttask;
    private ProgressDialog dialog;

    public void edit(View v) {
        // try {
        //FileOutputStream f=openFileOutput("tasks.txt", Context.MODE_WORLD_READABLE);
        //ObjectOutputStream bs= new ObjectOutputStream(f);
        Log.i("linepassed", "91");
        String name = ((EditText) findViewById(R.id.ETaskNameText)).getText().toString();
        Log.i("linepassed", "92");
        String description = ((EditText) findViewById(R.id.EDescriptionText)).getText().toString();
        Log.i("linepassed", "93");
        String date = ((TextView) findViewById(R.id.EDeadlineText)).getText().toString();
        Log.i("linepassed", "94");
        Log.i("linepassed", "95");
        task.setName(name);
        task.setDescription(description);
        task.setDeadline(date);
        Log.i("linepassed", "96-e");
        task.setIdp(employers.get(id-1).getId());

        if(id!=0) {
            Task t=task;
            task=null;
            mref.child("Tasks").child(t.getIdt()).setValue(t);
        }
        else
            for(Person y:employers)
            {
                Task t=task;
                task=null;
                mref.child("Tasks").child(task.getIdt()).setValue(t);
                if(y.getId()!=task.getIdp()) {
                    String idt = mref.child("Tasks").push().getKey();
                    mref.child("Tasks").child(idt).setValue(new Task(idt, TaskArray.person.getId(),
                            employers.get(id - 1).getId(), name, description, date));
                }
            }
        // for(Task e : TaskArray.tasks)
        // bs.writeObject(e);
        // bs.close();
        // f.close();
        //} catch (IOException e) {
        //  Toast.makeText(ETaske.this, "Can't open file tasks.txt", Toast.LENGTH_SHORT).show();
        //}

        Log.i("linepassed", "106");
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("linepassed","entered");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etaske);
        TextView d=findViewById(R.id.EDeadlineText);
        dialog = new ProgressDialog(this); // this = YourActivity
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Loading. Please wait...");
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("linepassed","60");
                ETaske.DatePickerFragment fragment =new ETaske.DatePickerFragment();
                fragment.show(getSupportFragmentManager(),"date");
                Log.i("linepassed","63");
            }
        });
        Spinner spinner = (Spinner) findViewById(R.id.EEmployerText);
        mref = TaskArray.getInstance().getReference();
        persons = mref.child("Persons").orderByChild("role").equalTo(1);
        final ETaske that=this;
        persons.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Spinner spinner = (Spinner) findViewById(R.id.EEmployerText);
                    employers=new LinkedList<>();
                    for (DataSnapshot p : dataSnapshot.getChildren()) {
                        employers.add(p.getValue(Person.class));
                    }
                    List<CharSequence> emp=new LinkedList<>();
                    emp.add("All");
                    for(Person e:employers)
                        emp.add(e.getName());
                    ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(that,
                            android.R.layout.simple_spinner_item,emp);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);
                    if(task!=null)
                        spinner.setSelection(getpos(task.getIdp())+1);
                }
                dialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                id=i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        Log.i("linepassed","111");
        Intent inte=getIntent();
        ttask=TaskArray.getInstance().getReference("Tasks/"+inte.getStringExtra("EXTRA_ID"));
        ttask.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    TextView d=findViewById(R.id.EDeadlineText);
                    Spinner spinner = (Spinner) findViewById(R.id.EEmployerText);
                    EditText namee=(EditText)findViewById(R.id.ETaskNameText);
                    EditText dese=(EditText)findViewById(R.id.EDescriptionText);
                    Task t= dataSnapshot.getValue(Task.class);
                    if(task!=null&&(!task.getIdp().equals(t.getIdp())||
                            !task.getName().equals(t.getName())||
                        !task.getDescription().equals(t.getDescription())||
                            !task.getDeadline().equals(t.getDeadline()))) {
                        AlertDialog.Builder alert=new AlertDialog.Builder(getApplicationContext())
                                .setMessage("The task has been updated.")
                                .setTitle("Alert")
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {}});
                        AlertDialog dialog = alert.create();
                        dialog.show();
                    }
                    else {
                        namee.setText(t.getName());
                        dese.setText(t.getDescription());
                        Log.i("linepassed", "108");
                        spinner.setSelection(getpos(t.getIdp()) + 1);
                        Log.i("linepassed", "109");
                        d.setText(t.getDeadline());
                        task=t;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private int getpos(String id){
        for(int i=0;i<employers.size();i++)
            if(employers.get(i).getId().equals(id))
                return i;
        return 0;
    }

    private void setDate(final Calendar c){
        Log.i("linepassed","123");
        final DateFormat df=DateFormat.getDateInstance(DateFormat.SHORT);
        ((TextView)findViewById(R.id.EDeadlineText)).setText(df.format(c.getTime()));
        Log.i("linepassed","126");
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        Log.i("linepassed","131");
        Calendar cal=new GregorianCalendar(i,i1,i2);
        setDate(cal);
        Log.i("linepassed","134");
    }


    public static class DatePickerFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle saveInstanceState){
            Log.i("linepassed","140");
            final Calendar c=Calendar.getInstance();
            int year=c.get(Calendar.YEAR);
            int month=c.get(Calendar.MONTH);
            int day=c.get(Calendar.DAY_OF_MONTH);

            Log.i("linepassed","146");
            return new DatePickerDialog(getActivity(),(DatePickerDialog.OnDateSetListener)getActivity(),year,month,day);
        }
    }
}

