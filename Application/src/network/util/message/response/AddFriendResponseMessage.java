package network.util.message.response;

import network.util.message.MessageBase;
import network.util.message.MessageSuperType;
import network.util.message.MessageType;

public class AddFriendResponseMessage extends MessageBase {

    private boolean success;

    public AddFriendResponseMessage(boolean success) {
        super(MessageType.ADD_FRIEND_RESPONSE_MESSAGE, MessageSuperType.FRIENDS);
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}
