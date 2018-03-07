// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) ansi

package jp.or.med.orca.jma_tokutei.common.frame.dialog;

import java.awt.*;
import java.awt.event.*;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.*;
import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedButton;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedImageIcon;
import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
import jp.or.med.orca.jma_tokutei.common.event.JSingleDoubleClickEvent;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import jp.or.med.orca.jma_tokutei.common.sql.JConnection;
import jp.or.med.orca.jma_tokutei.common.table.JSimpleTable;
import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableCellRowRenderer;
import org.apache.log4j.Logger;

// Referenced classes of package jp.or.med.orca.jma_tokutei.common.frame.dialog:
//            SelectKenshinPatternDialog, IDialog

public class SelectOrcaHokenjyaDialog extends JDialog
    implements ActionListener, KeyListener, ItemListener, IDialog
{
	private int selectedlocalRow = 0;

    public SelectOrcaHokenjyaDialog(JFrame jframe, String s, String s1)
    {
        super(jframe);
        jContentPane = null;
        jPanelButtonArea = null;
        jButtonOK = null;
        jButtonCancel = null;
        JPanelHokenja = null;
        dmodel = null;
        sorter = null;
        table = null;
        initialize(s, s1);
        setLocationRelativeTo(null);
    }

    private void initialize(String s, String s1)
    {
        setTitle(ViewSettings.getTokuteFrameTitleWithKikanInfomation());
        setResizable(false);
        setSize(ViewSettings.getFrameCommonSize());
        setLocationRelativeTo(null);
        setContentPane(getJContentPane(s1));
        jButtonOK.grabFocus();
        searchRefresh();
        setModal(true);
    }

    private void searchRefresh()
    {
        dmodel = new DefaultTableModel(getTeikeiMaster(), header) {

            public boolean isCellEditable(int j, int k)
            {
                return false;
            }

//            final SelectOrcaHokenjyaDialog this$0;
//
//
//            {
//                this$0 = SelectOrcaHokenjyaDialog.this;
//                super(aobj, aobj1);
//            }
        };
        sorter = new TableRowSorter(dmodel);
        table = new JSimpleTable(dmodel);
        table.setPreferedColumnWidths(new int[] {
            80, 200, 390, 80, 100, 100
        });
        table.setRowSorter(sorter);
        table.setSelectionMode(0);
        table.addMouseListener(new JSingleDoubleClickEvent(this, jButtonOK));
        final JScrollPane scroll = new JScrollPane(table);
        JViewport jviewport = new JViewport();
        scroll.setRowHeader(jviewport);
        scroll.getRowHeader().addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent changeevent)
            {
                JViewport jviewport1 = (JViewport)changeevent.getSource();
                scroll.getVerticalScrollBar().setValue(jviewport1.getViewPosition().y);
            }

//            final JScrollPane val$scroll;
//            final SelectOrcaHokenjyaDialog this$0;
//
//
//            {
//                this$0 = SelectOrcaHokenjyaDialog.this;
//                scroll = jscrollpane;
//                super();
//            }
        });
        dmodel.setDataVector(getTeikeiMaster(), header);
        JPanelHokenja.add(scroll);
        TableColumnModel tablecolumnmodel = table.getColumnModel();
        for(int i = 0; i < tablecolumnmodel.getColumnCount(); i++)
            tablecolumnmodel.getColumn(i).setCellRenderer(new JSimpleTableCellRowRenderer((DefaultTableCellRenderer)table.getDefaultRenderer(table.getColumnClass(i))));

        // eidt s.inoue 2013/06/11
        if(table.getRowCount() > 0)
            // table.setRowSelectionInterval(0, 0);
        	table.setRowSelectionInterval(1, 1);
        table.refreshTable();

        // add s.inoue 2013/06/11
        table.setAutoCreateRowSorter(true);
        table.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent event) {
            	table = (JSimpleTable)event.getSource();
                int[] selection = table.getSelectedRows();
                for (int i = 0; i < selection.length; i++) {

                    // テーブル・モデルの行数に変換
                	selectedlocalRow = selection[i];
                	// selectedlocalRow = table.convertRowIndexToModel(selection[i]);

                    System.out.println("View Row: " + selection[i]+ " Model Row: " + selectedlocalRow);
                }
            }});
    }

    public Object[][] getTeikeiMaster()
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
        // eidt s.inoue 2013/06/11
        StringBuilder sb = new StringBuilder();
