package dal.fabrique;

import dal.catalogue.I_CatalogueDAO;
import dal.categorie.I_CategorieDAO;
import dal.produit.I_ProduitDAO;

public interface FabriqueAbstraiteDAO {
    I_ProduitDAO creerProduitDAO();
    I_CatalogueDAO creerCatalogueDAO();
    I_CategorieDAO creerCategorieDAO();
}
