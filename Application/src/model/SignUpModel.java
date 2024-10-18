package model;

import network.client.observer.ClientSignUpObserver;
import network.client.socket.SocketClient;
import util.mvvm.Model;
import viewmodel.SignUpViewModel;

public class SignUpModel implements Model, ClientSignUpObserver {

    private SocketClient signUpClient;
    private ClientSignUpObserver observer;

    public SignUpModel() {
        signUpClient = SocketClient.getInstance();
        signUpClient.removeSignUpObserver(this);
        signUpClient.addSignUpObserver(this);
    }

    public void usernameExists(String username) {
        signUpClient.checkUsernameExists(username);
    }

    public void profileNameExists(String profileName) {
        signUpClient.checkProfileNameExists(profileName);
    }

    public void signUp(String username, String password, String profileName) {
        signUpClient.signUp(username, password, profileName);
    }

    @Override
    public void usernameExists(boolean exists) {
        observer.usernameExists(exists);
    }

    @Override
    public void profileNameExists(boolean exists) {
        observer.profileNameExists(exists);
    }

    @Override
    public void signUpSuccess() {
        observer.signUpSuccess();
    }

    public void setObserver(ClientSignUpObserver observer) {
        this.observer = observer;
    }


    public void removeObserver() {
        this.observer = null;
    }
}
