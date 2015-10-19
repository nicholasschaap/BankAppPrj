package project3;

import java.util.ArrayList;
import javax.swing.AbstractListModel;

public class BankModel extends AbstractListModel {
	
	private static final long serialVersionUID = 1L;
	private ArrayList<Account> accts;

	public BankModel() {
		accts = new ArrayList<Account>();
	}
	
	@Override
	public Object getElementAt(int i) {
		return accts.get(i);
	}

	@Override
	public int getSize() {
		return accts.size();
	}
	
	public void add(Account a) {
		accts.add(a);
		fireIntervalAdded(a, 0, getSize() - 1);
	}
	
	public void delete(Account a) {
		accts.remove(a);
		fireIntervalRemoved(a, 0, getSize() - 1);
	}
	
	public void update() {
		fireContentsChanged(this, 0, getSize());
	}
	
	
}
