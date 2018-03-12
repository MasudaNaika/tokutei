package jp.or.med.orca.jma_tokutei.common.filter;

import java.awt.Component;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.JCheckBox;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.util.FiscalYearUtil;

import org.openswing.swing.client.GridControl.ColumnContainer;
import org.openswing.swing.message.send.java.FilterWhereClause;
import org.openswing.swing.message.send.java.GridParams;
import org.openswing.swing.table.columns.client.Column;
import org.openswing.swing.util.java.Consts;


/**
 * 検索条件と並び順の保存設定を判定し、適切な条件を設定する共通クラス
 */
public class FilterSetting {

	/**
	 * 有効フラグ（1：有効　0：無効）
	 */
	private static final String YUKOU_FLG_YUKO = "1";
	private static final String YUKOU_FLG_MUKO = "0";
	
	/**
	 * 画面CD
	 */
	private String screenCode;
	
	/**
	 * org.openswing.swing.client.GridControl.ColumnContainer
	 */
	private ColumnContainer columnContainer;
	
	
	/**
	 * コンストラクタ（DB使用時）
	 */
	public FilterSetting(String screenCode, ColumnContainer columnContainer) {
		this.screenCode = screenCode;
		this.columnContainer = columnContainer;
	}
	
	/**
	 * コンストラクタ（DB未使用時）
	 */
	public FilterSetting() {
	}
	

	/**
	 * 検索・ソート条件を設定する
	 * 
	 * @param firstViewFlg
	 * @param jCheckBox
	 * @param gridParams
	 * @param filteredColumns
	 * @param currentSortedColumns
	 * @param currentSortedVersusColumns
	 * @param firstFilter	検索条件の初期値（[0]にAttributeName、[1]にOperatorを指定し、検索条件が無い場合は「null」を指定する）
	 * @throws SQLException
	 */
	public void setFilterSort(
			boolean firstViewFlg, 
			JCheckBox jCheckBox, 
			GridParams gridParams, 
			Map<String, FilterWhereClause[]> filteredColumns, 
			List<String> currentSortedColumns, 
			ArrayList<String> currentSortedVersusColumns,
			String[] firstFilter) throws SQLException {

		//検索・ソート条件を保持するかの判定用
		boolean isColumnsSave;
		
		//画面オープン初回
		if (firstViewFlg) {
			
			//マスタから条件保持を判定
			isColumnsSave = isColumnsSaveMaster();
			
			//チェックボックスへ設定
			jCheckBox.setSelected(isColumnsSave);
			
			//マスタの値が「条件を保持しない」 （orマスタに未登録）の場合
			if (!isColumnsSave) {
				
				//検索条件の初期値を設定
				if (firstFilter != null) {
					
					//「年度」かつ「=」の場合
					if (firstFilter.length == 2 && "NENDO".equals(firstFilter[0]) && "=".equals(firstFilter[1])) {
						String cYear = String.valueOf(FiscalYearUtil.getThisYear());
						FilterWhereClause clauseDesign = new FilterWhereClause();
						clauseDesign.setAttributeName(firstFilter[0]);
						clauseDesign.setOperator(firstFilter[1]);
						clauseDesign.setValue(cYear);
						gridParams.getFilteredColumns().put(clauseDesign.getAttributeName(), new FilterWhereClause[]{clauseDesign, null});
					}
				}
			}
			
		//初回以降（画面オープン後の検索時などの自画面遷移時）
		} else {
			
			//条件保存チェックボックスの設定
			isColumnsSave = jCheckBox.isSelected();
			registerFilterMaster(isColumnsSave);
			
			//条件を保持する場合
			if (isColumnsSave) {
				
				//ソート関係の初期化処理
				sortInit(gridParams);
				
				//検索条件を保存
				saveFilter(filteredColumns);
				
				//ソート条件を保存
				saveSort(currentSortedColumns, currentSortedVersusColumns);
			}
			//コミット
			JApplication.kikanDatabase.getMConnection().commit();
		}
		
		//条件を保持する場合
		if (isColumnsSave) {
			
			//検索条件の設定
			setFilteredColumns(gridParams);
			
			//ソート条件の設定
			setSortColumns(gridParams, currentSortedVersusColumns, currentSortedVersusColumns);
		}
		
		//「を含む」検索は「like %入力値%」になるので、入力値に"%"を付加する
		setLikeColumns(filteredColumns, true);
	}


