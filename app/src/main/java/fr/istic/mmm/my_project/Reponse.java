package fr.istic.mmm.my_project;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

@IgnoreExtraProperties
public class Reponse implements Serializable {

    private String owner;
    private String reponse;

    public Reponse() {

    }

    public Reponse(String owner, String reponse) {
        this.owner = owner;
        this.reponse = reponse;
    }

    public String getOwner() {
        return this.owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getReponse() {
        return this.reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }
}
