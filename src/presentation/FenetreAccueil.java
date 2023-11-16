package presentation;

import controller.ControllerCatalogue;
import metier.I_Catalogue;

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.List;
import javax.swing.*;

public class FenetreAccueil extends JFrame implements ActionListener, WindowListener {

	private JButton btAjouter, btSupprimer, btSelectionner;
	private JTextField txtAjouter;
	private JLabel lbNbCatalogues;
	private JComboBox cmbSupprimer, cmbSelectionner;
	private TextArea taDetailCatalogues;
	private ControllerCatalogue contrSelectionnerCatalogue;

	public FenetreAccueil() {
		setTitle("Catalogues");
		setBounds(500, 500, 200, 125);
		Container contentPane = getContentPane();
		JPanel panInfosCatalogues = new JPanel();
		JPanel panNbCatalogues = new JPanel();
		JPanel panDetailCatalogues = new JPanel();
		JPanel panGestionCatalogues = new JPanel();
		JPanel panAjouter = new JPanel();
		JPanel panSupprimer = new JPanel();
		JPanel panSelectionner = new JPanel();
		panNbCatalogues.setBackground(Color.white);
		panDetailCatalogues.setBackground(Color.white);
		panAjouter.setBackground(Color.gray);
		panSupprimer.setBackground(Color.lightGray);
		panSelectionner.setBackground(Color.gray);
		
		panNbCatalogues.add(new JLabel("Nous avons actuellement : "));
		lbNbCatalogues = new JLabel();
		panNbCatalogues.add(lbNbCatalogues);
		
		taDetailCatalogues = new TextArea();
		taDetailCatalogues.setEditable(false);
		new JScrollPane(taDetailCatalogues);
		taDetailCatalogues.setPreferredSize(new Dimension(300, 100));
		panDetailCatalogues.add(taDetailCatalogues);

		panAjouter.add(new JLabel("Ajouter un catalogue : "));
		txtAjouter = new JTextField(10);
		panAjouter.add(txtAjouter);
		btAjouter = new JButton("Ajouter");
		panAjouter.add(btAjouter);

		panSupprimer.add(new JLabel("Supprimer un catalogue : "));
		cmbSupprimer = new JComboBox();
		cmbSupprimer.setPreferredSize(new Dimension(100, 20));
		panSupprimer.add(cmbSupprimer);
		btSupprimer = new JButton("Supprimer");
		panSupprimer.add(btSupprimer);

		panSelectionner.add(new JLabel("Selectionner un catalogue : "));
		cmbSelectionner = new JComboBox();
		cmbSelectionner.setPreferredSize(new Dimension(100, 20));
		panSelectionner.add(cmbSelectionner);
		btSelectionner = new JButton("Selectionner");
		panSelectionner.add(btSelectionner);
		
		panGestionCatalogues.setLayout (new BorderLayout());
		panGestionCatalogues.add(panAjouter, "North");
		panGestionCatalogues.add(panSupprimer);
		panGestionCatalogues.add(panSelectionner, "South");
		
		panInfosCatalogues.setLayout(new BorderLayout());
		panInfosCatalogues.add(panNbCatalogues, "North");
		panInfosCatalogues.add(panDetailCatalogues, "South");
				
		contentPane.add(panInfosCatalogues, "North");
		contentPane.add(panGestionCatalogues, "South");
		pack();

		btAjouter.addActionListener(this);
		btSupprimer.addActionListener(this);
		btSelectionner.addActionListener(this);

		this.contrSelectionnerCatalogue = new ControllerCatalogue();
		this.majCatalogues();

		addWindowListener(this);
		setVisible(true);
	}

	protected void majCatalogues() {
		String[] tab = this.contrSelectionnerCatalogue.lireNomCatalogues();
		modifierListesCatalogues(tab);
		String[] tab2 = new String[tab.length];
		for (int i = 0; i < tab.length; i++) {
			tab2[i] = tab[i] + " : " + this.contrSelectionnerCatalogue.lireNbProduitCatalogue(tab[i]) + " produits";
		}
		modifierDetailCatalogues(tab2);
		modifierNbCatalogues(tab.length);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btAjouter) {
			String texteAjout = txtAjouter.getText();
			if (!texteAjout.equals("")) {
				System.out.println("Ajouter le catalogue " + texteAjout);
				txtAjouter.setText(null);
				this.contrSelectionnerCatalogue.creerCatalogue(texteAjout);
				this.majCatalogues();
			}
		}
		if (e.getSource() == btSupprimer) {
			String texteSupprime = (String) cmbSupprimer.getSelectedItem();
			if (texteSupprime != null) {
				this.contrSelectionnerCatalogue.supprimerCatalogue(texteSupprime);
				this.majCatalogues();
			}
		}
		if (e.getSource() == btSelectionner) {
			String texteSelection = (String) cmbSelectionner.getSelectedItem();
			if (texteSelection != null) {
				this.contrSelectionnerCatalogue.selectionnerCatalogue(texteSelection);
				this.dispose();
				new FenetrePrincipale(this);
			}
		}
	}

	private void modifierListesCatalogues(String[] nomsCatalogues) {
		cmbSupprimer.removeAllItems();
		cmbSelectionner.removeAllItems();
		if (nomsCatalogues != null) {
			for (int i = 0; i < nomsCatalogues.length; i++) {
				cmbSupprimer.addItem(nomsCatalogues[i]);
				cmbSelectionner.addItem(nomsCatalogues[i]);
			}
		}
	}
	
	private void modifierNbCatalogues(int nb) {
		lbNbCatalogues.setText(nb + " catalogues");
	}
	
	private void modifierDetailCatalogues(String[] detailCatalogues) {
		taDetailCatalogues.setText("");
		if (detailCatalogues != null) {
			for (int i = 0; i < detailCatalogues.length; i++) {
				taDetailCatalogues.append(detailCatalogues[i] + "\n");
			}
		}
	}

	public void windowClosing(WindowEvent arg0) {
		System.exit(0);
	}
	public void windowActivated(WindowEvent arg0) {}
	public void windowClosed(WindowEvent arg0) {}
	public void windowDeactivated(WindowEvent arg0) {}
	public void windowDeiconified(WindowEvent arg0) {}
	public void windowIconified(WindowEvent arg0) {}
	public void windowOpened(WindowEvent arg0) {}
	public static void main(String[] args) {
		new FenetreAccueil();
	}
}
