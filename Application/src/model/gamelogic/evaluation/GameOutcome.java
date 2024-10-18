package model.gamelogic.evaluation;

public class GameOutcome {

  // Represents the result of a game, indicating if player one won or if there was a tie.

  private final boolean playerOneWon;
  private final boolean tieGame;

  public GameOutcome(boolean playerOneWon, boolean tieGame) {
    this.playerOneWon = playerOneWon;
    this.tieGame = tieGame;
  }

  public boolean isPlayerOneWon() {
    return playerOneWon;
  }

  public boolean isTieGame() {
    return tieGame;
  }
}
