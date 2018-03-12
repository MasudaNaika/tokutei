package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.keinen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
import jp.or.med.orca.jma_tokutei.common.filter.FilterSetting;

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
 * 一覧Ctl画面
 * @author s.inoue
 * @version 2.0
 */
public class JKeinenMasterMaintenanceListFrameCtrl
		extends GridController
		implements GridDataLocator {

	private static final long serialVersionUID = -8442816488600129926L;	// edit n.ohkubo 2014/10/01　追加
	
	private JKeinenMasterMaintenanceListFrame grid = null;
	private Connection conn = null;
//	private JKeinenMasterMaintenanceListFrame frame;	// edit n.ohkubo 2014/10/01　未使用なので削除
	// add s.inoue 2012/11/21
	private boolean firstViewFlg =true;

    private static org.apache.log4j.Logger logger
    	= Logger.getLogger(JKeinenMasterMaintenanceListFrameCtrl.class);

	public JKeinenMasterMaintenanceListFrameCtrl(Connection conn) {
		this.conn = conn;
		grid = new JKeinenMasterMaintenanceListFrame(conn, this);
	}

	public JKeinenMasterMaintenanceListFrame getGridControl(){
		return grid;
	}
	/**
	 * INSERT modeの時、保存前に呼ばれる Callback関数(save buttonが押された時).
	 *
	 * @return <code>true</code> 保存許可, <code>false</code> 保存中断
	 */
	@Override
	public boolean beforeInsertGrid(GridControl grid) {
		// new JKeinenMasterMaintenanceDetailCtl(this.grid, null, conn);
		return false;
	}

	/**
	 * gridがdouble clickedされた時、Callback関数
	 * @param rowNumber selected row index
	 * @param persistentObject v.o. related to the selected row
	 */
	@Override
	public void doubleClick(int rowNumber, ValueObject persistentObject) {
		// JKeinenMasterMaintenanceListData vo = (JKeinenMasterMaintenanceListData)persistentObject;
		// new JKeinenMasterMaintenanceDetailCtl(grid, vo.getUKETUKE_ID(), conn);
	}

	/**
    * Callbackメソッド
    * exportダイアログを表示する前に呼び出す
    * gridに定義されたformatを再定義して呼び出せます
    * @return list of available formats; possible values: ExportOptions.XLS_FORMAT, ExportOptions.CSV_FORMAT1, ExportOptions.CSV_FORMAT2, ExportOptions.XML_FORMAT, ExportOptions.XML_FORMAT_FAT, ExportOptions.HTML_FORMAT, ExportOptions.PDF_FORMAT, ExportOptions.RTF_FORMAT; default value: ClientSettings.EXPORTING_FORMATS
    */
	@Override
	public String[] getExportingFormats() {
		return new String[]{ ExportOptions.XLS_FORMAT,ExportOptions.CSV_FORMAT1,ExportOptions.CSV_FORMAT2,ExportOptions.HTML_FORMAT,ExportOptions.XML_FORMAT };
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
//	@Override
//	public Response loadData(int action, int startIndex, Map filteredColumns,
//			ArrayList currentSortedColumns,
//			ArrayList currentSortedVersusColumns, Class valueObjectType,
//			Map otherGridParams) {
//		try {
//			ArrayList vals = new ArrayList();
//		    Map attribute2dbField = new HashMap();
//			attribute2dbField.put("NAYOSE_NO","NAYOSE_NO");
//			attribute2dbField.put("JYUSHIN_SEIRI_NO","JYUSHIN_SEIRI_NO");
//			attribute2dbField.put("NAME","NAME");
//			attribute2dbField.put("KANANAME","KANANAME");
//			attribute2dbField.put("BIRTHDAY","BIRTHDAY");
//			attribute2dbField.put("SEX","SEX");
//
//			attribute2dbField.put("HOME_ADRS","HOME_ADRS");
//			attribute2dbField.put("HIHOKENJYASYO_KIGOU","HIHOKENJYASYO_KIGOU");
//			attribute2dbField.put("HIHOKENJYASYO_NO","HIHOKENJYASYO_NO");
//			attribute2dbField.put("UKETUKE_ID","UKETUKE_ID");
//			attribute2dbField.put("UPDATE_TIMESTAMP","UPDATE_TIMESTAMP");
//
//			GridParams gridParams = new GridParams(action, startIndex,
//					filteredColumns, currentSortedColumns,
//					currentSortedVersusColumns, otherGridParams);
//
//			// 初期値 又は あいまい検索の設定
//		    if (filteredColumns.values().iterator().hasNext()){
//				Iterator it_dt = filteredColumns.values().iterator();
//			    FilterWhereClause[] filterClauses = null;
//
//			    while(it_dt.hasNext()) {
//			      filterClauses = (FilterWhereClause[])it_dt.next();
//			      // comment
////			      System.out.println(filterClauses[0].getAttributeName());
////			      System.out.println(filterClauses[0].getValue());
////			      System.out.println(filterClauses[0].getOperator());
//
//			      if (filterClauses[0].getOperator().equals("like")){
//						// add s.inoue 2014/03/18
//				    	String filterval = filterClauses[0].getValue().toString();
//				    	if(!filterval.startsWith("%"))
//				    	  filterval = "%"+filterval+"%";
//				    	  filterClauses[0].setValue(filterval);
//
//						gridParams.getFilteredColumns().put(filterClauses[0].getAttributeName(),
//								new FilterWhereClause[] { filterClauses[0], null });
//			      }
//			    }
//		    }else{
////				FilterWhereClause clauseDesign = new FilterWhereClause();
////				clauseDesign.setAttributeName("NENDO");
////				clauseDesign.setOperator("=");
////				clauseDesign.setValue(2012);
////
////				gridParams.getFilteredColumns().put(clauseDesign.getAttributeName(),
////						new FilterWhereClause[] { clauseDesign, null });
//		    }
//
//			// add s.inoue 2013/02/25
//			try {
//				JApplication.kikanDatabase.getMConnection().setAutoCommit(false);
//			} catch (SQLException ex) {
//				logger.warn(ex.getMessage());
//			}
//
//			// add s.inoue 2013/02/25
//			conn = grid.getConnection();
//
//			// 名寄せテーブル読み込み
//			// String sql = "SELECT SHIHARAI_DAIKO_NO, SHIHARAI_DAIKO_NAME, SHIHARAI_DAIKO_ZIPCD, SHIHARAI_DAIKO_ADR, SHIHARAI_DAIKO_TEL FROM T_SHIHARAI";
//			StringBuilder sb = new StringBuilder();
//			sb.append("SELECT TN.NAYOSE_NO NAYOSE_NO, TK.JYUSHIN_SEIRI_NO JYUSHIN_SEIRI_NO, TK.NAME NAME, TK.KANANAME KANANAME,");
//			sb.append(" TK.BIRTHDAY BIRTHDAY, TK.HOME_ADRS HOME_ADRS,TK.HIHOKENJYASYO_KIGOU HIHOKENJYASYO_KIGOU,");
//			sb.append(" TK.HIHOKENJYASYO_NO HIHOKENJYASYO_NO, TN.UPDATE_TIMESTAMP UPDATE_TIMESTAMP, TN.UKETUKE_ID UKETUKE_ID, ");
//			sb.append(" case TK.SEX when 1 then '男' when 2 then '女' end as SEX");
//			sb.append(" FROM T_NAYOSE TN ");
//			sb.append(" LEFT JOIN T_KOJIN TK ON TN.UKETUKE_ID = TK.UKETUKE_ID ");
//
//	    	// eidt s.inoue 2012/11/16
//	    	if (firstViewFlg)
//			sb.append(" ORDER BY NAYOSE_NO,UPDATE_TIMESTAMP ");
//			// openswing s.inoue 2010/12/17
////			sb.append("SELECT NAYOSE_NO ");
//			// , UKETUKE_ID, UPDATE_TIMESTAMP");
////			sb.append(" FROM T_NAYOSE ");
//
//	    	// add s.inoue 2012/11/16
//			firstViewFlg = false;
//
//			return QueryUtil
//					.getQuery(
//					conn,
//					new UserSessionParameters(),
//					sb.toString(),
//					vals,
//					attribute2dbField,
//					JKeinenMasterMaintenanceListData.class,
//					"Y", "N",
//					null, gridParams, JApplication.gridViewMasterCount, true);
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
			attribute2dbField.put("NAYOSE_NO","NAYOSE_NO");
			attribute2dbField.put("JYUSHIN_SEIRI_NO","JYUSHIN_SEIRI_NO");
			attribute2dbField.put("NAME","NAME");
			attribute2dbField.put("KANANAME","KANANAME");
			attribute2dbField.put("BIRTHDAY","BIRTHDAY");
			attribute2dbField.put("SEX","SEX");
			attribute2dbField.put("HOME_ADRS","HOME_ADRS");
			attribute2dbField.put("HIHOKENJYASYO_KIGOU","HIHOKENJYASYO_KIGOU");
			attribute2dbField.put("HIHOKENJYASYO_NO","HIHOKENJYASYO_NO");
			attribute2dbField.put("UKETUKE_ID","UKETUKE_ID");
			attribute2dbField.put("UPDATE_TIMESTAMP","UPDATE_TIMESTAMP");

			GridParams gridParams = new GridParams(action, startIndex, filteredColumns, currentSortedColumns, currentSortedVersusColumns, otherGridParams);

			try {
				JApplication.kikanDatabase.getMConnection().setAutoCommit(false);
			} catch (SQLException ex) {
				logger.warn(ex.getMessage());
			}
			
			//検索・ソート条件の設定用共通クラス
			FilterSetting filterSetting = new FilterSetting();
			
			//「を含む」検索は「like %入力値%」で検索するため、入力値に"%"を付加する
			filterSetting.setLikeColumns(filteredColumns, true);

			//名寄せテーブル読み込み
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT TN.NAYOSE_NO NAYOSE_NO, TK.JYUSHIN_SEIRI_NO JYUSHIN_SEIRI_NO, TK.NAME NAME, TK.KANANAME KANANAME,");
			sb.append(" TK.BIRTHDAY BIRTHDAY, TK.HOME_ADRS HOME_ADRS,TK.HIHOKENJYASYO_KIGOU HIHOKENJYASYO_KIGOU,");
			sb.append(" TK.HIHOKENJYASYO_NO HIHOKENJYASYO_NO, TN.UPDATE_TIMESTAMP UPDATE_TIMESTAMP, TN.UKETUKE_ID UKETUKE_ID, ");
			sb.append(" TK.SEX as SEX");
			sb.append(" FROM T_NAYOSE TN ");
			sb.append(" LEFT JOIN T_KOJIN TK ON TN.UKETUKE_ID = TK.UKETUKE_ID ");
	    	if (firstViewFlg) {
	    		sb.append(" ORDER BY NAYOSE_NO,UPDATE_TIMESTAMP ");
	    	}
	    	
	    	//データの取得実行
	    	conn = grid.getConnection();
			Response result = QueryUtil.getQuery(
											conn,
											new UserSessionParameters(),
											sb.toString(),
											new ArrayList<String>(),
											attribute2dbField,
											JKeinenMasterMaintenanceListData.class,
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
						JErrorMessage.show("M9804", getGridControl());	// edit n.ohkubo 2015/03/01　追加　メッセージの変更
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
	   * Method invoked when the user has clicked on save button and the grid is in EDIT mode.
	   * @param rowNumbers row indexes related to the changed rows
	   * @param oldPersistentObjects old value objects, previous the changes
	   * @param persistentObjects value objects relatied to the changed rows
	   * @return an ErrorResponse value object in case of errors, VOListResponse if the operation is successfully completed
	   */
	  @Override
	public Response updateRecords(int[] rowNumbers,ArrayList oldPersistentObjects,ArrayList persistentObjects) throws Exception {
	    PreparedStatement stmt = null;

	    // add s.inoue 2012/05/08
	    RETURN_VALUE retValue = JErrorMessage.show("M9802", getGridControl());
		if (retValue == RETURN_VALUE.NO)
			return new ErrorResponse(JErrorMessage.getMessageValue("M9803"));

	    try {

	    	StringBuilder sb = new StringBuilder();
	    	sb = new StringBuilder();
	    	sb.append("UPDATE T_NAYOSE SET NAYOSE_NO = ? ");
	    	sb.append("WHERE UKETUKE_ID =  ? ");

	    	stmt = conn.prepareStatement(sb.toString());

	      JKeinenMasterMaintenanceListData vo = null;
	      for(int i=0;i<persistentObjects.size();i++) {
	        vo = (JKeinenMasterMaintenanceListData)persistentObjects.get(i);
	        if (vo == null)
	        	break;
	        // 項目順
	        stmt.setString(1,vo.getNAYOSE_NO());
	        stmt.setString(2,vo.getUKETUKE_ID());

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
			stmt = conn.prepareStatement("delete from T_NAYOSE WHERE UKETUKE_ID=?");
			for (int i = 0; i < persistentObjects.size(); i++) {
				JKeinenMasterMaintenanceListData vo = (JKeinenMasterMaintenanceListData)persistentObjects.get(i);
				stmt.setString(1, vo.getUKETUKE_ID());
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
}
