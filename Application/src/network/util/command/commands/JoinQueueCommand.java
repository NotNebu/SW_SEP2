package network.util.command.commands;

import network.server.socket.SocketServer;
import network.util.command.BaseCommand;
import network.util.message.request.JoinQueueRequestMessage;

// Command to add a user to the game queue
public class JoinQueueCommand extends BaseCommand {
    private JoinQueueRequestMessage message;

    // Initializes with the client handler and the request message
    public JoinQueueCommand(SocketServer.ClientHandler clientHandler, JoinQueueRequestMessage message) {
        super(clientHandler);
        this.message = message;
    }

    // Executes the command to add the user to the queue
    @Override
    public void execute() {
        SocketServer.getGameManager().addUserToQueue(message.getUserDTO(), clientHandler);
    }
}
