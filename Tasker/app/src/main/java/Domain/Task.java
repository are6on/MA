package Domain;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Dragos on 12.11.2017.
 */
@Entity(tableName="Tasks",foreignKeys={@ForeignKey(entity = Person.class,
        parentColumns = "ID",
        childColumns = "Idm",
        onDelete = ForeignKey.CASCADE),@ForeignKey(entity = Person.class,
        parentColumns = "ID",
        childColumns = "Idp",
        onDelete = ForeignKey.CASCADE)},indices = { @Index(value = "ID"),@Index(value = "Idm"),@Index(value = "Idp")})
public class Task implements Serializable{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="ID")
    private int idt;
    @ColumnInfo(name="Idm")
    private int idm;
    @ColumnInfo(name="Idp")
    private int idp;
    @ColumnInfo(name="Name")
    private String name;
    @ColumnInfo(name="Description")
    private String description;
    @ColumnInfo(name = "Deadline")
    private String deadline;

    public Task(int idm,int idp, String name, String description, String deadline){
        this.idm = idm;
        this.idp = idp;
        this.name = name;
        this.description = description;
        this.deadline = deadline;
    }

    public int getIdt() {
        return idt;
    }

    public void setIdt(int idt) {
        this.idt = idt;
    }

    public int getIdm() {
        return idm;
    }

    public void setIdm(int idm) {
        this.idm = idm;
    }

    public int getIdp() {
        return idp;
    }

    public void setIdp(int idp) {
        this.idp = idp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }
}
