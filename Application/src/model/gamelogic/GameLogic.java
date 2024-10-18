package model.gamelogic;

import database.DAO.MatchDAO;
import database.DAO.ParticipantDAO;
import database.DAOManager;
import database.object.Match;
import model.gamelogic.evaluation.EvaluatePlayer;
import model.gamelogic.evaluation.EvaluatePlayerObserver;
import model.gamelogic.generatetext.ITextLoader;
import model.gamelogic.generatetext.LoadTextForGame;
import model.gamelogic.generatetext.TextGenerator;
import model.gamelogic.textlogic.WPM.WPMCalculator;
import model.gamelogic.textlogic.WPM.WPMObserver;
import model.gamelogic.textlogic.WordsCountHolder;
import model.gamelogic.textlogic.accuracy.AccuracyLogic;
import model.gamelogic.textlogic.accuracy.AccuracyObserver;
import model.gamelogic.textlogic.wordchecker.WordChecker;
import model.gamelogic.textlogic.wordchecker.WordCheckerObserver;
import model.gamelogic.timer.*;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

// Manages the overall game logic, coordinating word-checking, scoring, timing, and player evaluation
public class GameLogic implements WPMObserver, WordCheckerObserver, CountdownObserver,
        TimerObserver, AccuracyObserver, EvaluatePlayerObserver {

    // Core game components and attributes
    private final GameTimer timer;
    private final TextGenerator textGenerator;
    private final WordChecker wordCheckerForPlayerOne;
    private final WordChecker wordCheckerForPlayerTwo;
    private final WordsCountHolder wordsCountHolderForPlayerOne;
    private final WordsCountHolder wordsCountHolderForPlayerTwo;
    private final WPMCalculator wpmCalculatorForPlayerOne;
    private final WPMCalculator wpmCalculatorForPlayerTwo;
    private final AccuracyLogic accuracyLogicForPlayerOne;
    private final AccuracyLogic accuracyLogicForPlayerTwo;
    private final List<GameLogicObserver> observers;
    private final CountdownTimer countdownTimer;
    private final EvaluatePlayer evaluatePlayer;
    private String playerOneResult;
    private String playerTwoResult;
    private float playerOneEloChange;
    private float playerTwoEloChange;
    private final String playerOneID;
    private final String playerTwoID;
    private DAOManager daoManager;

    // Initializes game logic components with player IDs
    public GameLogic(String playerOneID, String playerTwoID, DAOManager daoManager) {
        this.playerOneID = playerOneID;
        this.playerTwoID = playerTwoID;

        this.daoManager = daoManager;

        // Initialize game timer
        this.timer = new GameTimer();

        // Load game text
        ITextLoader textLoader = new LoadTextForGame();
        this.textGenerator = new TextGenerator(textLoader);

        // Initialize word holders and word checkers
        this.wordsCountHolderForPlayerOne = new WordsCountHolder();
        this.wordsCountHolderForPlayerTwo = new WordsCountHolder();
        this.wordCheckerForPlayerOne = new WordChecker(wordsCountHolderForPlayerOne, textGenerator.getCurrentSentence(0), playerOneID);
        this.wordCheckerForPlayerTwo = new WordChecker(wordsCountHolderForPlayerTwo, textGenerator.getCurrentSentence(0), playerTwoID);

        // Initialize accuracy logic for both players
        this.accuracyLogicForPlayerOne = new AccuracyLogic(wordCheckerForPlayerOne, playerOneID);
        this.accuracyLogicForPlayerTwo = new AccuracyLogic(wordCheckerForPlayerTwo, playerTwoID);

        // Initialize WPM calculators for both players
        this.wpmCalculatorForPlayerOne = new WPMCalculator(timer, wordsCountHolderForPlayerOne, playerOneID);
        this.wpmCalculatorForPlayerTwo = new WPMCalculator(timer, wordsCountHolderForPlayerTwo, playerTwoID);
        this.wpmCalculatorForPlayerOne.bind();
        this.wpmCalculatorForPlayerTwo.bind();

        // Initialize evaluation and observers
        this.evaluatePlayer = new EvaluatePlayer(playerOneID, playerTwoID, daoManager);
        this.evaluatePlayer.setObserver(this);
        this.observers = new CopyOnWriteArrayList<>();
        this.countdownTimer = new CountdownTimer();

        // Register observers to listen for updates
        this.timer.addListener(this);
        this.wordCheckerForPlayerOne.addObserver(this);
        this.wordCheckerForPlayerTwo.addObserver(this);
        this.wpmCalculatorForPlayerOne.addListener(this);
        this.wpmCalculatorForPlayerTwo.addListener(this);
        this.countdownTimer.addListener(this);
        this.accuracyLogicForPlayerOne.addListener(this);
        this.accuracyLogicForPlayerTwo.addListener(this);

        // Preload next text
        this.textGenerator.generateNextText();

    }

    // Starts the countdown timer for the game
    public void startGame() {
        this.countdownTimer.startCountdown(3);
    }

    // Begins the game after the countdown finishes
    private void startGameAfterCountdown() {
        this.timer.startTimer();
    }

    // Sets the next sentence for Player One
    public void setNextSentenceForPlayerOne(int userOneSentenceCounter) {
        this.wordCheckerForPlayerOne.setSentence(textGenerator.getCurrentSentence(userOneSentenceCounter));
    }

    // Sets the next sentence for Player Two
    public void setNextSentenceForPlayerTwo(int userTwoSentenceCounter) {
        this.wordCheckerForPlayerTwo.setSentence(textGenerator.getCurrentSentence(userTwoSentenceCounter));
    }

    // Checks a word typed by Player One
    public void checkWordForPlayerOne(String wordWritten) {
        this.wordCheckerForPlayerOne.checkWord(wordWritten);
    }

    // Checks a word typed by Player Two
    public void checkWordForPlayerTwo(String wordWritten) {
        this.wordCheckerForPlayerTwo.checkWord(wordWritten);
    }

    // Retrieves a sentence at the specified index
    public String getSentence(int index) {
        return textGenerator.getCurrentSentence(index);
    }

    // Adds a GameLogicObserver to the observer list
    public void add(GameLogicObserver observer) {
        observers.add(observer);
    }

    // Removes a GameLogicObserver from the observer list
    public void remove(GameLogicObserver observer) {
        observers.remove(observer);
    }

    // Updates the WPM for the specified player
    @Override
    public void updateWPM(double wpm, String playerID) {
        if (playerID.equals(playerOneID)) {
            observers.forEach(observer -> observer.updateWPMForPlayerOne(wpm));
        } else if (playerID.equals(playerTwoID)) {
            observers.forEach(observer -> observer.updateWPMForPlayerTwo(wpm));
        } else {
            throw new IllegalArgumentException("Invalid player ID");
        }
    }

    // Final WPM calculation when the game is finished
    @Override
    public void gameFinishedFinalWPM(double finalWPM, String playerID) {
        if (playerID.equals(playerOneID)) {
            observers.forEach(observer -> observer.gameFinishedFinalWPMForPlayerOne(finalWPM));
        } else if (playerID.equals(playerTwoID)) {
            observers.forEach(observer -> observer.gameFinishedFinalWPMForPlayerTwo(finalWPM));
        } else {
            throw new IllegalArgumentException("Invalid player ID");
        }
    }

    // Notifies observers of an accuracy update for the given player
    @Override
    public void notifyOnAccuracyUpdate(double accuracy, String playerID) {
        if (playerID.equals(playerOneID)) {
            observers.forEach(observer -> observer.updateAccuracyForPlayerOne(accuracy));
        } else if (playerID.equals(playerTwoID)) {
            observers.forEach(observer -> observer.updateAccuracyForPlayerTwo(accuracy));
        } else {
            throw new IllegalArgumentException("Invalid player ID");
        }
    }

    // Notifies observers of a word-check update for the given player
    @Override
    public void notifyOnWordCheck(int correctCharacter, String playerID) {
        if (playerID.equals(playerOneID)) {
            observers.forEach(observer -> observer.wordCheckerUpdateForPlayerOne(correctCharacter));
        } else if (playerID.equals(playerTwoID)) {
            observers.forEach(observer -> observer.wordCheckerUpdateForPlayerTwo(correctCharacter));
        } else {
            throw new IllegalArgumentException("Invalid player ID");
        }
    }

    // Notifies observers of a correctly typed word for the given player
    @Override
    public void notifyOnCorrectWord(String playerID) {
        if (playerID.equals(playerOneID)) {
            observers.forEach(GameLogicObserver::wordCheckerCorrectWordForPlayerOne);
        } else if (playerID.equals(playerTwoID)) {
            observers.forEach(GameLogicObserver::wordCheckerCorrectWordForPlayerTwo);
        } else {
            throw new IllegalArgumentException("Invalid player ID");
        }
    }

    // Notifies observers when the player has completed their current sentence
    @Override
    public void sentenceIsDone(String playerID) {
        if (playerID.equals(playerOneID)) {
            observers.forEach(GameLogicObserver::sentenceIsDoneForPlayerOne);
        } else if (playerID.equals(playerTwoID)) {
            observers.forEach(GameLogicObserver::sentenceIsDoneForPlayerTwo);
        } else {
            throw new IllegalArgumentException("Invalid player ID");
        }
    }

    @Override
    public void notifyOnCurrentWord(String currentWord, String playerID) {
        if (playerID.equals(playerOneID)) {
            observers.forEach(observer -> observer.notifyOnCurrentWordForPlayerOne(currentWord));
        } else if (playerID.equals(playerTwoID)) {
            observers.forEach(observer -> observer.notifyOnCurrentWordForPlayerTwo(currentWord));
        } else {
            throw new IllegalArgumentException("Invalid player ID");
        }
    }

    // Updates the countdown timer remaining time
    @Override
    public void countdownUpdateTime(int timeLeft) {
        observers.forEach(observer -> observer.countdownUpdate(timeLeft));
    }

    // Handles the event when the countdown timer finishes
    @Override
    public void countdownTimerFinish() {
        observers.forEach(GameLogicObserver::countdownFinished);
        startGameAfterCountdown();
    }

    // Updates the remaining time on the main timer
    @Override
    public void updateTime(int remainingSeconds) {
        observers.forEach(observer -> observer.timerUpdated(remainingSeconds));
    }

    // Handles the main timer finishing and initiates game evaluation
    @Override
    public void timerFinish() {
        observers.forEach(GameLogicObserver::timerFinished);
        evaluatePlayer.setPlayerOneStats(wpmCalculatorForPlayerOne.getWpm(), accuracyLogicForPlayerOne.getAccuracy());
        evaluatePlayer.setPlayerTwoStats(wpmCalculatorForPlayerTwo.getWpm(), accuracyLogicForPlayerTwo.getAccuracy());
        evaluatePlayer.calculateElo();
        finishGame();
    }

    // Notifies observers of Player One's ELO change
    @Override
    public void playerOneEloChange(float elo) {
        observers.forEach(observer -> observer.playerOneEloChange(elo));
        playerOneEloChange = elo;
    }

    // Notifies observers of Player Two's ELO change
    @Override
    public void playerTwoEloChange(float elo) {
        observers.forEach(observer -> observer.playerTwoEloChange(elo));
        playerTwoEloChange = elo;
    }

    // Declares that Player One won the game
    @Override
    public void playerOneWon() {
        observers.forEach(observer -> {
            observer.playerOneWon();
            observer.playerTwoLost();
        });
        playerOneResult = "win";
        playerTwoResult = "loss";
    }

    // Declares that Player Two won the game
    @Override
    public void playerTwoWon() {
        observers.forEach(observer -> {
            observer.playerTwoWon();
            observer.playerOneLost();
        });
        playerOneResult = "loss";
        playerTwoResult = "win";
    }

    // Declares that the game ended in a tie
    @Override
    public void tieGame() {
        observers.forEach(GameLogicObserver::tieGame);
        playerOneResult = "tie";
        playerTwoResult = "tie";
    }

    // Finishes the game, notifying observers that the game is done
    private void finishGame() {
        MatchDAO matchDAO = daoManager.getMatchDAO();
        ParticipantDAO participantDAO = daoManager.getParticipantDAO();
        Match match;

        if (!playerOneID.equalsIgnoreCase("test") || !playerTwoID.equalsIgnoreCase("test")) {
            try {
                match = matchDAO.create();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                participantDAO.create(Integer.parseInt(playerOneID), match.getMatch_id(), playerOneResult, playerOneEloChange,
                        (float) wpmCalculatorForPlayerOne.getWpm(), (float) accuracyLogicForPlayerOne.getAccuracy());
                participantDAO.create(Integer.parseInt(playerTwoID), match.getMatch_id(), playerTwoResult, playerTwoEloChange,
                        (float) wpmCalculatorForPlayerTwo.getWpm(), (float) accuracyLogicForPlayerTwo.getAccuracy());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        observers.forEach(GameLogicObserver::gameIsDone);
    }
}
