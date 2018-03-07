// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) ansi

package jp.or.med.orca.jma_tokutei.common.frame.dialog;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.table.*;
import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedButton;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.event.JEnterEvent;
import jp.or.med.orca.jma_tokutei.common.event.JSingleDoubleClickEvent;
import jp.or.med.orca.jma_tokutei.common.focus.JFocusTraversalPolicy;
import jp.or.med.orca.jma_tokutei.common.sql.JConnection;
import jp.or.med.orca.jma_tokutei.common.table.*;
import org.apache.log4j.Logger;

// Referenced classes of package jp.or.med.orca.jma_tokutei.common.frame.dialog:
//            JSelectHokenjyaOrcaDialog, JSelectHokenjyaOrcaDialogData

public class JSelectHokenjyaOrcaDialogCtrl extends JSelectHokenjyaOrcaDialog
{

    public JSelectHokenjyaOrcaDialogCtrl(JFrame jframe, String s)
    {
        super(s);
        model = new JSimpleTable();
        SelectedData = null;
        focusTraversalPolicy = null;
        initilizeSettings();
    }

    public JSelectHokenjyaOrcaDialogCtrl(String s)
    {
        super(s);
        model = new JSimpleTable();
        SelectedData = null;
        focusTraversalPolicy = null;
        initilizeSettings();
    }

    private Vector getTeikeiMaster()
    {
        try
        {
            JApplication.hokenjyaDatabase = JConnection.ConnectHokenjyaDatabase();
        }
        catch(Exception exception)
        {
            System.out.println(exception.getMessage());
            JErrorMessage.show("M1000", null);
            System.exit(1);
        }
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("SELECT INSURER_NO, INSURER_NAME, INSURER_ADDRESS1 || INSURER_ADDRESS2 INSURER_ADDRESS,INSURER_POST,INSURER_TEL,LAST_TIME ");
        stringbuilder.append(" FROM M_INSURER");
        stringbuilder.append(" WHERE octet_length(INSURER_NO) = 8");
        stringbuilder.append(" ORDER BY INSURER_NO");
        ArrayList arraylist = null;
        try
        {
            arraylist = JApplication.hokenjyaDatabase.sendExecuteQuery(stringbuilder.toString());
        }
        catch(SQLException sqlexception)
        {
            logger.error(sqlexception.getMessage());
            sqlexception.printStackTrace();
        }
        String as[][] = new String[arraylist.size()][6];
        Vector vector = null;
        for(int i = 0; i < arraylist.size(); i++)
        {
            JSelectHokenjyaOrcaDialogData jselecthokenjyaorcadialogdata = new JSelectHokenjyaOrcaDialogData();
            Hashtable hashtable = (Hashtable)arraylist.get(i);
            jselecthokenjyaorcadialogdata.hknjanum = (String)hashtable.get("INSURER_NO");
            jselecthokenjyaorcadialogdata.hknjaname = (String)hashtable.get("INSURER_NAME");
            jselecthokenjyaorcadialogdata.adress = (String)hashtable.get("INSURER_ADDRESS");
            jselecthokenjyaorcadialogdata.post = (String)hashtable.get("INSURER_POST");
            jselecthokenjyaorcadialogdata.tel = (String)hashtable.get("INSURER_TEL");
            jselecthokenjyaorcadialogdata.lasttime = (String)hashtable.get("LAST_TIME");
            vector.add(jselecthokenjyaorcadialogdata);
        }

        return vector;
    }

