package session;

import model.User;

public class UserSession {

    private static User currentUser;  // Almacena el usuario logueado

    public static void startSession(User user) {
        currentUser = user;
    }

    public static void endSession() {
        currentUser = null;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static boolean isUserLoggedIn() {
        return currentUser != null;
    }
}
