package network.server.socket;

import database.DAOManager;
import model.gamelogic.GameManager;
import network.server.Server;
import network.util.command.CommandHandler;
import network.util.command.ICommand;
import network.util.message.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// Listens for client connections and delegates them to ClientHandler for processing
public class SocketServer implements Server {
    private final ServerSocket serverSocket;
    private DAOManager dataManager;
    private static final Map<String, Map<String, ClientHandler>> channels = new HashMap<>();
    private static GameManager gameManager = null;


    public SocketServer(int port) throws SQLException {
        // Initializes the server on the specified port
        try {
            serverSocket = new ServerSocket(port);
            CommandHandler commandHandler = new CommandHandler();
            dataManager = new DAOManager();
            gameManager = new GameManager(dataManager);
            System.out.println("Server started. Listening on port " + port);

            while (true) {
                // Accepts a client connection and handles communication through a new ClientHandler instance
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected");
                new ClientHandler(clientSocket, commandHandler, dataManager).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Subscribes a client to the game manager's channel
    public static synchronized void subscribeToGameManager(String channelId, String userId, ClientHandler clientHandler) {
        channels.computeIfAbsent(channelId, k -> new HashMap<>()).put(userId, clientHandler);
    }

    // Unsubscribes a client from the game manager's channel
    public static synchronized void unsubscribeFromGameManager(String channelId, String userId) {
        Map<String, ClientHandler> userMap = channels.get(channelId);
        if (userMap != null) {
            userMap.remove(userId);
        }
    }

    // Broadcasts a message to all clients in a specific channel
    public static synchronized void broadcastMessageInChannel(String channelId, Message message) {
        Map<String, ClientHandler> userMap = channels.get(channelId);
        if (userMap != null) {
            for (ClientHandler clientHandler : userMap.values()) {
                clientHandler.sendMessage(message);
            }
        }
    }

    // Sends a message to a specific user within a channel
    public static synchronized void broadcastMessageToUser(String channelId, String userId, Message message) {
        Map<String, ClientHandler> userMap = channels.get(channelId);
        if (userMap != null) {
            ClientHandler clientHandler = userMap.get(userId);
            if (clientHandler != null) {
                clientHandler.sendMessage(message);
            }
        }
    }

    // Retrieves the game manager instance
    public static GameManager getGameManager() {
        return gameManager;
    }

    // Handles individual client connections and processes incoming commands
    public class ClientHandler extends Thread {
        private final Socket clientSocket;
        private final CommandHandler commandHandler;
        private final DAOManager dataManager;
        private final ObjectOutputStream objectOutputStream;
        private final ObjectInputStream objectInputStream;
        private final Lock receiveLock = new ReentrantLock();
        private final Lock sendLock = new ReentrantLock();

        // Initializes the client handler with command handler and socket streams
        public ClientHandler(Socket socket, CommandHandler commandHandler, DAOManager dataManager) {
            this.clientSocket = socket;
            this.commandHandler = commandHandler;
            this.dataManager = dataManager;
            try {
                objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // Continuously listens for client messages and processes commands
        @Override
        public void run() {
            try {
                while (true) {
                    // Receive and execute commands from the client
                    Message message = receiveMessage();
                    ICommand command = commandHandler.getCommand(message, this);
                    command.execute();
                }
            } catch (IOException e) {
                System.out.println("Client disconnected unexpectedly");
                cleanup();
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // Cleans up resources associated with the client handler
        private void cleanup() {
            System.out.println("Cleaning up resources");
        }

        // Sends a response message to the client
        public void sendMessage(Message responseMessage) {
            synchronized (sendLock) {
                try {
                    if (!clientSocket.isClosed()) {
                        objectOutputStream.writeObject(responseMessage);
                        objectOutputStream.flush();
                    }
                } catch (IOException e) {
                    System.out.println("Error sending message to client: " + e.getMessage());
                }
            }
        }

        // Receives a message from the client
        public Message receiveMessage() throws IOException {
            synchronized (receiveLock) {
                try {
                    return (Message) objectInputStream.readObject();
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        public DAOManager getDataManager() {
            return dataManager;
        }
    }

    public static void main(String[] args) throws SQLException {
        // Entry point to start the server on the specified port
        new SocketServer(2910);
    }
}
