package tests;

import metier.Categorie;
import metier.I_Categorie;
import metier.I_Produit;
import metier.Produit;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CategorieTest {
    private I_Produit produit;
    private I_Categorie categorie;

    @Before
    @Test
    public void testCreationProduit() {
        this.produit = new Produit("testProduit", 20, 10);
        assertNotNull(this.produit);
    }

    @Before
    @Test
    public void testCreationCategorie() {
        this.categorie = new Categorie("testCategorie", 0.5);
        this.produit.setCategorie(this.categorie);
        assertNotNull(this.categorie);
    }

    @Before
    @Test
    public void testCreationProduitAvecCategorie() {
        this.produit.setCategorie(this.categorie);
        assertNotNull(this.produit);
    }

    @Test
    public void testGetPrixTtcSansCategorie() {
        this.produit.setCategorie(null);
        assertEquals(240, this.produit.getPrixStockTTC(), 0);
    }

    @Test
    public void testGetPrixTtcAvecCategorie03() {
        this.categorie.setTauxTVA(0.3);
        assertEquals(260, this.produit.getPrixStockTTC(), 0);
    }

    @Test
    public void testGetPrixTtcAvecCategorie05() {
        assertEquals(300, this.produit.getPrixStockTTC(), 0);
    }

    @Test
    public void testGetPrixTtcAvecCategorie06() {
        this.categorie.setTauxTVA(0.6);
        assertEquals(320, this.produit.getPrixStockTTC(), 0);
    }

    @Test
    public void testGetPrixTtcAvecCategorie09() {
        this.categorie.setTauxTVA(0.9);
        assertEquals(380, this.produit.getPrixStockTTC(), 0);
    }

    @Test
    public void testGetPrixTtcAvecCategorie1() {
        this.categorie.setTauxTVA(1);
        assertEquals(400, this.produit.getPrixStockTTC(), 0);
    }

    @Test
    public void testGetPrixTtcAvecCategorie2() {
        this.categorie.setTauxTVA(2);
        assertEquals(600, this.produit.getPrixStockTTC(), 0);
    }
}
