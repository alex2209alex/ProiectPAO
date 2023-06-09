package ro.fmi.pao.gestionarepersoane;

import ro.fmi.pao.framesraspuns.FrameRaspuns;
import ro.fmi.pao.gestionarecabinetmedical.GestionareCabinetMedicalInMemorie;
import ro.fmi.pao.model.Client;
import ro.fmi.pao.model.Medic;
import ro.fmi.pao.model.Persoana;
import ro.fmi.pao.model.SpecializareMedic;

import java.util.*;

public class GestionarePersoaneInMemorie implements GestionarePersoane {
    Set<Persoana> persoane;
    List<SpecializareMedic> specializari;
    private static GestionarePersoaneInMemorie INSTANCE;

    @Override
    public void adaugaSpecializare(SpecializareMedic specializareMedic) {
        if (specializareMedic != null && !specializari.contains(specializareMedic)) {
            specializari.add(specializareMedic);
            new FrameRaspuns("Specializare inserata", "Specializarea a fost inserata", GestionareCabinetMedicalInMemorie.getInstance());
        } else {
            new FrameRaspuns("Input invalid", "Specializarea nu a fost inserata", GestionareCabinetMedicalInMemorie.getInstance());
        }
    }

    @Override
    public void adaugaPersoana(Persoana persoana) {
        if (persoana != null && !persoane.contains(persoana)) {
            persoane.add(persoana);
            new FrameRaspuns("Persoana inserata", "Persoana a fost inserata", GestionareCabinetMedicalInMemorie.getInstance());
        } else {
            new FrameRaspuns("Input invalid", "Persoana nu a fost introdusa", GestionareCabinetMedicalInMemorie.getInstance());
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
        persoane = new TreeSet<>();
        specializari = new ArrayList<>();
    }
}
