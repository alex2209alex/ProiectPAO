package ro.fmi.pao.frameuriactiuni;

import ro.fmi.pao.framesraspuns.FrameRaspuns;
import ro.fmi.pao.gestionarecabinetmedical.GestionareCabinetMedical;
import ro.fmi.pao.gestionarepersoane.GestionarePersoane;
import ro.fmi.pao.gestionareprogramari.GestionareProgramari;
import ro.fmi.pao.model.Client;
import ro.fmi.pao.model.Medic;
import ro.fmi.pao.model.Programare;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Timestamp;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

public class CitireProgramare implements ActionListener {
    private final GestionarePersoane gestionarePersoane;
    private final GestionareProgramari gestionareProgramari;
    private static GestionareCabinetMedical gestionareCabinetMedical;
    private static CitireProgramare INSTANCE;
    private final JTextField codProgramare;
    private final JTextField cnpClient;
    private final JTextField codParafa;
    private final JTextField anData;
    private final JTextField lunaData;
    private final JTextField ziData;
    private final JTextField oraData;
    private final JTextField minutData;
    private final JButton adauga;
    private final JFrame jFrame;

    public void faVizibil() {
        jFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        jFrame.setVisible(false);
        if (actionEvent.getSource() == adauga) {
            Date data = new Date();
            GestionareCabinetMedical.scrieInCSV("ADAUGA_PROGRAMARE, " + (new Timestamp(data.getTime())));
            Optional<Client> client = gestionarePersoane.getClientDupaCnp(cnpClient.getText());
            Optional<Medic> medic = gestionarePersoane.getMedicDupaCodParafa(codParafa.getText());
            if (medic.isEmpty() || client.isEmpty()) {
                new FrameRaspuns("Input invalid", "Medicul sau clientul cautati nu exista", gestionareCabinetMedical);
            } else {
                try {
                    LocalDateTime ldt = LocalDateTime.of(Integer.parseInt(anData.getText()), Integer.parseInt(lunaData.getText()), Integer.parseInt(ziData.getText()), Integer.parseInt(oraData.getText()), Integer.parseInt(minutData.getText()));
                    gestionareProgramari.adaugaProgramare(new Programare(codProgramare.getText(), client.get(), medic.get(), ldt));
                } catch (NumberFormatException e) {
                    new FrameRaspuns("Input invalid", "Data e formata doar din numere", gestionareCabinetMedical);
                } catch (DateTimeException e) {
                    new FrameRaspuns("Input invalid", "Format data nerespectat", gestionareCabinetMedical);
                }
            }
            INSTANCE.codProgramare.setText("");
            INSTANCE.cnpClient.setText("");
            INSTANCE.codParafa.setText("");
            INSTANCE.anData.setText("");
            INSTANCE.lunaData.setText("");
            INSTANCE.ziData.setText("");
            INSTANCE.oraData.setText("");
            INSTANCE.minutData.setText("");
        }
    }

    public static CitireProgramare getInstance(GestionarePersoane gestionarePersoane, GestionareProgramari gestionareProgramari, GestionareCabinetMedical gestionareCabinetMedical) {
        if (INSTANCE == null) {
            INSTANCE = new CitireProgramare(gestionarePersoane, gestionareProgramari, gestionareCabinetMedical);
        }
        INSTANCE.jFrame.setVisible(true);
        return INSTANCE;
    }

    private CitireProgramare(GestionarePersoane gestionarePersoane, GestionareProgramari gestionareProgramari, GestionareCabinetMedical gestionareCabinetMedical) {
        this.gestionarePersoane = gestionarePersoane;
        this.gestionareProgramari = gestionareProgramari;
        CitireProgramare.gestionareCabinetMedical = gestionareCabinetMedical;
        jFrame = new JFrame("Citeste cod parafa si data");
        jFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                CitireProgramare.gestionareCabinetMedical.faVizibil();
                INSTANCE.codProgramare.setText("");
                INSTANCE.cnpClient.setText("");
                INSTANCE.codParafa.setText("");
                INSTANCE.anData.setText("");
                INSTANCE.lunaData.setText("");
                INSTANCE.ziData.setText("");
                INSTANCE.oraData.setText("");
                INSTANCE.minutData.setText("");
            }
        });
        JLabel codPro = new JLabel("Cod programare");
        codProgramare = new JTextField(20);
        JLabel cnp = new JLabel("CNP: ");
        cnpClient = new JTextField(20);
        JLabel cod = new JLabel("Cod parafa: ");
        codParafa = new JTextField(20);
        JLabel an = new JLabel("An: ");
        anData = new JTextField(20);
        JLabel luna = new JLabel("Luna: ");
        lunaData = new JTextField(20);
        JLabel zi = new JLabel("Zi: ");
        ziData = new JTextField(20);
        JLabel ora = new JLabel("Ora: ");
        oraData = new JTextField(20);
        JLabel minut = new JLabel("Minut: ");
        minutData = new JTextField(20);
        adauga = new JButton("Adauga programarea");
        adauga.addActionListener(this);
        jFrame.setLayout(new FlowLayout(FlowLayout.LEFT, 150, 10));
        jFrame.add(codPro);
        jFrame.add(codProgramare);
        jFrame.add(cnp);
        jFrame.add(cnpClient);
        jFrame.add(cod);
        jFrame.add(codParafa);
        jFrame.add(an);
        jFrame.add(anData);
        jFrame.add(luna);
        jFrame.add(lunaData);
        jFrame.add(zi);
        jFrame.add(ziData);
        jFrame.add(ora);
        jFrame.add(oraData);
        jFrame.add(minut);
        jFrame.add(minutData);
        jFrame.add(adauga);
        jFrame.setSize(500, 600);
    }
}
