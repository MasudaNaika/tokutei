package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.*;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
import jp.or.med.orca.jma_tokutei.common.execlocker.JExecLocker;
import jp.or.med.orca.jma_tokutei.common.focus.JFocusTraversalPolicy;
import jp.or.med.orca.jma_tokutei.common.scene.JScene;
import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.tree.CheckBoxNode;
import jp.or.med.orca.jma_tokutei.common.tree.CheckBoxNodeEditor;
import jp.or.med.orca.jma_tokutei.common.tree.CheckBoxNodeRenderer;
import jp.or.med.orca.jma_tokutei.common.tree.JSimpleTree;
import jp.or.med.orca.jma_tokutei.common.tree.NamedVector;
import org.apache.log4j.Logger;

/**
 * ユーザメンテナンス用フレームのコントロール
 */
public class JUsabilityFrameCtrl extends JUsabilityFrame{
	private static Logger logger = Logger.getLogger(JUsabilityFrameCtrl.class);

// del s.inoue 2009/12/18
//	private UIManager.LookAndFeelInfo looks[] = UIManager.getInstalledLookAndFeels();
//    private static final String mac     = "com.sun.java.swing.plaf.mac.MacLookAndFeel";
//    private static final String metal   = "javax.swing.plaf.metal.MetalLookAndFeel";
//    // edit s.inoue 2009/12/16
//    // private static final String motif   = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
//    private static final String windows = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
//    private static final String gtk     = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
//    private static final String nimbus  = "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";

	/* フォーカス移動制御 */
	private JFocusTraversalPolicy focusTraversalPolicy = null;
	private UIManager.LookAndFeelInfo looksinfo = null;

	// add s.inoue 2009/12/17
	// 画面Node
	private JSimpleTree functionTree = null;
	private CheckBoxNode chkNode[][] = null;
	private Object rootNodes[] = null;
	private Vector rootVector = null;

	/**
	 * デフォルトコンストラクタ
	 */
	public JUsabilityFrameCtrl() {
// del s.inoue 2009/12/18
//		if (isAvailableLookAndFeel(mac))
//			jCombobox_LookFeel.addItem("Mac");
//		if (isAvailableLookAndFeel(metal))
//			jCombobox_LookFeel.addItem("Metal");
//		// edit s.inoue 2009/12/16
//		// if (isAvailableLookAndFeel(motif))
//		//	jCombobox_LookFeel.addItem("motif");
//		if (isAvailableLookAndFeel(windows))
//			jCombobox_LookFeel.addItem("Windows");
//		if (isAvailableLookAndFeel(gtk))
//			jCombobox_LookFeel.addItem("GTK");
//		if (isAvailableLookAndFeel(nimbus))
//			jCombobox_LookFeel.addItem("Nimbus");

// edit s.inoue 2009/12/18
//		/* treeMap作成 */
//		CheckBoxNode accessibilityOptions[] = {
//		        new CheckBoxNode(
//		            "連続入力時、保険者情報を残す", false),
//		        new CheckBoxNode("窓口負担情報を残す", true) };
//
//		CheckBoxNode browsingOptions[] = {
//		        new CheckBoxNode("Notify when downloads complete", true),
//		        new CheckBoxNode("Disable script debugging", true),
//		        new CheckBoxNode("Use AutoComplete", true),
//		        new CheckBoxNode("Browse in a new process", false) };
//
//		Vector accessVector = new NamedVector("受診券入力",accessibilityOptions);
//		Vector browseVector = new NamedVector("Browsing", browsingOptions);

		rootNodes = getFunctionMaster();

		rootVector = new NamedVector("Root", rootNodes);
		functionTree = new JSimpleTree(rootVector);

	    CheckBoxNodeRenderer renderer = new CheckBoxNodeRenderer();
	    functionTree.setCellRenderer(renderer);

	    functionTree.setCellEditor(new CheckBoxNodeEditor(functionTree));
	    functionTree.setEditable(true);

	    functionTree.addTreeSelectionListener(this);
	    // edit s.inoue 2010/04/07
	    // JScrollPane scroll = new JScrollPane(functionTree);
	    JScrollPane scroll = new JScrollPane(functionTree);

		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.setPreferredSize(new Dimension(400,300));
		// edit s.inoue 2010/04/02
		scroll.addKeyListener(this);
		jPanel_Usability.addKeyListener(this);
		functionTree.addKeyListener(this);
		jButton_Register.setEnabled(false);

		jPanel_Usability.add(scroll);
		functionListner(scroll);

	}

