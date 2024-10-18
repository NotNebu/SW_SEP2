package model.gamelogic;

import database.DAO.UsersDAO;
import database.DAOManager;
import database.object.User;
import model.user.UserDTO;
import network.server.socket.SocketServer;
import network.util.message.Message;
import network.util.message.response.*;

import java.sql.SQLException;
import java.util.ArrayList;

// Coordinates game setup, player management, and broadcasting messages to players and channels
public class Game implements GameLogicObserver {
    private String uniqueGameID;
    private GameLogic gameLogic;
    private static int gameIDCounter = 0;
    private final ArrayList<UserDTO> users;
    private int userOneSentenceCounter = 0;
    private int userTwoSentenceCounter = 0;
    private String playerOneID;
    private String playerTwoID;
    private DAOManager daoManager;

    // Initializes a new game instance with a unique ID
    public Game(DAOManager daoManager) {
        this.users = new ArrayList<>(2);
        generateUniqueID();
        this.daoManager = daoManager;
        System.out.println("Game created with unique ID: " + uniqueGameID);
    }

    // Adds a user to the game
    public void addUser(UserDTO user) {
        users.add(user);
    }

    // Removes a user from the game
    public void removeUser(UserDTO user) {
        users.remove(user);
    }

    // Assigns players and starts the game
    public void assignPlayersAndStartGame() {
        playerOneID = users.get(0).getId();
        playerTwoID = users.get(1).getId();
        // from database, get the elo of the players
        startGame();
    }

    // Generates a unique ID for the game
    public void generateUniqueID() {
        uniqueGameID = String.valueOf(gameIDCounter);
        gameIDCounter++;
    }

    // Returns the list of users in the game
    public ArrayList<UserDTO> getUsers() {
        return users;
    }

    // Returns the unique ID of the game
    public String getUniqueGameID() {
        return uniqueGameID;
    }

