package network.client.extractors;

import network.client.observer.ClientLobbyObserver;
import network.util.message.Message;
import network.util.message.response.GetUsersFriendsResponseMessage;

import java.util.ArrayList;

public class LobbyExtractor implements IExtractor {

    private final ArrayList<ClientLobbyObserver> observers;

    public LobbyExtractor(ArrayList<ClientLobbyObserver> observers) {
        this.observers = observers;
    }

    @Override
    public void extractContext(Message message) {
        switch (message.getType()) {
            case USERS_FRIENDS_GOTTEN_RESPONSE_MESSAGE:
                GetUsersFriendsResponseMessage getUsersFriendsResponseMessage = (GetUsersFriendsResponseMessage) message;
                observers.forEach(observer -> observer.usersFriendsGotten(getUsersFriendsResponseMessage.getFriends(),
                        getUsersFriendsResponseMessage.getElos()));
                break;
            case FRIENDS_UPDATED_RESPONSE_MESSAGE:
                observers.forEach(ClientLobbyObserver::userUpdate);
                break;
            default:
                throw new IllegalArgumentException("Invalid message type: " + message.getType());
        }
    }
}
