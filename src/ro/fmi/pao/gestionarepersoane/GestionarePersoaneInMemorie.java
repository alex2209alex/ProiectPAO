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
    List<SpecializareMedic> specializari;
    private static GestionarePersoaneInMemorie INSTANCE;

    @Override
    public void adaugaSpecializare(SpecializareMedic specializareMedic) {
        if (!specializari.contains(specializareMedic)) {
            specializari.add(specializareMedic);
            System.out.println("Specializare inserata");
        } else {
            System.out.println("Codul nu este unic");
        }
    }

    @Override
    public void adaugaPersoana(Persoana persoana) {
        if (persoana != null && !persoane.contains(persoana)) {
            persoane.add(persoana);
            System.out.println("Persoana inserata");
        } else {
            System.out.println("Persoana nu a fost inserata");
        }
    }

    @Override
    public List<Medic> getTotiMedicii() {
        List<Medic> medici = new ArrayList<>();
        for (Persoana p : persoane) {
            if (p instanceof Medic) {
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
        for (Persoana p : persoane) {
            if (p instanceof Client) {
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
        for (Medic m : medici) {
            if (m.getCodParafa().equalsIgnoreCase(codParafa)) {
                return Optional.of(m);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Client> getClientDupaCnp(String cnp) {
        List<Client> clienti = getTotiClientii();
        for (Client c : clienti) {
            if (c.getCnp().equalsIgnoreCase(cnp)) {
                return Optional.of(c);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Medic> getMediciDupaSpecializare(String codUnicSpecializare) {
        SpecializareMedic specializareMedic = new SpecializareMedic(codUnicSpecializare, "");
        List<Medic> medici = new ArrayList<>();
        List<Medic> totiMedicii = getTotiMedicii();
        for (Medic m : totiMedicii) {
            if (m.areSpecializarea(specializareMedic)) {
                medici.add(m);
            }
        }
        NumePrenumeComparator npc = new NumePrenumeComparator();
        medici.sort(npc);
        return medici;
    }

    @Override
    public Boolean existaSpecializare(SpecializareMedic specializareMedic) {
        return specializari.contains(specializareMedic);
    }

    @Override
    public SpecializareMedic getSpecializare(SpecializareMedic specializareMedic) {
        return specializari.get(specializari.indexOf(specializareMedic));
    }

    public static GestionarePersoaneInMemorie getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GestionarePersoaneInMemorie();
        }
        return INSTANCE;
    }

    private GestionarePersoaneInMemorie() {
        persoane = new ArrayList<>();
        specializari = new ArrayList<>();
    }
}
