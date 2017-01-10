package BasqueUI;

import BasqueUI.objects.Sentence;
import BasqueUI.objects.SentenceData;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;

public class Controller {

	private ObservableList<Sentence> mSentences;

	@FXML
	private TextField textPane;
	@FXML
	private ListView<Sentence> sentenceListView;


	public void initialize() {
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
			if(newValue != null) {
				Sentence item = sentenceListView.getSelectionModel().getSelectedItem();
				textPane.setText(item.getSentence());
			}
		});

	}

	public void displayAbout(ActionEvent actionEvent) {
		System.out.print("about!");
		textPane.setText("About!");
	}
}
