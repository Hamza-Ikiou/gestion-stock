package dal.catalogue;

import metier.I_Catalogue;
import metier.I_Produit;

import java.util.List;

public interface I_CatalogueDAO {
    boolean creerCatalogue(I_Catalogue c);
    I_Catalogue lireCatalogue(String nom);
    int lireNbProduitsCatalogue(String nom);
    List<I_Catalogue> lireTousLesCatalogues();
    boolean supprimerCatalogue(I_Catalogue c);
    List<I_Produit> lireProduits(String nom);
}
