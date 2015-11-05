package project3;

import java.util.GregorianCalendar;

/***********************************************************************
CheckingAccount class that extends the abstract Account class and 
inherits its properties and methods.

@author Amanda Buhr, Nicholas Schaap
@version 1.0
***********************************************************************/
public class CheckingAccount extends Account {
	
	private static final long serialVersionUID = 1L;
	
	/** monthly fee */
	private double monthlyFee;
	
	/*******************************************************************
    Constructs a CheckingAccount with properties set to the given 
    parameters.
    
    @param number account number
    @param owner account owner name
    @param dateOpened date when account was opened
    @param balance current account balance
    @param monthlyFee monthly fee
    *******************************************************************/
	public CheckingAccount(int number, String owner,
			GregorianCalendar dateOpened, double balance,
			double monthlyFee) {
		
		super(number, owner, dateOpened, balance);
		
		this.setMonthlyFee(monthlyFee);
	}

	/*******************************************************************
    Tests whether "this" CheckingAccount is equal to another 
    CheckingAccount.
    
    @param other CheckingAccount which is being tested against for 
    	   equality
    @return boolean value representing the equality of the two checking 
    	    accounts
    *******************************************************************/
	@Override
	public boolean equals(Object other) {
		boolean result = false;
		if(other instanceof CheckingAccount) {
			CheckingAccount otherChecking = (CheckingAccount) other;
			if(number == otherChecking.getNumber() &&
					owner.equals(otherChecking.getOwner()) &&
					dateOpened.equals(otherChecking.getDateOpened()) &&
					balance == otherChecking.getBalance() &&
					monthlyFee == otherChecking.getMonthlyFee())
				result = true;
		}
		return result;
	}
	
	/*******************************************************************
    Converts the current CheckingAccount to a string.
    
    @return CheckingAccount in String format
    *******************************************************************/
	@Override
	public String toString() {
		String result = super.toString();
		result = result + "," + monthlyFee;
		
		return result;
	}

	/*******************************************************************
    Gets the monthly fee.
    
    @return monthly fee
    *******************************************************************/
	public double getMonthlyFee() {
		return monthlyFee;
	}

	/*******************************************************************
    Sets the monthly fee.
    
    @param monthlyFee monthly fee
    *******************************************************************/
	public void setMonthlyFee(double monthlyFee) {
		this.monthlyFee = monthlyFee;
	}
}