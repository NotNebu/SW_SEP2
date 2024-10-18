package network.util.command.commands;

import network.server.socket.SocketServer;
import network.util.command.BaseCommand;
import network.util.message.request.StartGameRequestMessage;

// Command to start a game and assign a user to it
public class StartGameCommand extends BaseCommand {
    private StartGameRequestMessage message;

    // Initializes with the client handler and the request message
    public StartGameCommand(SocketServer.ClientHandler clientHandler, StartGameRequestMessage message) {
        super(clientHandler);
        this.message = message;
    }

    // Executes the command to start the game and add the user
    @Override
    public void execute() {
        String channelId = SocketServer.getGameManager().getFreeGameID();
        SocketServer.subscribeToGameManager(channelId, message.getUserDTO().getId(), clientHandler);
        SocketServer.getGameManager().getGameByUniqueID(channelId)
            .addUser(message.getUserDTO());
    }
}
