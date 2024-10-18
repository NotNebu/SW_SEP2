package model.gamelogic.textlogic;

// Keeps track of the number of correctly typed words by the player
public class WordsCountHolder {
    private int wordCount;

    // Initializes the word count
    public WordsCountHolder() {
        setWordCount(0);
    }

    // Increments the count of correctly typed words
    public void increment() {
        this.wordCount++;
    }

    // Decrements the count of correctly typed words (if needed)
    public void decrement() {
        this.wordCount--;
    }

    // Sets the count of correctly typed words
    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }

    // Returns the count of correctly typed words
    public int getWordCount() {
        return wordCount;
    }

    public void reset() {
        setWordCount(0);
    }
}
