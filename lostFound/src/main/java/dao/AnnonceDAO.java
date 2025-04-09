package dao;

import model.Annonce;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnnonceDAO {
    public void ajouter(Annonce annonce) throws Exception {
        String sql = "INSERT INTO annonce (appareil_id, titre, description, date_creation, est_trouve) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, annonce.getIdAppareil());
            stmt.setString(2, annonce.getTitre());
            stmt.setString(3, annonce.getDescription());
            stmt.setString(4, annonce.getDateCreation());
            stmt.setBoolean(5, annonce.isEstTrouve());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                annonce.setId(rs.getInt(1));
            }
        }
    }

    public void modifier(Annonce annonce) throws Exception {
        String sql = "UPDATE annonce SET appareil_id = ?, titre = ?, description = ?, est_trouve = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, annonce.getIdAppareil());
            stmt.setString(2, annonce.getTitre());
            stmt.setString(3, annonce.getDescription());
            stmt.setBoolean(4, annonce.isEstTrouve());
            stmt.setInt(5, annonce.getId());

            stmt.executeUpdate();
        }
    }

    public void supprimer(int id) throws Exception {
        String sql = "DELETE FROM annonce WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public List<Annonce> findAll() throws Exception {
        String sql = "SELECT a.*, ap.num_serie, ap.marque, ap.modele FROM annonce a " +
                "JOIN appareil ap ON a.appareil_id = ap.id " +
                "ORDER BY a.date_creation DESC";
        List<Annonce> annonces = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Annonce annonce = new Annonce();
                annonce.setId(rs.getInt("id"));
                annonce.setIdAppareil(rs.getInt("appareil_id"));
                annonce.setTitre(rs.getString("titre"));
                annonce.setDescription(rs.getString("description"));
                annonce.setDateCreation(rs.getString("date_creation"));
                annonce.setEstTrouve(rs.getBoolean("est_trouve"));


                annonces.add(annonce);
            }
        }

        return annonces;
    }

    public List<Annonce> findByUtilisateur(int utilisateurId) throws Exception {
        String sql = "SELECT a.*, ap.num_serie, ap.marque, ap.modele FROM annonce a " +
                "JOIN appareil ap ON a.appareil_id = ap.id " +
                "WHERE ap.utilisateur_id = ? " +
                "ORDER BY a.date_creation DESC";
        List<Annonce> annonces = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, utilisateurId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Annonce annonce = new Annonce();
                annonce.setId(rs.getInt("id"));
                annonce.setIdAppareil(rs.getInt("appareil_id"));
                annonce.setTitre(rs.getString("titre"));
                annonce.setDescription(rs.getString("description"));
                annonce.setDateCreation(rs.getString("date_creation"));
                annonce.setEstTrouve(rs.getBoolean("est_trouve"));

                annonces.add(annonce);
            }
        }

        return annonces;
    }

    public List<Annonce> rechercher(String numSerie) throws Exception {
        String sql = "SELECT a.*, ap.num_serie, ap.marque, ap.modele FROM annonce a " +
                "JOIN appareil ap ON a.appareil_id = ap.id " +
                "WHERE ap.num_serie LIKE ? " +
                "ORDER BY a.date_creation DESC";
        List<Annonce> annonces = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + numSerie + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Annonce annonce = new Annonce();
                annonce.setId(rs.getInt("id"));
                annonce.setIdAppareil(rs.getInt("appareil_id"));
                annonce.setTitre(rs.getString("titre"));
                annonce.setDescription(rs.getString("description"));
                annonce.setDateCreation(rs.getString("date_creation"));
                annonce.setEstTrouve(rs.getBoolean("est_trouve"));

                annonces.add(annonce);
            }
        }

        return annonces;
    }

    public Annonce findById(int id) throws Exception {
        String sql = "SELECT a.*, ap.num_serie, ap.marque, ap.modele FROM annonce a " +
                "JOIN appareil ap ON a.appareil_id = ap.id " +
                "WHERE a.id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Annonce annonce = new Annonce();
                annonce.setId(rs.getInt("id"));
                annonce.setIdAppareil(rs.getInt("appareil_id"));
                annonce.setTitre(rs.getString("titre"));
                annonce.setDescription(rs.getString("description"));
                annonce.setDateCreation(rs.getString("date_creation"));
                annonce.setEstTrouve(rs.getBoolean("est_trouve"));

                return annonce;
            }
        }

        return null;
    }
}
