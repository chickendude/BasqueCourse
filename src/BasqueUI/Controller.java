package BasqueUI;

import BasqueUI.objects.FrequencyWord;
import BasqueUI.objects.FrequencyWordData;
import BasqueUI.objects.Sentence;
import BasqueUI.objects.SentenceData;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {

	private ObservableList<Sentence> mSentences;
	private ObservableList<FrequencyWord> mDictionary;
	private Sentence mCurSentence;

	private boolean mLoadText;
	private boolean mWasChanged;
	private String mOriginalValue;


	@FXML
	private TextField sentenceEdit;
	@FXML
	private ListView<Sentence> sentenceListView;
	@FXML
	private Label analysisLabel;

	public void initialize() {
		mCurSentence = null;
		mWasChanged = false;
		mOriginalValue = sentenceEdit.getText();
		mSentences = SentenceData.getInstance().getSentences();
		mDictionary = FrequencyWordData.getInstance().getDictionary();
		setupSentenceListView();
		setupSentenceEdit();
	}


	private void setupSentenceListView() {
		sentenceListView.setItems(mSentences);
		sentenceListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		// create click listener for sentences
		sentenceListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				if (oldValue != null && !mOriginalValue.equals(oldValue.getSentence())) {
					mWasChanged = true;
				}
				mLoadText = true;
				Sentence clickedSentence = sentenceListView.getSelectionModel().getSelectedItem();
				mCurSentence = clickedSentence;
				mOriginalValue = mCurSentence.getSentence();
				sentenceEdit.setText(mOriginalValue);
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						sentenceEdit.requestFocus();
						sentenceEdit.selectPositionCaret(sentenceEdit.getLength());
						sentenceEdit.deselect();
					}
				});
				analyzeSentence(mCurSentence);
			}
		});
	}

	private void setupSentenceEdit() {
		// listener for text changes in text edit box
		sentenceEdit.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				String title = "* " + Main.WINDOW_TITLE;
				if (newValue.equals(mOriginalValue) && !mWasChanged) {
					title = Main.WINDOW_TITLE;
				}
				setWindowTitle(title);
				mCurSentence.setSentence(newValue);
				sentenceListView.getItems().set(sentenceListView.getSelectionModel().getSelectedIndex(), mCurSentence);
			}
		});
	}


	private void analyzeSentence(Sentence sentence) {
		String[] words = sentence.getSentence().split(" ");
		String text = "";
		for (String word : words) {
			FrequencyWord frequencyWord = FrequencyWordData.getInstance().findWord(word);
			text += String.format("%s - %.3f (%d)\n", frequencyWord.getLemma(), frequencyWord.getScore(), frequencyWord.getPosition());
		}
		analysisLabel.setText(text);
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
