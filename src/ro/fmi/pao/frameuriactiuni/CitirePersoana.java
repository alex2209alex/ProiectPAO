package ro.fmi.pao.frameuriactiuni;

import ro.fmi.pao.framesraspuns.FrameRaspuns;
import ro.fmi.pao.gestionarecabinetmedical.GestionareCabinetMedical;
import ro.fmi.pao.gestionarepersoane.GestionarePersoane;
import ro.fmi.pao.model.Client;
import ro.fmi.pao.model.Medic;
import ro.fmi.pao.model.SpecializareMedic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Timestamp;
import java.util.Date;

public class CitirePersoana implements ActionListener {
    private final GestionarePersoane gestionarePersoane;
    private static GestionareCabinetMedical gestionareCabinetMedical;
    private static CitirePersoana INSTANCE;
    private final JLabel num;
    private final JTextField nume;
    private final JLabel pre;
    private final JTextField prenume;
    private final JButton medic;
    private final JButton client;
    private final JLabel cnp;
    private final JTextField cnpClient;
    private final JLabel cod;
    private final JTextField codParafa;
    private final JButton adaugaClient;
    private final JButton adaugaSpecializari;
    private final JLabel spe;
    private final JTextField specializare;
    private final JButton adaugaSpecializare;
    private final JButton adaugaMedic;
    private final JFrame jFrame;
    private Medic entitateMedic;

    public void faVizibil() {
        jFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        jFrame.setVisible(false);
        if (actionEvent.getSource() == client) {
            jFrame.remove(client);
            jFrame.remove(medic);
            jFrame.add(cnp);
            jFrame.add(cnpClient);
            jFrame.add(adaugaClient);
            jFrame.setVisible(true);
        } else if (actionEvent.getSource() == adaugaClient) {
            gestionarePersoane.adaugaPersoana(new Client(nume.getText(), prenume.getText(), cnpClient.getText()));
            Date data = new Date();
            GestionareCabinetMedical.scrieInCSV("ADAUGA_SPECIALIZARE, " + (new Timestamp(data.getTime())));
        } else if (actionEvent.getSource() == medic) {
            jFrame.remove(client);
            jFrame.remove(medic);
            jFrame.add(cod);
            jFrame.add(codParafa);
            jFrame.add(adaugaSpecializari);
            jFrame.setVisible(true);
        } else if (actionEvent.getSource() == adaugaSpecializari) {
            entitateMedic = new Medic(nume.getText(), prenume.getText(), codParafa.getText());
            jFrame.remove(num);
            jFrame.remove(nume);
            jFrame.remove(pre);
            jFrame.remove(prenume);
            jFrame.remove(cod);
            jFrame.remove(codParafa);
            jFrame.remove(adaugaSpecializari);
            jFrame.add(spe);
            jFrame.add(specializare);
            jFrame.add(adaugaSpecializare);
            jFrame.add(adaugaMedic);
            jFrame.setVisible(true);
        } else if (actionEvent.getSource() == adaugaSpecializare) {
            SpecializareMedic specializareMedic = new SpecializareMedic(specializare.getText(), "");
            if (entitateMedic.areSpecializarea(specializareMedic)) {
                new FrameRaspuns("Input invalid", "Medicul are deja acea specializare", gestionareCabinetMedical);
            } else if (!gestionarePersoane.existaSpecializare(specializareMedic)) {
                new FrameRaspuns("Input invalid", "Codul specializarii nu exista", gestionareCabinetMedical);
            } else {
                entitateMedic.adaugaSpecializare(gestionarePersoane.getSpecializare(specializareMedic));
                specializare.setText("");
                jFrame.setVisible(true);
            }
        } else if (actionEvent.getSource() == adaugaMedic) {
            gestionarePersoane.adaugaPersoana(entitateMedic);
        }
    }

    private void aranjeaza() {
        jFrame.add(num);
        jFrame.add(nume);
        jFrame.add(pre);
        jFrame.add(prenume);
        jFrame.add(medic);
        jFrame.add(client);
        nume.setText("");
        prenume.setText("");
        cnpClient.setText("");
        codParafa.setText("");
        jFrame.remove(cnp);
        jFrame.remove(cnpClient);
        jFrame.remove(adaugaClient);
        jFrame.remove(cod);
        jFrame.remove(codParafa);
        jFrame.remove(adaugaSpecializari);
        jFrame.remove(spe);
        jFrame.remove(specializare);
        jFrame.remove(adaugaSpecializare);
        jFrame.remove(adaugaMedic);
        jFrame.setSize(500, 500);
    }

    public static CitirePersoana getInstance(GestionarePersoane gestionarePersoane, GestionareCabinetMedical gestionareCabinetMedical) {
        if (INSTANCE == null) {
            INSTANCE = new CitirePersoana(gestionarePersoane, gestionareCabinetMedical);
        }
        INSTANCE.aranjeaza();
        return INSTANCE;
    }

    private CitirePersoana(GestionarePersoane gestionarePersoane, GestionareCabinetMedical gestionareCabinetMedical) {
        this.gestionarePersoane = gestionarePersoane;
        CitirePersoana.gestionareCabinetMedical = gestionareCabinetMedical;
        jFrame = new JFrame("Citeste persoana");
        jFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                CitirePersoana.gestionareCabinetMedical.faVizibil();
            }
        });
        num = new JLabel("Nume: ");
        nume = new JTextField(20);
        pre = new JLabel("Prenume: ");
        prenume = new JTextField(20);
        medic = new JButton("1.Medic");
        medic.addActionListener(this);
        client = new JButton("2.Client");
        client.addActionListener(this);
        cnp = new JLabel("CNP: ");
        cnpClient = new JTextField(20);
        cod = new JLabel("Cod parafa: ");
        codParafa = new JTextField(20);
        adaugaClient = new JButton("Adauga client");
        adaugaClient.addActionListener(this);
        adaugaSpecializari = new JButton("Adauga specializari la medic");
        adaugaSpecializari.addActionListener(this);
        spe = new JLabel("Cod specializare: ");
        specializare = new JTextField(20);
        adaugaSpecializare = new JButton("1.Adauga specializare");
        adaugaSpecializare.addActionListener(this);
        adaugaMedic = new JButton("2.Adauga medic");
        adaugaMedic.addActionListener(this);
        jFrame.setLayout(new FlowLayout(FlowLayout.LEFT, 170, 10));
    }
}
