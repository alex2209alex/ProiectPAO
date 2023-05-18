package ro.fmi.pao.gestionarecabinetmedical;

import ro.fmi.pao.framesraspuns.FrameRaspuns;
import ro.fmi.pao.frameuriactiuni.*;
import ro.fmi.pao.gestionarepersoane.GestionarePersoaneJDBC;
import ro.fmi.pao.gestionareprogramari.GestionareProgramariJDBC;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static ro.fmi.pao.utile.Constante.*;

public class GestionareCabinetMedicalJDBC extends GestionareCabinetMedical {
    private static GestionareCabinetMedicalJDBC INSTANCE;

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        jFrame.setVisible(false);
        if (actionEvent.getSource() == btn1) {
            CitireSpecializareMedic.getInstance(GestionarePersoaneJDBC.getInstance(), GestionareCabinetMedicalJDBC.getInstance()).faVizibil();
        } else if (actionEvent.getSource() == btn2) {
            CitirePersoana.getInstance(GestionarePersoaneJDBC.getInstance(), GestionareCabinetMedicalJDBC.getInstance()).faVizibil();
        } else if (actionEvent.getSource() == btn3) {
            CitireProgramare.getInstance(GestionarePersoaneJDBC.getInstance(), GestionareProgramariJDBC.getInstance(), GestionareCabinetMedicalJDBC.getInstance()).faVizibil();
        } else if (actionEvent.getSource() == btn4) {
            new FrameRaspuns("Toti medicii", GestionarePersoaneJDBC.getInstance().getTotiMedicii().toString(), GestionareCabinetMedicalJDBC.getInstance());
        } else if (actionEvent.getSource() == btn5) {
            new FrameRaspuns("Toti clientii", GestionarePersoaneJDBC.getInstance().getTotiClientii().toString(), GestionareCabinetMedicalJDBC.getInstance());
        } else if (actionEvent.getSource() == btn6) {
            CitireCodParafaAfisareMedic.getInstance(GestionarePersoaneJDBC.getInstance(), GestionareCabinetMedicalJDBC.getInstance()).faVizibil();
        } else if (actionEvent.getSource() == btn7) {
            CitireCnpAfisareClient.getInstance(GestionarePersoaneJDBC.getInstance(), GestionareCabinetMedicalJDBC.getInstance()).faVizibil();
        } else if (actionEvent.getSource() == btn8) {
            CitireCodProgramareAnulareProgramare.getInstance(GestionareProgramariJDBC.getInstance(), GestionareCabinetMedicalJDBC.getInstance()).faVizibil();
        } else if (actionEvent.getSource() == btn9) {
            CitireCodParafaDataAfisareProgramari.getInstance(GestionarePersoaneJDBC.getInstance(), GestionareProgramariJDBC.getInstance(), GestionareCabinetMedicalJDBC.getInstance()).faVizibil();
        } else if (actionEvent.getSource() == btn10) {
            CitireCnpAfisareProgramari.getInstance(GestionareProgramariJDBC.getInstance(), GestionareCabinetMedicalJDBC.getInstance()).faVizibil();
        } else if (actionEvent.getSource() == btn11) {
            CitireCodSpecializareAfisareMedici.getInstance(GestionarePersoaneJDBC.getInstance(), GestionareCabinetMedicalJDBC.getInstance()).faVizibil();
        } else {
            jFrame.dispose();
        }
    }

    public static GestionareCabinetMedicalJDBC getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GestionareCabinetMedicalJDBC();
            INSTANCE.jFrame = new JFrame("Selecteaza operatia\n");
            INSTANCE.jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            INSTANCE.jFrame.setBounds(0, 0, 700, 500);
            INSTANCE.container = INSTANCE.jFrame.getContentPane();
            INSTANCE.container.setLayout(null);
            INSTANCE.btn1 = new JButton(ADAUGA_SPECIALIZARE + ".Adauga specializare\n");
            INSTANCE.btn2 = new JButton(ADAUGA_PERSOANA + ".Adauga persoana\n");
            INSTANCE.btn3 = new JButton(ADAUGA_PROGRAMARE + ".Adaugare programare\n");
            INSTANCE.btn4 = new JButton(SELECTARE_TOTI_MEDICII_ORDONATI_DUPA_NUME_SI_PRENUME + ".Selectare toti medicii ordonati dupa nume si prenume\n");
            INSTANCE.btn5 = new JButton(SELECTARE_TOTI_CLIENTII_ORDONATI_DUPA_NUME_SI_PRENUME + ".Selectare toti clientii ordonati dupa nume si prenume\n");
            INSTANCE.btn6 = new JButton(SELECTARE_UN_MEDIC_DUPA_COD_PARAFA + ".Selectare un medic dupa cod parafa\n");
            INSTANCE.btn7 = new JButton(SELECTARE_UN_CLIENT_DUPA_CNP + ".Selectare un client dupa cnp\n");
            INSTANCE.btn8 = new JButton(ANULARE_PROGRAMARE + ".Anulare programare\n");
            INSTANCE.btn9 = new JButton(SELECTAREA_PROGRAMARILOR_UNUI_MEDIC_DINTR_O_ZI_ORDONATA_CRESCATOR_DUPA_ORA + ".Selectarea programarilor unui medic dintr-o zi ordonata crescator dupa ora\n");
            INSTANCE.btn10 = new JButton(SELECTAREA_TUTUROR_PROGRAMARILOR_UNUI_CLIENT + ".Selectarea tuturor programarilor unui client\n");
            INSTANCE.btn11 = new JButton(SELECTAREA_TUTUROR_MEDICILOR_DUPA_O_SPECIALIZARE_ORDONATI_DUPA_NUME_SI_PRENUME + ".Selectarea tuturor medicilor dupa o specializare ordonati dupa nume si prenume\n");
            INSTANCE.btn12 = new JButton(STOP + ".Stop\n");
            INSTANCE.btn1.addActionListener(INSTANCE);
            INSTANCE.btn2.addActionListener(INSTANCE);
            INSTANCE.btn3.addActionListener(INSTANCE);
            INSTANCE.btn4.addActionListener(INSTANCE);
            INSTANCE.btn5.addActionListener(INSTANCE);
            INSTANCE.btn6.addActionListener(INSTANCE);
            INSTANCE.btn7.addActionListener(INSTANCE);
            INSTANCE.btn8.addActionListener(INSTANCE);
            INSTANCE.btn9.addActionListener(INSTANCE);
            INSTANCE.btn10.addActionListener(INSTANCE);
            INSTANCE.btn11.addActionListener(INSTANCE);
            INSTANCE.btn12.addActionListener(INSTANCE);
            INSTANCE.btn1.setHorizontalAlignment(SwingConstants.LEFT);
            INSTANCE.btn2.setHorizontalAlignment(SwingConstants.LEFT);
            INSTANCE.btn3.setHorizontalAlignment(SwingConstants.LEFT);
            INSTANCE.btn4.setHorizontalAlignment(SwingConstants.LEFT);
            INSTANCE.btn5.setHorizontalAlignment(SwingConstants.LEFT);
            INSTANCE.btn6.setHorizontalAlignment(SwingConstants.LEFT);
            INSTANCE.btn7.setHorizontalAlignment(SwingConstants.LEFT);
            INSTANCE.btn8.setHorizontalAlignment(SwingConstants.LEFT);
            INSTANCE.btn9.setHorizontalAlignment(SwingConstants.LEFT);
            INSTANCE.btn10.setHorizontalAlignment(SwingConstants.LEFT);
            INSTANCE.btn11.setHorizontalAlignment(SwingConstants.LEFT);
            INSTANCE.btn12.setHorizontalAlignment(SwingConstants.LEFT);
            INSTANCE.btn1.setBounds(50, 10, 600, 20);
            INSTANCE.btn2.setBounds(50, 40, 600, 20);
            INSTANCE.btn3.setBounds(50, 70, 600, 20);
            INSTANCE.btn4.setBounds(50, 100, 600, 20);
            INSTANCE.btn5.setBounds(50, 130, 600, 20);
            INSTANCE.btn6.setBounds(50, 160, 600, 20);
            INSTANCE.btn7.setBounds(50, 190, 600, 20);
            INSTANCE.btn8.setBounds(50, 220, 600, 20);
            INSTANCE.btn9.setBounds(50, 250, 600, 20);
            INSTANCE.btn10.setBounds(50, 280, 600, 20);
            INSTANCE.btn11.setBounds(50, 310, 600, 20);
            INSTANCE.btn12.setBounds(50, 340, 600, 20);
            INSTANCE.container.add(INSTANCE.btn1);
            INSTANCE.container.add(INSTANCE.btn2);
            INSTANCE.container.add(INSTANCE.btn3);
            INSTANCE.container.add(INSTANCE.btn4);
            INSTANCE.container.add(INSTANCE.btn5);
            INSTANCE.container.add(INSTANCE.btn6);
            INSTANCE.container.add(INSTANCE.btn7);
            INSTANCE.container.add(INSTANCE.btn8);
            INSTANCE.container.add(INSTANCE.btn9);
            INSTANCE.container.add(INSTANCE.btn10);
            INSTANCE.container.add(INSTANCE.btn11);
            INSTANCE.container.add(INSTANCE.btn12);
        }
        return INSTANCE;
    }

    private GestionareCabinetMedicalJDBC() {
    }
}