	// add s.inoue 2010/04/09
	public void valueChanged(TreeSelectionEvent e) {
		jButton_Register.setEnabled(true);
	}
	private void functionListner(JScrollPane scroll){
		this.focusTraversalPolicy = new JFocusTraversalPolicy();
		this.setFocusTraversalPolicy(this.focusTraversalPolicy);
		this.focusTraversalPolicy.setDefaultComponent(scroll);
		this.focusTraversalPolicy.addComponent(scroll);
		this.focusTraversalPolicy.addComponent(this.jButton_Register);
		this.focusTraversalPolicy.addComponent(jButton_End);

		// add s.inoue 2009/12/02
		for (int i = 0; i < focusTraversalPolicy.getComponentSize(); i++) {
			Component comp = focusTraversalPolicy.getComponent(i);
			comp.addKeyListener(this);
		}
	}
	private void functionListner(){
		this.focusTraversalPolicy = new JFocusTraversalPolicy();
		this.setFocusTraversalPolicy(this.focusTraversalPolicy);
		this.focusTraversalPolicy.setDefaultComponent(jButton_Register);
		this.focusTraversalPolicy.addComponent(jButton_Register);
		this.focusTraversalPolicy.addComponent(jButton_End);

		// add s.inoue 2009/12/02
		for (int i = 0; i < focusTraversalPolicy.getComponentSize(); i++) {
			Component comp = focusTraversalPolicy.getComponent(i);
			comp.addKeyListener(this);
		}
	}
	/* 機能マスタ取得 */
	private Object[] getFunctionMaster(){

		ArrayList<Hashtable<String, String>> result = null;
		ArrayList<Hashtable<String, String>> resultCount = null;

		try {

			StringBuilder sbCnt = new StringBuilder();
			sbCnt.append("SELECT COUNT(distinct SCREEN_CD) SCREEN_CNT FROM T_SCREENFUNCTION ");
			resultCount = JApplication.kikanDatabase.sendExecuteQuery(sbCnt.toString());
			int screenCount = Integer.parseInt(resultCount.get(0).get("SCREEN_CNT"));

			StringBuilder sb = new StringBuilder();
			sb.append("SELECT sf.SCREEN_CD,sm.SCREEN_NAME,sf.FUNCTION_CD,");
			sb.append(" sf.FUNCTION_FLG,fm.FUNCTION_DETAIL");
			sb.append(" FROM T_SCREENFUNCTION sf,T_SCREENMASTER sm,T_FUNCTIONMASTER fm");
			sb.append(" WHERE sf.FUNCTION_CD = fm.FUNCTION_CD");
			sb.append(" AND sf.SCREEN_CD = sm.SCREEN_CD");
			// edit s.inoue 2010/04/14
			sb.append(" ORDER BY sf.SCREEN_CD");
			result = JApplication.kikanDatabase.sendExecuteQuery(sb.toString());

			String screenCD = "";
			String preScreenCD = "";
			String screenName[] = null;
			int screen_i = 0;
			int item_i = 0;

			chkNode = new CheckBoxNode[screenCount][result.size()];
			CheckBoxNode[][] chkNodeCopy = new CheckBoxNode[screenCount][];

			screenName = new String[screenCount];

			for (int i = 0; i < result.size(); i++) {
				Hashtable<String, String> item = result.get(i);

				screenCD = item.get("SCREEN_CD");
				Boolean functionFlg = item.get("FUNCTION_FLG").equals("1")?true:false;

				if (screenCD.equals(preScreenCD) || i == 0){
					item_i=i;
				}else{
					chkNodeCopy[screen_i] = new CheckBoxNode[item_i+1];
					System.arraycopy(chkNode[screen_i], 0,chkNodeCopy[screen_i] , 0,item_i+1);
					screen_i++;item_i=0;
				}

				chkNode[screen_i][item_i] = new CheckBoxNode(item.get("FUNCTION_CD") + ":" + item.get("FUNCTION_DETAIL"),
						functionFlg);
				screenName[screen_i] = screenCD + ":" + item.get("SCREEN_NAME");

				if (i == result.size()-1){
					chkNodeCopy[screen_i] = new CheckBoxNode[item_i+1];
					System.arraycopy(chkNode[screen_i], 0,chkNodeCopy[screen_i] , 0,item_i+1);
				}

				preScreenCD = screenCD;
			}

			Vector<String> accessVector = new Vector<String>();
			rootNodes = new Object[screenCount];

			for (int i = 0; i < chkNodeCopy.length; i++) {
				accessVector = new NamedVector(screenName[i],chkNodeCopy[i]);
				rootNodes[i] = accessVector;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JErrorMessage.show("M5405", this);
		}
		return rootNodes;
	}

	/**
	 * 終了ボタン押下
	 */
	public void pushedEndButton(ActionEvent e) {
// del s.inoue 2009/12/18
//		changeTheLookAndFeel("Metal");
		dispose();
	}

	/**
	 * 登録ボタン押下
	 */
	public void pushedRegisterButton(ActionEvent e) {
		// 機能マスタの更新
		functionNodeRegister();
		dispose();
//		RETURN_VALUE retValue = JErrorMessage.show("M2100", this);
//		if (retValue == RETURN_VALUE.YES) {
//			// 再ログイン処理
//			// reloginSystem();
//		}else{
//		}
	}

	/* DBへ登録 */
	private void register(String screenCd,String functionCd,String functionflg){

		StringBuilder sb = new StringBuilder();
		try{
			JApplication.kikanDatabase.Transaction();

			sb.append("UPDATE T_SCREENFUNCTION SET "
				+ " FUNCTION_FLG = "
				+ JQueryConvert.queryConvert(functionflg)
				+ " WHERE SCREEN_CD = " + JQueryConvert.queryConvert(screenCd)
				+ " AND FUNCTION_CD = "+ JQueryConvert.queryConvert(functionCd));


			JApplication.kikanDatabase.sendNoResultQuery(sb.toString());
			JApplication.kikanDatabase.Commit();
		}catch(SQLException ex){
			logger.error(ex.getMessage());
		}
	}

	private void functionNodeRegister(){
		// add s.inoue 2009/12/17
		// 選択されている要素を取得し、削除
		int [] selectedItems =null;

		//functionTree.getAllNode(node)

		DefaultMutableTreeNode node =
		      (DefaultMutableTreeNode)functionTree.getLastSelectedPathComponent();

			node = (DefaultMutableTreeNode) node.getParent();

			while (node!=null) {
				for (int i=0; i<node.getChildCount(); i++) {
					DefaultMutableTreeNode n = (DefaultMutableTreeNode) node.getChildAt(i);
					Object[] ob =n.getUserObjectPath();
					CheckBoxNode cb = (CheckBoxNode)ob[2];
					NamedVector title = (NamedVector)ob[1];

					String parentCd = title.getName().substring(0,title.getName().indexOf(":"));
					String functionVal = cb.isSelected() ? "1":"0";
					String functionCd = cb.getText().toString().substring(0,cb.getText().indexOf(":"));
					register(parentCd,functionCd,functionVal);
// edit s.inoue 2009/12/18
//					System.out.println(title.getName().substring(0,cb.getText().indexOf(":")));
//					System.out.println(cb.isSelected() ? 1:0);
//					System.out.println(cb.getText().toString().substring(0,cb.getText().indexOf(":")));
//					System.out.println(cb.getText().toString().substring(cb.getText().indexOf(":")+1));
				}
				node = node.getNextSibling();
			}
	}

// del s.inoue 2009/12/20
//	/* 再ログイン */
//	private void reloginSystem(){
//		// windowを全て閉じる
//		java.awt.Window[] win =this.getWindows();
//		for (int i = 0; i < win.length; i++) {
//			win[i].dispose();
//		}
//		// データベースとの接続を解除
//		JApplication.kikanDatabase = null;
//
//		JApplication.kikanNumber = null;
//		JApplication.userID = null;
//		JApplication.password = null;
//		JApplication.authority = 0;
//
//		JApplication.useORCA = false;
//		JApplication.orcaSetting = null;
//
//		JApplication.kikanName = null;
//
//		/*
//		 * ロックファイルを削除する。
//		 * 削除に失敗しても、ログイン画面に戻る。
//		 */
//		boolean isDeleted = JExecLocker.getLockFile().delete();
//		if (! isDeleted) {
//			JErrorMessage.show("M2000", this);
//		}
//
//		JScene.ChangeScene(new JLoginFrameCtrl());
//	}

	public void actionPerformed(ActionEvent e) {
		Object souce = e.getSource();
		/* 終了ボタン */
		if (souce == jButton_End) {
			logger.info(jButton_End.getText());
			pushedEndButton(e);
		}
		/* 登録ボタン */
		else if (souce == jButton_Register) {
			if(jButton_Register.isEnabled()){
				logger.info(jButton_Register.getText());
				pushedRegisterButton(e);
			}
		}
	}

	// add s.inoue 2009/12/02
	@Override
	public void keyPressed(KeyEvent keyEvent) {
		switch(keyEvent.getKeyCode()){
		case KeyEvent.VK_F1:
			logger.info(jButton_End.getText());
			pushedEndButton(null);break;
		case KeyEvent.VK_F12:
			if(jButton_Register.isEnabled()){
				logger.info(jButton_Register.getText());
				pushedRegisterButton(null);break;
			}
		}
	}

// del s.inoue 2009/12/18
//	/* Look & Feel 設定*/
//	protected boolean isAvailableLookAndFeel(String laf) {
//		try{
//		    Class lnfClass = Class.forName(laf);
//		    LookAndFeel newLAF = (LookAndFeel)(lnfClass.newInstance());
//
//		    return newLAF.isSupportedLookAndFeel();
//		}catch(Exception e) {
//		    return false;
//		}
//	}
//    /* comboイベント */
//	public void itemStateChanged(ItemEvent e) {
//		// edit s.inoue 2009/12/16
//		// int intItem = jCombobox_LookFeel.getSelectedIndex();
//		String selectItem = jCombobox_LookFeel.getSelectedItem().toString();
//		changeTheLookAndFeel(selectItem);
//	}
//
//	/* comboイベント */
//	public void changeTheLookAndFeel(String value) {
//	    try {
//
//	    	for (int i = 0; i < looks.length; i++) {
//				if (looks[i].getName().equals(value)){
//					looksinfo = looks[i];break;
//				}
//			}
//	    	// UIManager.setLookAndFeel(looks[value].getClassName());
//	    	UIManager.setLookAndFeel(looksinfo.getClassName());
//
//	    	SwingUtilities.updateComponentTreeUI(this);
//	    } catch (Exception e) {
//	      e.printStackTrace();
//	    }
//	}
}

//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

