package network.util.message.response;

import network.util.message.MessageBase;
import network.util.message.MessageSuperType;
import network.util.message.MessageType;

public class SentenceIsDoneResponseMessage extends MessageBase {

    public SentenceIsDoneResponseMessage() {
        super(MessageType.SENTENCE_IS_DONE_RESPONSE_MESSAGE, MessageSuperType.GAME);
    }
}
