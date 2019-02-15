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

public class ViewPollActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private List<Sondage> listSondage = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_poll);

        recyclerView = (RecyclerView) findViewById(R.id.course_recycler_view);


        Intent intent = getIntent();
        final Course course = (Course) intent.getSerializableExtra("course");
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Sondage sondage = listSondage.get(position);
                Intent intent = new Intent(ViewPollActivity.this, AdminPollActivity.class);
                intent.putExtra("sondage", sondage);
                intent.putExtra("course", course);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), sondage.question + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();



        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listSondage.clear();
                // Get Post object and use the values to update the UI
                Iterator<DataSnapshot> it = dataSnapshot.child("sondage").child(course.getName()).getChildren().iterator();
                while(it.hasNext()) {
                    listSondage.add(it.next().getValue(Sondage.class));
                }

                mAdapter = new ViewPollActivity.MyCourseAdapter(listSondage, course);

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

    public class MyCourseAdapter extends RecyclerView.Adapter<ViewPollActivity.MyCourseAdapter.MyViewHolder> {
        private List<Sondage> mDataset;
        private Course course;

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
        public MyCourseAdapter(List<Sondage> myDataset, Course course) {
            mDataset = myDataset;
            this.course = course;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public ViewPollActivity.MyCourseAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                                  int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.course_view, parent, false);
            // TODO ...
            ViewPollActivity.MyCourseAdapter.MyViewHolder vh = new ViewPollActivity.MyCourseAdapter.MyViewHolder(v);
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewPollActivity.MyCourseAdapter.MyViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element

            Sondage sondage = listSondage.get(position);
            holder.courseName.setText(sondage.question);
            holder.courseOwner.setText(course.getOwner());
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataset.size();
        }
    }
}
