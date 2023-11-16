package metier;

public class Categorie implements I_Categorie {
    private String nom;
    private double tauxTVA;

    public Categorie(String nom, double tauxTVA) {
        this.nom = nom;
        this.tauxTVA = tauxTVA;
    }

    @Override
    public String getNom() {
        return this.nom;
    }

    @Override
    public double getTauxTVA() {
        return this.tauxTVA;
    }

    @Override
    public void setTauxTVA(double tauxTVA) {
        this.tauxTVA = tauxTVA;
    }
}
