package controller;

import metier.I_Produit;
import presentation.FenetrePrincipale;

public class ControllerAchatVente {
    public String[] demandeUpdate() {
        FenetrePrincipale.cat.clear();
        FenetrePrincipale.cat.addProduits(ControllerNouveauSuppressionProduit.produitDAO.lireTousLesProduits(FenetrePrincipale.cat));
        return FenetrePrincipale.cat.getNomProduits();
    }

    public boolean vendreProduit(String nomProduit, String qte) {
        if (nomProduit != null && qte != null) {
            try {
                int qteParsed = Integer.parseInt(qte);
                I_Produit p = ControllerNouveauSuppressionProduit.produitDAO.lireProduit(nomProduit, FenetrePrincipale.cat);
                p.enlever(qteParsed);
                return ControllerNouveauSuppressionProduit.produitDAO.majProduit(p, FenetrePrincipale.cat);
            } catch (NumberFormatException e) {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean acheterProduit(String nomProduit, String qte) {
        if (nomProduit != null && qte != null) {
            try {
                int qteParsed = Integer.parseInt(qte);
                I_Produit p = ControllerNouveauSuppressionProduit.produitDAO.lireProduit(nomProduit, FenetrePrincipale.cat);
                p.ajouter(qteParsed);
                return ControllerNouveauSuppressionProduit.produitDAO.majProduit(p, FenetrePrincipale.cat);
            } catch (NumberFormatException e) {
                return false;
            }
        } else {
            return false;
        }
    }
}
