package model.gamelogic.textlogic.accuracy;

import model.gamelogic.textlogic.wordchecker.WordChecker;
import model.gamelogic.textlogic.wordchecker.WordCheckerObserver;
import java.util.ArrayList;

// Calculates and manages the player's typing accuracy
public class AccuracyLogic implements WordCheckerObserver {
    private int correctCharacters;
    private int totalCharacters;
    private int rememberInt;
    private ArrayList<AccuracyObserver> observers;
    private String playerID;

    // Initializes the AccuracyLogic class, listening to a WordChecker for typing updates
    public AccuracyLogic(WordChecker wordChecker, String playerID) {
        this.playerID = playerID;
        this.correctCharacters = 0;
        this.totalCharacters = 0;
        this.rememberInt = 0;
        wordChecker.addObserver(this);
        this.observers = new ArrayList<>();
    }

    // Updates the count of correct characters and notifies observers about accuracy changes
    @Override
    public void notifyOnWordCheck(int correctCharacter, String playerID) {
        if (rememberInt < correctCharacter) {
            this.correctCharacters++;
            this.rememberInt++;
        }
        totalCharacters++;
        notifyOnAccuracyUpdate(calculateAccuracy());
    }

    // Resets tracking when a correct word is detected
    @Override
    public void notifyOnCorrectWord(String playerID) {
        rememberInt = 0;
        totalCharacters--;
    }

    // Placeholder method for when a player finishes typing the sentence
    @Override
    public void sentenceIsDone(String playerID) {
    }

    @Override
    public void notifyOnCurrentWord(String currentWord, String playerID) {

    }

    // Adds a listener for accuracy updates
    public void addListener(AccuracyObserver listener) {
        this.observers.add(listener);
    }

    // Removes a listener from the accuracy update notifications
    public void removeListener(AccuracyObserver listener) {
        this.observers.remove(listener);
    }

    // Notifies all registered observers of an updated accuracy value
    public void notifyOnAccuracyUpdate(double accuracy) {
        for (AccuracyObserver observer : observers) {
            observer.notifyOnAccuracyUpdate(accuracy, this.playerID);
        }
    }

    // Calculates the player's typing accuracy based on the correct vs. total characters typed
    public double calculateAccuracy() {
        if (totalCharacters == 0) {
            return 0;
        }
        return (double) correctCharacters / totalCharacters * 100;
    }

    // Returns the current accuracy value
    public double getAccuracy() {
        return calculateAccuracy();
    }
}
