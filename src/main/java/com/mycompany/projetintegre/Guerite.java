/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.projetintegre;
import java.util.*;


public class Guerite extends Compte{
    
    public Date dateCreation;
    public String cin;
    
    public Guerite(int id, String nom, String prenom, String poste, String login, String mdp, String email, boolean statutC, Date dateCreation, String cin){
        super(id, nom, prenom, poste, login, mdp, email, statutC);
        this.dateCreation = dateCreation;
        this.cin = cin;
    }
    
    public boolean visualiserDemande(){
        //TO DO:
        
        return true;
    }
    
    public boolean chercherDemande(){
        //TO DO:
        
        return true;
    }
    
    public boolean autoriserDemande(){
        //TO DO:
        
        return true;
    }
    
    public boolean changerEtat(){
        //TO DO:
        
        return true;
    }
    
    public String notifier(){
        //TO DO:
        
        return "";
    }
    
    public boolean extraireDonnee(){
        //TO DO:
        
        return true;
    }
}
