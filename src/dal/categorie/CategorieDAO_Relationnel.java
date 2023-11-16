package dal.categorie;

import dal.ConnexionDB;
import metier.*;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategorieDAO_Relationnel implements I_CategorieDAO {
    private ConnexionDB connexion;
    private final PreparedStatement pst_read;
    private final PreparedStatement pst_read_all_categories;

    public CategorieDAO_Relationnel(ConnexionDB connexion) throws SQLException {
        this.connexion = connexion;
        this.pst_read = connexion.cn.prepareStatement("SELECT * FROM Categories WHERE nomCategorie = ?");
        this.pst_read_all_categories = connexion.cn.prepareStatement("SELECT * FROM Categories");
    }

    @Override
    public boolean creerCategorie(I_Categorie categorie) {
        return this.creerCategorie(categorie.getNom(), categorie.getTauxTVA());
    }

    private boolean creerCategorie(String nomCategorie, double tauxTVA) {
        try {
            CallableStatement cst = this.connexion.cn.prepareCall("{call nouvelleCategorie(?, ?)}");
            cst.setString(1, nomCategorie);
            cst.setDouble(2, tauxTVA);
            cst.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public I_Categorie lireCategorie(String nom) {
        try {
            this.pst_read.setString(1, nom);
            connexion.rs = this.pst_read.executeQuery();
            connexion.rs.next();
            return new Categorie(connexion.rs.getString(2), connexion.rs.getDouble(3));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<I_Categorie> lireTouteLesCategories() {
        try {
            connexion.rs = this.pst_read_all_categories.executeQuery();
            List<I_Categorie> listCategorie = new ArrayList<>();
            while (connexion.rs.next()) {
                listCategorie.add(new Categorie(connexion.rs.getString(2), connexion.rs.getDouble(3)));
            }
            return listCategorie;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean supprimerCategorie(I_Categorie categorie) {
        return this.supprimerCategorie(categorie.getNom());
    }

    private boolean supprimerCategorie(String nomCategorie) {
        try {
            CallableStatement cst = connexion.cn.prepareCall("{call supprimerCategorie(?)}");
            cst.setString(1, nomCategorie);
            cst.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
