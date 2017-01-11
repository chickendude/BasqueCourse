package BasqueUI.objects;

public class Sentence {
	private String mSentence;
	private String mSentenceAnalysis;
	private float mDifficulty;

	public Sentence(String sentence, float difficulty, String sentenceAnalysis) {
		mSentence = sentence;
		mDifficulty = difficulty;
		mSentenceAnalysis = sentenceAnalysis;
	}

	public String getSentenceAnalysis() {
		return mSentenceAnalysis;
	}

	public void setSentenceAnalysis(String sentenceAnalysis) {
		mSentenceAnalysis = sentenceAnalysis;
	}

	public String getSentence() {
		return mSentence;
	}

	public void setSentence(String sentence) {
		mSentence = sentence.trim();
	}

	public float getDifficulty() {
		return mDifficulty;
	}

	public void setDifficulty(float difficulty) {
		mDifficulty = difficulty;
	}

	@Override
	public String toString() {
		return String.format("%s (%.2f)", mSentence, mDifficulty);
	}
}
