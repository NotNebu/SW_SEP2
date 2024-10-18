package viewmodel;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.FriendListModel;
import util.mvvm.ViewModel;

public class FriendListViewModel implements ViewModel<FriendListModel> {

    private FriendListModel model;
    private StringProperty friendProfileName;
    private StringProperty friendElo;


    public FriendListViewModel(FriendListModel model) {
        setModel(model);
        this.friendProfileName = new SimpleStringProperty();
        this.friendElo = new SimpleStringProperty();
    }

    @Override
    public void setModel(FriendListModel model) {
        this.model = model;
    }


    public void setFriendProfileName(String name) {
        Platform.runLater(() -> friendProfileName.set(name));
    }

    public void setFriendElo(int elo) {
        Platform.runLater(() -> friendElo.set(String.valueOf(elo)));
    }

    public StringProperty friendProfileNameProperty() {
        return friendProfileName;
    }

    public StringProperty friendEloProperty() {
        return friendElo;
    }

    public void removeFriend() {
        model.removeFriend(friendProfileName.get());
    }
}
