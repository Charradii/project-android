package com.example.recyplast;

public class Demande {
    public String userEmail;
    public int codePostal;
    public double lat;
    public double alt;
    public Boolean isCollected;
    Demande(){

    }
    Demande(String userEmail,int codePostal, double lat,double alt){
        this.userEmail=userEmail;
        this.codePostal=codePostal;
        this.lat=lat;
        this.alt=alt;
        this.isCollected=false;
    }
}
