package game;

import model.gamelogic.generatetext.LoadTextForGame;
import model.gamelogic.generatetext.TextGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestTextGenerator {

    private TextGenerator textGenerator;

    @BeforeEach
    public void setUp() {
        textGenerator = new TextGenerator(new LoadTextForGame());
    }

    // Test if the text generator generates a text
    @Test
    public void testGenerateNextText() {
        textGenerator.generateNextText();
        assertNotNull(textGenerator.getCurrentSentence(0));
    }

    // Test if we can generate multiple text
    @Test
    public void testGenerateMultipleText() {
        textGenerator.generateNextText();
        textGenerator.generateNextText();
        textGenerator.generateNextText();
        assertNotNull(textGenerator.getCurrentSentence(0));
        assertNotNull(textGenerator.getCurrentSentence(1));
        assertNotNull(textGenerator.getCurrentSentence(2));
    }

}
