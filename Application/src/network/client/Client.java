package network.client;

import model.gamelogic.GameLogicObserver;

// Defines core game client behavior
public interface Client {
  // Starts the game
  void startGame();

  // Adds an observer for game logic changes
  void addObserver(GameLogicObserver observer);

  // Removes an observer for game logic changes
  void removeObserver(GameLogicObserver observer);

  // Retrieves the current sentence
  String getSentence();

  // Checks if the given word is correct
  void checkWord(String s);

  // Requests the generation of a new sentence
  void generateNewSentence();
}
