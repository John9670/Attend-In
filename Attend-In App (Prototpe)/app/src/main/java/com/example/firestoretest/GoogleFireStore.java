package com.example.firestoretest;
import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GoogleFireStore {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public interface OnGetDataListener {
        void onComplete(boolean success);
    }

    public interface OnGetClassListener {
        void onComplete(ArrayList<String> success);
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

    public void getClassList(String id,final OnGetClassListener callback){
        DocumentReference classes = db.collection("Users").document(id);
        classes.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                ArrayList<String> result = (ArrayList<String>) (task.getResult().get("ClassList"));
                callback.onComplete(result);
            }
        });
    }

    public void getOwnedClassList(String id,final OnGetClassListener callback){
        DocumentReference classes = db.collection("Users").document(id);
        classes.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                ArrayList<String> result = (ArrayList<String>) (task.getResult().get("OwnedClassList"));
                callback.onComplete(result);
            }
        });
    }

    public void addOwnerClass(final String id, final String crn, final  OnGetClassListener callback){
        DocumentReference classes = db.collection("Users").document(id);
        classes.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                ArrayList<String> result = (ArrayList<String>) (task.getResult().get("OwnedClassList"));
                result.add(crn);
                db.collection("Users").document(id).update("OwnedClassList",result);
                callback.onComplete(result);
            }
        });

    }

    //TODO Check if it already exist
    public void mkClass(SimpleDateFormat tm1, SimpleDateFormat tm2, String lon, String lat, final String crn){
        Map<String, String> note = new HashMap<>();
        note.put("Start Time", tm1.format(new Date()));
        note.put("End Time", tm2.format(new Date()));
        note.put("Longitude", lon);
        note.put("Latitude", lat);
        db.collection("Classes").document(crn).set(note);
    }

    //TODO Check if the class is valid (Add isValidClass) (For valid CRN Number and if it already exist inside your own list)
    public  void addClass(final String id, final String crn, final OnGetClassListener callback){
        DocumentReference classes = db.collection("Users").document(id);
        classes.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                ArrayList<String> result = (ArrayList<String>) (task.getResult().get("ClassList"));
                result.add(crn);
                db.collection("Users").document(id).update("ClassList",result);
                callback.onComplete(result);
            }
        });
    }

    //TODO ADD VALIDATOR FOR ADDCLASS
    public void isValidClass(final String name, String id, final OnGetDataListener callback) {
        DocumentReference user = db.collection("Classes").document(id);
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

    public void getClassLocation(String id,final OnGetClassListener callback){
        DocumentReference classes = db.collection("Classes").document(id);
        classes.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                ArrayList<String> result =new ArrayList<>();
                result.add(task.getResult().getString("Latitude"));
                result.add(task.getResult().getString("Longitude"));
                callback.onComplete(result);
            }
        });
    }

    public void setUserAcc(String name, String id, final OnGetDataListener callback){
        Map<String, String> note = new HashMap<>();
        note.put("Name", name);
        db.collection("Users").document(id).set(note);
        Map<String, Object> docData = new HashMap<>();
        docData.put("OwnedClassList", Arrays.asList());
        docData.put("ClassList", Arrays.asList());
        db.collection("Users").document(id).set(docData, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                callback.onComplete(true);
            }
        });
    }

    public void mkAttendanceList(final String crn, final OnGetDataListener callback){
        String date = new SimpleDateFormat("MM-dd-yyyy").format(new Date());
        Map<String, Object> docData = new HashMap<>();
        docData.put("Attendance", Arrays.asList());
        db.collection("Classes").document(crn).collection("Attendance").document(date).set(docData, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                callback.onComplete(true);
            }
        });
    }

    public void addAttendanceList(final String id, final String crn, final OnGetClassListener callback) {
        DocumentReference classes = db.collection("Classes").document(crn).collection("Attendance").document(new SimpleDateFormat("MM-dd-yyyy").format(new Date()));
        classes.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                ArrayList<String> result = (ArrayList<String>) (task.getResult().get("Attendance"));
                result.add(id);
                db.collection("Classes").document(crn).collection("Attendance").document(new SimpleDateFormat("MM-dd-yyyy").format(new Date())).update("Attendance",result);
                callback.onComplete(result);
            }
        });
    }

    public void isValidAttendanceList(final String crn,  final OnGetDataListener callback) {
        DocumentReference user = db.collection("Classes").document(crn).collection("Attendance").document(new SimpleDateFormat("MM-dd-yyyy").format(new Date()));
        user.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult().get("Attendance") == null){
                        callback.onComplete(false);
                    }
                    else{
                        callback.onComplete(true);
                    }
                }
            }
        });
    }

    public void getAttendanceList(final String crn,  final OnGetClassListener callback) {
        CollectionReference user = db.collection("Classes").document(crn).collection("Attendance");
        user.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                ArrayList<String> result = new ArrayList<>();
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        result.add(document.getId());
                    }
                    callback.onComplete(result);
                }
            }
        });
    }
    public void getStudentAttendance(final String crn, final String date ,final OnGetClassListener callback){
        DocumentReference user = db.collection("Classes").document(crn).collection("Attendance").document(date);
        user.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                ArrayList<String> result = (ArrayList<String>) (task.getResult().get("Attendance"));
                callback.onComplete(result);
            }
        });
    }

    //TODO Add If already checkedin validator

}