package network.util.message.response;

import network.util.message.MessageBase;
import network.util.message.MessageSuperType;
import network.util.message.MessageType;

public class EloChangeResponseMessage extends MessageBase {

    private float eloChange;
    public EloChangeResponseMessage(float eloChange) {
        super(MessageType.ELO_CHANGE_RESPONSE_MESSAGE, MessageSuperType.GAME);
        this.eloChange = eloChange;
    }

    public float getEloChange() {
        return eloChange;
    }
}
