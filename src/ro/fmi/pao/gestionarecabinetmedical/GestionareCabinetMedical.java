package ro.fmi.pao.gestionarecabinetmedical;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;

public abstract class GestionareCabinetMedical implements ActionListener {
    protected JButton btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11, btn12;
    protected JFrame jFrame;
    protected Container container;

    public static void scrieInCSV(String actiune) {
        try {
            FileWriter fileWriter = new FileWriter("audit.csv", true);
            fileWriter.append(actiune);
            fileWriter.append(System.lineSeparator());
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void faVizibil() {
        jFrame.setVisible(true);
    }
}
