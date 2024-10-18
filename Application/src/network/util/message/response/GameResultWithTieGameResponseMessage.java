package network.util.message.response;

import network.util.message.MessageBase;
import network.util.message.MessageSuperType;
import network.util.message.MessageType;

public class GameResultWithTieGameResponseMessage extends MessageBase {

    public GameResultWithTieGameResponseMessage() {
        super(MessageType.GAME_RESULT_WITH_TIE_GAME_RESPONSE_MESSAGE, MessageSuperType.GAME);
    }
}
