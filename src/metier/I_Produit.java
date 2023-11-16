package metier;

public interface I_Produit {
	boolean ajouter(int qteAchetee);
	boolean enlever(int qteVendue);
	String getNom();
	int getQuantite();
	double getPrixUnitaireHT();
	double getPrixUnitaireTTC();
	double getPrixStockTTC();
	I_Catalogue getCatalogue();
	void setCatalogue(I_Catalogue catalogue);
	I_Categorie getCategorie();
	void setCategorie(I_Categorie categorie);
	String toString();
}