package controller;

import dal.produit.I_ProduitDAO;
import dal.fabrique.FabriqueConcrete_Relationnel;
import metier.*;
import presentation.FenetrePrincipale;

import java.util.List;

import static presentation.FenetrePrincipale.cat;

public class ControllerNouveauSuppressionProduit {
    public static I_ProduitDAO produitDAO;

    static {
        try {
            produitDAO = FabriqueConcrete_Relationnel.getInstance().creerProduitDAO();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String[] demandeSuppression() {
        return getNomProduits();
    }

    private String[] getNomProduits() {
        List<I_Produit> listeProduits = produitDAO.lireTousLesProduits(FenetrePrincipale.cat);
        String[] tabNoms = new String[listeProduits.size()];
        for (int i = 0; i < listeProduits.size(); i++) {
            tabNoms[i] = listeProduits.get(i).getNom();
        }
        return tabNoms;
    }

    public boolean supprimerProduit(String nomProduit) {
        return produitDAO.supprimerProduit(produitDAO.lireProduit(nomProduit, FenetrePrincipale.cat), FenetrePrincipale.cat);
    }

    public boolean creerProduit(String nomProduit, String prix, String qte, String nomCategorie) {
        if (nomProduit != null && prix != null && qte != null && nomCategorie != null) {
            try {
                I_Produit p = new Produit(nomProduit, Double.parseDouble(prix), Integer.parseInt(qte), cat, ControllerNouvelleSuppressionCategorie.categorieDAO.lireCategorie(nomCategorie));
                produitDAO.creerProduit(p, FenetrePrincipale.cat);
                return cat.addProduit(p);
            } catch (NumberFormatException e) {
                return false;
            }
        } else {
            return false;
        }
    }
}
