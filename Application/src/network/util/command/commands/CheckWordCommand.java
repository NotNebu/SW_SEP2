package network.util.command.commands;

import network.server.socket.SocketServer;
import network.util.command.BaseCommand;
import network.util.message.request.CheckWordRequestMessage;

// Command to check a word input for a particular user
public class CheckWordCommand extends BaseCommand {
    private CheckWordRequestMessage message;

    // Initializes with the client handler and the request message
    public CheckWordCommand(SocketServer.ClientHandler clientHandler, CheckWordRequestMessage message) {
        super(clientHandler);
        this.message = message;
    }

    // Executes the command to check a word
    @Override
    public void execute() {
        SocketServer.getGameManager().getGameByUserDTO(message.getUser())
            .checkWord(message.getWord(), message.getUser().getId());
    }
}
