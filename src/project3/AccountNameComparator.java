package project3;


import java.util.Comparator;

public class AccountNameComparator implements Comparator<Account> {

	@Override
	public int compare(Account a1, Account a2) {
		return a1.getOwner().compareToIgnoreCase(a2.getOwner());
	}
}