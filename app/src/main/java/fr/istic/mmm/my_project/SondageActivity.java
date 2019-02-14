package fr.istic.mmm.my_project;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;
public class SondageActivity extends AppCompatActivity {

    EditText question;
    EditText nbChoix;
    Button ajout;
    ListView reponses;
    MyAdapter adapter;
    List<Choix> reponsesList = new ArrayList<>();
    Button publish;

    int nb_questions = 4;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sondage);

        question = findViewById(R.id.question);
        reponses = findViewById(R.id.reponses);
        reponses.setItemsCanFocus(true);
        publish = findViewById(R.id.publish);
        nbChoix = findViewById(R.id.nbChoix);
        ajout = findViewById(R.id.ajout);

        ajout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nbChoix.getText() != null && !nbChoix.getText().toString().isEmpty()) {

                }
            }
        });

        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder builder = new StringBuilder();
                builder.append("[\"choices\":");
                builder.append("{");
                int i = 0;
                for (Choix c : reponsesList) {
                    builder.append("\"").append(c.getValeur()).append("\"");
                    i++;
                    if (i < reponsesList.size()) {
                        builder.append(",");
                    }
                }
                builder.append("},\"question\":\"").append(question.getText().toString()).append("\"}]");
                System.out.println(builder.toString());
            }
        });



        reponses.setAdapter(adapter);
    }

    private List<Choix> modifyChoix() {
        int size = reponsesList.size();
        if (nb_questions > size) {
            for (int i = 0; i < nb_questions - size; i++) {
                reponsesList.add(new Choix(""));
            }
        } else {
            for (int i = 0; i < size - nb_questions; i++) {
                reponsesList.remove(reponsesList.size() - 1);
            }
        }
        return reponsesList;
    }
}

