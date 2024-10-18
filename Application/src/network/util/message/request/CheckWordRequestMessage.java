package network.util.message.request;

import model.user.UserDTO;
import network.util.message.MessageBase;
import network.util.message.MessageSuperType;
import network.util.message.MessageType;

public class CheckWordRequestMessage extends MessageBase {

    private UserDTO user;
    private String word;

    public CheckWordRequestMessage(UserDTO user, String word) {
        super(MessageType.CHECK_WORD_REQUEST_MESSAGE, MessageSuperType.GAME);
        this.user = user;
        this.word = word;
    }

    public UserDTO getUser() {
        return user;
    }

    public String getWord() {
        return word;
    }
}
