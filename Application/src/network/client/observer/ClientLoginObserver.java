package network.client.observer;

public interface ClientLoginObserver {

    void loginSuccess(String username, String userId);
    void loginFailed();

}
