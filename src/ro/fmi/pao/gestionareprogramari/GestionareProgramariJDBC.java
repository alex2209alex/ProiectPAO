package ro.fmi.pao.gestionareprogramari;

import ro.fmi.pao.framesraspuns.FrameRaspuns;
import ro.fmi.pao.gestionarecabinetmedical.GestionareCabinetMedicalJDBC;
import ro.fmi.pao.gestionarepersoane.GestionarePersoaneJDBC;
import ro.fmi.pao.model.Client;
import ro.fmi.pao.model.Medic;
import ro.fmi.pao.model.Programare;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GestionareProgramariJDBC implements GestionareProgramari {
    private static GestionareProgramariJDBC INSTANCE;

    @Override
    public void adaugaProgramare(Programare programare) {
        if (programare == null) {
            new FrameRaspuns("Input invalid", "Programarea nu a fost adaugata", GestionareCabinetMedicalJDBC.getInstance());
            return;
        }
        if (!existaProgramare(programare)) {
            String getIdClientSql = "SELECT id_persoana FROM client WHERE LOWER(cnp) = LOWER(?)";
            int idClient = -1;
            String getIdMedicSql = "SELECT id_persoana FROM medic WHERE LOWER(cod_parafa) = LOWER(?)";
            int idMedic = -1;
            String insertSql = "INSERT INTO programare(id_client, id_medic, cod_programare, data_programare) VALUES(?, ?, ?, ?)";
            try (Connection con = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5433/pao", "postgres", "1234")) {
                try (PreparedStatement pstmt = con.prepareStatement(getIdClientSql)) {
                    pstmt.setString(1, programare.getClient().getCnp());
                    try (ResultSet resultSet = pstmt.executeQuery()) {
                        if (resultSet.next()) {
                            idClient = resultSet.getInt(1);
                        }
                    }
                }
                try (PreparedStatement pstmt = con.prepareStatement(getIdMedicSql)) {
                    pstmt.setString(1, programare.getMedic().getCodParafa());
                    try (ResultSet resultSet = pstmt.executeQuery()) {
                        if (resultSet.next()) {
                            idMedic = resultSet.getInt(1);
                        }
                    }
                }
                if (idMedic == -1 || idClient == -1) {
                    new FrameRaspuns("Input invalid", "Programarea nu a fost adaugata", GestionareCabinetMedicalJDBC.getInstance());
                    return;
                }
                try (PreparedStatement pstmt = con.prepareStatement(insertSql)) {
                    pstmt.setInt(1, idClient);
                    pstmt.setInt(2, idMedic);
                    pstmt.setString(3, programare.getCodProgramare());
                    pstmt.setTimestamp(4, Timestamp.valueOf(programare.getDataOraProgramarii()));
                    pstmt.executeUpdate();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            new FrameRaspuns("Programare adaugata", "Programarea a fost adaugata", GestionareCabinetMedicalJDBC.getInstance());

        } else {
            new FrameRaspuns("Input invalid", "Codul programarii nu este unic", GestionareCabinetMedicalJDBC.getInstance());
        }
    }

    @Override
    public void anuleazaProgramare(Programare programare) {
        if (!existaProgramare(programare)) {
            String text = "Nu exista programare cu codul " + programare.getCodProgramare();
            new FrameRaspuns("Fara rezultat", text, GestionareCabinetMedicalJDBC.getInstance());
            return;
        }
        String selectSql = "DELETE FROM programare WHERE LOWER(cod_programare) = LOWER(?)";
        try (Connection con = DriverManager
                .getConnection("jdbc:postgresql://localhost:5433/pao", "postgres", "1234")) {
            try (PreparedStatement pstmt = con.prepareStatement(selectSql)) {
                pstmt.setString(1, programare.getCodProgramare());
                pstmt.executeUpdate();
                new FrameRaspuns("Programare stearsa", "Programarea a fost anulata", GestionareCabinetMedicalJDBC.getInstance());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Programare> getProgramariPentruMedicDinZiua(Medic medic, LocalDate ziua) {
        String selectSql = "SELECT cod_programare, data_programare, cnp FROM programare p JOIN medic m ON m.id_persoana = p.id_medic JOIN client c on c.id_persoana = p.id_client WHERE LOWER(m.cod_parafa) = LOWER(?)";
        List<Programare> programari = new ArrayList<>();
        try (Connection con = DriverManager
                .getConnection("jdbc:postgresql://localhost:5433/pao", "postgres", "1234")) {
            try (PreparedStatement pstmt = con.prepareStatement(selectSql)) {
                pstmt.setString(1, medic.getCodParafa());
                try (ResultSet resultSet = pstmt.executeQuery()) {
                    while (resultSet.next()) {
                        Optional<Client> clientOptional = GestionarePersoaneJDBC.getInstance().getClientDupaCnp(resultSet.getString("cnp"));
                        LocalDate data = resultSet.getTimestamp("data_programare").toLocalDateTime().toLocalDate();
                        if (clientOptional.isPresent() && ziua.equals(data)) {
                            programari.add(new Programare(resultSet.getString("cod_programare"), clientOptional.get(), medic, resultSet.getTimestamp("data_programare").toLocalDateTime()));
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        DataOraProgramariiComparator dopc = new DataOraProgramariiComparator();
        programari.sort(dopc);
        return programari;
    }

    @Override
    public List<Programare> getToateProgramarileClient(Client client) {
        String selectSql = "SELECT cod_programare, data_programare, cod_parafa FROM programare p JOIN medic m ON m.id_persoana = p.id_medic JOIN client c on c.id_persoana = p.id_client WHERE LOWER(c.cnp) = LOWER(?)";
        List<Programare> programari = new ArrayList<>();
        try (Connection con = DriverManager
                .getConnection("jdbc:postgresql://localhost:5433/pao", "postgres", "1234")) {
            try (PreparedStatement pstmt = con.prepareStatement(selectSql)) {
                pstmt.setString(1, client.getCnp());
                try (ResultSet resultSet = pstmt.executeQuery()) {
                    while (resultSet.next()) {
                        Optional<Client> clientOptional = GestionarePersoaneJDBC.getInstance().getClientDupaCnp(client.getCnp());
                        Optional<Medic> medicOptional = GestionarePersoaneJDBC.getInstance().getMedicDupaCodParafa(resultSet.getString("cod_parafa"));
                        if (clientOptional.isPresent() && medicOptional.isPresent()) {
                            programari.add(new Programare(resultSet.getString("cod_programare"), clientOptional.get(), medicOptional.get(), resultSet.getTimestamp("data_programare").toLocalDateTime()));
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        DataOraProgramariiComparator dopc = new DataOraProgramariiComparator();
        programari.sort(dopc);
        return programari;
    }

    public static GestionareProgramariJDBC getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GestionareProgramariJDBC();
        }
        return INSTANCE;
    }

    private GestionareProgramariJDBC() {
    }

    private boolean existaProgramare(Programare programare) {
        String selectSql = "SELECT COUNT(*) as cnt FROM programare WHERE LOWER(cod_programare) = LOWER(?)";
        try (Connection con = DriverManager
                .getConnection("jdbc:postgresql://localhost:5433/pao", "postgres", "1234")) {
            try (PreparedStatement pstmt = con.prepareStatement(selectSql)) {
                pstmt.setString(1, programare.getCodProgramare());
                try (ResultSet resultSet = pstmt.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt("cnt") > 0;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
