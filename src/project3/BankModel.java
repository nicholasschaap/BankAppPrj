package project3;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.Scanner;

import javax.swing.table.AbstractTableModel;

import com.toedter.calendar.JCalendar;

public class BankModel extends AbstractTableModel implements Serializable{
	
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
	
//	@Override
//	public Class<?> getColumnClass(int col) {
//		switch(col) {
//		case 0:
//			return String.class;
//		case 1:
//			return String.class;
//		case 2:
//			return GregorianCalendar.class;
//		case 3:
//			return double.class;
//		case 4:
//			return double.class;
//		case 5:
//			return double.class;
//		case 6:
//			return double.class;
//		default:
//			throw new IndexOutOfBoundsException();
//		}
//	}
	
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
			return (DateFormat.getDateInstance(DateFormat.SHORT).format(acct.getDateOpened().getTime()));
		case 3:
			return acct.getBalance();
		case 4:
			if(acct instanceof CheckingAccount) {
				return ((CheckingAccount) acct).getMonthlyFee();
			} else {
				return "";
			}
		case 5:
			if(acct instanceof SavingsAccount) {
				return ((SavingsAccount) acct).getInterestRate();
			} else {
				return "";
			}
		case 6:
			if(acct instanceof SavingsAccount) {
				return ((SavingsAccount) acct).getMinBalance();
			} else {
				return "";
			}
		default:
			throw new IndexOutOfBoundsException();
		}
	}
	
	public void setValueAt(Object value, int row, int col) {
		Account acct = accounts.get(row);
		switch(col) {
		case 0:
			acct.setNumber((int)value);
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
		default:
			throw new IndexOutOfBoundsException();
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
	
	public BankModel loadSerializable() {
		
		BankModel bankModel;
		ObjectInputStream ois;
		
		try {
			FileInputStream file = new FileInputStream("data.bin");
			ois = new ObjectInputStream(file);
			bankModel = (BankModel) ois.readObject();
			ois.close();
			fireTableDataChanged();
			
			return bankModel;
			
		} catch(Exception e) {
			e.printStackTrace();
			
			return null;
		}
	}
	
	public void saveSerializable() {
		
		ObjectOutputStream oos = null;
		
		try {
			oos = new ObjectOutputStream(new FileOutputStream("data.bin"));
			oos.writeObject(this);
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	public BankModel loadText() {

		BankModel bankModel;

		try {
			Scanner fileReader = new Scanner(new File("data.txt"));

			bankModel = new BankModel();

			while(fileReader.hasNextLine() && fileReader.nextLine() != "") {
				String temp = fileReader.nextLine();
				String[] str = temp.split(",");
				if(str.length == 7) {
					int number = Integer.parseInt(str[0]);
					String owner = str[1];
					int year = Integer.parseInt(str[2]);
					int month = Integer.parseInt(str[3]);
					int day = Integer.parseInt(str[4]);
					GregorianCalendar dateOpened = new GregorianCalendar(year, month, day);
					double balance = Double.parseDouble(str[5]);
					double monthlyFee = Double.parseDouble(str[6]);
					CheckingAccount checking = new CheckingAccount(number, owner, dateOpened, balance, monthlyFee);
					bankModel.add(checking);
				} else if(str.length == 8) {
					int number = Integer.parseInt(str[0]);
					String owner = str[1];
					int year = Integer.parseInt(str[2]);
					int month = Integer.parseInt(str[3]);
					int day = Integer.parseInt(str[4]);
					GregorianCalendar dateOpened = new GregorianCalendar(year, month, day);
					double balance = Double.parseDouble(str[5]);
					double interestRate = Double.parseDouble(str[6]);
					double minBalance = Double.parseDouble(str[7]);
					SavingsAccount savings = new SavingsAccount(number, owner, dateOpened, balance, interestRate, minBalance);
					bankModel.add(savings);
				}
			}
			fileReader.close();
			fireTableDataChanged();
			return bankModel;
		}

		// could not find file
		catch(FileNotFoundException error) {
			System.out.println("File not found.");
			return null;
		}

		// problem reading the file
		catch(IOException error) {
			System.out.println("Oops!  Something went wrong.");
			return null;
		}
	}

	public void saveText() {
		PrintWriter out = null;

		try {
			out = new PrintWriter(new BufferedWriter(
					new FileWriter("data.txt")));
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		for(int i = 0; i < accounts.size(); i++) {
			out.println(accounts.get(i).toString());
		}
		out.close();
	}
	//	
	//	public BankModel loadXML() {
	//		
	//	}
	//	
	//	public void saveXML() {
	//		
	//	}
//	public void sortAccountNumber() {
//		Collections.sort(accounts,new Account() {
//
//			@Override
//			public int compare(Account arg0, Account arg1) {
//				// TODO Auto-generated method stub
//				return 0;
//			}
//			
//		});
//	}
	
	public void sortAccountName() {
		
	}
	
	public void sortDateOpened() {
		
	}
}
