package view.util;

import java.util.ArrayList;

public interface ProfileViewObserver {

    void eloRankingData(ArrayList<Float> eloRankingData, int totalMatches);

}
