package ro.fmi.pao.gestionarepersoane;

import ro.fmi.pao.model.Client;
import ro.fmi.pao.model.Medic;
import ro.fmi.pao.model.Persoana;
import ro.fmi.pao.model.SpecializareMedic;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static ro.fmi.pao.utile.Constante.*;

public class GestionarePersoaneInMemorie implements GestionarePersoane {
    List<Persoana> persoane;
    List<SpecializareMedic> specializari;
    private static GestionarePersoaneInMemorie INSTANCE;

    @Override
    public void adaugaSpecializare(Scanner scanner) {
        System.out.print("Cod unic specializare: ");
        String codUnicSpecializare = scanner.next();
        System.out.print("Denumire: ");
        String denumire = scanner.next();
        SpecializareMedic specializareMedic = new SpecializareMedic(codUnicSpecializare, denumire);

        if(!specializari.contains(specializareMedic)) {
            specializari.add(specializareMedic);
            System.out.println("Specializare inserata");
        }
        else {
            System.out.println("Codul nu este unic");
        }
    }

    @Override
    public void adaugaPersoana(Scanner scanner) {
        System.out.print("Nume: ");
        String nume = scanner.next();
        System.out.print("Prenume: ");
        String prenume = scanner.next();
        System.out.print("1.Medic\n2.Client\n");
        int tip = scanner.nextInt();
        if(tip == 1) {
            System.out.print("Cod parafa: ");
            String codParafa = scanner.next();
            Medic medic = new Medic(nume, prenume, codParafa);
            if(!persoane.contains(medic)) {
                persoane.add(medic);
                while(true) {
                    System.out.print("""
                            Selecteaza operatia
                            1.Adauga specializare la medic
                            2.Stop
                            """);
                    int operatie = scanner.nextInt();
                    if(operatie == 1) {
                        System.out.print("Cod specializare: ");
                        String cod = scanner.next();
                        SpecializareMedic specializareMedic = new SpecializareMedic(cod, "");
                        if(medic.areSpecializarea(specializareMedic)) {
                            System.out.println("Medicul are deja aceasta specializare");
                        }
                        else if(!specializari.contains(specializareMedic)) {
                            System.out.println("Codul specializarii nu exista");
                        }
                        else {
                            medic.adaugaSpecializare(specializari.get(specializari.indexOf(specializareMedic)));
                        }
                    }
                    else if(operatie == 2) {
                        break;
                    }
                    else {
                        System.out.println(INPUT_INVALID);
                    }
                }
                System.out.println("Medic inserat");
            }
            else {
                System.out.println("Codul parafei nu e unic");
            }
        }
        else if(tip == 2) {
            System.out.print("CNP: ");
            String cnp = scanner.next();
            Client client = new Client(nume, prenume, cnp);
            if(!persoane.contains(client)) {
                persoane.add(client);
                System.out.println("Client inserat");
            }
            else {
                System.out.println("CNP-ul nu e unic");
            }
        }
        else {
            System.out.println(INPUT_INVALID);
        }
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
        SpecializareMedic specializareMedic = new SpecializareMedic(codUnicSpecializare, "");
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

    public static GestionarePersoaneInMemorie getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new GestionarePersoaneInMemorie();
        }
        return INSTANCE;
    }

    private GestionarePersoaneInMemorie() {
        persoane = new ArrayList<>();
        specializari = new ArrayList<>();
    }
}
