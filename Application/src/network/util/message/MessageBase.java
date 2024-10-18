package network.util.message;

import java.io.Serializable;

public abstract class MessageBase implements Message, Serializable {

    private MessageType type;

    private MessageSuperType superType;

    public MessageBase(MessageType type, MessageSuperType superType) {
        this.type = type;
        this.superType = superType;
    }

    public MessageType getType() {
        return type;
    }

    public MessageSuperType getSuperType() {
        return superType;
    }

}
