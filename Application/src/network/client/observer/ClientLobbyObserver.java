package network.client.observer;

import java.util.List;

public interface ClientLobbyObserver {

    void usersFriendsGotten(List<String> friends, List<Integer> elos);

    void userUpdate();

}
