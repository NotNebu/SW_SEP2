package model.gamelogic.evaluation;

public class StandardEloCalculator implements IEloCalculator {

  // Implements IEloCalculator to calculate ELO changes based on player stats.

  @Override
  public float calculate(PlayerStats stats, float opponentElo, boolean playerWon) {
    float wpm = (float) stats.getWpm();
    float accuracy = (float) stats.getAccuracy();
    float elo = stats.getElo();

    return (wpm * (0.4f * (800 / elo)) + accuracy * 0.15f) *
        (elo / opponentElo) * (playerWon ? 0.5f : -0.45f) + (1 >= wpm ? -40f : 0f);
  }
}
