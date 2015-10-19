package project3;

import java.io.Serializable;
import java.util.GregorianCalendar;

public abstract class Account implements Serializable {
	
	private static final long serialVersionUID = 1L;
	protected int number;
	protected String owner;
	protected GregorianCalendar dateOpened;
	protected double balance;
	
	public Account(int number, String owner, 
			GregorianCalendar dateOpened, double balance) {

		this.number = number;
		this.owner = owner;
		this.dateOpened = dateOpened;
		this.balance = balance;
	}

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
	
	@Override
	public String toString() {
		String result = number + "," + owner + "," + dateOpened + "," + 
				balance;
//		String result = String.format("%d,%s,%tD,%f", number,owner,
//				dateOpened,balance);
		return result;
	}


	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public GregorianCalendar getDateOpened() {
		return dateOpened;
	}

	public void setDateOpened(GregorianCalendar dateOpened) {
		this.dateOpened = dateOpened;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
}
