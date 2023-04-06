package ro.fmi.pao.gestionarepersoane;

import ro.fmi.pao.model.Client;
import ro.fmi.pao.model.Medic;
import ro.fmi.pao.model.Persoana;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public interface GestionarePersoane {
    void adaugaSpecializare(Scanner scanner);
    void adaugaPersoana(Scanner scanner);
    List<Medic> getTotiMedicii();
    List<Client> getTotiClientii();
    Optional<Medic> getMedicDupaCodParafa(String codParafa);
    Optional<Client> getClientDupaCnp(String cnp);
    List<Medic> getMediciDupaSpecializare(String codUnicSpecializare);
}
