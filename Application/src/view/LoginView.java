package view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.SignUpModel;
import util.FXMLUtils;
import util.mvvm.View;
import view.util.LoginViewObserver;
import viewmodel.LoginViewModel;
import viewmodel.LobbyViewModel;
import model.LobbyModel;
import viewmodel.SignUpViewModel;

public class LoginView implements View<LoginViewModel>, LoginViewObserver {


    private LoginViewModel viewModel;

    @FXML
    private Button loginButton;
    @FXML
    private Button signUpButton;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private Label errorTextLabel;


    public LoginView(LoginViewModel viewModel) {
        setViewModel(viewModel);
    }

    @Override
    public void setViewModel(LoginViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @FXML
    public void initialize() {
        loginButton.setOnAction(e -> loginButtonClicked());
        signUpButton.setOnAction(e -> signUpButtonClicked());

        passwordTextField.textProperty().bindBidirectional(viewModel.passwordProperty());
        usernameTextField.textProperty().bindBidirectional(viewModel.usernameProperty());
        errorTextLabel.textProperty().bind(viewModel.errorProperty());

        viewModel.setViewObserver(this);
    }

    private void signUpButtonClicked() {
        FXMLUtils.changeFXML(new Stage(), "signUp", "/fxml/signUp.fxml", SignUpModel.class, SignUpViewModel.class, SignUpView.class);
    }

    public void loginButtonClicked() {
        viewModel.login();
    }

    @Override
    public void loginSuccesFull() {
        viewModel.removeViewObserver();
        Stage currentStage = (Stage) loginButton.getScene().getWindow();
        Platform.runLater(() ->
                FXMLUtils.changeFXML(currentStage, "lobby", "/fxml/lobby.fxml", LobbyModel.class, LobbyViewModel.class, LobbyView.class)
        );
    }
}
