import javafx.application.Application;
import javafx.stage.Stage;
import model.LoginModel;
import util.FXMLUtils;
import util.holders.PrimaryStageHolder;
import view.LoginView;
import viewmodel.LoginViewModel;

public class Main extends Application {

  public static void main(String[] args) {
    launch();
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    PrimaryStageHolder.setPrimaryStage(primaryStage);

    FXMLUtils.changeFXML(primaryStage, "login", "/fxml/login.fxml", LoginModel.class, LoginViewModel.class, LoginView.class);

  }
}
