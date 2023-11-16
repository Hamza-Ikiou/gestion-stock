package metier;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Catalogue implements I_Catalogue {
    private String nom;
    private List<I_Produit> lesProduits;

    public Catalogue() {
        this.lesProduits = new ArrayList<>();
    }

    public Catalogue(String nom) {
        this();
        this.nom = nom;
    }

    @Override
    public boolean addProduit(I_Produit produit) {
        if (produit != null
                && this.lesProduits.stream().noneMatch(p -> p.getNom().equals(produit.getNom().trim()))
                && produit.getQuantite() >= 0
                && produit.getPrixUnitaireHT() > 0) {
            this.lesProduits.add(produit);
            produit.setCatalogue(this);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean addProduit(String nom, double prix, int qte) {
        if (!Objects.equals(nom, "null")
                && this.lesProduits.stream().noneMatch(p -> p.getNom().equals(nom.trim()))
                && qte >= 0
                && prix > 0) {
            Produit p = new Produit(nom.replace("\t", " ").trim(), prix, qte);
            return this.addProduit(p);
        } else {
            return false;
        }
    }

    @Override
    public int addProduits(List<I_Produit> l) {
        int compteur = 0;
        if (l != null) {
            for (I_Produit produit : l) {
                if (this.addProduit(produit)){
                    compteur++;
                }
            }
        }
        return compteur;
    }

    private I_Produit getProduitByName(String nom) {
        I_Produit p = null;
        boolean verif = false;
        int i = 0;
        if (!this.lesProduits.isEmpty() && nom != null) {
            while(!verif && i < this.lesProduits.size()) {
                if (Objects.equals(this.lesProduits.get(i).getNom(), nom)){
                    verif = true;
                    p = this.lesProduits.get(i);
                } else {
                    i++;
                }
            }
        }
        return p;
    }

    @Override
    public boolean removeProduit(String nom) {
        I_Produit p = this.getProduitByName(nom);
        if (p != null) {
            this.lesProduits.remove(p);
            p.setCatalogue(null);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean acheterStock(String nomProduit, int qteAchetee) {
        I_Produit p = this.getProduitByName(nomProduit);
        if (p != null && qteAchetee > 0) {
            return p.ajouter(qteAchetee);
        } else {
            return false;
        }
    }

    @Override
    public boolean vendreStock(String nomProduit, int qteVendue) {
        I_Produit p = this.getProduitByName(nomProduit);
        if (p != null && qteVendue > 0) {
            return p.enlever(qteVendue);
        } else {
            return false;
        }
    }

    @Override
    public String[] getNomProduits() {
        int listeProduitSize = this.lesProduits.size();
        String[] tabNoms = new String[listeProduitSize];
        for (int i = 0; i < listeProduitSize; i++) {
            tabNoms[i] = this.lesProduits.get(i).getNom();
        }
        Arrays.sort(tabNoms);
        return tabNoms;
    }

    @Override
    public List<I_Produit> getProduits() {
        return this.lesProduits;
    }

    @Override
    public double getMontantTotalTTC() {
        double res = 0;
        for (I_Produit produit : this.lesProduits) {
            res += produit.getPrixStockTTC();
        }
        return Math.round(res * 100.0) / 100.0;
    }

    @Override
    public String getNom() { return this.nom; }

    @Override
    public String toString() {
        double total = this.getMontantTotalTTC();
        StringBuilder str = new StringBuilder();
        if (total > 0) {
            for (I_Produit p : this.lesProduits) {
                str.append(p);
                str.append("\n");
            }
            String strTotal = "\nMontant total TTC du stock : " + (new DecimalFormat("0.00").format(total)) + " €";
            str.append(strTotal.replace(".", ","));
            return str.toString();
        } else {
            return "\nMontant total TTC du stock : 0,00 €";
        }
    }

    @Override
    public void clear() {
        this.lesProduits.clear();
    }
}
