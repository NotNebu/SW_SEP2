package network.util.message.request;

import network.util.message.MessageBase;
import network.util.message.MessageSuperType;
import network.util.message.MessageType;

public class LoginRequestMessage extends MessageBase {

    private String username;
    private String password;

    public LoginRequestMessage(String username, String password) {
        super(MessageType.LOGIN_REQUEST_MESSAGE, MessageSuperType.LOGIN);
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
