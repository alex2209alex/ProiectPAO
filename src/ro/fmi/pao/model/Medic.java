package ro.fmi.pao.model;

import java.util.ArrayList;
import java.util.List;

public class Medic extends Persoana {
    private List<SpecializareMedic> specializari;

    public Medic(String nume, String prenume) {
        super(nume, prenume);
        specializari = new ArrayList<>();
    }

    public void adaugaSpecializare(SpecializareMedic specializareMedic) {
        if(!areSpecializarea(specializareMedic)) {
            specializari.add(specializareMedic);
        }
    }

    public boolean areSpecializarea(SpecializareMedic specializareMedic) {
        return specializari.contains(specializareMedic);
    }

    public List<SpecializareMedic> getSpecializari() {
        return specializari;
    }

    @Override
    public String toString() {
        return "Medic{" +
                "nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                "specializari=" + specializari +
                '}';
    }
}
