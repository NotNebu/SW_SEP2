package network.util.message.response;

import network.util.message.MessageBase;
import network.util.message.MessageSuperType;
import network.util.message.MessageType;

public class GetProfileResponseMessage extends MessageBase {

    private String profileName;
    private float eloRanking;
    private float wpm;
    private float accuracy;
    private int totalMatches;
    private float winRate;

    public GetProfileResponseMessage(String profileName, float eloRanking, float wpm, float accuracy, int totalMatches, float winRate) {
        super(MessageType.GET_PROFILE_RESPONSE_MESSAGE, MessageSuperType.PROFILE);
        this.profileName = profileName;
        this.eloRanking = eloRanking;
        this.wpm = wpm;
        this.accuracy = accuracy;
        this.totalMatches = totalMatches;
        this.winRate = winRate;
    }

    public String getProfileName() {
        return profileName;
    }

    public float getEloRanking() {
        return eloRanking;
    }

    public float getWpm() {
        return wpm;
    }

    public float getAccuracy() {
        return accuracy;
    }

    public int getTotalMatches() {
        return totalMatches;
    }

    public float getWinRate() {
        return winRate;
    }

}
