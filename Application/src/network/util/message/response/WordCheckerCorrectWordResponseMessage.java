package network.util.message.response;

import network.util.message.MessageBase;
import network.util.message.MessageSuperType;
import network.util.message.MessageType;

public class WordCheckerCorrectWordResponseMessage extends MessageBase {

    public WordCheckerCorrectWordResponseMessage() {
        super(MessageType.WORD_CHECKER_CORRECT_WORD_RESPONSE_MESSAGE, MessageSuperType.GAME);
    }
}
