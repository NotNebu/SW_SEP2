package view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import util.mvvm.View;
import viewmodel.AddFriendViewModel;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;



public class AddFriendView implements View<AddFriendViewModel> {

    @FXML
    private TextField searchTextField;
    @FXML
    private ListView<String> searchResultsListView;
    @FXML
    private Button addButton, closeButton;
    @FXML
    private Label errorTextLabel;

    private AddFriendViewModel viewModel;

    public AddFriendView(AddFriendViewModel viewModel){
        setViewModel(viewModel);
    }

    @Override
    public void setViewModel(AddFriendViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void initialize() {
        closeButton.setOnAction(e -> closeButtonClicked());
        addButton.setOnAction(e -> addFriendButtonClicked());

        searchResultsListView.itemsProperty().bind(viewModel.usernamesTyped());

        errorTextLabel.textProperty().bindBidirectional(viewModel.errorTextProperty());

        searchTextField.textProperty().bindBidirectional(viewModel.searchTextProperty());

        searchTextField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.DOWN) {
                searchResultsListView.requestFocus();
                searchResultsListView.getSelectionModel().selectFirst();
            }
        });

        searchResultsListView.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                searchTextField.setText(searchResultsListView.getSelectionModel().getSelectedItem());
                searchResultsListView.getItems().clear();
            }
        });

        searchResultsListView.setOnMouseClicked(mouseEvent -> {
            String selectedItem = searchResultsListView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                searchTextField.setText(selectedItem);
            }
        });

    }

    private void addFriendButtonClicked() {
        viewModel.addFriend();
    }

    private void closeButtonClicked() {
        viewModel.close();
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }


}
