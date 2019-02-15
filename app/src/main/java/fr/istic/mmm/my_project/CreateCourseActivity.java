package fr.istic.mmm.my_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateCourseActivity extends AppCompatActivity {

    private Button btnCreateCourse;
    private EditText inputCourseName;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_course);

        btnCreateCourse = (Button) findViewById(R.id.create_course);
        inputCourseName = (EditText) findViewById(R.id.course_name);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser == null) {
            startActivity(new Intent(CreateCourseActivity.this, MainActivity.class));
        }

        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();



        btnCreateCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String courseName = inputCourseName.getText().toString().trim();

                Course course = new Course(mAuth.getCurrentUser().getEmail(), courseName);

                mDatabase.child("course").child(course.name).setValue(course);
                mDatabase.push();

                finish();
            }
        });
    }
}
