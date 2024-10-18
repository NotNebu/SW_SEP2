package model.gamelogic.evaluation;

import model.gamelogic.evaluation.GameOutcome;
import model.gamelogic.evaluation.PlayerStats;

public interface IGameOutcomeEvaluator {

  // Interface for determining the winner and game outcome.

  GameOutcome evaluate(PlayerStats playerOne, PlayerStats playerTwo);
}
