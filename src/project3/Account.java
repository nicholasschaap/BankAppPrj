package project3;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/***********************************************************************
Abstract Account class which determines the properties and methods 
inherited by child classes.

@author Amanda Buhr, Nicholas Schaap
@version 1.0
***********************************************************************/
public abstract class Account implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/** account number */
	protected int number;
	
	/** account owner name */
	protected String owner;
	
	/** date when account was opened */
	protected GregorianCalendar dateOpened;
	
	/** current account balance */
	protected double balance;
	
	/*******************************************************************
    Abstract constructor used by constructors of child classes.
    
    @param number account number
    @param owner name of account owner
    @param dateOpened date when account was opened
    @param balance current account balance
    *******************************************************************/
	public Account(int number, String owner, 
			GregorianCalendar dateOpened, double balance) {

		this.number = number;
		this.owner = owner;
		this.dateOpened = dateOpened;
		this.balance = balance;
	}

	/*******************************************************************
    Tests whether "this" Account is equal to another Account.
    
    @param other Account which is being tested against for equality
    @return boolean value representing the equality of the two accounts
    *******************************************************************/
	@Override
	public boolean equals(Object other) {
		boolean result = false;
		if(other instanceof Account) {
			Account otherAccount = (Account) other;
			if(number == otherAccount.getNumber() &&
					owner.equals(otherAccount.getOwner()) &&
					dateOpened.equals(otherAccount.getDateOpened()) &&
					balance == otherAccount.getBalance())
				result = true;
		}
		return result;
	}
	
	/*******************************************************************
    Converts the current Account to a string.
    
    @return Account in String format
    *******************************************************************/
	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy,MM,dd");
		Date date = dateOpened.getTime();
		String result = number + "," + owner.replace(",","") + "," + 
				sdf.format(date) + "," + balance;
		
		return result;
	}

	/*******************************************************************
    Gets the account number.
    
    @return account number
    *******************************************************************/
	public int getNumber() {
		return number;
	}

	/*******************************************************************
    Sets the account number.
    
    @param number account number
    *******************************************************************/
	public void setNumber(int number) {
		this.number = number;
	}

	/*******************************************************************
    Gets the account owner name.
    
    @return name of account owner
    *******************************************************************/
	public String getOwner() {
		return owner;
	}

	/*******************************************************************
    Sets the account owner name.
    
    @param owner name of account owner
    *******************************************************************/
	public void setOwner(String owner) {
		this.owner = owner;
	}

	/*******************************************************************
    Gets the date when the account was opened.
    
    @return date when the account was opened
    *******************************************************************/
	public GregorianCalendar getDateOpened() {
		return dateOpened;
	}

	/*******************************************************************
    Sets the date when the account was opened.
    
    @param dateOpened date when the account was opened
    *******************************************************************/
	public void setDateOpened(GregorianCalendar dateOpened) {
		this.dateOpened = dateOpened;
	}

	/*******************************************************************
    Gets the current balance.
    
    @return current account balance
    *******************************************************************/
	public double getBalance() {
		return balance;
	}

	/*******************************************************************
    Sets the current balance.
    
    @param balance current account balance
    *******************************************************************/
	public void setBalance(double balance) {
		this.balance = balance;
	}
}