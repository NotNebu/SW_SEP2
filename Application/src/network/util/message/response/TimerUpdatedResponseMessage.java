package network.util.message.response;

import network.util.message.MessageBase;
import network.util.message.MessageSuperType;
import network.util.message.MessageType;

public class TimerUpdatedResponseMessage extends MessageBase {

    private int remainingSeconds;

    public TimerUpdatedResponseMessage(int remainingSeconds) {
        super(MessageType.TIMER_UPDATED_RESPONSE_MESSAGE, MessageSuperType.GAME);
        this.remainingSeconds = remainingSeconds;
    }

    public int getRemainingSeconds() {
        return remainingSeconds;
    }
}
