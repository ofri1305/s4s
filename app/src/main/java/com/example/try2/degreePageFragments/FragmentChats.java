package com.example.try2.degreePageFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Nullable;

import static com.example.try2.utils.Utils.globalUser;


public class FragmentChats extends Fragment {
    StorageReference storageReference;
    FirebaseStorage storage;
    FirebaseFirestore fStore;
    EditText textToSend;
    Button sendButton;
    private String nameOfCourse;
    private ArrayList<Chat> chats;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chats, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        fStore = FirebaseFirestore.getInstance();
        textToSend = view.findViewById(R.id.textToSend);
        sendButton = view.findViewById(R.id.buttonSend);
        nameOfCourse = getArguments().getString("nameOfCourse");
        sendButton.setOnClickListener(v -> setComponents());
        chats=new ArrayList<>();
        liveChats();

        //setRecyclerView();

    }

    private void loadAllChats() {
        //load all chats
        fStore.collection(nameOfCourse).document("chats").collection("chatsObjects").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                chats = (ArrayList<Chat>) task.getResult().toObjects(Chat.class);
                setRecyclerView();

            }
        });
    }

    private void liveChats() {
        fStore.collection(nameOfCourse).
                document("chats").
                collection("chatsObjects").
                addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            return;
                        }


                        try {

                            chats= (ArrayList<Chat>) value.toObjects(Chat.class);
                        }catch (Exception e){
                            e.printStackTrace();
                        }


                        setRecyclerView();
                    }
                });
    }

    private void setComponents() {
        String username = globalUser.getFirstName().concat(" " + globalUser.getLastName());
        Chat chat = new Chat(username, textToSend.getText().toString(), new Date().toString());
        long date = new Date().getTime();
        fStore.collection(nameOfCourse).
                document("chats").
                collection("chatsObjects").
                document("" + date).
                set(chat);
    }


    private void setRecyclerView() {
        RecyclerView recyclerView = getView().findViewById(R.id.chat_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());


        recyclerView.setLayoutManager(layoutManager);
        recyclerView.scrollToPosition(chats.size()-1);
        ChatRecycler recycler = new ChatRecycler(chats, nameOfCourse, getContext());
        recyclerView.setAdapter(recycler);
    }

}
