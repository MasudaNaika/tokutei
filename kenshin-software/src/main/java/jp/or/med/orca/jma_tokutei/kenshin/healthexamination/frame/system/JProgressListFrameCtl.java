package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.system;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;

import org.apache.log4j.Logger;
import org.openswing.swing.export.java.ExportOptions;
import org.openswing.swing.message.receive.java.ErrorResponse;
import org.openswing.swing.message.receive.java.Response;
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
public class JProgressListFrameCtl
		extends GridController
		implements GridDataLocator {

	private JProgressListFrame grid = null;
	private JProgressListFrame frame;
	private Connection conn = null;
	private static jp.or.med.orca.jma_tokutei.common.csv.JCSVReaderStream reader = null;
	private static jp.or.med.orca.jma_tokutei.common.csv.JCSVWriterStream writer = null;

	private static Vector<Vector<String>> CSVItems = null;

	private static Logger logger = Logger.getLogger(JProgressListFrameCtl.class);

	public JProgressListFrameCtl(Connection conn) {
		this.conn = conn;
		grid = new JProgressListFrame(this);
	}

	public JProgressListFrame getGridControl(){
		return grid;
	}

//	/**
//	* @param attributeName attribute name that identify a grid column
//	* @return tooltip text to show in the column header; this text will be automatically translated according to internationalization settings
//	*/
//	public String getHeaderTooltip(String attributeName) {
//	    return attributeName;
//	}

//    /**
//	* @param row row index in the grid
//	* @param attributeName attribute name that identify a grid column
//	* @return tooltip text to show in the cell identified by the specified row and attribute name; this text will be automatically translated according to internationalization settings
//	*/
//	public String getCellTooltip(int row,String attributeName) {
//
//		return (String) grid.getGrid().getVOListTableModel().getField(row,attributeName);
////		return attributeName+" at row "+row;
//	}

//	/**
//	 * INSERT modeの時、保存前に呼ばれる Callback関数(save buttonが押された時).
//	 *
//	 * @return <code>true</code> 保存許可, <code>false</code> 保存中断
//	 */
//	public boolean beforeInsertGrid(GridControl grid) {
//		// new JShokenMasterMaintenanceDetailCtl(this.grid,null, null, conn);
//		return false;
//	}

//	/**
//	 * gridがdouble clickedされた時、Callback関数
//	 * @param rowNumber selected row index
//	 * @param persistentObject v.o. related to the selected row
//	 */
//	public void doubleClick(int rowNumber, ValueObject persistentObject) {
//		JKikanLogListFrameData vo = (JKikanLogListFrameData)persistentObject;
//
//		// openswing s.inoue 2011/02/09
//		// new JShokenMasterMaintenanceDetailCtl(grid, vo.getSYOKEN_TYPE(),vo.getSYOKEN_NO(), conn);
////		JScene.ChangeScene(grid,new JShokenMasterMaintenanceDetailCtl(
////				vo.getSYOKEN_NO()),new WindowRefreshEvent());
//		JScene.ChangeScene(grid,new JShokenMasterMaintenanceEditFrameCtrl(
//				vo.getSYOKEN_TYPE(), vo.getSYOKEN_NO()),new WindowRefreshEvent());
//	}

    /**
     * Callbackメソッド
     * exportダイアログを表示する前に呼び出す
     * gridに定義されたformatを再定義して呼び出せます
     * @return list of available formats; possible values: ExportOptions.XLS_FORMAT, ExportOptions.CSV_FORMAT1, ExportOptions.CSV_FORMAT2, ExportOptions.XML_FORMAT, ExportOptions.XML_FORMAT_FAT, ExportOptions.HTML_FORMAT, ExportOptions.PDF_FORMAT, ExportOptions.RTF_FORMAT; default value: ClientSettings.EXPORTING_FORMATS
     */
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
			attribute2dbField.put("HKNJANUM","HKNJANUM");
			attribute2dbField.put("BIRTHDAY","BIRTHDAY");
			attribute2dbField.put("JYUSHIN_SEIRI_NO","JYUSHIN_SEIRI_NO");
			attribute2dbField.put("SEX","SEX");
			attribute2dbField.put("HIHOKENJYASYO_KIGOU","HIHOKENJYASYO_KIGOU");
			attribute2dbField.put("HIHOKENJYASYO_NO","HIHOKENJYASYO_NO");
			attribute2dbField.put("KENSA_NENGAPI","KENSA_NENGAPI");
			
			attribute2dbField.put("KANANAME","KANANAME");
			attribute2dbField.put("KEKA_FLG","KEKA_FLG");
			attribute2dbField.put("HANTEI_FLG","HANTEI_FLG");
			attribute2dbField.put("NITIJI_FLG","NITIJI_FLG");
			attribute2dbField.put("GETUJI_FLG","GETUJI_FLG");

			GridParams gridParams = new GridParams(action, startIndex,
					filteredColumns, currentSortedColumns,
					currentSortedVersusColumns, otherGridParams);

			// add s.inoue 2013/02/25
			try {
				JApplication.kikanDatabase.getMConnection().setAutoCommit(false);
			} catch (SQLException ex) {
				logger.warn(ex.getMessage());
			}

			StringBuilder sb = new StringBuilder();

			sb.append("SELECT TK.HKNJANUM HKNJANUM,TK.BIRTHDAY BIRTHDAY,TK.JYUSHIN_SEIRI_NO JYUSHIN_SEIRI_NO,TK.SEX SEX,");
			sb.append("TK.HIHOKENJYASYO_KIGOU HIHOKENJYASYO_KIGOU,TK.HIHOKENJYASYO_NO HIHOKENJYASYO_NO,TT.KENSA_NENGAPI KENSA_NENGAPI,TK.KANANAME KANANAME,");
			sb.append("case WHEN KEKA_INPUT_FLG is not null then '済' else '未' end KEKA_FLG,");
			sb.append("case when TT.HANTEI_NENGAPI is not null then '済' else '未' end HANTEI_FLG,");
			sb.append("case when WS.JISI_KBN is not null then '済' else '未' end NITIJI_FLG,");
			sb.append("case when TS.JISI_KBN is not null then '済' else '未' end GETUJI_FLG");
			sb.append(" FROM T_KOJIN TK");
			sb.append(" LEFT JOIN T_KENSAKEKA_TOKUTEI TT");
			sb.append(" ON TK.UKETUKE_ID = TT.UKETUKE_ID");
			sb.append(" LEFT JOIN T_KESAI_WK WS");
			sb.append(" ON TT.UKETUKE_ID = WS.UKETUKE_ID");
			sb.append(" AND TT.KENSA_NENGAPI = WS.KENSA_NENGAPI");
			sb.append(" LEFT JOIN T_KESAI TS");
			sb.append(" ON TT.UKETUKE_ID = TS.UKETUKE_ID");
			sb.append(" AND TT.KENSA_NENGAPI = TS.KENSA_NENGAPI");

			return QueryUtil
					.getQuery(
							conn,
							new UserSessionParameters(),
							sb.toString(),
							vals,
							attribute2dbField,
							JProgressListFrameData.class,
							"Y", "N",
							null, gridParams, JApplication.gridViewMasterCount, true);


		} catch (Exception ex) {
			ex.printStackTrace();
			return new ErrorResponse(ex.getMessage());
		}
	}

    public void afterDeleteGrid()
    {
    	grid.reloadButton.doClick();
    }

	/**
	 * 遷移先の画面から戻ってきた場合
	 */
	public class WindowRefreshEvent extends WindowAdapter {
		public void windowClosed(WindowEvent e) {
			grid.setEnabled(true);
			// eidt s.inoue 2011/04/08
			grid.reloadButton.doClick();
		}
	}

}
