package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.system;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.csv.JCSVReaderStream;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.filter.FilterSetting;

import org.apache.log4j.Logger;
import org.openswing.swing.client.GridControl.ColumnContainer;
import org.openswing.swing.export.java.ExportOptions;
import org.openswing.swing.message.receive.java.ErrorResponse;
import org.openswing.swing.message.receive.java.Response;
import org.openswing.swing.message.receive.java.VOListResponse;
import org.openswing.swing.message.send.java.FilterWhereClause;
import org.openswing.swing.table.client.GridController;
import org.openswing.swing.table.java.GridDataLocator;

/**
 * Ctl画面
 * @author s.inoue
 * @version 2.0
 */
public class JKikanLogListFrameCtl
		extends GridController
		implements GridDataLocator {

	private static final long serialVersionUID = -2988778262514672086L;	// edit n.ohkubo 2014/10/01　追加
	
	private JKikanLogListFrame grid = null;
//	private JKikanLogListFrame frame;	// edit n.ohkubo 2014/10/01　未使用なので削除
//	private Connection conn = null;	// edit n.ohkubo 2014/10/01　未使用なので削除
	private static jp.or.med.orca.jma_tokutei.common.csv.JCSVReaderStream reader = null;
//	private static jp.or.med.orca.jma_tokutei.common.csv.JCSVWriterStream writer = null;	// edit n.ohkubo 2014/10/01　未使用なので削除

//	private static Vector<Vector<String>> CSVItems = null;	// edit n.ohkubo 2014/10/01　未使用なので削除

	private static Logger logger = Logger.getLogger(JKikanLogListFrameCtl.class);
	
	private boolean firstViewFlg = true;	// edit n.ohkubo 2014/10/01　追加

	public JKikanLogListFrameCtl() {
		// this.conn = conn;
		grid = new JKikanLogListFrame(this);
	}

	public JKikanLogListFrame getGridControl(){
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
	public Response loadData(int action, int startIndex, Map filteredColumns,
			ArrayList currentSortedColumns,
			ArrayList currentSortedVersusColumns, Class valueObjectType,
			Map otherGridParams) {

		try {
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
//							JKikanLogListFrameData.class,
//							"Y", "N",
//							null, gridParams, JApplication.gridViewMasterCount, true);


			// add s.inoue 2013/11/08
			// CSV読込処理
			reader = new JCSVReaderStream();
			// edit s.inoue 2014/05/20
			// String filePath = "C:\\NITTOKU\\Log\\JKenshinSoftware.log";
			String filePath = JPath.CURRENT_DIR_PATH + File.separator+"Log"+ File.separator+ "JKenshinSoftware.log";

//			try {
				reader.openCSV(filePath,JApplication.CSV_CHARSET,' ');
//			} catch (IOException e) {
//				JErrorMessage.show("M9913",this);
//				logger.error(e.getMessage());
//			}

				// edit n.ohkubo 2014/10/01　start　絞込みとソート処理が行われていないので、コメントアウトして新しく記載 
//			CSVItems = reader.readAllTable();

//			ArrayList vos = new ArrayList();
//
//			// 検査結果データ取込処理
//			for (int i = 1; i < CSVItems.size(); i++) {
//
//				Vector<String> insertRow = new Vector<String>();
//
//				insertRow =CSVItems.get(i);
//
//				JKikanLogListFrameData vo = new JKikanLogListFrameData();
//				System.out.println(insertRow.size());
//				if (insertRow.size()!=7)continue;
//				
//				// vo.setLOG_DATE(insertRow.get(0));
//				vo.setUPDATE_TIMESTAMP(insertRow.get(0) +" "+ insertRow.get(1));
//				vo.setLOG_MESSAGE_LEVEL(insertRow.get(3));
//				vo.setLOG_ERROR_PLACE(insertRow.get(4));
//
//				String var6 ="";
//				if (insertRow.get(6).indexOf("?s")>0)
//					var6= insertRow.get(6).substring(insertRow.get(6).indexOf("?s")+2);
//				if (insertRow.get(6).indexOf("jp.or.med.orca.jma_tokutei")>0)continue;
//				
//				vo.setLOG_MESSAGE(var6);
//				vos.add(vo);
//
//			}
//
//	        return new VOListResponse(vos,false,vos.size());
				
			//戻り値用リスト
			List<JKikanLogListFrameData> vos = new ArrayList<JKikanLogListFrameData>();

			//フィルターが設定されているかの判定（ループの外で取得）
			boolean isFilterSetting = (filteredColumns.size() != 0) ? true : false;
			
			//ログファイルを読み込む（分割は不要なので適当な文字を指定
			reader.openCSV(filePath, JApplication.CSV_CHARSET, '\n');
			
			//ログファイルの全行を取得
			Vector<Vector<String>> vecLines = reader.readAllTable();
			
			//ログファイルの内容を精査
			for (int i = 0; i < vecLines.size(); i++) {
				
				//1行分の表示データ格納用
				JKikanLogListFrameData bean = new JKikanLogListFrameData();
				
				//ログファイルの1行格納用
				String line = vecLines.get(i).get(0);

				//30文字未満は通常のログではないのでスキップ
				if (line.length() < 30) {
					continue;
				}
				
				//" - "が30文字より後に無い場合も通常のログではないのでスキップ
				int index = line.indexOf(" - ");
				if (index < 30) {
					continue;
				}
				
				//更新日時
				String timeStamp = line.substring(0, 23);
				bean.setUPDATE_TIMESTAMP(timeStamp);
				bean.setUPDATE_TIMESTAMP2(timeStamp);
				
				//メッセージ種別
				String logLevelCode = line.substring(23, 30).trim();
				bean.setLOG_MESSAGE_LEVEL(logLevelCode);
				
				//画面
				String placeCode = line.substring(30, line.indexOf(" - "));
				bean.setLOG_ERROR_PLACE(placeCode);
				
				//内容
				String msg = line.substring((line.indexOf("?s") + 2), line.length());
				bean.setLOG_MESSAGE(msg);
				
				//絞込みの判定
				if (isFilterSetting && !isFilter(filteredColumns, bean)) {
					continue;
				}
				vos.add(bean);
			}
			
			//ソート
			int currentSortedColumnsCount = currentSortedColumns.size();
			int currentSortedVersusColumnsCount = currentSortedVersusColumns.size();
			if ((currentSortedColumnsCount > 0) && (currentSortedColumnsCount == currentSortedVersusColumnsCount)) {
				Collections.sort(vos, new LogListSort(currentSortedColumns, currentSortedVersusColumns));
			}
			
			//検索・ソート条件の設定用共通クラス
			FilterSetting filterSetting = new FilterSetting();
			
			//検索画面へソート条件を反映する（検索後、検索画面を閉じて再度開いたときに、値が反映されていない現象の対応）
			ColumnContainer columnContainer = grid.getColumnContainer();
			filterSetting.setSortColumnsAfter(columnContainer, currentSortedColumns, currentSortedVersusColumns);
			
			Response result = new VOListResponse(vos, false, vos.size());
			
			//0件の場合、メッセージの表示（画面オープン初回以外）
			if (!firstViewFlg) {
				if ((result != null) && (!result.isError() && (result instanceof VOListResponse))){
					if (((VOListResponse)result).getRows().size() == 0) {
//						JErrorMessage.show("M3550", getGridControl());	// edit n.ohkubo 2015/03/01　削除
						JErrorMessage.show("M7600", getGridControl());	// edit n.ohkubo 2015/03/01　追加　メッセージの変更
					}
				}
			}

			firstViewFlg = false;			

			return result;
			// edit n.ohkubo 2014/10/01　追加　end
			
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ErrorResponse(ex.getMessage());
		}
	}


	// edit n.ohkubo 2014/10/01　追加　start
	/**
	 * 絞込み判定
	 * 
	 * @param filteredColumns
	 * @param bean
	 * @return	絞込み条件に一致（表示対象）の場合：true、絞込み条件に不一致（表示対象外）の場合：false
	 */
	private boolean isFilter(Map<String, FilterWhereClause[]> filteredColumns, JKikanLogListFrameData bean) {
		
		//戻り値
		boolean isResult = true;
		
		//指定されているのは何か調べる
		Set<Entry<String, FilterWhereClause[]>> set = filteredColumns.entrySet();
		Iterator<Entry<String, FilterWhereClause[]>> iterator = set.iterator();
		
		//指定されている全件を調査
		while(iterator.hasNext()) {
			Entry<String, FilterWhereClause[]> entry = iterator.next();
			FilterWhereClause[] values = entry.getValue();
			
			//指定されている項目
			String key = entry.getKey();
			
			//指定された絞込み条件
			String operator = values[0].getOperator();
			
			//入力された値
			String inputValue;
			if (values[0].getValue() != null) {
				inputValue = values[0].getValue().toString();
			} else {
				inputValue = "";
			}
			
			String getValue = "";
			
			//画面
			if ("LOG_ERROR_PLACE".equals(key)) {
//					System.out.println("LOG_ERROR_PLACE Operator:[" + values[0].getOperator() + "] value:[" + values[0].getValue() + "]");
				
				getValue = bean.getLOG_ERROR_PLACE();
				
			//メッセージ種別
			} else if ("LOG_MESSAGE_LEVEL".equals(key)) {
//					System.out.println("LOG_MESSAGE_LEVEL Operator:[" + values[0].getOperator() + "] value:[" + values[0].getValue() + "]");
				
				getValue = bean.getLOG_MESSAGE_LEVEL();
				
			//内容
			} else if ("LOG_MESSAGE".equals(key)) {
//					System.out.println("LOG_MESSAGE Operator:[" + values[0].getOperator() + "] value:[" + values[0].getValue() + "]");
				
				getValue = bean.getLOG_MESSAGE();
				
			//更新日時
			} else if ("UPDATE_TIMESTAMP".equals(key)) {
//				System.out.println("UPDATE_TIMESTAMP Operator:[" + values[0].getOperator() + "] value:[" + values[0].getValue() + "]");
			
				getValue = bean.getUPDATE_TIMESTAMP();

				//範囲指定で検索したときの対応
				if (inputValue.length() > 0) {
					inputValue = convertTimeStamp(inputValue, operator);
				}
				
			//更新日時2
			} else if ("UPDATE_TIMESTAMP2".equals(key)) {
//				System.out.println("UPDATE_TIMESTAMP2 Operator:[" + values[0].getOperator() + "] value:[" + values[0].getValue() + "]");
			
				getValue = bean.getUPDATE_TIMESTAMP2();
				
				//範囲指定で検索したときの対応
				if (inputValue.length() > 0) {
					inputValue = convertTimeStamp(inputValue, operator);
				}
			
			} else {
//				throw new RuntimeException("想定外の項目:[" + key + "]");
				logger.warn("想定外の項目:[" + key + "]");
				continue;
			}

			boolean isOK = isTarget(operator, inputValue, getValue);
			if (!isOK) {
				isResult = false;
				break;
			}
		}
			
		return isResult;
	}
	
	/**
	 * 更新日時のミニマムとマックスを設定する（範囲指定で検索した場合を想定）
	 * 例）
	 * 入力値が「2014」、条件が「以上」の場合、「2014-01-01 00:00:00,000」になる
	 * 入力値が「2013-02」、条件が「未満」の場合、「2013-02-31 23:59:59,999」になる
	 * （31日まで無い月もあるが、文字列比較なので問題無し）
	 * 
	 * @param inputValue	入力値
	 * @param operator		条件
	 * @return
	 */
	private String convertTimeStamp(String inputValue, String operator) {
		
		String result;
		final String over = "0000-01-01 00:00:00,000";
		final String under = "9999-12-31 23:59:59,999";
		
		//以上 or 超過の場合
		if (">=".equals(operator) || ">".equals(operator)) {
			
			result = inputValue + over.substring(inputValue.length(), over.length());
			
		//以下 or 未満
		} else if ("<=".equals(operator) || "<".equals(operator)) {
			
			result = inputValue + under.substring(inputValue.length(), under.length());
			
		} else {
			result = inputValue;
		}
		
		return result;
	}
	
	/**
	 * フィルター条件にマッチするかの判定
	 * 
	 * @param operator
	 * @param inputValue
	 * @param getValue
	 * 
	 * @return フィルター条件に一致（表示対象）の場合：true、フィルター条件に不一致（表示対象外）の場合：false
	 */
	private boolean isTarget(String operator, String inputValue, String getValue) {
		
		if (operator == null || inputValue == null || getValue == null) {
			return false;
		}

		//戻り値
		boolean isResult = false;
		
		//等しい
		if ("=".equals(operator)) {
			isResult = inputValue.equals(getValue) ? true : false;
			
		//等しくない
		} else if ("<>".equals(operator)) {
			isResult = inputValue.equals(getValue) ? false : true;
			
		//を含む
		} else if ("like".equals(operator)) {
			isResult = (getValue.indexOf(inputValue) < 0) ? false : true;
			
		//空でない
		} else if ("is not null".equals(operator)) {
//			isResult = (("".equals(getValue.trim()) ? false : true) || (((Domain)JApplication.domains.get("LOG_PLACE")).getDomainPair(getValue) != null));
			isResult = "".equals(getValue.trim()) ? false : true;
			
		//空の
		} else if (("is null".equals(operator))){
//			isResult = (("".equals(getValue.trim()) ? true : false) || (((Domain)JApplication.domains.get("LOG_PLACE")).getDomainPair(getValue) == null));
			isResult = "".equals(getValue.trim()) ? true : false;
			
		//以上
		} else if (">=".equals(operator)) {
			isResult = (getValue.compareTo(inputValue) >= 0) ? true : false;
			
		//超過
		} else if (">".equals(operator)) {
			isResult = (getValue.compareTo(inputValue) > 0) ? true : false;
			
		//以下
		} else if ("<=".equals(operator)) {
			isResult = (getValue.compareTo(inputValue) <= 0) ? true : false;
			
		//未満
		} else if ("<".equals(operator)) {
			isResult = (getValue.compareTo(inputValue) < 0) ? true : false;
		}
		
		return isResult;
	}
	// edit n.ohkubo 2014/10/01　追加　end

//	  /**
//	   * Method invoked when the user has clicked on save button and the grid is in INSERT mode.
//	   * @param rowNumbers row indexes related to the new rows to save
//	   * @param newValueObjects list of new value objects to save
//	   * @return an ErrorResponse value object in case of errors, VOListResponse if the operation is successfully completed
//	   */
//	  public Response insertRecords(int[] rowNumbers, ArrayList newValueObjects) throws Exception {
//
//		PreparedStatement stmt = null;
//
//		System.out.println("row" + rowNumbers[0] + "value" + newValueObjects);
//
//		if (rowNumbers[0] <= 0)
//			return new VOListResponse(newValueObjects,false,newValueObjects.size());
//
//		try {
//	    	  StringBuffer sb = new StringBuffer();
//			  sb.append("INSERT INTO T_SYOKENMASTER (SYOKEN_TYPE,SYOKEN_TYPE_NAME, SYOKEN_NO, SYOKEN_NAME, UPDATE_TIMESTAMP)");
//			  sb.append("VALUES (?,?,?,?,?)");
//
//			  stmt = conn.prepareStatement(sb.toString());
//
//		      // 行毎追加処理
//		      JKikanLogListFrameData vo = null;
//
//		      for(int i=0;i<newValueObjects.size();i++) {
//		        vo = (JKikanLogListFrameData)newValueObjects.get(i);
//		        stmt.setString(1,vo.getSYOKEN_TYPE());
//		        stmt.setString(2,vo.getSYOKEN_TYPE_NAME());
//		        stmt.setString(3,vo.getSYOKEN_NO());
//		        stmt.setString(4,vo.getSYOKEN_NAME());
//		        stmt.setString(5,vo.getUPDATE_TIMESTAMP());
//		        stmt.execute();
//		      }
//        return new VOListResponse(newValueObjects,false,newValueObjects.size());
//		}catch (SQLException ex) {
//			logger.error(ex.getMessage());
//	      return new ErrorResponse(JErrorMessage.getMessageValue("M9912"));
//	    }
//	    finally {
//	      try {
//	        stmt.close();
//	        conn.commit();
//	      }
//	      catch (SQLException ex1) {
//	      }
//	    }
//	  }
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

//	/**
//	 * 削除ボタンイベント ※gridがREADONLY
//	 * @param persistentObjects value objects to delete (related to the currently selected rows)
//	 * @return an ErrorResponse value object in case of errors, VOResponse if the operation is successfully completed
//	 */
//	public Response deleteRecords(ArrayList persistentObjects) throws Exception {
//		PreparedStatement stmt = null;
//
//		try {
//
//			StringBuilder sb = new StringBuilder();
//			sb.append(" delete from T_SYOKENMASTER");
//			sb.append(" WHERE SYOKEN_TYPE = ?");
//			sb.append(" AND SYOKEN_NO = ?");
//			stmt = conn.prepareStatement(sb.toString());
//
//			for (int i = 0; i < persistentObjects.size(); i++) {
//				JKikanLogListFrameData vo = (JKikanLogListFrameData)persistentObjects.get(i);
//				stmt.setString(1, vo.getSYOKEN_TYPE());
//				stmt.setString(2, vo.getSYOKEN_NO());
//				stmt.execute();
//			}
//			return new VOResponse(new Boolean(true));
//		} catch (SQLException ex) {
//			ex.printStackTrace();
//			return new ErrorResponse(ex.getMessage());
//		} finally {
//			try {
//				stmt.close();
//				conn.commit();
//			} catch (SQLException ex1) {
//			}
//		}
//	}

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

	
	// edit n.ohkubo 2014/10/01　追加 start　ソート用クラス
	private class LogListSort implements Comparator<JKikanLogListFrameData> {
		
		List<String> sortedColumnsList;
		List<String> sortedVersusList;
		
		public LogListSort(List<String> sortedColumnsList, List<String> sortedVersusList) {
			this.sortedColumnsList = sortedColumnsList;
			this.sortedVersusList = sortedVersusList;
		}

		@Override
		public int compare(JKikanLogListFrameData o1, JKikanLogListFrameData o2) {
			
			//戻り値
			int result = 0;
			
			//比較対象の値
			String o1Value = "";
			String o2Value = "";
			
			//指定されたソートの個数
			int sortedSize = sortedColumnsList.size();
			
			for (int i = 0; i < sortedSize; i++) {
				
				//ソート対象のカラム
				String sortedColumn = sortedColumnsList.get(i);
				
				//ソートの条件
				String sortedVersus = sortedVersusList.get(i);

				/*指定されたソート項目の値を取得*/
				//画面
				if ("LOG_ERROR_PLACE".equals(sortedColumn)) {
//					System.out.println("LOG_ERROR_PLACE Operator:[" + values[0].getOperator() + "] value:[" + values[0].getValue() + "]");
					
					o1Value = o1.getLOG_ERROR_PLACE();
					o2Value = o2.getLOG_ERROR_PLACE();
					
				//メッセージ種別
				} else if ("LOG_MESSAGE_LEVEL".equals(sortedColumn)) {
//					System.out.println("LOG_MESSAGE_LEVEL Operator:[" + values[0].getOperator() + "] value:[" + values[0].getValue() + "]");
					
					o1Value = o1.getLOG_MESSAGE_LEVEL();
					o2Value = o2.getLOG_MESSAGE_LEVEL();
					
				//内容
				} else if ("LOG_MESSAGE".equals(sortedColumn)) {
//					System.out.println("LOG_MESSAGE Operator:[" + values[0].getOperator() + "] value:[" + values[0].getValue() + "]");
					
					o1Value = o1.getLOG_MESSAGE();
					o2Value = o2.getLOG_MESSAGE();
					
				//更新日時
				} else if ("UPDATE_TIMESTAMP".equals(sortedColumn)) {
					
					o1Value = o1.getUPDATE_TIMESTAMP();
					o2Value = o2.getUPDATE_TIMESTAMP();
					
				} else {
//					throw new RuntimeException("想定外のソート項目:[" + sortedColumn + "]");
				}
				
				/*指定されたソートの条件で結果を取得*/
				//昇順
				if ("ASC".equals(sortedVersus)) {
					result = o1Value.compareTo(o2Value);
					
				//降順
				} else if ("DESC".equals(sortedVersus)) {
					result = (o1Value.compareTo(o2Value) * -1);
					
				} else {
//					throw new RuntimeException("想定外のソートの条件:[" + sortedVersus + "]");
				}
				
				//比較結果が同じ　かつ　次のソート項目がある場合、次の項目でソート
				if ((result == 0) && (i <= sortedSize)){
					continue;
					
				//ソート結果がでたので、ループを抜ける
				} else {
					break;
				}
			}
			return result;
		}
	}
	// edit n.ohkubo 2014/10/01　追加　end
}
