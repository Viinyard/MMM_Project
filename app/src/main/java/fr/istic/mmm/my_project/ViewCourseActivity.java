package fr.istic.mmm.my_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ViewCourseActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private List<Course> listCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_course);


        recyclerView = (RecyclerView) findViewById(R.id.course_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        listCourse = new ArrayList<>();
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Course course = listCourse.get(position);
                Intent intent = new Intent(ViewCourseActivity.this, AdminCourseActivity.class);
                intent.putExtra("course", course);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), course.getName() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();



        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                listCourse.clear();
                Iterator<DataSnapshot> it = dataSnapshot.child("course").getChildren().iterator();
                while(it.hasNext()) {
                    listCourse.add(it.next().getValue(Course.class));
                }

                mAdapter = new MyCourseAdapter(listCourse);

                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message

            }
        };
        mDatabase.addValueEventListener(postListener);

        // specify an adapter (see also next example)


    }

    public class MyCourseAdapter extends RecyclerView.Adapter<MyCourseAdapter.MyViewHolder> {
        private List<Course> mDataset;

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView courseName, courseOwner;
            // each data item is just a string in this case
            public TextView textView;
            public MyViewHolder(View view) {
                super(view);
                courseName = (TextView) view.findViewById(R.id.course_name);
                courseOwner = (TextView) view.findViewById(R.id.course_owner);
            }
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public MyCourseAdapter(List<Course> myDataset) {
            mDataset = myDataset;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public MyCourseAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.course_view, parent, false);
            // TODO ...
            MyViewHolder vh = new MyViewHolder(v);
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element

            Course course = listCourse.get(position);
            holder.courseName.setText(course.getName());
            holder.courseOwner.setText(course.getOwner());
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataset.size();
        }
    }
}
