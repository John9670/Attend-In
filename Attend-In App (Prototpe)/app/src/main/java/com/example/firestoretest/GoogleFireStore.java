package com.example.firestoretest;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class GoogleFireStore {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void setUserAcc(String name,String id){
        Map<String, String> note = new HashMap<>();
        note.put("Name", name);
        db.collection("Users").document(id).set(note);
    }
}
