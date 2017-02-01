package BasqueUI;

import BasqueUI.alerts.Alerts;
import BasqueUI.objects.FrequencyWordData;
import BasqueUI.objects.SentenceData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    protected static final String WINDOW_TITLE = "Basque Course Creator";
    private MainController mMainController;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("xml/main_window.fxml"));
        Parent root = fxmlLoader.load();
        mMainController = fxmlLoader.getController();
        primaryStage.setTitle(WINDOW_TITLE);
        primaryStage.setMinWidth(600);
        primaryStage.setMinHeight(400);
        primaryStage.setOnCloseRequest(event -> {
            mMainController.closeAddSentenceWindow();
            if (SentenceData.getInstance().isModified()) {
                if (!Alerts.UnsavedChanges()) {
                    event.consume();
                }
            }
        });
        primaryStage.setScene(new Scene(root, 1200, 800));
        primaryStage.show();
    }

    @Override
    public void init() throws Exception {
        SentenceData mSentenceData = SentenceData.getInstance();
        try {
            mSentenceData.loadSentences();
            FrequencyWordData.getInstance().loadSentences();
        } catch (IOException e) {
            System.out.println("No sentence save... ignoring.");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
