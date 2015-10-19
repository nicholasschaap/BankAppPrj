package project3;

import java.awt.*;

import javax.swing.*;

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
	private JTextField jtfAcctNumber, jtfAcctOwner, jtfDateOpened, 
		jtfAcctBalance, jtfMonthlyFee, jtfInterestRate, jtfMinBalance;
	private JTable acctTable;
	private JScrollPane scrollPane;
	private BankModel tableModel;
	
	public static void main(String[] args) {
		BankGUI frame = new BankGUI ("Bank Application");
	    frame.pack();
	    frame.setLocationRelativeTo(null);
	    //frame.setResizable(false);
	    frame.setVisible(true);
	}
	
	// constructor
	public BankGUI(String title) {
		super(title);
		//setSize(400,800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		// create menu
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		jmFile = new JMenu("File");
		jmiLoadBinary = new JMenuItem("Load From Binary...");
		jmiSaveBinary = new JMenuItem("Save As Binary...");
		jmiLoadText = new JMenuItem("Load From Text...");
		jmiSaveText = new JMenuItem("Save As Text...");
		jmiLoadXML = new JMenuItem("Load From XML...");
		jmiSaveXML = new JMenuItem("Save As XML...");
		jmiQuit = new JMenuItem("Quit");
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
		jmSort = new JMenu("Sort");
		jmiSortByAcctNumber = new JMenuItem("By Account Number");
		jmiSortByAcctOwner = new JMenuItem("By Account Owner");
		jmiSortByDateOpened = new JMenuItem("By Date Opened");
		jmSort.add(jmiSortByAcctNumber);
		jmSort.add(jmiSortByAcctOwner);
		jmSort.add(jmiSortByDateOpened);
		menuBar.add(jmFile);
		menuBar.add(jmSort);
		
		// create table
		tableModel = new BankModel();
		acctTable = new JTable(tableModel);
		scrollPane = new JScrollPane(acctTable);
		add(scrollPane, BorderLayout.CENTER);
		
		// create account type panel
		acctTypePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		jrbChecking = new JRadioButton("Checking", true);
		jrbSavings = new JRadioButton("Savings");
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
		jtfDateOpened = new JTextField();
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
		
		infoPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		infoPanel.add(acctTypePanel, c);
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.WEST;
		c.gridy = 1;
		c.gridwidth = 1;
		infoPanel.add(jlblAcctNumber, c); 
		c.gridy = 2;
		infoPanel.add(jlblAcctOwner, c);
		c.gridy = 3;
		infoPanel.add(jlblDateOpened, c);
		c.gridy = 4;
		infoPanel.add(jlblAcctBalance, c);
		c.gridy = 5;
		infoPanel.add(jlblMonthlyFee, c);
		c.gridy = 6;
		infoPanel.add(jlblInterestRate, c);
		c.gridy = 7;
		infoPanel.add(jlblMinBalance, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 1;
		infoPanel.add(jtfAcctNumber, c); 
		c.gridy = 2;
		infoPanel.add(jtfAcctOwner, c); 
		c.gridy = 3;
		infoPanel.add(jtfDateOpened, c); 
		c.gridy = 4;
		infoPanel.add(jtfAcctBalance, c); 
		c.gridy = 5;
		infoPanel.add(jtfMonthlyFee, c); 
		c.gridy = 6;
		infoPanel.add(jtfInterestRate, c); 
		c.gridy = 7;
		infoPanel.add(jtfMinBalance, c);
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 2;
		c.gridy = 0;
		c.gridheight = 8;
		infoPanel.add(buttonPanel, c);
		add(infoPanel, BorderLayout.SOUTH);
		setDefaultLookAndFeelDecorated(true);
	}
}
