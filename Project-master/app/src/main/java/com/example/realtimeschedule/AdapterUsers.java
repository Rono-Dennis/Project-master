package com.example.realtimeschedule;

import android.os.Build;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.time.Instant;
import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

//import de.hdodenhof.circleimageview.CircleImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AdapterUsers extends RecyclerView.Adapter<AdapterUsers.MyHolder>{

    Context context;
    List<ModelUsers> usersList;

    //constructor
    public AdapterUsers(Context context, List<ModelUsers> usersList) {
        this.context = context;
        this.usersList = usersList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout user_details
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.userdetails, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        //get data
        String userName= usersList.get(position).getName();
        String userEmail= usersList.get(position).getEmail();
        String userPhone= usersList.get(position).getPhone();

        //set data
        holder.name.setText(userName);
        holder.email.setText(userEmail);
        holder.phone.setText(userPhone);
//
//        try{
//
//        }
//
//        }catch (Exception e){
        //item onclick
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,""+userEmail, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    //view holder class
    class MyHolder extends RecyclerView.ViewHolder{

        TextView name, email, phone;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            //initialize views
            name= itemView.findViewById(R.id.emailp);
            email= itemView.findViewById(R.id.namep);
            phone= itemView.findViewById(R.id.phonep);
        }
    }
// extends RecyclerView.Adapter<AdapterUsers.MyHolder> {
//
//    Context context;
//    FirebaseAuth firebaseAuth;
//    String uid;
//    private Instant Glide;
//
//    public AdapterUsers(Context context, List<ModelUsers> list) {
//        this.context = context;
//        this.list = list;
//        firebaseAuth = FirebaseAuth.getInstance();
//        uid = firebaseAuth.getUid();
//    }
//
//    List<ModelUsers> list;
//
//    @NonNull
//    @Override
//    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.userdetails, parent, false);
//        return new MyHolder(view);
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    @Override
//    public void onBindViewHolder(@NonNull MyHolder holder, final int position) {
//        final String hisuid = list.get(position).getUid();
//        String userImage = list.get(position).getImage();
//        String username = list.get(position).getName();
//        String usermail = list.get(position).getEmail();
//        holder.name.setText(username);
//        holder.email.setText(usermail);
////        try {
////            Glide.with(Context).load(userImage).into(holder.profiletv);
////        }
////        catch (Exception e) {
////        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return list.size();
//    }
//
//    class MyHolder extends RecyclerView.ViewHolder {
//
////        CircleImageView profiletv;
//        TextView name, email, phone;
//
//        public MyHolder(@NonNull View itemView) {
//            super(itemView);
////            profiletv = itemView.findViewById(R.id.imagep);
//            name = itemView.findViewById(R.id.namep);
//            email = itemView.findViewById(R.id.emailp);
//            phone= itemView.findViewById(R.id.phone);
//        }
//    }
}
