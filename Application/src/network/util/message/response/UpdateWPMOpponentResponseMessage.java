package network.util.message.response;

import network.util.message.MessageBase;
import network.util.message.MessageSuperType;
import network.util.message.MessageType;

public class UpdateWPMOpponentResponseMessage extends MessageBase {

    private double wpm;

    public UpdateWPMOpponentResponseMessage(double wpm) {
        super(MessageType.UPDATE_WPM_OPPONENT_RESPONSE_MESSAGE, MessageSuperType.GAME);
        this.wpm = wpm;
    }

    public double getWpm() {
        return wpm;
    }
}
