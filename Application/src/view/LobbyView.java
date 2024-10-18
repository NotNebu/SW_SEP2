package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.*;
import util.FXMLUtils;
import util.mvvm.MVVMFactory;
import util.mvvm.View;
import viewmodel.*;

import java.io.IOException;

public class LobbyView implements View<LobbyViewModel> {

    @FXML
    private ScrollPane friendListScrollPane;
    @FXML
    private Button queueButton;
    @FXML
    private Button rulesButton;
    @FXML
    private AnchorPane profileContainer;
    @FXML
    private Button addFriendButton;
    @FXML
    private VBox friendListVbox;

    private LobbyViewModel viewModel;

    // Initializes the LobbyView with a ViewModel.
    public LobbyView(LobbyViewModel viewModel) {
        setViewModel(viewModel);
    }

    // Sets the ViewModel.
    @Override
    public void setViewModel(LobbyViewModel viewModel) {
        this.viewModel = viewModel;
    }

    // Initializes the view by binding the view model properties to UI elements.
    @Override
    public void initialize() {
        queueButton.setOnAction(e -> queueButtonClicked());
        rulesButton.setOnAction(e -> rulesButtonClicked());
        addFriendButton.setOnAction(e -> addFriendButtonClicked());

        viewModel.getFriends();

        MVVMFactory<ProfileModel, ProfileViewModel, ProfileView> profileFactory = new MVVMFactory<>();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/profile.fxml"));
        ProfileView profileView = profileFactory.createView(ProfileModel.class, ProfileViewModel.class, ProfileView.class);
        loader.setControllerFactory(controllerClass -> profileView);
        try {
            AnchorPane profileAnchorPane = loader.load();
            profileContainer.getChildren().add(profileAnchorPane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        viewModel.friendCountProperty().addListener((observable, oldValue, newValue) -> {
            setFriendsList(newValue.intValue());
        });


    }

    private void addFriendButtonClicked() {
        FXMLUtils.openPopupWindow(new Stage(), "AddFriend", "/fxml/addFriend.fxml", AddFriendModel.class, AddFriendViewModel.class, AddFriendView.class);
    }

    private void rulesButtonClicked() {
        FXMLUtils.openPopupWindow(new Stage(), "Rules", "/fxml/rules.fxml", RulesModel.class, RulesViewModel.class, RulesView.class);
    }

    private void setFriendsList(int newValue) {
        friendListVbox.getChildren().clear();
        for (int i = 0; i < newValue; i++) {
            MVVMFactory<FriendListModel, FriendListViewModel, FriendListView> friendFactory = new MVVMFactory<>();
            FXMLLoader friendLoader = new FXMLLoader(getClass().getResource("/fxml/friendList.fxml"));
            FriendListView friend = friendFactory.createView(FriendListModel.class, FriendListViewModel.class, FriendListView.class);
            friend.setProperties(viewModel.getFriendProfileName(i), viewModel.getFriendElo(i));
            friendLoader.setControllerFactory(controllerClass -> friend);
            try {
                friendListVbox.getChildren().add(friendLoader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // Handles the queue button click to join a game queue and open the game window.
    private void queueButtonClicked() {
        viewModel.queue();
        FXMLUtils.openPopupWindow(new Stage(), "Game", "/fxml/game.fxml", GameModel.class, GameViewModel.class, GameView.class);
    }
}
