package network.client.observer;

public interface ClientGameObserver {

    void updateWPM(double wpm);

    void gameFinishedFinalWPM(double wpm);

    void wordCheckerUpdate(int wordLength);

    void wordCheckerCorrectWord();

    void timerUpdated(int remainingSeconds);

    void timerFinished();

    void sentenceIsDone();

    void countdownUpdate(int remainingSeconds);

    void countdownFinished();

    void getSentence(String sentence);

    void updateAccuracy(double accuracy);

    void updateAccuracyOpponent(double accuracy);

    void WPMUpdateOpponent(double wpm);

    void gameStarted();

    void allPlayersJoined();

    void eloChange(float eloChange);

    void gameResult(boolean aWin);

    void gameResultWithTieGame();

    void opponentProfileName(String opponentName);

    void currentWord(String currentWord);
}