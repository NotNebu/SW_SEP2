package viewmodel;

import javafx.application.Platform;
import javafx.beans.property.*;
import model.LobbyModel;
import network.client.observer.ClientLobbyObserver;
import util.mvvm.ViewModel;

import java.util.List;
import java.util.Map;

// Manages the lobby view's state and communicates with the LobbyModel.
public class LobbyViewModel implements ViewModel<LobbyModel>, ClientLobbyObserver {

    private LobbyModel model;
    private IntegerProperty friendCount;



    // Initializes LobbyViewModel with the provided LobbyModel.
    public LobbyViewModel(LobbyModel model) {
        setModel(model);
        friendCount = new SimpleIntegerProperty();
        model.setObserver(this);
    }

    public IntegerProperty friendCountProperty() {
        return friendCount;
    }

    @Override
    public void setModel(LobbyModel model) {
        this.model = model;
    }
    public void queue(){
    model.queue();
    }


    public String getFriendProfileName(int i) {
        return model.getFriendProfileName(i);
    }

    public int getFriendElo(int i) {
        return model.getFriendElo(i);
    }

    @Override
    public void usersFriendsGotten(List<String> friends, List<Integer> elos) {
        Platform.runLater(() -> {
            friendCount.set(friends.size());
        });
    }

    @Override
    public void userUpdate() {

    }

    public void getFriends() {
        model.getFriends();
    }
}
