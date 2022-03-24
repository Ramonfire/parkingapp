/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.projetintegre;


public class SuperAdmin extends Admin{
    
    public SuperAdmin(int id, String nom, String prenom, String poste, String login, String mdp, String email, boolean statutC, int tel){
        super(id, nom, prenom, poste, login, mdp, email, statutC, tel);
    }
    
    public boolean creerCompte(){
        //TO DO:
        
        return true;
    }
    
    public boolean activerCompte(){
        //TO DO:
        
        return true;
    }
    
    public boolean desactiverCompte(){
        //TO DO:
        
        return true;
    }
    
    public boolean reinitialiserMdp(){
        //TO DO:
        
        return true;
    }
    
    public boolean creerSite(){
        //TO DO:
        
        return true;
    }
    
    public boolean modifierSite(){
        //TO DO:
        
        return true;
    }
    
    public boolean supprimerSite(){
        //TO DO:
        
        return true;
    }
    
    public boolean consulterSite(){
        //TO DO:
        
        return true;
    }
    
}
