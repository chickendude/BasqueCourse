package BasqueUI;

import BasqueUI.objects.Sentence;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * Created by crater-windoze on 1/15/2017.
 */
public class AddSentenceController {

	MainController mMainController;

	@FXML
	TextField sentenceTextField;
	@FXML
	TextField positionTextField;

	public void addSentence(ActionEvent actionEvent) {
		String sentenceText = sentenceTextField.getText();
		int position = Integer.parseInt(positionTextField.getText());
		Sentence sentence = new Sentence(sentenceText, 0.0f, "");
		mMainController.addSentence(position, sentence);
	}

	public void setParentController(MainController mainController) {
		mMainController = mainController;
	}
}
