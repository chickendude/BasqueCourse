package BasqueUI;

import BasqueUI.objects.SentenceData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
	public static final String WINDOW_TITLE ="Basque Course Creator";

	private SentenceData mSentenceData;

	@Override
    public void start(Stage primaryStage) throws Exception{
		Parent root = FXMLLoader.load(getClass().getResource("xml/main_window.fxml"));
		primaryStage.setTitle(WINDOW_TITLE);
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    @Override
    public void init() throws Exception {
		mSentenceData = SentenceData.getInstance();
		try {
			mSentenceData.loadSentences();
		} catch (IOException e) {
			System.out.println("No sentence save... ignoring.");
		}
	}

    public static void main(String[] args) {
        launch(args);
    }
}
