package ro.fmi.pao.gestionarepersoane;

import ro.fmi.pao.model.Client;
import ro.fmi.pao.model.Medic;
import ro.fmi.pao.model.Persoana;

import java.util.List;
import java.util.Optional;

public interface GestionarePersoane {
    void adaugaPersoana(Persoana persoana);
    List<Medic> getTotiMedicii();
    List<Client> getTotiClientii();
    Optional<Medic> getMedicDupaCodParafa(String codParafa);
    Optional<Client> getClientDupaCnp(String cnp);
    List<Medic> getMediciDupaSpecializare(String codUnicSpecializare);
}
