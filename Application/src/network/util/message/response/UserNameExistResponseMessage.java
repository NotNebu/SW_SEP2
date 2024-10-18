package network.util.message.response;

import network.util.message.MessageBase;
import network.util.message.MessageSuperType;
import network.util.message.MessageType;

public class UserNameExistResponseMessage extends MessageBase {

    private boolean exists;

    public UserNameExistResponseMessage(boolean exists) {
        super(MessageType.USER_NAME_EXIST_RESPONSE_MESSAGE, MessageSuperType.SIGN_UP);
        this.exists = exists;
    }

    public boolean exists() {
        return exists;
    }
}
