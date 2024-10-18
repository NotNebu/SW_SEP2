package network.util.message.request;

import network.util.message.MessageBase;
import network.util.message.MessageSuperType;
import network.util.message.MessageType;

public class ProfileNameExistsRequestMessage extends MessageBase {

    private String profileName;

    public ProfileNameExistsRequestMessage(String profileName) {
        super(MessageType.PROFILE_NAME_EXISTS_REQUEST_MESSAGE, MessageSuperType.SIGN_UP);
        this.profileName = profileName;
    }

    public String getProfileName() {
        return profileName;
    }
}
