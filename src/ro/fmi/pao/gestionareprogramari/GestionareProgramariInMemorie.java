package ro.fmi.pao.gestionareprogramari;

import ro.fmi.pao.framesraspuns.FrameRaspuns;
import ro.fmi.pao.gestionarecabinetmedical.GestionareCabinetMedicalInMemorie;
import ro.fmi.pao.model.Client;
import ro.fmi.pao.model.Medic;
import ro.fmi.pao.model.Programare;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GestionareProgramariInMemorie implements GestionareProgramari {
    List<Programare> programari;
    private static GestionareProgramariInMemorie INSTANCE;


    @Override
    public void adaugaProgramare(Programare programare) {
        if (programare == null) {
            new FrameRaspuns("Input invalid", "Programarea nu a fost adaugata", GestionareCabinetMedicalInMemorie.getInstance());
            return;
        }
        if (!existaProgramare(programare)) {
            programari.add(programare);
            new FrameRaspuns("Programare adaugata", "Programarea a fost adaugata", GestionareCabinetMedicalInMemorie.getInstance());

        } else {
            new FrameRaspuns("Input invalid", "Codul programarii nu este unic", GestionareCabinetMedicalInMemorie.getInstance());
        }
    }

    @Override
    public void anuleazaProgramare(Programare programare) {
        if (programari.contains(programare)) {
            programari.remove(programare);
            new FrameRaspuns("Programare stearsa", "Programarea a fost anulata", GestionareCabinetMedicalInMemorie.getInstance());
        } else {
            new FrameRaspuns("Niciun rezultat", "Programarea nu exista", GestionareCabinetMedicalInMemorie.getInstance());
        }
    }

    @Override
    public List<Programare> getProgramariPentruMedicDinZiua(Medic medic, LocalDate ziua) {
        List<Programare> programariMedic = new ArrayList<>();
        for (Programare p : programari) {
            if (p.getMedic().equals(medic) && p.getDataOraProgramarii().toLocalDate().equals(ziua)) {
                programariMedic.add(p);
            }
        }
        DataOraProgramariiComparator dopc = new DataOraProgramariiComparator();
        programariMedic.sort(dopc);
        return programariMedic;
    }

    @Override
    public List<Programare> getToateProgramarileClient(Client client) {
        List<Programare> programariClient = new ArrayList<>();
        for (Programare p : programari) {
            if (p.getClient().equals(client)) {
                programariClient.add(p);
            }
        }
        DataOraProgramariiComparator dopc = new DataOraProgramariiComparator();
        programariClient.sort(dopc);
        return programariClient;
    }

    private boolean existaProgramare(Programare programare) {
        return programari.contains(programare);
    }

    public static GestionareProgramariInMemorie getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GestionareProgramariInMemorie();
        }
        return INSTANCE;
    }

    private GestionareProgramariInMemorie() {
        programari = new ArrayList<>();
    }
}
