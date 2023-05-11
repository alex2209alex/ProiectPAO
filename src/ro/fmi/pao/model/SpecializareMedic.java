package ro.fmi.pao.model;

import java.util.Objects;

public class SpecializareMedic {
    private String codUnicSpecializare;
    private String denumire;


    public SpecializareMedic(String codUnicSpecializare, String denumire) {
        this.codUnicSpecializare = codUnicSpecializare;
        this.denumire = denumire;
    }

    public String getCodUnicSpecializare() {
        return codUnicSpecializare;
    }

    public void setCodUnicSpecializare(String codUnicSpecializare) {
        this.codUnicSpecializare = codUnicSpecializare;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpecializareMedic that = (SpecializareMedic) o;
        return codUnicSpecializare.equalsIgnoreCase(that.codUnicSpecializare);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codUnicSpecializare);
    }

    @Override
    public String toString() {
        return "SpecializareMedic{" +
                "codUnicSpecializare='" + codUnicSpecializare + '\'' +
                ", denumire='" + denumire + '\'' +
                '}';
    }
}
