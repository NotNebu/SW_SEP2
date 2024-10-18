package model.gamelogic.evaluation;

import database.DAO.StatsDAO;
import database.DAOManager;
import database.object.Stats;

import java.sql.SQLException;

public class EvaluatePlayer {
    private final PlayerStats playerOneStats;
    private final PlayerStats playerTwoStats;
    private final IGameOutcomeEvaluator gameOutcomeEvaluator;
    private final IEloCalculator eloCalculator;
    private EvaluatePlayerObserver observer;
    private DAOManager daoManager;

    // Coordinates the evaluation process and handles player stats, ELO calculation, and observer notifications.

    public EvaluatePlayer(String playerOneID, String playerTwoID, DAOManager daoManager) {
        this.daoManager = daoManager;


        float playerOneElo, playerTwoElo;

        if(playerOneID.equalsIgnoreCase("test") && playerTwoID.equalsIgnoreCase("test")){
            playerOneElo = 1000;
            playerTwoElo = 1000;
        } else {
            try {
                StatsDAO statsDAO = this.daoManager.getStatsDAO();
                Stats playerOne = statsDAO.readByUserID(Integer.parseInt(playerOneID));
                Stats playerTwo = statsDAO.readByUserID(Integer.parseInt(playerTwoID));
                playerOneElo = playerOne.getElo();
                playerTwoElo = playerTwo.getElo();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }


        this.playerOneStats = new PlayerStats(playerOneElo);
        this.playerTwoStats = new PlayerStats(playerTwoElo);
        this.gameOutcomeEvaluator = new StandardGameOutcomeEvaluator();
        this.eloCalculator = new StandardEloCalculator();
    }


    public void setPlayerOneStats(double playerOneWPM, double playerOneAccuracy) {
        playerOneStats.updateStats(playerOneWPM, playerOneAccuracy);
    }

    public void setPlayerTwoStats(double playerTwoWPM, double playerTwoAccuracy) {
        playerTwoStats.updateStats(playerTwoWPM, playerTwoAccuracy);
    }

    public void calculateElo() {
        GameOutcome outcome = gameOutcomeEvaluator.evaluate(playerOneStats, playerTwoStats);
        float playerOneEloChange, playerTwoEloChange;

        if (outcome.isTieGame()) {
            playerOneEloChange = 10f;
            playerTwoEloChange = 10f;
            if(observer != null){
                observer.tieGame();
            }
        } else {
            playerOneEloChange = eloCalculator.calculate(playerOneStats, playerTwoStats.getElo(), outcome.isPlayerOneWon());
            playerTwoEloChange = eloCalculator.calculate(playerTwoStats, playerOneStats.getElo(), !outcome.isPlayerOneWon());

            if (observer != null) {
                if (outcome.isPlayerOneWon()) {
                    observer.playerOneWon();
                } else {
                    observer.playerTwoWon();
                }
            }
        }

        playerOneStats.updateElo(playerOneEloChange);
        playerTwoStats.updateElo(playerTwoEloChange);

        if (observer != null) {
            observer.playerOneEloChange(playerOneEloChange);
            observer.playerTwoEloChange(playerTwoEloChange);
        }
    }

    public void removeObserver() {
        this.observer = null;
    }

    public void setObserver(EvaluatePlayerObserver observer) {
        this.observer = observer;
    }

}
