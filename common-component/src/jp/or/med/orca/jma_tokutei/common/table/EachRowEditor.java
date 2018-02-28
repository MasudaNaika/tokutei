package jp.or.med.orca.jma_tokutei.common.table;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;

import jp.or.med.orca.jma_tokutei.common.component.ExtendedTextField;

/**
  * each row TableCellEditor
  *
  * @version 1.1 09/09/99
  * @author Nobuo Tamemasa
  */

public class EachRowEditor implements TableCellEditor {
  protected Hashtable editors;
  protected TableCellEditor editor, defaultEditor;
  JTable table;

  public EachRowEditor(JTable table) {
    this.table = table;
    editors = new Hashtable();
    defaultEditor = new DefaultCellEditor(new ExtendedTextField());
  }
  public void setEditorAt(int row, TableCellEditor editor) {
    editors.put(new Integer(row),editor);
  }
  public Component getTableCellEditorComponent(JTable table,
      Object value, boolean isSelected, int row, int column) {
    return editor.getTableCellEditorComponent(table,
             value, isSelected, row, column);
  }

  public Object getCellEditorValue() {
    return editor.getCellEditorValue();
  }
  public boolean stopCellEditing() {
    return editor.stopCellEditing();
  }
  public void cancelCellEditing() {
    editor.cancelCellEditing();
  }
  public boolean isCellEditable(EventObject anEvent) {
    if (table == null)
    	return false;

    selectEditor((MouseEvent)anEvent);
    return editor.isCellEditable(anEvent);
  }
  public void addCellEditorListener(CellEditorListener l) {
    editor.addCellEditorListener(l);
  }
  public void removeCellEditorListener(CellEditorListener l) {
    editor.removeCellEditorListener(l);
  }
  public boolean shouldSelectCell(EventObject anEvent) {
    if (table == null)
    	return false;

    selectEditor((MouseEvent)anEvent);
    return editor.shouldSelectCell(anEvent);
  }

  protected void selectEditor(MouseEvent e) {
    int row;

    if (e == null) {
      row = table.getSelectionModel().getAnchorSelectionIndex();
    } else {
      row = table.rowAtPoint(e.getPoint());
    }
    editor = (TableCellEditor)editors.get(new Integer(row));
    if (editor == null) {
      editor = defaultEditor;
    }
  }
}


