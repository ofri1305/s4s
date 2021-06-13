package com.example.try2;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.try2.objects.Material;
import com.example.try2.objects.Meme;
import com.example.try2.recyclers.MaterialRecycler;
import com.example.try2.recyclers.MemeRecycler;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

import javax.annotation.Nullable;

public class FragmentMemes extends Fragment {
    StorageReference storageReference;
    FirebaseStorage storage ;
    FirebaseFirestore fStore;
    private String nameOfCourse;
    ImageView memePhoto;
    //TextView whoPosted;
    FirebaseAuth fAuth;
    private ArrayList<Meme> memes;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_memes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        fStore= FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        memePhoto =getView().findViewById(R.id.memePhoto);
        //whoPosted = getView().findViewById(R.id.memeUserName);
        nameOfCourse=getArguments().getString("nameOfCourse");
        Button postMeme=getView().findViewById(R.id.addMemeButton);
        loadAllMemes();
        postMeme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadMeme();
            }
        });
    }

    private void loadAllMemes() {
        fStore.collection(nameOfCourse).document("meme").collection("memesObjects").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                memes = (ArrayList<Meme>) task.getResult().toObjects(Meme.class);
                setRecyclerView();
            }
        });
    }
    public void uploadMeme(){
        Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(openGallery, 1000);
    }
    @Override
    public void onActivityResult(int requestCode,int resultCode,@androidx.annotation.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1000){
            if(resultCode== Activity.RESULT_OK){
                Uri imageUri = data.getData();
                uploadImageToFirebase(imageUri);
            }
        }
    }
    private void uploadImageToFirebase(Uri imageUri) {
        Long date = new Date().getTime();
        StorageReference fileRef = storageReference.child(nameOfCourse+"/meme/"+date);
        fileRef.putFile(imageUri).addOnSuccessListener((OnSuccessListener)(taskSnapshot)->{
            fileRef.getDownloadUrl().addOnSuccessListener((OnSuccessListener)(uri)->{
                Meme meme = new Meme(uri.toString(),fAuth.getCurrentUser().toString());
                Picasso.get().load((Uri) uri).into(memePhoto);
                fStore.collection(nameOfCourse).document("meme").collection("memesObjects").document(date.toString()).set(meme);
                memes.add(meme);
                setRecyclerView();
            });
        }).addOnFailureListener((e) -> {
            //Toast.makeText(EditProfile.this, "Failed", Toast.LENGTH_SHORT).show();
        });

    }
    private void setRecyclerView() {
        RecyclerView recyclerView = getView().findViewById(R.id.memes_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        MemeRecycler recycler= new MemeRecycler(memes,nameOfCourse,getContext());
        recyclerView.setAdapter(recycler);
    }
}
