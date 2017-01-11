package BasqueUI.objects;

/**
 * Created by crater-windoze on 1/11/2017.
 */
public class FrequencyWord {
	private String mLemma;
	private int mPosition;
	private float mScore;

	public FrequencyWord(String lemma, int position, float score) {
		mLemma = lemma;
		mPosition = position;
		mScore = score;
	}

	public String getLemma() {
		return mLemma;
	}

	public void setLemma(String lemma) {
		mLemma = lemma;
	}

	public int getPosition() {
		return mPosition;
	}

	public float getScore() {
		return mScore;
	}

	public void setScore(float score) {
		mScore = score;
	}
}
