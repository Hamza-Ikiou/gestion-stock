package controller;

import dal.categorie.I_CategorieDAO;
import dal.fabrique.FabriqueConcrete_Relationnel;
import metier.Categorie;
import metier.I_Categorie;

import java.util.List;

public class ControllerNouvelleSuppressionCategorie {
    public static I_CategorieDAO categorieDAO;

    static {
        try {
            categorieDAO = FabriqueConcrete_Relationnel.getInstance().creerCategorieDAO();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String[] demandeListe() {
        return getNomCategorie();
    }

    private String[] getNomCategorie() {
        List<I_Categorie> listeCategorie = categorieDAO.lireTouteLesCategories();
        String[] tabNoms = new String[listeCategorie.size()];
        for (int i = 0; i < listeCategorie.size(); i++) {
            tabNoms[i] = listeCategorie.get(i).getNom();
        }
        return tabNoms;
    }

    public boolean supprimerCategorie(String nomCategorie) {
        return categorieDAO.supprimerCategorie(categorieDAO.lireCategorie(nomCategorie));
    }

    public boolean creerCategorie(String nomCategorie, String tauxTVA) {
        if (nomCategorie != null && tauxTVA != null) {
            try {
                I_Categorie c = new Categorie(nomCategorie, Double.parseDouble(tauxTVA));
                categorieDAO.creerCategorie(c);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        } else {
            return false;
        }
    }
}
