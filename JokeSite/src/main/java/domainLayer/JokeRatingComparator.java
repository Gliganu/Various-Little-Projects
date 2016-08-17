package domainLayer;

import java.util.Comparator;

public class JokeRatingComparator implements Comparator<Joke> {

	@Override
	public int compare(Joke joke1, Joke joke2) {
		if(joke1.getRating()<joke2.getRating())
			return 1;
		else if ((joke1.getRating()>joke2.getRating())){
			return -1;
		}
		return 0;
	}

}
