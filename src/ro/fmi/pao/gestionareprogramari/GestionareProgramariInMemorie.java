package ro.fmi.pao.gestionareprogramari;

import ro.fmi.pao.gestionarepersoane.GestionarePersoaneInMemorie;
import ro.fmi.pao.model.Client;
import ro.fmi.pao.model.Medic;
import ro.fmi.pao.model.Programare;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class GestionareProgramariInMemorie implements GestionareProgramari {
    List<Programare> programari;
    private static GestionareProgramariInMemorie INSTANCE;


    @Override
    public void adaugaProgramare(Scanner scanner) {
        System.out.print("Cod programare: ");
        String codProgramare = scanner.next();
        System.out.print("CNP: ");
        String cnp = scanner.next();
        Optional<Client> client = GestionarePersoaneInMemorie.getInstance().getClientDupaCnp(cnp);
        if(client.isEmpty()) {
            System.out.println("Niciun client nu a fost gasit cu CNP-ul " + cnp);
            return;
        }
        System.out.print("Cod parafa: ");
        String codParafa = scanner.next();
        Optional<Medic> medic = GestionarePersoaneInMemorie.getInstance().getMedicDupaCodParafa(codParafa);
        if(medic.isEmpty()) {
            System.out.println("Niciun medic nu a fost gasit cu codul parafei " + codParafa);
            return;
        }
        System.out.print("An: ");
        int an = scanner.nextInt();
        System.out.print("Luna: ");
        int luna = scanner.nextInt();
        System.out.print("Zi: ");
        int zi = scanner.nextInt();
        System.out.print("Ora: ");
        int ora = scanner.nextInt();
        System.out.print("Minut: ");
        int minut = scanner.nextInt();
        LocalDateTime ldt = LocalDateTime.of(an, luna, zi, ora, minut);
        Programare programare = new Programare(codProgramare, client.get(), medic.get(), ldt);
        if(!existaProgramare(programare)) {
            programari.add(programare);
        }
        else {
            System.out.println("Codul programarii nu e unic");
        }
    }

    @Override
    public void anuleazaProgramare(Programare programare) {
        if(programari.contains(programare)) {
            programari.remove(programare);
            System.out.println("Programarea a fost stearsa");
        }
        else {
            System.out.println("Programarea nu exista");
        }
    }

    @Override
    public List<Programare> getProgramariPentruMedicDinZiua(Medic medic, LocalDate ziua) {
        List<Programare> programariMedic = new ArrayList<>();
        for(Programare p : programari) {
            if(p.getMedic().equals(medic) && p.getDataOraProgramarii().toLocalDate().equals(ziua)) {
                programariMedic.add(p);
            }
        }
        DataOraProgramariiComparator dopc = new DataOraProgramariiComparator();
        programariMedic.sort(dopc);
        return programariMedic;
    }

    @Override
    public List<Programare> getToateProgramarileClient(Client client) {
        List<Programare> programariClient = new ArrayList<>();
        for(Programare p : programari) {
            if(p.getClient().equals(client)) {
                programariClient.add(p);
            }
        }
        DataOraProgramariiComparator dopc = new DataOraProgramariiComparator();
        programariClient.sort(dopc);
        return programariClient;
    }

    private boolean existaProgramare(Programare programare) {
        return programari.contains(programare);
    }

    public static GestionareProgramariInMemorie getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new GestionareProgramariInMemorie();
        }
        return INSTANCE;
    }

    private GestionareProgramariInMemorie() {
        programari = new ArrayList<>();
    }
}
