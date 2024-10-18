package network.util.message.response;

import network.util.message.MessageBase;
import network.util.message.MessageSuperType;
import network.util.message.MessageType;

public class GameResultResponseMessage extends MessageBase {

    private boolean isAWin;

    public GameResultResponseMessage(boolean isAWin) {
        super(MessageType.GAME_RESULT_RESPONSE_MESSAGE, MessageSuperType.GAME);
        this.isAWin = isAWin;
    }

    public boolean isAWin() {
        return isAWin;
    }
}
