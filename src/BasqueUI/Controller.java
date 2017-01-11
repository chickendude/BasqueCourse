package BasqueUI;

import BasqueUI.objects.Sentence;
import BasqueUI.objects.SentenceData;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;

public class Controller {

	private ObservableList<Sentence> mSentences;
	private Sentence curSentence;

	@FXML
	private TextField sentenceEdit;
	@FXML
	private ListView<Sentence> sentenceListView;


	public void initialize() {
		curSentence = null;
		mSentences = SentenceData.getInstance().getSentences();
		mSentences.add(new Sentence("Zer moduz!", 3));
		mSentences.add(new Sentence("Kaixo!", 3));
		mSentences.add(new Sentence("Epa!", 3));
		mSentences.add(new Sentence("Maite zaitut, euskara!", 1));
		mSentences.add(new Sentence("Zer moduz!", 3));
		mSentences.add(new Sentence("Zer moduz!", 3));
		mSentences.add(new Sentence("Zer moduz!", 3));
		mSentences.add(new Sentence("Zer moduz!", 3));
		mSentences.add(new Sentence("Zer moduz!", 3));
		mSentences.add(new Sentence("Zer moduz!", 3));
		mSentences.add(new Sentence("Zer moduz!", 3));
		mSentences.add(new Sentence("Zer moduz!", 3));
		mSentences.add(new Sentence("Zer moduz!", 3));
		sentenceListView.setItems(mSentences);
		sentenceListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		// create listener
		sentenceListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				Sentence clickedSentence = sentenceListView.getSelectionModel().getSelectedItem();
				curSentence = clickedSentence;
				sentenceEdit.setText(clickedSentence.getSentence());
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						sentenceEdit.requestFocus();
						sentenceEdit.selectPositionCaret(sentenceEdit.getLength());
						sentenceEdit.deselect();
					}
				});
			}
		});

		sentenceEdit.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				curSentence.setSentence(newValue);
				sentenceListView.getItems().set(sentenceListView.getSelectionModel().getSelectedIndex(), curSentence);
			}
		});

	}

	public void displayAbout(ActionEvent actionEvent) {
		System.out.print("about!");
		sentenceEdit.setText("About!");
	}
}
