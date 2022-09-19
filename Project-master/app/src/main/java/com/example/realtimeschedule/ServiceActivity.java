package com.example.realtimeschedule;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.realtimeschedule.ViewHolder.ServiceViewHolder;
import com.example.realtimeschedule.model.Services;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

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


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel("Notification","Notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }
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

                        final String  email = "ronodennis580@gmail.com";//vdmvfjtrlgjkgwbe
                        final String password = "vdmvfjtrlgjkgwbe";//3?3?9!1!2.1.1,8,R
                        String messageToSend = "Hello Sir, Please check the appointments that have been send to you";
                        Properties  properties = new Properties();
                        properties.put("mail.smtp.auth","true");
                        properties.put("mail.smtp.ssl.enable","true");
                        properties.put("mail.smtp.host","smtp.gmail.com");
                        properties.put("mail.smtp.port","465");

                        Session session = Session.getInstance(properties, new Authenticator() {
                            @Override
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return  new PasswordAuthentication(email, password);
                                /*return super.getPasswordAuthentication();*/
                            }
                        });
                        try {
                            MimeMessage message = new MimeMessage(session);
                            message.setFrom(new InternetAddress(email));
                            message.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse("ronodennis72@gmail.com"));
                            message.setSubject("Booking appointments");
                            message.setText(messageToSend);
                            Transport.send(message);
                            Toast.makeText(ServiceActivity.this, "message sent successfully", Toast.LENGTH_SHORT).show();
                        } catch (AddressException e) {
                            e.printStackTrace();
                        } catch (MessagingException e) {
                            e.printStackTrace();
                        }

                    }
                });

                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

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
