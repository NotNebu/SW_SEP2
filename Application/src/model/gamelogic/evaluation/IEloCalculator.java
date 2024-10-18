package model.gamelogic.evaluation;

import model.gamelogic.evaluation.PlayerStats;

public interface IEloCalculator {

  // Interface for calculating ELO changes.

  float calculate(PlayerStats stats, float opponentElo, boolean playerWon);
}
