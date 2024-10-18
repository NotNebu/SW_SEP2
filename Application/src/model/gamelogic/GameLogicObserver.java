package model.gamelogic;

// Defines the observer methods to notify game logic changes
public interface GameLogicObserver {
    void updateWPMForPlayerOne(double wpm);

    void updateWPMForPlayerTwo(double wpm);

    void gameFinishedFinalWPMForPlayerOne(double wpm);

    void gameFinishedFinalWPMForPlayerTwo(double wpm);

    void wordCheckerUpdateForPlayerOne(int wordLength);

    void wordCheckerUpdateForPlayerTwo(int wordLength);

    void wordCheckerCorrectWordForPlayerOne();

    void wordCheckerCorrectWordForPlayerTwo();

    void timerUpdated(int remainingSeconds);

    void timerFinished();

    void sentenceIsDoneForPlayerOne();

    void sentenceIsDoneForPlayerTwo();

    void countdownUpdate(int remainingSeconds);

    void countdownFinished();

    void updateAccuracyForPlayerOne(double accuracy);

    void updateAccuracyForPlayerTwo(double accuracy);

    void gameStarted();

    void playerOneEloChange(float elo);

    void playerTwoEloChange(float elo);

    void playerOneWon();

    void playerTwoLost();

    void playerTwoWon();

    void playerOneLost();

    void tieGame();

    void gameIsDone();

    void notifyOnCurrentWordForPlayerTwo(String currentWord);

    void notifyOnCurrentWordForPlayerOne(String currentWord);
}
