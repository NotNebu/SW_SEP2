package view;

import javafx.fxml.FXML;
import util.mvvm.View;
import viewmodel.FriendListViewModel;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


public class FriendListView implements View<FriendListViewModel> {

    @FXML
    private Label friendNameLabel;
    @FXML
    private Label friendELOLabel;
    @FXML
    private Button removeButton;
    private FriendListViewModel viewModel;

    public FriendListView(FriendListViewModel viewModel) {
        setViewModel(viewModel);
    }

    @Override
    public void setViewModel(FriendListViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void initialize() {
        removeButton.setOnAction(e -> removeFriendButtonClicked());

        friendNameLabel.textProperty().bind(viewModel.friendProfileNameProperty());
        friendELOLabel.textProperty().bind(viewModel.friendEloProperty());
    }

    private void removeFriendButtonClicked() {
        viewModel.removeFriend();
        removeButton.setVisible(false);
    }

    public void setProperties(String name, int elo) {
        viewModel.setFriendProfileName(name);
        viewModel.setFriendElo(elo);
    }

}
