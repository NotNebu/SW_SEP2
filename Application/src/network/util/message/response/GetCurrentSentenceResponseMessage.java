package network.util.message.response;

import network.util.message.MessageBase;
import network.util.message.MessageSuperType;
import network.util.message.MessageType;

public class GetCurrentSentenceResponseMessage extends MessageBase {

    private String sentence;

    public GetCurrentSentenceResponseMessage(String sentence) {
        super(MessageType.GET_CURRENT_SENTENCE_RESPONSE_MESSAGE, MessageSuperType.GAME);
        this.sentence = sentence;
    }

    public String getSentence() {
        return sentence;
    }
}
