package database.object;

public class Stats {

    private int stats_id;
    private int user_id;
    private float elo, avg_WPM, avg_Accuracy, winRate;
    private int total_matches;

    public Stats(int stats_id, int user_id, float elo, float avg_WPM, float avg_Accuracy, float winRate, int total_matches) {
        this.stats_id = stats_id;
        this.user_id = user_id;
        this.elo = elo;
        this.avg_WPM = avg_WPM;
        this.avg_Accuracy = avg_Accuracy;
        this.winRate = winRate;
        this.total_matches = total_matches;
    }

    public int getStats_id() {
        return stats_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public float getElo() {
        return elo;
    }

    public float getAvg_WPM() {
        return avg_WPM;
    }

    public float getAvg_Accuracy() {
        return avg_Accuracy;
    }

    public float getWinRate() {
        return winRate;
    }

    public int getTotal_matches() {
        return total_matches;
    }
}
