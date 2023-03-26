package ro.fmi.pao;

import ro.fmi.pao.gestionarepersoane.GestionarePersoane;
import ro.fmi.pao.gestionarepersoane.GestionarePersoaneInMemorie;
import ro.fmi.pao.gestionareprogramari.GestionareProgramari;
import ro.fmi.pao.gestionareprogramari.GestionareProgramariInMemorie;
import ro.fmi.pao.model.Client;
import ro.fmi.pao.model.Medic;
import ro.fmi.pao.model.Programare;
import ro.fmi.pao.model.SpecializareMedic;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static ro.fmi.pao.utile.ConstanteInitializare.*;


public class Main {
    public static void main(String[] args) {
        System.out.println("Programare cabinet medical");
        GestionarePersoane gestionarePersoane = new GestionarePersoaneInMemorie();
        GestionareProgramari gestionareProgramari = new GestionareProgramariInMemorie();
        initializarePersoane(gestionarePersoane);
        initializareProgramari(gestionarePersoane, gestionareProgramari);
        // Afisare lista medici
        List<Medic> medici = gestionarePersoane.getTotiMedicii();
        System.out.println(medici);
        // Afisare lista clienti
        List<Client> clienti = gestionarePersoane.getTotiClientii();
        System.out.println(clienti);
        // Afisare programarile medic_1 in 30.03.2023
        System.out.println(gestionareProgramari
                .getProgramariPentruMedicDinZiua(
                        gestionarePersoane.getMedicDupaCodParafa(COD_PARAFA_MEDIC_1).get(),
                        LocalDate.of(2023, Month.MARCH, 30)
                )
        );
        // Afisare lista programari client_1
        System.out.println(gestionareProgramari.getToateProgramarileClient(
                gestionarePersoane.getClientDupaCnp(CNP_CLIENT_1).get()
        ));
    }

    private static void initializarePersoane(GestionarePersoane gestionarePersoane) {
        // Medici
        SpecializareMedic specializare_1 = new SpecializareMedic(COD_UNIC_SPECIALIZARE_1);
        specializare_1.setDenumire("Cardiologie");
        SpecializareMedic specializare_2 = new SpecializareMedic(COD_UNIC_SPECIALIZARE_2);
        specializare_2.setDenumire("Neurochirurgie");
        Medic medic_1 = new Medic("Popescu", "Ion", COD_PARAFA_MEDIC_1);
        medic_1.adaugaSpecializare(specializare_1);
        medic_1.adaugaSpecializare(specializare_2);
        gestionarePersoane.adaugaPersoana(medic_1);
        Medic medic_2 = new Medic("Vasile", "Marius", COD_PARAFA_MEDIC_2);
        medic_2.adaugaSpecializare(specializare_2);
        gestionarePersoane.adaugaPersoana(medic_2);
        Medic medic_3 = new Medic("Toma", "Ion", COD_PARAFA_MEDIC_3);
        gestionarePersoane.adaugaPersoana(medic_3);
        // Clienti
        Client client_1 = new Client("Pascal", "Flavius", CNP_CLIENT_1);
        gestionarePersoane.adaugaPersoana(client_1);
        Client client_2 = new Client("Ionescu", "Mihai", CNP_CLIENT_2);
        gestionarePersoane.adaugaPersoana(client_2);
        Client client_3 = new Client("Enescu", "George", CNP_CLIENT_3);
        gestionarePersoane.adaugaPersoana(client_3);
    }

    private static void initializareProgramari(GestionarePersoane gestionarePersoane, GestionareProgramari gestionareProgramari) {
        Programare programare_1 = new Programare(
                gestionarePersoane.getClientDupaCnp(CNP_CLIENT_1).get(),
                gestionarePersoane.getMedicDupaCodParafa(COD_PARAFA_MEDIC_1).get(),
                LocalDateTime.of(2023, Month.MARCH, 30, 10, 0, 0)
        );
        gestionareProgramari.adaugaProgramare(programare_1);
        Programare programare_2 = new Programare(
                gestionarePersoane.getClientDupaCnp(CNP_CLIENT_2).get(),
                gestionarePersoane.getMedicDupaCodParafa(COD_PARAFA_MEDIC_1).get(),
                LocalDateTime.of(2023, Month.MARCH, 30, 10, 30, 0)
        );
        gestionareProgramari.adaugaProgramare(programare_2);
        Programare programare_3 = new Programare(
                gestionarePersoane.getClientDupaCnp(CNP_CLIENT_3).get(),
                gestionarePersoane.getMedicDupaCodParafa(COD_PARAFA_MEDIC_1).get(),
                LocalDateTime.of(2023, Month.MARCH, 30, 11, 0, 0)
        );
        gestionareProgramari.adaugaProgramare(programare_3);
        Programare programare_4 = new Programare(
                gestionarePersoane.getClientDupaCnp(CNP_CLIENT_1).get(),
                gestionarePersoane.getMedicDupaCodParafa(COD_PARAFA_MEDIC_2).get(),
                LocalDateTime.of(2023, Month.APRIL, 1, 11, 0, 0)
        );
        gestionareProgramari.adaugaProgramare(programare_4);
    }
}
