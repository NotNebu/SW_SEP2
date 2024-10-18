package viewmodel;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.GameModel;
import util.mvvm.ViewModel;
import network.client.observer.ClientGameObserver;
import view.util.GameViewObserver;

// Manages the game view's state and communicates with the GameModel.
public class GameViewModel implements ViewModel<GameModel>, ClientGameObserver {

    private GameModel model;
    private GameViewObserver observer;

    // Properties to bind with the UI
    private final StringProperty accuracyProperty = new SimpleStringProperty("0");
    private final StringProperty countDownProperty = new SimpleStringProperty("3");
    private final StringProperty sentenceProperty = new SimpleStringProperty("");
    private final StringProperty accuracyOpponentProperty = new SimpleStringProperty("0");
    private final StringProperty wpmOpponentProperty = new SimpleStringProperty("0.0");
    private final StringProperty gameResultProperty = new SimpleStringProperty("");
    private final StringProperty currentEloResultProperty = new SimpleStringProperty("");
    private final StringProperty eloChangeResultProperty = new SimpleStringProperty("");
    private final StringProperty opponentNameProperty = new SimpleStringProperty("");
    private final StringProperty typedWord = new SimpleStringProperty("");
    private final StringProperty timerProperty = new SimpleStringProperty("40");
    private final StringProperty wpmProperty = new SimpleStringProperty("0.0");
    private final StringProperty currentWordProperty = new SimpleStringProperty("");

    // Initializes GameViewModel with the provided GameModel.
    public GameViewModel(GameModel model) {
        setModel(model);
        this.model.setObserver(this);
    }

    @Override
    public void setModel(GameModel model) {
        this.model = model;
    }

    // Sets a GameViewObserver to receive game view updates.
    public void setObserver(GameViewObserver observer) {
        this.observer = observer;
    }

    // Removes the observer from the model and sets this observer to null.
    public void removeObserver() {
        model.removeObserver();
        this.observer = null;
    }

    // Updates WPM property.
    @Override
    public void updateWPM(double wpm) {
        Platform.runLater(() -> wpmProperty.set(String.format("%.1f", wpm)));
    }

    // Updates the final WPM when the game finishes.
    @Override
    public void gameFinishedFinalWPM(double wpm) {
        updateWPM(wpm);
    }

    // Called when the word checker detects a new word length.
    @Override
    public void wordCheckerUpdate(int wordLength) {
        Platform.runLater(() -> {});
    }

    // Clears the typed word after a correct word is detected.
    @Override
    public void wordCheckerCorrectWord() {
        Platform.runLater(() -> typedWord.set(""));

    }

    // Updates the timer property with the remaining seconds.
    @Override
    public void timerUpdated(int remainingSeconds) {
        Platform.runLater(() -> timerProperty.set(String.valueOf(remainingSeconds)));
    }

    // Called when the timer finishes.
    @Override
    public void timerFinished() {
        observer.onGameFinished();
    }

    // Notifies when the sentence is completed.
    @Override
    public void sentenceIsDone() {
        if (observer != null) {
            observer.onSentenceDone();

        }
    }

    // Updates the countdown timer property.
    @Override
    public void countdownUpdate(int remainingSeconds) {
        Platform.runLater(() -> countDownProperty.set(String.valueOf(remainingSeconds)));
    }

    // Notifies when the countdown finishes.
    @Override
    public void countdownFinished() {
        if (observer != null) {
            observer.onCountDownFinished();
        }
    }

    // Sets the new sentence for the game.
    @Override
    public void getSentence(String sentence) {
        Platform.runLater(() -> sentenceProperty.set(sentence));

    }

    // Updates the accuracy property.
    @Override
    public void updateAccuracy(double accuracy) {
        Platform.runLater(() -> accuracyProperty.set(String.format("%.1f", accuracy)));
    }

    // Updates the opponent's accuracy property.
    @Override
    public void updateAccuracyOpponent(double accuracy) {
        Platform.runLater(() -> accuracyOpponentProperty.set(String.format("%.1f", accuracy)));
    }

    // Updates the opponent's WPM property.
    @Override
    public void WPMUpdateOpponent(double wpm) {
        Platform.runLater(() -> wpmOpponentProperty.set(String.format("%.1f", wpm)));
    }

    // Notifies when the game starts.
    @Override
    public void gameStarted() {
            observer.gameStarted();
    }

    // Notifies when all players have joined the game.
    @Override
    public void allPlayersJoined() {
        observer.gameAboutToStartAllPlayerHasJoined();
    }

    // Updates the ELO change property.
    @Override
    public void eloChange(float eloChange) {
        Platform.runLater(() -> eloChangeResultProperty.set(String.format("%.1f", eloChange)));
    }

    // Sets the game result based on whether the player won or lost.
    @Override
    public void gameResult(boolean aWin) {
        Platform.runLater(() -> {
            if (aWin) {
                gameResultProperty.set("You won!");
            } else {
                gameResultProperty.set("You lost!");
            }
        });
    }

    // Sets the game result to "Tie game!" if the game is a tie.
    @Override
    public void gameResultWithTieGame() {
        Platform.runLater(() -> gameResultProperty.set("Tie game!"));
    }

    @Override
    public void opponentProfileName(String opponentName) {
        Platform.runLater(() -> opponentNameProperty.set(opponentName));
    }

    @Override
    public void currentWord(String currentWord) {
        Platform.runLater(() -> currentWordProperty.set(currentWord));
    }

    // Notifies the model that the typed word has changed.
    public void wordChanged() {
        model.wordChanged(typedWord.get());
    }

    // Requests a new sentence from the model.
    public void requestNewSentence() {
        model.requestNewSentence();
    }

    // Retrieves the current sentence from the model.
    public void getCurrentSentence() {
        model.getCurrentSentence();
    }

    // Leaves the game queue via the model.
    public void leaveQueue() {
        model.leaveQueue();
    }

    // Property accessors for data binding
    public StringProperty typedWordProperty() {
        return typedWord;
    }

    public StringProperty timerProperty() {
        return timerProperty;
    }

    public StringProperty wpmProperty() {
        return wpmProperty;
    }

    public StringProperty sentenceProperty() {
        return sentenceProperty;
    }

    public StringProperty accuracyProperty() {
        return accuracyProperty;
    }

    public StringProperty countDownProperty() {
        return countDownProperty;
    }

    public StringProperty accuracyOpponentProperty() {
        return accuracyOpponentProperty;
    }

    public StringProperty wpmOpponentProperty() {
        return wpmOpponentProperty;
    }

    public StringProperty gameResultProperty() {
        return gameResultProperty;
    }

    public StringProperty currentEloResultProperty() {
        return currentEloResultProperty;
    }

    public StringProperty eloChangeResultProperty() {
        return eloChangeResultProperty;
    }

    public StringProperty opponentNameProperty() {
        return opponentNameProperty;
    }
    public StringProperty currentWordProperty() {
        return currentWordProperty;
    }



}
