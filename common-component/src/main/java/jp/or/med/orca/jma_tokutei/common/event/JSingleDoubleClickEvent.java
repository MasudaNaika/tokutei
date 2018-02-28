package jp.or.med.orca.jma_tokutei.common.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;

import jp.or.med.orca.jma_tokutei.common.table.JSimpleTable;

public class JSingleDoubleClickEvent extends MouseAdapter {
	private ActionListener ActionFrame;
	private JComponent TargetComponent;
	private JSimpleTable fixedtable;

	// edit s.inoue 2009/12/12
	public JSingleDoubleClickEvent(ActionListener ActionFrame, JComponent TargetComponent,JSimpleTable fixedtable)
	{
		if (ActionFrame != null)
			this.ActionFrame = ActionFrame;
		if (TargetComponent != null)
			this.TargetComponent = TargetComponent;

		this.fixedtable = fixedtable;
	}
	// edit s.inoue 2009/12/12
	public JSingleDoubleClickEvent(ActionListener ActionFrame, JComponent TargetComponent)
	{
		this.ActionFrame = ActionFrame;
		this.TargetComponent = TargetComponent;
	}

	public void mouseClicked(MouseEvent e)
	{
		// single
		if(e.getClickCount() == 1){
			if (fixedtable == null)
				return;
			int i = fixedtable.getSelectedRow();

			// edit s.inoue 2010/07/09
			if (i == 0)
			fixedtable.setRowSelectionInterval(i,i);

			// add s.inoue 2010/02/19
			int j = fixedtable.getSelectedColumn();

			if((fixedtable.getValueAt(i, 0) == null)){
				if (i==0){
		    		 fixedtable.setValueAt(Boolean.TRUE, i, 0);
				}
				return;
			}
			// edit s.inoue 2010/02/19
			// 0列対応
			boolean checkVal = false;
			if (j == 0)
				checkVal = !checkVal;

			if (fixedtable.getValueAt(i, 0).equals(Boolean.TRUE)){
				fixedtable.setValueAt(checkVal, i, 0);
			}else{
				fixedtable.setValueAt(!checkVal, i, 0);
			}

		// double
		}else if(e.getClickCount() == 2){
			if (ActionFrame == null || TargetComponent == null)
				return;
			ActionFrame.actionPerformed(new ActionEvent(TargetComponent,0,""));
		}
	}
}

//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

