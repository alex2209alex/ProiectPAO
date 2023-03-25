package ro.fmi.pao.model;

public class CabinetMedical {
    private Adresa adresa;
    private String denumire;

    public CabinetMedical(Adresa adresa, String denumire) {
        this.adresa = adresa;
        this.denumire = denumire;
    }

    public Adresa getAdresa() {
        return adresa;
    }

    public void setAdresa(Adresa adresa) {
        this.adresa = adresa;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }
}
