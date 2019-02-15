package fr.istic.mmm.my_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AdminCourseActivity extends AppCompatActivity {

    private Button btnCreatePoll, btnViewPoll, btnLogout;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_course);

        Intent intent = getIntent();
        Course course = (Course) intent.getSerializableExtra("course");

        btnCreatePoll = (Button) findViewById(R.id.button_create_poll);
        btnViewPoll = (Button) findViewById(R.id.view_poll);
        btnLogout = (Button) findViewById(R.id.logout);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser == null) {
            startActivity(new Intent(AdminCourseActivity.this, MainActivity.class));
        }

        btnCreatePoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminCourseActivity.this, CreateCourseActivity.class));
            }
        });

        btnViewPoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminCourseActivity.this, ViewCourseActivity.class));
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(AdminCourseActivity.this, MainActivity.class));
            }
        });



    }


}
