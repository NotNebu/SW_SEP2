package model;

import network.client.observer.ClientProfileObserver;
import network.client.socket.SocketClient;
import util.holders.UserDTOHolder;
import util.mvvm.Model;

import java.util.ArrayList;

public class ProfileModel implements Model, ClientProfileObserver {

    private SocketClient profileClient;
    private ClientProfileObserver observer;

    public ProfileModel() {
        profileClient = SocketClient.getInstance();
        profileClient.addProfileObserver(this);
    }

    public void setObserver(ClientProfileObserver observer) {
        this.observer = observer;
    }

    public void removeObserver() {
        this.observer = null;
    }

    @Override
    public void profileName(String profileName) {
        observer.profileName(profileName);
    }

    @Override
    public void eloRanking(float eloRanking) {
        observer.eloRanking(eloRanking);
    }

    @Override
    public void wpm(float wpm) {
        observer.wpm(wpm);
    }

    @Override
    public void accuracy(float acc) {
        observer.accuracy(acc);
    }

    @Override
    public void totalMatches(int gamesPlayed) {
        observer.totalMatches(gamesPlayed);
    }

    @Override
    public void winRate(float winRate) {
        observer.winRate(winRate);
    }

    @Override
    public void eloRankingData(ArrayList<Float> eloRankingData, int totalMatches) {
        observer.eloRankingData(eloRankingData, totalMatches);
    }


    public void loadProfile() {
        profileClient.getProfile(UserDTOHolder.getUserDTO().getId());
    }
}
