package com.example.try2.recyclers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
//import org.jetbrains.annotations.NotNull;



import com.example.try2.R;
import com.example.try2.objects.Meme;


import java.util.ArrayList;

public class MemeRecycler  extends RecyclerView.Adapter<MemeRecycler.ViewHolder>{
    private ArrayList<Meme> memes;
    private String nameOfCourse;
    private Context context;
    public MemeRecycler(ArrayList<Meme> memes, String nameOfCourse, Context context){
        this.memes = memes;
        this.nameOfCourse = nameOfCourse;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meme,parent,false);
        return new MemeRecycler.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  MemeRecycler.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return memes.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView whoPosted;
        public ImageView image;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            whoPosted = itemView.findViewById(R.id.memeUserName);
            image = itemView.findViewById(R.id.memePhoto);


        }
    }
}