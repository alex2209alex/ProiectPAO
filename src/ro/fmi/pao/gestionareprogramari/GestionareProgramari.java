package ro.fmi.pao.gestionareprogramari;

import ro.fmi.pao.model.Client;
import ro.fmi.pao.model.Medic;
import ro.fmi.pao.model.Programare;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public interface GestionareProgramari {
    void adaugaProgramare(Scanner scanner);
    void anuleazaProgramare(Programare programare);
    List<Programare> getProgramariPentruMedicDinZiua(Medic medic, LocalDate ziua);
    List<Programare> getToateProgramarileClient(Client client);
}
