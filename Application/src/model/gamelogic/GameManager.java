package model.gamelogic;

import database.DAOManager;
import model.user.UserDTO;
import model.util.ListWithListener;
import network.server.socket.SocketServer;

import java.util.*;

// Manages game assignments and connected users
public class GameManager {

    private static final int MAX_GAMES = 5;
    private static final int MAX_PLAYERS_PER_GAME = 2;
    private ArrayList<Game> games;
    private ListWithListener<Map<UserDTO, SocketServer.ClientHandler>> connectedUsers;
    private DAOManager daoManager;

    // Initializes GameManager and sets up listener for user assignment
    public GameManager(DAOManager daoManager) {
        games = new ArrayList<>(MAX_GAMES);
        connectedUsers = new ListWithListener<>(MAX_PLAYERS_PER_GAME);
        this.daoManager = daoManager;
        createGames();
        connectedUsers.setListener(this::assignUsersToGame);

    }

    // Creates a fixed number of games and adds them to the games list
    private void createGames() {
        for (int i = 0; i < MAX_GAMES; i++) {
            games.add(new Game(daoManager));
        }
    }

    // Retrieves a game by its unique ID
    public Game getGameByUniqueID(String uniqueID) {
        return games.stream()
            .filter(game -> game.getUniqueGameID().equals(uniqueID))
            .findFirst().orElse(null);
    }

    // Retrieves the first game that has space available
    private Game getFreeGame() {
        return games.stream()
            .filter(game -> game.getUsers().size() < MAX_PLAYERS_PER_GAME)
            .findFirst().orElse(null);
    }

    // Retrieves a game associated with a given user object
    public Game getGameByUserDTO(UserDTO userDTO) {
        return games.stream()
            .filter(game -> game.getUsers().contains(userDTO))
            .findFirst().orElse(null);
    }

    // Retrieves the unique ID of the first available game
    public String getFreeGameID() {
        return Objects.requireNonNull(getFreeGame()).getUniqueGameID();
    }

    // Adds a new user to the connected user queue if not already present
    public void addUserToQueue(UserDTO user, SocketServer.ClientHandler clientHandler) {
        if (!isUserInQueue(user)) {
            Map<UserDTO, SocketServer.ClientHandler> userMap = new HashMap<>();
            userMap.put(user, clientHandler);
            connectedUsers.add(userMap);
        }
    }

    // Removes a user from the connected queue
    public void removeUserFromQueue(UserDTO user, SocketServer.ClientHandler clientHandler) {
        connectedUsers.removeIf(userMap -> userMap.containsKey(user) && userMap.get(user) == clientHandler);
    }

    // Checks if a user is already in the queue
    private boolean isUserInQueue(UserDTO user) {
        return connectedUsers.stream()
            .anyMatch(userMap -> userMap.containsKey(user));
    }

    // Assigns the first two users in the queue to an available game and starts it
    private void assignUsersToGame() {
        if (connectedUsers.size() >= MAX_PLAYERS_PER_GAME) {
            Game game = getFreeGame();
            if (game != null) {
                List<Map<UserDTO, SocketServer.ClientHandler>> firstTwoUsers = connectedUsers.subList(0, MAX_PLAYERS_PER_GAME);
                firstTwoUsers.forEach(userMap -> {
                    UserDTO user = userMap.keySet().iterator().next();
                    game.addUser(user);
                    SocketServer.subscribeToGameManager(game.getUniqueGameID(), user.getId(), userMap.get(user));
                });
                connectedUsers.removeAll(firstTwoUsers);
                game.assignPlayersAndStartGame();
            }
        }
    }
}
