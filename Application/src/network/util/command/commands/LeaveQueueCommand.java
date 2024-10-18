package network.util.command.commands;

import network.server.socket.SocketServer;
import network.util.command.BaseCommand;
import network.util.message.request.LeaveQueueRequestMessage;

// Command to remove a user from the queue
public class LeaveQueueCommand extends BaseCommand {
    private LeaveQueueRequestMessage message;

    // Initializes the command with the client handler and request message
    public LeaveQueueCommand(SocketServer.ClientHandler clientHandler, LeaveQueueRequestMessage message) {
        super(clientHandler);
        this.message = message;
    }

    // Executes the command to remove the user from the queue
    @Override
    public void execute() {
        SocketServer.getGameManager().removeUserFromQueue(message.getUser(), clientHandler);
    }
}
