package BasqueUI.objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SentenceData {
	private static SentenceData instance = new SentenceData();
	private static String filename = "sentences.csv";

	private ObservableList<Sentence> mSentences;
	private boolean mModified;

	public static SentenceData getInstance() {
		return instance;
	}

	public ObservableList<Sentence> getSentences() {
		return mSentences;
	}

	public void loadSentences() throws IOException {
		mSentences = FXCollections.observableArrayList();

		Path path = Paths.get(filename);
		BufferedReader br = Files.newBufferedReader(path);

		if (br != null) {
			System.out.println(String.format("Opening %s...", filename));
			String input;
			while ((input = br.readLine()) != null) {
				String[] values = input.split("\t");

				String sentenceText = values[0];
				float difficulty = Float.parseFloat(values[1]);
				String sentenceAnalysis = values[2];

				Sentence sentence = new Sentence(sentenceText, difficulty, sentenceAnalysis);
				mSentences.add(sentence);
			}
			br.close();
		} else {
			System.out.println(String.format("Error parsing '%s'", filename));
		}
	}

	public void saveSentences() throws IOException {
		Path path = Paths.get(filename);
		BufferedWriter bw = Files.newBufferedWriter(path);
		if (bw != null) {
			for (Sentence item : mSentences) {
				bw.write(String.format("%s\t%f\t%s\n",
						item.getSentence(),
						item.getDifficulty(),
						item.getSentenceAnalysis()));
			}
			bw.close();

		} else {
			System.out.println(String.format("Error saving '%s'", filename));
		}
	}

	public boolean isModified() {
		return mModified;
	}

	public void setModified(boolean modified) {
		mModified = modified;
	}
}
