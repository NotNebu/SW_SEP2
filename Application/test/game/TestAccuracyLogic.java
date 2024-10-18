package game;

import model.gamelogic.textlogic.accuracy.AccuracyLogic;
import model.gamelogic.textlogic.accuracy.AccuracyObserver;
import model.gamelogic.textlogic.wordchecker.WordChecker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestAccuracyLogic {

    private WordChecker wordChecker;
    private AccuracyLogic accuracyLogic;
    private AccuracyObserver observer;

    @BeforeEach
    public void setUp() {
        wordChecker = Mockito.mock(WordChecker.class);
        accuracyLogic = new AccuracyLogic(wordChecker, "1");
        observer = Mockito.mock(AccuracyObserver.class);
        accuracyLogic.addListener(observer);
    }

    // Test if the accuracy logic notifies the observer when a word is correct
    @Test
    public void testNotifyOnWordCheck() {
        accuracyLogic.notifyOnWordCheck(1, "1");
        Mockito.verify(observer, Mockito.times(1)).notifyOnAccuracyUpdate(Mockito.anyDouble(), Mockito.anyString());
    }

    // Test if the accuracy logic notifies the observer when a word is incorrect
    @Test
    public void testNotifyOnCorrectWord() {
        accuracyLogic.notifyOnWordCheck(0,"1");
        Mockito.verify(observer, Mockito.times(1)).notifyOnAccuracyUpdate(Mockito.anyDouble(), Mockito.anyString());
    }

    // Test if the accuracy logic correctly calculates the accuracy
    @Test
    public void testCalculateAccuracy() {
        accuracyLogic.notifyOnWordCheck(1, "1");
        accuracyLogic.notifyOnWordCheck(2, "1");
        accuracyLogic.notifyOnWordCheck(3, "1");
        accuracyLogic.notifyOnWordCheck(4, "1");
        accuracyLogic.notifyOnWordCheck(5, "1");
        accuracyLogic.notifyOnWordCheck(0, "1"); // space bar
        accuracyLogic.notifyOnCorrectWord("1");
        assertEquals(100, accuracyLogic.calculateAccuracy());

    }


}
