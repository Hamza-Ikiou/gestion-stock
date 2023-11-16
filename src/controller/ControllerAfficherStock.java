package controller;

import presentation.FenetrePrincipale;

public class ControllerAfficherStock {
    public String afficherStock() {
        FenetrePrincipale.cat.clear();
        FenetrePrincipale.cat.addProduits(ControllerCatalogue.dao.lireProduits(FenetrePrincipale.cat.getNom()));
        return FenetrePrincipale.cat.toString();
    }
}
