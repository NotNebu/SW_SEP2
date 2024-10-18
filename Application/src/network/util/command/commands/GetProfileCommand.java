package network.util.command.commands;

import database.DAO.MatchDAO;
import database.DAO.ParticipantDAO;
import database.DAO.StatsDAO;
import database.DAO.UsersDAO;
import database.object.Match;
import database.object.Participant;
import database.object.Stats;
import database.object.User;
import network.server.socket.SocketServer;
import network.util.command.BaseCommand;
import network.util.message.request.GetProfileRequestMessage;
import network.util.message.response.GetProfileResponseMessage;
import network.util.message.response.GetProfileStatsForLineChartResponseMessage;

import java.sql.SQLException;
import java.util.ArrayList;

public class GetProfileCommand extends BaseCommand {

    private GetProfileRequestMessage message;
    private UsersDAO usersDAO;
    private StatsDAO statsDAO;
    private MatchDAO matchDAO;
    private ParticipantDAO participantDAO;

    public GetProfileCommand(SocketServer.ClientHandler clientHandler, GetProfileRequestMessage message) {
        super(clientHandler);
        this.message = message;
        this.usersDAO = clientHandler.getDataManager().getUsersDAO();
        this.statsDAO = clientHandler.getDataManager().getStatsDAO();
        this.matchDAO = clientHandler.getDataManager().getMatchDAO();
        this.participantDAO = clientHandler.getDataManager().getParticipantDAO();
    }

    @Override
    public void execute() {
        User user;
        Stats stats;
        try {
            user = usersDAO.readById(Integer.parseInt(message.getUserID()));
            stats = statsDAO.readByUserID(Integer.parseInt(message.getUserID()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(user == null || stats == null) {
            clientHandler.sendMessage(new GetProfileResponseMessage("error", 0,
                    0, 0, 0, 0));
        } else{
            clientHandler.sendMessage(new GetProfileResponseMessage(user.getUsername(), stats.getElo(),
                    stats.getAvg_WPM(), stats.getAvg_Accuracy(), stats.getTotal_matches(), stats.getWinRate()));
        }

        ArrayList<Match> matches;
        try {
            matches = matchDAO.readByUserId(Integer.parseInt(message.getUserID()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }if(matches.isEmpty()){
            clientHandler.sendMessage(new GetProfileStatsForLineChartResponseMessage(new ArrayList<>(), 0));
        }else{
            ArrayList<Float> eloRankingData = new ArrayList<>();
            Participant participant;
            for(Match match : matches){
                try {
                    participant = participantDAO.readById(Integer.parseInt(message.getUserID()), match.getMatch_id());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                eloRankingData.add(participant.getEloChange());
            }
            clientHandler.sendMessage(new GetProfileStatsForLineChartResponseMessage(eloRankingData, matches.size()));
        }


    }
}
