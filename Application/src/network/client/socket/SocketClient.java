package network.client.socket;

import model.user.UserDTO;
import network.client.extractors.*;
import network.client.observer.*;
import network.util.message.Message;
import network.util.message.request.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SocketClient {

    private Socket socket;
    private Thread serverListener;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private final Lock receiveLock = new ReentrantLock();
    private final Lock sendLock = new ReentrantLock();
    private final GameExtractor gameExtractor;
    private final SignUpExtractor signUpExtractor;
    private final LoginExtractor loginExtractor;
    private final ProfileExtractor profileExtractor;
    private final AddFriendExtractor addFriendExtractor;
    private final LobbyExtractor lobbyExtractor;
    private static SocketClient instance;


    /*
    Observer
     */
    private final ArrayList<ClientGameObserver> gameObservers = new ArrayList<>();
    private final ArrayList<ClientSignUpObserver> signUpObservers = new ArrayList<>();
    private final ArrayList<ClientLoginObserver> loginObservers = new ArrayList<>();
    private final ArrayList<ClientProfileObserver> profileObservers = new ArrayList<>();
    private final ArrayList<ClientAddFriendObserver> addFriendObservers = new ArrayList<>();
    private final ArrayList<ClientLobbyObserver> lobbyObservers = new ArrayList<>();

    private SocketClient() {
        try {
            socket = new Socket("localhost", 2910);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            startListeningToServer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        addFriendExtractor = new AddFriendExtractor(addFriendObservers);
        gameExtractor = new GameExtractor(gameObservers);
        signUpExtractor = new SignUpExtractor(signUpObservers);
        loginExtractor = new LoginExtractor(loginObservers);
        profileExtractor = new ProfileExtractor(profileObservers);
        lobbyExtractor = new LobbyExtractor(lobbyObservers);
    }

    public static SocketClient getInstance() {
        if (instance == null) {
            instance = new SocketClient();
        }
        return instance;
    }

    /*
    Thread that listens to the server
     */
    public void startListeningToServer() {
        serverListener = new Thread(() -> {
            while (true) {
                Message message = receiveMessage();
                switch (message.getSuperType()) {
                    case GAME:
                        gameExtractor.extractContext(message);
                        break;
                    case SIGN_UP:
                        signUpExtractor.extractContext(message);
                        break;
                    case LOGIN:
                        loginExtractor.extractContext(message);
                        break;
                    case PROFILE:
                        profileExtractor.extractContext(message);
                        break;
                    case FRIENDS:
                        addFriendExtractor.extractContext(message);
                        break;
                    case LOBBY:
                        lobbyExtractor.extractContext(message);
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown super message type");
                }
            }
        });

        serverListener.setDaemon(true);
        serverListener.start();
    }

    //send message to server with synchronized method
    public void sendMessage(Message message) {
        synchronized (sendLock) {
            try {
                objectOutputStream.writeObject(message);
                objectOutputStream.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // receive message from server with synchronized method
    public Message receiveMessage() {
        synchronized (receiveLock) {
            try {
                return (Message) objectInputStream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // add game observer
    public void addGameObserver(ClientGameObserver observer) {
        this.gameObservers.add(observer);
    }

    // remove game observer
    public void removeGameObserver(ClientGameObserver observer) {
        this.gameObservers.remove(observer);
    }

    // add sign up observer
    public void addSignUpObserver(ClientSignUpObserver observer) {
        this.signUpObservers.add(observer);
    }

    // remove sign up observer
    public void removeSignUpObserver(ClientSignUpObserver observer) {
        this.signUpObservers.remove(observer);
        this.signUpObservers.clear();
    }

    // add login observer
    public void addLoginObserver(ClientLoginObserver observer) {
        this.loginObservers.add(observer);
    }

    // remove login observer
    public void removeLoginObserver(ClientLoginObserver observer) {
        this.loginObservers.remove(observer);
    }

    // add profile observer
    public void addProfileObserver(ClientProfileObserver observer) {
        this.profileObservers.add(observer);
    }

    // remove profile observer
    public void removeProfileObserver(ClientProfileObserver observer) {
        this.profileObservers.remove(observer);
    }

    // add friend observer
    public void addAddFriendObserver(ClientAddFriendObserver observer) {
        this.addFriendObservers.add(observer);
    }

    // remove add friend observer
    public void removeAddFriendObserver(ClientAddFriendObserver observer) {
        this.addFriendObservers.remove(observer);
    }

    // add lobby observer
    public void addLobbyObserver(ClientLobbyObserver observer) {
        this.lobbyObservers.add(observer);
    }

    // remove lobby observer
    public void removeLobbyObserver(ClientLobbyObserver observer) {
        this.lobbyObservers.remove(observer);
    }

    //get sentence
    public void getSentence(UserDTO userDTO) {
        sendMessage(new GetCurrentSentenceRequestMessage(userDTO));
    }

    //check word
    public void checkWord(UserDTO userDTO, String word) {
        sendMessage(new CheckWordRequestMessage(userDTO, word));
    }

    //request new sentence
    public void generateNewSentence(UserDTO userDTO) {
        sendMessage(new GenerateNewSentenceRequestMessage(userDTO));
    }

    //join game queue
    public void joinGameQueue(UserDTO userDTO) {
        sendMessage(new JoinQueueRequestMessage(userDTO));
    }

    //leave game queue
    public void leaveQueue(UserDTO userDTO) {
        sendMessage(new LeaveQueueRequestMessage(userDTO));
    }

    //check username exists
    public void checkUsernameExists(String username) {
        sendMessage(new UserNameExistRequestMessage(username));
    }

    //check profile name exists
    public void checkProfileNameExists(String profileName) {
        sendMessage(new ProfileNameExistsRequestMessage(profileName));
    }

    //sign up
    public void signUp(String username, String password, String profileName) {
        sendMessage(new SignUpRequestMessage(username, password, profileName));
    }

    public void login(String username, String password) {
        sendMessage(new LoginRequestMessage(username, password));
    }

    public void getProfile(String userID) {
        sendMessage(new GetProfileRequestMessage(userID));
    }

    public void getUsernames(String id, String username) {
        sendMessage(new GetUsernamesForFriendRequestMessage(id, username));
    }

    public void addFriend(String username, String id, String friendUsername) {
        sendMessage(new AddFriendRequestMessage(username, id, friendUsername));
    }

    public void getUsersFriends(String username, String id) {
        sendMessage(new GetUsersFriendsRequestMessage(username, id));
    }

    public void removeFriend(String friendName, String username, String id) {
        sendMessage(new RemoveFriendRequestMessage(friendName, username, id));
    }
}