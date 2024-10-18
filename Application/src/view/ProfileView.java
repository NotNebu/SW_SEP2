package view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import util.mvvm.View;
import view.util.ProfileViewObserver;
import viewmodel.ProfileViewModel;

import java.util.ArrayList;

public class ProfileView implements View<ProfileViewModel>, ProfileViewObserver {

    @FXML
    private LineChart progressLineChart;
    @FXML
    private NumberAxis xAxis, yAxis;
    @FXML
    private Label totalMatchesLabel, winrateLabel, profileNameLabel, eloRankingLabel, wpmLabel, accuracyLabel;

    private final XYChart.Series<Integer, Float> series = new XYChart.Series<>();
    private ProfileViewModel viewModel;

    public ProfileView(ProfileViewModel viewModel) {
        setViewModel(viewModel);
    }

    @Override
    public void setViewModel(ProfileViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void initialize() {
        totalMatchesLabel.textProperty().bind(viewModel.totalMatchesProperty());
        winrateLabel.textProperty().bind(viewModel.winRateProperty());
        profileNameLabel.textProperty().bind(viewModel.profileNameProperty());
        eloRankingLabel.textProperty().bind(viewModel.eloRankingProperty());
        wpmLabel.textProperty().bind(viewModel.wpmProperty());
        accuracyLabel.textProperty().bind(viewModel.accuracyProperty());
        this.viewModel.setObserver(this);
        this.viewModel.loadProfile();
        // hide the data name
        progressLineChart.setLegendVisible(false);

    }


    @Override
    public void eloRankingData(ArrayList<Float> eloRankingData, int totalMatches) {
        Platform.runLater(() -> {
            progressLineChart.getData().clear();
            series.getData().clear();

            float elo = 1000;
            float max = 0, min = 10000;

            for (int i = 0; i < totalMatches; i++) {
                elo += eloRankingData.get(i);
                series.getData().add(new XYChart.Data<>(i + 1, elo));
                if (elo < min) min = elo;
                if (elo > max) max = elo;
            }
            progressLineChart.getData().add(series);
            // set tick start and end
            xAxis.setAutoRanging(false);
            xAxis.setLowerBound(1);
            xAxis.setUpperBound(totalMatches);
            // dynamical set tick unit
            xAxis.setTickUnit((double) totalMatches / 10 > 1 ? (double) totalMatches / 10 : 1);

            yAxis.setAutoRanging(false);
            yAxis.setLowerBound(min - 20);
            yAxis.setUpperBound(max + 20);
            yAxis.setTickUnit((double) (max - min) / 10 > 10 ? (double) (max - min) / 10 : 10);

        });
    }

}
