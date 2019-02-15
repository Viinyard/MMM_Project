package fr.istic.mmm.my_project;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

@IgnoreExtraProperties
public class Course implements Serializable {

    public String name;
    public String owner;

    public Course() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Course(String owner, String name) {
        this.owner = owner;
        this.name = name;
    }

    public String getOwner() {
        return this.owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
