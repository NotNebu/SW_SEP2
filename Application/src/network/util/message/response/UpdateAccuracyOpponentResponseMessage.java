package network.util.message.response;

import network.util.message.MessageBase;
import network.util.message.MessageSuperType;
import network.util.message.MessageType;

public class UpdateAccuracyOpponentResponseMessage extends MessageBase {

    private double accuracy;

    public UpdateAccuracyOpponentResponseMessage(double accuracy) {
        super(MessageType.UPDATE_ACCURACY_OPPONENT_RESPONSE_MESSAGE, MessageSuperType.GAME);
        this.accuracy = accuracy;
    }

    public double getAccuracy() {
        return accuracy;
    }
}
