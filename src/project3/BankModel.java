package project3;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import javax.swing.table.AbstractTableModel;

public class BankModel extends AbstractTableModel {
	
	private static final long serialVersionUID = 1L;
	private ArrayList<Account> accounts;
	private static final String[] columnNames = {"Number","Account Owner",
			"Date Opened","Current Balance","Monthly Fee",
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
			if(acct instanceof CheckingAccount) {
				return ((CheckingAccount) acct).getMonthlyFee();
			}
		case 5:
			if(acct instanceof SavingsAccount) {
				return ((SavingsAccount) acct).getInterestRate();
			}
		case 6:
			if(acct instanceof SavingsAccount) {
				return ((SavingsAccount) acct).getMinBalance();
			}
		default:
			return null;
		}
	}
	
	public void setValueAt(Object value, int row, int col) {
		Account acct = accounts.get(row);
		switch(col) {
		case 0:
			acct.setNumber(Integer.parseInt((String)value));
			break;
		case 1:
			acct.setOwner((String) value);
			break;
		case 2:
			acct.setDateOpened((GregorianCalendar) value);
			break;
		case 3:
			acct.setBalance(Double.valueOf((String)value));
			break;
		case 4:
			if(acct instanceof CheckingAccount) {
				((CheckingAccount)acct).setMonthlyFee(Double.valueOf((String)value));
			}
			break;
		case 5:
			if(acct instanceof SavingsAccount) {
				((SavingsAccount) acct).setInterestRate(Double.valueOf((String)value));
			}
			break;
		case 6:
			if(acct instanceof SavingsAccount) {
				((SavingsAccount) acct).setMinBalance(Double.valueOf((String)value));
			}
			break;
		}
		
		fireTableCellUpdated(row,col);
		
	}
	
	public void add(Account a) {
		accounts.add(a);
		fireTableRowsInserted(accounts.indexOf(a),accounts.indexOf(a));
	}
	
	public void delete(int selectedAcct) {
		accounts.remove(selectedAcct);
		fireTableRowsDeleted(selectedAcct, selectedAcct);
	}
	
	public void update(int selectedAcct) {
		
		fireTableRowsUpdated(selectedAcct, selectedAcct);
	}
	
//	public ArrayList<Account> loadSerializable() {
//		
//	}
//	
//	public void saveSerializable() {
//		
//	}
//	
//	public ArrayList<Account> loadText() {
//		
//	}
//	
//	public void saveText() {
//		
//	}
//	
//	public ArrayList<Account> loadXML() {
//		
//	}
//	
//	public void saveXML() {
//		
//	}
}
