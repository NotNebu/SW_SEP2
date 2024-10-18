package network.util.message.response;

import network.util.message.MessageBase;
import network.util.message.MessageSuperType;
import network.util.message.MessageType;

public class SignUpResponseMessage extends MessageBase {

    private boolean success;

    public SignUpResponseMessage(boolean success) {
        super(MessageType.SIGN_UP_RESPONSE_MESSAGE, MessageSuperType.SIGN_UP);
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}
