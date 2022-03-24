/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.projetintegre;


public class ResponsableSite extends Compte{
    
    public ResponsableSite(int id, String nom, String prenom, String poste, String login, String mdp, String email, boolean statutC){
        super(id, nom, prenom, poste, login, mdp, email, statutC);
    }
    
    public boolean creerRoleGuerite(){
        //TO DO:
        
        return true;
    }
    
    public boolean creerRoleUser(){
        //TO DO:
        
        return true;
    }
    
    public void visualiserDemande(){
        //TO DO:
        
    }
    
}
