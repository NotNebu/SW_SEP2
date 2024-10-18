package model.gamelogic.evaluation;

public class StandardGameOutcomeEvaluator implements IGameOutcomeEvaluator {

  // Implements IGameOutcomeEvaluator to evaluate game outcome based on WPM and accuracy.

  @Override
  public GameOutcome evaluate(PlayerStats playerOne, PlayerStats playerTwo) {
    boolean playerOneWon = false;
    boolean tieGame = false;

    if (playerOne.getWpm() > playerTwo.getWpm()) {
      playerOneWon = true;
    } else if (playerOne.getWpm() < playerTwo.getWpm()) {
      playerOneWon = false;
    } else {
      if (playerOne.getAccuracy() > playerTwo.getAccuracy()) {
        playerOneWon = true;
      } else if (playerOne.getAccuracy() < playerTwo.getAccuracy()) {
        playerOneWon = false;
      } else {
        tieGame = true;
      }
    }

    return new GameOutcome(playerOneWon, tieGame);
  }
}
