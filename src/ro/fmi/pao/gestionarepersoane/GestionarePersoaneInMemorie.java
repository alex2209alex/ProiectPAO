package ro.fmi.pao.gestionarepersoane;

import ro.fmi.pao.model.Client;
import ro.fmi.pao.model.Medic;
import ro.fmi.pao.model.Persoana;
import ro.fmi.pao.model.SpecializareMedic;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        NumePrenumeComparator npc = new NumePrenumeComparator();
        medici.sort(npc);
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
        NumePrenumeComparator npc = new NumePrenumeComparator();
        clienti.sort(npc);
        return clienti;
    }

    @Override
    public Optional<Medic> getMedicDupaCodParafa(String codParafa) {
        List<Medic> medici = getTotiMedicii();
        for(Medic m : medici) {
            if(m.getCodParafa().equals(codParafa)) {
                return Optional.of(m);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Client> getClientDupaCnp(String cnp) {
        List<Client> clienti = getTotiClientii();
        for(Client c : clienti) {
            if(c.getCnp().equals(cnp)) {
                return Optional.of(c);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Medic> getMediciDupaSpecializare(String codUnicSpecializare) {
        SpecializareMedic specializareMedic = new SpecializareMedic(codUnicSpecializare);
        List<Medic> medici = new ArrayList<>();
        List<Medic> totiMedicii = getTotiMedicii();
        for(Medic m : totiMedicii) {
            if(m.areSpecializarea(specializareMedic)) {
                medici.add(m);
            }
        }
        NumePrenumeComparator npc = new NumePrenumeComparator();
        medici.sort(npc);
        return medici;
    }
}
