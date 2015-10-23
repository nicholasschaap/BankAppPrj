package project3;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.event.*;
import javax.swing.*;
import javax.swing.table.*;
import com.toedter.calendar.*;

public class BankGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel acctTypePanel, infoPanel, buttonPanel;
	private JMenuBar menuBar;
	private JMenu jmFile, jmSort;
	private JMenuItem jmiLoadBinary, jmiSaveBinary, jmiLoadText, 
		jmiSaveText, jmiLoadXML, jmiSaveXML, jmiQuit, 
		jmiSortByAcctNumber, jmiSortByAcctOwner, jmiSortByDateOpened;
	private JButton jbtAdd, jbtDelete, jbtUpdate, jbtClear;
	private JRadioButton jrbChecking, jrbSavings;
	private ButtonGroup acctType;
	private JLabel jlblAcctNumber, jlblAcctOwner, jlblDateOpened, 
		jlblAcctBalance, jlblMonthlyFee, jlblInterestRate, 
		jlblMinBalance;
	private JTextField jtfAcctNumber, jtfAcctOwner, jtfAcctBalance, 
		jtfMonthlyFee, jtfInterestRate, jtfMinBalance;
	private JDateChooser jdcDateOpened;
	private JTable acctTable;
	private JScrollPane scrollPane;
	private BankModel tableModel;
	private ButtonListener listener;
	private ListSelectionModel listSelectionModel;
	
	public static void main(String[] args) {
		BankGUI frame = new BankGUI ("Bank Application");
	    frame.pack();
	    frame.setLocationRelativeTo(null);
//	    frame.setResizable(false);
	    setDefaultLookAndFeelDecorated(true);
	    frame.setVisible(true);
	}
	
	// constructor
	public BankGUI(String title) {
		super(title);
		//setSize(1000,800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		listener = new ButtonListener();
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 3;
		c.gridheight = 10;
		
		// create menu
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		jmFile = new JMenu("File");
		jmSort = new JMenu("Sort");
		
		//instantiate menu items
		jmiLoadBinary = new JMenuItem("Load From Binary...");
		jmiSaveBinary = new JMenuItem("Save As Binary...");
		jmiLoadText = new JMenuItem("Load From Text...");
		jmiSaveText = new JMenuItem("Save As Text...");
		jmiLoadXML = new JMenuItem("Load From XML...");
		jmiSaveXML = new JMenuItem("Save As XML...");
		jmiQuit = new JMenuItem("Quit");
		
		jmiSortByAcctNumber = new JMenuItem("By Account Number");
		jmiSortByAcctOwner = new JMenuItem("By Account Owner");
		jmiSortByDateOpened = new JMenuItem("By Date Opened");
		
		// add action listeners to menu items
		jmFile.addActionListener(listener);
		jmiLoadBinary.addActionListener(listener);
		jmiSaveBinary.addActionListener(listener);
		jmiLoadText.addActionListener(listener);
		jmiSaveText.addActionListener(listener);
		jmiLoadXML.addActionListener(listener);
		jmiSaveXML.addActionListener(listener);
		jmiQuit.addActionListener(listener);
		
		jmiSortByAcctNumber.addActionListener(listener);
		jmiSortByAcctOwner.addActionListener(listener);
		jmiSortByDateOpened.addActionListener(listener);
		
		//add menu items to correct menu lists
		jmFile.add(jmiLoadBinary);
		jmFile.add(jmiSaveBinary);
		jmFile.addSeparator();
		jmFile.add(jmiLoadText);
		jmFile.add(jmiSaveText);
		jmFile.addSeparator();
		jmFile.add(jmiLoadXML);
		jmFile.add(jmiSaveXML);
		jmFile.addSeparator();
		jmFile.add(jmiQuit);
		
		jmSort.add(jmiSortByAcctNumber);
		jmSort.add(jmiSortByAcctOwner);
		jmSort.add(jmiSortByDateOpened);
		
		//add menus to menu bar
		menuBar.add(jmFile);
		menuBar.add(jmSort);
		
		// create table
		tableModel = new BankModel();
		acctTable = new JTable(tableModel);;
		acctTable.setMinimumSize(new Dimension(600,200));
		// JTableHeader header = acctTable.getTableHeader();	
		scrollPane = new JScrollPane(acctTable);
		scrollPane.setMinimumSize(new Dimension(600, 23));
		acctTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		add(scrollPane, c);
		//add(header, c);
		
		TableColumn column = null;
		for (int i = 0; i < tableModel.getColumnCount(); i++) {
		    column = acctTable.getColumnModel().getColumn(i);
		        column.setPreferredWidth(100); //third column is bigger
		}
		
		listSelectionModel = acctTable.getSelectionModel();
		listSelectionModel.addListSelectionListener(
				new SharedListSelectionHandler());
		
		// create account type panel
		acctTypePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		jrbChecking = new JRadioButton("Checking", true);
		jrbSavings = new JRadioButton("Savings");
		
		jrbChecking.addActionListener(listener);
		jrbSavings.addActionListener(listener);
		
		acctType = new ButtonGroup();
		acctType.add(jrbChecking);
		acctType.add(jrbSavings);
		acctTypePanel.add(jrbChecking); 
		acctTypePanel.add(jrbSavings);
		acctType = new ButtonGroup();
		acctType.add(jrbChecking);
		acctType.add(jrbSavings);
		
		// create button panel
		buttonPanel = new JPanel(new GridLayout(4,1));
		jbtAdd = new JButton("Add");
		jbtDelete = new JButton("Delete");
		jbtUpdate = new JButton("Update");
		jbtClear = new JButton("Clear");
		
		jbtAdd.addActionListener(listener);
		jbtDelete.addActionListener(listener);
		jbtUpdate.addActionListener(listener);
		jbtClear.addActionListener(listener);
		
		buttonPanel.add(jbtAdd);
		buttonPanel.add(jbtDelete);
		buttonPanel.add(jbtUpdate);
		buttonPanel.add(jbtClear);
				
		// create account info panel
		infoPanel = new JPanel();
		jlblAcctNumber = new JLabel("Account Number: ");
		jtfAcctNumber = new JTextField();
		jlblAcctOwner = new JLabel("Account Owner: ");
		jtfAcctOwner = new JTextField();
		jlblDateOpened = new JLabel("Date Opened: ");
		jdcDateOpened = new JDateChooser();
		jdcDateOpened.setLocale(Locale.US);
		jlblAcctBalance = new JLabel("Account Balance: ");
		jtfAcctBalance = new JTextField();
		jlblMonthlyFee = new JLabel("Monthly Fee: ");
		jtfMonthlyFee = new JTextField();
		jlblInterestRate = new JLabel("Interest Rate: ");
		jtfInterestRate = new JTextField();
		jtfInterestRate.setEnabled(false);
		jlblMinBalance = new JLabel("Minimum Balance: ");
		jtfMinBalance = new JTextField();
		jtfMinBalance.setEnabled(false); 
		
		//infoPanel.setLayout(new GridBagLayout());
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 10;
		c.gridwidth = 2;
		c.gridheight = 1;
		add(acctTypePanel, c);
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.EAST;
		c.gridy = 11;
		c.gridwidth = 1;
		add(jlblAcctNumber, c); 
		c.gridy = 12;
		add(jlblAcctOwner, c);
		c.gridy = 13;
		add(jlblDateOpened, c);
		c.gridy = 14;
		add(jlblAcctBalance, c);
		c.gridy = 15;
		add(jlblMonthlyFee, c);
		c.gridy = 16;
		add(jlblInterestRate, c);
		c.gridy = 17;
		add(jlblMinBalance, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 11;
		add(jtfAcctNumber, c); 
		c.gridy = 12;
		add(jtfAcctOwner, c); 
		c.gridy = 13;
		add(jdcDateOpened, c); 
		c.gridy = 14;
		add(jtfAcctBalance, c); 
		c.gridy = 15;
		add(jtfMonthlyFee, c); 
		c.gridy = 16;
		add(jtfInterestRate, c); 
		c.gridy = 17;
		add(jtfMinBalance, c);
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 2;
		c.gridy = 10;
		c.gridheight = 8;
		add(buttonPanel, c);
		//add(infoPanel, BorderLayout.SOUTH);
//		setDefaultLookAndFeelDecorated(true);
	}
	
	private boolean checkingIsSelected() {
		if(jrbChecking.isSelected()) {
			return true;
		}
		return false;
	}
	
	private boolean savingsIsSelected() {
		if(jrbSavings.isSelected()) {
			return true;
		}
		return false;
	}

	private void addChecking() {
		int account = Integer.parseInt(jtfAcctNumber.getText());
		String owner = jtfAcctOwner.getText();
		
		Date date = jdcDateOpened.getDate();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		JCalendar dateOpened = new JCalendar(jdcDateOpened.getDate());
		
		double balance = Double.valueOf(jtfAcctBalance.getText());
		double monthlyFee = Double.valueOf(jtfMonthlyFee.getText());
	
		CheckingAccount checking = new CheckingAccount(account,owner,dateOpened,balance,monthlyFee);
		tableModel.add(checking);
	}
	
	private void addSavings() {
		int account = Integer.parseInt(jtfAcctNumber.getText());
		String owner = jtfAcctOwner.getText();
	
		Date date = jdcDateOpened.getDate();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		JCalendar dateOpened = new JCalendar(jdcDateOpened.getDate());
		
		double balance = Double.valueOf(jtfAcctBalance.getText());
		double minBalance = Double.valueOf(jtfMinBalance.getText());
		double interestRate = Double.valueOf(jtfInterestRate.getText());
	
		SavingsAccount savings = new SavingsAccount(account,owner,dateOpened,balance,minBalance,interestRate);
		tableModel.add(savings);
	}
	
	private GregorianCalendar getDate(String str) {
		String[] date = str.split("/");
		
		int year = Integer.parseInt(date[2]);
		int month = Integer.parseInt(date[0]);
		int day = Integer.parseInt(date[1]);
		GregorianCalendar dateOpened = new GregorianCalendar(year,month,day);
		
		return dateOpened;
	}
	
	private int rowIndex() {
		return acctTable.getSelectedRow();
	}
	
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == jrbChecking){ 
				jtfMinBalance.setText("");
				jtfInterestRate.setText("");
				jtfInterestRate.setEnabled(false);
				jtfMinBalance.setEnabled(false);
				jtfMonthlyFee.setEnabled(true);
			}

			if(e.getSource() == jrbSavings){
				jtfInterestRate.setEnabled(true);
				jtfMinBalance.setEnabled(true);
				jtfMonthlyFee.setText("");
				jtfMonthlyFee.setEnabled(false);
			}
			
			if(e.getSource() == jbtAdd) {
				if(jrbChecking.isSelected()) {
					addChecking();
				}
				
				if(jrbSavings.isSelected()) {
					addSavings();
				}
			}
			
			if(e.getSource() == jbtUpdate) {
				
				if(jtfAcctNumber.getText() != "") {
						tableModel.setValueAt(jtfAcctNumber.getText(), rowIndex(), 0);
				}
				if(jtfAcctOwner.getText() != "") {
						tableModel.setValueAt(jtfAcctOwner.getText(), rowIndex(), 1);
				}
				if(jdcDateOpened.toString() != "") {
						tableModel.setValueAt(getDate(jdcDateOpened.toString()), rowIndex(), 2);
				}
				if(jtfAcctBalance.getText() != "") {
						tableModel.setValueAt(jtfAcctBalance.getText(), rowIndex(), 3);
				}
					
				if(savingsIsSelected()) {
					if(jtfMinBalance.getText() != "") {
						tableModel.setValueAt(jtfMinBalance.getText(), rowIndex(), 6);
					}
					if(jtfInterestRate.getText() != "") {
						tableModel.setValueAt(jtfInterestRate.getText(), rowIndex(), 5);
					}
				}
					
				if(checkingIsSelected()) {
					if(jtfMonthlyFee.getText() != "") {
						tableModel.setValueAt(jtfMonthlyFee.getText(), rowIndex(), 4);
					}
				}
			}
			
			if(e.getSource() == jbtClear) {
				jtfAcctNumber.setText("");
				jtfAcctOwner.setText("");
				//jdcDateOpened.setDate();
				jtfAcctBalance.setText("");
				jtfMonthlyFee.setText("");
				jtfInterestRate.setText("");
				jtfMinBalance.setText("");
			}
			
			if(e.getSource() == jbtDelete) {
				tableModel.delete(rowIndex());
			}
			
			if(e.getSource() == jmiQuit) {
				if (JOptionPane.showConfirmDialog(null, 
						"Exit Bank Application?", "Confirm Exit",
						JOptionPane.YES_NO_OPTION) == 
						JOptionPane.YES_OPTION)
					System.exit(0);
			}
