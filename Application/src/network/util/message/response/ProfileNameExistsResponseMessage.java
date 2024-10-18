package network.util.message.response;

import network.util.message.MessageBase;
import network.util.message.MessageSuperType;
import network.util.message.MessageType;

public class ProfileNameExistsResponseMessage extends MessageBase {

    private boolean exists;

    public ProfileNameExistsResponseMessage(boolean exists) {
        super(MessageType.PROFILE_NAME_EXISTS_RESPONSE_MESSAGE, MessageSuperType.SIGN_UP);
        this.exists = exists;
    }

    public boolean getExists() {
        return exists;
    }
}
