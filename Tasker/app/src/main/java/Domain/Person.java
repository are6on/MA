package Domain;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Dragos on 02.01.2018.
 */
@Entity(tableName = "Persons",indices = { @Index(value = "ID")})
public class Person {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private int id;
    @ColumnInfo(name = "Name")
    private String Name;
    @ColumnInfo(name = "Address")
    private String address;
    @ColumnInfo(name = "Role")
    private int role;

    public Person(String Name,String address, int role){
        this.address=address;
        this.Name=Name;
        this.role=role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
