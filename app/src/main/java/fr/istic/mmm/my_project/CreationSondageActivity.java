package fr.istic.mmm.my_project;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CreationSondageActivity extends AppCompatActivity {

    EditText question;
    EditText nouvelleRep;
    Button ajout;
    ListView reponses;
    List<String> contenuReponses = new ArrayList<>();
    Button publier;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sondage_creation);

        question = findViewById(R.id.question_creation);
        nouvelleRep = findViewById(R.id.nouvelleRep);
        ajout = findViewById(R.id.ajout);
        reponses = findViewById(R.id.liste_creation);
        reponses.setItemsCanFocus(true);
        publier = findViewById(R.id.publier);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, contenuReponses);

        reponses.setAdapter(arrayAdapter);

        // Add une réponse en cliquant sur le bouton
        ajout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nouvelleRep.getText() != null && !nouvelleRep.getText().toString().isEmpty()) {
                    contenuReponses.add(nouvelleRep.getText().toString());
                    arrayAdapter.notifyDataSetChanged();
                }
            }
        });

        // Delete une réponse en cliquant dessus
        reponses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                arrayAdapter.remove(arrayAdapter.getItem(position));
            }
        });

        // Publie un sondage vers Firebase
        publier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> reponses = new LinkedList<>();
                for (int i = 0; i < arrayAdapter.getCount(); i++) {
                    String reponse = arrayAdapter.getItem(i);
                    reponses.add(reponse);
                }
                Sondage sondage = new Sondage(question.getText().toString(), reponses);
                System.out.println(sondage.toString());
            }
        });
    }
}

