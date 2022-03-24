/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.projetintegre;

import java.util.*;

public class Demande {
    
    public int numDemande;
    public int idDemander;
    public String nomDemandeur;
    public String nomC;
    public String prenomC;
    public String cnie;
    public Date dateVisite;
    public Date dateFinVisite;
    public String personne;
    public String email;
    public int nbrPersonnes;
    public int statutD;
    
    public Demande(int numDemande, int idDemander, String nomDemandeur, String nomC, String prenomC, String cnie, Date dateVisite, Date dateFinVisite, String personne, String email, int nbrPersonnes, int statutD){
        this.nomDemandeur = nomDemandeur;
        this.numDemande = numDemande;
        this.idDemander = idDemander;
        this.nomC = nomC;
        this.prenomC = prenomC;
        this.cnie = cnie;
        this.dateVisite = dateVisite;
        this.dateFinVisite = dateFinVisite;
        this.personne = personne;
        this.email = email;
        this.nbrPersonnes = nbrPersonnes;
        this.statutD = statutD;
    }

    @Override
    public String toString() {
        return "Demande{" + "numDemande=" + numDemande + ", idDemander=" + idDemander + ", nomDemandeur=" + nomDemandeur + ", nomC=" + nomC + ", prenomC=" + prenomC + ", cnie=" + cnie + ", dateVisite=" + dateVisite + ", dateFinVisite=" + dateFinVisite + ", personne=" + personne + ", email=" + email + ", nbrPersonnes=" + nbrPersonnes + ", statutD=" + statutD + '}';
    }
    
    
    
}
