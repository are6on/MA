package com.example.dragos.tasker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import Dao.AppDatabase;
import Domain.Task;

/**
 * Created by Dragos on 22.12.2017.
 */

public class TaskAdaptor extends ArrayAdapter{

    List list=new ArrayList();
    public TaskAdaptor(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        list=objects;
    }

    static class DataHandler
    {
        TextView title;
        Button upb;
        Button delb;

    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public void remove(Object object){
        super.remove(object);
        list.remove(object);
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return this.list.get(position);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View row;
        row=convertView;
        DataHandler handler;
        if(convertView==null)
        {
            LayoutInflater inflater=(LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=inflater.inflate(R.layout.activity_list_item,parent,false);
            handler=new DataHandler();
            handler.title=(TextView)row.findViewById(R.id.name);
            handler.delb=(Button)row.findViewById(R.id.delete_button);
            handler.upb=(Button)row.findViewById(R.id.update_button);
            row.setTag(handler);
        }
        else
        {
            handler=(DataHandler)row.getTag();
        }
        String name;
        name=(String)((Task)this.getItem(position)).getName();
        handler.title.setText(name);
        handler.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task t=(Task)getItem(position);
                Intent intent = new Intent(getContext(),DPTask.class);
                intent.putExtra("EXTRA_ID",Integer.toString(t.getIdt()));
                Log.i("linepassed","entering DPTask");
                getContext().startActivity(intent);
            }
        });
        handler.upb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task t=(Task)getItem(position);
                Intent intent = new Intent(getContext(),ETaske.class);
                intent.putExtra("EXTRA_ID",Integer.toString(t.getIdt()));
                Log.i("linepassed","entering ETask");
                getContext().startActivity(intent);
            }
        });
        final AppDatabase db= AppDatabase.getDatabase(getContext());
        handler.delb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert=new AlertDialog.Builder(getContext())
                        .setMessage("Do you want to delete the task.")
                        .setTitle("Alert")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Task t=(Task)getItem(position);
                                db.taskDao().removeTask(t.getIdt());
                                remove(list.get(position));
                                notifyDataChanged();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                AlertDialog dialog = alert.create();
                dialog.show();
            }
        });
        return row;
    }

    private void notifyDataChanged() {
        this.notifyDataSetChanged();
    }
}
