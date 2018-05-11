package com.example.laure.applijson;

/**
 * Created by JCC on 06/12/2015.
 */
public class Personnage {
    String nom;
    String topo;

    public Personnage(String nom, String topo) {
        this.nom = nom;
        this.topo = topo;
    }

    public Personnage() {
        super();
    }

    public String getNom() {
        return nom;
    }

    public String getTopo() {
        return topo;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setTopo(String topo) {
        this.topo = topo;
    }
}
