package BasqueUI;

import BasqueUI.alerts.Alerts;
import BasqueUI.objects.FrequencyWordData;
import BasqueUI.objects.SentenceData;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class Main extends Application {
	public static final String WINDOW_TITLE ="Basque Course Creator";
	MainController mMainController;

	private SentenceData mSentenceData;

	@Override
    public void start(Stage primaryStage) throws Exception{
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("xml/main_window.fxml"));
		Parent root = fxmlLoader.load();
		mMainController = fxmlLoader.getController();
		primaryStage.setTitle(WINDOW_TITLE);
		primaryStage.setMinWidth(600);
		primaryStage.setMinHeight(400);
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				mMainController.closeAddSentenceWindow();
				if (SentenceData.getInstance().isModified()) {
					if (!Alerts.UnsavedChanges()) {
						event.consume();
					}
				}
			}
		});
        primaryStage.setScene(new Scene(root, 1200, 800));
        primaryStage.show();
    }

    @Override
    public void init() throws Exception {
		mSentenceData = SentenceData.getInstance();
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
