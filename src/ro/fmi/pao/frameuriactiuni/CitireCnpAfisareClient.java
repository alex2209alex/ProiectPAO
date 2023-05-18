package ro.fmi.pao.frameuriactiuni;

import ro.fmi.pao.framesraspuns.FrameRaspuns;
import ro.fmi.pao.gestionarecabinetmedical.GestionareCabinetMedical;
import ro.fmi.pao.gestionarepersoane.GestionarePersoane;
import ro.fmi.pao.model.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

public class CitireCnpAfisareClient implements ActionListener {
    private final GestionarePersoane gestionarePersoane;
    private static GestionareCabinetMedical gestionareCabinetMedical;
    private static CitireCnpAfisareClient INSTANCE;
    private final JTextField cnpClient;
    private final JButton cauta;
    private final JFrame jFrame;

    public void faVizibil() {
        jFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == cauta) {
            Optional<Client> client = gestionarePersoane.getClientDupaCnp(cnpClient.getText());
            if (client.isEmpty()) {
                String text = "Niciun client nu a fost gasit cu CNP-ul " + cnpClient.getText();
                new FrameRaspuns("Fara rezultat", text, gestionareCabinetMedical);
            } else {
                new FrameRaspuns("Client gasit", client.get().toString(), gestionareCabinetMedical);
            }
            cnpClient.setText("");
            Date data = new Date();
            GestionareCabinetMedical.scrieInCSV("SELECTARE_UN_CLIENT_DUPA_CNP, " + (new Timestamp(data.getTime())));
            jFrame.setVisible(false);
        }
    }

    public static CitireCnpAfisareClient getInstance(GestionarePersoane gestionarePersoane, GestionareCabinetMedical gestionareCabinetMedical) {
        if (INSTANCE == null) {
            INSTANCE = new CitireCnpAfisareClient(gestionarePersoane, gestionareCabinetMedical);
        }
        return INSTANCE;
    }

    private CitireCnpAfisareClient(GestionarePersoane gestionarePersoane, GestionareCabinetMedical gestionareCabinetMedical) {
        this.gestionarePersoane = gestionarePersoane;
        CitireCnpAfisareClient.gestionareCabinetMedical = gestionareCabinetMedical;
        jFrame = new JFrame("Citeste CNP");
        jFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                CitireCnpAfisareClient.gestionareCabinetMedical.faVizibil();
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
