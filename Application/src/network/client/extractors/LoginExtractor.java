package network.client.extractors;

import network.client.observer.ClientLoginObserver;
import network.util.message.Message;
import network.util.message.response.LoginResponseMessage;

import java.util.ArrayList;

public class LoginExtractor implements IExtractor {

    private ArrayList<ClientLoginObserver> loginObservers;

    public LoginExtractor(ArrayList<ClientLoginObserver> loginObservers) {
        this.loginObservers = loginObservers;
    }

    public void extractContext(Message message) {
        switch (message.getType()){
            case LOGIN_RESPONSE_MESSAGE:
                LoginResponseMessage loginResponseMessage = (LoginResponseMessage) message;
                if(!loginResponseMessage.isSuccessful()) {
                    loginObservers.forEach(ClientLoginObserver::loginFailed);
                    return;
                }
                loginObservers.forEach(observer -> observer.loginSuccess(loginResponseMessage.getUsername(), loginResponseMessage.getUserId()));
                break;
            default:
                throw new IllegalArgumentException("Invalid message type: " + message.getType());

        }
    }
}
