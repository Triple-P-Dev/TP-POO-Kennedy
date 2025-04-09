package dao;

import model.Telephone;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TelephoneDAO {
    public void ajouter(Telephone telephone) throws Exception {
        // SQL pour insérer d'abord dans la table appareils puis dans téléphones
        String sqlAppareil = "INSERT INTO appareil (num_serie, marque, modele, description, utilisateur_id) VALUES (?, ?, ?, ?, ?)";
        String sqlTelephone = "INSERT INTO telephone (appareil_id, imei) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtAppareil = conn.prepareStatement(sqlAppareil, Statement.RETURN_GENERATED_KEYS)) {
                stmtAppareil.setString(1, telephone.getNumSerie());
                stmtAppareil.setString(2, telephone.getMarque());
                stmtAppareil.setString(3, telephone.getModele());
                stmtAppareil.setString(4, telephone.getDescription());
                stmtAppareil.setInt(5, telephone.getUtilisateurId());

                stmtAppareil.executeUpdate();

                ResultSet rs = stmtAppareil.getGeneratedKeys();
                if (rs.next()) {
                    int appareilId = rs.getInt(1);
                    telephone.setId(appareilId);

                    try (PreparedStatement stmtTelephone = conn.prepareStatement(sqlTelephone)) {
                        stmtTelephone.setInt(1, appareilId);
                        stmtTelephone.setString(2, telephone.getImei());
                        stmtTelephone.executeUpdate();
                    }
                }
            }

            conn.commit();
        }
    }

    public Telephone findById(int id) throws Exception {
        String sql = "SELECT a.*, t.imei FROM appareil a " +
                "JOIN telephone t ON a.id = t.appareil_id " +
                "WHERE a.id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Telephone telephone = new Telephone();
                telephone.setId(rs.getInt("id"));
                telephone.setNumSerie(rs.getString("num_serie"));
                telephone.setMarque(rs.getString("marque"));
                telephone.setModele(rs.getString("modele"));
                telephone.setDescription(rs.getString("description"));
                telephone.setUtilisateurId(rs.getInt("utilisateur_id"));
                telephone.setImei(rs.getString("imei"));

                return telephone;
            }
        }

        return null;
    }

    public List<Telephone> findByUtilisateur(int utilisateurId) throws Exception {
        String sql = "SELECT a.*, t.imei FROM appareil a " +
                "JOIN telephone t ON a.id = t.appareil_id " +
                "WHERE a.utilisateur_id = ?";
        List<Telephone> telephones = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, utilisateurId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Telephone telephone = new Telephone();
                telephone.setId(rs.getInt("id"));
                telephone.setNumSerie(rs.getString("num_serie"));
                telephone.setMarque(rs.getString("marque"));
                telephone.setModele(rs.getString("modele"));
                telephone.setDescription(rs.getString("description"));
                telephone.setUtilisateurId(rs.getInt("utilisateur_id"));
                telephone.setImei(rs.getString("imei"));

                telephones.add(telephone);
            }
        }

        return telephones;
    }

    public void modifier(Telephone telephone) throws Exception {
        String sqlAppareil = "UPDATE appareil SET num_serie = ?, marque = ?, modele = ?, description = ? WHERE id = ?";
        String sqlTelephone = "UPDATE telephone SET imei = ? WHERE appareil_id = ?";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtAppareil = conn.prepareStatement(sqlAppareil)) {
                stmtAppareil.setString(1, telephone.getNumSerie());
                stmtAppareil.setString(2, telephone.getMarque());
                stmtAppareil.setString(3, telephone.getModele());
                stmtAppareil.setString(4, telephone.getDescription());
                stmtAppareil.setInt(5, telephone.getId());

                stmtAppareil.executeUpdate();

                try (PreparedStatement stmtTelephone = conn.prepareStatement(sqlTelephone)) {
                    stmtTelephone.setString(1, telephone.getImei());
                    stmtTelephone.setInt(2, telephone.getId());

                    stmtTelephone.executeUpdate();
                }
            }

            conn.commit();
        }
    }

    public void supprimer(int id) throws Exception {
        String sql = "DELETE FROM appareil WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
