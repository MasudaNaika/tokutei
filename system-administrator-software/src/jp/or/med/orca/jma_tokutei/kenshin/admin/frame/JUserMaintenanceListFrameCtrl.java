package jp.or.med.orca.jma_tokutei.kenshin.admin.frame;

import org.apache.log4j.Logger;
import org.openswing.swing.table.client.GridController;
import org.openswing.swing.message.receive.java.*;
//import java.util.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;

import org.openswing.swing.table.java.GridDataLocator;
import org.openswing.swing.client.GridControl;
//import org.openswing.swing.server.QueryUtil;
import org.openswing.swing.server.UserSessionParameters;
import org.openswing.swing.message.send.java.GridParams;

/**
 * Ctl画面
 * @author s.inoue
 * @version 2.0
 */
public class JUserMaintenanceListFrameCtrl
		extends GridController
		implements GridDataLocator {

	private JUserMaintenanceListFrame grid = null;
	private JUserMaintenanceListFrame frame;
	private Connection conn = null;

	private static Logger logger = Logger.getLogger(JUserMaintenanceListFrameCtrl.class);

	public JUserMaintenanceListFrameCtrl(Connection conn) {
		this.conn = conn;
		grid = new JUserMaintenanceListFrame(conn, this);
	}

	public JUserMaintenanceListFrame getGridControl(){
		return grid;
	}

	/**
	* @param attributeName attribute name that identify a grid column
	* @return tooltip text to show in the column header; this text will be automatically translated according to internationalization settings
	*/
	public String getHeaderTooltip(String attributeName) {
	    return attributeName;
	}

    /**
	* @param row row index in the grid
	* @param attributeName attribute name that identify a grid column
	* @return tooltip text to show in the cell identified by the specified row and attribute name; this text will be automatically translated according to internationalization settings
	*/
	public String getCellTooltip(int row,String attributeName) {

		return (String) grid.getGrid().getVOListTableModel().getField(row,attributeName);
//		return attributeName+" at row "+row;
	}

//	/**
//	 * INSERT modeの時、保存前に呼ばれる Callback関数(save buttonが押された時).
//	 *
//	 * @return <code>true</code> 保存許可, <code>false</code> 保存中断
//	 */
//	public boolean beforeInsertGrid(GridControl grid) {
////		new JUserMaintenanceDetailFrameCtrl(this.grid,null, null, conn);
////		return false;
//		return true;
//	}

	/**
	 * gridがdouble clickedされた時、Callback関数
	 * @param rowNumber selected row index
	 * @param persistentObject v.o. related to the selected row
	 */
	public void doubleClick(int rowNumber, ValueObject persistentObject) {
//		JUserMaintenanceListFrameData vo = (JUserMaintenanceListFrameData)persistentObject;
//		new JUserMaintenanceDetailFrameCtrl(grid, vo.getUserName(), conn);
	}

	/**
	 * gridのデータがロードされた時のCallback関数
	 * @param PREVIOUS_BLOCK_ACTION:前行へ移動, NEXT_BLOCK_ACTION:次行へ移動 or LAST_BLOCK_ACTION:最終行へ移動
	 * @param startPos start position of data fetching in result set
	 * @param filteredColumns filtered columns
	 * @param currentSortedColumns sorted columns
	 * @param currentSortedVersusColumns ordering versus of sorted columns
	 * @param valueObjectType v.o. type
	 * @param otherGridParams other grid parameters
	 * @return response from the server: an object of type VOListResponse
	 *  if data loading was successfully completed, or an ErrorResponse onject if some error occours
	 */
	public Response loadData(int action, int startIndex, Map filteredColumns,
			ArrayList currentSortedColumns,
			ArrayList currentSortedVersusColumns, Class valueObjectType,
			Map otherGridParams) {

		try {
			ArrayList vals = new ArrayList();
			Map attribute2dbField = new HashMap();
			attribute2dbField.put("USER_NAME","USER_NAME");
			attribute2dbField.put("PASS","PASS");

			GridParams gridParams = new GridParams(action, startIndex,
					filteredColumns, currentSortedColumns,
					currentSortedVersusColumns, otherGridParams);
// del s.inoue 2013/02/28
//			// add s.inoue 2013/02/25
//			try {
//				JApplication.kikanDatabase.getMConnection().setAutoCommit(false);
//			} catch (SQLException ex) {
//				logger.warn(ex.getMessage());
//			}

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT USER_NAME,PASS");
			sql.append(" FROM T_SYS_USER ");

			return org.openswing.swing.server.QueryUtil
					.getQuery(
							conn,
							new UserSessionParameters(),
							sql.toString(),
							vals,
							attribute2dbField,
							JUserMaintenanceListFrameData.class,
							"Y", "N",
							null, gridParams, JApplication.gridViewMasterCount, true);

		} catch (Exception ex) {
			ex.printStackTrace();
			return new ErrorResponse(ex.getMessage());
		}
	}

	// add s.inoue 2013/02/27
    public void afterDeleteGrid()
    {
    	grid.reloadButton.doClick();
    }

	/**
	 * 削除ボタンイベント ※gridがREADONLY
	 * @param persistentObjects value objects to delete (related to the currently selected rows)
	 * @return an ErrorResponse value object in case of errors, VOResponse if the operation is successfully completed
	 */
	public Response deleteRecords(ArrayList persistentObjects) throws Exception {
		PreparedStatement stmt = null;

		try {

			StringBuilder sb = new StringBuilder();
			sb.append(" DELETE from T_SYS_USER WHERE USER_NAME = ?");
			stmt = conn.prepareStatement(sb.toString());

			for (int i = 0; i < persistentObjects.size(); i++) {
				JUserMaintenanceListFrameData vo = (JUserMaintenanceListFrameData)persistentObjects.get(i);
				stmt.setString(1, vo.getUSER_NAME());
				stmt.execute();
			}
			return new VOResponse(new Boolean(true));
		} catch (SQLException ex) {
			ex.printStackTrace();
			return new ErrorResponse(ex.getMessage());
		} finally {
			try {
				stmt.close();
				conn.commit();
			} catch (SQLException ex1) {
			}
		}
	}

	  /**
	   * Method invoked when the user has clicked on save button and the grid is in INSERT mode.
	   * @param rowNumbers row indexes related to the new rows to save
	   * @param newValueObjects list of new value objects to save
	   * @return an ErrorResponse value object in case of errors, VOListResponse if the operation is successfully completed
	   */
	  public Response insertRecords(int[] rowNumbers, ArrayList newValueObjects) throws Exception {

		PreparedStatement stmt = null;

		System.out.println("row" + rowNumbers[0] + "value" + newValueObjects);

		try {
	    	  StringBuffer sb = new StringBuffer();
			  sb.append("INSERT INTO T_SYS_USER (USER_NAME, PASS)");
			  sb.append("VALUES (?,?)");

			  stmt = conn.prepareStatement(sb.toString());

		      // 行毎追加処理
		      JUserMaintenanceListFrameData vo = null;

		      for(int i=0;i<newValueObjects.size();i++) {
		        vo = (JUserMaintenanceListFrameData)newValueObjects.get(i);
		        stmt.setString(1,vo.getUSER_NAME());
		        stmt.setString(2,vo.getPASS());
//		        stmt.setString(3,vo.getKENGEN());
		        stmt.execute();
		      }
      return new VOListResponse(newValueObjects,false,newValueObjects.size());
		}catch (SQLException ex) {
			logger.error(ex.getMessage());
	      return new ErrorResponse(JErrorMessage.getMessageValue("M7300"));
	    }
	    finally {
	      try {
	        stmt.close();
	        conn.commit();
	      }
	      catch (SQLException ex1) {
	      }
	    }
	  }

		 /**
	   * Method invoked when the user has clicked on save button and the grid is in EDIT mode.
	   * @param rowNumbers row indexes related to the changed rows
	   * @param oldPersistentObjects old value objects, previous the changes
	   * @param persistentObjects value objects relatied to the changed rows
	   * @return an ErrorResponse value object in case of errors, VOListResponse if the operation is successfully completed
	   */
	  public Response updateRecords(int[] rowNumbers,ArrayList oldPersistentObjects,ArrayList persistentObjects) throws Exception {
	    PreparedStatement stmt = null;

	    // add s.inoue 2012/05/08
	    RETURN_VALUE retValue = JErrorMessage.show("M7411", getGridControl());
		if (retValue == RETURN_VALUE.NO)
			return new ErrorResponse(JErrorMessage.getMessageValue("M7412"));

	    try {

	    	StringBuilder sb = new StringBuilder();
	    	sb = new StringBuilder();
	    	sb.append("UPDATE T_SYS_USER SET USER_NAME = ?,PASS = ? ");
	    	sb.append(" WHERE USER_NAME =  ? ");

	    	stmt = conn.prepareStatement(sb.toString());

	    	JUserMaintenanceListFrameData vo = null;
	    	JUserMaintenanceListFrameData voOld = null;

	      for(int i=0;i<persistentObjects.size();i++) {
	        vo = (JUserMaintenanceListFrameData)persistentObjects.get(i);
	        voOld = (JUserMaintenanceListFrameData)oldPersistentObjects.get(i);

	        if (vo == null || voOld == null)
	        	break;

	        stmt.setString(1,vo.getUSER_NAME());
	        stmt.setString(2,vo.getPASS());
//	        stmt.setString(3,vo.getKENGEN());
	        stmt.setString(3,voOld.getUSER_NAME());
	        stmt.execute();
	      }
	      return new VOListResponse(persistentObjects,false,persistentObjects.size());
	    }
	    catch (SQLException ex) {
	      ex.printStackTrace();
	      return new ErrorResponse(ex.getMessage());
	    }
	    finally {
	      try {
	        stmt.close();
	        conn.commit();
	      }
	      catch (SQLException ex1) {
	      }
	    }
	  }
}

