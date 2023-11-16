package dal.produit;

import metier.I_Catalogue;
import metier.I_Produit;

import java.util.List;

public interface I_ProduitDAO {
    boolean creerProduit(I_Produit p, I_Catalogue catalogue);
    I_Produit lireProduit(String nom, I_Catalogue c);
    List<I_Produit> lireTousLesProduits(I_Catalogue c);
    boolean majProduit(I_Produit p, I_Catalogue c);
    boolean supprimerProduit(I_Produit p, I_Catalogue c);
}