	/**
	 * 検索・ソート条件を保存するかの判定
	 * マスタからフラグを取得し、設定が"有効"かを判定する
	 * 
	 * @return	保存する場合：true、保存しない場合：false
	 * @throws SQLException 
	 */
	private boolean isColumnsSaveMaster() throws SQLException {
		
		boolean isResult;
		
		//検索・ソート条件マスタの値を取得し、条件の保持を確認する
		String sql = "SELECT YUKOU_FLG FROM T_SCREEN_FILTER_MASTER WHERE SCREEN_CD = ? AND USER_NAME = ?";
		String[] params = {screenCode, JApplication.userID};
		List<Hashtable<String, String>> result = JApplication.kikanDatabase.sendExecuteQuery(sql, params);
		
		//マスタに登録有り
		if (result.size() == 1) {
			
			//取得結果の判定
			Hashtable<String, String> hashtable = result.get(0);
			String yukouFlg = hashtable.get("YUKOU_FLG");
			isResult = YUKOU_FLG_YUKO.equals(yukouFlg) ? true : false;
			
		//マスタに登録無し
		} else {
			isResult = false;
		}

		return isResult;
	}
	
	/**
	 * 「T_SCREEN_FILTER_MASTER」テーブルへ、登録する
	 * 
	 * @param yukou_flg
	 * @throws SQLException
	 */
	private void registerFilterMaster(boolean isYuko) throws SQLException {
		
		//最初に削除
		JApplication.kikanDatabase.sendNoResultQuery(
				"DELETE FROM T_SCREEN_FILTER_MASTER WHERE SCREEN_CD = ? AND USER_NAME = ?", 
				new String[] {screenCode, JApplication.userID}
		);
		
		//次に登録
		String insSql = "INSERT INTO T_SCREEN_FILTER_MASTER VALUES (?, ?, ?)";
		//有効フラグを変換
		String yukou_flg = (isYuko) ? YUKOU_FLG_YUKO : YUKOU_FLG_MUKO;
		String[] params = {screenCode, JApplication.userID, yukou_flg};
		JApplication.kikanDatabase.sendNoResultQuery(insSql, params);
	}
	
	/**
	 * ソート関係の初期化を行う
	 * ※ 色々と初期化しないと、ソートの表示がおかしくなる
	 * 
	 * @param gridParams
	 */
	private void sortInit(GridParams gridParams) {
		
		//項目に表示するソート順位を初期化（検索後の順位がおかしくなる対応）
		gridParams.setCurrentSortedColumns(new ArrayList<String>());
		gridParams.setCurrentSortedVersusColumns(new ArrayList<String>());
		
		//ソートの有無を初期化（検索後、検索画面を閉じて再度開いたときに、値が反映されていない現象の対応）
		Component[] componentArgs = columnContainer.getComponents();
		if (componentArgs != null) {
			for (int i = 0; i < componentArgs.length; i++) {
				
				//項目がソート対象かの判定用
				boolean isColumnSortable;
				
				Component component = componentArgs[i];
				if (component instanceof Column) {
					
					//キャストが面倒なので、クラスへ代入
					Column column = (Column)component;
					
					//項目がソート対象かチェック
					isColumnSortable = column.isColumnSortable();
					if (isColumnSortable) {
						
						//"ソートしない"で初期化（ソートする場合、この後にDBからソート条件を取得して設定する）
						column.setSortVersus(Consts.NO_SORTED);
						column.setSortingOrder(0);
					}
				}
			}
		}
	}
	/**
	 * 検索条件の登録を行う
	 * ※一度削除してから登録する
	 * 
	 * @param filteredColumns
	 * @throws SQLException 
	 */
	private void saveFilter(Map<String, FilterWhereClause[]> filteredColumns) throws SQLException {
		
		//削除してから登録する
		JApplication.kikanDatabase.sendNoResultQuery(
				"DELETE FROM T_SCREEN_FILTER_COLUMNS WHERE SCREEN_CD = ? AND USER_NAME = ?", 
				new String[] {screenCode, JApplication.userID}
		);
		
		//登録用SQL
		final String sql = "INSERT INTO T_SCREEN_FILTER_COLUMNS VALUES (?, ?, ?, ?, ?)";
		
		//画面で指定された検索条件を取得
		Set<Entry<String, FilterWhereClause[]>> set = filteredColumns.entrySet();
		Iterator<Entry<String, FilterWhereClause[]>> iterator = set.iterator();
		
		//指定されている全件を登録
		while(iterator.hasNext()) {
			Entry<String, FilterWhereClause[]> entry = iterator.next();
			
			//指定されている項目
			String key = entry.getKey();
			//オペレーションと値
			FilterWhereClause[] values = entry.getValue();

			for (int i = 0; i < values.length; i++) {
				
				//通常は[0]に値があり、[1]はnullになる（生年月日等同じ項目が2つある場合、[1]に二個目の条件が入っているが、現状はattribute2dbFieldのキーを別にしているので、[1]に値は入らない）
				if (values[i] != null) {
					
					//指定された絞込み条件
					String operator = values[i].getOperator();
					
					//入力された値
					String inputValue;
					if (values[0].getValue() != null) {
						inputValue = values[i].getValue().toString();
					} else {
						inputValue = "";
					}
					
					//登録の実行
					String[] params = {screenCode, JApplication.userID, key, operator, inputValue};
					JApplication.kikanDatabase.sendNoResultQuery(sql, params);
					
				}
			}
		}
	}
	
