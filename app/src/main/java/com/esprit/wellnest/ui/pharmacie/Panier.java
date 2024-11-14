package com.esprit.wellnest.ui.pharmacie;

import java.util.ArrayList;
import java.util.List;

public class Panier {
    private static Panier instance;
    private List<String> panierProduits;

    private Panier() {
        panierProduits = new ArrayList<>();
    }

    public static synchronized Panier getInstance() {
        if (instance == null) {
            instance = new Panier();
        }
        return instance;
    }

    public List<String> getPanierProduits() {
        return panierProduits;
    }

    public void ajouterProduitAuPanier(String nomProduit, String marqueProduit, double prixProduit, int quantiteProduit) {
        String produitInfo = String.format("%s|%s|%s|%s", nomProduit, marqueProduit, prixProduit, quantiteProduit);
        panierProduits.add(produitInfo);
    }

}


