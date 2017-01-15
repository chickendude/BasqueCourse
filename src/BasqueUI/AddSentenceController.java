package BasqueUI;

import BasqueUI.objects.Sentence;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 * Created by crater-windoze on 1/15/2017.
 */
public class AddSentenceController {

	MainController mMainController;

	@FXML
	TextField sentenceTextField;
	@FXML
	TextField positionTextField;

	public void initialize() {
		Platform.runLater(() -> sentenceTextField.requestFocus());
	}

	public void addSentence() {
		String sentenceText = sentenceTextField.getText();
		sentenceTextField.setText("");
		int position = Integer.parseInt(positionTextField.getText());
		Sentence sentence = new Sentence(sentenceText, 0.0f, "");
		mMainController.addSentence(position, sentence);
	}

	public void setParentController(MainController mainController) {
		mMainController = mainController;
	}

	public void closeWindow() {
		mMainController.closeAddSentenceWindow();
	}

	public void checkKeyPressed(KeyEvent keyEvent) {
		switch(keyEvent.getCode()) {
			case ENTER:
				addSentence();
				break;
			case ESCAPE:
				closeWindow();
				break;
		}
	}
}