    private void initilizeSettings()
    {
        model.setPreferedColumnWidths(new int[] {
            100, 200, 100, 300, 100, 150
        });
        JSimpleTableScrollPanel jsimpletablescrollpanel = new JSimpleTableScrollPanel(model);
        jPanel_Table.add(jsimpletablescrollpanel);
        model.addHeader("保険者番号");
        model.addHeader("保険者名称");
        model.addHeader("郵便番号");
        model.addHeader("住所");
        model.addHeader("電話番号");
        model.addHeader("更新日付");
        focusTraversalPolicy = new JFocusTraversalPolicy();
        setFocusTraversalPolicy(focusTraversalPolicy);
        focusTraversalPolicy.setDefaultComponent(model);
        focusTraversalPolicy.addComponent(model);
        focusTraversalPolicy.addComponent(jButton_Select);
        focusTraversalPolicy.addComponent(jButton_End);
        functionListner();
        Vector vector = getTeikeiMaster();
        for(int i = 0; i < vector.size(); i++)
        {
            JSelectHokenjyaOrcaDialogData jselecthokenjyaorcadialogdata = (JSelectHokenjyaOrcaDialogData)vector.get(i);
            String as[] = {
                jselecthokenjyaorcadialogdata.hknjanum, jselecthokenjyaorcadialogdata.hknjaname, jselecthokenjyaorcadialogdata.post, jselecthokenjyaorcadialogdata.adress, jselecthokenjyaorcadialogdata.tel
            };
            model.addData(as);
        }

        JSimpleTableCellPosition ajsimpletablecellposition[] = {
            new JSimpleTableCellPosition(-1, -1)
        };
        model.setCellEditForbid(ajsimpletablecellposition);
        HokenjyaData = vector;
        jButton_End.addKeyListener(new JEnterEvent(this));
        jButton_Select.addKeyListener(new JEnterEvent(this));
        model.addKeyListener(new JEnterEvent(this, jButton_Select));
        model.addMouseListener(new JSingleDoubleClickEvent(this, jButton_Select));
        TableColumnModel tablecolumnmodel = model.getColumnModel();
        for(int j = 0; j < tablecolumnmodel.getColumnCount(); j++)
            tablecolumnmodel.getColumn(j).setCellRenderer(new JSimpleTableCellRowRenderer((DefaultTableCellRenderer)model.getDefaultRenderer(model.getColumnClass(j))));

        if(model.getRowCount() > 0)
            model.setRowSelectionInterval(0, 0);
        else
            jButton_End.requestFocus();
        setVisible(true);

    }

    private void functionListner()
    {
        for(int i = 0; i < focusTraversalPolicy.getComponentSize(); i++)
        {
            Component component = focusTraversalPolicy.getComponent(i);
            component.addKeyListener(this);
        }

    }

    public void pushedEndButton(ActionEvent actionevent)
    {
        SelectedData = null;
        dispose();
    }

    public void pushedSelectButton(ActionEvent actionevent)
    {
        if(model.getSelectedRow() < 0)
        {
            return;
        } else
        {
            SelectedData = (JSelectHokenjyaOrcaDialogData)HokenjyaData.get(model.getSelectedRow());
            dispose();
            return;
        }
    }

    public JSelectHokenjyaOrcaDialogData GetSelectedData()
    {
        return SelectedData;
    }

    public void actionPerformed(ActionEvent actionevent)
    {
        if(actionevent.getSource() == jButton_End)
        {
            logger.info(jButton_End.getText());
            pushedEndButton(actionevent);
        }
        if(actionevent.getSource() == jButton_Select)
        {
            logger.info(jButton_Select.getText());
            pushedSelectButton(actionevent);
        }
    }

    public void keyPressed(KeyEvent keyevent)
    {
        switch(keyevent.getKeyCode())
        {
        case 112: // 'p'
            logger.info(jButton_End.getText());
            pushedEndButton(null);
            break;

        case 123: // '{'
            logger.info(jButton_Select.getText());
            pushedSelectButton(null);
            break;
        }
    }

    private JSimpleTable model;
    private Vector HokenjyaData;
    private JSelectHokenjyaOrcaDialogData SelectedData;
    private JFocusTraversalPolicy focusTraversalPolicy;
    private static Logger logger = Logger.getLogger("jp/or/med/orca/jma_tokutei/common/frame/dialog/JSelectHokenjyaOrcaDialogCtrl");

}
