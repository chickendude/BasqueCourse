package BasqueUI.objects;

public class Sentence {
	private String mSentence;
	private float mDifficulty;

	public Sentence(String sentence, float difficulty) {
		mSentence = sentence;
		mDifficulty = difficulty;
	}

	public String getSentence() {
		return mSentence;
	}

	public void setSentence(String sentence) {
		mSentence = sentence;
	}

	public float getDifficulty() {
		return mDifficulty;
	}

	public void setDifficulty(float difficulty) {
		mDifficulty = difficulty;
	}

	@Override
	public String toString() {
		return String.format("%s (%.2f)",mSentence,mDifficulty);
	}
}
