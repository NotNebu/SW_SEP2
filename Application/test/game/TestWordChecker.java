package game;

import model.gamelogic.textlogic.WordsCountHolder;
import model.gamelogic.textlogic.wordchecker.WordChecker;
import model.gamelogic.textlogic.wordchecker.WordCheckerObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

public class TestWordChecker {

    private WordChecker wordChecker;
    private WordCheckerObserver observer;
    private WordsCountHolder wordsCountHolder;

    @BeforeEach
    public void setUp() {
        observer = Mockito.mock(WordCheckerObserver.class);
        wordsCountHolder = Mockito.mock(WordsCountHolder.class);
        wordChecker = new WordChecker(wordsCountHolder, "This is a test", "1");
        wordChecker.addObserver(observer);
    }

    // Test if the word checker notifies the observer when a word is correct
    @Test
    public void testCheckWord() {
        wordChecker.checkWord("This ");
        verify(observer, times(1)).notifyOnCorrectWord("1");
    }

    // Test if the word checker notifies the observer when a word is incorrect
    @Test
    public void testSetSentence() {
        wordChecker.setSentence("New sentence");
        wordChecker.checkWord("New ");
        verify(observer, times(1)).notifyOnCorrectWord("1");
    }

    // Test add and remove observer
    @Test
    public void testAddAndRemoveObserver() {
        WordCheckerObserver newObserver = Mockito.mock(WordCheckerObserver.class);
        wordChecker.addObserver(newObserver);
        wordChecker.checkWord("This ");
        verify(newObserver, times(1)).notifyOnCorrectWord("1");

        wordChecker.removeObserver(newObserver);
        wordChecker.checkWord("is ");
        verify(newObserver, times(1)).notifyOnCorrectWord("1"); // The count should still be 1 as the observer was removed
    }

    // Test if the word checker notifies the observer when a word finished
    @Test
    public void testNotifyOnSentenceIsDone() {
        wordChecker.checkWord("This ");
        wordChecker.checkWord("is ");
        wordChecker.checkWord("a ");
        wordChecker.checkWord("test ");
        wordChecker.checkWord(" ");
        verify(observer, times(1)).sentenceIsDone("1");
    }
}
