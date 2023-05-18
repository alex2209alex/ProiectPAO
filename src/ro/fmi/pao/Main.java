package ro.fmi.pao;

import ro.fmi.pao.gestionarecabinetmedical.GestionareCabinetMedical;
import ro.fmi.pao.gestionarecabinetmedical.GestionareCabinetMedicalJDBC;
// import ro.fmi.pao.gestionarecabinetmedical.GestionareCabinetMedicalInMemorie;

public class Main {
    public static void main(String[] args) {
        // GestionareCabinetMedical gestionareCabinetMedical = GestionareCabinetMedicalInMemorie.getInstance();
        GestionareCabinetMedical gestionareCabinetMedical = GestionareCabinetMedicalJDBC.getInstance();
        gestionareCabinetMedical.faVizibil();
    }
}
