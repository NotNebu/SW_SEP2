package view.util;

// Observer interface for handling game view events.
public interface GameViewObserver {

    // Notifies when a sentence is completed.
    void onSentenceDone();

    // Notifies when the game finishes.
    void onGameFinished();

    // Notifies when the countdown timer finishes.
    void onCountDownFinished();

    // Notifies that the game has started.
    void gameStarted();

    // Notifies that the game is about to start after all players have joined.
    void gameAboutToStartAllPlayerHasJoined();
}
