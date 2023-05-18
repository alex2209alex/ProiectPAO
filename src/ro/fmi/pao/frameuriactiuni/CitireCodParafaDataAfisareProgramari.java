package ro.fmi.pao.frameuriactiuni;

import ro.fmi.pao.framesraspuns.FrameRaspuns;
import ro.fmi.pao.gestionarecabinetmedical.GestionareCabinetMedical;
import ro.fmi.pao.gestionarepersoane.GestionarePersoane;
import ro.fmi.pao.gestionareprogramari.GestionareProgramari;
import ro.fmi.pao.model.Medic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Timestamp;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

public class CitireCodParafaDataAfisareProgramari implements ActionListener {
    private final GestionarePersoane gestionarePersoane;
    private final GestionareProgramari gestionareProgramari;
    private static GestionareCabinetMedical gestionareCabinetMedical;
    private static CitireCodParafaDataAfisareProgramari INSTANCE;
    private final JTextField codParafa;
    private final JTextField anData;
    private final JTextField lunaData;
    private final JTextField ziData;
    private final JButton cauta;
    private final JFrame jFrame;

    public void faVizibil() {
        jFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        jFrame.setVisible(false);
        if (actionEvent.getSource() == cauta) {
            Optional<Medic> medic = gestionarePersoane.getMedicDupaCodParafa(codParafa.getText());
            if (medic.isEmpty()) {
                String text = "Niciun medic nu a fost gasit cu codul parafei " + codParafa.getText();
                new FrameRaspuns("Input invalid", text, gestionareCabinetMedical);
            } else {
                try {
                    LocalDate localDate = LocalDate.of(Integer.parseInt(anData.getText()), Integer.parseInt(lunaData.getText()), Integer.parseInt(ziData.getText()));
                    new FrameRaspuns("Programari medic in ziua", gestionareProgramari.getProgramariPentruMedicDinZiua(medic.get(), localDate).toString(), gestionareCabinetMedical);
                    Date data = new Date();
                    GestionareCabinetMedical.scrieInCSV("SELECTAREA_PROGRAMARILOR_UNUI_MEDIC_DINTR_O_ZI_ORDONATA_CRESCATOR_DUPA_ORA, " + (new Timestamp(data.getTime())));
                } catch (NumberFormatException e) {
                    new FrameRaspuns("Input invalid", "Data e formata doar din numere", gestionareCabinetMedical);
                } catch (DateTimeException e) {
                    new FrameRaspuns("Input invalid", "Format data nerespectat", gestionareCabinetMedical);
                }
            }
            INSTANCE.codParafa.setText("");
            INSTANCE.anData.setText("");
            INSTANCE.lunaData.setText("");
            INSTANCE.ziData.setText("");
        }
    }

    public static CitireCodParafaDataAfisareProgramari getInstance(GestionarePersoane gestionarePersoane, GestionareProgramari gestionareProgramari, GestionareCabinetMedical gestionareCabinetMedical) {
        if (INSTANCE == null) {
            INSTANCE = new CitireCodParafaDataAfisareProgramari(gestionarePersoane, gestionareProgramari, gestionareCabinetMedical);
        }
        return INSTANCE;
    }

    private CitireCodParafaDataAfisareProgramari(GestionarePersoane gestionarePersoane, GestionareProgramari gestionareProgramari, GestionareCabinetMedical gestionareCabinetMedical) {
        this.gestionarePersoane = gestionarePersoane;
        this.gestionareProgramari = gestionareProgramari;
        CitireCodParafaDataAfisareProgramari.gestionareCabinetMedical = gestionareCabinetMedical;
        jFrame = new JFrame("Citeste cod parafa si data");
        jFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                CitireCodParafaDataAfisareProgramari.gestionareCabinetMedical.faVizibil();
                INSTANCE.codParafa.setText("");
                INSTANCE.anData.setText("");
                INSTANCE.lunaData.setText("");
                INSTANCE.ziData.setText("");
            }
        });
        JLabel cod = new JLabel("Cod parafa: ");
        codParafa = new JTextField(20);
        JLabel an = new JLabel("An: ");
        anData = new JTextField(20);
        JLabel luna = new JLabel("Luna: ");
        lunaData = new JTextField(20);
        JLabel zi = new JLabel("Zi: ");
        ziData = new JTextField(20);
        cauta = new JButton("Cauta");
        cauta.addActionListener(this);
        jFrame.setLayout(new FlowLayout(FlowLayout.LEFT, 150, 10));
        jFrame.add(cod);
        jFrame.add(codParafa);
        jFrame.add(an);
        jFrame.add(anData);
        jFrame.add(luna);
        jFrame.add(lunaData);
        jFrame.add(zi);
        jFrame.add(ziData);
        jFrame.add(cauta);
        jFrame.setSize(500, 500);
    }
}
