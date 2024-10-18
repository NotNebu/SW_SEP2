package network.util.message.request;

import network.util.message.MessageBase;
import network.util.message.MessageSuperType;
import network.util.message.MessageType;

public class GetUsernamesForFriendRequestMessage extends MessageBase {

    private String username;
    private String id;

    public GetUsernamesForFriendRequestMessage(String id, String username) {
        super(MessageType.GET_USERNAMES_FOR_FRIEND_REQUEST_MESSAGE, MessageSuperType.FRIENDS);
        this.username = username;
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public String getId() {
        return id;
    }
}
