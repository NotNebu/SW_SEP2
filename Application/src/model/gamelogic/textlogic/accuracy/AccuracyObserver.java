package model.gamelogic.textlogic.accuracy;

// Observer interface for tracking accuracy changes
public interface AccuracyObserver {
  // Called when the player's accuracy is updated
  void notifyOnAccuracyUpdate(double accuracy, String playerID);
}
