package project3;
import java.io.*;
import java.util.*;
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
public class BankModel extends AbstractTableModel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/** ArrayList of type Account */
	private ArrayList<Account> accounts;
	
	/** Array type String that stores the column names */
	private static final String[] columnNames = {"Number","Account Owner",
		"Date Opened","Current Balance","Monthly Fee",
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
		switch(col) {
		case 0:
			int acctNum = acct.getNumber();
			return acctNum;
		case 1:
			String owner = acct.getOwner();
			return owner;
		case 2:
			SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			String acctDate = df.format(acct.getDateOpened().getTime());
			return acctDate;
		case 3:
			Double acctBal = acct.getBalance();
			return acctBal;
		case 4:
			if(acct instanceof CheckingAccount) {
				Double mnthlyFee = ((CheckingAccount) acct).getMonthlyFee();
				return mnthlyFee;
			} else {
				return "";
			}
		case 5:
			if(acct instanceof SavingsAccount) {
				Double intRate = ((SavingsAccount) acct).getInterestRate();
				return intRate;
			} else {
				return "";
			}
		case 6:
			if(acct instanceof SavingsAccount) {
				Double minBal = ((SavingsAccount) acct).getMinBalance();
				return minBal;
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
	
	/*******************************************************************
    Mutator method that adds an account to the ArrayList and updates
    the table
    
    @param a Account being added to the ArrayList
    *******************************************************************/
	public void add(Account a) {
		accounts.add(a);
		fireTableRowsInserted(accounts.indexOf(a),accounts.indexOf(a));
	}
	
	/*******************************************************************
    Mutator method that deletes an account from the ArrayList and 
    updates the table
    
    @param selectedAcct Index of the account to be deleted
    *******************************************************************/
	public void delete(int selectedAcct) {
		accounts.remove(selectedAcct);
		fireTableRowsDeleted(selectedAcct, selectedAcct);
	}
	
	/*******************************************************************
    Method that loads a binary file, creates a new bank model from
    the file, and updates the table.
    
    @return bankModel BankModel created from loaded data
    *******************************************************************/
	public BankModel loadSerializable() {
		try {
			BankModel bankModel;
			ObjectInputStream ois;
			
			FileInputStream file = new FileInputStream("data.bin");
			ois = new ObjectInputStream(file);
			bankModel = (BankModel) ois.readObject();
			ois.close();
			fireTableDataChanged();
			
			return bankModel;
			
		} catch(Exception e) {
			JOptionPane optionPane = new JOptionPane();
			JOptionPane.showMessageDialog(optionPane, "No such file type found!", "Error!", JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}
	
	/*******************************************************************
    Method that saves a binary file from the BankModel
    *******************************************************************/
	public void saveSerializable() {
		
		ObjectOutputStream oos = null;
		if(accounts.isEmpty()) {
			JOptionPane optionPane = new JOptionPane();
			JOptionPane.showMessageDialog(optionPane, "No data in the table!", "Error!", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		try {
			oos = new ObjectOutputStream(new FileOutputStream("data.bin"));
			oos.writeObject(this);
			oos.close();
		} catch (Exception e) {
			JOptionPane optionPane = new JOptionPane();
			JOptionPane.showMessageDialog(optionPane, "Oops something went wrong!", "Error!", JOptionPane.ERROR_MESSAGE);
		} 
	}

	/*******************************************************************
	Method that loads a text file, creates a new BankModel after parsing 
	the file, and updates the table.
    
    @return bankModel BankModel created from loaded data
    *******************************************************************/
	public BankModel loadText() {

		BankModel bankModel;
		Scanner fileReader;

		try {
			bankModel = new BankModel();
			fileReader = new Scanner(new File("data.txt"));

			while(fileReader.hasNextLine()) {
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
		catch(Exception error) {
			JOptionPane optionPane = new JOptionPane();
			JOptionPane.showMessageDialog(optionPane, "No such file type found!", "Error!", JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}

	/*******************************************************************
	Method that saves a text file from the BankModel
    *******************************************************************/
	public void saveText() {
		PrintWriter out = null;
		
		if(accounts.isEmpty()) {
			JOptionPane optionPane = new JOptionPane();
			JOptionPane.showMessageDialog(optionPane, "No data in table!", "Error!", JOptionPane.ERROR_MESSAGE);
		} else {
			try {
				out = new PrintWriter(new BufferedWriter(
					new FileWriter("data.txt")));
				for(int i = 0; i < accounts.size(); i++) {
					out.println(accounts.get(i).toString());
				}
			}
			catch (Exception e) {
				JOptionPane optionPane = new JOptionPane();
				JOptionPane.showMessageDialog(optionPane, "No data in table!", "Error!", JOptionPane.ERROR_MESSAGE);
			}
		
			out.close();
		}
	}
	
	/*******************************************************************
	Method that uses a comparator to sort the accounts in the ArrayList
	from lowest to highest by account number and updates the table
	 * @throws Exception 
    *******************************************************************/
	public void sortAccountNumber() throws Exception {
		if(!accounts.isEmpty()) {
			Collections.sort(accounts, new AccountNumberComparator());
			fireTableDataChanged();
		} else {
			throw new Exception();
		}
	}
	
	/*******************************************************************
	Method that uses a comparator to sort the accounts in the ArrayList 
	alphabetically by account name and updates the table
	@throws Exception
    *******************************************************************/
	public void sortAccountName() throws Exception {
		if(!accounts.isEmpty()) {	
			Collections.sort(accounts, new AccountNameComparator());
			fireTableDataChanged();
		} else {
			throw new Exception();
		}
	}
	
	/*******************************************************************
	Method that uses a comparator to sort the accounts in the ArrayList
	from oldest to newest by date opened and updates the table
	@throws Exception
    *******************************************************************/
	public void sortDateOpened() throws Exception {
		if(!accounts.isEmpty()) {
			Collections.sort(accounts, new DateOpenedComparator());
			fireTableDataChanged();
		} else {
			throw new Exception();
		}
	}
	
	/*******************************************************************
    Method that saves an XML file from the BankModel
    *******************************************************************/
	public void saveXML() {
		if (accounts.isEmpty()) {
			JOptionPane optionPane = new JOptionPane();
			JOptionPane.showMessageDialog(optionPane, "No data in table!", "Error!", JOptionPane.ERROR_MESSAGE);
			return;
		}
		try {

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("Account");
			doc.appendChild(rootElement);
			Element account;
			
			for(int i = 0; i < accounts.size(); i++) {
				if(isChecking(i)) {
					// type account elements
					account = doc.createElement("Checking");
					rootElement.appendChild(account);
				} else {
					// type account elements
					account = doc.createElement("Savings");
					rootElement.appendChild(account);
				}

				// account number elements
				Element accountNumber = doc.createElement("AccountNumber");
				accountNumber.appendChild(doc.createTextNode("" + getValueAt(i,0)));
				account.appendChild(accountNumber);

				// account name elements
				Element accountName = doc.createElement("AccountOwner");
				accountName.appendChild(doc.createTextNode("" + getValueAt(i,1)));
				account.appendChild(accountName);

				// date opened elements
				Element dateOpened = doc.createElement("DateOpened");
				dateOpened.appendChild(doc.createTextNode("" + getValueAt(i,2)));
				account.appendChild(dateOpened);

				// current balance elements
				Element currentBal = doc.createElement("CurrentBalance");
				currentBal.appendChild(doc.createTextNode("" + getValueAt(i,3)));
				account.appendChild(currentBal);
				
				if(isChecking(i)) {
					// monthly fee elements
					Element monthlyFee = doc.createElement("MonthlyFee");
					monthlyFee.appendChild(doc.createTextNode("" + getValueAt(i,4)));
					account.appendChild(monthlyFee);
				} else {
					// interest rate elements
					Element intRate = doc.createElement("InterstRate");
					intRate.appendChild(doc.createTextNode("" + getValueAt(i,5)));
					account.appendChild(intRate);
					
					// minimum balance elements
					Element minBal = doc.createElement("MinimumBalance");
					minBal.appendChild(doc.createTextNode("" + getValueAt(i,6)));
					account.appendChild(minBal);
				}
			}

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("file.xml"));
			
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			transformer.transform(source, result);

			System.out.println("File saved!");

		  } catch (ParserConfigurationException pce) {
			  JOptionPane optionPane = new JOptionPane();
			  JOptionPane.showMessageDialog(optionPane, "Oops something went wrong!", "Error!", JOptionPane.ERROR_MESSAGE);
		  } catch (TransformerException tfe) {
			  JOptionPane optionPane = new JOptionPane();
			  JOptionPane.showMessageDialog(optionPane, "Oops something went wrong!", "Error!", JOptionPane.ERROR_MESSAGE);
		  }        
	}
	
	/*******************************************************************
    Method that loads a binary file from the BankModel, creates a new 
    BankModel after parsing the file, and updates the table.
    
    @return bankModel BankModel created from loaded data
    *******************************************************************/
	public BankModel loadXML() {

		try {
			// 
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			DefaultHandler handler = new DefaultHandler() {

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

				public void startElement(String uri, String localName,String qName, 
						Attributes attributes) throws SAXException {

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

				public void endElement(String uri, String localName,
						String qName) throws SAXException {

					if(qName.equalsIgnoreCase("Checking")) {
						CheckingAccount acct = new CheckingAccount(iActNum, actOwner, dateOpened, dCurrentBal, dMonthlyFee);
						add(acct);
					}
					if(qName.equalsIgnoreCase("Savings")) {
						SavingsAccount acct = new SavingsAccount(iActNum, actOwner, dateOpened, dCurrentBal, dIntRate, dMinBal);
						add(acct);
					}

				}

				public void characters(char ch[], int start, int length) throws SAXException {

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
						dateOpened = new GregorianCalendar(year, month, day);
						bDateOpened = false;
					}

					if (bCurrentBal) {
						String currentBal = new String(ch, start, length);
						dCurrentBal = Double.valueOf(currentBal);
						bCurrentBal = false;
					}

					if(checking) {
						if (bMonthlyFee) {

							String monthlyFee = new String(ch, start, length);
							dMonthlyFee = Double.valueOf(monthlyFee);
							bMonthlyFee = false;
						}
					}

					if(savings) {
						if (bIntRate) {

							String intRate = new String(ch, start, length);
							dIntRate = Double.valueOf(intRate);
							bIntRate = false;
						}

						if (bMinBal) {
							String minBal = new String(ch, start, length);
							dMinBal = Double.valueOf(minBal);
							bMinBal = false;
						}
					}
				}
			};

			saxParser.parse("file.xml", handler);

		} catch (Exception e) {
			JOptionPane optionPane = new JOptionPane();
			JOptionPane.showMessageDialog(optionPane, "No such file found!", "Error!", JOptionPane.ERROR_MESSAGE);
		}

		fireTableDataChanged();
		return this;
	}


	/*******************************************************************
    Method that checks to see if the account type is a checking account
    
    @return checking Boolean true or false depending on if account type
    		is a checking account
    *******************************************************************/
	public boolean isChecking(int row) {
		boolean checking = false;
		Account acct = accounts.get(row);
		
		if (acct instanceof CheckingAccount) {
			checking = true;
		}
		
		return checking;
	}
}
