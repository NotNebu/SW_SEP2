package network.util.message.response;

import network.util.message.MessageBase;
import network.util.message.MessageSuperType;
import network.util.message.MessageType;

public class LoginResponseMessage extends MessageBase {

    private boolean isSuccessful;
    private String username;
    private String userId;

    public LoginResponseMessage(boolean isSuccessful, String username, String userId) {
        super(MessageType.LOGIN_RESPONSE_MESSAGE, MessageSuperType.LOGIN);
        this.isSuccessful = isSuccessful;
        this.username = username;
        this.userId = userId;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public String getUsername() {
        return username;
    }

    public String getUserId() {
        return userId;
    }
}
