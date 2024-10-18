package model.gamelogic.evaluation;

public class PlayerStats {
  private double wpm;
  private double accuracy;
  private float elo;

  // Holds individual player's statistics like WPM, accuracy, and ELO.

  public PlayerStats(float initialElo) {
    this.elo = initialElo;
  }

  public void updateStats(double wpm, double accuracy) {
    this.wpm = wpm;
    this.accuracy = accuracy;
  }

  public double getWpm() {
    return wpm;
  }

  public double getAccuracy() {
    return accuracy;
  }

  public float getElo() {
    return elo;
  }

  public void updateElo(float eloChange) {
    this.elo += eloChange;
  }

  public void setElo(float elo) {
    this.elo = elo;
  }
}
