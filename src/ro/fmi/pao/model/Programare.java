package ro.fmi.pao.model;

import java.time.LocalDateTime;

public class Programare {
    private CabinetMedical cabinetMedical;
    private Client client;
    private Medic medic;
    private LocalDateTime dataOraProgramarii;

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
}
