package network.util.message.response;

import network.util.message.MessageBase;
import network.util.message.MessageSuperType;
import network.util.message.MessageType;

public class FriendsUpdatedResponseMessage extends MessageBase {

    public FriendsUpdatedResponseMessage() {
        super(MessageType.FRIENDS_UPDATED_RESPONSE_MESSAGE, MessageSuperType.LOBBY);
    }
}
