package viewmodel;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import model.LoginModel;
import network.client.observer.ClientLoginObserver;
import util.holders.UserDTOHolder;
import util.mvvm.ViewModel;
import view.util.LoginViewObserver;

public class LoginViewModel implements ViewModel<LoginModel>, ClientLoginObserver {

    private LoginModel model;

    private LoginViewObserver viewObserver;

    private final StringProperty usernameProperty = new SimpleStringProperty();
    private final StringProperty passwordProperty = new SimpleStringProperty();
    private final StringProperty errorProperty = new SimpleStringProperty();

    private final String DEFAULT_ERROR_MESSAGE = "Invalid username or password";

    public LoginViewModel(LoginModel model) {
        setModel(model);
        model.setObserver(this);
    }

    public StringProperty usernameProperty() {
        return usernameProperty;
    }

    // Property accessor for ID data binding.
    public StringProperty passwordProperty() {
        return passwordProperty;
    }

    @Override
    public void setModel(LoginModel model) {
        this.model = model;
    }

    public void setViewObserver(LoginViewObserver viewObserver) {
        this.viewObserver = viewObserver;
    }

    public void removeViewObserver() {
        this.viewObserver = null;
    }

    public void login(){
        model.login(usernameProperty.get(), passwordProperty.get());
    }

    public StringProperty errorProperty() {
        return errorProperty;
    }

    @Override
    public void loginSuccess(String username, String userId) {
        viewObserver.loginSuccesFull();
        model.removeObserver();
    }

    @Override
    public void loginFailed() {
        Platform.runLater(() -> errorProperty.set(DEFAULT_ERROR_MESSAGE));
    }
}
