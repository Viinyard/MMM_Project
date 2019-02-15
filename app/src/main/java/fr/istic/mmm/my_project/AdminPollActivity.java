package fr.istic.mmm.my_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AdminPollActivity extends AppCompatActivity {

    private Button btnAnswering, btnViewResulsts;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_poll_acitivity);

        btnAnswering = (Button) findViewById(R.id.repondre_action);
        btnViewResulsts = (Button) findViewById(R.id.view_results_action);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        Intent intent = getIntent();
        final Course course = (Course) intent.getSerializableExtra("course");
        final Sondage sondage = (Sondage) intent.getSerializableExtra("sondage");

        if(currentUser == null) {
            startActivity(new Intent(AdminPollActivity.this, MainActivity.class));
        }

        btnAnswering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminPollActivity.this, ReponseSondageActivity.class);
                intent.putExtra("course", course);
                intent.putExtra("sondage", sondage);
                startActivity(intent);
            }
        });

        btnViewResulsts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminPollActivity.this, ViewResult.class);
                intent.putExtra("course", course);
                intent.putExtra("sondage", sondage);
                startActivity(intent);
            }
        });
    }
}
