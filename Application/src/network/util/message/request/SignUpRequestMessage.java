package network.util.message.request;

import network.util.message.MessageBase;
import network.util.message.MessageSuperType;
import network.util.message.MessageType;

public class SignUpRequestMessage extends MessageBase {

    private String username;
    private String password;
    private String profileName;

    public SignUpRequestMessage(String username, String password, String profileName) {
        super(MessageType.SIGN_UP_REQUEST_MESSAGE, MessageSuperType.SIGN_UP);
        this.username = username;
        this.password = password;
        this.profileName = profileName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getProfileName() {
        return profileName;
    }
}
