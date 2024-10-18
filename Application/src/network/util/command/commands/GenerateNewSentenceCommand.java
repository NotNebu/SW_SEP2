package network.util.command.commands;

import network.server.socket.SocketServer;
import network.util.command.BaseCommand;
import network.util.message.request.GenerateNewSentenceRequestMessage;

// Command to generate a new sentence for a user
public class GenerateNewSentenceCommand extends BaseCommand {
    private GenerateNewSentenceRequestMessage message;

    // Initializes with the client handler and the request message
    public GenerateNewSentenceCommand(SocketServer.ClientHandler clientHandler, GenerateNewSentenceRequestMessage message) {
        super(clientHandler);
        this.message = message;
    }

    // Executes the command to generate a new sentence
    @Override
    public void execute() {
        SocketServer.getGameManager().getGameByUserDTO(message.getUser())
            .generateNewSentenceForPlayer(message.getUser().getId());
    }
}
