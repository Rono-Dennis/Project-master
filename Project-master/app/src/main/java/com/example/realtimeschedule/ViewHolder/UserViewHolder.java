package com.example.realtimeschedule.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.realtimeschedule.Interface.ItemClickListener;
import com.example.realtimeschedule.R;


public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
    public TextView User_Name, Phones,email, User_Id;
    public ItemClickListener itemClickListener;
    public UserViewHolder(@NonNull View itemView) {
        super(itemView);
        User_Name=itemView.findViewById(R.id.user_Name);
        Phones=itemView.findViewById(R.id.Phone);
        email=itemView.findViewById(R.id.Email);
        User_Id=itemView.findViewById(R.id.User_Id);
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(),false);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