//			if(e.getSource() == rowIndex()) {
//				
//			}
		}
	}
	
	class SharedListSelectionHandler implements ListSelectionListener {
	    public void valueChanged(ListSelectionEvent e) {
	    	ListSelectionModel lsm = (ListSelectionModel)e.getSource();
	    	
	    	if (lsm.isSelectionEmpty()) {
	    		JOptionPane error = new JOptionPane("No accounts!");
            } else {
                // Find out which indexes are selected.
                int minIndex = lsm.getMinSelectionIndex();
                int maxIndex = lsm.getMaxSelectionIndex();
                for (int i = minIndex; i <= maxIndex; i++) {
                    if (lsm.isSelectedIndex(i)) {
                    	jtfAcctNumber.setText(""+ acctTable.getValueAt(i,0));
                    	jtfAcctOwner.setText("" + acctTable.getValueAt(i, 1));
                    	jtfDateOpened.setText("" + acctTable.getValueAt(i, 2));
                		jtfAcctBalance.setText("" + acctTable.getValueAt(i, 3));
                    	if (acctTable.getValueAt(i,4) == null) {
                    		jtfMonthlyFee.setText("");
                    	} else{
                    		jtfMonthlyFee.setText("" + acctTable.getValueAt(i, 4));
                    	}
                    	if (acctTable.getValueAt(i,5) == null) {
                    		jtfInterestRate.setText("");
                    	} else{
                    		jtfInterestRate.setText("" + acctTable.getValueAt(i, 5));
                    	}
                    	if (acctTable.getValueAt(i,6) == null) {
                    		jtfMinBalance.setText("");
                    	} else{
                    		jtfMinBalance.setText("" + acctTable.getValueAt(i, 6));
                    	}
                    }
                }
            }
	    }
	}
}
