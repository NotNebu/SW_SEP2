package network.util.message.response;

import network.util.message.MessageBase;
import network.util.message.MessageSuperType;
import network.util.message.MessageType;

public class CurrentWordResponseMessage extends MessageBase {

    private String currentWord;

    public CurrentWordResponseMessage(String currentWord) {
        super(MessageType.CURRENT_WORD_RESPONSE_MESSAGE, MessageSuperType.GAME);
        this.currentWord = currentWord;
    }

    public String getCurrentWord() {
        return currentWord;
    }
}
