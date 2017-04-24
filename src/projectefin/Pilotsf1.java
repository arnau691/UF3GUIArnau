/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectefin;

import java.io.Serializable;

/**
 *
 * @author arnau
 */
public class Pilotsf1 implements Serializable{
    
    // Les meues propietats
    private String nom="";
    private int pes;
    private boolean acciden_pilot;
    private double minut_per_volta;
    private boolean omplit;

    public Pilotsf1(String nom, int pes, boolean acciden_pilot, double minut_per_volta, boolean omplit) {
        this.nom = nom;
        this.pes = pes;
        this.acciden_pilot = acciden_pilot;
        this.minut_per_volta = minut_per_volta;
        this.omplit = omplit;
    }

    public Pilotsf1() {
    }
    
    public Pilotsf1(String nom) {
        this.nom=nom;
    }
    
    public Pilotsf1(String nom, int pes) {
        this.nom=nom;
        this.pes=pes;
    }

    @Override
    public String toString() {
        
        return "\nNom: "+nom+
               "\npes: "+pes+
               "\nminut_per_volta: "+minut_per_volta+
               (acciden_pilot?"\nAcciden: Si":"\nAcciden: No");
    }
    
    
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getpes() {
        return pes;
    }

    public void setpes(int pes) {
        if(pes<0) System.out.println("El pes ha de ser positiu o zero!!");
        else this.pes = pes;
    }

    public boolean isacciden_pilot() {
        return acciden_pilot;
    }

    public void setSi(boolean acciden_pilot) {
        this.acciden_pilot = acciden_pilot;
    }

    public double getminut_per_volta() {
        return minut_per_volta;
    }

    public void setminut_per_volta(double minut_per_volta) {
        if(minut_per_volta<0) System.out.println("Els minut per volta no poden ser negatius!!");
        else this.minut_per_volta = minut_per_volta;
    }

    public boolean isOmplit() {
        return omplit;
    }

    public void setOmplit(boolean omplit) {
        this.omplit = omplit;
    }

}
