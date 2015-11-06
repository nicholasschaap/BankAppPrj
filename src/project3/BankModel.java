package project3;
import java.io.*;
import java.util.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import org.w3c.dom.*;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

/***********************************************************************
Public BankModel class which determines the properties and methods 
inherited by child classes.

@author Amanda Buhr, Nicholas Schaap
@version 1.0
***********************************************************************/
public class BankModel extends AbstractTableModel 
		implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/** ArrayList of type Account */
	private ArrayList<Account> accounts;
	
	/** Array type String that stores the column names */
	private static final String[] columnNames = {"Number",
		"Account Owner","Date Opened","Current Balance","Monthly Fee",
		"Interest Rate","Minimum Balance"};

	/*******************************************************************
    Constructor that initializes an ArrayList of Accounts
    *******************************************************************/
	public BankModel() {
		accounts = new ArrayList<Account>();
	}
	
	/*******************************************************************
    Accessor method that gets the name of a column passed to it.
    
    @param col Given column
    @return columnNames[col] Stored name of the column from the array
    *******************************************************************/
	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}
	
	/*******************************************************************
    Accessor method that gets the total number of columns stored in
    columnNames
    
    @return columnNames.length Length of the array columnNames
    *******************************************************************/
	@Override
	public int getColumnCount() {
		return columnNames.length; // 7
	}

	/*******************************************************************
    Accessor method that gets the total number of rows in the model
    
    @return accounts.size() Size of the array holding accounts
    *******************************************************************/
	@Override
	public int getRowCount() {
		return accounts.size();
	}
	
	/*******************************************************************
    Accessor method that gets a specific value type from an account
    
    @param row The index the account is stored in
    @param col The column the information wanted is stored in
    @return acctNum Object Account number
    @return owner Object Account owner
    @return acctDate Object Date account was opened
    @return acctBal Object Balance of account
    @return mnthlyFee Object Monthly fee of checking account
    @return intRate Object Interest rate of savings account
    @return minBal Object Minimum balance of savings account
    @return "" Object that represents an empty string
    @throw IndexOutOfBoundsException When there is not an account at 
    	   the given row
    *******************************************************************/
	@Override
	public Object getValueAt(int row, int col) {
		Account acct = accounts.get(row);
		DecimalFormat df = new DecimalFormat("0.00");
		
		// checks which column and gets appropriate data
		switch(col) {
		case 0:
			int acctNum = acct.getNumber();
			return acctNum;
		case 1:
			String owner = acct.getOwner().trim();
			return owner;
		case 2:
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			String acctDate = sdf.format(
					acct.getDateOpened().getTime());
			return acctDate;
		case 3:
			Double acctBal = acct.getBalance();
			return df.format(acctBal);
		case 4:
			if(acct instanceof CheckingAccount) {
				Double monthlyFee = 
						((CheckingAccount) acct).getMonthlyFee();
				return df.format(monthlyFee);
			} else {
				return "";
			}
		case 5:
			if(acct instanceof SavingsAccount) {
				Double intRate = 
						((SavingsAccount) acct).getInterestRate();
				return df.format(intRate);
			} else {
				return "";
			}
		case 6:
			if(acct instanceof SavingsAccount) {
				Double minBal = ((SavingsAccount) acct).getMinBalance();
				return df.format(minBal);
			} else {
				return "";
			}
		default:
			throw new IndexOutOfBoundsException();
		}
	}
	
	/*******************************************************************
    Mutator method that sets a specific value type for an account and
    updates the table
    
    @param value Object that will get set to an account parameter
    @param row The index the account is stored in
    @param col The column the information wanted is stored in
    @throw IndexOutOfBoundsException When there is not an account at 
    	   the given row
    *******************************************************************/
	public void setValueAt(Object value, int row, int col) {
		Account acct = accounts.get(row);
		
		// checks which column and sets appropriate data
		switch(col) {
		case 0:
			acct.setNumber(Integer.parseInt((String) value));
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
				((CheckingAccount)acct).setMonthlyFee(
						Double.valueOf((String)value));
			}
			break;
		case 5:
			if(acct instanceof SavingsAccount) {
				((SavingsAccount) acct).setInterestRate(
						Double.valueOf((String)value));
			}
			break;
		case 6:
			if(acct instanceof SavingsAccount) {
				((SavingsAccount) acct).setMinBalance(
						Double.valueOf((String)value));
			}
			break;
		default:
			throw new IndexOutOfBoundsException();
		}
		
		// tells table to update specific entry
		fireTableCellUpdated(row,col);

	}
	
	/*******************************************************************
    Mutator method that adds an account to the ArrayList and updates
    the table
    
    @param a Account being added to the ArrayList
    *******************************************************************/
	public void add(Account a) {
		accounts.add(a);
		
		// tells table which row to update
		fireTableRowsInserted(accounts.indexOf(a),accounts.indexOf(a));
	}
	
	/*******************************************************************
    Mutator method that deletes an account from the ArrayList and 
    updates the table
    
    @param selectedAcct Index of the account to be deleted
    *******************************************************************/
	public void delete(int selectedAcct) {
		accounts.remove(selectedAcct);
		
		// tells table to update specific account
		fireTableRowsDeleted(selectedAcct, selectedAcct);
	}
	
	/*******************************************************************
    Method that loads a binary file, creates a new bank model from
    the file, and updates the table.
    
    @return bankModel BankModel created from loaded data
    *******************************************************************/
	public BankModel loadSerializable() {
		
		BankModel bankModel;
		ObjectInputStream ois;
		
		// tries to load file, add to BankModel, and update table
		try {
			FileInputStream file = new FileInputStream("data.bin");
			ois = new ObjectInputStream(file);
			bankModel = (BankModel) ois.readObject();
			ois.close();
			fireTableDataChanged();
			
			return bankModel;
			
		} 
		// catches exception and displays error message
		catch(Exception e) {
			JOptionPane optionPane = new JOptionPane();
			JOptionPane.showMessageDialog(optionPane, 
					"No such file type found!", "Error!", 
					JOptionPane.ERROR_MESSAGE);
			
			return this;
		}
	}
	
	/*******************************************************************
    Method that saves a binary file from the BankModel
    *******************************************************************/
	public void saveSerializable() {
		
		ObjectOutputStream oos = null;
		
		// tries to save data from table
		try {
			oos = new ObjectOutputStream(new FileOutputStream(
					"data.bin"));
			oos.writeObject(this);
			oos.close();
		} 
		// catches exception and displays error message
		catch (Exception e) {
			JOptionPane optionPane = new JOptionPane();
			JOptionPane.showMessageDialog(optionPane, 
					"No data in the table!", "Error!", 
					JOptionPane.ERROR_MESSAGE);
		} 
	}

	/*******************************************************************
	Method that loads a text file, creates a new BankModel after parsing 
	the file, and updates the table.
    
    @return bankModel BankModel created from loaded data
    *******************************************************************/
	public BankModel loadText() {

		BankModel bankModel;
		
		// tries to load file, add to BankModel, and updates table
		try {
			Scanner fileReader = new Scanner(new File("data.txt"));

			bankModel = new BankModel();

			while(fileReader.hasNextLine()) {
				String temp = fileReader.nextLine();
				String[] str = temp.split(",");
				
				// parses checking account (7 pieces of info)
				if(str.length == 7) {
					int number = Integer.parseInt(str[0]);
					String owner = str[1];
					int year = Integer.parseInt(str[2]);
					int month = Integer.parseInt(str[3]);
					int day = Integer.parseInt(str[4]);
					GregorianCalendar dateOpened = 
							new GregorianCalendar(year, month, day);
					double balance = Double.parseDouble(str[5]);
					double monthlyFee = Double.parseDouble(str[6]);
					CheckingAccount checking = new CheckingAccount(
							number, owner, dateOpened, balance, 
							monthlyFee);
					bankModel.add(checking);
				} 
				// else parses savings account (8 pieces of info)
				else if(str.length == 8) {
					int number = Integer.parseInt(str[0]);
					String owner = str[1];
					int year = Integer.parseInt(str[2]);
					int month = Integer.parseInt(str[3]);
					int day = Integer.parseInt(str[4]);
					GregorianCalendar dateOpened = 
							new GregorianCalendar(year, month, day);
					double balance = Double.parseDouble(str[5]);
					double interestRate = Double.parseDouble(str[6]);
					double minBalance = Double.parseDouble(str[7]);
					SavingsAccount savings = new SavingsAccount(
							number, owner, dateOpened, balance, 
							interestRate, minBalance);
					bankModel.add(savings);
				}
			}
			fileReader.close();
			fireTableDataChanged();
			return bankModel;
		}

		// could not find file type
		catch(FileNotFoundException error) {
			JOptionPane optionPane = new JOptionPane();
			JOptionPane.showMessageDialog(optionPane, 
					"No such file type found!", "Error!", 
					JOptionPane.ERROR_MESSAGE);
			return this;
		}
	}

	/*******************************************************************
	Method that saves a text file from the BankModel
    *******************************************************************/
	public void saveText() {
		PrintWriter out = null;
		
		// tries to save data from BankModel to .txt file
		try {
			out = new PrintWriter(new BufferedWriter(
					new FileWriter("data.txt")));
		}
		catch (IOException e) {
			JOptionPane optionPane = new JOptionPane();
			JOptionPane.showMessageDialog(optionPane, 
					"No data in table!", "Error!", 
					JOptionPane.ERROR_MESSAGE);
		}
		
		// writes account information
		for(int i = 0; i < accounts.size(); i++) {
			out.println(accounts.get(i).toString());
		}
		out.close();
	}
	
	/*******************************************************************
	Method that uses a comparator to sort the accounts in the ArrayList
	from lowest to highest by account number and updates the table
    *******************************************************************/
	public void sortAccountNumber() {
		Collections.sort(accounts, new AccountNumberComparator());
		fireTableDataChanged();
	}
	
	/*******************************************************************
	Method that uses a comparator to sort the accounts in the ArrayList 
	alphabetically by account name and updates the table
    *******************************************************************/
	public void sortAccountName() {
		Collections.sort(accounts, new AccountNameComparator());
		fireTableDataChanged();
	}
	
	/*******************************************************************
	Method that uses a comparator to sort the accounts in the ArrayList
	from oldest to newest by date opened and updates the table
    *******************************************************************/
	public void sortDateOpened() {
		Collections.sort(accounts, new DateOpenedComparator());
		fireTableDataChanged();
	}
	
	/*******************************************************************
    Method that saves an XML file from the BankModel
    *******************************************************************/
	public void saveXML() {
		
		// if BankModel has no accounts, displays error, leaves method
		if (accounts.isEmpty()) {
			JOptionPane optionPane = new JOptionPane();
			JOptionPane.showMessageDialog(optionPane, 
					"No data in table!", "Error!", 
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		// tries to make an xml document from data
		try {

			DocumentBuilderFactory docFactory = 
					DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("Account");
			doc.appendChild(rootElement);
			Element account;
			
			// iterates through ArrayList
			for(int i = 0; i < accounts.size(); i++) {
				// if checking account, creates root element
				if(isChecking(i)) {
					// type account elements
					account = doc.createElement("Checking");
					rootElement.appendChild(account);
				} 
				// else if savings account, creates root element
				else {
					// type account elements
					account = doc.createElement("Savings");
					rootElement.appendChild(account);
				}

				// account number elements
				Element accountNumber = 
						doc.createElement("AccountNumber");
				accountNumber.appendChild(doc.createTextNode(
						"" + getValueAt(i,0)));
				account.appendChild(accountNumber);

				// account owner elements
				Element accountName = doc.createElement("AccountOwner");
				accountName.appendChild(doc.createTextNode(
						"" + getValueAt(i,1)));
				account.appendChild(accountName);

				// date opened elements
				Element dateOpened = doc.createElement("DateOpened");
				dateOpened.appendChild(doc.createTextNode(
						"" + getValueAt(i,2)));
				account.appendChild(dateOpened);

				// current balance elements
				Element currentBal = doc.createElement("CurrentBalance");
				currentBal.appendChild(doc.createTextNode(
						"" + getValueAt(i,3)));
				account.appendChild(currentBal);
				
				// only appendChild if type checking account
				if(isChecking(i)) {
					// monthly fee elements
					Element monthlyFee = 
							doc.createElement("MonthlyFee");
					monthlyFee.appendChild(doc.createTextNode(
							"" + getValueAt(i,4)));
					account.appendChild(monthlyFee);
				} 
				// only appendChild if type savings account
				else {
					// interest rate elements
					Element intRate = doc.createElement("InterstRate");
					intRate.appendChild(doc.createTextNode(
							"" + getValueAt(i,5)));
					account.appendChild(intRate);
					
					// minimum balance elements
					Element minBal = 
							doc.createElement("MinimumBalance");
					minBal.appendChild(doc.createTextNode(
							"" + getValueAt(i,6)));
					account.appendChild(minBal);
				}
			}

			// write the content into xml file
			TransformerFactory transformerFactory = 
					TransformerFactory.newInstance();
			Transformer transformer = 
					transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = 
					new StreamResult(new File("file.xml"));
			
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(
					"{http://xml.apache.org/xslt}indent-amount", "2");
			transformer.transform(source, result);

			System.out.println("File saved!");

		  } 
			// for both, catches Exception and displays error message
			catch (ParserConfigurationException pce) {
			  JOptionPane optionPane = new JOptionPane();
			  JOptionPane.showMessageDialog(optionPane, 
					  "Oops something went wrong!", "Error!", 
					  JOptionPane.ERROR_MESSAGE);
		  } catch (TransformerException tfe) {
			  JOptionPane optionPane = new JOptionPane();
			  JOptionPane.showMessageDialog(optionPane, 
					  "Oops something went wrong!", "Error!", 
					  JOptionPane.ERROR_MESSAGE);
		  }        
	}
	
	/*******************************************************************
    Method that loads a binary file from the BankModel, creates a new 
    BankModel after parsing the file, and updates the table.
    
    @return bankModel BankModel created from loaded data
    *******************************************************************/
	public BankModel loadXML() {

		// tries to readXML file, add to BankModel, and update table
		try {
			// 
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			DefaultHandler handler = new DefaultHandler() {

				// local variables to help switch through elements
				boolean bActNum = false;
				boolean bActOwner = false;
				boolean bDateOpened = false;
				boolean bCurrentBal = false;
				boolean bMonthlyFee = false;
				boolean bIntRate = false;
				boolean bMinBal = false;
				boolean checking;
				boolean savings;

				Integer iActNum = 0;
				String actOwner = "";
				GregorianCalendar dateOpened = new GregorianCalendar();
				Double dCurrentBal = 0.0;
				Double dMonthlyFee = 0.0;
				Double dIntRate = 0.0;
				Double dMinBal = 0.0;

				/*******************************************************
				 Method that checks if the start element matches
				 different data types for account.

				 @param uri
				 @param localName
				 @param qName String name of element
				 @param attributes
				 ******************************************************/
				public void startElement(String uri, String localName,
						String qName, Attributes attributes) 
								throws SAXException {

					if(qName.equalsIgnoreCase("Checking")) {
						checking = true;
						savings = false;
					}

					if(qName.equalsIgnoreCase("Savings")) {
						savings = true;
						checking = false;
					}

					if (qName.equalsIgnoreCase("AccountNumber")) {
						bActNum = true;
					}

					if (qName.equalsIgnoreCase("AccountOwner")) {
						bActOwner = true;
					}

					if (qName.equalsIgnoreCase("DateOpened")) {
						bDateOpened = true;
					}

					if (qName.equalsIgnoreCase("CurrentBalance")) {
						bCurrentBal = true;
					}

					if (qName.equalsIgnoreCase("MonthlyFee")) {
						bMonthlyFee = true;
					}

					if (qName.equalsIgnoreCase("AccountNumber")) {
						bActNum = true;
					}

					if (qName.equalsIgnoreCase("AccountOwner")) {
						bActOwner = true;
					}

					if (qName.equalsIgnoreCase("DateOpened")) {
						bDateOpened = true;
					}

					if (qName.equalsIgnoreCase("CurrentBalance")) {
						bCurrentBal = true;
					}

					if (qName.equalsIgnoreCase("InterestRate")) {
						bIntRate = true;
					}

					if (qName.equalsIgnoreCase("MinimumBalance")) {
						bMinBal = true;
					}

				}

				/*******************************************************
				 Method that checks if the end element matches
				 different data types for account.

				 @param uri
				 @param localName
				 @param qName String name of element
				 @param attributes
				 ******************************************************/
				public void endElement(String uri, String localName,
						String qName) throws SAXException {

					// if end element is checking, creates checking
					// account with data gathered from child elements
					if(qName.equalsIgnoreCase("Checking")) {
						CheckingAccount acct = new CheckingAccount(
								iActNum, actOwner, dateOpened, 
								dCurrentBal, dMonthlyFee);
						add(acct);
					}

					// if end element is savings, creates savings
					// account with data gathered from child elements
					if(qName.equalsIgnoreCase("Savings")) {
						SavingsAccount acct = new SavingsAccount(
								iActNum, actOwner, dateOpened, 
								dCurrentBal, dIntRate, dMinBal);
						add(acct);
					}

				}

				/*******************************************************
				 Method that takes the characters from child elements
				 to create a string.

				 @param ch[] array of characters from child element
				 @param start int starting place
				 @param length int ending place 
				 @throws SAXException 
				 ******************************************************/
				public void characters(char ch[], int start, int length) 
						throws SAXException {
					// if child elements are true, create a string
					if (bActNum) {
						String actNum = new String(ch, start, length);
						iActNum = Integer.parseInt(actNum);
						bActNum = false;
					}

					if (bActOwner) {
						actOwner = new String(ch, start, length);
						bActOwner = false;
					}

					if (bDateOpened) {
						String date = new String(ch, start, length);
						String[] str = date.split("/");
						int year = Integer.parseInt(str[2]);
						int month = Integer.parseInt(str[0]);
						int day = Integer.parseInt(str[1]);
						dateOpened = new GregorianCalendar(year, month, 
								day);
						bDateOpened = false;
					}

					if (bCurrentBal) {
						String currentBal = 
								new String(ch, start, length);
						dCurrentBal = Double.valueOf(currentBal);
						bCurrentBal = false;
					}

					// only check these elements if checking is true
					if(checking) {
						if (bMonthlyFee) {

							String monthlyFee = 
									new String(ch, start, length);
							dMonthlyFee = Double.valueOf(monthlyFee);
							bMonthlyFee = false;
						}
					}

					// only check these elements if savings is true
					if(savings) {
						if (bIntRate) {

							String intRate = 
									new String(ch, start, length);
							dIntRate = Double.valueOf(intRate);
							bIntRate = false;
						}

						if (bMinBal) {
							String minBal = 
									new String(ch, start, length);
							dMinBal = Double.valueOf(minBal);
							bMinBal = false;
						}
					}
				}
			};

			// parse given xml file
			saxParser.parse("file.xml", handler);

		} 
		// catches exceptions and displays error message
		catch (Exception e) {
			JOptionPane optionPane = new JOptionPane();
			JOptionPane.showMessageDialog(optionPane, 
					"No such file found!", "Error!", 
					JOptionPane.ERROR_MESSAGE);
		}

		// update table
		fireTableDataChanged();
		return this;
	}


	/*******************************************************************
    Method that checks to see if the account type is a checking account
    
    @return checking Boolean true or false if account type is a 
    		checking account
    *******************************************************************/
	public boolean isChecking(int row) {
		boolean checking = false;
		Account acct = accounts.get(row);
		
		// if instance of CheckingAccount return true, else assume not 
		// checking account (false)
		if (acct instanceof CheckingAccount) {
			checking = true;
		}
		
		return checking;
	}
}
