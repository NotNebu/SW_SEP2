package network.util.message.request;

import model.user.UserDTO;
import network.util.message.MessageBase;
import network.util.message.MessageSuperType;
import network.util.message.MessageType;

public class StartGameRequestMessage extends MessageBase {

    private UserDTO userDTO;

    public StartGameRequestMessage(UserDTO userDTO) {
        super(MessageType.START_GAME_REQUEST_MESSAGE, MessageSuperType.GAME);
        this.userDTO = userDTO;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }
}
