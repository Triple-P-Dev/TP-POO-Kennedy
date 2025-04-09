package dao;


import model.Notification;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotificationDAO {
    public void ajouter(Notification notification) throws Exception {
        String sql = "INSERT INTO notification (message, date_envoi, destinataire_id, annonce_id, est_lu) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, notification.getMessage());
            stmt.setString(2, notification.getDateEnvoi());
            stmt.setInt(3, notification.getDestinataireId());
            stmt.setInt(4, notification.getAnnonceId());
            stmt.setBoolean(5, notification.isEstLu());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                notification.setId(rs.getInt(1));
            }
        }
    }

    public List<Notification> findByDestinataire(int destinataireId) throws Exception {
        String sql = "SELECT * FROM notification WHERE destinataire_id = ? ORDER BY date_envoi DESC";
        List<Notification> notifications = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, destinataireId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Notification notification = new Notification();
                notification.setId(rs.getInt("id"));
                notification.setMessage(rs.getString("message"));
                notification.setDateEnvoi(rs.getString("date_envoi"));
                notification.setDestinataireId(rs.getInt("destinataire_id"));
                notification.setAnnonceId(rs.getInt("annonce_id"));
                notification.setEstLu(rs.getBoolean("est_lu"));

                notifications.add(notification);
            }
        }

        return notifications;
    }

    public void marquerCommeLu(Notification notificationId) throws Exception {
        String sql = "UPDATE notification SET est_lu = true WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            //stmt.setInt(1, notificationId);
            stmt.executeUpdate();
        }
    }
}
