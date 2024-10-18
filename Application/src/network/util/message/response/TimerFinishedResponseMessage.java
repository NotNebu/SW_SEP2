package network.util.message.response;

import network.util.message.MessageBase;
import network.util.message.MessageSuperType;
import network.util.message.MessageType;

public class TimerFinishedResponseMessage extends MessageBase {

    public TimerFinishedResponseMessage() {
        super(MessageType.TIMER_FINISHED_RESPONSE_MESSAGE, MessageSuperType.GAME);
    }
}
