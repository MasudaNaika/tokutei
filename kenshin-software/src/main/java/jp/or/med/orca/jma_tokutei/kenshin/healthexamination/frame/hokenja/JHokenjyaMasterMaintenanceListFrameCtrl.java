package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.hokenja;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.scene.JScene;

import org.apache.log4j.Logger;
import org.openswing.swing.client.GridControl;
import org.openswing.swing.export.java.ExportOptions;
import org.openswing.swing.message.receive.java.ErrorResponse;
import org.openswing.swing.message.receive.java.Response;
import org.openswing.swing.message.receive.java.VOResponse;
import org.openswing.swing.message.receive.java.ValueObject;
import org.openswing.swing.message.send.java.FilterWhereClause;
import org.openswing.swing.message.send.java.GridParams;
import org.openswing.swing.server.QueryUtil;
import org.openswing.swing.server.UserSessionParameters;
import org.openswing.swing.table.client.GridController;
import org.openswing.swing.table.java.GridDataLocator;

/**
 * 一覧Ctl画面
 * @author s.inoue
 * @version 2.0
 */
public class JHokenjyaMasterMaintenanceListFrameCtrl
		extends GridController
		implements GridDataLocator,IHokenjyaMasterMaintenanceDetailFrame {

	private JHokenjyaMasterMaintenanceListFrame grid = null;
	private Connection conn = null;
	private JHokenjyaMasterMaintenanceListFrame frame;
	private static Logger logger = Logger.getLogger(JHokenjyaMasterMaintenanceListFrameCtrl.class);

	public JHokenjyaMasterMaintenanceListFrameCtrl(Connection conn) {
		this.conn = conn;
		grid = new JHokenjyaMasterMaintenanceListFrame(conn, this);
	}

	public JHokenjyaMasterMaintenanceListFrame getGridControl(){
		return grid;
	}
	/**
	 * INSERT modeの時、保存前に呼ばれる Callback関数(save buttonが押された時).
	 *
	 * @return <code>true</code> 保存許可, <code>false</code> 保存中断
	 */
	public boolean beforeInsertGrid(GridControl grid) {
//		new xJHokenjyaMasterMaintenanceDetailCtl(this.grid, null, conn);
		return false;
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
	/**
	 * gridがdouble clickedされた時、Callback関数
	 * @param rowNumber selected row index
	 * @param persistentObject v.o. related to the selected row
	 */
	public void doubleClick(int rowNumber, ValueObject persistentObject) {
		JHokenjyaMasterMaintenanceListData vo = (JHokenjyaMasterMaintenanceListData)persistentObject;
		// openswing s.inoue 2011/01/26
		// new xJHokenjyaMasterMaintenanceDetailCtl(grid, vo.getHKNJANUM(), conn);
		// 呼び出し
		JScene.ChangeScene(grid,new JHokenjyaMasterMaintenanceEditFrameCtrl(
				vo.getHKNJANUM_M()),new WindowRefreshEvent());
//		JScene.CreateDialog(getJFrame(), new JHokenjyaMasterMaintenanceEditFrameCtrl(
//				vo.getHKNJANUM()));
	}

	/**
     * Callbackメソッド
     * exportダイアログを表示する前に呼び出す
     * gridに定義されたformatを再定義して呼び出せます
     * @return list of available formats; possible values: ExportOptions.XLS_FORMAT, ExportOptions.CSV_FORMAT1, ExportOptions.CSV_FORMAT2, ExportOptions.XML_FORMAT, ExportOptions.XML_FORMAT_FAT, ExportOptions.HTML_FORMAT, ExportOptions.PDF_FORMAT, ExportOptions.RTF_FORMAT; default value: ClientSettings.EXPORTING_FORMATS
     */
	 public String[] getExportingFormats() {
		 return new String[]{ ExportOptions.XLS_FORMAT,ExportOptions.HTML_FORMAT,ExportOptions.XML_FORMAT };
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
		// PreparedStatement stmt = null;
		try {
			ArrayList vals = new ArrayList();
			Map attribute2dbField = new HashMap();
			// HKNJANUM, HKNJANAME, POST, ADRS, BANTI, TEL
			// eidt s.inoue 2012/10/29
			attribute2dbField.put("HKNJANUM_M","GET_HKNJANUM.hknjanum");
			attribute2dbField.put("HKNJANAME","HKNJANAME");
			attribute2dbField.put("POST","POST");
			attribute2dbField.put("ADRS","ADRS");
			attribute2dbField.put("TEL","TEL");

			GridParams gridParams = new GridParams(action, startIndex,
					filteredColumns, currentSortedColumns,
					currentSortedVersusColumns, otherGridParams);

			// 初期値 又は あいまい検索の設定
		    if (filteredColumns.values().iterator().hasNext()){
				Iterator it_dt = filteredColumns.values().iterator();
			    FilterWhereClause[] filterClauses = null;

			    while(it_dt.hasNext()) {
			      filterClauses = (FilterWhereClause[])it_dt.next();
			      // comment
//			      System.out.println(filterClauses[0].getAttributeName());
//			      System.out.println(filterClauses[0].getValue());
//			      System.out.println(filterClauses[0].getOperator());

			      if (filterClauses[0].getOperator().equals("like")){
			    	// add s.inoue 2014/03/18
			    	  String filterval = filterClauses[0].getValue().toString();
			    	  if(!filterval.startsWith("%"))
			    		  filterval = "%"+filterval+"%";
			    	  filterClauses[0].setValue(filterval);

						gridParams.getFilteredColumns().put(filterClauses[0].getAttributeName(),
								new FilterWhereClause[] { filterClauses[0], null });
			      }
			    }
		    }else{
//				FilterWhereClause clauseDesign = new FilterWhereClause();
//				clauseDesign.setAttributeName("NENDO");
//				clauseDesign.setOperator("=");
//				clauseDesign.setValue(2012);
//
//				gridParams.getFilteredColumns().put(clauseDesign.getAttributeName(),
//						new FilterWhereClause[] { clauseDesign, null });
		    }

			// eidt s.inoue 2011/04/08
			try {
				JApplication.kikanDatabase.getMConnection().setAutoCommit(false);
			} catch (SQLException ex) {
				logger.warn(ex.getMessage());
			}
			// eidt s.inoue 2012/10/29
			// String sql = "SELECT DISTINCT(HKNJANUM) HKNJANUMBER, HKNJANAME, POST, ADRS, TEL FROM T_HOKENJYA ORDER BY HKNJANUM DESC";
			String sql = "SELECT DISTINCT GET_HKNJANUM.hknjanum,HKNJANAME, POST, ADRS, TEL FROM T_HOKENJYA LEFT JOIN (select DISTINCT(hknjanum) from T_HOKENJYA) as GET_HKNJANUM ON GET_HKNJANUM.hknjanum = T_HOKENJYA.HKNJANUM";
			return QueryUtil
					.getQuery(
							conn,
							new UserSessionParameters(),
							sql,
							vals,
							attribute2dbField,
							JHokenjyaMasterMaintenanceListData.class,
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

	// eidt s.inoue 2011/06/07
	/**
	 * 削除ボタンイベント ※gridがREADONLY
	 * @param persistentObjects value objects to delete (related to the currently selected rows)
	 * @return an ErrorResponse value object in case of errors, VOResponse if the operation is successfully completed
	 */
	public Response deleteRecords(ArrayList persistentObjects) throws Exception {
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("delete from T_HOKENJYA where HKNJANUM=?");
			for (int i = 0; i < persistentObjects.size(); i++) {
				JHokenjyaMasterMaintenanceListData vo = (JHokenjyaMasterMaintenanceListData)persistentObjects.get(i);
				stmt.setString(1, vo.getHKNJANUM_M());
				stmt.execute();
			}

			stmt = conn.prepareStatement("delete from T_KENSHINMASTER where HKNJANUM=?");
			for (int i = 0; i < persistentObjects.size(); i++) {
				JHokenjyaMasterMaintenanceListData vo = (JHokenjyaMasterMaintenanceListData)persistentObjects.get(i);
				stmt.setString(1, vo.getHKNJANUM_M());
				stmt.execute();
			}
			// eidt s.inoue 2011/06/07
			grid.reloadButton.doClick();

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

	/* frameInterface */
	@Override
	public GridControl getGrid() {
		return frame.getGrid();
	}

	@Override
	public void jbInit() {
		try {
			frame.jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void reloadData() {
		frame.reloadData();
	}

	@Override
	public void setControl(Connection conn,
			JHokenjyaMasterMaintenanceListFrameCtrl controller) {
		frame.setControl(conn, controller);

	}

	@Override
	public void setJFrameeDitable() {
		frame.setJFrameeDitable();
	}

	/**
	 * 遷移先の画面から戻ってきた場合
	 */
	public class WindowRefreshEvent extends WindowAdapter {
		public void windowClosed(WindowEvent e) {
			grid.setEnabled(true);
			// eidt s.inoue 2011/05/12
			// grid.reloadData();
			grid.reloadButton.doClick();
		}
	}

}
