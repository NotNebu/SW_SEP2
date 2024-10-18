package model;

import network.client.socket.SocketClient;
import util.holders.UserDTOHolder;
import util.mvvm.Model;

public class FriendListModel implements Model {

    private SocketClient friendListClient;

    public FriendListModel() {
        friendListClient = SocketClient.getInstance();
    }

    public void removeFriend(String friendName) {
        friendListClient.removeFriend(friendName, UserDTOHolder.getUsername(), UserDTOHolder.getID());
    }
}
