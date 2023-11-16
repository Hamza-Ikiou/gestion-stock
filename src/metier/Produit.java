package metier;

import java.text.DecimalFormat;

public class Produit implements I_Produit {
    private String nom;
    private double prixUnitaireHT;
    private int quantiteStock;
    private I_Catalogue catalogue;
    private I_Categorie categorie;

    public Produit(String nom, double prixUnitaireHT, int quantiteStock) {
        this.nom = nom;
        this.prixUnitaireHT = prixUnitaireHT;
        this.quantiteStock = quantiteStock;
    }

    public Produit(String nom, double prixUnitaireHT, int quantiteStock, I_Catalogue catalogue) {
        this(nom, prixUnitaireHT, quantiteStock);
        this.catalogue = catalogue;
    }

    public Produit(String nom, double prixUnitaireHT, int quantiteStock, I_Categorie categorie) {
        this(nom, prixUnitaireHT, quantiteStock);
        this.categorie = categorie;
    }

    public Produit(String nom, double prixUnitaireHT, int quantiteStock, I_Catalogue catalogue, I_Categorie categorie) {
        this(nom, prixUnitaireHT, quantiteStock, catalogue);
        this.categorie = categorie;
    }

    @Override
    public boolean ajouter(int qteAchetee) {
        this.quantiteStock += qteAchetee;
        return true;
    }

    @Override
    public boolean enlever(int qteVendue) {
        if (this.getQuantite() <= 0 || this.getQuantite() - qteVendue < 0 ) {
            return false;
        } else {
            this.quantiteStock -= qteVendue;
            return true;
        }
    }

    @Override
    public String getNom() {
        return this.nom;
    }

    @Override
    public int getQuantite() {
        return this.quantiteStock;
    }

    @Override
    public double getPrixUnitaireHT() {
        return this.prixUnitaireHT;
    }

    @Override
    public double getPrixUnitaireTTC() {
        return this.getPrixUnitaireHT() * (1 + this.getTauxTva());
    }

    @Override
    public double getPrixStockTTC() {
        return this.getPrixUnitaireTTC() * this.getQuantite();
    }

    @Override
    public I_Catalogue getCatalogue() {
        return this.catalogue;
    }

    @Override
    public void setCatalogue(I_Catalogue catalogue) {
        this.catalogue = catalogue;
    }

    @Override
    public I_Categorie getCategorie() {
        return categorie;
    }

    @Override
    public void setCategorie(I_Categorie categorie) {
        this.categorie = categorie;
    }

    public double getTauxTva() {
        if (this.categorie == null) {
            return 0.2;
        }
        return this.categorie.getTauxTVA();
    }

    @Override
    public String toString() {
        String prixHT = ("" + (new DecimalFormat("0.00").format(this.getPrixUnitaireHT()))).replace(".", ",");
        String prixTTC = ("" + (new DecimalFormat("0.00").format(this.getPrixUnitaireTTC()))).replace(".", ",");
        return nom + " - prix HT : " + prixHT + " € - prix TTC : " + prixTTC + " € - quantité en stock : " + this.getQuantite();
    }

    public static void main(String[] args) {
        Produit p = new Produit("RTX 3090", 1000, 5);
        System.out.println(p);
        System.out.println(p.enlever(6));
        System.out.println(p.ajouter(1));
        System.out.println(p.enlever(6));
        System.out.println(p);
    }
}