	/**
	 * ソート条件の登録を行う
	 * 
	 * @param currentSortedColumns
	 * @param currentSortedVersusColumns
	 * @throws SQLException 
	 */
	private void saveSort(List<String> currentSortedColumns, List<String> currentSortedVersusColumns) throws SQLException {
		
		//削除してから登録する
		JApplication.kikanDatabase.sendNoResultQuery(
				"DELETE FROM T_SCREEN_SORT_COLUMNS WHERE SCREEN_CD = ? AND USER_NAME = ?", 
				new String[]{screenCode, JApplication.userID}
		);
		
		//登録用SQL
		final String sql = "INSERT INTO T_SCREEN_SORT_COLUMNS VALUES (?, ?, ?, ?, ?)";

		//指定されている全件を登録
		for (int i = 0; i < currentSortedColumns.size(); i++) {
			
			String[] params = {screenCode, JApplication.userID, String.valueOf(currentSortedColumns.get(i)), String.valueOf(currentSortedVersusColumns.get(i)), String.valueOf((i + 1))};
			JApplication.kikanDatabase.sendNoResultQuery(sql, params);
		}
	}
	
	/**
	 * 検索条件の取得と設定を行う
	 * 
	 * @param gridParams
	 * @throws SQLException 
	 */
	private void setFilteredColumns(GridParams gridParams) throws SQLException {
		
		//検索条件の取得
		String sql = "SELECT FILTER_COLUMN_ITEM, FILTER_COLUMN_OPERATOR, FILTER_COLUMN_VALUE FROM T_SCREEN_FILTER_COLUMNS WHERE SCREEN_CD = ? AND USER_NAME = ? ORDER BY FILTER_COLUMN_ITEM";
		String[] params = {screenCode, JApplication.userID};
		List<Hashtable<String, String>> filterResult = JApplication.kikanDatabase.sendExecuteQuery(sql, params);
		
		Map<String, FilterWhereClause[]> filterMap = new HashMap<String, FilterWhereClause[]>();
		for (int i = 0; i < filterResult.size(); i++) {
			FilterWhereClause[] filterArgs = new FilterWhereClause[2];

			filterArgs[0] = new FilterWhereClause();
			String filterColumnItem = filterResult.get(i).get("FILTER_COLUMN_ITEM");
			filterArgs[0].setOperator(filterResult.get(i).get("FILTER_COLUMN_OPERATOR"));
			filterArgs[0].setValue(filterResult.get(i).get("FILTER_COLUMN_VALUE"));
			filterArgs[0].setAttributeName(filterColumnItem);
			
			//次に指定された項目があり　かつ　その項目が今の項目と同じ場合（検索条件に同じ項目を2つ指定した場合）
			if (((i + 1) < filterResult.size()) && filterColumnItem.equals(filterResult.get(i+1).get("FILTER_COLUMN_ITEM"))) {
				filterArgs[1] = new FilterWhereClause();
				filterArgs[1].setOperator(filterResult.get(i+1).get("FILTER_COLUMN_OPERATOR"));
				filterArgs[1].setValue(filterResult.get(i+1).get("FILTER_COLUMN_VALUE"));
				filterArgs[1].setAttributeName(filterResult.get(i+1).get("FILTER_COLUMN_ITEM"));
				i++;
			}
			filterMap.put(filterColumnItem, filterArgs);
			
			//検索条件を設定
			gridParams.getFilteredColumns().put(filterColumnItem, filterArgs);
		}
	}
	
