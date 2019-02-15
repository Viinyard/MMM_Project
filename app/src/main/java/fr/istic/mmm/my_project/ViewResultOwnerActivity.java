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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Repo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ViewResultOwnerActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private List<Reponse> listReponse = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_course);

        recyclerView = (RecyclerView) findViewById(R.id.course_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        Intent intent = getIntent();
        final Course course = (Course) intent.getSerializableExtra("course");
        final Sondage sondage = (Sondage) intent.getSerializableExtra("sondage");
        final String reponse = intent.getStringExtra("reponse");

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                Iterator<DataSnapshot> it = dataSnapshot.child("reponses").child(course.getName()).child(sondage.question).getChildren().iterator();

                while(it.hasNext()) {
                    Reponse rep = it.next().getValue(Reponse.class);
                    if(rep.getReponse().equals(reponse)) {
                        listReponse.add(rep);
                    }
                }

                mAdapter = new ViewResultOwnerActivity.MyCourseAdapter(listReponse);

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

    public class MyCourseAdapter extends RecyclerView.Adapter<ViewResultOwnerActivity.MyCourseAdapter.MyViewHolder> {
        private List<Reponse> mDataset;

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
        public MyCourseAdapter(List<Reponse> myDataset) {
            mDataset = myDataset;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public ViewResultOwnerActivity.MyCourseAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                          int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.course_view, parent, false);
            // TODO ...
            ViewResultOwnerActivity.MyCourseAdapter.MyViewHolder vh = new ViewResultOwnerActivity.MyCourseAdapter.MyViewHolder(v);
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewResultOwnerActivity.MyCourseAdapter.MyViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element

            Reponse reponse = listReponse.get(position);
            holder.courseName.setText(reponse.getOwner());
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataset.size();
        }
    }
}
