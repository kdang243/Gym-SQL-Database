package src.gym.delegates;

/**
 * This interface uses the delegation design pattern where instead of having
 * the LoginWindow class try to do everything, it will only focus on
 * handling the UI. The actual logic/operation will be delegated to the controller
 * class (in this case Gym).
 * 
 * LoginWindow calls the methods that we have listed below but
 * Gym is the actual class that will implement the methods.
 *
 * Template taken from https://github.students.cs.ubc.ca/CPSC304/CPSC304_Java_Project
 */
public interface LoginWindowDelegate {
    void login(String username, String password);
}
