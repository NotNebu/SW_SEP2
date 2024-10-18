package model.gamelogic.textlogic.wordchecker;

// Observer interface for tracking word verification results
public interface WordCheckerObserver {
  // Notifies observers of the count of correctly typed characters in a word
  void notifyOnWordCheck(int correctCharacter, String playerID);

  // Notifies observers when a word is typed correctly
  void notifyOnCorrectWord(String playerID);

  // Notifies observers when the entire sentence is typed correctly
  void sentenceIsDone(String playerID);

    // Notifies observers of the current word
    void notifyOnCurrentWord(String currentWord, String playerID);
}
