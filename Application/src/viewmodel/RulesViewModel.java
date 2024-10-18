package viewmodel;

import model.RulesModel;
import util.mvvm.Model;
import util.mvvm.ViewModel;

public class RulesViewModel implements ViewModel<RulesModel> {

    private RulesModel model;

    public RulesViewModel(RulesModel model) {
        setModel(model);
    }
    @Override
    public void setModel(RulesModel model) {
        this.model = model;
    }
}
