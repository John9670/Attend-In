package com.example.firestoretest;
import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class GoogleFireStore {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public interface OnGetDataListener {
        void onComplete(boolean success);
    }

    public void isValid(final String name, String id, final OnGetDataListener callback) {
        DocumentReference user = db.collection("Users").document(id);
        user.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult().getString("Name") == null){
                        callback.onComplete(false);
                    }
                    else if(task.getResult().getString("Name").equals(name)){
                        callback.onComplete(true);
                    }
                    else{
                        callback.onComplete(false);
                    }
                }
            }
        });
    }

    public void doesExist(String id, final OnGetDataListener callback){
        DocumentReference user = db.collection("Users").document(id);
        user.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult().getString("Name") == null){
                        callback.onComplete(false);
                    }
                    else{
                        callback.onComplete(true);
                    }
                }
            }
        });

    }

    public void setUserAcc(String name, String id){
        Map<String, String> note = new HashMap<>();
        note.put("Name", name);
        db.collection("Users").document(id).set(note);
    }
}
