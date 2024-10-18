package viewmodel;

import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.AddFriendModel;
import network.client.observer.ClientAddFriendObserver;
import util.mvvm.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AddFriendViewModel implements ViewModel<AddFriendModel>, ClientAddFriendObserver {

    private AddFriendModel model;
    private StringProperty searchTextProperty;
    private StringProperty errorText;
    private ListProperty<String> usernames = new SimpleListProperty<>(FXCollections.observableArrayList());

    private final String ADD_FRIEND_ERROR = "Could not find friend";
    private final String ADD_FRIEND_SUCCESS = "Friend added successfully";

    public AddFriendViewModel(AddFriendModel model) {
        setModel(model);
        this.model.setObserver(this);
        searchTextProperty = new SimpleStringProperty("");
        errorText = new SimpleStringProperty("");
        searchTextProperty.addListener((observable, oldValue, newValue) -> getUsernames());
    }

    @Override
    public void setModel(AddFriendModel model) {
        this.model = model;
    }

    public void getUsernames() {
        model.getUsernames();
    }

    public ListProperty<String> usernamesTyped() {
        return usernames;
    }

    public StringProperty searchTextProperty() {
        return searchTextProperty;
    }

    public StringProperty errorTextProperty() {
        return errorText;
    }

    public void close() {
        model.removeObserver();
        model.close();
    }


    @Override
    public void gottenUsernames(List<String> usernames) {
        Platform.runLater(() -> {
            List<String> filtered = usernames.stream()
                    .filter(username -> username.toLowerCase().contains(searchTextProperty.get().toLowerCase()))
                    .collect(Collectors.toList());
            this.usernames.setAll(filtered);
        });
    }

    @Override
    public void friendAdded(boolean success) {
        Platform.runLater(() -> {
            if (success) {
                errorText.set(ADD_FRIEND_SUCCESS);
            } else {
                errorText.set(ADD_FRIEND_ERROR);
            }
        });
    }

    public void addFriend() {
        model.addFriend(searchTextProperty.get());
    }
}
