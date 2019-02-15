package fr.istic.mmm.my_project;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.List;

@IgnoreExtraProperties
public class Sondage implements Serializable {

    public String question;
    public List<String> reponses;

    public Sondage() {

    }

    public Sondage(String question, List<String> reponses) {
        this.question = question;
        this.reponses = reponses;
    }

    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append("[Sondage : ")
                .append(question)
                .append(",");
        int i = 0;
        for (String reponse : reponses) {
            builder.append(reponse);
            i++;
            if (i != reponses.size()) {
                builder.append(",");
            }
        }
        return builder.toString();
    }
}
