package model.gamelogic.textlogic.WPM;

import model.gamelogic.textlogic.WordsCountHolder;
import model.gamelogic.timer.GameTimer;
import model.gamelogic.timer.TimerObserver;
import java.util.ArrayList;
import java.util.List;

// Calculates the words per minute (WPM) typed by the player
public class WPMCalculator implements TimerObserver {
    private int totalTimerTime;
    private double wpm;
    private GameTimer gameTimer;
    private WordsCountHolder wordsCountHolder;
    private List<WPMObserver> wpmObservers;
    private String playerID;

    // Initializes the WPMCalculator with a game timer and a word counter
    public WPMCalculator(GameTimer gameTimer, WordsCountHolder wordsCountHolder, String playerID) {
        this.playerID = playerID;
        this.wpmObservers = new ArrayList<>();
        this.totalTimerTime = gameTimer.getTotalTimerTimeInSeconds();
        this.gameTimer = gameTimer;
        this.wordsCountHolder = wordsCountHolder;
        this.wpm = 0;
    }

    // Registers the calculator to receive timer updates
    public void bind() {
        this.gameTimer.addListener(this);
    }

    // Unregisters the calculator from receiving timer updates
    private void unbind() {
        this.gameTimer.removeListener(this);
    }

    // Adds a listener to receive WPM updates
    public void addListener(WPMObserver listener) {
        this.wpmObservers.add(listener);
    }

    // Removes a listener from receiving WPM updates
    public void removeListener(WPMObserver listener) {
        this.wpmObservers.remove(listener);
    }

    // Notifies all registered observers of the current WPM
    public void broadcastWPM() {
        for (WPMObserver observer : wpmObservers) {
            observer.updateWPM(this.wpm, this.playerID);
        }
    }

    // Notifies all registered observers of the final WPM when the game ends
    public void broadcastGameFinishedWPM() {
        for (WPMObserver observer : wpmObservers) {
            observer.gameFinishedFinalWPM(this.wpm, this.playerID);
        }
    }

    // Calculates words per minute from the remaining seconds and written words
    public double calculateWPM(int remainingSeconds, int wordsWritten) {
        return (remainingSeconds != totalTimerTime) ? (wordsWritten * 60) / ((double) (totalTimerTime - remainingSeconds)) : 0;
    }

    // Called from the timer every second to calculate and notify listeners of the updated WPM
    @Override
    public void updateTime(int remainingSeconds) {
        if (this.gameTimer != null) {
            this.wpm = calculateWPM(remainingSeconds, wordsCountHolder.getWordCount());
            broadcastWPM();
        } else {
            throw new RuntimeException("GameTimer not bind to WPMCalculator");
        }
    }

    // Called when the timer ends to calculate and notify listeners of the final WPM
    @Override
    public void timerFinish() {
        this.wpm = calculateWPM(0, wordsCountHolder.getWordCount());
        broadcastGameFinishedWPM();
    }

    // Returns the current WPM value
    public double getWpm() {
        return this.wpm;
    }
}
