package com.example.realtimeschedule.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.realtimeschedule.Interface.ItemClickListener;
import com.example.realtimeschedule.R;

public class ServiceViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtServiceName;
    public ImageView imageView;
    public ItemClickListener listener;
    public Button book;

    public ServiceViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView = (ImageView) itemView.findViewById(R.id.services_image);
        txtServiceName = (TextView) itemView.findViewById(R.id.services_name);
        book=itemView.findViewById(R.id.book_services);
    }

    public void setItemClickListener(ItemClickListener listener)
    {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        listener.onClick(view, getAdapterPosition(), false);
    }
}
