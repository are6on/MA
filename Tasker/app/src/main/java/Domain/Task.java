package Domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Dragos on 12.11.2017.
 */

public class Task implements Serializable{
    private int idt;
    private int idm;
    private int idp;
    private String name;
    private String description;
    private Date deadline;

    public Task(int idt,int idm,int idp, String name, String description, Date deadline){
        this.idt=idt;
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

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }
}
