package project3;

import java.awt.Component;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


public class DateCellRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 1L;
	
	SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
	
	public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {

        super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
                row, column);

        if (value instanceof GregorianCalendar) {
        	Date date = ((GregorianCalendar) value).getTime();
            this.setText(sdf.format(date));
        } else {
            System.out.println("class: " + value.getClass().getCanonicalName());
        }

        return this;
    }

	public void setValue(Object value) {
		Object result = value;
	    if ((value != null) && (value instanceof GregorianCalendar)) {
	      GregorianCalendar cal = (GregorianCalendar) value;
	      result = cal.getTime();
	      sdf.format(result);
	    } 
	    super.setValue(result);
	}
}
