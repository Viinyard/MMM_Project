package fr.istic.mmm.my_project;

import android.content.Intent;
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

public class AdminCourseActivity extends AppCompatActivity {

    private TextView courseName;
    private Button btnCreatePoll, btnViewPoll, btnLogout, btnRemove;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_course);

        Intent intent = getIntent();
        final Course course = (Course) intent.getSerializableExtra("course");

        btnCreatePoll = (Button) findViewById(R.id.button_create_poll);
        btnRemove = (Button) findViewById(R.id.remove);
        courseName = (TextView) findViewById(R.id.course_name_tv);

        courseName.setText(course.getName());


        btnViewPoll = (Button) findViewById(R.id.view_poll);
        btnLogout = (Button) findViewById(R.id.logout);

        mAuth = FirebaseAuth.getInstance();


        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser == null) {
            startActivity(new Intent(AdminCourseActivity.this, MainActivity.class));
        }

        if(!course.getOwner().equals(currentUser.getEmail())) {
            btnCreatePoll.setEnabled(false);
            btnCreatePoll.setVisibility(View.INVISIBLE);
        }

        if(!currentUser.getEmail().equals(course.getOwner())) {
            btnRemove.setEnabled(false);
            btnRemove.setVisibility(View.INVISIBLE);
        }

        btnCreatePoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCourseActivity.this, CreationSondageActivity.class);
                intent.putExtra("course", course);
                startActivity(intent);
            }
        });

        btnViewPoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCourseActivity.this, ViewPollActivity.class);
                intent.putExtra("course", course);
                startActivity(intent);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(AdminCourseActivity.this, MainActivity.class));
            }
        });

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.child("course").child(course.name).removeValue();
                Toast.makeText(AdminCourseActivity.this, "Deleted successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


    }


}
