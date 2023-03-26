package ro.fmi.pao;

import ro.fmi.pao.gestionarepersoane.GestionarePersoane;
import ro.fmi.pao.gestionarepersoane.GestionarePersoaneInMemorie;
import ro.fmi.pao.model.Client;
import ro.fmi.pao.model.Medic;
import ro.fmi.pao.model.SpecializareMedic;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Programare cabinet medical");
        GestionarePersoane gestionarePersoane = new GestionarePersoaneInMemorie();
        initializarePersoane(gestionarePersoane);
        List<Medic> medici = gestionarePersoane.getTotiMedicii();
        System.out.println(medici);
        List<Client> clienti = gestionarePersoane.getTotiClientii();
        System.out.println(clienti);
    }

    private static void initializarePersoane(GestionarePersoane gestionarePersoane) {
        SpecializareMedic specializare_1 = new SpecializareMedic("SM1234567");
        specializare_1.setDenumire("Cardiologie");
        SpecializareMedic specializare_2 = new SpecializareMedic("SM7654321");
        specializare_2.setDenumire("Neurochirurgie");
        Medic medic_1 = new Medic("Popescu", "Ion", "X1234567");
        medic_1.adaugaSpecializare(specializare_1);
        medic_1.adaugaSpecializare(specializare_2);
        gestionarePersoane.adaugaPersoana(medic_1);
        Medic medic_2 = new Medic("Vasile", "Marius", "X7654321");
        medic_2.adaugaSpecializare(specializare_2);
        gestionarePersoane.adaugaPersoana(medic_2);
        Client client_1 = new Client("Pascal", "Flavius", "1234567890123");
        gestionarePersoane.adaugaPersoana(client_1);
    }
}
