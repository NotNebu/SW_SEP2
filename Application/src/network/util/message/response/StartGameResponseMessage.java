package network.util.message.response;

import network.util.message.MessageBase;
import network.util.message.MessageSuperType;
import network.util.message.MessageType;

public class StartGameResponseMessage extends MessageBase {

    public StartGameResponseMessage() {
        super(MessageType.START_GAME_RESPONSE_MESSAGE, MessageSuperType.GAME);
    }

}
