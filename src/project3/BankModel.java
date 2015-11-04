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
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.Scanner;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import org.w3c.dom.*;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

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
	
	public void saveXML() {

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
//				// set attribute to staff element
//				Attr attr = doc.createAttribute("id");
//				attr.setValue("1");
//				staff.setAttributeNode(attr);
//
//				// shorten way
//				// staff.setAttribute("id", "1");


				// account number elements
				Element accountNumber = doc.createElement("AccountNumber");
				accountNumber.appendChild(doc.createTextNode("" + getValueAt(i,0)));
				account.appendChild(accountNumber);

				// account name elements
				Element accountName = doc.createElement("AccountOwner");
				accountName.appendChild(doc.createTextNode("" + getValueAt(i,1)));
				account.appendChild(accountName);

//				// date opened elements
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
			StreamResult result = new StreamResult(new File("C:\\file.xml"));

//			// Output to console for testing
//			StreamResult result = new StreamResult(System.out);
			
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			transformer.transform(source, result);

			System.out.println("File saved!");

		  } catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		  } catch (TransformerException tfe) {
			tfe.printStackTrace();
		  }
		        
	}
	
	public void loadXML() {
		try {

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

			public void startElement(String uri, String localName,String qName, 
					Attributes attributes) throws SAXException {

				System.out.println("Start Element :" + qName);

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

				if (qName.equalsIgnoreCase("InterestRate")) {
					bIntRate = true;
				}

				if (qName.equalsIgnoreCase("MinimumBalance")) {
					bMinBal = true;
				}

			}

			public void endElement(String uri, String localName,
					String qName) throws SAXException {

				System.out.println("End Element :" + qName);

			}
			
			public void characters(char ch[], int start, int length) throws SAXException {
				
				if (bActNum) {
					System.out.println("First Name : " + new String(ch, start, length));
					bActNum = false;
				}

				if (bActOwner) {
					System.out.println("Last Name : " + new String(ch, start, length));
					bActOwner = false;
				}

				if (bDateOpened) {
					System.out.println("Nick Name : " + new String(ch, start, length));
					bDateOpened = false;
				}

				if (bCurrentBal) {
					System.out.println("Salary : " + new String(ch, start, length));
					bCurrentBal = false;
				}
				
				if (bMonthlyFee) {
					System.out.println("Salary : " + new String(ch, start, length));
					bMonthlyFee = false;
				}

				if (bIntRate) {
					System.out.println("Salary : " + new String(ch, start, length));
					bIntRate = false;
				}
				
				if (bMinBal) {
					System.out.println("Salary : " + new String(ch, start, length));
					bMinBal = false;
				}


			}

			};

			saxParser.parse("c:\\file.xml", handler);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private boolean isChecking(int row) {
		boolean checking = false;
		Account acct = accounts.get(row);
		
		if (acct instanceof CheckingAccount) {
			checking = true;
		}
		
		return checking;
	}
}
