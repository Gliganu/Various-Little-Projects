package domainLayer;

import java.util.Comparator;

public class JokeDateComparator implements Comparator<Joke> {

	@Override
	public int compare(Joke joke1, Joke joke2) {
		return (-1) * joke1.getDateAdded().compareTo(joke2.getDateAdded());
	}

}
