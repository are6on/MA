package Domain;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Dragos on 02.01.2018.
 */
@IgnoreExtraProperties
public class Person {
    private String id;
    private String Name;
    private String address;
    private int role;

    public Person(){}
    public Person(String id,String Name,String address, int role){
        this.address=address;
        this.Name=Name;
        this.role=role;
        this.id=id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
