package network.util.message.request;

import network.util.message.MessageBase;
import network.util.message.MessageSuperType;
import network.util.message.MessageType;

public class UserNameExistRequestMessage extends MessageBase {

    private String username;

    public UserNameExistRequestMessage(String username) {
        super(MessageType.USER_NAME_EXIST_REQUEST_MESSAGE, MessageSuperType.SIGN_UP);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
