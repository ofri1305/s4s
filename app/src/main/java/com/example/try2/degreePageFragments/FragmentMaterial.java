package com.example.try2.degreePageFragments;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.try2.R;
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
    private EditText description;
    private String nameOfCourse;
    private ArrayList<Material>materials;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return inflater.inflate(R.layout.fragment_material, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        fStore= FirebaseFirestore.getInstance();
        nameOfCourse=getArguments().getString("nameOfCourse");
        description=getView().findViewById(R.id.description_material);
        Button button=getView().findViewById(R.id.upload_file);
        loadAllMaterials();
        searchItem();
        button.setOnClickListener(v -> uploadFile());
    }

    private void loadAllMaterials() {
        fStore.collection(nameOfCourse).document("material").collection("materialsObjects").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                materials= (ArrayList<Material>) task.getResult().toObjects(Material.class);
                setRecyclerView(materials);

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
                Material material=new Material(uri.toString(),description.getText().toString(),date.toString(),typeOfFile);
                fStore.collection(nameOfCourse).document("material").collection("materialsObjects").document(date.toString()).set(material);
                materials.add(material);
                setRecyclerView(materials);
            });
        }).addOnFailureListener((e) -> {

        });
    }

    private void searchItem() {
        EditText editText = getView().findViewById(R.id.search_item);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayList<Material> textToCheckList = new ArrayList<>();
                String textToCheck = s.toString();
                if (textToCheck.length() != 0) {
                //materials.stream().filter(m -> textToCheck.equals(m.getDescription()));
                    for (Material searchModel :materials) {
                        if (searchModel.getDescription().toLowerCase().contains(textToCheck.toLowerCase())) {
                            textToCheckList.add(searchModel);
                        }
                    }
                } else {
                    textToCheckList.addAll(materials);
                }

                // Setting new list to adapter and notifying it
                setRecyclerView(textToCheckList);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setRecyclerView(ArrayList<Material>materials) {
        RecyclerView recyclerView = getView().findViewById(R.id.material_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        MaterialRecycler recycler= new MaterialRecycler(materials,nameOfCourse,getContext());
        recyclerView.setAdapter(recycler);
    }
}
