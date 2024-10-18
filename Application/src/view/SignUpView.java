package view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import util.mvvm.View;
import view.util.SignUpViewObserver;
import viewmodel.SignUpViewModel;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


public class SignUpView implements View<SignUpViewModel>, SignUpViewObserver {

    private SignUpViewModel viewModel;
    @FXML
    private Button signUpButton;
    @FXML
    private TextField usernameTextField, profilenameTextfield, passwordTextField, comfirmpasswordTextField;
    @FXML
    private Label errorTextLabel;

    public SignUpView(SignUpViewModel viewModel) {
        setViewModel(viewModel);
    }

    @Override
    public void setViewModel(SignUpViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void initialize() {
        signUpButton.setOnAction(e -> signUpButtonClicked());
        usernameTextField.textProperty().bindBidirectional(viewModel.usernameProperty());
        profilenameTextfield.textProperty().bindBidirectional(viewModel.profileNameProperty());
        passwordTextField.textProperty().bindBidirectional(viewModel.passwordProperty());
        comfirmpasswordTextField.textProperty().bindBidirectional(viewModel.confirmPasswordProperty());
        errorTextLabel.textProperty().bind(viewModel.errorProperty());
        viewModel.setObserver(this);
    }

    private void signUpButtonClicked() {
        viewModel.signUp();
    }

    @Override
    public void signUpSucces() {
        viewModel.removeObserver();
        Platform.runLater(() -> {
            Stage stage = (Stage) signUpButton.getScene().getWindow();
            stage.close();
        });
    }

}
