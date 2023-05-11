package ro.fmi.pao.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Medic extends Persoana {
    private List<SpecializareMedic> specializari;
    private String codParafa;

    public Medic(String nume, String prenume, String codParafa) {
        super(nume, prenume);
        specializari = new ArrayList<>();
        this.codParafa = codParafa;
    }

    public void adaugaSpecializare(SpecializareMedic specializareMedic) {
        if (!areSpecializarea(specializareMedic)) {
            specializari.add(specializareMedic);
        }
    }

    public boolean areSpecializarea(SpecializareMedic specializareMedic) {
        return specializari.contains(specializareMedic);
    }

    public List<SpecializareMedic> getSpecializari() {
        return specializari;
    }

    public String getCodParafa() {
        return codParafa;
    }

    public void setCodParafa(String codParafa) {
        this.codParafa = codParafa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Medic medic = (Medic) o;
        return codParafa.equalsIgnoreCase(medic.codParafa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codParafa);
    }

    @Override
    public String toString() {
        return "Medic{" +
                "nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                ", specializari=" + specializari +
                ", codParafa=" + codParafa +
                '}';
    }
}
