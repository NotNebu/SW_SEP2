package network.util.command.commands;

import database.DAO.FriendShipDAO;
import database.DAO.StatsDAO;
import database.DAO.UsersDAO;
import database.object.FriendShip;
import database.object.User;
import network.server.socket.SocketServer;
import network.util.command.BaseCommand;
import network.util.message.request.GetUsersFriendsRequestMessage;
import network.util.message.response.GetUsersFriendsResponseMessage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetUsersFriendsCommand extends BaseCommand {

    private GetUsersFriendsRequestMessage message;
    private UsersDAO usersDAO;
    private FriendShipDAO friendShipDAO;
    private StatsDAO statsDAO;

    public GetUsersFriendsCommand(SocketServer.ClientHandler clientHandler, GetUsersFriendsRequestMessage message) {
        super(clientHandler);
        this.message = message;
        this.usersDAO = clientHandler.getDataManager().getUsersDAO();
        this.friendShipDAO = clientHandler.getDataManager().getFriendShipDAO();
        this.statsDAO = clientHandler.getDataManager().getStatsDAO();
    }

    @Override
    public void execute() {
        User user;
        try {
            user = usersDAO.readById(Integer.parseInt(message.getID()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        List<FriendShip> friendShips;
        try {
            friendShips = friendShipDAO.readByUserID(user.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (friendShips.isEmpty()) {
            clientHandler.sendMessage(new GetUsersFriendsResponseMessage(new ArrayList<>(), new ArrayList<>()));
        } else {
            List<String> friends = new ArrayList<>();
            List<Integer> elos = new ArrayList<>();
            for (FriendShip friendShip : friendShips) {
                User friend;
                try {
                    friend = usersDAO.readById(friendShip.getFriend_id());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                friends.add(friend.getUsername());
                try {
                    elos.add((int) statsDAO.readByUserID(friend.getId()).getElo());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            clientHandler.sendMessage(new GetUsersFriendsResponseMessage(friends, elos));
        }
    }

}
