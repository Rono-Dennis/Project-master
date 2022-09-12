package com.example.realtimeschedule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.realtimeschedule.ViewHolder.ServiceViewHolder;
import com.example.realtimeschedule.ViewHolder.UserViewHolder;
import com.example.realtimeschedule.model.Services;
import com.example.realtimeschedule.model.User;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ServiceActivity extends AppCompatActivity {
    DatabaseReference ServicesRef;
    EditText inputSearch;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        recyclerView = findViewById(R.id.service_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
    }
    @Override
    protected void onStart() {
        super.onStart();

        final DatabaseReference usersref = FirebaseDatabase.getInstance().getReference().child("Services");

        FirebaseRecyclerOptions<Services> options = new FirebaseRecyclerOptions.Builder<Services>()
                .setQuery(usersref, Services.class)
                .build();

        FirebaseRecyclerAdapter<Services, ServiceViewHolder> adapter = new FirebaseRecyclerAdapter<Services, ServiceViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ServiceViewHolder serviceViewHolder, int i, @NonNull Services services) {

                serviceViewHolder.txtServiceName.setText("Name: "+services.getSname());
                Picasso.get().load(services.getImage()).into(serviceViewHolder.imageView);

                serviceViewHolder.book.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent= new Intent(ServiceActivity.this, BookService.class);
                        startActivity(intent);
                    }
                });

            }

            @NonNull
            @Override
            public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_items_layout, parent, false);
                ServiceViewHolder holder = new ServiceViewHolder(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }
}
