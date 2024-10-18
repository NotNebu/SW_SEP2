package game;

import database.DAOManager;
import model.gamelogic.GameLogic;
import model.gamelogic.GameLogicObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class TestGameLogic {

    private GameLogic gameLogic;
    private GameLogicObserver observer;

    @BeforeEach
    public void setUp() {
        gameLogic = new GameLogic("test", "test", mock(DAOManager.class));
        observer = Mockito.mock(GameLogicObserver.class);
        gameLogic.add(observer);
    }

    // Test if the game logic notifies the observer when a player wins
    @Test
    public void testNotifyOnWin() {
        gameLogic.playerOneWon();
        gameLogic.playerTwoWon();
        verify(observer, Mockito.times(1)).playerOneWon();
        verify(observer, Mockito.times(1)).playerTwoWon();
    }

    // Test if the game logic notifies the observer when a player loses
    @Test
    public void testNotifyOnLoss() {
        gameLogic.playerOneWon();
        gameLogic.playerTwoWon();
        verify(observer, Mockito.times(1)).playerOneLost();
        verify(observer, Mockito.times(1)).playerTwoLost();
    }

    // Test if the game logic notifies the observer when the game is a tie
    @Test
    public void testNotifyOnTie() {
        gameLogic.tieGame();
        verify(observer, Mockito.times(1)).tieGame();
    }

    // Test if the game logic notifies the observer when the game is done
    @Test
    public void testNotifyOnGameDone() {
        gameLogic.timerFinish();
        verify(observer, Mockito.times(1)).gameIsDone();
    }

    // Test if the game logic notifies the observer when the game is started
    @Test
    public void testNotifyOnGameStart() {
        gameLogic.startGame();
        verify(observer, timeout(5000).times(4)).countdownUpdate(anyInt());
    }

    // Test if the game logic notifies the observer when the timer is updated
    @Test
    public void testNotifyOnTimerUpdate() {
        gameLogic.startGame();
        verify(observer, timeout(6000).times(1)).timerUpdated(anyInt());
    }

}
