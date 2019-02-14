package fr.istic.mmm.my_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

public class ReponseSondageActivity extends AppCompatActivity {

    TextView question;
    ListView reponses;
    Button valider;
    String choix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reponse_sondage);

        question = findViewById(R.id.question_reponse);
        reponses = findViewById(R.id.liste_reponse);
        reponses.setItemsCanFocus(true);
        valider = findViewById(R.id.valider);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        Sondage sondage = (Sondage) intent.getSerializableExtra("sondage");

        // mock
        LinkedList<String> liste = new LinkedList<>();
        liste.add("oui");
        liste.add("non");
        liste.add("je ne sais pas");
        sondage = new Sondage("Tu l'as trop écrasé, César, ce Port-Salut ?", liste);

        question.setText(sondage.question);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, sondage.reponses);

        reponses.setAdapter(arrayAdapter);

        // Sélectionne la réponse et affiche un background coloré
        reponses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                view.setSelected(true);
                choix = arrayAdapter.getItem(position);
            }
        });

        // Valide la réponse et l'envoie vers Firebase
        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (choix != null && !choix.isEmpty()) {
                    // TODO : Firebase
                }
            }
        });
    }
}
