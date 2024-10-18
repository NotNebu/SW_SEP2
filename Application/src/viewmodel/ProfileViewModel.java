package viewmodel;


import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.ProfileModel;
import network.client.observer.ClientProfileObserver;
import util.mvvm.ViewModel;
import view.util.ProfileViewObserver;

import java.util.ArrayList;

public class ProfileViewModel implements ViewModel<ProfileModel>, ClientProfileObserver {

    private ProfileModel model;
    private StringProperty profileName, eloRanking, wpm, accuracy, totalMatches, winRate;
    private ProfileViewObserver observer;

    public ProfileViewModel(ProfileModel model) {
        setModel(model);
        model.setObserver(this);
        profileName = new SimpleStringProperty();
        eloRanking = new SimpleStringProperty();
        wpm = new SimpleStringProperty();
        accuracy = new SimpleStringProperty();
        totalMatches = new SimpleStringProperty();
        winRate = new SimpleStringProperty();
    }

    @Override
    public void setModel(ProfileModel model) {
        this.model = model;
    }

    public StringProperty profileNameProperty() {
        return profileName;
    }

    public StringProperty eloRankingProperty() {
        return eloRanking;
    }

    public StringProperty wpmProperty() {
        return wpm;
    }

    public StringProperty accuracyProperty() {
        return accuracy;
    }

    public StringProperty totalMatchesProperty() {
        return totalMatches;
    }

    public StringProperty winRateProperty() {
        return winRate;
    }

    public void setObserver(ProfileViewObserver observer) {
        this.observer = observer;
    }

    public void removeObserver() {
        this.observer = null;
    }

    @Override
    public void profileName(String profileName) {
        Platform.runLater(() -> this.profileName.set(profileName));
    }

    @Override
    public void eloRanking(float eloRanking) {
        Platform.runLater(() -> this.eloRanking.set(String.valueOf(eloRanking)));
    }

    @Override
    public void wpm(float wpm) {
        Platform.runLater(() -> this.wpm.set(String.valueOf(wpm)));
    }

    @Override
    public void accuracy(float acc) {
        Platform.runLater(() -> this.accuracy.set(String.valueOf(acc)));
    }

    @Override
    public void totalMatches(int gamesPlayed) {
        Platform.runLater(() -> this.totalMatches.set(String.valueOf(gamesPlayed)));
    }

    @Override
    public void winRate(float winRate) {
        Platform.runLater(() -> this.winRate.set(String.format("%.2f", winRate * 100) + "%"));
    }

    @Override
    public void eloRankingData(ArrayList<Float> eloRankingData, int totalMatches) {
        observer.eloRankingData(eloRankingData, totalMatches);
    }


    public void loadProfile() {
        model.loadProfile();
    }
}
