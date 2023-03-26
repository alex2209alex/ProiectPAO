package ro.fmi.pao.gestionareprogramari;

import ro.fmi.pao.model.Client;
import ro.fmi.pao.model.Medic;
import ro.fmi.pao.model.Programare;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GestionareProgramariInMemorie implements GestionareProgramari {
    List<Programare> programari;

    public GestionareProgramariInMemorie() {
        programari = new ArrayList<>();
    }

    @Override
    public void adaugaProgramare(Programare programare) {
        if(!existaProgramare(programare)) {
            programari.add(programare);
        }
    }

    @Override
    public void anuleazaProgramare(Programare programare) {
        while(programari.contains(programare)) {
            programari.remove(programare);
        }
    }

    @Override
    public List<Programare> getProgramariPentruMedicDinZiua(Medic medic, LocalDate ziua) {
        List<Programare> programariMedic = new ArrayList<>();
        for(Programare p : programari) {
            if(p.getMedic().equals(medic) && p.getDataOraProgramarii().toLocalDate().equals(ziua)) {
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
        for(Programare p : programari) {
            if(p.getClient().equals(client)) {
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
}
