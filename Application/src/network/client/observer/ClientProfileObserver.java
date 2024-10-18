package network.client.observer;

import java.util.ArrayList;

public interface ClientProfileObserver {

    void profileName(String profileName);

    void eloRanking(float eloRanking);

    void wpm(float wpm);

    void accuracy(float acc);

    void totalMatches(int gamesPlayed);

    void winRate(float winRate);

    void eloRankingData(ArrayList<Float> eloRankingData, int totalMatches);

}
