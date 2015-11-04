package project3;

import java.util.Comparator;

public class AccountNumberComparator implements Comparator<Account> {

	@Override
	public int compare(Account a1, Account a2) {
		if(a1.getNumber() < a2.getNumber())
			return -1;
		else if(a1.getNumber() > a2.getNumber())
			return 1;
		return 0;
	}

}
