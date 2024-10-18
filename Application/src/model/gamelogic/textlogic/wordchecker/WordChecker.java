package model.gamelogic.textlogic.wordchecker;

import model.gamelogic.textlogic.WordsCountHolder;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

// Checks and verifies typed words against the original text
public class WordChecker {
    private WordsCountHolder wordsCountHolder;
    private String sentence;
    private Queue<String> sentenceQueue;
    private String playerID;
    private ArrayList<WordCheckerObserver> observers;

    // Initializes the WordChecker with the original text and a player's ID
    public WordChecker(WordsCountHolder wordsCountHolder, String sentence, String playerID) {
        this.playerID = playerID;
        setSentence(sentence);
        observers = new ArrayList<>();
        this.wordsCountHolder = wordsCountHolder;
    }

    // Sets a new sentence for verification and prepares the queue
    public void setSentence(String sentence) {
        this.sentence = sentence;
        setupQueue();
    }

    // Prepares the word queue for the given sentence
    public void setupQueue() {
        String[] words = sentence.split("\\s+");
        sentenceQueue = new LinkedList<>();
        for (String word : words) {
            sentenceQueue.offer(word + " ");
        }
    }

    // Verifies if the given word matches the expected word and notifies observers accordingly
    public void checkWord(String word) {
        int i = 0;
        String currentWord = "";
        if (!sentenceQueue.isEmpty()) {
            currentWord = sentenceQueue.peek();
            notifyOnCurrentWord(currentWord);
            String[] wordToCheckArray = word.split("");
            for (String currentCharacter : wordToCheckArray) {
                String currentCharacterFromQueue = currentWord.split("")[i];
                if (currentCharacterFromQueue.equals(currentCharacter)) {
                    i++;
                } else {
                    break;
                }
            }
            if (i == currentWord.length()) {
                sentenceQueue.poll();
                wordsCountHolder.increment();
                notifyWhenWordIsCorrect();
            } else {
                notifyOnWordChecked(i);
            }
        } else {
            notifyOnSentenceIsDone();
        }
    }

    // Adds an observer to the WordChecker's notifications
    public void addObserver(WordCheckerObserver observer) {
        observers.add(observer);
    }

    // Removes an observer from the WordChecker's notifications
    public void removeObserver(WordCheckerObserver observer) {
        observers.remove(observer);
    }

    public void notifyOnCurrentWord(String currentWord){
        for (WordCheckerObserver observer : observers) {
            observer.notifyOnCurrentWord(currentWord, playerID);
        }
    }


    // Notifies observers when a word is typed correctly
    public void notifyWhenWordIsCorrect() {
        for (WordCheckerObserver observer : observers) {
            observer.notifyOnCorrectWord(playerID);
        }
    }

    // Notifies observers of the count of correctly typed characters
    public void notifyOnWordChecked(int count) {
        for (WordCheckerObserver observer : observers) {
            observer.notifyOnWordCheck(count, playerID);
        }
    }

    // Notifies observers when the entire sentence is typed correctly
    public void notifyOnSentenceIsDone() {
        for (WordCheckerObserver observer : observers) {
            observer.sentenceIsDone(playerID);
        }
    }
}
