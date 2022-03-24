/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.projetintegre;


public class Compte {
    
    public int id;
    public String nom;
    public String prenom;
    public String poste;
    public String login;
    public String mdp;
    public String email;
    public boolean statutC;
    
    public Compte(int id, String nom, String prenom, String poste, String login, String mdp, String email, boolean statutC){
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.poste = poste;
        this.login = login;
        this.mdp = mdp;
        this.email = email;
        this.statutC = statutC;
    }
    
    public Compte(int id, String nom, String prenom, String poste, boolean statutC){
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.poste = poste;
        this.statutC = statutC;
    }
    
    public int seConnecter(){
        //TO DO:
        
        return 0;
    };
    
}
