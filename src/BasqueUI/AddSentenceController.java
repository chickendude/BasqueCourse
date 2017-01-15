package BasqueUI;

import BasqueUI.objects.Sentence;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
		// limit positionTextField to 4 characters
		positionTextField.lengthProperty().addListener(positionCharacterLimitListener);
		Platform.runLater(() -> sentenceTextField.requestFocus());
	}

	public void addSentence() {
		String sentenceText = sentenceTextField.getText();
		sentenceTextField.setText("");
		String positionText = positionTextField.getText();
		int position = getPosition();
		if (position >= 0) {
			positionTextField.setText(Integer.toString(position + 1));
		}
		Sentence sentence = new Sentence(sentenceText, 0.0f, "");
		mMainController.addSentence(position, sentence);
		sentenceTextField.requestFocus();
	}

	public int getPosition() {
		int position;
		try {
			position = Integer.parseInt(positionTextField.getText());
			if (position < 0) {
				position = -1;
			}
		} catch (NumberFormatException e) {
			// not a valid integer
			position = -1;
		}
		return position;
	}


	public void setParentController(MainController mainController) {
		mMainController = mainController;
	}

	public void closeWindow() {
		mMainController.closeAddSentenceWindow();
	}

	public void checkKeyPressed(KeyEvent keyEvent) {
		switch (keyEvent.getCode()) {
			case ENTER:
				addSentence();
				break;
			case ESCAPE:
				closeWindow();
				break;
		}
	}

	private ChangeListener positionCharacterLimitListener = new ChangeListener<Number>() {
		@Override
		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
			if (newValue.intValue() > oldValue.intValue()) {
				// Check if the new character is greater than LIMIT
				if (positionTextField.getText().length() >= 4) {
					positionTextField.setText(positionTextField.getText().substring(0, 4));
				}
			}
		}
	};
}
