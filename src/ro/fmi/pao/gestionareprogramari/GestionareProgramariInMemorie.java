package ro.fmi.pao.gestionareprogramari;

import ro.fmi.pao.model.Client;
import ro.fmi.pao.model.Medic;
import ro.fmi.pao.model.Programare;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GestionareProgramariInMemorie implements GestionareProgramari {
    List<Programare> programari;

    public GestionareProgramariInMemorie() {
        programari = new ArrayList<>();
    }

    @Override
    public void adaugaProgramare(Programare programare) {

    }

    @Override
    public void anuleazaProgramare(Programare programare) {

    }

    @Override
    public List<Programare> getProgramariPentruMedicDinZiua(Medic medic, LocalDate ziua) {
        return null;
    }

    @Override
    public List<Programare> getToateProgramarileClient(Client client) {
        return null;
    }
}
