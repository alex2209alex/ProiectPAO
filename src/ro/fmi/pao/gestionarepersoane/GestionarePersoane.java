package ro.fmi.pao.gestionarepersoane;

import ro.fmi.pao.model.Client;
import ro.fmi.pao.model.Medic;
import ro.fmi.pao.model.Persoana;
import ro.fmi.pao.model.SpecializareMedic;

import java.util.List;
import java.util.Optional;

public interface GestionarePersoane {
    void adaugaSpecializare(SpecializareMedic specializareMedic);

    void adaugaPersoana(Persoana persoana);

    List<Medic> getTotiMedicii();

    List<Client> getTotiClientii();

    Optional<Medic> getMedicDupaCodParafa(String codParafa);

    Optional<Client> getClientDupaCnp(String cnp);

    List<Medic> getMediciDupaSpecializare(String codUnicSpecializare);

    Boolean existaSpecializare(SpecializareMedic specializareMedic);

    SpecializareMedic getSpecializare(SpecializareMedic specializareMedic);
}
