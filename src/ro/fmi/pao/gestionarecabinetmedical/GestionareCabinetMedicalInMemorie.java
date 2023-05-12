package ro.fmi.pao.gestionarecabinetmedical;

import ro.fmi.pao.gestionarepersoane.GestionarePersoane;
import ro.fmi.pao.gestionarepersoane.GestionarePersoaneInMemorie;
import ro.fmi.pao.gestionareprogramari.GestionareProgramari;
import ro.fmi.pao.gestionareprogramari.GestionareProgramariInMemorie;
import ro.fmi.pao.model.Client;
import ro.fmi.pao.model.Medic;
import ro.fmi.pao.model.Programare;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import java.util.Scanner;

import static ro.fmi.pao.utile.Constante.*;

public class GestionareCabinetMedicalInMemorie implements GestionareCabinetMedical {
    private static GestionareCabinetMedicalInMemorie INSTANCE;

    @Override
    public void startExecutareaOperatilorAplicatiei() {
        Scanner scanner = new Scanner(System.in);
        GestionarePersoane gestionarePersoane = GestionarePersoaneInMemorie.getInstance();
        GestionareProgramari gestionareProgramari = GestionareProgramariInMemorie.getInstance();

        System.out.println("Programare cabinet medical");

        while (true) {
            System.out.print("Selecteaza operatia\n" +
                    ADAUGA_SPECIALIZARE + ".Adauga specializare\n" +
                    ADAUGA_PERSOANA + ".Adauga persoana\n" +
                    ADAUGA_PROGRAMARE + ".Adaugare programare\n" +
                    SELECTARE_TOTI_MEDICII_ORDONATI_DUPA_NUME_SI_PRENUME + ".Selectare toti medicii ordonati dupa nume si prenume\n" +
                    SELECTARE_TOTI_CLIENTII_ORDONATI_DUPA_NUME_SI_PRENUME + ".Selectare toti clientii ordonati dupa nume si prenume\n" +
                    SELECTARE_UN_MEDIC_DUPA_COD_PARAFA + ".Selectare un medic dupa cod parafa\n" +
                    SELECTARE_UN_CLIENT_DUPA_CNP + ".Selectare un client dupa cnp\n" +
                    ANULARE_PROGRAMARE + ".Anulare programare\n" +
                    SELECTAREA_PROGRAMARILOR_UNUI_MEDIC_DINTR_O_ZI_ORDONATA_CRESCATOR_DUPA_ORA + ".Selectarea programarilor unui medic dintr-o zi ordonata crescator dupa ora\n" +
                    SELECTAREA_TUTUROR_PROGRAMARILOR_UNUI_CLIENT + ".Selectarea tuturor programarilor unui client\n" +
                    SELECTAREA_TUTUROR_MEDICILOR_DUPA_O_SPECIALIZARE_ORDONATI_DUPA_NUME_SI_PRENUME + ".Selectarea tuturor medicilor dupa o specializare ordonati dupa nume si prenume\n" +
                    STOP + ".Stop\nInput: ");
            int operatie = scanner.nextInt();
            if (operatie == ADAUGA_SPECIALIZARE) {
                gestionarePersoane.adaugaSpecializare(citesteSpecializareMedic(scanner));
                Date data = new Date();
                scrieInCSV("ADAUGA_SPECIALIZARE, " + (new Timestamp(data.getTime())));
            } else if (operatie == ADAUGA_PERSOANA) {
                gestionarePersoane.adaugaPersoana(citestePersoana(scanner, gestionarePersoane));
                Date data = new Date();
                scrieInCSV("ADAUGA_PERSOANA, " + (new Timestamp(data.getTime())));
            } else if (operatie == ADAUGA_PROGRAMARE) {
                gestionareProgramari.adaugaProgramare(citesteProgramare(scanner, gestionarePersoane));
                Date data = new Date();
                scrieInCSV("ADAUGA_PROGRAMARE, " + (new Timestamp(data.getTime())));
            } else if (operatie == SELECTARE_TOTI_MEDICII_ORDONATI_DUPA_NUME_SI_PRENUME) {
                System.out.println(gestionarePersoane.getTotiMedicii());
                Date data = new Date();
                scrieInCSV("SELECTARE_TOTI_MEDICII_ORDONATI_DUPA_NUME_SI_PRENUME, " + (new Timestamp(data.getTime())));
            } else if (operatie == SELECTARE_TOTI_CLIENTII_ORDONATI_DUPA_NUME_SI_PRENUME) {
                System.out.println(gestionarePersoane.getTotiClientii());
                Date data = new Date();
                scrieInCSV("SELECTARE_TOTI_CLIENTII_ORDONATI_DUPA_NUME_SI_PRENUME, " + (new Timestamp(data.getTime())));
            } else if (operatie == SELECTARE_UN_MEDIC_DUPA_COD_PARAFA) {
                System.out.print("Cod parafa: ");
                String codParafa = scanner.next();
                Optional<Medic> medic = gestionarePersoane.getMedicDupaCodParafa(codParafa);
                if (medic.isEmpty()) {
                    System.out.println("Niciun medic nu a fost gasit cu codul parafei " + codParafa);
                } else {
                    System.out.println(medic.get());
                }
                Date data = new Date();
                scrieInCSV("SELECTARE_UN_MEDIC_DUPA_COD_PARAFA, " + (new Timestamp(data.getTime())));
            } else if (operatie == SELECTARE_UN_CLIENT_DUPA_CNP) {
                System.out.print("CNP: ");
                String cnp = scanner.next();
                Optional<Client> client = gestionarePersoane.getClientDupaCnp(cnp);
                if (client.isEmpty()) {
                    System.out.println("Niciun client nu a fost gasit cu CNP-ul " + cnp);
                } else {
                    System.out.println(client.get());
                }
                Date data = new Date();
                scrieInCSV("SELECTARE_UN_CLIENT_DUPA_CNP, " + (new Timestamp(data.getTime())));
            } else if (operatie == SELECTAREA_TUTUROR_MEDICILOR_DUPA_O_SPECIALIZARE_ORDONATI_DUPA_NUME_SI_PRENUME) {
                System.out.print("Cod specializare: ");
                String specializare = scanner.next();
                System.out.println(gestionarePersoane.getMediciDupaSpecializare(specializare));
                Date data = new Date();
                scrieInCSV("SELECTAREA_TUTUROR_MEDICILOR_DUPA_O_SPECIALIZARE_ORDONATI_DUPA_NUME_SI_PRENUME, " + (new Timestamp(data.getTime())));
            } else if (operatie == SELECTAREA_PROGRAMARILOR_UNUI_MEDIC_DINTR_O_ZI_ORDONATA_CRESCATOR_DUPA_ORA) {
                System.out.print("Cod parafa: ");
                String codParafa = scanner.next();
                Optional<Medic> medic = GestionarePersoaneInMemorie.getInstance().getMedicDupaCodParafa(codParafa);
                if (medic.isEmpty()) {
                    System.out.println("Niciun medic nu a fost gasit cu codul parafei " + codParafa);
                    continue;
                }
                System.out.print("An: ");
                int an = scanner.nextInt();
                System.out.print("Luna: ");
                int luna = scanner.nextInt();
                System.out.print("Zi: ");
                int zi = scanner.nextInt();
                LocalDate localDate = LocalDate.of(an, luna, zi);
                System.out.println(gestionareProgramari.getProgramariPentruMedicDinZiua(medic.get(), localDate));
                Date data = new Date();
                scrieInCSV("SELECTAREA_PROGRAMARILOR_UNUI_MEDIC_DINTR_O_ZI_ORDONATA_CRESCATOR_DUPA_ORA, " + (new Timestamp(data.getTime())));
            } else if (operatie == SELECTAREA_TUTUROR_PROGRAMARILOR_UNUI_CLIENT) {
                System.out.print("CNP: ");
                String cnp = scanner.next();
                Client client = new Client("", "", cnp);
                System.out.println(gestionareProgramari.getToateProgramarileClient(client));
                Date data = new Date();
                scrieInCSV("SELECTAREA_TUTUROR_PROGRAMARILOR_UNUI_CLIENT, " + (new Timestamp(data.getTime())));
            } else if (operatie == ANULARE_PROGRAMARE) {
                System.out.print("Cod programare: ");
                String codProgramare = scanner.next();
                Programare programare = new Programare(codProgramare, null, null, null);
                gestionareProgramari.anuleazaProgramare(programare);
                Date data = new Date();
                scrieInCSV("ANULARE_PROGRAMARE, " + (new Timestamp(data.getTime())));
            } else if (operatie == STOP) {
                break;
            } else {
                System.out.println(INPUT_INVALID);
            }
        }
    }

    public static GestionareCabinetMedicalInMemorie getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GestionareCabinetMedicalInMemorie();
        }
        return INSTANCE;
    }

    private GestionareCabinetMedicalInMemorie() {
    }
}
