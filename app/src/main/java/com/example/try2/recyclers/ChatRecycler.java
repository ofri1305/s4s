package com.example.try2.recyclers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.try2.R;
import com.example.try2.objects.Chat;

import java.util.ArrayList;

public class ChatRecycler extends RecyclerView.Adapter<ChatRecycler.ViewHolder> {
    private ArrayList<Chat> chats = new ArrayList<>();



    public ChatRecycler( ArrayList<Chat> chats) {
        this.chats = chats;

    }




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat,parent,false);
        return new ChatRecycler.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
      /*  holder.nameOfSender.setText(chats.get(position).getSender());*/
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nameOfSender,date,text;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameOfSender=itemView.findViewById(R.id.textView14);


        }
    }



}