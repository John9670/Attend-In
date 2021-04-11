package com.example.firestoretest;

public class Maps {

    public boolean validLocation(double x1, double y1, double x2, double y2){
        double dis = Math.sqrt(Math.pow((y2-y1),2)+Math.pow((x2-x1),2));
        if(dis <= .000540830228){
            return true ;
        }
        else{
            return false;
        }
        
    }
}
