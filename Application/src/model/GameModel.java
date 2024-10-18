package model;

import network.client.socket.SocketClient;
import util.holders.UserDTOHolder;
import util.mvvm.Model;
import network.client.observer.ClientGameObserver;

// Manages game-related data and acts as a bridge between the UI and socket client
public class GameModel implements Model, ClientGameObserver {
    private final SocketClient gameClient;
    private ClientGameObserver observer;

    // Initializes the model and subscribes to game events
    public GameModel() {
        gameClient = SocketClient.getInstance();
        gameClient.addGameObserver(this);
    }

    // Sets an observer to notify when game events occur
    public void setObserver(ClientGameObserver observer) {
        this.observer = observer;
    }

    // Removes the observer from receiving game events
    public void removeObserver() {
        gameClient.removeGameObserver(this);
        this.observer = null;
    }

    // Relay methods implementing the ClientGameObserver interface
    @Override
    public void updateWPM(double wpm) {
        if (observer != null) {
            observer.updateWPM(wpm);
        }
    }

    @Override
    public void gameFinishedFinalWPM(double wpm) {
        if (observer != null) {
            observer.gameFinishedFinalWPM(wpm);
        }
    }

    @Override
    public void wordCheckerUpdate(int wordLength) {
        if (observer != null) {
            observer.wordCheckerUpdate(wordLength);
        }
    }

    @Override
    public void wordCheckerCorrectWord() {
        if (observer != null) {
            observer.wordCheckerCorrectWord();
        }
    }

    @Override
    public void timerUpdated(int remainingSeconds) {
        if (observer != null) {
            observer.timerUpdated(remainingSeconds);
        }
    }

    @Override
    public void timerFinished() {
        if (observer != null) {
            observer.timerFinished();
        }
    }

    @Override
    public void sentenceIsDone() {
        if (observer != null) {
            observer.sentenceIsDone();
        }
    }

    @Override
    public void countdownUpdate(int remainingSeconds) {
        if (observer != null) {
            observer.countdownUpdate(remainingSeconds);
        }
    }

    @Override
    public void countdownFinished() {
        if (observer != null) {
            observer.countdownFinished();
        }
    }

    @Override
    public void getSentence(String sentence) {
        if (observer != null) {
            observer.getSentence(sentence);
        }
    }

    @Override
    public void updateAccuracy(double accuracy) {
        if (observer != null) {
            observer.updateAccuracy(accuracy);
        }
    }

    @Override
    public void updateAccuracyOpponent(double accuracy) {
        if (observer != null) {
            observer.updateAccuracyOpponent(accuracy);
        }
    }

    @Override
    public void WPMUpdateOpponent(double wpm) {
        if (observer != null) {
            observer.WPMUpdateOpponent(wpm);
        }
    }

    @Override
    public void gameStarted() {
        if (observer != null) {
            observer.gameStarted();
        }
    }

    @Override
    public void allPlayersJoined() {
        if (observer != null) {
            observer.allPlayersJoined();
        }
    }

    @Override
    public void eloChange(float eloChange) {
        if (observer != null) {
            observer.eloChange(eloChange);
        }
    }

    @Override
    public void gameResult(boolean aWin) {
        if (observer != null) {
            observer.gameResult(aWin);
        }
    }

    @Override
    public void gameResultWithTieGame() {
        if (observer != null) {
            observer.gameResultWithTieGame();
        }
    }

    @Override
    public void opponentProfileName(String opponentName) {
        if (observer != null) {
            observer.opponentProfileName(opponentName);
        }
    }

    @Override
    public void currentWord(String currentWord) {
        if(observer != null) {
            observer.currentWord(currentWord);
        }
    }

    // Requests the current sentence from the server
    public void getCurrentSentence() {
        gameClient.getSentence(UserDTOHolder.getUserDTO());
    }

    // Sends the updated word to the server
    public void wordChanged(String word) {
        gameClient.checkWord(UserDTOHolder.getUserDTO(), word);
    }

    // Requests a new sentence and retrieves it from the server
    public void requestNewSentence() {
        gameClient.generateNewSentence(UserDTOHolder.getUserDTO());
        gameClient.getSentence(UserDTOHolder.getUserDTO());
    }

    // Removes the user from the server queue
    public void leaveQueue() {
        gameClient.leaveQueue(UserDTOHolder.getUserDTO());
    }
}
