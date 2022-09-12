package com.example.realtimeschedule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AdminAddNewServiceActivity extends AppCompatActivity {
    private String CategoryName, Description, Price, Sname, saveCurrentDate, saveCurrentTime;
    private Button AddNewServiceButton;
    private ImageView InputServiceImage;
    private EditText InputSeviceName, InputProductDescription, InputProductPrice;
    private static final int GalleryPick = 1;
    private Uri ImageUri;
    private String serviceRandomKey, downloadImageUrl;
    private StorageReference ServiceImagesRef;
    private DatabaseReference ServiceRef;
    private ProgressDialog loadingBar;
    DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new_service);

        AddNewServiceButton = (Button) findViewById(R.id.add_new_service);
        InputServiceImage = (ImageView) findViewById(R.id.select_service_image);
        InputSeviceName = (EditText) findViewById(R.id.service_name);
        loadingBar = new ProgressDialog(this);

        userRef= FirebaseDatabase.getInstance().getReference();

        ServiceImagesRef = FirebaseStorage.getInstance().getReference().child("Services Images");
        ServiceRef = FirebaseDatabase.getInstance().getReference().child("services");

        InputServiceImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenGallery();
            }
        });

        AddNewServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidateProductData();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(AdminAddNewServiceActivity.this, AddService.class);

        startActivity(intent);
    }

    private void ValidateProductData()
    {
        Sname = InputSeviceName.getText().toString();

        if (ImageUri == null)
        {
            Toast.makeText(this, "Service image is mandatory...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Sname))
        {
            Toast.makeText(this, "Please write service name...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            StoreProductInformation();
        }
    }

    private void StoreProductInformation()
    {
        loadingBar.setTitle("Adding New Service");
        loadingBar.setMessage("Dear Admin, please wait while we are adding the new service.");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        java.util.Calendar calender = Calendar.getInstance();

        java.text.SimpleDateFormat currentDate = new java.text.SimpleDateFormat("MM dd, yyyy");
        saveCurrentDate = currentDate.format(calender.getTime());

        java.text.SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime= currentTime.format((calender.getTime()));

        serviceRandomKey = saveCurrentDate + saveCurrentTime;

        final StorageReference filePath = ServiceImagesRef.child(ImageUri.getLastPathSegment() + serviceRandomKey + ".jpg");

        final UploadTask uploadTask = filePath.putFile(ImageUri);


        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                String message = e.toString();
                Toast.makeText(AdminAddNewServiceActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
                Toast.makeText(AdminAddNewServiceActivity.this, "Service Image uploaded Successfully...", Toast.LENGTH_SHORT).show();

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                    {
                        if (!task.isSuccessful())
                        {
                            throw task.getException();
                        }

                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task)
                    {
                        if (task.isSuccessful())
                        {
                            downloadImageUrl = task.getResult().toString();

                            Toast.makeText(AdminAddNewServiceActivity.this, "got the Service image Url Successfully...", Toast.LENGTH_SHORT).show();

                            SaveServiceInfoToDatabase();
                        }
                    }
                });
            }
        });
    }

    private void SaveServiceInfoToDatabase() {
        Toast.makeText(this, "Inserting data", Toast.LENGTH_SHORT).show();

        HashMap<String, Object> serviceMap = new HashMap<>();
        serviceMap.put("pid", serviceRandomKey);
        serviceMap.put("date", saveCurrentDate);
        serviceMap.put("time", saveCurrentTime);
        serviceMap.put("image", downloadImageUrl);
        serviceMap.put("servicename", Sname);

        //set path
        userRef.child("Services").child(serviceRandomKey).setValue(serviceMap)

//        ServiceRef.child(serviceRandomKey).updateChildren(serviceMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
                            Intent intent = new Intent(AdminAddNewServiceActivity.this, AddService.class);
                            startActivity(intent);

                            loadingBar.dismiss();
                            Toast.makeText(AdminAddNewServiceActivity.this, "Service is added successfully..", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            loadingBar.dismiss();
                            String message = task.getException().toString();
                            Toast.makeText(AdminAddNewServiceActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void OpenGallery()
    {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==GalleryPick  &&  resultCode==RESULT_OK  &&  data!=null)
        {
            ImageUri = data.getData();
            InputServiceImage.setImageURI(ImageUri);
        }
    }

}