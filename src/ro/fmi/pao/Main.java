package ro.fmi.pao;

import ro.fmi.pao.gestionarepersoane.GestionarePersoane;
import ro.fmi.pao.gestionarepersoane.GestionarePersoaneInMemorie;
import ro.fmi.pao.model.Client;
import ro.fmi.pao.model.Medic;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Programare cabinet medical");
        GestionarePersoane gestionarePersoane = new GestionarePersoaneInMemorie();
        initializarePersoane(gestionarePersoane);
        List<Medic> medici = gestionarePersoane.getTotiMedicii();
        System.out.println(medici);
    }

    private static void initializarePersoane(GestionarePersoane gestionarePersoane) {
        Medic medic_1 = new Medic("Popescu", "Ion");
        gestionarePersoane.adaugaPersoana(medic_1);
        Medic medic_2 = new Medic("Vasile", "Marius");
        gestionarePersoane.adaugaPersoana(medic_2);
        Client client_1 = new Client("Pascal", "Flavius", "1234567890123");
        gestionarePersoane.adaugaPersoana(client_1);
    }
}
