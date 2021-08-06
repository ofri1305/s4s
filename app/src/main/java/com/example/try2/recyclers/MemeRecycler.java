package com.example.try2.recyclers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
//import org.jetbrains.annotations.NotNull;


import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.example.try2.R;
import com.example.try2.objects.Meme;
import com.squareup.picasso.Picasso;


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
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meme,parent,false);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meme2,parent,false);
        return new MemeRecycler.ViewHolder(view);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull  MemeRecycler.ViewHolder holder, int position) {
        holder.whoPosted.setText(memes.get(position).getUserName().toUpperCase());
        Uri imgUri=Uri.parse(memes.get(position).getUriToImage());

        Glide
                .with(holder.image)
                .load(imgUri)
                .into(holder.image);
//        .placeholder(R.raw.loading)
    }

    @Override
    public int getItemCount() {
        return memes.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView whoPosted;
        public ImageView image;
        private LottieAnimationView lottieAnimationView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//            whoPosted = itemView.findViewById(R.id.memeUserName);
//            image = itemView.findViewById(R.id.memePhoto);
            lottieAnimationView = itemView.findViewById(R.id.animationView);
            whoPosted = itemView.findViewById(R.id.userMeme2);
            image = itemView.findViewById(R.id.postMeme2);

        }
    }
}
