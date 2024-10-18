package network.util.message;

public interface Message {
    MessageType getType();
    MessageSuperType getSuperType();
}
