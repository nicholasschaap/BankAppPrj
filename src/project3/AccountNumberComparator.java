package project3;

import java.util.Comparator;

/***********************************************************************
Comparator for use in the sortAccountNumber() method of the BankModel
class to compare the numbers of two accounts.

@author Amanda Buhr, Nicholas Schaap
@version 1.0
***********************************************************************/
public class AccountNumberComparator implements Comparator<Account> {

	/*******************************************************************
    Compares the numbers of two accounts
    
    @param a1 account being compared
    @param a2 account being compared to
    *******************************************************************/
	@Override
	public int compare(Account a1, Account a2) {
		if(a1.getNumber() < a2.getNumber())
			return -1;
		else if(a1.getNumber() > a2.getNumber())
			return 1;
		return 0;
	}
}
