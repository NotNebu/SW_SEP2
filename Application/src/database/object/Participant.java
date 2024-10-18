package database.object;

public class Participant {

    private int user_id, match_id;
    private String match_result;
    private float elo_change, avg_WPM, accuracy;

    public Participant(int user_id, int match_id, String match_result, float elo_change, float avg_WPM, float accuracy) {
        this.user_id = user_id;
        this.match_id = match_id;
        this.match_result = match_result;
        this.elo_change = elo_change;
        this.avg_WPM = avg_WPM;
        this.accuracy = accuracy;
    }

    public int getUserId() {
        return user_id;
    }

    public int getMatchId() {
        return match_id;
    }

    public String getMatchResult() {
        return match_result;
    }

    public float getEloChange() {
        return elo_change;
    }

    public float getAvgWPM() {
        return avg_WPM;
    }

    public float getAccuracy() {
        return accuracy;
    }


}
