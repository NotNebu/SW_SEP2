package network.util.message.response;

import network.util.message.MessageBase;
import network.util.message.MessageSuperType;
import network.util.message.MessageType;

public class AllPlayerJoinedResponseMessage extends MessageBase {

    public AllPlayerJoinedResponseMessage() {
        super(MessageType.ALL_PLAYER_JOINED_RESPONSE_MESSAGE, MessageSuperType.GAME);
    }
}
