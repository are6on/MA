package Domain;

import android.util.Log;
import android.widget.Toast;

import com.example.dragos.tasker.TaskList;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import Domain.Task;

/**
 * Created by Dragos on 12.11.2017.
 */

public class TaskArray {
    public static List<Task> tasks;
    public static Person person;
    private TaskArray(){}
    public static List<Task> getInstance(){
        if(tasks==null) {
            tasks= new ArrayList<>();
        }
        Log.i("size:",Integer.toString(tasks.size()));
        return tasks;
    }
}
