package network.util.command.commands;

import database.DAO.ParticipantDAO;
import database.DAO.StatsDAO;
import database.DAO.UsersDAO;
import database.object.User;
import network.server.socket.SocketServer;
import network.util.command.BaseCommand;
import network.util.message.request.SignUpRequestMessage;
import network.util.message.response.SignUpResponseMessage;

import java.sql.SQLException;

public class SignUpCommand extends BaseCommand {

    private SignUpRequestMessage message;
    private UsersDAO usersDAO;
    private StatsDAO statsDAO;

    public SignUpCommand(SocketServer.ClientHandler clientHandler, SignUpRequestMessage message) {
        super(clientHandler);
        this.usersDAO = clientHandler.getDataManager().getUsersDAO();
        this.statsDAO = clientHandler.getDataManager().getStatsDAO();
        this.message = message;
    }

    @Override
    public void execute() {
       User user = null;
        try {
            user = usersDAO.create(message.getUsername(), message.getPassword(), message.getProfileName());
            statsDAO.create(user.getId(), 1000f, 0f, 0f, 0, 0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(user == null) {
            clientHandler.sendMessage(new SignUpResponseMessage(false));
        } else {
            clientHandler.sendMessage(new SignUpResponseMessage(true));
        }
    }

}
