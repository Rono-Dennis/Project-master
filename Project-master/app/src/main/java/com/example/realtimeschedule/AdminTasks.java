package com.example.realtimeschedule;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;

import com.example.realtimeschedule.databinding.ActivityAdminTasksBinding;

public class AdminTasks extends MenuContainer {
    ActivityAdminTasksBinding activityAdminTasksBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAdminTasksBinding= ActivityAdminTasksBinding.inflate(getLayoutInflater());
        setContentView(activityAdminTasksBinding.getRoot());
    }
}