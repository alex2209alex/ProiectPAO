package ro.fmi.pao.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Programare {
    private CabinetMedical cabinetMedical;
    private Client client;
    private Medic medic;
    private LocalDateTime dataOraProgramarii;

    public Programare(Client client, Medic medic, LocalDateTime dataOraProgramarii) {
        this.client = client;
        this.medic = medic;
        this.dataOraProgramarii = dataOraProgramarii;
    }

    public CabinetMedical getCabinetMedical() {
        return cabinetMedical;
    }

    public void setCabinetMedical(CabinetMedical cabinetMedical) {
        this.cabinetMedical = cabinetMedical;
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
        return client.equals(that.client) && medic.equals(that.medic) && dataOraProgramarii.equals(that.dataOraProgramarii);
    }

    @Override
    public int hashCode() {
        return Objects.hash(client, medic, dataOraProgramarii);
    }

    @Override
    public String toString() {
        return "Programare{" +
                "client=" + client +
                ", medic=" + medic +
                ", dataOraProgramarii=" + dataOraProgramarii +
                '}';
    }
}
