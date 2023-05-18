package ro.fmi.pao.framesraspuns;

import ro.fmi.pao.gestionarecabinetmedical.GestionareCabinetMedical;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FrameRaspuns {
    private static GestionareCabinetMedical gestionareCabinetMedical;

    public FrameRaspuns(String title, String text, GestionareCabinetMedical gestionareCabinetMedical) {
        FrameRaspuns.gestionareCabinetMedical = gestionareCabinetMedical;
        JFrame jFrame = new JFrame(title);
        jFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                FrameRaspuns.gestionareCabinetMedical.faVizibil();
            }
        });
        jFrame.setBounds(0, 0, 1000, 500);
        Container container = jFrame.getContentPane();
        container.setLayout(null);
        JLabel jLabel = new JLabel("<html>" + text.replace("},", "},<br>") + "</html>");
        jLabel.setBounds(0, 0, 1000, 100);
        container.add(jLabel);
        jFrame.setVisible(true);
    }
}