//
////import java.awt.event.*;
//import java.awt.Component;
//import java.awt.event.ActionEvent;
//import java.awt.event.KeyEvent;
//import java.awt.event.WindowAdapter;
//import java.awt.event.WindowEvent;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.Hashtable;
//
//import javax.swing.JTable;
//import javax.swing.table.DefaultTableCellRenderer;
//import javax.swing.table.TableColumnModel;
//
//import org.apache.log4j.Logger;
//
//import jp.or.med.orca.jma_tokutei.common.app.JApplication;
//import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
//import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
//import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
//import jp.or.med.orca.jma_tokutei.common.event.JSingleDoubleClickEvent;
//import jp.or.med.orca.jma_tokutei.common.event.JEnterEvent;
//import jp.or.med.orca.jma_tokutei.common.focus.JFocusTraversalPolicy;
//import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
//import jp.or.med.orca.jma_tokutei.common.scene.JScene;
//import jp.or.med.orca.jma_tokutei.common.table.JSimpleTable;
//import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableCellPosition;
//import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableCellRowRenderer;
//import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableScrollPanel;
//
///**
// * ユーザメンテナンス
// */
//public class JUserMaintenanceListFrameCtrl extends JUserMaintenanceListFrame {
//	private JSimpleTable model = new JSimpleTable();
//
//	/* フォーカス移動制御 */
//	private JFocusTraversalPolicy focusTraversalPolicy = null;
//	private static Logger logger = Logger.getLogger(JUserMaintenanceListFrameCtrl.class);
//
//	public JUserMaintenanceListFrameCtrl() {
//		JSimpleTableScrollPanel scroll = new JSimpleTableScrollPanel(model);
//		jPanel_Table.add(scroll);
//
//		model.addHeader("ユーザ名");
//
//		jp.or.med.orca.jma_tokutei.common.table.JSimpleTableCellPosition iSetColumnList[] = { new JSimpleTableCellPosition(
//				-1, 0) };
//		model.setCellEditForbid(iSetColumnList);
//
//		init();
//		model.addKeyListener(new JEnterEvent(this, jButton_Change));
//
//		// ダブルクリックの処理
//		model.addMouseListener(new JSingleDoubleClickEvent(this, jButton_Change));
//		model.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
//
//		// focus制御
//		// edit s.inoue 2009/10/07
//		this.focusTraversalPolicy = new JFocusTraversalPolicy();
//		this.setFocusTraversalPolicy(this.focusTraversalPolicy);
//		this.focusTraversalPolicy.setDefaultComponent(this.model);
//		this.focusTraversalPolicy.addComponent(this.model);
//		this.focusTraversalPolicy.addComponent(this.jButton_Add);
//		this.focusTraversalPolicy.addComponent(this.jButton_Change);
//		this.focusTraversalPolicy.addComponent(this.jButton_Delete);
//		this.focusTraversalPolicy.addComponent(jButton_End);
//
//		// add s.inoue 2009/12/03
//		for (int i = 0; i < focusTraversalPolicy.getComponentSize(); i++) {
//			Component comp = focusTraversalPolicy.getComponent(i);
//			comp.addKeyListener(this);
//		}
//
//		this.setTitle(ViewSettings.getAdminCommonTitleWithVersion());
//
//		// add ver2 s.inoue 2009/05/08
//		TableColumnModel columns = model.getColumnModel();
//        for(int i=0;i<columns.getColumnCount();i++) {
//
//            columns.getColumn(i).setCellRenderer(new JSimpleTableCellRowRenderer(
//                (DefaultTableCellRenderer)model.getDefaultRenderer(model.getColumnClass(i))));
//        }
//	}
//
//	/**
//	 * 終了ボタン
//	 */
//	public void pushedEndButton(ActionEvent e) {
//		dispose();
//	}
//
//	/**
//	 * 追加ボタン
//	 */
//	public void pushedAddButton(ActionEvent e) {
//		JScene.CreateDialog(this, new JRegisterUserFrameCtrl(),
//				new RefreshEvent());
//	}
//
//	/**
//	 * 変更ボタン
//	 */
//	public void pushedChangeButton(ActionEvent e) {
//		int Row = model.getSelectedRow();
//		if (Row < 0) {
//			return;
//		}
//
//		String name = (String) model.getData(Row, 0);
//		JScene.CreateDialog(
//				this,
//				new JRegisterUserFrameCtrl(name),
//				new RefreshEvent()
//			);
//	}
//
//	/**
//	 * 削除ボタン
//	 */
//	public void pushedDeleteButton(ActionEvent e) {
//		int[] Rows = model.getSelectedRows();
//		if (Rows.length <= 0) {
//			return;
//		}
//
//		if (model.getRowCount() == model.getSelectedRowCount()) {
//			JErrorMessage.show("M7410", this);
//			return;
//		}
//
//		RETURN_VALUE Ret = JErrorMessage.show("M7402", this);
//
//		if (Ret == RETURN_VALUE.YES) {
//			for (int i = 0; i < Rows.length; i++) {
//				String UserName = (String) model.getData(Rows[i], 0);
//
//				try {
//					JApplication.systemDatabase
//							.sendNoResultQuery("DELETE FROM T_SYS_USER WHERE USER_NAME = "
//									+ JQueryConvert.queryConvert(UserName));
//				} catch (Exception e2) {
//					e2.printStackTrace();
//					JErrorMessage.show("M7401", this);
//				}
//			}
//
//			init();
//		}
//	}
//
//	/**
//	 * テーブルの中身を設定する
//	 */
//	private void init() {
//		model.clearAllRow();
//
//		ArrayList<Hashtable<String, String>> result = null;
//
//		try {
//			result = JApplication.systemDatabase
//					.sendExecuteQuery("SELECT * FROM T_SYS_USER");
//		} catch (SQLException e) {
//			e.printStackTrace();
//			JErrorMessage.show("M7400", this);
//			return;
//		}
//
//		for (int i = 0; i < result.size(); i++) {
//			Hashtable<String, String> item = result.get(i);
//
//			String ItemArray[] = { item.get("USER_NAME") };
//			model.addData(ItemArray);
//		}
//		// 初期選択
//		if (model.getRowCount() > 0) {
//			model.setRowSelectionInterval(0, 0);
//		} else {
//			jButton_Add.requestFocus();
//		}
//	}
//
//	public void actionPerformed(ActionEvent e) {
//		Object source = e.getSource();
//		if (source == jButton_End) {
//			logger.info(jButton_End.getText());
//			pushedEndButton(e);
//		}
//		else if (e.getSource() == jButton_Add) {
//			logger.info(jButton_Add.getText());
//			pushedAddButton(e);
//		}
//		else if (e.getSource() == jButton_Change) {
//			logger.info(jButton_Change.getText());
//			pushedChangeButton(e);
//		}
//		else if (e.getSource() == jButton_Delete) {
//			logger.info(jButton_Delete.getText());
//			pushedDeleteButton(e);
//		}
//	}
//
//	// add s.inoue 2009/12/03
//	@Override
//	public void keyPressed(KeyEvent keyEvent) {
//		switch(keyEvent.getKeyCode()){
//		case KeyEvent.VK_F1:
//			logger.info(jButton_End.getText());
//			pushedEndButton(null);break;
//		case KeyEvent.VK_F9:
//			logger.info(jButton_Add.getText());
//			pushedAddButton(null);break;
//		case KeyEvent.VK_F11:
//			logger.info(jButton_Change.getText());
//			pushedChangeButton(null);break;
//		case KeyEvent.VK_F12:
//			logger.info(jButton_Delete.getText());
//			pushedDeleteButton(null);break;
//		}
//	}
//
//	/**
//	 * ユーザ情報の登録画面を開いた後など、登録画面が閉じたときに呼び出されるイベント
//	 */
//	public class RefreshEvent extends WindowAdapter {
//		public void windowClosed(WindowEvent e) {
//			init();
//		}
//	}
//}
//
////Source Code Version Tag System v1.00
////Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}
