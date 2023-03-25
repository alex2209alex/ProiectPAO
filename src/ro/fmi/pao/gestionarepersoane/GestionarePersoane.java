package ro.fmi.pao.gestionarepersoane;

import ro.fmi.pao.model.Client;
import ro.fmi.pao.model.Medic;
import ro.fmi.pao.model.Persoana;

import java.util.List;

public interface GestionarePersoane {
    void adaugaPersoana(Persoana persoana);
    List<Medic> getTotiMedicii();
    List<Client> getTotiClientii();
}
