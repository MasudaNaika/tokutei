package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.shoken;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.filter.FilterSetting;
import jp.or.med.orca.jma_tokutei.common.scene.JScene;

import org.apache.log4j.Logger;
import org.openswing.swing.client.GridControl;
import org.openswing.swing.client.GridControl.ColumnContainer;
import org.openswing.swing.export.java.ExportOptions;
import org.openswing.swing.message.receive.java.ErrorResponse;
import org.openswing.swing.message.receive.java.Response;
import org.openswing.swing.message.receive.java.VOListResponse;
import org.openswing.swing.message.receive.java.VOResponse;
import org.openswing.swing.message.receive.java.ValueObject;
import org.openswing.swing.message.send.java.GridParams;
import org.openswing.swing.server.QueryUtil;
import org.openswing.swing.server.UserSessionParameters;
import org.openswing.swing.table.client.GridController;
import org.openswing.swing.table.java.GridDataLocator;

/**
 * Ctl画面
 * @author s.inoue
 * @version 2.0
 */
public class JShokenMasterMaintenanceListFrameCtrl
		extends GridController
		implements GridDataLocator {

	private static final long serialVersionUID = 677230912170503336L;	// edit n.ohkubo 2014/10/01
	
	private JShokenMasterMaintenanceListFrame grid = null;
//	private JShokenMasterMaintenanceListFrame frame;	// edit n.ohkubo 2014/10/01　未使用なので削除
	private Connection conn = null;

	private static Logger logger = Logger.getLogger(JShokenMasterMaintenanceListFrameCtrl.class);
	
	private boolean firstViewFlg = true;	// edit n.ohkubo 2014/10/01

	public JShokenMasterMaintenanceListFrameCtrl(Connection conn) {
		this.conn = conn;
		grid = new JShokenMasterMaintenanceListFrame(conn, this);
	}

	public JShokenMasterMaintenanceListFrame getGridControl(){
		return grid;
	}

	/**
	* @param attributeName attribute name that identify a grid column
	* @return tooltip text to show in the column header; this text will be automatically translated according to internationalization settings
	*/
	@Override
	public String getHeaderTooltip(String attributeName) {
	    return attributeName;
	}

    /**
	* @param row row index in the grid
	* @param attributeName attribute name that identify a grid column
	* @return tooltip text to show in the cell identified by the specified row and attribute name; this text will be automatically translated according to internationalization settings
	*/
	@Override
	public String getCellTooltip(int row,String attributeName) {

		return (String) grid.getGrid().getVOListTableModel().getField(row,attributeName);
//		return attributeName+" at row "+row;
	}

	/**
	 * INSERT modeの時、保存前に呼ばれる Callback関数(save buttonが押された時).
	 *
	 * @return <code>true</code> 保存許可, <code>false</code> 保存中断
	 */
	@Override
	public boolean beforeInsertGrid(GridControl grid) {
		// new JShokenMasterMaintenanceDetailCtl(this.grid,null, null, conn);
		return false;
	}

	/**
	 * gridがdouble clickedされた時、Callback関数
	 * @param rowNumber selected row index
	 * @param persistentObject v.o. related to the selected row
	 */
	@Override
	public void doubleClick(int rowNumber, ValueObject persistentObject) {
		JShokenMasterMaintenanceListData vo = (JShokenMasterMaintenanceListData)persistentObject;

		// openswing s.inoue 2011/02/09
		// new JShokenMasterMaintenanceDetailCtl(grid, vo.getSYOKEN_TYPE(),vo.getSYOKEN_NO(), conn);
//		JScene.ChangeScene(grid,new JShokenMasterMaintenanceDetailCtl(
//				vo.getSYOKEN_NO()),new WindowRefreshEvent());
		JScene.ChangeScene(grid,new JShokenMasterMaintenanceEditFrameCtrl(
				vo.getSYOKEN_TYPE(), vo.getSYOKEN_NO()),new WindowRefreshEvent());
	}

    /**
     * Callbackメソッド
     * exportダイアログを表示する前に呼び出す
     * gridに定義されたformatを再定義して呼び出せます
     * @return list of available formats; possible values: ExportOptions.XLS_FORMAT, ExportOptions.CSV_FORMAT1, ExportOptions.CSV_FORMAT2, ExportOptions.XML_FORMAT, ExportOptions.XML_FORMAT_FAT, ExportOptions.HTML_FORMAT, ExportOptions.PDF_FORMAT, ExportOptions.RTF_FORMAT; default value: ClientSettings.EXPORTING_FORMATS
     */
	 @Override
	public String[] getExportingFormats() {
		 return new String[]{ ExportOptions.XLS_FORMAT,ExportOptions.HTML_FORMAT,ExportOptions.XML_FORMAT };
	 }

// del s.inoue 2013/03/14
//	 // add s.inoue 2011/04/05
//	 /**
//	   * Callbackメソッド
//	   * gridからexportを実施したときに呼び出す
//	   * @param exportOptions options used to export data; these options can be programmatically changed, in order to customize exporting result
//	   */
//	 public void exportGrid(ExportOptions exportOptions) {
//		 GridExportOptions empsOpts = (GridExportOptions)exportOptions.getComponentsExportOptions().get(1);
//
//		 final GridExportOptions wdOpts = frame.getGrid().getDefaultGridExportOptions();
//		 GridExportCallbacks callbacks = new GridExportCallbacks() {
//		     /**
//		     * @param vo value object related to current exported row
//		     * @param row row index just exported
//		     * @return method invoked after adding a row to export document; if not null, then specified ComponentExportOptions will be added after current row
//		     */
//		    public ComponentExportOptions getComponentPerRow(ValueObject vo,int row) {
//		    	JShokenMasterMaintenanceListData empVO = (JShokenMasterMaintenanceListData)vo;
//		        wdOpts.getOtherGridParams().put("empCode",empVO.getSYOKEN_NO());
//		        return wdOpts;
//		    }
//
//	    };
//	    empsOpts.setCallbacks(callbacks);
//	 }

	// edit n.ohkubo 2014/10/01　start　ソート設定の不具合や0件メッセージ等の修正を行うので、メソッドを新規で作成（既存のロジックは全コメント）
//	/**
//	 * gridのデータがロードされた時のCallback関数
//	 * @param PREVIOUS_BLOCK_ACTION:前行へ移動, NEXT_BLOCK_ACTION:次行へ移動 or LAST_BLOCK_ACTION:最終行へ移動
//	 * @param startPos start position of data fetching in result set
//	 * @param filteredColumns filtered columns
//	 * @param currentSortedColumns sorted columns
//	 * @param currentSortedVersusColumns ordering versus of sorted columns
//	 * @param valueObjectType v.o. type
//	 * @param otherGridParams other grid parameters
//	 * @return response from the server: an object of type VOListResponse
//	 *  if data loading was successfully completed, or an ErrorResponse onject if some error occours
//	 */
//	public Response loadData(int action, int startIndex, Map filteredColumns,
//			ArrayList currentSortedColumns,
//			ArrayList currentSortedVersusColumns, Class valueObjectType,
//			Map otherGridParams) {
//
//		try {
//			ArrayList vals = new ArrayList();
//			Map attribute2dbField = new HashMap();
//			attribute2dbField.put("SYOKEN_TYPE","SYOKEN_TYPE");
//			attribute2dbField.put("SYOKEN_TYPE_NAME","SYOKEN_TYPE_NAME");
//			attribute2dbField.put("SYOKEN_NO","SYOKEN_NO");
//			attribute2dbField.put("SYOKEN_NAME","SYOKEN_NAME");
//			attribute2dbField.put("UPDATE_TIMESTAMP","UPDATE_TIMESTAMP");
//
//
//			GridParams gridParams = new GridParams(action, startIndex,
//					filteredColumns, currentSortedColumns,
//					currentSortedVersusColumns, otherGridParams);
//
////			// add s.inoue 2013/02/25
////			try {
////				JApplication.kikanDatabase.getMConnection().setAutoCommit(false);
////			} catch (SQLException ex) {
////				logger.warn(ex.getMessage());
////			}
//
//			StringBuilder sql = new StringBuilder();
//			sql.append("SELECT SYOKEN_TYPE,SYOKEN_TYPE_NAME,");
//			sql.append(" SYOKEN_NO, SYOKEN_NAME, UPDATE_TIMESTAMP");
//			sql.append(" FROM T_SYOKENMASTER ");
//			// add s.inoue 2012/05/07
//			if (currentSortedVersusColumns.size() == 0)
//				sql.append(" ORDER BY SYOKEN_TYPE,SYOKEN_NO ");
//
//			return QueryUtil
//					.getQuery(
//							conn,
//							new UserSessionParameters(),
//							sql.toString(),
//							vals,
//							attribute2dbField,
//							JShokenMasterMaintenanceListData.class,
//							"Y", "N",
//							null, gridParams, JApplication.gridViewMasterCount, true);
//
//		} catch (Exception ex) {
//			ex.printStackTrace();
//			return new ErrorResponse(ex.getMessage());
//		}
//	}
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
	@Override
	public Response loadData(int action, int startIndex, Map filteredColumns, ArrayList currentSortedColumns, ArrayList currentSortedVersusColumns, Class valueObjectType, Map otherGridParams) {

		try {
			Map<String, String> attribute2dbField = new HashMap<String, String>();
			attribute2dbField.put("SYOKEN_TYPE","SYOKEN_TYPE");
			attribute2dbField.put("SYOKEN_TYPE_NAME","SYOKEN_TYPE_NAME");
			attribute2dbField.put("SYOKEN_NO","SYOKEN_NO");
			attribute2dbField.put("SYOKEN_NAME","SYOKEN_NAME");
			attribute2dbField.put("UPDATE_TIMESTAMP","UPDATE_TIMESTAMP");

			GridParams gridParams = new GridParams(action, startIndex, filteredColumns, currentSortedColumns, currentSortedVersusColumns, otherGridParams);
			
			//検索・ソート条件の設定用共通クラス
			FilterSetting filterSetting = new FilterSetting();
			
			//「を含む」検索は「like %入力値%」で検索するため、入力値に"%"を付加する
			filterSetting.setLikeColumns(filteredColumns, true);

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT SYOKEN_TYPE,SYOKEN_TYPE_NAME,");
			sql.append(" SYOKEN_NO, SYOKEN_NAME, UPDATE_TIMESTAMP");
			sql.append(" FROM T_SYOKENMASTER ");
			if (currentSortedVersusColumns.size() == 0) {
				sql.append(" ORDER BY SYOKEN_TYPE,SYOKEN_NO ");
			}

	    	//データの取得実行
			Response result = QueryUtil.getQuery(
											conn,
											new UserSessionParameters(),
											sql.toString(),
											new ArrayList<String>(),
											attribute2dbField,
											JShokenMasterMaintenanceListData.class,
											"Y",
											"N",
											null,
											gridParams,
											JApplication.gridViewMasterCount,
											true
			);

			//「を含む」検索は「like %入力値%」で検索するため、入力値に"%"を付加しているので、付加した"%"を削除する
			filterSetting.setLikeColumns(filteredColumns, false);

			//検索画面へソート条件を反映する（検索後、検索画面を閉じて再度開いたときに、値が反映されていない現象の対応）
			ColumnContainer columnContainer = grid.getColumnContainer();
			filterSetting.setSortColumnsAfter(columnContainer, currentSortedColumns, currentSortedVersusColumns);
			
			//0件の場合、メッセージの表示（画面オープン初回以外）
			if (!firstViewFlg) {
				if ((result != null) && (!result.isError() && (result instanceof VOListResponse))){
					if (((VOListResponse)result).getRows().size() == 0) {
//						JErrorMessage.show("M3550", getGridControl());	// edit n.ohkubo 2015/03/01　削除
						JErrorMessage.show("M9923", getGridControl());	// edit n.ohkubo 2015/03/01　追加　メッセージの変更
					}
				}
			}

			firstViewFlg = false;
			
			return result;
			

		} catch (Exception ex) {
			ex.printStackTrace();
			return new ErrorResponse(ex.getMessage());
		}
	}
	// edit n.ohkubo 2014/10/01　end　ソート設定の不具合や0件メッセージ等の修正を行うので、メソッドを新規で作成（既存のロジックは全コメント）


	  /**
	   * Method invoked when the user has clicked on save button and the grid is in INSERT mode.
	   * @param rowNumbers row indexes related to the new rows to save
	   * @param newValueObjects list of new value objects to save
	   * @return an ErrorResponse value object in case of errors, VOListResponse if the operation is successfully completed
	   */
	  @Override
	public Response insertRecords(int[] rowNumbers, ArrayList newValueObjects) throws Exception {

		PreparedStatement stmt = null;

		System.out.println("row" + rowNumbers[0] + "value" + newValueObjects);

		if (rowNumbers[0] <= 0)
			return new VOListResponse(newValueObjects,false,newValueObjects.size());

		try {
	    	  StringBuffer sb = new StringBuffer();
			  sb.append("INSERT INTO T_SYOKENMASTER (SYOKEN_TYPE,SYOKEN_TYPE_NAME, SYOKEN_NO, SYOKEN_NAME, UPDATE_TIMESTAMP)");
			  sb.append("VALUES (?,?,?,?,?)");

			  stmt = conn.prepareStatement(sb.toString());

		      // 行毎追加処理
		      JShokenMasterMaintenanceListData vo = null;

		      for(int i=0;i<newValueObjects.size();i++) {
		        vo = (JShokenMasterMaintenanceListData)newValueObjects.get(i);
		        stmt.setString(1,vo.getSYOKEN_TYPE());
		        stmt.setString(2,vo.getSYOKEN_TYPE_NAME());
		        stmt.setString(3,vo.getSYOKEN_NO());
		        stmt.setString(4,vo.getSYOKEN_NAME());
		        stmt.setString(5,vo.getUPDATE_TIMESTAMP());
		        stmt.execute();
		      }
        return new VOListResponse(newValueObjects,false,newValueObjects.size());
		}catch (SQLException ex) {
			logger.error(ex.getMessage());
	      return new ErrorResponse(JErrorMessage.getMessageValue("M9912"));
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
//	 /**
//	   * Method invoked when the user has clicked on save button and the grid is in INSERT mode.
//	   * @param rowNumbers row indexes related to the new rows to save
//	   * @param newValueObjects list of new value objects to save
//	   * @return an ErrorResponse value object in case of errors, VOListResponse if the operation is successfully completed
//	   */
//	  public Response insertRecords(int[] rowNumbers, ArrayList newValueObjects) throws Exception {
//	    try {
//	      Map attribute2dbField = new HashMap();
//	      attribute2dbField.put("SYOKEN_TYPE","SYOKEN_TYPE");
//	      attribute2dbField.put("SYOKEN_TYPE_NAME","SYOKEN_TYPE_NAME");
//	      attribute2dbField.put("SYOKEN_NO","SYOKEN_NO");
//	      attribute2dbField.put("SYOKEN_NAME","SYOKEN_NAME");
//	      attribute2dbField.put("UPDATE_TIMESTAMP","UPDATE_TIMESTAMP");
//
//	      return QueryUtil.insertTable(
//	        conn,
//	        new UserSessionParameters(),
//	        (ValueObject)newValueObjects.get(0),
//	        "T_SYOKENMASTER",
//	        attribute2dbField,
//	        "Y",
//	        "N",
//	        null,
//	        true
//	      );
//	    }
//	    catch (Exception ex) {
//	      ex.printStackTrace();
//	      return new ErrorResponse(ex.getMessage());
//	    }
//	    finally {
//	      try {
//	        conn.commit();
//	      }
//	      catch (SQLException ex1) {
//	      }
//	    }
//	  }

	// add s.inoue 2013/02/27
    @Override
	public void afterDeleteGrid()
    {
    	grid.reloadButton.doClick();
    }

	/**
	 * 削除ボタンイベント ※gridがREADONLY
	 * @param persistentObjects value objects to delete (related to the currently selected rows)
	 * @return an ErrorResponse value object in case of errors, VOResponse if the operation is successfully completed
	 */
	@Override
	public Response deleteRecords(ArrayList persistentObjects) throws Exception {
		PreparedStatement stmt = null;

		try {

			StringBuilder sb = new StringBuilder();
			sb.append(" delete from T_SYOKENMASTER");
			sb.append(" WHERE SYOKEN_TYPE = ?");
			sb.append(" AND SYOKEN_NO = ?");
			stmt = conn.prepareStatement(sb.toString());

			for (int i = 0; i < persistentObjects.size(); i++) {
				JShokenMasterMaintenanceListData vo = (JShokenMasterMaintenanceListData)persistentObjects.get(i);
				stmt.setString(1, vo.getSYOKEN_TYPE());
				stmt.setString(2, vo.getSYOKEN_NO());
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
	 * 遷移先の画面から戻ってきた場合
	 */
	public class WindowRefreshEvent extends WindowAdapter {
		@Override
		public void windowClosed(WindowEvent e) {
			grid.setEnabled(true);
			// eidt s.inoue 2011/04/08
			grid.reloadButton.doClick();
		}
	}

}
