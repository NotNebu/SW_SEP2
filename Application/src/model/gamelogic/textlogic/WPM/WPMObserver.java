package model.gamelogic.textlogic.WPM;

// Observer interface for tracking changes in words per minute
public interface WPMObserver {
    // Called when the WPM value is updated
    void updateWPM(double wpm, String playerID);

    // Called when the game ends to report the final WPM value
    void gameFinishedFinalWPM(double finalWPM, String playerID);
}
