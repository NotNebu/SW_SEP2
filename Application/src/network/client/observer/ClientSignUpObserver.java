package network.client.observer;

public interface ClientSignUpObserver {

    void usernameExists(boolean exists);
    void profileNameExists(boolean exists);
    void signUpSuccess();

}
