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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.LinkedList;
import java.util.List;

public class ReponseSondageActivity extends AppCompatActivity {

    TextView question;
    ListView reponses;
    Button valider;
    String choix;

    private FirebaseAuth mAuth;

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
        final Sondage sondage = (Sondage) intent.getSerializableExtra("sondage");
        final Course course = (Course) intent.getSerializableExtra("course");

        mAuth = FirebaseAuth.getInstance();

        final FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser == null) {
            startActivity(new Intent(ReponseSondageActivity.this, MainActivity.class));
        }

        question.setText(sondage.question);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, sondage.reponses);

        reponses.setAdapter(arrayAdapter);

        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

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
                    Reponse reponse = new Reponse(currentUser.getEmail(), choix);
                    mDatabase.child("reponses").child(course.name).child(sondage.question).child(currentUser.getUid()).setValue(reponse);
                    mDatabase.push();
                    Intent intent = new Intent(ReponseSondageActivity.this, ViewPollActivity.class);
                    intent.putExtra("course", course);
                    intent.putExtra("sondage", sondage);

                    startActivity(intent);
                }
            }
        });
    }
}
