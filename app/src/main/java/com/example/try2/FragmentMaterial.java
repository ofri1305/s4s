package com.example.try2;

import android.app.Activity;
import android.content.ContentResolver;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.try2.objects.Chat;
import com.example.try2.objects.Material;
import com.example.try2.recyclers.ChatRecycler;
import com.example.try2.recyclers.MaterialRecycler;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

import javax.annotation.Nullable;

public class FragmentMaterial extends Fragment {
    StorageReference storageReference;
    FirebaseStorage storage ;
    FirebaseFirestore fStore;
    private String nameOfCourse;
    private ArrayList<Material>materials;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_material, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        fStore= FirebaseFirestore.getInstance();
        nameOfCourse=getArguments().getString("nameOfCourse");
        Button button=getView().findViewById(R.id.upload_file);
        loadAllMaterials();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile();

            }
        });
    }

    private void loadAllMaterials() {
        fStore.collection(nameOfCourse).document("material").collection("materialsObjects").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                materials= (ArrayList<Material>) task.getResult().toObjects(Material.class);
                setRecyclerView();

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
                ContentResolver cr = this.getActivity().getContentResolver();
                String mime = cr.getType(imageUri);
                uploadImageToFirebase(imageUri,mime);
            }
        }
    }
    private void uploadImageToFirebase(Uri imageUri,String typeOfFile) {

        Long date=new Date().getTime();
        StorageReference fileRef = storageReference.child(nameOfCourse+"/material/"+date);
        fileRef.putFile(imageUri).addOnSuccessListener((OnSuccessListener)(taskSnapshot)->{
            fileRef.getDownloadUrl().addOnSuccessListener((OnSuccessListener)(uri)->{
                Material material=new Material(uri.toString(),"ttt",date.toString(),typeOfFile);
                fStore.collection(nameOfCourse).document("material").collection("materialsObjects").document(date.toString()).set(material);
                materials.add(material);
                setRecyclerView();
            });
        }).addOnFailureListener((e) -> {

        });
    }

    private void setRecyclerView() {
        RecyclerView recyclerView = getView().findViewById(R.id.material_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        MaterialRecycler recycler= new MaterialRecycler(materials,nameOfCourse,getContext());
        recyclerView.setAdapter(recycler);
    }
}
