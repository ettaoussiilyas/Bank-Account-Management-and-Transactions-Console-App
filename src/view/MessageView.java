package view;

public class MessageView {

    public static void displaySuccess(String message) {
        System.out.println("SUCCESS: " + message);
    }

    public static void displayError(String message) {
        System.out.println("ERROR: " + message);
    }

    public static void displayInfo(String message) {
        System.out.println("INFO: " + message);
    }
}
