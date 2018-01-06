package Dao;

import Domain.Person;
import Domain.Task;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by Dragos on 02.01.2018.
 */


@Database(entities = {Person.class, Task.class}, version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract PersonDao personDao();
    public abstract TaskDao taskDao();

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =Room.databaseBuilder(context, AppDatabase.class, "taskdb")
                    .allowMainThreadQueries().build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}