package BasqueUI.objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by crater-windoze on 1/11/2017.
 */
public class FrequencyWordData {
	private static FrequencyWordData instance = new FrequencyWordData();
	private static int total_lemmas = 13302602;

	private ObservableList<FrequencyWord> mDictionary;

	public FrequencyWord findWord(String word) {
		word = word.toLowerCase();
		if (word.equals("edun")) {
			word = word;
		}
		word = word.replaceAll("^[^a-zA-Z0-9\\s]+|[^a-zA-Z0-9\\s]+$", "");
		FrequencyWord entry = new FrequencyWord(word, 0,0);
		for (FrequencyWord frequencyWord : mDictionary) {
			if (word.equals(frequencyWord.getLemma())) {
				entry = frequencyWord;
			}
		}
		return entry;
	}

	public static FrequencyWordData getInstance() {
		return instance;
	}

	public ObservableList<FrequencyWord> getDictionary() {
		return mDictionary;
	}

	public void loadSentences() throws IOException {
		mDictionary = FXCollections.observableArrayList();

		String filename = "corpus_goenkale.csv";
		Path path = Paths.get(filename);
		BufferedReader br = Files.newBufferedReader(path);

		if (br != null) {
			System.out.println(String.format("Opening %s...", filename));
			String input;
			int i = 0;
			while ((input = br.readLine()) != null && ++i < 6000) {
				String[] values = input.split("\t");

				String sentenceText = values[0];
				float score = Float.parseFloat(values[2]);

				FrequencyWord frequencyWord = new FrequencyWord(sentenceText, i, score);
				mDictionary.add(frequencyWord);
			}
			br.close();
		} else {
			System.out.println(String.format("Error parsing '%s'", filename));
		}
	}

}
