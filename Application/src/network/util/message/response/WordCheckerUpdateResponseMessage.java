package network.util.message.response;

import network.util.message.MessageBase;
import network.util.message.MessageSuperType;
import network.util.message.MessageType;

public class WordCheckerUpdateResponseMessage extends MessageBase {

    private int wordLength;

    public WordCheckerUpdateResponseMessage(int wordLength) {
        super(MessageType.WORD_CHECKER_UPDATE_RESPONSE_MESSAGE, MessageSuperType.GAME);
        this.wordLength = wordLength;
    }

    public int getWordLength() {
        return wordLength;
    }
}
