package Domain;

import android.util.Log;
import android.widget.Toast;

import com.example.dragos.tasker.TaskList;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import Domain.Task;

/**
 * Created by Dragos on 12.11.2017.
 */

public class TaskArray {
    public static Person person;
    public static FirebaseDatabase db;
    private TaskArray(){}
    public static FirebaseDatabase getInstance(){
        if(db==null) {
            db=FirebaseDatabase.getInstance();
            db.setPersistenceEnabled(true);
        }
        return db;
    }
}
