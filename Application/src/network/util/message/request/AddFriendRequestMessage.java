package network.util.message.request;

import network.util.message.MessageBase;
import network.util.message.MessageSuperType;
import network.util.message.MessageType;

public class AddFriendRequestMessage extends MessageBase {

    private String username;
    private String userID;
    private String friendUsername;

    public AddFriendRequestMessage(String username, String userID, String friendUsername) {
        super(MessageType.ADD_FRIEND_REQUEST_MESSAGE, MessageSuperType.FRIENDS);
        this.username = username;
        this.userID = userID;
        this.friendUsername = friendUsername;
    }

    public String getUsername() {
        return username;
    }

    public String getUserID() {
        return userID;
    }

    public String getFriendUsername() {
        return friendUsername;
    }
}
