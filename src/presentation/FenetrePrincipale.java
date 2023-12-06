package presentation;

import controller.ControllerAchatVente;
import controller.ControllerAfficherStock;
import controller.ControllerNouveauSuppressionProduit;
import controller.ControllerNouvelleSuppressionCategorie;
import metier.I_Catalogue;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class FenetrePrincipale extends JFrame implements ActionListener, WindowListener {

	private FenetreAccueil fenetreAccueil;
	private JButton btAfficher;
	private JButton btNouveauProduit;
	private JButton btSupprimerProduit;
	private JButton btNouvelleCategorie;
	private JButton btSupprimerCategorie;
	private ControllerAfficherStock contrAfficherStock;
	private ControllerNouveauSuppressionProduit contrNouveauSuppr;
	private ControllerNouvelleSuppressionCategorie contrNouveauSupprCat;
	private ControllerAchatVente contrAchatVente;
	private JButton btAchat;
	private JButton btVente;
	private JButton btQuitter;
	public static I_Catalogue cat;

	public FenetrePrincipale(FenetreAccueil fenetreAccueil) {
		this.fenetreAccueil = fenetreAccueil;
		setTitle("Produits");
		setBounds(750, 250, 320, 250);
		JPanel panAffichage = new JPanel();
		JPanel panNouveauSupprimerProduit = new JPanel();
		JPanel panNouveauSupprimerCategorie = new JPanel();
		JPanel panAchatVente = new JPanel();
		JPanel panQuitter = new JPanel();
		Container contentPane = getContentPane();
		contentPane.setLayout(new FlowLayout());
		contrAfficherStock = new ControllerAfficherStock();
		contrNouveauSuppr = new ControllerNouveauSuppressionProduit();
		contrNouveauSupprCat = new ControllerNouvelleSuppressionCategorie();
		contrAchatVente = new ControllerAchatVente();
		btAfficher = new JButton("Quantités en stock");
		btNouveauProduit = new JButton("Nouveau Produit");
		btSupprimerProduit = new JButton("Supprimer Produit");
		btNouvelleCategorie = new JButton("Nouvelle Categorie");
		btSupprimerCategorie = new JButton("Supprimer Categorie");
		btAchat = new JButton("Achat Produits");
		btVente = new JButton("Vente Produits");
		btQuitter = new JButton("Quitter");
		panAffichage.add(btAfficher);
		panNouveauSupprimerProduit.add(btNouveauProduit); 
		panNouveauSupprimerProduit.add(btSupprimerProduit);
		panNouveauSupprimerCategorie.add(btNouvelleCategorie);
		panNouveauSupprimerCategorie.add(btSupprimerCategorie);
		panAchatVente.add(btAchat); 
		panAchatVente.add(btVente);  
		panQuitter.add(btQuitter);

		contentPane.add(panAffichage);
		contentPane.add(panNouveauSupprimerCategorie);
		contentPane.add(panNouveauSupprimerProduit);
		contentPane.add(panAchatVente);
		contentPane.add(panQuitter);

		btAfficher.addActionListener(this);
		btNouveauProduit.addActionListener(this);
		btSupprimerProduit.addActionListener(this);
		btNouvelleCategorie.addActionListener(this);
		btSupprimerCategorie.addActionListener(this);
		btAchat.addActionListener(this);
		btVente.addActionListener(this);
		btQuitter.addActionListener(this);

		addWindowListener(this);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == btAfficher) {
			new FenetreAffichage(contrAfficherStock.afficherStock());
		}
		if (e.getSource() == btNouveauProduit) {
			new FenetreNouveauProduit(contrNouveauSupprCat.demandeListe());
		}
		if (e.getSource() == btSupprimerProduit) {
			new FenetreSuppressionProduit(contrNouveauSuppr.demandeSuppression());
		}

		if (e.getSource() == btNouvelleCategorie) {
			new FenetreNouvelleCategorie();
		}
		if (e.getSource() == btSupprimerCategorie) {
			new FenetreSuppressionCategorie(contrNouveauSupprCat.demandeListe());
		}
		if (e.getSource() == btAchat) {
			new FenetreAchat(contrAchatVente.demandeUpdate());
		}
		if (e.getSource() == btVente) {
			new FenetreVente(contrAchatVente.demandeUpdate());
		}
		if (e.getSource() == btQuitter) {
			this.dispose();
			this.fenetreAccueil.majCatalogues();
			this.fenetreAccueil.setVisible(true);
		}
	}

	public void windowClosing(WindowEvent arg0) {
		this.dispose();
		this.fenetreAccueil.setVisible(true);
	}
	public void windowActivated(WindowEvent arg0) {}
	public void windowClosed(WindowEvent arg0) {}
	public void windowDeactivated(WindowEvent arg0) {}
	public void windowDeiconified(WindowEvent arg0) {}
	public void windowIconified(WindowEvent arg0) {}
	public void windowOpened(WindowEvent arg0) {}

}
