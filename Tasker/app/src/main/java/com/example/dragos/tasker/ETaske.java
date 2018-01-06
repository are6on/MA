package com.example.dragos.tasker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.provider.Contacts;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import Dao.AppDatabase;
import Domain.Person;
import Domain.Task;
import Domain.TaskArray;

public class ETaske extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    public static int id=1;
    public static List<Person> employers;
    public static Task task;
    private Date convertDate(String d){
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        Date date=null;
        try {
            date = formatter.parse(d);
        } catch (ParseException e) {
        }
        return date;
    }

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

        if(id!=0) {
            task.setName(name);
            task.setDescription(description);
            task.setDeadline(date);
            Log.i("linepassed", "96-e");
            task.setIdp(employers.get(id-1).getId());
            Log.i("linepassed", "97-e");
            AppDatabase.getDatabase(getApplicationContext()).taskDao().updateTask(task);
            Log.i("linepassed", "98-e");
        }
        else {
            AppDatabase.getDatabase(getApplicationContext()).taskDao().updateTask(
                    new Task(TaskArray.person.getId(), employers.get(id-1).getId()
                            , name, description, date));
            Log.i("linepassed", "100-e");
            for (Person y : employers) {
                if(y.getId()!=employers.get(id-1).getId())
                AppDatabase.getDatabase(getApplicationContext()).taskDao().addTask(
                        new Task(TaskArray.person.getId(), y.getId()
                                , name, description, date));
            }
            Log.i("linepassed", "111-e");
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
        Log.i("linepassed","67");
        employers= AppDatabase.getDatabase(getApplicationContext()).personDao().getPersons();
        List<CharSequence> emp=new LinkedList<>();
        emp.add("All");
        for(Person e:employers)
            emp.add(e.getName());
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,emp);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        Log.i("linepassed","72");
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
        EditText namee=(EditText)findViewById(R.id.ETaskNameText);
        EditText dese=(EditText)findViewById(R.id.EDescriptionText);
        Intent inte=getIntent();
        task= AppDatabase.getDatabase(getApplicationContext()).taskDao()
                .getTask(Integer.parseInt(inte.getStringExtra("EXTRA_ID"))).get(0);
        namee.setText(task.getName());
        dese.setText(task.getDescription());
        Log.i("linepassed","108");
        spinner.setSelection(getpos(task.getIdp())+1);
        Log.i("linepassed","109");
        d.setText(task.getDeadline());
        Log.i("linepassed","119");
    }

    private int getpos(int id){
        for(int i=0;i<employers.size();i++)
            if(employers.get(i).getId()==id)
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

