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
import java.util.List;

public class CitireCodSpecializareAfisareMedici implements ActionListener {
    private final GestionarePersoane gestionarePersoane;
    private static GestionareCabinetMedical gestionareCabinetMedical;
    private static CitireCodSpecializareAfisareMedici INSTANCE;
    private final JTextField codSpecializare;
    private final JButton cauta;
    private final JFrame jFrame;

    public void faVizibil() {
        jFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == cauta) {
            List<Medic> medici = gestionarePersoane.getMediciDupaSpecializare(codSpecializare.getText());
            if (medici.size() == 0) {
                new FrameRaspuns("Nimic gasit", "Niciun medic nu a fost gasit cu specializarea introdusa", gestionareCabinetMedical);
            } else {
                new FrameRaspuns("Medici cu specializare", medici.toString(), gestionareCabinetMedical);
            }
            codSpecializare.setText("");
            Date data = new Date();
            GestionareCabinetMedical.scrieInCSV("SELECTAREA_TUTUROR_MEDICILOR_DUPA_O_SPECIALIZARE_ORDONATI_DUPA_NUME_SI_PRENUME, " + (new Timestamp(data.getTime())));
            jFrame.setVisible(false);
        }
    }

    public static CitireCodSpecializareAfisareMedici getInstance(GestionarePersoane gestionarePersoane, GestionareCabinetMedical gestionareCabinetMedical) {
        if (INSTANCE == null) {
            INSTANCE = new CitireCodSpecializareAfisareMedici(gestionarePersoane, gestionareCabinetMedical);
        }
        return INSTANCE;
    }

    private CitireCodSpecializareAfisareMedici(GestionarePersoane gestionarePersoane, GestionareCabinetMedical gestionareCabinetMedical) {
        this.gestionarePersoane = gestionarePersoane;
        CitireCodSpecializareAfisareMedici.gestionareCabinetMedical = gestionareCabinetMedical;
        jFrame = new JFrame("Medici cu specializare");
        jFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                CitireCodSpecializareAfisareMedici.gestionareCabinetMedical.faVizibil();
                INSTANCE.codSpecializare.setText("");
            }
        });
        JLabel cod = new JLabel("Cod specializare: ");
        codSpecializare = new JTextField(20);
        cauta = new JButton("Cauta");
        cauta.addActionListener(this);
        jFrame.setLayout(new FlowLayout(FlowLayout.LEFT, 150, 10));
        jFrame.add(cod);
        jFrame.add(codSpecializare);
        jFrame.add(cauta);
        jFrame.setSize(500, 500);
    }
}
