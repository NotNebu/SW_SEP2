package network.util.message.request;

import model.user.UserDTO;
import network.util.message.MessageBase;
import network.util.message.MessageSuperType;
import network.util.message.MessageType;

public class GetCurrentSentenceRequestMessage extends MessageBase {

    private UserDTO user;

    public GetCurrentSentenceRequestMessage(UserDTO user) {
        super(MessageType.GET_CURRENT_SENTENCE_REQUEST_MESSAGE, MessageSuperType.GAME);
        this.user = user;
    }

    public UserDTO getUser() {
        return user;
    }
}
