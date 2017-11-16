package com.example.dragos.tasker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
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

import Domain.Task;
import Domain.TaskArray;

public class ETaske extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

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
        int i = Integer.parseInt(getIntent().getStringExtra("EXTRA_INDEX"));
        Log.i("linepassed", "95");

        TaskArray.getInstance().set(i, new Task(TaskArray.getInstance().get(i).getIdt(), 0, id,
                name, description, convertDate(date)));
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
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.employ, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        Log.i("linepassed","72");
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                id=i+1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Log.i("linepassed","111");
        EditText namee=(EditText)findViewById(R.id.ETaskNameText);
        EditText dese=(EditText)findViewById(R.id.EDescriptionText);
        Intent inte=getIntent();
        namee.setText(inte.getStringExtra("EXTRA_NAME"));
        dese.setText(inte.getStringExtra("EXTRA_DESCRIPTION"));
        Log.i("linepassed",inte.getStringExtra("EXTRA_IDE"));
        int pos= Integer.parseInt(inte.getStringExtra("EXTRA_IDE"));
        Log.i("linepassed","108");
        spinner.setSelection(pos-1);
        Log.i("linepassed","109");
        d.setText(inte.getStringExtra("EXTRA_DATE"));
        Log.i("linepassed","119");
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

