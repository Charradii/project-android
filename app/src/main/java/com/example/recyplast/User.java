package com.example.recyplast;

public class User {

    public String name;
    public String email;
    public String password;
    public int codePostal;
    public boolean isCollecteur;
    public User(){

    }
    public User(String name,String email,String password,int codePostal){
        this.name=name;
        this.email=email;
        this.password=password;
        this.codePostal=codePostal;
        this.isCollecteur=false;
    }
}
