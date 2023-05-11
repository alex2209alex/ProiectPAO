package ro.fmi.pao.gestionareprogramari;

import ro.fmi.pao.model.Client;
import ro.fmi.pao.model.Medic;
import ro.fmi.pao.model.Programare;

import java.time.LocalDate;
import java.util.List;

public interface GestionareProgramari {
    void adaugaProgramare(Programare programare);

    void anuleazaProgramare(Programare programare);

    List<Programare> getProgramariPentruMedicDinZiua(Medic medic, LocalDate ziua);

    List<Programare> getToateProgramarileClient(Client client);
}
