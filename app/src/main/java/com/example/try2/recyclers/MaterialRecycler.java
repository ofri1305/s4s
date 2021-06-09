package com.example.try2.recyclers;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.try2.R;
import com.example.try2.objects.Material;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MaterialRecycler  extends RecyclerView.Adapter<MaterialRecycler.ViewHolder> {
    private ArrayList<Material>materials;
    private String nameOfCourse;
    private Context context;
    public  MaterialRecycler(ArrayList<Material>materials,String nameOfCourse,Context context){
        this.materials=materials;
        this.nameOfCourse=nameOfCourse;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.material,parent,false);
        return new MaterialRecycler.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(context,android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    DownloadManager downloadmanager = (DownloadManager)context.
                            getSystemService(Context.DOWNLOAD_SERVICE);
                    Uri uri = Uri.parse(materials.get(position).getUrlToFile());
                    DownloadManager.Request request = new DownloadManager.Request(uri);

                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    request.setDestinationInExternalPublicDir( Environment.DIRECTORY_DOWNLOADS, ""+materials.get(position).getNameOfFile());

                    downloadmanager.enqueue(request);
                    Toast.makeText(context,"ההורדה מתבצעת, בסיום ההורדה הקובץ יהיה בתקייה הורדות.",Toast.LENGTH_LONG).show();
                }else
                    ActivityCompat.requestPermissions((Activity)context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);



            }
        });
        holder.description.setText(""+materials.get(position).getDescription());

    }

    @Override
    public int getItemCount() {
        return materials.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView description;
        public Button materialButton;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            description = itemView.findViewById(R.id.description_material);
            materialButton = itemView.findViewById(R.id.material_download);


        }
    }
}
