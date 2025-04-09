package util;

import model.Utilisateur;

public class SessionManager {
    private static Utilisateur currentUser;

    public static Utilisateur getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(Utilisateur utilisateur) {
        currentUser = utilisateur;
    }

    public static void logout() {
        currentUser = null;
    }
}
