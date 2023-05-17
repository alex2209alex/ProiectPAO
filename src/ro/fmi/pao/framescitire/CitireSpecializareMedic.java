package ro.fmi.pao.framescitire;

import ro.fmi.pao.gestionarecabinetmedical.GestionareCabinetMedical;
import ro.fmi.pao.gestionarepersoane.GestionarePersoane;
import ro.fmi.pao.model.SpecializareMedic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Timestamp;
import java.util.Date;

public class CitireSpecializareMedic implements ActionListener {
    private final GestionarePersoane gestionarePersoane;
    private static GestionareCabinetMedical gestionareCabinetMedical;
    private static CitireSpecializareMedic INSTANCE;
    private final JTextField codUnicSpecializare;
    private final JTextField denumire;
    private final JButton adauga;
    private final JFrame jFrame;

    public void faVizibil() {
        jFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == adauga) {
            gestionarePersoane.adaugaSpecializare(new SpecializareMedic(codUnicSpecializare.getText(), denumire.getText()));
            codUnicSpecializare.setText("");
            denumire.setText("");
            Date data = new Date();
            GestionareCabinetMedical.scrieInCSV("ADAUGA_SPECIALIZARE, " + (new Timestamp(data.getTime())));
            jFrame.setVisible(false);
        }
    }

    public static CitireSpecializareMedic getInstance(GestionarePersoane gestionarePersoane, GestionareCabinetMedical gestionareCabinetMedical) {
        if (INSTANCE == null) {
            INSTANCE = new CitireSpecializareMedic(gestionarePersoane, gestionareCabinetMedical);
        }
        INSTANCE.codUnicSpecializare.setText("");
        INSTANCE.denumire.setText("");
        return INSTANCE;
    }

    private CitireSpecializareMedic(GestionarePersoane gestionarePersoane, GestionareCabinetMedical gestionareCabinetMedical) {
        this.gestionarePersoane = gestionarePersoane;
        CitireSpecializareMedic.gestionareCabinetMedical = gestionareCabinetMedical;
        jFrame = new JFrame("Citeste specializare medic");
        jFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                CitireSpecializareMedic.gestionareCabinetMedical.faVizibil();
            }
        });
        JLabel cod = new JLabel("Cod unic specializare: ");
        JLabel den = new JLabel("Denumire: ");
        codUnicSpecializare = new JTextField(20);
        denumire = new JTextField(20);
        adauga = new JButton("Adauga");
        adauga.addActionListener(this);
        jFrame.setLayout(new FlowLayout(FlowLayout.LEFT, 150, 10));
        jFrame.add(cod);
        jFrame.add(codUnicSpecializare);
        jFrame.add(den);
        jFrame.add(denumire);
        jFrame.add(adauga);
        jFrame.setSize(500, 500);
    }
}
