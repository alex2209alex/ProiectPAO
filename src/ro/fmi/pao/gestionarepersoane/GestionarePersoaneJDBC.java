package ro.fmi.pao.gestionarepersoane;

import ro.fmi.pao.framesraspuns.FrameRaspuns;
import ro.fmi.pao.gestionarecabinetmedical.GestionareCabinetMedicalJDBC;
import ro.fmi.pao.model.Client;
import ro.fmi.pao.model.Medic;
import ro.fmi.pao.model.Persoana;
import ro.fmi.pao.model.SpecializareMedic;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GestionarePersoaneJDBC implements GestionarePersoane {
    private static GestionarePersoaneJDBC INSTANCE;

    @Override
    public void adaugaSpecializare(SpecializareMedic specializareMedic) {
        try {
            if (existaCodSpecializare(specializareMedic.getCodUnicSpecializare())) {
                new FrameRaspuns("Input invalid", "Codul nu este unic", GestionareCabinetMedicalJDBC.getInstance());
            } else {
                String insertSql = "INSERT INTO specializare(cod_specializare, denumire) VALUES(?, ?)";
                try (Connection con = DriverManager
                        .getConnection("jdbc:postgresql://localhost:5433/pao", "postgres", "1234")) {
                    try (PreparedStatement pstmt = con.prepareStatement(insertSql)) {
                        pstmt.setString(1, specializareMedic.getCodUnicSpecializare());
                        pstmt.setString(2, specializareMedic.getDenumire());
                        pstmt.executeUpdate();
                    }
                }
                new FrameRaspuns("Specializare inserata", "Specializare inserata", GestionareCabinetMedicalJDBC.getInstance());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public void adaugaPersoana(Persoana persoana) {
        if (persoana == null) {
            new FrameRaspuns("Input invalid", "Persoana nu a fost introdusa", GestionareCabinetMedicalJDBC.getInstance());
            return;
        }
        if (persoana instanceof Client client) {
            try {
                if (existaCnp(client.getCnp())) {
                    new FrameRaspuns("Input invalid", "CNP-ul nu este unic", GestionareCabinetMedicalJDBC.getInstance());
                } else {
                    String insertPersoanaSql = "INSERT INTO persoana(nume, prenume) VALUES(?, ?)";
                    String insertClientSql = "INSERT INTO client(id_persoana, cnp) VALUES(?, ?)";
                    try (Connection con = DriverManager
                            .getConnection("jdbc:postgresql://localhost:5433/pao", "postgres", "1234")) {
                        int id;
                        try (PreparedStatement pstmt = con.prepareStatement(insertPersoanaSql, Statement.RETURN_GENERATED_KEYS)) {
                            pstmt.setString(1, client.getNume());
                            pstmt.setString(2, client.getPrenume());
                            pstmt.executeUpdate();
                            ResultSet generatedKeys = pstmt.getGeneratedKeys();
                            generatedKeys.next();
                            id = generatedKeys.getInt(1);
                        }
                        try (PreparedStatement pstmt = con.prepareStatement(insertClientSql)) {
                            pstmt.setInt(1, id);
                            pstmt.setString(2, client.getCnp());
                            pstmt.executeUpdate();
                        }
                    }
                    new FrameRaspuns("Client inserat", "Client inserat", GestionareCabinetMedicalJDBC.getInstance());
                }
            } catch (SQLException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        } else {
            Medic medic = (Medic) persoana;
            try {
                if (existaCodParafa(medic.getCodParafa())) {
                    new FrameRaspuns("Input invalid", "Codul parafei nu este unic", GestionareCabinetMedicalJDBC.getInstance());
                } else {
                    String insertPersoanaSql = "INSERT INTO persoana(nume, prenume) VALUES(?, ?)";
                    String insertMedicSql = "INSERT INTO medic(id_persoana, cod_parafa) VALUES(?, ?)";
                    String insertMedicSpecializareSql = "INSERT INTO medic_specializare(id_specializare, id_medic) VALUES(?, ?)";
                    try (Connection con = DriverManager
                            .getConnection("jdbc:postgresql://localhost:5433/pao", "postgres", "1234")) {
                        int id;
                        try (PreparedStatement pstmt = con.prepareStatement(insertPersoanaSql, Statement.RETURN_GENERATED_KEYS)) {
                            pstmt.setString(1, medic.getNume());
                            pstmt.setString(2, medic.getPrenume());
                            pstmt.executeUpdate();
                            ResultSet generatedKeys = pstmt.getGeneratedKeys();
                            generatedKeys.next();
                            id = generatedKeys.getInt(1);
                        }
                        try (PreparedStatement pstmt = con.prepareStatement(insertMedicSql)) {
                            pstmt.setInt(1, id);
                            pstmt.setString(2, medic.getCodParafa());
                            pstmt.executeUpdate();
                        }
                        for (SpecializareMedic specializare : medic.getSpecializari()) {
                            try (PreparedStatement pstmt = con.prepareStatement(insertMedicSpecializareSql)) {
                                int idSpecializare = getIdSpecializare(specializare);
                                if (id == -1) {
                                    new FrameRaspuns("Input invalid", "O specializare nu a fost adaugata medicului", GestionareCabinetMedicalJDBC.getInstance());
                                    return;
                                }
                                pstmt.setInt(1, idSpecializare);
                                pstmt.setInt(2, id);
                                pstmt.executeUpdate();
                            }
                        }
                    }
                    new FrameRaspuns("Medic inserat", "Medic inserat", GestionareCabinetMedicalJDBC.getInstance());
                }
            } catch (SQLException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }
    }

    @Override
    public List<Medic> getTotiMedicii() {
        String selectCoduriParafaSql = "SELECT cod_parafa FROM medic";
        List<Medic> medici = new ArrayList<>();
        try (Connection con = DriverManager
                .getConnection("jdbc:postgresql://localhost:5433/pao", "postgres", "1234")) {
            try (PreparedStatement pstmt = con.prepareStatement(selectCoduriParafaSql)) {
                try (ResultSet resultSet = pstmt.executeQuery()) {
                    while (resultSet.next()) {
                        Optional<Medic> medic = getMedicDupaCodParafa(resultSet.getString("cod_parafa"));
                        medic.ifPresent(medici::add);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        NumePrenumeComparator npc = new NumePrenumeComparator();
        medici.sort(npc);
        return medici;
    }

    @Override
    public List<Client> getTotiClientii() {
        String selectSql = "SELECT nume, prenume, cnp FROM client JOIN persoana p on p.id_persoana = client.id_persoana";
        List<Client> clienti = new ArrayList<>();
        try (Connection con = DriverManager
                .getConnection("jdbc:postgresql://localhost:5433/pao", "postgres", "1234")) {
            try (PreparedStatement pstmt = con.prepareStatement(selectSql)) {
                try (ResultSet resultSet = pstmt.executeQuery()) {
                    while (resultSet.next()) {
                        clienti.add(new Client(resultSet.getString("nume"), resultSet.getString("prenume"), resultSet.getString("cnp")));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        NumePrenumeComparator npc = new NumePrenumeComparator();
        clienti.sort(npc);
        return clienti;
    }

    @Override
    public Optional<Medic> getMedicDupaCodParafa(String codParafa) {
        if (!existaCodParafa(codParafa)) {
            return Optional.empty();
        }
        String selectNumePrenumeSql = "SELECT nume, prenume FROM persoana JOIN medic m on persoana.id_persoana = m.id_persoana WHERE LOWER(m.cod_parafa) = LOWER(?)";
        String nume = "";
        String prenume = "";
        try (Connection con = DriverManager
                .getConnection("jdbc:postgresql://localhost:5433/pao", "postgres", "1234")) {
            try (PreparedStatement pstmt = con.prepareStatement(selectNumePrenumeSql)) {
                pstmt.setString(1, codParafa);
                try (ResultSet resultSet = pstmt.executeQuery()) {
                    if (resultSet.next()) {
                        nume = resultSet.getString("nume");
                        prenume = resultSet.getString("prenume");
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (nume.equals("") || prenume.equals("")) {
            return Optional.empty();
        }
        Medic medic = new Medic(nume, prenume, codParafa);
        String selectSpecializariSql = "SELECT s.cod_specializare, s.denumire FROM specializare s JOIN medic_specializare ms on s.id_specializare = ms.id_specializare JOIN medic m on m.id_persoana = ms.id_medic WHERE LOWER(m.cod_parafa) = LOWER(?)";
        try (Connection con = DriverManager
                .getConnection("jdbc:postgresql://localhost:5433/pao", "postgres", "1234")) {
            try (PreparedStatement pstmt = con.prepareStatement(selectSpecializariSql)) {
                pstmt.setString(1, codParafa);
                try (ResultSet resultSet = pstmt.executeQuery()) {
                    while (resultSet.next()) {
                        medic.adaugaSpecializare(new SpecializareMedic(resultSet.getString("cod_specializare"), resultSet.getString("denumire")));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.of(medic);
    }

    @Override
    public Optional<Client> getClientDupaCnp(String cnp) {
        if (!existaCnp(cnp)) {
            return Optional.empty();
        }
        String selectSql = "SELECT nume, prenume FROM persoana JOIN client c on persoana.id_persoana = c.id_persoana WHERE LOWER(c.cnp) = LOWER(?)";
        String nume = "";
        String prenume = "";
        try (Connection con = DriverManager
                .getConnection("jdbc:postgresql://localhost:5433/pao", "postgres", "1234")) {
            try (PreparedStatement pstmt = con.prepareStatement(selectSql)) {
                pstmt.setString(1, cnp);
                try (ResultSet resultSet = pstmt.executeQuery()) {
                    if (resultSet.next()) {
                        nume = resultSet.getString("nume");
                        prenume = resultSet.getString("prenume");
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (nume.equals("") || prenume.equals("")) {
            return Optional.empty();
        }
        return Optional.of(new Client(nume, prenume, cnp));
    }

    @Override
    public List<Medic> getMediciDupaSpecializare(String codUnicSpecializare) {
        String selectCoduriParafaSql = "SELECT DISTINCT cod_parafa FROM medic JOIN medic_specializare ms on medic.id_persoana = ms.id_medic JOIN specializare s on ms.id_specializare = s.id_specializare WHERE LOWER(s.cod_specializare) = LOWER(?)";
        List<Medic> medici = new ArrayList<>();
        try (Connection con = DriverManager
                .getConnection("jdbc:postgresql://localhost:5433/pao", "postgres", "1234")) {
            try (PreparedStatement pstmt = con.prepareStatement(selectCoduriParafaSql)) {
                pstmt.setString(1, codUnicSpecializare);
                try (ResultSet resultSet = pstmt.executeQuery()) {
                    while (resultSet.next()) {
                        Optional<Medic> medic = getMedicDupaCodParafa(resultSet.getString("cod_parafa"));
                        medic.ifPresent(medici::add);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        NumePrenumeComparator npc = new NumePrenumeComparator();
        medici.sort(npc);
        return medici;
    }

    @Override
    public Boolean existaSpecializare(SpecializareMedic specializareMedic) {
        return existaCodSpecializare(specializareMedic.getCodUnicSpecializare());
    }

    @Override
    public SpecializareMedic getSpecializare(SpecializareMedic specializareMedic) {
        String selectSql = "SELECT cod_specializare, denumire FROM specializare WHERE LOWER(cod_specializare) = LOWER(?)";
        try (Connection con = DriverManager
                .getConnection("jdbc:postgresql://localhost:5433/pao", "postgres", "1234")) {
            try (PreparedStatement pstmt = con.prepareStatement(selectSql)) {
                pstmt.setString(1, specializareMedic.getCodUnicSpecializare());
                try (ResultSet resultSet = pstmt.executeQuery()) {
                    if (resultSet.next()) {
                        specializareMedic.setDenumire(resultSet.getString("denumire"));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return specializareMedic;
    }

    public static GestionarePersoaneJDBC getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GestionarePersoaneJDBC();
        }
        return INSTANCE;
    }

    private GestionarePersoaneJDBC() {
    }

    private boolean existaCodSpecializare(String codSpecializare) {
        String selectSql = "SELECT COUNT(*) as cnt FROM specializare WHERE LOWER(cod_specializare) = LOWER(?)";
        try (Connection con = DriverManager
                .getConnection("jdbc:postgresql://localhost:5433/pao", "postgres", "1234")) {
            try (PreparedStatement pstmt = con.prepareStatement(selectSql)) {
                pstmt.setString(1, codSpecializare);
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

    private boolean existaCnp(String cnp) {
        String selectSql = "SELECT COUNT(*) as cnt FROM client WHERE LOWER(cnp) = LOWER(?)";
        try (Connection con = DriverManager
                .getConnection("jdbc:postgresql://localhost:5433/pao", "postgres", "1234")) {
            try (PreparedStatement pstmt = con.prepareStatement(selectSql)) {
                pstmt.setString(1, cnp);
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

    private boolean existaCodParafa(String codParafa) {
        String selectSql = "SELECT COUNT(*) as cnt FROM medic WHERE LOWER(cod_parafa) = LOWER(?)";
        try (Connection con = DriverManager
                .getConnection("jdbc:postgresql://localhost:5433/pao", "postgres", "1234")) {
            try (PreparedStatement pstmt = con.prepareStatement(selectSql)) {
                pstmt.setString(1, codParafa);
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

    private int getIdSpecializare(SpecializareMedic specializareMedic) {
        String selectSql = "SELECT id_specializare FROM specializare WHERE LOWER(cod_specializare) = LOWER(?)";
        try (Connection con = DriverManager
                .getConnection("jdbc:postgresql://localhost:5433/pao", "postgres", "1234")) {
            try (PreparedStatement pstmt = con.prepareStatement(selectSql)) {
                pstmt.setString(1, specializareMedic.getCodUnicSpecializare());
                try (ResultSet resultSet = pstmt.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt("id_specializare");
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }
}
