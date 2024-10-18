package network.client.observer;

import java.util.List;

public interface ClientAddFriendObserver {

    void gottenUsernames(List<String> usernames);

    void friendAdded(boolean success);

}
