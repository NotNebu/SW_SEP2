package model.gamelogic.generatetext;

import java.util.ArrayList;
import java.util.List;

// Manages a queue of text lines for the game, relying on a text loader
public class TextGenerator {
    private final List<String> textsForGame;
    private final ITextLoader textLoader;

    // Initializes the text generator with a specified text loader
    public TextGenerator(ITextLoader textLoader) {
        this.textsForGame = new ArrayList<>();
        this.textLoader = textLoader;
    }

    // Returns the current sentence at the specified index without removing it
    public String getCurrentSentence(int index) {
        if (index >= textsForGame.size()) {
            generateNextText();
        }
        return textsForGame.get(index);
    }

    // Generates and loads the next text into the queue
    public void generateNextText() {
        textLoader.loadRandomText(textsForGame);
    }
}
