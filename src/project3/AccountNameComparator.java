package project3;

import java.util.Comparator;

/***********************************************************************
Comparator for use in the sortAccountName() method of the BankModel
class to alphabetically compare the owner names of two accounts.

@author Amanda Buhr, Nicholas Schaap
@version 1.0
***********************************************************************/
public class AccountNameComparator implements Comparator<Account> {

	/*******************************************************************
    Compares the owner names of two accounts alphabetically
    
    @param a1 account being compared
    @param a2 account being compared to
    *******************************************************************/
	@Override
	public int compare(Account a1, Account a2) {
		return a1.getOwner().compareToIgnoreCase(a2.getOwner());
	}
}