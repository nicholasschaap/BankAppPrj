package project3;

import java.util.Comparator;

/***********************************************************************
Comparator for use in the sortDateOpened() method of the BankModel
class to compare the dates on which two accounts were opened.

@author Amanda Buhr, Nicholas Schaap
@version 1.0
***********************************************************************/
public class DateOpenedComparator implements Comparator<Account> {
	
	/*******************************************************************
    Compares the dates on which two accounts were opened.
    
    @param a1 account being compared
    @param a2 account being compared to
    *******************************************************************/
	@Override
	public int compare(Account a1, Account a2) {
		return a1.getDateOpened().compareTo(a2.getDateOpened());
	}
}
