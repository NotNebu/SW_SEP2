package network.util.message.request;

import model.user.UserDTO;
import network.util.message.MessageBase;
import network.util.message.MessageSuperType;
import network.util.message.MessageType;

public class LeaveQueueRequestMessage extends MessageBase {

    private UserDTO user;

    public LeaveQueueRequestMessage(UserDTO user) {
        super(MessageType.LEAVE_QUEUE_REQUEST_MESSAGE, MessageSuperType.GAME);
        this.user = user;
    }

    public UserDTO getUser() {
        return user;
    }
}
