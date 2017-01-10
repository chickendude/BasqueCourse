package BasqueUI.objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

public class SentenceData {
	private static SentenceData instance = new SentenceData();
	private static String filename = "sentences.csv";

	private ObservableList<Sentence> mSentences;

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

				Sentence sentence = new Sentence(sentenceText, difficulty);
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
			Iterator<Sentence> iter = mSentences.iterator();
			while (iter.hasNext()) {
				Sentence item = iter.next();
				bw.write(String.format("%s\t%f\n",
						item.getSentence(),
						item.getDifficulty()));
			}
			bw.close();

		} else {
			System.out.println(String.format("Error saving '%s'", filename));
		}
	}
}