	/**
	 * ソート条件の取得と設定を行う
	 * 
	 * @param gridParams
	 * @param currentSortedColumns
	 * @param currentSortedVersusColumns
	 * @throws SQLException 
	 */
	private void setSortColumns(GridParams gridParams, ArrayList<String> currentSortedColumns, ArrayList<String> currentSortedVersusColumns) throws SQLException {
		
			//ソート条件の取得
			String sql = "SELECT SORT_COLUMN_ITEM, SORT_COLUMN_OPERATOR FROM T_SCREEN_SORT_COLUMNS WHERE SCREEN_CD = ? AND USER_NAME = ? ORDER BY SORT_COLUMN_SEQNO";
			String[] params = {screenCode, JApplication.userID};
			List<Hashtable<String, String>> sortResult = JApplication.kikanDatabase.sendExecuteQuery(sql, params);
			
			ArrayList<String> sortedColumnsList = new ArrayList<String>();
			ArrayList<String> sortedVersusList = new ArrayList<String>();
			for (int i = 0; i < sortResult.size(); i++) {
				
				String sortColumnItem = sortResult.get(i).get("SORT_COLUMN_ITEM");
				String sortColumnOperator = sortResult.get(i).get("SORT_COLUMN_OPERATOR");
				
				//ソート条件を設定
				sortedColumnsList.add(sortColumnItem);
				sortedVersusList.add(sortColumnOperator);
				gridParams.getCurrentSortedColumns().add(sortColumnItem);
				gridParams.getCurrentSortedVersusColumns().add(sortColumnOperator);
			}
			currentSortedColumns = sortedColumnsList;
			currentSortedVersusColumns = sortedVersusList;
			gridParams.setCurrentSortedColumns(currentSortedColumns);
			gridParams.setCurrentSortedVersusColumns(currentSortedVersusColumns);
			
			//検索画面へソート条件を反映する
			Component[] componentArgs = columnContainer.getComponents();
			if (componentArgs != null) {
				for (int i = 0; i < componentArgs.length; i++) {
					
					Component component = componentArgs[i];
					if (component instanceof Column) {
						
						//キャストが面倒なので、クラスへ代入
						Column column = (Column)component;
						
						//項目名の取得
						String columnName = column.getColumnName();
						
						//項目がソート条件に使用されているか判定
						int columnNameIndex = sortedColumnsList.indexOf(columnName);
						if(columnNameIndex > -1) {
							
							//項目とソート条件を設定
							column.setSortVersus(sortedVersusList.get(columnNameIndex));
							column.setSortingOrder((columnNameIndex + 1));
						}
					}
				}
			}
	}
	
	/**
	 * like検索の入力値をセットする
	 * （%の付加と削除）
	 * 
	 * @param filteredColumns
	 * @param isAdd				"%"を付加するとき：true、"%"を削除するとき：false
	 */
	public void setLikeColumns(Map<String, FilterWhereClause[]> filteredColumns, boolean isAdd) {
		
		//検索条件を取得
		Set<Entry<String, FilterWhereClause[]>> set = filteredColumns.entrySet();
		Iterator<Entry<String, FilterWhereClause[]>> iterator = set.iterator();
		
		//指定されている全件を登録
		while(iterator.hasNext()) {
			Entry<String, FilterWhereClause[]> entry = iterator.next();
			
			//オペレーションと値
			FilterWhereClause[] values = entry.getValue();

			for (int i = 0; i < values.length; i++) {
				if (values[i] != null) {
					if ("like".equals(values[i].getOperator())) {
						
						//「%」を付ける
						if (isAdd) {
							values[i].setValue("%" + values[i].getValue() + "%");
							
						//「%」を消す
						} else {
							values[i].setValue(((String)values[i].getValue()).replaceAll("%", ""));
						}
						
					}
				}
			}
		}
	}
	
	/**
	 * 検索画面へソート条件を反映する（「条件を保持しない」の場合、検索後、検索画面を閉じて再度開いたときに、値が反映されていない現象の対応）
	 * 
	 * @param columnContainer
	 * @param currentSortedColumns
	 * @param currentSortedVersusColumns
	 */
	public void setSortColumnsAfter(ColumnContainer columnContainer, ArrayList<String> currentSortedColumns, ArrayList<String> currentSortedVersusColumns) {
		
		Component[] componentArgs = columnContainer.getComponents();
		if (componentArgs != null) {
			for (int i = 0; i < componentArgs.length; i++) {
				
				Component component = componentArgs[i];
				if (component instanceof Column) {
					
					//キャストが面倒なので、クラスへ代入
					Column column = (Column)component;
					
					//項目名の取得
					String columnName = column.getColumnName();
					
					//項目がソート条件に使用されているか判定
					int columnNameIndex = currentSortedColumns.indexOf(columnName);
					if(columnNameIndex > -1) {
						
						//項目とソート条件を設定
						column.setSortVersus((String)currentSortedVersusColumns.get(columnNameIndex));
						column.setSortingOrder((columnNameIndex + 1));
					}
				}
			}
		}
	}
}
