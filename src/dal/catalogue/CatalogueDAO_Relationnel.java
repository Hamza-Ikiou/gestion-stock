package dal.catalogue;

import dal.ConnexionDB;
import metier.*;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CatalogueDAO_Relationnel implements I_CatalogueDAO {
    private ConnexionDB connexion;

    private final PreparedStatement pst_read;
    private final PreparedStatement pst_read_all_catalogues;
    private final PreparedStatement pst_read_all_nom_catalogues;
    private final PreparedStatement pst_nb_catalogues;
    private final PreparedStatement pst_read_nb_produits;
    private final PreparedStatement pst_read_all_produits;
    private final PreparedStatement pst_idCatalogue;
    private final PreparedStatement pst_read_categorie_produit;

    public CatalogueDAO_Relationnel(ConnexionDB connexion) throws SQLException {
        this.connexion = connexion;
        this.pst_read = connexion.cn.prepareStatement("SELECT * FROM Catalogues WHERE nomCatalogue = ?");
        this.pst_read_nb_produits = connexion.cn.prepareStatement("SELECT COUNT(*) FROM Produits WHERE idCatalogue = ?");
        this.pst_read_all_produits = connexion.cn.prepareStatement("SELECT p.nomProduit, p.prixProduitHt, p.quantiteStockProduits, c.nomCategorie, c.tauxTva FROM Produits p JOIN Categories c ON c.idCategorie = p.idCategorie WHERE idCatalogue = ?");
        this.pst_read_all_catalogues = connexion.cn.prepareStatement("SELECT * FROM Catalogues");
        this.pst_read_all_nom_catalogues = connexion.cn.prepareStatement("SELECT nomCatalogue FROM Catalogues");
        this.pst_nb_catalogues = connexion.cn.prepareStatement("SELECT COUNT(*) FROM Catalogues");
        this.pst_idCatalogue = connexion.cn.prepareStatement("SELECT idCatalogue FROM Catalogues WHERE nomCatalogue = ?");
        this.pst_read_categorie_produit = connexion.cn.prepareStatement("SELECT * FROM Categories WHERE nomCategorie = ?");
    }

    @Override
    public boolean creerCatalogue(I_Catalogue c) {
        return this.creerCatalogue(c.getNom());
    }

    public boolean creerCatalogue(String nomCatalogue) {
        try {
            CallableStatement cst = connexion.cn.prepareCall("{call nouveauCatalogue(?)}");
            cst.setString(1, nomCatalogue);
            cst.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public I_Catalogue lireCatalogue(String nom) {
        try {
            this.pst_read.setString(1, nom);
            connexion.rs = this.pst_read.executeQuery();
            connexion.rs.next();
            return new Catalogue(connexion.rs.getString(2));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int lireNbProduitsCatalogue(String nom) {
        try {
            this.pst_idCatalogue.setString(1, nom);
            this.connexion.rs = this.pst_idCatalogue.executeQuery();
            this.connexion.rs.next();
            this.pst_read_nb_produits.setInt(1, this.connexion.rs.getInt(1));
            connexion.rs = this.pst_read_nb_produits.executeQuery();
            connexion.rs.next();
            return connexion.rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<I_Produit> lireProduits(String nom) {
        try {
            this.pst_idCatalogue.setString(1, nom);
            this.connexion.rs = this.pst_idCatalogue.executeQuery();
            this.connexion.rs.next();
            this.pst_read_all_produits.setInt(1, this.connexion.rs.getInt(1));
            connexion.rs = this.pst_read_all_produits.executeQuery();
            List<I_Produit> listProduits = new ArrayList<>();
            while (connexion.rs.next()) {
                String nomProduit = connexion.rs.getString(1);
                double prixProduit = connexion.rs.getDouble(2);
                int quantiteStockProduit = connexion.rs.getInt(3);
                String nomCategorie = connexion.rs.getString(4);
                double tauxTvaCategorie = connexion.rs.getDouble(5);
                listProduits.add(new Produit(nomProduit, prixProduit, quantiteStockProduit, new Categorie(nomCategorie, tauxTvaCategorie)));
            }
            return listProduits;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<I_Catalogue> lireTousLesCatalogues() {
        try {
            connexion.rs = this.pst_read_all_catalogues.executeQuery();
            List<I_Catalogue> listCatalogues = new ArrayList<>();
            while (connexion.rs.next()) {
                listCatalogues.add(new Catalogue(connexion.rs.getString(2)));
            }
            return listCatalogues;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean supprimerCatalogue(I_Catalogue c) {
        return this.supprimerCatalogue(c.getNom());
    }

    public boolean supprimerCatalogue(String nomCatalogue) {
        try {
            CallableStatement cst = connexion.cn.prepareCall("{call supprimerCatalogue(?)}");
            cst.setString(1, nomCatalogue);
            cst.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
