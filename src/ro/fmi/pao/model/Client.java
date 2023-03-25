package ro.fmi.pao.model;

public class Client extends Persoana {
    private String cnp;

    public Client(String nume, String prenume, String cnp) {
        super(nume, prenume);
        this.cnp = cnp;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }
}
