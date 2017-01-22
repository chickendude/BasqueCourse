package BasqueUI;

import BasqueUI.objects.FrequencyWord;
import BasqueUI.objects.FrequencyWordData;
import BasqueUI.objects.Sentence;
import BasqueUI.objects.SentenceData;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainController {

	private ObservableList<Sentence> mSentences;
	private ObservableList<FrequencyWord> mDictionary;
	private Sentence mCurSentence;

	private String mOriginalValue;

	private Stage mAddSentenceStage;


	@FXML
	private TextField sentenceEdit;
	@FXML
	private ListView<Sentence> sentenceListView;
	@FXML
	private Label analysisLabel;
	@FXML
	private SplitPane splitPaneLeft;
	@FXML
	private AnchorPane paneRight;

	public void initialize() {
		mCurSentence = null;
		SentenceData.getInstance().setModified(false);
		mOriginalValue = sentenceEdit.getText();
		mSentences = SentenceData.getInstance().getSentences();
		mDictionary = FrequencyWordData.getInstance().getDictionary();
		setupSentenceListView();
		setupSentenceEdit();
	}


	private void setupSentenceListView() {
		sentenceListView.setItems(mSentences);
		sentenceListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		sentenceListView.setCellFactory(param -> new ListCell<Sentence>() {
			@Override
			protected void updateItem(Sentence item, boolean empty) {
				super.updateItem(item, empty);
				if (empty || item == null || item.getSentence() == null) {
					setText(null);
				} else {
					String text = mSentences.indexOf(item) + 1 + " " + item.getSentence();
					setText(text);
				}
			}
		});
		// create click listener for sentences
		sentenceListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
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
				loadAnalysisData();
			}
		});
	}

	private void setupSentenceEdit() {
		// listener for text changes in text edit box
		sentenceEdit.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				String title;
				if (newValue.equals(mOriginalValue) && !SentenceData.getInstance().isModified()) {
					title = Main.WINDOW_TITLE;
				} else {
					title = "* " + Main.WINDOW_TITLE;
					SentenceData.getInstance().setModified(true);
				}
				setWindowTitle(title);
				mCurSentence.setSentence(newValue);
				sentenceListView.getItems().set(sentenceListView.getSelectionModel().getSelectedIndex(), mCurSentence);
			}
		});
	}


	private void loadAnalysisData() {
		String sentenceAnalysis = mCurSentence.getSentenceAnalysis();
		if (sentenceAnalysis == null) {
			analysisLabel.setText("");
			return;
		}
		String[] words = sentenceAnalysis.split(" ");
		String text = "";
		for (String word : words) {
			String[] data = word.split("/");
			if (data.length == 4) {
				String lemma, score, position, grammar;
				lemma = data[0];
				score = data[1];
				position = data[2];
				grammar = data[3];
				text += String.format("%s - %.3f (%s) - %s\n", lemma, Float.parseFloat(score), position, grammar);
			}
		}
		analysisLabel.setText(text);
	}

	private void setWindowTitle(String title) {
		Stage primaryStage = (Stage) sentenceEdit.getScene().getWindow();
		primaryStage.setTitle(title);
	}

	@FXML
	public void displayAbout() {
		System.out.print("about!");
		sentenceEdit.setText("About!");
	}

	@FXML
	public void saveSentences() {
		mOriginalValue = sentenceEdit.getText();
		setWindowTitle(Main.WINDOW_TITLE);
		try {
			SentenceData.getInstance().saveSentences();
		} catch (IOException e) {
			System.out.println("Error writing to file.");
		}
	}

	@FXML
	public void analyzeText() {
		SentenceData.getInstance().setModified(true);
		String[] words = mCurSentence.getSentence().split(" ");
		String text = "";
		String sentenceAnalysis = "";
		for (String word : words) {
			FrequencyWord frequencyWord = FrequencyWordData.getInstance().findWord(word);
			text += String.format("%s - %.3f (%d)\n", frequencyWord.getLemma(), frequencyWord.getScore(), frequencyWord.getPosition());
			sentenceAnalysis += String.format("%s/%s/%s/%s ",
					frequencyWord.getLemma(),
					frequencyWord.getScore(),
					frequencyWord.getPosition(),
					"noun,determiner,singular");
		}
		mCurSentence.setSentenceAnalysis(sentenceAnalysis);
		analysisLabel.setText(text);
	}

	@FXML
	public void exitProgram() {
		Stage primaryStage = (Stage) sentenceEdit.getScene().getWindow();
		primaryStage.fireEvent(
				new WindowEvent(
						primaryStage,
						WindowEvent.WINDOW_CLOSE_REQUEST
				));
	}

	@FXML
	public void openAddSentenceWindow() throws Exception {
		closeAddSentenceWindow();


		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("xml/add_sentence_window.fxml"));
		Parent root = fxmlLoader.load();
		// connect the two controllers
		AddSentenceController controller = fxmlLoader.getController();
		controller.setParentController(this);
		mAddSentenceStage = new Stage();
		mAddSentenceStage.setIconified(false);
		mAddSentenceStage.setTitle("Add Sentence");
		mAddSentenceStage.setScene(new Scene(root, 600, 200));
		mAddSentenceStage.setMinWidth(300);
		mAddSentenceStage.setMinHeight(150);
		mAddSentenceStage.show();
	}

	public void closeAddSentenceWindow() {
		// make sure we don't open multiple windows
		if (mAddSentenceStage != null) {
			mAddSentenceStage.close();
		}
	}

	public void addSentence(int position, Sentence sentence) {
		// add sentence to sentence listview
		if (position >= 0) {
			sentenceListView.getItems().add(position, sentence);
		} else {
			sentenceListView.getItems().add(sentence);
		}
		sentenceListView.scrollTo(sentence);
		// mark window as modified
		SentenceData.getInstance().setModified(true);
		setWindowTitle("* " + Main.WINDOW_TITLE);
	}

	public void deleteSentences() {
		System.out.println("delete");
		mCurSentence = sentenceListView.getItems().get(0);
		List<Sentence> sentences = new ArrayList<>(sentenceListView.getSelectionModel().getSelectedItems());
		sentenceListView.getSelectionModel().clearSelection();
		sentenceListView.getItems().removeAll(sentences);
	}
}
