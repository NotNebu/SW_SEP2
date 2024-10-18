package model;

import model.user.UserDTO;
import network.client.observer.ClientLoginObserver;
import network.client.socket.SocketClient;
import util.holders.UserDTOHolder;
import util.mvvm.Model;

public class LoginModel implements Model, ClientLoginObserver {


    private ClientLoginObserver observer;

    private final SocketClient loginClient;
    public LoginModel() {
        loginClient = SocketClient.getInstance();
        loginClient.addLoginObserver(this);
    }

    public void setObserver(ClientLoginObserver observer) {
        this.observer = observer;
    }

    public void removeObserver() {
        this.observer = null;
    }

    public void login(String username, String password) {
        loginClient.login(username, password);
    }

    @Override
    public void loginSuccess(String username, String userId) {
        UserDTOHolder.setUserDTO(new UserDTO(username, userId));
        observer.loginSuccess(username, userId);
    }

    @Override
    public void loginFailed() {
        observer.loginFailed();
    }
}
