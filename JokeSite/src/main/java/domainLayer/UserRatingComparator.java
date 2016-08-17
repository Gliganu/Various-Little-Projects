package domainLayer;

import java.util.Comparator;

public class UserRatingComparator implements Comparator<User> {

	@Override
	public int compare(User user1, User user2) {
		if(user1.getRating()<user2.getRating())
			return 1;
		else if ((user1.getRating()>user2.getRating())){
			return -1;
		}
		return 0;
	}

}
