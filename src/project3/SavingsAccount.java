package project3;

import java.util.GregorianCalendar;

/***********************************************************************
SavingsAccount class that extends the abstract Account class and 
inherits its properties and methods.

@author Amanda Buhr, Nicholas Schaap
@version 1.0
***********************************************************************/
public class SavingsAccount extends Account {
	
	private static final long serialVersionUID = 1L;
	
	/** minimum balance */
	private double minBalance;
	
	/** interest rate */
	private double interestRate;
	
	/*******************************************************************
    Constructs a SavingsAccount with properties set to the given 
    parameters.
    
    @param number account number
    @param owner account owner name
    @param dateOpened date when account was opened
    @param balance current account balance
    @param minBalance minimum account balance
    @param interestRate interest rate
    *******************************************************************/
	public SavingsAccount(int number, String owner,
			GregorianCalendar dateOpened, double balance, 
			double minBalance, double interestRate) {
		
		super(number, owner, dateOpened, balance);
		
		this.minBalance = minBalance;
		this.interestRate = interestRate;
	}

	/*******************************************************************
    Tests whether "this" SavingsAccount is equal to another 
    SavingsAccount.
    
    @param other SavingsAccount which is being tested against for 
    	   equality
    @return boolean value representing the equality of the two savings 
     		accounts
    *******************************************************************/
	@Override
	public boolean equals(Object other) {
		boolean result = false;
		if(other instanceof SavingsAccount) {
			SavingsAccount otherSavings = (SavingsAccount) other;
			if(number == otherSavings.getNumber() &&
					owner.equals(otherSavings.getOwner()) &&
					dateOpened.equals(otherSavings.getDateOpened()) &&
					balance == otherSavings.getBalance() &&
					minBalance == otherSavings.getMinBalance() &&
					interestRate == otherSavings.getInterestRate())
				result = true;
		}
		return result;
	}
	
	/*******************************************************************
    Converts the current SavingsAccount to a string.
    
    @return SavingsAccount in String format
    *******************************************************************/
	@Override
	public String toString() {
		String result = super.toString();
		result = result + "," + minBalance + "," + 
				interestRate;
		
		return result;
	}

	/*******************************************************************
    Gets the minimum account balance.
    
    @return minimum account balance
    *******************************************************************/
	public double getMinBalance() {
		return minBalance;
	}

	/*******************************************************************
    Sets the minimum account balance.
    
    @param minBalance minimum account balance
    *******************************************************************/
	public void setMinBalance(double minBalance) {
		this.minBalance = minBalance;
	}

	/*******************************************************************
    Gets the interest rate.
    
    @return interest rate
    *******************************************************************/
	public double getInterestRate() {
		return interestRate;
	}

	/*******************************************************************
    Sets the interest rate.
    
    @param interestRate interest rate
    *******************************************************************/
	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}
}