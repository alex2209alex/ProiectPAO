package ro.fmi.pao.gestionarepersoane;

import ro.fmi.pao.model.Client;
import ro.fmi.pao.model.Medic;
import ro.fmi.pao.model.Persoana;

import java.util.ArrayList;
import java.util.List;

public class GestionarePersoaneInMemorie implements GestionarePersoane {
    List<Persoana> persoane;

    public GestionarePersoaneInMemorie() {
        persoane = new ArrayList<>();
    }

    @Override
    public void adaugaPersoana(Persoana persoana) {
        persoane.add(persoana);
    }

    @Override
    public List<Medic> getTotiMedicii() {
        List<Medic> medici = new ArrayList<>();
        for(Persoana p : persoane) {
            if(p instanceof Medic) {
                medici.add((Medic) p);
            }
        }
        return medici;
    }

    @Override
    public List<Client> getTotiClientii() {
        List<Client> clienti = new ArrayList<>();
        for(Persoana p : persoane) {
            if(p instanceof Client) {
                clienti.add((Client) p);
            }
        }
        return clienti;
    }
}