    // Starts the game after ensuring two players are present
    public void startGame() {
        try {
            Thread.sleep(2000); // Wait for 2 seconds to let the second player load the game
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (users.size() != 2) {
            throw new IllegalArgumentException("Game must have 2 players to start");
        }
        gameLogic = new GameLogic(playerOneID, playerTwoID, daoManager);
        gameLogic.add(this);
        gameLogic.startGame();
        broadCastMessageInChannel(new AllPlayerJoinedResponseMessage());

        UsersDAO userDTO = daoManager.getUsersDAO();

        try {
            User userOne = userDTO.readById(Integer.parseInt(playerOneID));
            User userTwo = userDTO.readById(Integer.parseInt(playerTwoID));
            broadCastMessageToUser(new OpponentNameResponseMessage(userTwo.getUsername()), playerOneID);
            broadCastMessageToUser(new OpponentNameResponseMessage(userOne.getUsername()), playerTwoID);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    // Broadcasts a message to all players in the game channel
    public void broadCastMessageInChannel(Message message) {
        SocketServer.broadcastMessageInChannel(uniqueGameID, message);
    }

    // Broadcasts a message to a specific user in the game channel
    public void broadCastMessageToUser(Message message, String id) {
        SocketServer.broadcastMessageToUser(uniqueGameID, id, message);
    }

    // Generates a new sentence for player one and increments their counter
    public void generateNewForPlayerOneSentence() {
        userOneSentenceCounter++;
        gameLogic.setNextSentenceForPlayerOne(userOneSentenceCounter);
    }

    // Generates a new sentence for player two and increments their counter
    public void generateNewSentenceForPlayerTwo() {
        userTwoSentenceCounter++;
        gameLogic.setNextSentenceForPlayerTwo(userTwoSentenceCounter);
    }

    // Broadcasts the current sentence to player one
    public void getCurrentSentenceForPlayerOne() {
        broadCastMessageToUser(new GetCurrentSentenceResponseMessage(gameLogic.getSentence(userOneSentenceCounter)), playerOneID);
    }

    // Broadcasts the current sentence to player two
    public void getCurrentSentenceForPlayerTwo() {
        broadCastMessageToUser(new GetCurrentSentenceResponseMessage(gameLogic.getSentence(userTwoSentenceCounter)), playerTwoID);
    }

    // Checks the word provided by a player and validates it
    public void checkWord(String word, String playerID) {
        if (playerID.equals(playerOneID)) {
            gameLogic.checkWordForPlayerOne(word);
        } else if (playerID.equals(playerTwoID)) {
            gameLogic.checkWordForPlayerTwo(word);
        } else {
            throw new IllegalArgumentException("Invalid player ID");
        }
    }

    // Generates a new sentence for the specified player
    public void generateNewSentenceForPlayer(String id) {
        if (id.equals(playerOneID)) {
            generateNewForPlayerOneSentence();
        } else if (id.equals(playerTwoID)) {
            generateNewSentenceForPlayerTwo();
        } else {
            throw new IllegalArgumentException("Invalid player ID");
        }
    }

    // Broadcasts the current sentence to the specified player
    public void getCurrentSentenceForPlayer(String id) {
        if (id.equals(playerOneID)) {
            getCurrentSentenceForPlayerOne();
        } else if (id.equals(playerTwoID)) {
            getCurrentSentenceForPlayerTwo();
        } else {
            throw new IllegalArgumentException("Invalid player ID");
        }
    }

    // Updates the WPM score for player one
    @Override
    public void updateWPMForPlayerOne(double wpm) {
        broadCastMessageToUser(new UpdateWPMResponseMessage(wpm), playerOneID);
        broadCastMessageToUser(new UpdateWPMOpponentResponseMessage(wpm), playerTwoID);
    }

    // Updates the WPM score for player two
    @Override
    public void updateWPMForPlayerTwo(double wpm) {
        broadCastMessageToUser(new UpdateWPMResponseMessage(wpm), playerTwoID);
        broadCastMessageToUser(new UpdateWPMOpponentResponseMessage(wpm), playerOneID);
    }

    // Sends the final WPM score to player one when the game finishes
    @Override
    public void gameFinishedFinalWPMForPlayerOne(double wpm) {
        broadCastMessageToUser(new GameFinishedFinalWPMResponseMessage(wpm), playerOneID);
    }

    // Sends the final WPM score to player two when the game finishes
    @Override
    public void gameFinishedFinalWPMForPlayerTwo(double wpm) {
        broadCastMessageToUser(new GameFinishedFinalWPMResponseMessage(wpm), playerTwoID);
    }

    // Sends a word update to player one
    @Override
    public void wordCheckerUpdateForPlayerOne(int wordLength) {
        broadCastMessageToUser(new WordCheckerUpdateResponseMessage(wordLength), playerOneID);
    }

    // Sends a word update to player two
    @Override
    public void wordCheckerUpdateForPlayerTwo(int wordLength) {
        broadCastMessageToUser(new WordCheckerUpdateResponseMessage(wordLength), playerTwoID);
    }

    // Notifies player one of a correct word
    @Override
    public void wordCheckerCorrectWordForPlayerOne() {
        broadCastMessageToUser(new WordCheckerCorrectWordResponseMessage(), playerOneID);
    }

    // Notifies player two of a correct word
    @Override
    public void wordCheckerCorrectWordForPlayerTwo() {
        broadCastMessageToUser(new WordCheckerCorrectWordResponseMessage(), playerTwoID);
    }

    // Sends a countdown timer update to all players
    @Override
    public void timerUpdated(int remainingSeconds) {
        broadCastMessageInChannel(new TimerUpdatedResponseMessage(remainingSeconds));
    }

    // Sends a timer finished message to all players
    @Override
    public void timerFinished() {
        broadCastMessageInChannel(new TimerFinishedResponseMessage());
    }

    // Notifies player one that their sentence is complete
    @Override
    public void sentenceIsDoneForPlayerOne() {
        broadCastMessageToUser(new SentenceIsDoneResponseMessage(), playerOneID);
    }

    // Notifies player two that their sentence is complete
    @Override
    public void sentenceIsDoneForPlayerTwo() {
        broadCastMessageToUser(new SentenceIsDoneResponseMessage(), playerTwoID);
    }

    // Sends a countdown update to all players
    @Override
    public void countdownUpdate(int remainingSeconds) {
        broadCastMessageInChannel(new CountdownUpdateResponseMessage(remainingSeconds));
    }

    // Notifies all players that the countdown is finished and starts the game
    @Override
    public void countdownFinished() {
        broadCastMessageInChannel(new CountdownFinishedResponseMessage());
        gameStarted();
    }

    // Updates the accuracy score for player one
    @Override
    public void updateAccuracyForPlayerOne(double accuracy) {
        broadCastMessageToUser(new UpdateAccuracyResponseMessage(accuracy), playerOneID);
        broadCastMessageToUser(new UpdateAccuracyOpponentResponseMessage(accuracy), playerTwoID);
    }

    // Updates the accuracy score for player two
    @Override
    public void updateAccuracyForPlayerTwo(double accuracy) {
        broadCastMessageToUser(new UpdateAccuracyResponseMessage(accuracy), playerTwoID);
        broadCastMessageToUser(new UpdateAccuracyOpponentResponseMessage(accuracy), playerOneID);
    }

    // Notifies all players that the game has started
    @Override
    public void gameStarted() {
        broadCastMessageInChannel(new StartGameResponseMessage());
    }

    // Updates the Elo score for player one
    @Override
    public void playerOneEloChange(float elo) {
        broadCastMessageToUser(new EloChangeResponseMessage(elo), playerOneID);
    }

    // Updates the Elo score for player two
    @Override
    public void playerTwoEloChange(float elo) {
        broadCastMessageToUser(new EloChangeResponseMessage(elo), playerTwoID);
    }

    // Notifies player one that they won the game
    @Override
    public void playerOneWon() {
        broadCastMessageToUser(new GameResultResponseMessage(true), playerOneID);
    }

    // Notifies player two that they lost the game
    @Override
    public void playerTwoLost() {
        broadCastMessageToUser(new GameResultResponseMessage(false), playerTwoID);
    }

    // Notifies player two that they won the game
    @Override
    public void playerTwoWon() {
        broadCastMessageToUser(new GameResultResponseMessage(true), playerTwoID);
    }

    // Notifies player one that they lost the game
    @Override
    public void playerOneLost() {
        broadCastMessageToUser(new GameResultResponseMessage(false), playerOneID);
    }

    // Notifies all players that the game ended in a tie
    @Override
    public void tieGame() {
        broadCastMessageInChannel(new GameResultWithTieGameResponseMessage());
    }

    // Resets the game state when the game ends
    @Override
    public void gameIsDone() {
        resetGame();
    }

    @Override
    public void notifyOnCurrentWordForPlayerTwo(String currentWord) {
        broadCastMessageToUser(new CurrentWordResponseMessage(currentWord), playerTwoID);
    }

    @Override
    public void notifyOnCurrentWordForPlayerOne(String currentWord) {
        broadCastMessageToUser(new CurrentWordResponseMessage(currentWord), playerOneID);
    }

    // Resets the game to its initial state
    public void resetGame() {
        // save to database here maybe

        // Reset game state
        gameLogic.remove(this);
        gameLogic = null;
        this.users.clear();
        userOneSentenceCounter = 0;
        userTwoSentenceCounter = 0;
        SocketServer.unsubscribeFromGameManager(uniqueGameID, playerOneID);
        SocketServer.unsubscribeFromGameManager(uniqueGameID, playerTwoID);
        playerOneID = null;
        playerTwoID = null;
    }
}
