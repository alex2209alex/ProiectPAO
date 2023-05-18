package ro.fmi.pao.frameuriactiuni;

import ro.fmi.pao.framesraspuns.FrameRaspuns;
import ro.fmi.pao.gestionarecabinetmedical.GestionareCabinetMedical;
import ro.fmi.pao.gestionarepersoane.GestionarePersoane;
import ro.fmi.pao.model.Medic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

public class CitireCodParafaAfisareMedic implements ActionListener {
    private final GestionarePersoane gestionarePersoane;
    private static GestionareCabinetMedical gestionareCabinetMedical;
    private static CitireCodParafaAfisareMedic INSTANCE;
    private final JTextField codParafa;
    private final JButton cauta;
    private final JFrame jFrame;

    public void faVizibil() {
        jFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == cauta) {
            Optional<Medic> medic = gestionarePersoane.getMedicDupaCodParafa(codParafa.getText());
            if (medic.isEmpty()) {
                String text = "Niciun medic nu a fost gasit cu codul parafei " + codParafa.getText();
                new FrameRaspuns("Fara rezultat", text, gestionareCabinetMedical);
            } else {
                new FrameRaspuns("Medic gasit", medic.get().toString(), gestionareCabinetMedical);
            }
            codParafa.setText("");
            Date data = new Date();
            GestionareCabinetMedical.scrieInCSV("SELECTARE_UN_MEDIC_DUPA_COD_PARAFA, " + (new Timestamp(data.getTime())));
            jFrame.setVisible(false);
        }
    }

    public static CitireCodParafaAfisareMedic getInstance(GestionarePersoane gestionarePersoane, GestionareCabinetMedical gestionareCabinetMedical) {
        if (INSTANCE == null) {
            INSTANCE = new CitireCodParafaAfisareMedic(gestionarePersoane, gestionareCabinetMedical);
        }
        return INSTANCE;
    }

    private CitireCodParafaAfisareMedic(GestionarePersoane gestionarePersoane, GestionareCabinetMedical gestionareCabinetMedical) {
        this.gestionarePersoane = gestionarePersoane;
        CitireCodParafaAfisareMedic.gestionareCabinetMedical = gestionareCabinetMedical;
        jFrame = new JFrame("Citeste cod parafa");
        jFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                CitireCodParafaAfisareMedic.gestionareCabinetMedical.faVizibil();
                INSTANCE.codParafa.setText("");
            }
        });
        JLabel cod = new JLabel("Cod parafa: ");
        codParafa = new JTextField(20);
        cauta = new JButton("Cauta");
        cauta.addActionListener(this);
        jFrame.setLayout(new FlowLayout(FlowLayout.LEFT, 150, 10));
        jFrame.add(cod);
        jFrame.add(codParafa);
        jFrame.add(cauta);
        jFrame.setSize(500, 500);
    }
}
