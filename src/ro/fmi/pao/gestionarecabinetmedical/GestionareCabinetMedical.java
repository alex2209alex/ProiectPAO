package ro.fmi.pao.gestionarecabinetmedical;

import ro.fmi.pao.gestionarepersoane.GestionarePersoane;
import ro.fmi.pao.model.*;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ro.fmi.pao.utile.Constante.INPUT_INVALID;

public interface GestionareCabinetMedical {
    void startExecutareaOperatilorAplicatiei();

    default SpecializareMedic citesteSpecializareMedic(Scanner scanner) {
        System.out.print("Cod unic specializare: ");
        String codUnicSpecializare = scanner.next();
        System.out.print("Denumire: ");
        String denumire = scanner.next();
        return new SpecializareMedic(codUnicSpecializare, denumire);
    }

    default Persoana citestePersoana(Scanner scanner, GestionarePersoane gestionarePersoane) {
        System.out.print("Nume: ");
        String nume = scanner.next();
        System.out.print("Prenume: ");
        String prenume = scanner.next();
        System.out.print("1.Medic\n2.Client\n");
        int tip = scanner.nextInt();
        if (tip == 1) {
            System.out.print("Cod parafa: ");
            String codParafa = scanner.next();
            Medic medic = new Medic(nume, prenume, codParafa);
            while (true) {
                System.out.print("""
                        Selecteaza operatia
                        1.Adauga specializare la medic
                        2.Stop
                        """);
                int operatie = scanner.nextInt();
                if (operatie == 1) {
                    System.out.print("Cod specializare: ");
                    String cod = scanner.next();
                    SpecializareMedic specializareMedic = new SpecializareMedic(cod, "");
                    if (medic.areSpecializarea(specializareMedic)) {
                        System.out.println("Medicul are deja aceasta specializare");
                    } else if (!gestionarePersoane.existaSpecializare(specializareMedic)) {
                        System.out.println("Codul specializarii nu exista");
                    } else {
                        medic.adaugaSpecializare(gestionarePersoane.getSpecializare(specializareMedic));
                        System.out.println("Specializarea a fost adaugata medicului");
                    }
                } else if (operatie == 2) {
                    break;
                } else {
                    System.out.println(INPUT_INVALID);
                }
            }
            return medic;
        } else if (tip == 2) {
            System.out.print("CNP: ");
            String cnp = scanner.next();
            return new Client(nume, prenume, cnp);
        } else {
            System.out.println(INPUT_INVALID);
            return null;
        }
    }

    default Programare citesteProgramare(Scanner scanner, GestionarePersoane gestionarePersoane) {
        System.out.print("Cod programare: ");
        String codProgramare = scanner.next();
        System.out.print("CNP: ");
        String cnp = scanner.next();
        Optional<Client> client = gestionarePersoane.getClientDupaCnp(cnp);
        if (client.isEmpty()) {
            System.out.println("Niciun client nu a fost gasit cu CNP-ul " + cnp);
            return null;
        }
        System.out.print("Cod parafa: ");
        String codParafa = scanner.next();
        Optional<Medic> medic = gestionarePersoane.getMedicDupaCodParafa(codParafa);
        if (medic.isEmpty()) {
            System.out.println("Niciun medic nu a fost gasit cu codul parafei " + codParafa);
            return null;
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
        return new Programare(codProgramare, client.get(), medic.get(), ldt);
    }

    default void scrieInCSV(String actiune) {
        try {
            FileWriter fileWriter = new FileWriter("audit.csv", true);
            fileWriter.append(actiune);
            fileWriter.append(System.lineSeparator());
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
