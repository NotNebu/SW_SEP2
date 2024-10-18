package model;

import model.user.UserDTO;
import network.client.observer.ClientAddFriendObserver;
import network.client.socket.SocketClient;
import util.holders.UserDTOHolder;
import util.mvvm.Model;

import java.util.List;

public class AddFriendModel implements Model, ClientAddFriendObserver {

    private SocketClient addFriendClient;
    private ClientAddFriendObserver observer;

    public AddFriendModel(){
        addFriendClient = SocketClient.getInstance();
        addFriendClient.addAddFriendObserver(this);
    }

    public void setObserver(ClientAddFriendObserver observer){
        this.observer = observer;
    }

    public void removeObserver(){
        this.observer = null;
    }

    public void getUsernames() {
        addFriendClient.getUsernames(UserDTOHolder.getID(), UserDTOHolder.getUsername());
    }

    @Override
    public void gottenUsernames(List<String> usernames) {
        if(observer != null){
            observer.gottenUsernames(usernames);
        }
    }

    @Override
    public void friendAdded(boolean success) {
        if(observer != null){
            observer.friendAdded(success);
        }
    }

    public void close() {
        addFriendClient.removeAddFriendObserver(this);
    }

    public void addFriend(String friendUsername) {
        addFriendClient.addFriend(UserDTOHolder.getUsername(), UserDTOHolder.getID(), friendUsername);
    }
}
