package network.util.message.response;

import network.util.message.MessageBase;
import network.util.message.MessageSuperType;
import network.util.message.MessageType;

import java.util.ArrayList;

public class GetProfileStatsForLineChartResponseMessage extends MessageBase {

    private ArrayList<Float> eloRankingData;
    private int totalMatches;

    public GetProfileStatsForLineChartResponseMessage(ArrayList<Float> eloRankingData, int totalMatches) {
        super(MessageType.GET_PROFILE_STATS_FOR_LINE_CHART_RESPONSE_MESSAGE, MessageSuperType.PROFILE);
        this.eloRankingData = eloRankingData;
        this.totalMatches = totalMatches;
    }

    public ArrayList<Float> getEloRankingData() {
        return eloRankingData;
    }

    public int getTotalMatches() {
        return totalMatches;
    }
}
