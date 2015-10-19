package project3;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class BankModel extends AbstractTableModel {
	
	private static final long serialVersionUID = 1L;
	private ArrayList<Account> accounts;
	private static final String[] columnNames = {"Number","Date Opened",
			"Account Owner","Current Balance","Monthly Fee",
			"Interest Rate","Minimum Balance"};

	public BankModel() {
		accounts = new ArrayList<Account>();
	}
	
	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}
	
	@Override
	public int getColumnCount() {
		return columnNames.length; // 7
	}

	@Override
	public int getRowCount() {
		return accounts.size();
	}
	
	@Override
	public Object getValueAt(int row, int col) {
		Account acct = accounts.get(row);
		switch(col) {
		case 0:
			return acct.getNumber();
		case 1:
			return acct.getOwner();
		case 2:
			return acct.getDateOpened();
		case 3:
			return acct.getBalance();
		case 4:
			return ((CheckingAccount) acct).getMonthlyFee();
		case 5:
			return ((SavingsAccount) acct).getInterestRate();
		case 6:
			return ((SavingsAccount) acct).getMinBalance();
		default:
			return null;
		}
	}
	
	public void add(Account a) {
		accounts.add(a);
		fireTableRowsInserted(0, getRowCount() - 1);
	}
	
	public void delete(Account a) {
		accounts.remove(a);
		fireTableRowsDeleted(0, getRowCount() - 1);
	}
	
	public void update() {
		fireTableRowsUpdated(0, getRowCount() - 1);
	}


	
	
}
