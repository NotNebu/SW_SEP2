package viewmodel;


import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.SignUpModel;
import network.client.observer.ClientSignUpObserver;
import util.mvvm.ViewModel;
import view.util.SignUpViewObserver;

public class SignUpViewModel implements ViewModel<SignUpModel>, ClientSignUpObserver {

    private SignUpModel model;
    private final StringProperty usernameProperty;
    private final StringProperty profileNameProperty;
    private final StringProperty passwordProperty;
    private final StringProperty confirmPasswordProperty;
    private final StringProperty errorProperty;
    private SignUpViewObserver observer;

    private final String ERROR_TEXT_INVALID_PASSWORD = "Passwords do not match";
    private final String ERROR_TEXT_INVALID_USERNAME = "Username already exists";
    private final String ERROR_TEXT_INVALID_PROFILE_NAME = "Profile name already exists";
    private final String ERROR_TEXT_INVALID_PASSWORD_LENGTH = "Password must be no longer than 8 characters";
    private final String ERROR_TEXT_INVALID_PROFILE_NAME_LENGTH = "Profile name must be no longer than 16 characters";
    private final String ERROR_TEXT_INVALID_USERNAME_LENGTH = "Username must be no longer than 16 characters";

    public SignUpViewModel(SignUpModel model) {
        setModel(model);
        usernameProperty = new SimpleStringProperty();
        profileNameProperty = new SimpleStringProperty();
        passwordProperty = new SimpleStringProperty();
        confirmPasswordProperty = new SimpleStringProperty();
        errorProperty = new SimpleStringProperty();
        model.setObserver(this);
    }

    @Override
    public void setModel(SignUpModel model) {
        this.model = model;
    }

    public StringProperty usernameProperty() {
        return usernameProperty;
    }

    public StringProperty profileNameProperty() {
        return profileNameProperty;
    }

    public StringProperty passwordProperty() {
        return passwordProperty;
    }

    public StringProperty confirmPasswordProperty() {
        return confirmPasswordProperty;
    }

    public StringProperty errorProperty() {
        return errorProperty;
    }

    //set observer
    public void setObserver(SignUpViewObserver observer) {
        this.observer = observer;
    }

    //remove observer
    public void removeObserver() {
        this.observer = null;
        model.removeObserver();
    }

    public void signUp() {
        if (!passwordProperty.get().equals(confirmPasswordProperty.get())) {
            errorProperty.set(ERROR_TEXT_INVALID_PASSWORD);
            return;
        } else if (passwordProperty.get().length() > 8) {
            errorProperty.set(ERROR_TEXT_INVALID_PASSWORD_LENGTH);
            return;
        } else if (profileNameProperty.get().length() > 16) {
            errorProperty.set(ERROR_TEXT_INVALID_PROFILE_NAME_LENGTH);
            return;
        } else if (usernameProperty.get().length() > 16) {
            errorProperty.set(ERROR_TEXT_INVALID_USERNAME_LENGTH);
            return;
        }
        model.usernameExists(usernameProperty.get());
    }


    @Override
    public void usernameExists(boolean exists) {
        if (exists) {
            Platform.runLater(() -> errorProperty.set(ERROR_TEXT_INVALID_USERNAME));
        } else {
            model.profileNameExists(profileNameProperty.get());
        }
    }

    @Override
    public void profileNameExists(boolean exists) {
        if (exists) {
            Platform.runLater(() -> errorProperty.set(ERROR_TEXT_INVALID_PROFILE_NAME));
        } else {
            model.signUp(usernameProperty.get(), passwordProperty.get(), profileNameProperty.get());
        }
    }

    @Override
    public void signUpSuccess() {
        observer.signUpSucces();
    }
}
