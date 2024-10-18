package game;

import model.gamelogic.textlogic.WordsCountHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestWordHolder {

    private WordsCountHolder wordsCountHolder;

    @BeforeEach
    public void setUp() {
        wordsCountHolder = new WordsCountHolder();
    }

    // Test if words holder increments correctly
    @Test
    public void testIncrementByOne() {
        wordsCountHolder.increment();
        assertEquals(1, wordsCountHolder.getWordCount());
    }

    // Test if words holder increments correctly with multiple increments
    @Test
    public void testIncrementByMultiple() {
        wordsCountHolder.increment();
        wordsCountHolder.increment();
        wordsCountHolder.increment();
        assertEquals(3, wordsCountHolder.getWordCount());
    }

    // Test if words holder resets correctly
    @Test
    public void testReset() {
        wordsCountHolder.increment();
        wordsCountHolder.increment();
        wordsCountHolder.reset();
        assertEquals(0, wordsCountHolder.getWordCount());
    }



}
