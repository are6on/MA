package com.example.dragos.tasker;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.app.DatePickerDialog;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

import java.text.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import Domain.Task;
import Domain.TaskArray;

public class CreateTask extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    public static int id=1;
    private Date convertDate(String d){
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        Date date=null;
        try {
            date = formatter.parse(d);
        } catch (ParseException e) {
        }
        return date;
    }

    public void sendemail(String email,String name,String description,String date){
        Intent i = new Intent(Intent.ACTION_SEND);
        String message="Hello there you Have a new task:\n"+name+":\n"+description+"\nDeadline:"+date.toString();
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , email);
        i.putExtra(Intent.EXTRA_SUBJECT, "New Task");
        i.putExtra(Intent.EXTRA_TEXT   , message);
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(CreateTask.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        TextView d=(TextView)findViewById(R.id.DeadlineText);
        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment fragment =new DatePickerFragment();
                fragment.show(getSupportFragmentManager(),"date");
            }
        });
        Spinner spinner = (Spinner) findViewById(R.id.EmployerText);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.employ, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                id=i+1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        Button sub=(Button)findViewById(R.id.Submitbutton);
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // try {
                    //FileOutputStream f=openFileOutput("tasks.txt", Context.MODE_APPEND);
                    //ObjectOutputStream bs= new ObjectOutputStream(f);
                    String name=((EditText)findViewById(R.id.TaskNameText)).getText().toString();
                    String description =((EditText)findViewById(R.id.DescriptionText)).getText().toString();
                    String date =((TextView)findViewById(R.id.DeadlineText)).getText().toString();
                    //bs.writeObject(new Task(1,0,id,name,description,convertDate(date)));
                    //bs.close();
                    //f.close();
                TaskArray.getInstance().add(new Task(1,0,id,name,description,convertDate(date)));
                    sendemail("aragon.raskolnikov@gmail.com",name,description,date);
               // } catch (IOException e) {
                  //  Toast.makeText(CreateTask.this, "Can't open file tasks.txt", Toast.LENGTH_SHORT).show();
                //}
            }
        });
    }

    private void setDate(final Calendar c){
        final DateFormat df=DateFormat.getDateInstance(DateFormat.SHORT);
        ((TextView)findViewById(R.id.DeadlineText)).setText(df.format(c.getTime()));
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        Calendar cal=new GregorianCalendar(i,i1,i2);
        setDate(cal);
    }

    public static class DatePickerFragment extends DialogFragment{
        @Override
        public Dialog onCreateDialog(Bundle saveInstanceState){
            final Calendar c=Calendar.getInstance();
            int year=c.get(Calendar.YEAR);
            int month=c.get(Calendar.MONTH);
            int day=c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(),(DatePickerDialog.OnDateSetListener)getActivity(),year,month,day);
        }
    }
}
