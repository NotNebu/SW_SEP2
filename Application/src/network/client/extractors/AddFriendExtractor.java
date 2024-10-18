package network.client.extractors;

import network.client.observer.ClientAddFriendObserver;
import network.util.message.Message;
import network.util.message.response.AddFriendResponseMessage;
import network.util.message.response.GetUsernamesForAddFriendResponseMessage;

import java.util.ArrayList;

public class AddFriendExtractor implements IExtractor {

    private ArrayList<ClientAddFriendObserver> observers;

    public AddFriendExtractor(ArrayList<ClientAddFriendObserver> observers) {
        this.observers = observers;
    }

    @Override
    public void extractContext(Message message) {
        switch (message.getType()) {
            case GET_USERNAMES_FOR_FRIEND_RESPONSE_MESSAGE:
                GetUsernamesForAddFriendResponseMessage getUsernamesForAddFriendResponseMessage = (GetUsernamesForAddFriendResponseMessage) message;
                observers.forEach(observer -> observer.gottenUsernames(getUsernamesForAddFriendResponseMessage.getUsernames()));
                break;
            case ADD_FRIEND_RESPONSE_MESSAGE:
                AddFriendResponseMessage addFriendResponseMessage = (AddFriendResponseMessage) message;
                observers.forEach(observer -> observer.friendAdded(addFriendResponseMessage.isSuccess()));
                break;
            default:
                throw new IllegalArgumentException("Invalid message type: " + message.getType());

        }
    }
}
