package ro.fmi.pao.frameuriactiuni;

import ro.fmi.pao.framesraspuns.FrameRaspuns;
import ro.fmi.pao.gestionarecabinetmedical.GestionareCabinetMedical;
import ro.fmi.pao.gestionareprogramari.GestionareProgramari;
import ro.fmi.pao.model.Client;
import ro.fmi.pao.model.Programare;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class CitireCnpAfisareProgramari implements ActionListener {
    private final GestionareProgramari gestionareProgramari;
    private static GestionareCabinetMedical gestionareCabinetMedical;
    private static CitireCnpAfisareProgramari INSTANCE;
    private final JTextField cnpClient;
    private final JButton cauta;
    private final JFrame jFrame;

    public void faVizibil() {
        jFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        jFrame.setVisible(false);
        if (actionEvent.getSource() == cauta) {
            Client client = new Client("", "", cnpClient.getText());
            List<Programare> programari = gestionareProgramari.getToateProgramarileClient(client);
            if (programari.size() == 0) {
                new FrameRaspuns("Nimic gasit", "Nicio programare gasita pentru clientul cautat", gestionareCabinetMedical);
            } else {
                new FrameRaspuns("Programari client", programari.toString(), gestionareCabinetMedical);
            }
            Date data = new Date();
            GestionareCabinetMedical.scrieInCSV("SELECTAREA_TUTUROR_PROGRAMARILOR_UNUI_CLIENT, " + (new Timestamp(data.getTime())));
            INSTANCE.cnpClient.setText("");
        }
    }

    public static CitireCnpAfisareProgramari getInstance(GestionareProgramari gestionareProgramari, GestionareCabinetMedical gestionareCabinetMedical) {
        if (INSTANCE == null) {
            INSTANCE = new CitireCnpAfisareProgramari(gestionareProgramari, gestionareCabinetMedical);
        }
        return INSTANCE;
    }

    private CitireCnpAfisareProgramari(GestionareProgramari gestionareProgramari, GestionareCabinetMedical gestionareCabinetMedical) {
        this.gestionareProgramari = gestionareProgramari;
        CitireCnpAfisareProgramari.gestionareCabinetMedical = gestionareCabinetMedical;
        jFrame = new JFrame("Citeste cod parafa si data");
        jFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                CitireCnpAfisareProgramari.gestionareCabinetMedical.faVizibil();
                INSTANCE.cnpClient.setText("");
            }
        });
        JLabel cnp = new JLabel("CNP: ");
        cnpClient = new JTextField(20);
        cauta = new JButton("Cauta");
        cauta.addActionListener(this);
        jFrame.setLayout(new FlowLayout(FlowLayout.LEFT, 150, 10));
        jFrame.add(cnp);
        jFrame.add(cnpClient);
        jFrame.add(cauta);
        jFrame.setSize(500, 500);
    }
}
