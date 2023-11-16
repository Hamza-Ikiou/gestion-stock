package controller;

import dal.catalogue.I_CatalogueDAO;
import dal.fabrique.FabriqueConcrete_Relationnel;
import metier.Catalogue;
import metier.I_Catalogue;
import presentation.FenetrePrincipale;

import java.util.List;

public class ControllerCatalogue {
    public static I_CatalogueDAO dao;

    static {
        try {
            dao = FabriqueConcrete_Relationnel.getInstance().creerCatalogueDAO();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void selectionnerCatalogue(String nomCatalogue) {
        FenetrePrincipale.cat = dao.lireCatalogue(nomCatalogue);
    }

    public String[] lireNomCatalogues() {
        List<I_Catalogue> listeCatalogues = dao.lireTousLesCatalogues();
        String[] tabNoms = new String[listeCatalogues.size()];
        for (int i = 0; i < listeCatalogues.size(); i++) {
            tabNoms[i] = listeCatalogues.get(i).getNom();
        }
        return tabNoms;
    }

    public int lireNbProduitCatalogue(String nomCatalogue) {
        return dao.lireNbProduitsCatalogue(nomCatalogue);
    }

    public boolean supprimerCatalogue(String nomCatalogue) {
        return dao.supprimerCatalogue(dao.lireCatalogue(nomCatalogue));
    }

    public boolean creerCatalogue(String nomCatalogue) {
        if (nomCatalogue != null) {
            try {
                return dao.creerCatalogue(new Catalogue(nomCatalogue));
            } catch (NumberFormatException e) {
                return false;
            }
        } else {
            return false;
        }
    }
}
