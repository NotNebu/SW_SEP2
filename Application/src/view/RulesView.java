package view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.mvvm.View;
import viewmodel.RulesViewModel;

public class RulesView implements View<RulesViewModel> {
    private RulesViewModel viewModel;
    @FXML
    private Button okButton;
    @FXML
    private AnchorPane rulesAnchorPane;

    public RulesView(RulesViewModel viewModel) {
        setViewModel(viewModel);
    }

    @Override
    public void setViewModel(RulesViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void initialize() {
        okButton.setOnAction(e -> okButtonClicked());
    }

    private void okButtonClicked() {
        Stage stage = (Stage) rulesAnchorPane.getScene().getWindow();
        stage.close();
    }
}
