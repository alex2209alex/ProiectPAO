package ro.fmi.pao.model;

public class Adresa {
    private String localitate;
    private String strada;
    private String numar;
    private String detalii;

    public Adresa(String localitate, String strada, String numar) {
        this.localitate = localitate;
        this.strada = strada;
        this.numar = numar;
    }

    public String getLocalitate() {
        return localitate;
    }

    public void setLocalitate(String localitate) {
        this.localitate = localitate;
    }

    public String getStrada() {
        return strada;
    }

    public void setStrada(String strada) {
        this.strada = strada;
    }

    public String getNumar() {
        return numar;
    }

    public void setNumar(String numar) {
        this.numar = numar;
    }

    public String getDetalii() {
        return detalii;
    }

    public void setDetalii(String detalii) {
        this.detalii = detalii;
    }
}
