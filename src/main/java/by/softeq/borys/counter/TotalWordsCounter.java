package by.softeq.borys.counter;

public class TotalWordsCounter {
	private String[] wordsToCount;
	private String homeUrl;
	private static final int DEPTH_LEVEL = 8;
	private static final int PAGES_LIMIT = 10000;
	
	public TotalWordsCounter(String url, String[] words) {
		this.homeUrl = url;
		this.wordsToCount = words;
		
	}

	public String[] getWordsToCount() {
		return wordsToCount;
	}

	public void setWordsToCount(String[] wordsToCount) {
		this.wordsToCount = wordsToCount;
	}

}
