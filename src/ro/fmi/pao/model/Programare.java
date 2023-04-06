package ro.fmi.pao.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Programare {
    private String codProgramare;
    private Client client;
    private Medic medic;
    private LocalDateTime dataOraProgramarii;

    public Programare(String codProgramare, Client client, Medic medic, LocalDateTime dataOraProgramarii) {
        this.codProgramare = codProgramare;
        this.client = client;
        this.medic = medic;
        this.dataOraProgramarii = dataOraProgramarii;
    }

    public String getCodProgramare() {
        return codProgramare;
    }

    public void setCodProgramare(String codProgramare) {
        this.codProgramare = codProgramare;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Medic getMedic() {
        return medic;
    }

    public void setMedic(Medic medic) {
        this.medic = medic;
    }

    public LocalDateTime getDataOraProgramarii() {
        return dataOraProgramarii;
    }

    public void setDataOraProgramarii(LocalDateTime dataOraProgramarii) {
        this.dataOraProgramarii = dataOraProgramarii;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Programare that = (Programare) o;
        return codProgramare.equals(that.codProgramare);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codProgramare);
    }

    @Override
    public String toString() {
        return "Programare{" +
                "codProgramare='" + codProgramare + '\'' +
                ", client=" + client +
                ", medic=" + medic +
                ", dataOraProgramarii=" + dataOraProgramarii +
                '}';
    }
}
