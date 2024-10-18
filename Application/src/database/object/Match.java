package database.object;

public class Match {
    private int match_id;
    private String time_stamp;

    public Match(int match_id, String time_stamp) {
        this.match_id = match_id;
        this.time_stamp = time_stamp;
    }

    public int getMatch_id() {
        return match_id;
    }

    public String getTime_stamp() {
        return time_stamp;
    }
}
