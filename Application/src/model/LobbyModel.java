package model;

import network.client.observer.ClientLobbyObserver;
import network.client.socket.SocketClient;
import util.holders.UserDTOHolder;
import util.mvvm.Model;

import java.util.ArrayList;
import java.util.List;

// Manages the game lobby, handling queue requests via the socket client
public class LobbyModel implements Model, ClientLobbyObserver {

    private final SocketClient lobbyClient;
    private List<String> friendList;
    private List<Integer> friendElo;
    private ClientLobbyObserver observer;

    // Initializes the model with the singleton socket client
    public LobbyModel() {
        lobbyClient = SocketClient.getInstance();
        friendList = new ArrayList<>();
        friendElo = new ArrayList<>();
        lobbyClient.addLobbyObserver(this);
    }

    // Adds the current user to the game queue
    public void queue() {
        lobbyClient.joinGameQueue(UserDTOHolder.getUserDTO());
    }

    // Sets the observer for the lobby model
    public void setObserver(ClientLobbyObserver observer) {
        this.observer = observer;
    }

    // remove the observer for the lobby model
    public void removeObserver() {
        this.observer = null;
    }

    public String getFriendProfileName(int i) {
        if(i >= friendList.size()) {
            return "ERROR";
        }
        return friendList.get(i);
    }

    public int getFriendElo(int i) {
        if(i >= friendElo.size()) {
            return -1;
        }
        return friendElo.get(i);
    }

    @Override
    public void usersFriendsGotten(List<String> friends, List<Integer> elos) {
        this.friendList = friends;
        this.friendElo = elos;
        this.observer.usersFriendsGotten(friends, elos);
    }

    @Override
    public void userUpdate() {
        getFriends();
    }

    public void getFriends() {
        lobbyClient.getUsersFriends(UserDTOHolder.getUsername(), UserDTOHolder.getID());
    }
}
