package dal.produit;

import dal.ConnexionDB;
import metier.I_Catalogue;
import metier.I_Produit;
import metier.Produit;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProduitDAO_Relationnel implements I_ProduitDAO {
    private ConnexionDB connexion;
    private final PreparedStatement pst_read;
    private final PreparedStatement pst_read_all_produits;
    private final PreparedStatement pst_read_all_nom_produits;
    private final PreparedStatement pst_nb_produits;
    private final PreparedStatement pst_idCatalogue;
    private final PreparedStatement pst_idCategorie;


    public ProduitDAO_Relationnel(ConnexionDB connexion) throws SQLException {
        this.connexion = connexion;
        this.pst_read = connexion.cn.prepareStatement("SELECT * FROM Produits WHERE nomProduit = ? AND idCatalogue = ?");
        this.pst_read_all_produits = connexion.cn.prepareStatement("SELECT * FROM Produits WHERE idCatalogue = ?");
        this.pst_read_all_nom_produits = connexion.cn.prepareStatement("SELECT nomProduit FROM Produits");
        this.pst_nb_produits = connexion.cn.prepareStatement("SELECT COUNT(*) FROM Produits");
        this.pst_idCatalogue = connexion.cn.prepareStatement("SELECT idCatalogue FROM Catalogues WHERE nomCatalogue = ?");
        this.pst_idCategorie = connexion.cn.prepareStatement("SELECT idCategorie FROM Categories WHERE nomCategorie = ?");
    }

    @Override
    public boolean creerProduit(I_Produit p, I_Catalogue catalogue) {
        return this.creerProduit(p.getNom(), p.getPrixUnitaireHT(), p.getQuantite(), catalogue, p.getCategorie().getNom());
    }

    private boolean creerProduit(String nomProduit, double prix, int quantite, I_Catalogue catalogue, String nomCategorie) {
        try {
            CallableStatement cst = this.connexion.cn.prepareCall("{call nouveauProduit(?, ?, ?, ?, ?)}");
            cst.setString(1, nomProduit);
            cst.setLong(2, (long) prix);
            cst.setInt(3, quantite);
            this.pst_idCatalogue.setString(1, catalogue.getNom());
            this.connexion.rs = this.pst_idCatalogue.executeQuery();
            this.connexion.rs.next();
            cst.setInt(4, this.connexion.rs.getInt(1));
            this.pst_idCategorie.setString(1, nomCategorie);
            this.connexion.rs = this.pst_idCategorie.executeQuery();
            this.connexion.rs.next();
            cst.setInt(5, this.connexion.rs.getInt(1));
            cst.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public I_Produit lireProduit(String nomProduit, I_Catalogue c) {
        try {
            this.pst_idCatalogue.setString(1, c.getNom());
            this.connexion.rs = this.pst_idCatalogue.executeQuery();
            this.connexion.rs.next();
            this.pst_read.setString(1, nomProduit);
            this.pst_read.setInt(2, this.connexion.rs.getInt(1));
            connexion.rs = this.pst_read.executeQuery();
            connexion.rs.next();
            return new Produit(connexion.rs.getString(2), connexion.rs.getDouble(3), connexion.rs.getInt(4));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<I_Produit> lireTousLesProduits(I_Catalogue c) {
        try {
            this.pst_idCatalogue.setString(1, c.getNom());
            this.connexion.rs = this.pst_idCatalogue.executeQuery();
            this.connexion.rs.next();
            this.pst_read_all_produits.setInt(1, this.connexion.rs.getInt(1));
            connexion.rs = this.pst_read_all_produits.executeQuery();
            List<I_Produit> listProduits = new ArrayList<>();
            while (connexion.rs.next()) {
                listProduits.add(new Produit(
                        connexion.rs.getString(2),
                        connexion.rs.getDouble(3),
                        connexion.rs.getInt(4)));
            }
            return listProduits;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean majProduit(I_Produit p, I_Catalogue c) {
        return this.majProduit(p.getNom(), p.getPrixUnitaireHT(), p.getQuantite(), c);
    }

    private boolean majProduit(String nomProduit, double prix, int quantite, I_Catalogue c) {
        try {
            this.pst_idCatalogue.setString(1, c.getNom());
            this.connexion.rs = this.pst_idCatalogue.executeQuery();
            this.connexion.rs.next();
            CallableStatement cst = connexion.cn.prepareCall("{call modifierProduit(?, ?, ?, ?)}");
            cst.setString(1, nomProduit);
            cst.setLong(2, (long) prix);
            cst.setInt(3, quantite);
            cst.setInt(4, this.connexion.rs.getInt(1));
            cst.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean supprimerProduit(I_Produit p, I_Catalogue c) {
        return this.supprimerProduit(p.getNom(), c);
    }

    private boolean supprimerProduit(String nomProduit, I_Catalogue c) {
        try {
            this.pst_idCatalogue.setString(1, c.getNom());
            this.connexion.rs = this.pst_idCatalogue.executeQuery();
            this.connexion.rs.next();
            CallableStatement cst = connexion.cn.prepareCall("{call supprimerProduit(?, ?)}");
            cst.setString(1, nomProduit);
            cst.setInt(2, this.connexion.rs.getInt(1));
            cst.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
