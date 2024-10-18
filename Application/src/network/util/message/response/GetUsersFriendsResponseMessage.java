package network.util.message.response;

import network.util.message.MessageBase;
import network.util.message.MessageSuperType;
import network.util.message.MessageType;

import java.util.List;

public class GetUsersFriendsResponseMessage extends MessageBase {

    private List<String> friends;
    private List<Integer> elos;

    public GetUsersFriendsResponseMessage(List<String> friends, List<Integer> elos) {
        super(MessageType.USERS_FRIENDS_GOTTEN_RESPONSE_MESSAGE, MessageSuperType.LOBBY);
        this.friends = friends;
        this.elos = elos;
    }

    public List<String> getFriends() {
        return friends;
    }

    public List<Integer> getElos() {
        return elos;
    }
}
