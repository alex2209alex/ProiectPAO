package ro.fmi.pao.model;

import java.util.Objects;

public class SpecializareMedic {
    private String denumire;
    private String codUnicSpecializare;

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public String getCodUnicSpecializare() {
        return codUnicSpecializare;
    }

    public void setCodUnicSpecializare(String codUnicSpecializare) {
        this.codUnicSpecializare = codUnicSpecializare;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpecializareMedic that = (SpecializareMedic) o;
        return codUnicSpecializare.equals(that.codUnicSpecializare);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codUnicSpecializare);
    }
}
