package project3;

import java.util.GregorianCalendar;
import com.toedter.calendar.JCalendar;

public class SavingsAccount extends Account {
	
	private static final long serialVersionUID = 1L;
	private double minBalance;
	private double interestRate;
	
	public SavingsAccount(int number, String owner,
			GregorianCalendar dateOpened, double balance, 
			double minBalance, double interestRate) {
		
		super(number, owner, dateOpened, balance);
		
		this.minBalance = minBalance;
		this.interestRate = interestRate;
	}

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
	
	@Override
	public String toString() {
		String result = super.toString();
		result += "," + minBalance + "," + interestRate;
		
		return result;
	}

	public double getMinBalance() {
		return minBalance;
	}

	public void setMinBalance(double minBalance) {
		this.minBalance = minBalance;
	}

	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}
}