package com.example.try2;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.Date;

import javax.annotation.Nullable;

public class FragmentMaterial extends Fragment {
    StorageReference storageReference;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_material, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button button=getView().findViewById(R.id.upload_file);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile();

            }
        });
    }

    public void uploadFile(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        Intent i = Intent.createChooser(intent, "File");
        startActivityForResult(i, 15);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==15){
            if(resultCode== Activity.RESULT_OK){
                Uri imageUri = data.getData();
                uploadImageToFirebase(imageUri);
            }
        }
    }
    private void uploadImageToFirebase(Uri imageUri) {
        String nameOfCourse=getArguments().getString("nameOfCourse");
        Long date=new Date().getTime();
        Log.i("nameOfCourse", "uploadImageToFirebase: "+nameOfCourse);
        StorageReference fileRef = storageReference.child("nameOfCourse"+"/material/"+"date");
        fileRef.putFile(imageUri).addOnSuccessListener((OnSuccessListener)(taskSnapshot)->{
            fileRef.getDownloadUrl().addOnSuccessListener((OnSuccessListener)(uri)->{
              Toast.makeText(getContext(), "ffffff", Toast.LENGTH_LONG).show();
            });
        }).addOnFailureListener((e) -> {

        });
    }
}
