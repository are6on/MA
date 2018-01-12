package Domain;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

/**
 * Created by Dragos on 12.11.2017.
 */
@IgnoreExtraProperties
public class Task implements Serializable{
    private String idt;
    private String idm;
    private String idp;
    private String name;
    private String description;
    private String deadline;

    public Task(){}
    public Task(String idt, String idm, String idp, String name, String description, String deadline){
        this.idt = idt;
        this.idm = idm;
        this.idp = idp;
        this.name = name;
        this.description = description;
        this.deadline = deadline;
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

    public String getIdm() {
        return idm;
    }

    public void setIdm(String idm) {
        this.idm = idm;
    }

    public String getIdp() {
        return idp;
    }

    public void setIdp(String idp) {
        this.idp = idp;
    }

    public String getIdt() {
        return idt;
    }

    public void setIdt(String idt) {
        this.idt = idt;
    }
}
