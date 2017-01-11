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
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {

	private ObservableList<Sentence> mSentences;
	private Sentence curSentence;

	private boolean mLoadText;
	private boolean mWasChanged;
	private String mOriginalValue;


	@FXML
	private TextField sentenceEdit;
	@FXML
	private ListView<Sentence> sentenceListView;

	public void initialize() {
		curSentence = null;
		mWasChanged = false;
		mOriginalValue = sentenceEdit.getText();
		mSentences = SentenceData.getInstance().getSentences();
		sentenceListView.setItems(mSentences);
		sentenceListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		// create click listener for sentences
		sentenceListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				if (oldValue != null && !mOriginalValue.equals(oldValue.getSentence())) {
					mWasChanged = true;
				}
				mLoadText = true;
				Sentence clickedSentence = sentenceListView.getSelectionModel().getSelectedItem();
				curSentence = clickedSentence;
				mOriginalValue = curSentence.getSentence();
				sentenceEdit.setText(mOriginalValue);
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
		// listener for text changes in text edit box
		sentenceEdit.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				String title = "* " + Main.WINDOW_TITLE;
				if (newValue.equals(mOriginalValue) && !mWasChanged) {
					title = Main.WINDOW_TITLE;
				}
				setWindowTitle(title);
				curSentence.setSentence(newValue);
				sentenceListView.getItems().set(sentenceListView.getSelectionModel().getSelectedIndex(), curSentence);
			}
		});
	}

	private void setWindowTitle(String title) {
		Stage primaryStage = (Stage) sentenceEdit.getScene().getWindow();
		primaryStage.setTitle(title);
	}

	public void displayAbout(ActionEvent actionEvent) {
		System.out.print("about!");
		sentenceEdit.setText("About!");
	}

	public void saveSentences(ActionEvent actionEvent) {
		mOriginalValue = sentenceEdit.getText();
		setWindowTitle(Main.WINDOW_TITLE);
		try {
			SentenceData.getInstance().saveSentences();
		} catch (IOException e) {
			System.out.println("Error writing to file.");
		}
	}
}
