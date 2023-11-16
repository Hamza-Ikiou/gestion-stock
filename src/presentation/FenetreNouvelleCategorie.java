package presentation;

import controller.ControllerNouvelleSuppressionCategorie;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FenetreNouvelleCategorie extends JFrame implements ActionListener {
    private JTextField txtNom;
    private JTextField txtTauxTVA;
    private JButton btValider;

    public FenetreNouvelleCategorie() {

        setTitle("Création Categorie");
        setBounds(500, 500, 200, 250);
        Container contentPane = getContentPane();
        contentPane.setLayout(new FlowLayout());

        JLabel labNom = new JLabel("Nom categorie");
        JLabel labTauxTVA = new JLabel("Taux de TVA");

        contentPane.add(labNom);
        txtNom = new JTextField(15);
        contentPane.add(txtNom);

        contentPane.add(labTauxTVA);
        txtTauxTVA = new JTextField(15);
        contentPane.add(txtTauxTVA);

        btValider = new JButton("Valider");
        contentPane.add(btValider);

        btValider.addActionListener(this);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btValider) {
            new ControllerNouvelleSuppressionCategorie().creerCategorie(txtNom.getText(), txtTauxTVA.getText());
        }
        this.dispose();
    }
}
