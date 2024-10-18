package network.util.message.response;

import network.util.message.MessageBase;
import network.util.message.MessageSuperType;
import network.util.message.MessageType;

public class OpponentNameResponseMessage extends MessageBase {

    private String opponentName;

    public OpponentNameResponseMessage(String opponentName) {
        super(MessageType.OPPONENT_NAME_RESPONSE_MESSAGE, MessageSuperType.GAME);
        this.opponentName = opponentName;
    }

    public String getOpponentName() {
        return opponentName;
    }
}