//        stringbuilder.append("SELECT INSURER_NO, INSURER_NAME, INSURER_ADDRESS1 || INSURER_ADDRESS2 INSURER_ADDRESS,INSURER_POST,INSURER_TEL,SUBSTRING(LAST_TIME from 1 for 10) LAST_TIME ");
//        stringbuilder.append(" FROM M_INSURER");
//        stringbuilder.append(" WHERE octet_length(INSURER_NO) = 8");
//        stringbuilder.append(" ORDER BY INSURER_NO");
		sb.append("select a.INSURER_NO, INSURER_NAME, INSURER_ADDRESS1 || INSURER_ADDRESS2 INSURER_ADDRESS,INSURER_POST,INSURER_TEL,SUBSTRING(LAST_TIME from 1 for 10) LAST_TIME");
		sb.append(" FROM M_INSURER a");
		sb.append(" where  char_length(a.INSURER_NO)=8 ");
		sb.append(" union all ");
		sb.append(" select '00' || a.INSURER_NO, INSURER_NAME, INSURER_ADDRESS1 || INSURER_ADDRESS2 INSURER_ADDRESS,INSURER_POST,INSURER_TEL,SUBSTRING(LAST_TIME from 1 for 10) LAST_TIME");
		sb.append(" FROM M_INSURER a");
		sb.append(" where  char_length(a.INSURER_NO)<8");

        ArrayList arraylist = null;
        try
        {
            arraylist = JApplication.hokenjyaDatabase.sendExecuteQuery(sb.toString());
        }
        catch(SQLException sqlexception)
        {
            logger.error(sqlexception.getMessage());
            sqlexception.printStackTrace();
        }
        String as[][] = new String[arraylist.size()][6];
        for(int i = 0; i < arraylist.size(); i++)
        {
            Hashtable hashtable = (Hashtable)arraylist.get(i);
            as[i][0] = ((String) (hashtable.get("INSURER_NO")));
            as[i][1] = ((String) (hashtable.get("INSURER_NAME")));
            as[i][2] = ((String) (hashtable.get("INSURER_ADDRESS")));
            as[i][3] = ((String) (hashtable.get("INSURER_POST")));
            as[i][4] = ((String) (hashtable.get("INSURER_TEL")));
            as[i][5] = ((String) (hashtable.get("LAST_TIME")));
        }

        return as;
    }

    private JPanel getJContentPane(String s)
    {
        if(jContentPane == null)
        {
            GridBagConstraints gridbagconstraints = new GridBagConstraints();
            gridbagconstraints.gridx = 0;
            gridbagconstraints.gridy = 0;
            gridbagconstraints.weightx = 1.0D;
            gridbagconstraints.fill = 1;
            GridBagConstraints gridbagconstraints1 = new GridBagConstraints();
            gridbagconstraints1.gridx = 0;
            gridbagconstraints1.gridy = 1;
            jContentPane = new JPanel();
            jContentPane.setLayout(new GridBagLayout());
            jContentPane.add(getJPanelButtonArea(), gridbagconstraints);
            jContentPane.add(getHokenjyaInfo(), gridbagconstraints1);
        }
        return jContentPane;
    }

    private JPanel getJPanelButtonArea()
    {
        if(jPanelButtonArea == null)
        {
            GridBagConstraints gridbagconstraints = new GridBagConstraints();
            gridbagconstraints.gridx = 1;
            gridbagconstraints.gridy = 0;
            gridbagconstraints.insets = new Insets(5, 10, 5, 0);
            GridBagConstraints gridbagconstraints1 = new GridBagConstraints();
            gridbagconstraints1.gridx = 2;
            gridbagconstraints1.gridy = 0;
            gridbagconstraints1.insets = new Insets(5, 5, 5, 5);
            gridbagconstraints1.weightx = 1.0D;
            gridbagconstraints1.anchor = 17;
            jPanelButtonArea = new JPanel();
            jPanelButtonArea.setLayout(new GridBagLayout());
            jPanelButtonArea.add(getJButtonOK(), gridbagconstraints1);
            jPanelButtonArea.add(getJButtonCancel(), gridbagconstraints);
        }
        return jPanelButtonArea;
    }

    private ExtendedButton getJButtonOK()
    {
        if(jButtonOK == null)
        {
            ExtendedImageIcon extendedimageicon = new ExtendedImageIcon(JPath.Ico_Common_Select);
            javax.swing.ImageIcon imageicon = extendedimageicon.setStrechIcon(this, 0.80000000000000004D);
            jButtonOK = new ExtendedButton("Select", "選択(S)", "選択(ALT+S)", 83, imageicon);
            jButtonOK.addActionListener(this);
        }
        return jButtonOK;
    }

    private ExtendedButton getJButtonCancel()
    {
        if(jButtonCancel == null)
        {
            ExtendedImageIcon extendedimageicon = new ExtendedImageIcon(JPath.Ico_Common_Back);
            javax.swing.ImageIcon imageicon = extendedimageicon.setStrechIcon(this, 0.80000000000000004D);
            jButtonCancel = new ExtendedButton("Return", "戻る(R)", "戻る(ALT+R)", 82, imageicon);
            jButtonCancel.addActionListener(this);
        }
        return jButtonCancel;
    }

    public void keyPressed(KeyEvent keyevent)
    {
        switch(keyevent.getKeyCode())
        {
        case 89: // 'Y'
            ReturnValue = RETURN_VALUE.YES;
            setVisible(false);
            break;

        case 67: // 'C'
            ReturnValue = RETURN_VALUE.CANCEL;
            setVisible(false);
            break;
        }
    }

    public void keyReleased(KeyEvent keyevent)
    {
    }

    public void keyTyped(KeyEvent keyevent)
    {
    }

    public RETURN_VALUE getStatus()
    {
        return ReturnValue;
    }

    public Integer getPrintSelect()
    {
        return Integer.valueOf(returnPageSelect);
    }

    public Vector getDataSelect()
    {
        return returnVec;
    }

    public void actionPerformed(ActionEvent actionevent)
    {
        if(actionevent.getSource() == jButtonOK)
        {
        	// eidt s.inoue 2013/06/11
            // returnVec = table.getData(table.getSelectedRow());
        	returnVec = table.getData(selectedlocalRow);
            ReturnValue = RETURN_VALUE.YES;
        } else
        if(actionevent.getSource() == jButtonCancel)
            ReturnValue = RETURN_VALUE.CANCEL;
        setVisible(false);
    }

    private JPanel getHokenjyaInfo()
    {
        if(JPanelHokenja == null)
        {
            JPanelHokenja = new JPanel();
            JPanelHokenja.setLayout(new BorderLayout());
            JPanelHokenja.setPreferredSize(new Dimension(970, 570));
        }
        return JPanelHokenja;
    }

    public void itemStateChanged(ItemEvent itemevent)
    {
    }

    public String getKenshinDate()
    {
        return null;
    }

    public void setText(String s)
    {
    }

    public void setShowCancelButton(boolean flag)
    {
    }

    public String getFilePath()
    {
        return null;
    }

    public void setDialogTitle(String s)
    {
    }

    public void setDialogSelect(boolean flag)
    {
    }

    public void setSaveFileName(String s)
    {
    }

    public String getK_PNo()
    {
        return null;
    }

    public String getK_PName()
    {
        return null;
    }

    public String getK_PNote()
    {
        return null;
    }

    public void setMessageTitle(String s)
    {
    }

    private static final long serialVersionUID = 1L;
    private JPanel jContentPane;
    private JPanel jPanelButtonArea;
    protected ExtendedButton jButtonOK;
    protected ExtendedButton jButtonCancel;
    protected RETURN_VALUE ReturnValue;
    protected int returnPageSelect;
    protected Vector returnVec;
    private JPanel JPanelHokenja;
    private DefaultTableModel dmodel;
    private TableRowSorter sorter;
    private JSimpleTable table;
    private static final String header[] = {
        "保険者番号", "保険者名", "住所", "郵便番号", "電話番号", "更新日付"
    };
    private static Logger logger = Logger.getLogger("jp/or/med/orca/jma_tokutei/common/frame/dialog/SelectKenshinPatternDialog");

}
