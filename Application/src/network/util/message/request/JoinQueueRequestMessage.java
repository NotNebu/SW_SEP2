package network.util.message.request;

import model.user.UserDTO;
import network.util.message.MessageBase;
import network.util.message.MessageSuperType;
import network.util.message.MessageType;

public class JoinQueueRequestMessage extends MessageBase {

    private UserDTO user;

    public JoinQueueRequestMessage(UserDTO user) {
        super(MessageType.JOIN_QUEUE_REQUEST_MESSAGE, MessageSuperType.GAME);
        this.user = user;
    }

    public UserDTO getUserDTO() {
        return user;
    }
}
