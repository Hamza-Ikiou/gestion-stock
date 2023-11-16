package dal.categorie;

import metier.I_Categorie;
import metier.I_Produit;

import java.util.List;

public interface I_CategorieDAO {
    boolean creerCategorie(I_Categorie categorie);
    I_Categorie lireCategorie(String nom);
    List<I_Categorie> lireTouteLesCategories();
    boolean supprimerCategorie(I_Categorie categorie);
}