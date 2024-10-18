package network.client.extractors;

import network.client.observer.ClientProfileObserver;
import network.util.message.Message;
import network.util.message.response.GetProfileResponseMessage;
import network.util.message.response.GetProfileStatsForLineChartResponseMessage;

import java.util.ArrayList;

public class ProfileExtractor implements IExtractor {

    private ArrayList<ClientProfileObserver> observers;

    public ProfileExtractor(ArrayList<ClientProfileObserver> observers) {
        this.observers = observers;
    }

    @Override
    public void extractContext(Message message) {
        switch (message.getType()) {
            case GET_PROFILE_RESPONSE_MESSAGE:
                GetProfileResponseMessage getProfileResponseMessage = (GetProfileResponseMessage) message;
                observers.forEach(observer -> observer.profileName(getProfileResponseMessage.getProfileName()));
                observers.forEach(observer -> observer.accuracy(getProfileResponseMessage.getAccuracy()));
                observers.forEach(observer -> observer.wpm(getProfileResponseMessage.getWpm()));
                observers.forEach(observer -> observer.eloRanking(getProfileResponseMessage.getEloRanking()));
                observers.forEach(observer -> observer.totalMatches(getProfileResponseMessage.getTotalMatches()));
                observers.forEach(observer -> observer.winRate(getProfileResponseMessage.getWinRate()));
                break;
            case GET_PROFILE_STATS_FOR_LINE_CHART_RESPONSE_MESSAGE:
                GetProfileStatsForLineChartResponseMessage getProfileStatsForLineChartResponseMessage =
                        (GetProfileStatsForLineChartResponseMessage) message;
                observers.forEach(observer -> observer.eloRankingData(getProfileStatsForLineChartResponseMessage.getEloRankingData(),
                        getProfileStatsForLineChartResponseMessage.getTotalMatches()));
                break;
            default:
                throw new IllegalArgumentException("Invalid message type: " + message.getType());
        }
    }
}
