package presentation;

import controller.ControllerNouveauSuppressionProduit;

import java.awt.*;
import java.awt.event.*;
import java.util.Objects;
import javax.swing.*;

public class FenetreSuppressionProduit extends JFrame implements ActionListener {

	private JButton btSupprimer;
	private JComboBox<String> combo;
	
	public FenetreSuppressionProduit(String lesProduits[]) {
		
		setTitle("Suppression produit");
		setBounds(500, 500, 200, 105);
		Container contentPane = getContentPane();
		contentPane.setLayout(new FlowLayout());
		btSupprimer = new JButton("Supprimer");

		combo = new JComboBox<String>(lesProduits);
		combo.setPreferredSize(new Dimension(100, 20));
		contentPane.add(new JLabel("Produit"));
		contentPane.add(combo);
		contentPane.add(btSupprimer);

		btSupprimer.addActionListener(this);

		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.btSupprimer) {
			new ControllerNouveauSuppressionProduit().supprimerProduit(Objects.requireNonNull(this.combo.getSelectedItem()).toString());
		}
		this.dispose();
	}

}
