package util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import util.mvvm.MVVMFactory;
import util.mvvm.Model;
import util.mvvm.View;
import util.mvvm.ViewModel;

import java.io.IOException;

// Utility class for managing JavaFX FXML loading and displaying new windows.
public class FXMLUtils {

    // Replaces the primary stage's scene with a new one, based on provided FXML and MVVM classes.
    public static <M extends Model, VM extends ViewModel<M>, V extends View<VM>> void changeFXML(Stage primaryStage, String name, String resource, Class<M> modelClass, Class<VM> viewModelClass, Class<V> viewClass) {
        try {
            MVVMFactory<M, VM, V> factory = new MVVMFactory<>();
            V newView = factory.createView(modelClass, viewModelClass, viewClass);
            FXMLLoader fxmlLoader = new FXMLLoader(FXMLUtils.class.getResource(resource));
            fxmlLoader.setControllerFactory(controllerClass -> newView);
            Scene scene = new Scene(fxmlLoader.load());
            primaryStage.setScene(scene);
            primaryStage.setTitle(name);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Handle appropriately
        }
    }

    // Opens a new window using the provided FXML and MVVM classes.
    public static <M extends Model, VM extends ViewModel<M>, V extends View<VM>> void openPopupWindow(Stage stage, String title, String resource, Class<M> modelClass, Class<VM> viewModelClass, Class<V> viewClass) {
        try {
            MVVMFactory<M, VM, V> factory = new MVVMFactory<>();
            V newView = factory.createView(modelClass, viewModelClass, viewClass);
            FXMLLoader fxmlLoader = new FXMLLoader(FXMLUtils.class.getResource(resource));
            fxmlLoader.setControllerFactory(controllerClass -> newView);
            stage.initModality(Modality.APPLICATION_MODAL); // Blocks access to other windows
            stage.initStyle(StageStyle.UNDECORATED); // Removes the window decorations
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.setTitle(title);
            stage.showAndWait(); // Wait until this popup is closed
        } catch (IOException e) {
            e.printStackTrace(); // Handle appropriately
        }
    }
}
