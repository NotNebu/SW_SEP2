package model.gamelogic.evaluation;

public interface EvaluatePlayerObserver {

    // Observer interface for receiving notifications about game results and ELO changes.
    void playerOneEloChange(float elo);
    void playerTwoEloChange(float elo);
    void playerOneWon();
    void playerTwoWon();
    void tieGame();
}
