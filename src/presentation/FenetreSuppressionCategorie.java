package presentation;

import controller.ControllerNouvelleSuppressionCategorie;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class FenetreSuppressionCategorie extends JFrame implements ActionListener {
    private JButton btSupprimer;
    private JComboBox<String> combo;

    public FenetreSuppressionCategorie(String lesCategories[]) {

        setTitle("Suppression produit");
        setBounds(500, 500, 200, 105);
        Container contentPane = getContentPane();
        contentPane.setLayout(new FlowLayout());
        btSupprimer = new JButton("Supprimer");

        combo = new JComboBox<String>(lesCategories);
        combo.setPreferredSize(new Dimension(100, 20));
        contentPane.add(new JLabel("Categories"));
        contentPane.add(combo);
        contentPane.add(btSupprimer);

        btSupprimer.addActionListener(this);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btSupprimer) {
            new ControllerNouvelleSuppressionCategorie().supprimerCategorie(Objects.requireNonNull(this.combo.getSelectedItem()).toString());
        }
        this.dispose();
    }
}
