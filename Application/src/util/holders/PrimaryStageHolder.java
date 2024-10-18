package util.holders;

import javafx.stage.Stage;

// Holds a reference to the primary Stage for application-wide use.
public class PrimaryStageHolder {

    private static Stage primaryStage;

    // Sets the primary stage to be used throughout the application.
    public static void setPrimaryStage(Stage primaryStage) {
        PrimaryStageHolder.primaryStage = primaryStage;
    }

    // Returns the primary stage currently set.
    public static Stage getPrimaryStage() {
        return primaryStage;
    }
}
