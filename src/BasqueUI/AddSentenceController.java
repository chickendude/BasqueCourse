package BasqueUI;

import javafx.event.ActionEvent;

/**
 * Created by crater-windoze on 1/15/2017.
 */
public class AddSentenceController {

	MainController mMainController;

	public void addSentence(ActionEvent actionEvent) {
		mMainController.addSentence();
	}

	public void setParentController(MainController mainController) {
		mMainController = mainController;
	}
}
