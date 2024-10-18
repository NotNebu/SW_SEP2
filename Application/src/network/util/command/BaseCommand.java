package network.util.command;

import network.server.socket.SocketServer;

// Base command class providing shared functionality for all commands
public abstract class BaseCommand implements ICommand {
    protected SocketServer.ClientHandler clientHandler;

    // Initializes with the provided client handler
    public BaseCommand(SocketServer.ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    // Abstract method to be implemented by concrete commands
    @Override
    public abstract void execute();
}
