package dal.fabrique;

import dal.ConnexionDB;
import dal.catalogue.CatalogueDAO_Relationnel;
import dal.catalogue.I_CatalogueDAO;
import dal.categorie.CategorieDAO_Relationnel;
import dal.categorie.I_CategorieDAO;
import dal.produit.I_ProduitDAO;
import dal.produit.ProduitDAO_Relationnel;

import java.sql.SQLException;

public class FabriqueConcrete_Relationnel implements FabriqueAbstraiteDAO {
    private static FabriqueConcrete_Relationnel instance;
    private ConnexionDB connexionBD;

    private FabriqueConcrete_Relationnel() {
        try {
            this.connexionBD = new ConnexionDB();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static FabriqueConcrete_Relationnel getInstance() {
        if (instance == null) {
            instance = new FabriqueConcrete_Relationnel();
        }
        return instance;
    }

    @Override
    public I_ProduitDAO creerProduitDAO() {
        try {
            return new ProduitDAO_Relationnel(this.connexionBD);
        } catch (SQLException e) {
            e.getMessage();
            return null;
        }
    }

    @Override
    public I_CatalogueDAO creerCatalogueDAO() {
        try {
            return new CatalogueDAO_Relationnel(this.connexionBD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public I_CategorieDAO creerCategorieDAO() {
        try {
            return new CategorieDAO_Relationnel(this.connexionBD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
