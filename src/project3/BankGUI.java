package project3;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.*;
import javax.swing.*;
import javax.swing.table.*;

import com.toedter.calendar.*;

public class BankGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel acctTypePanel, infoPanel, buttonPanel, lowerPanel;
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
	    //frame.pack();
	    frame.setLocationRelativeTo(null);
	    //frame.setResizable(false);
	    setDefaultLookAndFeelDecorated(true);
	    frame.setVisible(true);
	}
	
	// frame constructor
	public BankGUI(String title) {
		super(title);
		setSize(800,550);
		setMinimumSize(new Dimension(600,450));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		listener = new ButtonListener();
	
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
		acctTable = new JTable(tableModel);	
		scrollPane = new JScrollPane(acctTable);
		scrollPane.setPreferredSize(new Dimension(800,300));
		acctTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		add(scrollPane);
		acctTable.setRowSelectionAllowed(true);
		acctTable.setColumnSelectionAllowed(false);
		acctTable.setSelectionBackground(Color.LIGHT_GRAY);
		Border loweredBevel = BorderFactory.createBevelBorder(
				BevelBorder.LOWERED);
		Border padSidesTop = BorderFactory.createEmptyBorder(
				10,10,10,10);
		Border tableBorder = BorderFactory.createCompoundBorder(
				padSidesTop, loweredBevel);
		scrollPane.setBorder(tableBorder);
		
		// set alignments
		DefaultTableCellRenderer right = 
				new DefaultTableCellRenderer();
		right.setHorizontalAlignment(JLabel.RIGHT);
		acctTable.getColumnModel().getColumn(3).setCellRenderer(right);
		acctTable.getColumnModel().getColumn(4).setCellRenderer(right);
		acctTable.getColumnModel().getColumn(5).setCellRenderer(right);
		acctTable.getColumnModel().getColumn(6).setCellRenderer(right);
		DefaultTableCellRenderer center = 
				new DefaultTableCellRenderer();
		center.setHorizontalAlignment(JLabel.CENTER);
		acctTable.getColumnModel().getColumn(2).setCellRenderer(center);
		
		TableColumn column = null;
		for (int i = 0; i < tableModel.getColumnCount(); i++) {
		    column = acctTable.getColumnModel().getColumn(i);
		    	if(i == 0 || i == 4 || i == 5) {
		    		column.setPreferredWidth(80);
		    		column.setMinWidth(60);
				} else {
		    		column.setPreferredWidth(100);
		    		column.setMinWidth(70);
				}
		}
		
		listSelectionModel = acctTable.getSelectionModel();
		listSelectionModel.addListSelectionListener(
				new SharedListSelectionHandler());
		listSelectionModel.setSelectionMode(
				listSelectionModel.SINGLE_SELECTION);
		
		
		// create account type panel with radio buttons
		acctTypePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		jrbChecking = new JRadioButton("Checking", true);
		jrbSavings = new JRadioButton("Savings");
		
		// add action listeners to radio buttons
		jrbChecking.addActionListener(listener);
		jrbSavings.addActionListener(listener);
		
		// create group for account type radio buttons
		acctType = new ButtonGroup();
		acctType.add(jrbChecking);
		acctType.add(jrbSavings);
		
		// add radio buttons to acctTypePanel
		acctTypePanel.add(jrbChecking); 
		acctTypePanel.add(jrbSavings);

		// create account info panel
		infoPanel = new JPanel(new GridBagLayout());
		Border padSidesBottom = BorderFactory.createEmptyBorder(
				0,10,5,10);
		infoPanel.setBorder(padSidesBottom);
		GridBagConstraints c = new GridBagConstraints();

		jlblAcctNumber = new JLabel("Account Number: ");
		jlblAcctOwner = new JLabel("Account Owner: ");
		jlblDateOpened = new JLabel("Date Opened: ");
		jlblAcctBalance = new JLabel("Account Balance: ");
		jlblMonthlyFee = new JLabel("Monthly Fee: ");
		jlblInterestRate = new JLabel("Interest Rate: ");
		jlblMinBalance = new JLabel("Minimum Balance: ");

		jtfAcctNumber = new JTextField();
		jtfAcctOwner = new JTextField();
		jdcDateOpened = new JDateChooser();
		jdcDateOpened.setLocale(Locale.US);
		JTextFieldDateEditor editor = 
				(JTextFieldDateEditor) jdcDateOpened.getDateEditor();
		editor.setEditable(false);
		jtfAcctBalance = new JTextField();
		jtfMonthlyFee = new JTextField();
		jtfInterestRate = new JTextField();
		jtfInterestRate.setEnabled(false);
		jtfMinBalance = new JTextField();
		jtfMinBalance.setEnabled(false); 

		c.fill = GridBagConstraints.NONE;
		c.gridwidth = 2;
		c.weightx = 0;
		c.gridx = 0;
		c.gridy = 0;
		infoPanel.add(acctTypePanel, c);
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.EAST;
		c.gridwidth = 1;
		c.gridy++;
		c.insets = new Insets(0,100,1,5);
		infoPanel.add(jlblAcctNumber, c);
		c.gridy++;
		infoPanel.add(jlblAcctOwner, c);
		c.gridy++;
		infoPanel.add(jlblDateOpened, c);
		c.gridy++;
		infoPanel.add(jlblAcctBalance, c);
		c.gridy++;
		infoPanel.add(jlblMonthlyFee, c);
		c.gridy++;
		infoPanel.add(jlblInterestRate, c);
		c.gridy++;
		infoPanel.add(jlblMinBalance, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.WEST;
		c.weightx = 1.0;
		c.gridx = 1;
		c.gridy = 1;
		c.insets = new Insets(0,0,1,30);
		infoPanel.add(jtfAcctNumber, c);
		c.gridy++;
		infoPanel.add(jtfAcctOwner, c);
		c.gridy++;
		infoPanel.add(jdcDateOpened, c);
		c.gridy++;
		infoPanel.add(jtfAcctBalance, c);
		c.gridy++;
		infoPanel.add(jtfMonthlyFee, c);
		c.gridy++;
		infoPanel.add(jtfInterestRate, c);
		c.gridy++;
		infoPanel.add(jtfMinBalance, c);
		
		// add key listeners to text fields to check input validity
		jtfAcctNumber.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent event) {

				if (isStringInt(jtfAcctNumber)) {
					jtfAcctNumber.setForeground(Color.BLACK);
				} else {
					jtfAcctNumber.setForeground(Color.RED);
				}
			}
		});
		
		jtfAcctBalance.addKeyListener(new KeyAdapter() {
		    public void keyReleased(KeyEvent event) {

		        if (isStringDouble(jtfAcctBalance)) {
		        	jtfAcctBalance.setForeground(Color.BLACK);
		        } else {
		        	jtfAcctBalance.setForeground(Color.RED);
		        }
		    }
		});
		
		jtfAcctOwner.addKeyListener(new KeyAdapter() {
		    public void keyReleased(KeyEvent event) {

		        if (jtfAcctOwner.getText().matches(".*\\d+.*")) {
		        	jtfAcctOwner.setForeground(Color.RED);
		        } else {
		        	jtfAcctOwner.setForeground(Color.BLACK);
		        }
		    }
		});
		
		jtfMonthlyFee.addKeyListener(new KeyAdapter() {
		    public void keyReleased(KeyEvent event) {

		        if (isStringDouble(jtfMonthlyFee)) {
		        	jtfMonthlyFee.setForeground(Color.BLACK);
		        } else {
		        	jtfMonthlyFee.setForeground(Color.RED);
		        }
		    }
		});
		
		jtfInterestRate.addKeyListener(new KeyAdapter() {
		    public void keyReleased(KeyEvent event) {

		        if (isStringDouble(jtfInterestRate)) {
		        	jtfInterestRate.setForeground(Color.BLACK);
		        } else {
		        	jtfInterestRate.setForeground(Color.RED);
		        }
		    }
		});
		
		jtfMinBalance.addKeyListener(new KeyAdapter() {
		    public void keyReleased(KeyEvent event) {

		        if (isStringDouble(jtfMinBalance)) {
		        	jtfMinBalance.setForeground(Color.BLACK);
		        } else {
		        	jtfMinBalance.setForeground(Color.RED);
		        }
		    }
		});
		
		// create button panel
		buttonPanel = new JPanel(new GridLayout(4,1,5,14));
		buttonPanel.setBorder(padSidesBottom);
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
		
		// create lower panel
		lowerPanel = new JPanel(new GridBagLayout());
		lowerPanel.setPreferredSize(new Dimension(800,220));
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.9;
		c.insets = new Insets(0,0,0,0);
		lowerPanel.add(infoPanel, c);
		c.gridx++;
		c.weightx = 0.1;
		c.insets = new Insets(30,0,0,30);
		lowerPanel.add(buttonPanel, c);
		Border loweredEtched = BorderFactory.createEtchedBorder(
				EtchedBorder.LOWERED);
		TitledBorder titleBorder = BorderFactory.createTitledBorder(
                loweredEtched, "Account Details");
		titleBorder.setTitleJustification(TitledBorder.LEFT);
		Border lowerBorder = BorderFactory.createCompoundBorder(
				padSidesBottom, titleBorder);
		lowerPanel.setBorder(lowerBorder);

		// add lower panel to frame
		add(lowerPanel, BorderLayout.SOUTH);
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

	private boolean isStringInt(JTextField field) {
		if(field.getText() != null) {
			try{
			    Integer.parseInt(field.getText());   
			}catch(Exception e){
			    return false;
			}
		} else {
			return false;
		}
		return true;
	}
	
	private boolean isStringDouble(JTextField field) {
		if(field.getText() != null) {
			try{
			    Double.valueOf(field.getText());
			}catch(Exception e){
			    return false;
			}
			
			if(field.getText().contains("d") || 
					field.getText().contains("f")) {
				return false;
			}
		} else {
			return false;
		}
		return true;
	}
	
	private void addChecking() {
		boolean valid = true;
		if(jtfAcctNumber.getText().isEmpty() || 
				jtfAcctNumber.getForeground() == Color.RED) {
			valid = false;
		} if(jtfAcctOwner.getText().isEmpty() || 
				jtfAcctOwner.getForeground() == Color.RED) {
			valid = false;
		} if(jdcDateOpened == null) {
			valid = false;
		} if(jtfAcctBalance.getText().isEmpty() || 
				jtfAcctBalance.getForeground() == Color.RED) {
			valid = false;
		} if(jtfMonthlyFee.getText().isEmpty() || 
				jtfMonthlyFee.getForeground() == Color.RED) {
			valid = false;
		} 
			
		if(valid) {
			
			try {
				int account = Integer.parseInt(jtfAcctNumber.getText());
				String owner = jtfAcctOwner.getText();
				Date date = jdcDateOpened.getDate();
				GregorianCalendar dateOpened = new GregorianCalendar();
				dateOpened.setTime(date);
				double balance = Double.valueOf(
						jtfAcctBalance.getText());
				double monthlyFee = Double.valueOf(
						jtfMonthlyFee.getText());
		
				CheckingAccount checking = new CheckingAccount(account,
						owner,dateOpened,balance,monthlyFee);
				tableModel.add(checking);
			}
			catch(NullPointerException ex) {
				JOptionPane optionPane = new JOptionPane();
				JOptionPane.showMessageDialog(optionPane, 
						"No Date Chosen!", "Error!", 
						JOptionPane.ERROR_MESSAGE);
			}
			
		}
	}
	
	private void addSavings() {
		boolean valid = true;
		if(jtfAcctNumber.getText().isEmpty() || 
				jtfAcctNumber.getForeground() == Color.RED) {
			valid = false;
		} if(jtfAcctOwner.getText().isEmpty() || 
				jtfAcctOwner.getForeground() == Color.RED) {
			valid = false;
		} if(jtfAcctBalance.getText().isEmpty() || 
				jtfAcctBalance.getForeground() == Color.RED) {
			valid = false;
		} if(jtfMinBalance.getText().isEmpty() || 
				jtfMinBalance.getForeground() == Color.RED) {
			valid = false;
		} if(jtfInterestRate.getText().isEmpty() || 
				jtfInterestRate.getForeground() == Color.RED) {
			valid = false;
		}
		
		if(valid) {
			int account = Integer.parseInt(jtfAcctNumber.getText());
			String owner = jtfAcctOwner.getText();
	
			Date date = jdcDateOpened.getDate();
			GregorianCalendar dateOpened = new GregorianCalendar();
			dateOpened.setTime(date);
			
			double balance = Double.valueOf(jtfAcctBalance.getText());
			double minBalance = Double.valueOf(jtfMinBalance.getText());
			double interestRate = Double.valueOf(
					jtfInterestRate.getText());

			SavingsAccount savings = new SavingsAccount(account, owner,
					dateOpened, balance, minBalance, interestRate);
			tableModel.add(savings);
		}
		
	}
	
	private int rowIndex() {
		return acctTable.getSelectedRow();
	}
	
	private void selectChecking() {
		jtfMinBalance.setText("");
		jtfInterestRate.setText("");
		jtfInterestRate.setEnabled(false);
		jtfMinBalance.setEnabled(false);
		jtfMonthlyFee.setEnabled(true);
	}
	
	private void selectSavings() {
		jtfInterestRate.setEnabled(true);
		jtfMinBalance.setEnabled(true);
		jtfMonthlyFee.setText("");
		jtfMonthlyFee.setEnabled(false);
	}
	
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == jrbChecking){ 
				selectChecking();
			}

			if(e.getSource() == jrbSavings){
				selectSavings();
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

				try {
					if(!jtfAcctNumber.getText().isEmpty()) {
						tableModel.setValueAt(
								jtfAcctNumber.getText(), rowIndex(), 0);
					}
					if(!jtfAcctOwner.getText().isEmpty()) {
						tableModel.setValueAt(
								jtfAcctOwner.getText(), 
								rowIndex(), 1);
					}
					if(jdcDateOpened != null) {
						Date date = jdcDateOpened.getDate();
						GregorianCalendar dateOpened = 
								new GregorianCalendar();
						dateOpened.setTime(date);
						tableModel.setValueAt(dateOpened, 
								rowIndex(), 2);
					}
					if(!jtfAcctBalance.getText().isEmpty()) {
						tableModel.setValueAt(
								jtfAcctBalance.getText(), 
								rowIndex(), 3);
					}

					if(savingsIsSelected()) {
						if(!jtfMinBalance.getText().isEmpty()) {
							tableModel.setValueAt(
									jtfMinBalance.getText(), 
									rowIndex(), 6);
						}
						if(!jtfInterestRate.getText().isEmpty()) {
							tableModel.setValueAt(
									jtfInterestRate.getText(), 
									rowIndex(), 5);
						}
					}

					if(checkingIsSelected()) {
						if(!jtfMonthlyFee.getText().isEmpty()) {
							tableModel.setValueAt(
									jtfMonthlyFee.getText(), 
									rowIndex(), 4);
						}
					}
				} 
				catch(IndexOutOfBoundsException ex) {
					JOptionPane optionPane = new JOptionPane();
					JOptionPane.showMessageDialog(optionPane, 
							"No Accounts!", "Error!", 
							JOptionPane.ERROR_MESSAGE);
				}
				catch(NullPointerException ex) {
					JOptionPane optionPane = new JOptionPane();
					JOptionPane.showMessageDialog(optionPane, 
							"No Accounts!", "Error!", 
							JOptionPane.ERROR_MESSAGE);
				}
			}

			if(e.getSource() == jbtClear) {
				jtfAcctNumber.setText("");
				jtfAcctOwner.setText("");
				jdcDateOpened.setCalendar(null);
				jtfAcctBalance.setText("");
				jtfMonthlyFee.setText("");
				jtfInterestRate.setText("");
				jtfMinBalance.setText("");
			}
			
			if(e.getSource() == jbtDelete) {
				try{
					tableModel.delete(rowIndex());
				} catch(IndexOutOfBoundsException ex) {
					JOptionPane optionPane = new JOptionPane();
					JOptionPane.showMessageDialog(optionPane, 
							"No accounts!", "Error!", 
							JOptionPane.ERROR_MESSAGE);
				}
			}
			
			if(e.getSource() == jmiQuit) {
				if (JOptionPane.showConfirmDialog(null, 
						"Exit Bank Application?", "Confirm Exit",
						JOptionPane.YES_NO_OPTION) == 
						JOptionPane.YES_OPTION)
					System.exit(0);
			}

			if(e.getSource() == jmiSaveBinary) {
					tableModel.saveSerializable();
			}
			
			if(e.getSource() == jmiLoadBinary) {
				try {
					tableModel = tableModel.loadSerializable();
					acctTable.setModel(tableModel);
				} catch(IllegalArgumentException ex) {
					return;
				}				
			}
			
			if(e.getSource() == jmiSaveText) {
				tableModel.saveText();
			}
			
			if(e.getSource() == jmiLoadText) {
				try {
					tableModel = tableModel.loadText();
					acctTable.setModel(tableModel);
				} catch(IllegalArgumentException ex) {
					return;
				}	

			}
			
			if(e.getSource() == jmiSaveXML) {
				tableModel.saveXML();
			}
			
			if(e.getSource() == jmiLoadXML) {
				tableModel.loadXML();
				acctTable.setModel(tableModel);
			}
			
			if(e.getSource() == jmiSortByAcctNumber) {
				tableModel.sortAccountNumber();
			}
			
			if(e.getSource() == jmiSortByAcctOwner) {
				tableModel.sortAccountName();
			}
			
			if(e.getSource() == jmiSortByDateOpened) {
				tableModel.sortDateOpened();
			}
		}
	}
	
	class SharedListSelectionHandler implements ListSelectionListener {
	    public void valueChanged(ListSelectionEvent e) {
	    	ListSelectionModel lsm = (ListSelectionModel)e.getSource();
	    	
	    	// Find out which indexes are selected.
	    	int minIndex = lsm.getMinSelectionIndex();
	    	int maxIndex = lsm.getMaxSelectionIndex();
	    	for (int i = minIndex; i <= maxIndex; i++) {
	    		if (lsm.isSelectedIndex(i)) {
	    			if(tableModel.isChecking(i)) {
	    				jrbChecking.setSelected(true);
	    				selectChecking();
	    			} else {
	    				jrbSavings.setSelected(true);
	    				selectSavings();
	    			}
	    			jtfAcctNumber.setText(
	    					""+ tableModel.getValueAt(i,0));
	    			jtfAcctOwner.setText(
	    					"" + tableModel.getValueAt(i,1));


	    			String date = (String) tableModel.getValueAt(i,2);
	    			String[] dateSplit = date.split("/");
	    			int year = Integer.parseInt(dateSplit[2]);
	    			int month = Integer.parseInt(dateSplit[0]) - 1;
	    			int day = Integer.parseInt(dateSplit[1]);

	    			GregorianCalendar cal = new GregorianCalendar(
	    					year, month, day);

	    			jdcDateOpened.setCalendar(cal);
	    			jtfAcctBalance.setText(
	    					"" + tableModel.getValueAt(i,3));
	    			if (tableModel.getValueAt(i,4) == null) {
	    				jtfMonthlyFee.setText("");
	    			} else{
	    				jtfMonthlyFee.setText(
	    						"" + tableModel.getValueAt(i,4));
	    			}
	    			if (tableModel.getValueAt(i,5) == null) {
	    				jtfInterestRate.setText("");
	    			} else{
	    				jtfInterestRate.setText(
	    						"" + tableModel.getValueAt(i,5));
	    			}
	    			if (tableModel.getValueAt(i,6) == null) {
	    				jtfMinBalance.setText("");
	    			} else{
	    				jtfMinBalance.setText(
	    						"" + tableModel.getValueAt(i,6));
	    			}
	    		}
            }
	    }
	}
}
