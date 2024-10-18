package network.util.command.commands;

import network.server.socket.SocketServer;
import network.util.command.BaseCommand;
import network.util.message.request.GetCurrentSentenceRequestMessage;

// Command to retrieve the current sentence for a user
public class GetCurrentSentenceCommand extends BaseCommand {
    private GetCurrentSentenceRequestMessage message;

    // Initializes with the client handler and the request message
    public GetCurrentSentenceCommand(SocketServer.ClientHandler clientHandler, GetCurrentSentenceRequestMessage message) {
        super(clientHandler);
        this.message = message;
    }

    // Executes the command to get the current sentence for the player
    @Override
    public void execute() {
        SocketServer.getGameManager().getGameByUserDTO(message.getUser())
            .getCurrentSentenceForPlayer(message.getUser().getId());
    }
}
