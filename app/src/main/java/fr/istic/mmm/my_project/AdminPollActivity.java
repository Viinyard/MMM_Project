package fr.istic.mmm.my_project;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminPollActivity extends AppCompatActivity {

    private TextView pollName;
    private Button btnAnswering, btnViewResulsts, btnLogout, btnRemove;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_poll_acitivity);

        btnAnswering = (Button) findViewById(R.id.repondre_action);
        btnViewResulsts = (Button) findViewById(R.id.view_results_action);
        btnLogout = (Button) findViewById(R.id.logout);
        btnRemove = (Button) findViewById(R.id.remove);
        pollName = (TextView) findViewById(R.id.poll_name_tv);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();



        Intent intent = getIntent();
        final Course course = (Course) intent.getSerializableExtra("course");
        final Sondage sondage = (Sondage) intent.getSerializableExtra("sondage");

        pollName.setText(sondage.question);

        if(currentUser == null) {
            startActivity(new Intent(AdminPollActivity.this, MainActivity.class));
        }

        if(!currentUser.getEmail().equals(course.getOwner())) {
            btnRemove.setEnabled(false);
            btnRemove.setVisibility(View.INVISIBLE);
        }

        btnAnswering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminPollActivity.this, ReponseSondageActivity.class);
                intent.putExtra("course", course);
                intent.putExtra("sondage", sondage);
                startActivityForResult(intent, 10);
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

        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.child("sondage").child(course.name).child(sondage.question).removeValue();
                Toast.makeText(AdminPollActivity.this, "Deleted successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(AdminPollActivity.this, MainActivity.class));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 10 && resultCode == RESULT_OK) {
            finish();
        }
    }
}
