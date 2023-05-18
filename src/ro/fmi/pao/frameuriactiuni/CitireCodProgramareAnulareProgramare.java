package ro.fmi.pao.frameuriactiuni;

import ro.fmi.pao.gestionarecabinetmedical.GestionareCabinetMedical;
import ro.fmi.pao.gestionareprogramari.GestionareProgramari;
import ro.fmi.pao.model.Programare;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Timestamp;
import java.util.Date;

public class CitireCodProgramareAnulareProgramare implements ActionListener {
    private final GestionareProgramari gestionareProgramari;
    private static GestionareCabinetMedical gestionareCabinetMedical;
    private static CitireCodProgramareAnulareProgramare INSTANCE;
    private final JTextField codProgramare;
    private final JButton anuleaza;
    private final JFrame jFrame;

    public void faVizibil() {
        jFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == anuleaza) {
            Programare programare = new Programare(codProgramare.getText(), null, null, null);
            gestionareProgramari.anuleazaProgramare(programare);
            Date data = new Date();
            GestionareCabinetMedical.scrieInCSV("ANULARE_PROGRAMARE, " + (new Timestamp(data.getTime())));
            codProgramare.setText("");
            jFrame.setVisible(false);
        }
    }

    public static CitireCodProgramareAnulareProgramare getInstance(GestionareProgramari gestionareProgramari, GestionareCabinetMedical gestionareCabinetMedical) {
        if (INSTANCE == null) {
            INSTANCE = new CitireCodProgramareAnulareProgramare(gestionareProgramari, gestionareCabinetMedical);
        }
        return INSTANCE;
    }

    private CitireCodProgramareAnulareProgramare(GestionareProgramari gestionareProgramari, GestionareCabinetMedical gestionareCabinetMedical) {
        this.gestionareProgramari = gestionareProgramari;
        CitireCodProgramareAnulareProgramare.gestionareCabinetMedical = gestionareCabinetMedical;
        jFrame = new JFrame("Citeste cod programare");
        jFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                CitireCodProgramareAnulareProgramare.gestionareCabinetMedical.faVizibil();
                INSTANCE.codProgramare.setText("");
            }
        });
        JLabel cod = new JLabel("Cod programare: ");
        codProgramare = new JTextField(20);
        anuleaza = new JButton("Anuleaza programarea");
        anuleaza.addActionListener(this);
        jFrame.setLayout(new FlowLayout(FlowLayout.LEFT, 150, 10));
        jFrame.add(cod);
        jFrame.add(codProgramare);
        jFrame.add(anuleaza);
        jFrame.setSize(500, 500);
    }
}
