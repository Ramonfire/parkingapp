/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.projetintegre;


public class Admin extends Compte{
    
    public int tel;
    
    public Admin(int id, String nom, String prenom, String poste, String login, String mdp, String email, boolean statutC, int tel){
        super(id, nom, prenom, poste, login, mdp, email, statutC);
        this.tel = tel;
    }
    
    public boolean changerRole(){
        //TO DO:
        
        return true;
    }
    
    public boolean creerRole(){
        //TO DO:
        
        return true;
    }
    
    public boolean modifierAffectationSite(){
        //TO DO:
        
        return true;
    }
}
