package Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import Domain.Person;

/**
 * Created by Dragos on 02.01.2018.
 */

@Dao
public interface PersonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addPerson(Person person);

    @Query("select * from Persons where Role=1")
    public List<Person> getPersons();

    @Query("select * from Persons where Name=:name")
    public Person getPerson(String name);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    public void updatePerson(Person person);

    @Query("select * from Persons where ID=:id")
    public Person getPerson(long id);

    @Query("delete from Persons where ID=:id")
    void removePerson(long id);
}

