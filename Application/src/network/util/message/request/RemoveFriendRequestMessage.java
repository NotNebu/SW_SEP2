package network.util.message.request;

import network.util.message.MessageBase;
import network.util.message.MessageSuperType;
import network.util.message.MessageType;

public class RemoveFriendRequestMessage extends MessageBase {

    private String friendName;
    private String username;
    private String id;

    public RemoveFriendRequestMessage(String friendName, String username, String id) {
        super(MessageType.REMOVE_FRIEND_REQUEST_MESSAGE, MessageSuperType.FRIENDS);
        this.friendName = friendName;
        this.username = username;
        this.id = id;
    }

    public String getFriendName() {
        return friendName;
    }

    public String getUsername() {
        return username;
    }

    public String getId() {
        return id;
    }
}
