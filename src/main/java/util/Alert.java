package util;

public class Alert {
    private void showAlert(javafx.scene.control.Alert.AlertType type, String title, String header, String content) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
