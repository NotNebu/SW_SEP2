package view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import util.javafx.LabelUtils;
import util.mvvm.View;
import view.util.GameViewObserver;
import viewmodel.GameViewModel;


public class GameView implements View<GameViewModel>, GameViewObserver {

    @FXML
    private Pane resultPane;
    @FXML
    private Label secondsLabel, wordsPerMinLabel, accuracyLabel, countdownLabel, showTextLabel,
        accuracyOpponentLabel, wordsPerMinOpponentLabel, opponentNameLabel,
        gameResultLabel, opponentWPMResultLabel, opponentAccuracyResultLabel,
        yourWPMResultLabel, yourAccuracyResultLabel, currentEloResultLabel, eloChangeResultLabel,
        textToWriteLabel;

    @FXML
    private TextField typedWordTextField;
    @FXML
    private VBox sentenceHolderVBox, countdownOverlay;
    @FXML
    private Button leaveQueueButton, resultOKButton;
    @FXML
    private StackPane gamePane;

    private GameViewModel viewModel;

    // Initializes the GameView with a ViewModel.
    public GameView(GameViewModel viewModel) {
        setViewModel(viewModel);
    }

    // Sets the ViewModel and attaches the observer.
    @Override
    public void setViewModel(GameViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.setObserver(this);
    }

    // Initializes the view by binding the view model properties to UI elements.
    @Override
    public void initialize() {
        resultPane.setVisible(false);
        countdownOverlay.setVisible(true);
        typedWordTextField.setEditable(false);

        // Bind text properties to ViewModel properties.
        typedWordTextField.textProperty().bindBidirectional(viewModel.typedWordProperty());
        secondsLabel.textProperty().bindBidirectional(viewModel.timerProperty());
        wordsPerMinLabel.textProperty().bindBidirectional(viewModel.wpmProperty());
        accuracyLabel.textProperty().bindBidirectional(viewModel.accuracyProperty());
        countdownLabel.textProperty().bindBidirectional(viewModel.countDownProperty());
        showTextLabel.textProperty().bindBidirectional(viewModel.sentenceProperty());
        accuracyOpponentLabel.textProperty().bindBidirectional(viewModel.accuracyOpponentProperty());
        wordsPerMinOpponentLabel.textProperty().bindBidirectional(viewModel.wpmOpponentProperty());
        gameResultLabel.textProperty().bindBidirectional(viewModel.gameResultProperty());
        opponentWPMResultLabel.textProperty().bindBidirectional(viewModel.wpmOpponentProperty());
        opponentAccuracyResultLabel.textProperty().bindBidirectional(viewModel.accuracyOpponentProperty());
        yourWPMResultLabel.textProperty().bindBidirectional(viewModel.wpmProperty());
        yourAccuracyResultLabel.textProperty().bindBidirectional(viewModel.accuracyProperty());
        currentEloResultLabel.textProperty().bindBidirectional(viewModel.currentEloResultProperty());
        eloChangeResultLabel.textProperty().bindBidirectional(viewModel.eloChangeResultProperty());
        opponentNameLabel.textProperty().bindBidirectional(viewModel.opponentNameProperty());
        textToWriteLabel.textProperty().bindBidirectional(viewModel.currentWordProperty());


        // Register listeners and event handlers.
        typedWordTextField.textProperty().addListener((observable, oldValue, newValue) -> viewModel.wordChanged());
        leaveQueueButton.setOnAction(e -> leaveQueueButtonClicked());
        resultOKButton.setOnAction(e -> resultOKButtonClicked());
    }

    // Handles the result OK button click to close the window.
    private void resultOKButtonClicked() {
        Stage stage = (Stage) gamePane.getScene().getWindow();
        viewModel.removeObserver();
        stage.close();
    }

    // Handles the leave queue button click to exit the queue and close the window.
    private void leaveQueueButtonClicked() {
        viewModel.leaveQueue();
        Stage stage = (Stage) gamePane.getScene().getWindow();
        viewModel.removeObserver();
        stage.close();
    }

    // Implements GameViewObserver interface methods.
    @Override
    public void onSentenceDone() {
        Platform.runLater(() -> {
            Label newShowTextLabel = LabelUtils.cloneLabel(showTextLabel);
            int index = sentenceHolderVBox.getChildren().indexOf(showTextLabel);
            sentenceHolderVBox.getChildren().add(index + 1, newShowTextLabel);
            viewModel.requestNewSentence();
        });
    }

    @Override
    public void onCountDownFinished() {
        countdownOverlay.setVisible(false);
        typedWordTextField.setEditable(true);
    }

    @Override
    public void gameStarted() {
        viewModel.getCurrentSentence();
    }

    @Override
    public void gameAboutToStartAllPlayerHasJoined() {
        leaveQueueButton.setVisible(false);
    }

    @Override
    public void onGameFinished() {
        typedWordTextField.setEditable(false);
        resultPane.setVisible(true);
    }
}
