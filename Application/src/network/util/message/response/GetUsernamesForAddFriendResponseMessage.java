package network.util.message.response;

import database.object.User;
import network.util.message.MessageBase;
import network.util.message.MessageSuperType;
import network.util.message.MessageType;

import java.util.ArrayList;
import java.util.List;

public class GetUsernamesForAddFriendResponseMessage extends MessageBase {

    private ArrayList<String> usernames;

    public GetUsernamesForAddFriendResponseMessage(ArrayList<String> usernames) {
        super(MessageType.GET_USERNAMES_FOR_FRIEND_RESPONSE_MESSAGE, MessageSuperType.FRIENDS);
        this.usernames = usernames;
    }

    public List<String> getUsernames() {
        return usernames;
    }
}
