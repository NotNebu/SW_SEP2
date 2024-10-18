package network.util.message.response;

import network.util.message.MessageBase;
import network.util.message.MessageSuperType;
import network.util.message.MessageType;

public class CountdownFinishedResponseMessage extends MessageBase {

    public CountdownFinishedResponseMessage() {
        super(MessageType.COUNTDOWN_FINISHED_RESPONSE_MESSAGE, MessageSuperType.GAME);
    }
}
