package game;

import database.DAOManager;
import model.gamelogic.evaluation.EvaluatePlayer;
import model.gamelogic.evaluation.EvaluatePlayerObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

public class TestEloSystem {

    private EvaluatePlayer evaluatePlayer;
    private EvaluatePlayerObserver observer;

    @BeforeEach
    public void setUp() {

        evaluatePlayer = new EvaluatePlayer("test", "test", Mockito.mock(DAOManager.class));

        observer = Mockito.mock(EvaluatePlayerObserver.class);
        evaluatePlayer.setObserver(observer);
    }

    // Test if the elo is calculated correctly
    @Test
    public void testCalculateElo() {
        evaluatePlayer.setPlayerOneStats(100, 100);
        evaluatePlayer.setPlayerTwoStats(50, 50);
        evaluatePlayer.calculateElo();
        verify(observer).playerOneEloChange(Mockito.anyFloat());
        verify(observer).playerTwoEloChange(Mockito.anyFloat());
        verify(observer).playerOneWon();
    }

    // Test if the elo is calculated correctly
    @Test
    public void testCalculateElo2() {
        evaluatePlayer.setPlayerOneStats(50, 50);
        evaluatePlayer.setPlayerTwoStats(100, 100);
        evaluatePlayer.calculateElo();
        verify(observer).playerOneEloChange(Mockito.anyFloat());
        verify(observer).playerTwoEloChange(Mockito.anyFloat());
        verify(observer).playerTwoWon();
    }

    // Test if elo is calculated correctly with tie game
    @Test
    public void testCalculateEloWithTieGame() {
        evaluatePlayer.setPlayerOneStats(100, 100);
        evaluatePlayer.setPlayerTwoStats(100, 100);
        evaluatePlayer.calculateElo();
        verify(observer).playerOneEloChange(Mockito.anyFloat());
        verify(observer).playerTwoEloChange(Mockito.anyFloat());
        verify(observer).tieGame();
    }


    // Test if elo is calculated correctly for average WPM
    @Test
    public void testCalculateEloForAverageWPM() {
        evaluatePlayer.setPlayerOneStats(52, 90);
        evaluatePlayer.setPlayerTwoStats(48, 90);
        evaluatePlayer.calculateElo();
        verify(observer).playerOneEloChange(Mockito.anyFloat());
        verify(observer).playerTwoEloChange(Mockito.anyFloat());
        verify(observer).playerOneWon();

    }


}
