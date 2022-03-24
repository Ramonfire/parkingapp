/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.projetintegre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;


public class UtilisateurEntreprise extends Compte{
    
    

    
    public String societe;
    public String fonction;
    public int tel;
    public boolean active;
    
    public UtilisateurEntreprise(int id, String nom, String prenom, String poste, String login, String mdp, String email, boolean statutC, String societe, String fonction, int tel, boolean active){
        super(id, nom, prenom, poste, login, mdp, email, statutC);
        this.societe = societe;
        this.fonction = fonction;
        this.tel = tel;
        this.active = active;
    }
    
    public boolean creerDemande(){
        return true;
    }
    
}
