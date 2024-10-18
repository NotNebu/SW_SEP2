package network.util.message.response;

import network.util.message.MessageBase;
import network.util.message.MessageSuperType;
import network.util.message.MessageType;

public class GameFinishedFinalWPMResponseMessage extends MessageBase {

    private double wpm;

    public GameFinishedFinalWPMResponseMessage(double wpm) {
        super(MessageType.GAME_FINISHED_FINAL_WPM_RESPONSE_MESSAGE, MessageSuperType.GAME);
        this.wpm = wpm;
    }

    public double getWpm() {
        return wpm;
    }

}
