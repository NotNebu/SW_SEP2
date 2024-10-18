package network.util.message.request;

import network.util.message.MessageBase;
import network.util.message.MessageSuperType;
import network.util.message.MessageType;

public class GetUsersFriendsRequestMessage extends MessageBase {

    private String username;

    private String ID;

    public GetUsersFriendsRequestMessage(String username, String ID) {
        super(MessageType.USERS_FRIENDS_GOTTEN_REQUEST_MESSAGE, MessageSuperType.LOBBY);
        this.username = username;
        this.ID = ID;
    }

    public String getUsername() {
        return username;
    }

    public String getID() {
        return ID;
    }
}
