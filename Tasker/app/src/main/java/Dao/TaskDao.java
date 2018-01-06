package Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import Domain.Task;

/**
 * Created by Dragos on 02.01.2018.
 */

@Dao
public interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addTask(Task task);

    @Query("select * from Tasks where Idm=:id")
    public List<Task> getTaskByMananger(long id);

    @Query("select * from Tasks where Idp = :id")
    public List<Task> getTaskByPerson(long id);

    @Query("select * from Tasks where ID=:id")
    public List<Task> getTask(long id);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateTask(Task task);

    @Query("select COUNT(ID) from Tasks where Idp=:id")
    long getnroftasks(long id);

    @Query("delete from Tasks where ID=:id")
    void removeTask(long id);
}
