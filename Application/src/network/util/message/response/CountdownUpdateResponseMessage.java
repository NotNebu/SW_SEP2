package network.util.message.response;

import network.util.message.MessageBase;
import network.util.message.MessageSuperType;
import network.util.message.MessageType;

public class CountdownUpdateResponseMessage extends MessageBase {

    private int remainingSeconds;

    public CountdownUpdateResponseMessage(int remainingSeconds) {
        super(MessageType.COUNTDOWN_UPDATE_RESPONSE_MESSAGE, MessageSuperType.GAME);
        this.remainingSeconds = remainingSeconds;
    }

    public int getRemainingSeconds() {
        return remainingSeconds;
    }
}
