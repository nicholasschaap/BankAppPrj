package project3;

import java.io.Serializable;
import java.util.GregorianCalendar;

public class CheckingAccount extends Account {
	
	private static final long serialVersionUID = 1L;
	private double monthlyFee;
	
	public CheckingAccount(int number, String owner,
			GregorianCalendar dateOpened, double balance,
			double monthlyFee) {
		
		super(number, owner, dateOpened, balance);
		
		this.setMonthlyFee(monthlyFee);
	}

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
	
	@Override
	public String toString() {
		String result = super.toString();
		result = result + "," + monthlyFee;
		
		return result;
	}

	public double getMonthlyFee() {
		return monthlyFee;
	}

	public void setMonthlyFee(double monthlyFee) {
		this.monthlyFee = monthlyFee;
	}

	@Override
	public int compare(Account a1, Account a2) {
		return 0;
	}
}