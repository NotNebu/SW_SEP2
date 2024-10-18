package network.util.message.request;

import network.util.message.MessageBase;
import network.util.message.MessageSuperType;
import network.util.message.MessageType;

public class GetProfileRequestMessage extends MessageBase {

    private String userID;

    public GetProfileRequestMessage(String userID) {
        super(MessageType.GET_PROFILE_REQUEST_MESSAGE, MessageSuperType.PROFILE);
        this.userID = userID;
    }

    public String getUserID() {
        return userID;
    }
}
