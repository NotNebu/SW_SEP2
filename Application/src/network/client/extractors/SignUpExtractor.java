package network.client.extractors;

import network.client.observer.ClientSignUpObserver;
import network.util.message.Message;
import network.util.message.response.ProfileNameExistsResponseMessage;
import network.util.message.response.UserNameExistResponseMessage;

import java.util.ArrayList;

public class SignUpExtractor implements IExtractor{

    private ArrayList<ClientSignUpObserver> signUpObservers;

    public SignUpExtractor(ArrayList<ClientSignUpObserver> signUpObservers) {
        this.signUpObservers = signUpObservers;
    }

    public void extractContext(Message message) {
        switch (message.getType()){
            case SIGN_UP_RESPONSE_MESSAGE:
                signUpObservers.forEach(ClientSignUpObserver::signUpSuccess);
                break;
            case PROFILE_NAME_EXISTS_RESPONSE_MESSAGE:
                ProfileNameExistsResponseMessage profileNameExistResponseMessage = (ProfileNameExistsResponseMessage) message;
                signUpObservers.forEach(observer -> observer.profileNameExists(profileNameExistResponseMessage.getExists()));
                break;
            case USER_NAME_EXIST_RESPONSE_MESSAGE:
                UserNameExistResponseMessage userNameExistResponseMessage = (UserNameExistResponseMessage) message;
                signUpObservers.forEach(observer -> observer.usernameExists(userNameExistResponseMessage.exists()));
                break;
            default:
                throw new IllegalArgumentException("Invalid message type: " + message.getType());

        }
    }
}
