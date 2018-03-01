//package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;
//
//import javax.swing.*;
//import javax.swing.table.JTableHeader;
//
//import java.util.ArrayList;
//import java.util.Collections;
//
//import java.awt.event.*;
//import java.io.File;
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.*;
//
//import jp.or.med.orca.jma_tokutei.common.app.JPath;
//import jp.or.med.orca.jma_tokutei.common.component.DialogFactory;
//import jp.or.med.orca.jma_tokutei.common.component.IDialog;
//import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
//import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
//import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
//import jp.or.med.orca.jma_tokutei.common.filectrl.JDirChooser;
//import jp.or.med.orca.jma_tokutei.common.filectrl.JFileCopy;
//import jp.or.med.orca.jma_tokutei.common.focus.JFocusTraversalPolicy;
//import jp.or.med.orca.jma_tokutei.common.table.JSimpleTable;
//import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableCellPosition;
//import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableScrollPanel;
//import jp.or.med.orca.jma_tokutei.common.app.JApplication;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.outputhl7.JKessaiProcess;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.outputhl7.JKessaiProcessData;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.outputhl7.JOutputHL7;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.outputhl7.JSyuukeiProcess;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.JInputKessaiDataFrameCtrl;
//import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableCellRowRenderer;
//import jp.or.med.orca.jma_tokutei.common.util.FiscalYearUtil;
//
//import javax.swing.table.DefaultTableCellRenderer;
//import javax.swing.table.TableColumnModel;
//
//import org.apache.log4j.Logger;
//
///**
// * 請求・HL7出力画面制御
// *
// * 処理が複雑なため、全体的にリファクタリングと処理の見直しを行なった。
// */
//public class JOutputHL7FrameCtrl extends JOutputHL7Frame
//{
//	/** 保険者番号、支払代行機関選択欄の、番号と名称の区切り文字列 */
//
//	private static final String COMBOBOX_NUMBER_NAME_SEPARATOR = " ";  //  @jve:decl-index=0:
//	private static final String JISSIKUBUN_TOKUTEIKENSHIN = "1";  //  @jve:decl-index=0:
//	private static final String SYUBETSU_CODE_6_DISPLAY_NAME = "特定健診機関又は特定保健指導機関から保険者";
//	private static final String SYUBETSU_CODE_1_DISPLAY_NAME = "特定健診機関又は特定保健指導機関から代行機関";
//
//	private static org.apache.log4j.Logger logger =
//		Logger.getLogger(JOutputHL7FrameCtrl.class);
//
//	// フレームの状態を管理する
//	public enum JOutputHL7FrameState {
//		/* 初期状態 */
//		STATUS_INITIALIZED,
//		/* 検索後 */
//		STATUS_AFTER_SEARCH,
//		/* 請求後 */
//		STATUS_AFTER_SEIKYU,
//		/* HL7変換後 */
//		STATUS_AFTER_HL7
//	}
//
//	// 全選択ボタンの状態
//	boolean isAllSelect = true;
//
//	/** 検索結果（画面上のデータ） */
//	private ArrayList<Hashtable<String, String>> result = null;  //  @jve:decl-index=0:
//
//	private JOutputHL7FrameState state = JOutputHL7FrameState.STATUS_INITIALIZED;  //  @jve:decl-index=0:
//	private Vector<JSimpleTableCellPosition> forbitCells = new Vector<JSimpleTableCellPosition>();
//	private Vector<JKessaiProcessData> datas = new Vector<JKessaiProcessData>();
//	JSimpleTable model = new JSimpleTable();
//	private IDialog messageDialog;
//	private JFocusTraversalPolicy focusTraversalPolicy = null;  //  @jve:decl-index=0:
//
//	/**
//	 * フレームの状態によって押せるボタンなどを制御する
//	 */
//	public void buttonCtrl()
//	{
//		switch(state)
//		{
//		case STATUS_INITIALIZED:
//			jButton_Seikyu.setEnabled(false);
//			jButton_SeikyuEdit.setEnabled(false);
//			jButton_HL7Output.setEnabled(false);
//			jButton_HL7Copy.setEnabled(false);
//			jButton_End.setEnabled(true);
//			jButton_Search.setEnabled(true);
//			jButton_AllSelect.setEnabled(false);
//			// del ver2 s.inoue 2009/07/27
//			forbitCells.clear();
//			forbitCells.add(new JSimpleTableCellPosition(-1,0));
//			break;
//
//		case STATUS_AFTER_SEARCH:
//			jButton_Seikyu.setEnabled(true);
//			jButton_SeikyuEdit.setEnabled(false);
//			jButton_HL7Output.setEnabled(false);
//			jButton_HL7Copy.setEnabled(false);
//			jButton_End.setEnabled(true);
//			jButton_Search.setEnabled(true);
//			jButton_AllSelect.setEnabled(true);
//			forbitCells.clear();
//			break;
//
//		case STATUS_AFTER_SEIKYU:
//			jButton_Seikyu.setEnabled(false);
//			jButton_SeikyuEdit.setEnabled(true);
//			jButton_HL7Output.setEnabled(true);
//			jButton_HL7Copy.setEnabled(false);
//			jButton_End.setEnabled(true);
//			jButton_Search.setEnabled(true);
//			jButton_AllSelect.setEnabled(false);
//			forbitCells.clear();
//			forbitCells.add(new JSimpleTableCellPosition(-1,0));
//			break;
//
//		case STATUS_AFTER_HL7:
//			jButton_Seikyu.setEnabled(false);
//			jButton_SeikyuEdit.setEnabled(false);
//			jButton_HL7Output.setEnabled(false);
//			jButton_HL7Copy.setEnabled(true);
//			jButton_End.setEnabled(true);
//			jButton_Search.setEnabled(true);
//			jButton_AllSelect.setEnabled(false);
//			forbitCells.clear();
//			forbitCells.add(new JSimpleTableCellPosition(-1,0));
//			break;
//		}
//	}
//
//	/**
//	 * テーブルに表示する内容を更新する。
//	 */
//	public int tableRefresh()
//	{
//		JOutputHL7FrameData validatedData = new JOutputHL7FrameData();
//
//		/* コンボボックスで選択されている保険者番号、名称を取得する。 */
//		String hokenjaNumberAndNameString = (String)jComboBox_HokenjyaNumber.getSelectedItem();
//		String hokenjaNumber = "";
//		String hokenjaName = "";
//		if (hokenjaNumberAndNameString != null && ! hokenjaNumberAndNameString.isEmpty()) {
//			String[] hokenjaNumberAndName = hokenjaNumberAndNameString.split(COMBOBOX_NUMBER_NAME_SEPARATOR);
//			hokenjaNumber = hokenjaNumberAndName[0];
//
//			if (hokenjaNumberAndName.length > 1) {
//				hokenjaName = hokenjaNumberAndName[1];
//			}
//		}
//
//		/* コンボボックスで選択されている支払代行機関番号、名称を取得する。 */
//		String daikoNumberAndNameString = (String)jComboBox_SeikyuKikanNumber.getSelectedItem();
//		String daikoNumber = "";
//		String daikoName = "";
//		if (daikoNumberAndNameString != null && ! daikoNumberAndNameString.isEmpty()) {
//			String[] daikoNumberAndName = daikoNumberAndNameString.split(COMBOBOX_NUMBER_NAME_SEPARATOR);
//			daikoNumber = daikoNumberAndName[0];
//
//			if (daikoNumberAndName.length > 1) {
//				daikoName = daikoNumberAndName[1];
//			}
//		}
//
//		if(
//				/* 被保険者証等記号 */
//				validatedData.setHihokenjyasyo_kigou(jTextField_Hihokenjyasyo_kigou.getText()) &&
//				/* 被保険者証等番号 */
//				validatedData.setHihokenjyasyo_Number(jTextField_Hihokenjyasyo_Number.getText()) &&
//				/* 実施日（開始） */
//				validatedData.setJissiBeginDate(jTextField_JissiBeginDate.getText()) &&
//				/* 実施日（終了） */
//				validatedData.setJissiEndDate(jTextField_JissiEndDate.getText()) &&
//				/* 変換日（開始） */
//				validatedData.setHL7BeginDate(jTextField_HL7BeginDate.getText()) &&
//				/* 変換日（終了） */
//				validatedData.setHL7EndDate(jTextField_HL7EndDate.getText()) &&
//				/* 保険証番号 */
//				validatedData.setHokenjyaNumber(hokenjaNumber) &&
//				/* 保険者名称 */
//				validatedData.setHokenjyaName(hokenjaName) &&
//				/* 請求先機関番号 */
//				validatedData.setSeikyuKikanNumber(daikoNumber) &&
//				/* 請求先機関名称 */
//				validatedData.setSeikyuKikanName(daikoName) &&
//				/* 氏名 */
//				validatedData.setName(jTextField_Name.getText())
//		)
//		{
//			/* Where 句を除いたSQLを作成する。 */
//			StringBuffer queryBuffer = this.createSearchQueryWithoutCondition();
//
//			/* 条件文を付加する。 */
//			this.appendCondition(queryBuffer, validatedData);
//
//			/* SQLを実行する。 */
//			try{
//				result = JApplication.kikanDatabase.sendExecuteQuery(queryBuffer.toString());
//			}catch(SQLException f){
//				f.printStackTrace();
//			}
//
//			/* 検索結果をテーブルに追加する。 */
//			this.addRowToTable();
//
//			return result.size();
//		}
//		// バリデータで引っかかった場合
//		return 0;
//	}
//
//	/**
//	 * 検索結果をテーブルに追加する。
//	 */
//	private void addRowToTable() {
//		Hashtable<String, String> resultItem;
//		model.clearAllRow();
//		for(int i = 0;i < result.size();i++ )
//		{
//			Vector<String> row = new Vector<String>();
//			resultItem = result.get(i);
//
//			row.add(null);
//			// add ver2 s.inoue 2009/06/23
//			/* 年度 */
//			row.add(resultItem.get("NENDO"));
//
//			/* 受診券整理番号 */
//			row.add(resultItem.get("JYUSHIN_SEIRI_NO"));
//			/* 氏名（カナ） */
//			row.add(resultItem.get("KANANAME"));
//			/* 氏名（名前） */
//			row.add(resultItem.get("NAME"));
//
//			/* 性別 */
//			String sexCode = resultItem.get("SEX");
//			String sexText = "";
//			if (sexCode.equals("1")) {
//				sexText = "男性";
//			}
//			else if(sexCode.equals("2")){
//				sexText = "女性";
//			}
//			row.add(sexText);
//
//			/* 生年月日 */
//			row.add(resultItem.get("BIRTHDAY"));
//			/* 健診実施日 */
//			row.add(resultItem.get("KENSA_NENGAPI"));
//
//			/* 変換実施日 */
//			// edit ver2 s.inoue 2009/04/02
//			//row.add(resultItem.get("TUTI_NENGAPI"));
//			row.add(resultItem.get("HENKAN_NITIJI"));
//
//			/* 保険者番号 */
//			row.add(resultItem.get("HKNJANUM"));
//			/* 被保険者証等記号 */
//			row.add(resultItem.get("HIHOKENJYASYO_KIGOU"));
//			/* 被保険者証等番号 */
//			row.add(resultItem.get("HIHOKENJYASYO_NO"));
//
//			model.addData(row);
//		}
//	}
//
//	/**
//	 * 条件文を付加する。
//	 */
//	private void appendCondition(StringBuffer Query, JOutputHL7FrameData validatedData) {
//		/* 検索条件を付加する。 */
//		ArrayList<String> conditions = new ArrayList<String>();
//
//		/* 空値のテキストフィールドは条件から除外する */
//
//		String jyushinkenId = jTextField_JyushinKenID.getText();
//		if (jyushinkenId != null && ! jyushinkenId.isEmpty()) {
//			conditions.add(" KOJIN.JYUSHIN_SEIRI_NO CONTAINING " + JQueryConvert.queryConvert(jyushinkenId));
//		}
//
//		String hihokenjyasyo_kigou = validatedData.getHihokenjyasyo_kigou();
//		if( !hihokenjyasyo_kigou.isEmpty() )
//		{
//			conditions.add(" KOJIN.HIHOKENJYASYO_KIGOU CONTAINING " + JQueryConvert.queryConvert(hihokenjyasyo_kigou));
//		}
//		String hihokenjyasyo_Number = validatedData.getHihokenjyasyo_Number();
//		if( !hihokenjyasyo_Number.isEmpty() )
//		{
//			conditions.add(" KOJIN.HIHOKENJYASYO_NO CONTAINING " + JQueryConvert.queryConvert(hihokenjyasyo_Number));
//		}
//		String hokenjyaNumber = validatedData.getHokenjyaNumber();
//		if( !hokenjyaNumber.isEmpty() )
//		{
//			conditions.add(" KOJIN.HKNJANUM CONTAINING " + JQueryConvert.queryConvert(hokenjyaNumber));
//		}
//		String seikyuKikanNumber = validatedData.getSeikyuKikanNumber();
//		if( !seikyuKikanNumber.isEmpty() )
//		{
//			conditions.add(" KOJIN.SIHARAI_DAIKOU_BANGO CONTAINING " + JQueryConvert.queryConvert(seikyuKikanNumber));
//		}
//		String name2 = validatedData.getName();
//		if( !name2.isEmpty() )
//		{
//			conditions.add(" KOJIN.KANANAME CONTAINING " + JQueryConvert.queryConvert(name2));
//		}
//
//		String jissiBeginDate = validatedData.getJissiBeginDate();
//		if( !jissiBeginDate.isEmpty() )
//		{
//			conditions.add(" TOKUTEI.KENSA_NENGAPI >= " + JQueryConvert.queryConvert(jissiBeginDate));
//		}
//		String jissiEndDate = validatedData.getJissiEndDate();
//		if( !jissiEndDate.isEmpty() )
//		{
//			conditions.add(" TOKUTEI.KENSA_NENGAPI <= " + JQueryConvert.queryConvert(jissiEndDate));
//		}
//
//		String beginDate = validatedData.getHL7BeginDate();
//		if( !beginDate.isEmpty() )
//		{
//			// edit ver2 s.inoue 2009/04/02
//			//conditions.add(" TOKUTEI.TUTI_NENGAPI >= " + JQueryConvert.queryConvert(beginDate));
//			conditions.add(" TOKUTEI.HENKAN_NITIJI >= " + JQueryConvert.queryConvert(beginDate));
//		}
//		String endDate = validatedData.getHL7EndDate();
//		if( !endDate.isEmpty() )
//		{
//			// edit ver2 s.inoue 2009/04/02
//			//conditions.add(" TOKUTEI.TUTI_NENGAPI <= " + JQueryConvert.queryConvert(endDate));
//			conditions.add(" TOKUTEI.HENKAN_NITIJI <= " + JQueryConvert.queryConvert(endDate));
//		}
//		if( jCheckBox_IsPermitHL7.isSelected() )
//		{
//			// edit ver2 s.inoue 2009/04/02
//			//conditions.add(" TOKUTEI.TUTI_NENGAPI IS NULL ");
//			conditions.add(" TOKUTEI.HENKAN_NITIJI IS not NULL ");
//		}else{
//			conditions.add(" TOKUTEI.HENKAN_NITIJI IS NULL ");
//		}
//
//		/* 今年度 */
//		// add ver2 s.inoue 2009/06/23
//		// 今年度を表示
//		if (jCheckBox_JisiYear.isSelected()) {
//			conditions.add(" GET_NENDO.NENDO = "	+ JQueryConvert.queryConvert(String.valueOf(FiscalYearUtil.getThisYear())));
//		}
//
//		// 結果登録「未、済」の「未」のデータは表示しない
//		conditions.add(" TOKUTEI.KEKA_INPUT_FLG = '2' ");
//
//		/* 検索条件を付加する */
//		if (conditions.size() > 0) {
//			Query.append(" WHERE ");
//
//			for (ListIterator<String> iter = conditions.listIterator(); iter.hasNext();) {
//				String condition = iter.next();
//				Query.append(condition);
//
//				if (iter.hasNext()) {
//					Query.append(" AND ");
//				}
//			}
//			// add ver2 s.inoue 2009/06/12
//			// 表示順
//			Query.append(" ORDER BY NENDO DESC,KANANAME,BIRTHDAY,KENSA_NENGAPI DESC");
//		}
//	}
//
//	/**
//	 * 検索用の SQL を作成する。
//	 */
//	private StringBuffer createSearchQueryWithoutCondition() {
//		// 検索項目を抜いたクエリ
//		StringBuffer Query = new StringBuffer(
//				"SELECT DISTINCT " +
//					/* 受付ID */
//					"KOJIN.UKETUKE_ID," +
//					/* 生年月日 */
//					"KOJIN.BIRTHDAY," +
//					/* 性別 */
//					"KOJIN.SEX," +
//					/* 氏名 */
//					"KOJIN.NAME," +
//					/* 受診券整理番号 */
//					"KOJIN.JYUSHIN_SEIRI_NO," +
//					/* 被保険者証等記号 */
//					"KOJIN.HIHOKENJYASYO_KIGOU," +
//					/* 被保険者証等番号 */
//					"KOJIN.HIHOKENJYASYO_NO," +
//					/* 検査年月日 */
//					"TOKUTEI.KENSA_NENGAPI," +
//
//					/* 変換日時 */
//					// edit ver2 s.inoue 20090401
//					// "TOKUTEI.TUTI_NENGAPI," +
//					"TOKUTEI.HENKAN_NITIJI," +
//
//					/* 保険者番号（個人） */
//					"KOJIN.HKNJANUM," +
//					/* 支払代行機関番号 */
//					"KOJIN.SIHARAI_DAIKOU_BANGO," +
//					/* カナ氏名 */
//					"KOJIN.KANANAME, " +
//					/* 年度 */
//					// add ver2 s.inoue 2009/06/23
//					"GET_NENDO.NENDO " +
//
//				"FROM" +
//					" T_KOJIN AS KOJIN " +
//
//				"LEFT JOIN T_KENSAKEKA_TOKUTEI AS TOKUTEI " +
//				"ON " +
//				"KOJIN.UKETUKE_ID = TOKUTEI.UKETUKE_ID " +
//				"LEFT JOIN T_KESAI_WK AS KESAI " +
//				"ON " +
//				"KOJIN.UKETUKE_ID = KESAI.UKETUKE_ID " +
//				// add ver2 s.inoue 2009/06/23
//				" LEFT JOIN (select UKETUKE_ID,KENSA_NENGAPI,case when tokutei.NENDO is not null then tokutei.NENDO " +
//				// edit ver2 s.inoue 2009/07/13
//				// " else substring(tokutei.KENSA_NENGAPI FROM 1 FOR 4) end as NENDO " +
//				" when substring(KENSA_NENGAPI FROM 5 FOR 2) ='01' OR " +
//				" substring(KENSA_NENGAPI FROM 5 FOR 2) ='02' OR " +
//				" substring(KENSA_NENGAPI FROM 5 FOR 2) ='03' " +
//				" then CAST(substring(tokutei.KENSA_NENGAPI FROM 1 FOR 4) AS INTEGER) - 1 " +
//				" else substring(tokutei.KENSA_NENGAPI FROM 1 FOR 4) end as NENDO " +
//
//				" from T_KENSAKEKA_TOKUTEI tokutei) as GET_NENDO " +
//				" ON GET_NENDO.UKETUKE_ID = tokutei.UKETUKE_ID " +
//				" AND GET_NENDO.KENSA_NENGAPI = tokutei.KENSA_NENGAPI "
//		);
//		return Query;
//	}
//
//	/**
//	 * コンストラクタ
//	 */
//	public JOutputHL7FrameCtrl()
//	{
//		this.initializeCtrl();
//	}
//
//	/**
//	 * 制御クラスの初期化
//	 */
//	private void initializeCtrl() {
//
//		state = JOutputHL7FrameState.STATUS_INITIALIZED;
//
//		JSimpleTableScrollPanel scroll = new JSimpleTableScrollPanel(model);
//		jPanel_TableArea.add(scroll);
//
//		/* テーブルを初期化する。 */
//		this.initializeTable();
//
//		// edit ver2 s.inoue 2009/06/23
//		jCheckBox_IsPermitHL7.setSelected(true);
//		jCheckBox_JisiYear.setSelected(true);
//
//		/* 保険者番号コンボボックスの設定 */
//		this.initializeHokenjaNumComboBox();
//
//		/* 請求機関番号コンボボックスを初期化する。 */
//		this.initializeSeikyukikanNumberComboBox();
//
//		// 種別コードコンボボックスの設定
//		jComboBox_SyubetuCode.addItem(SYUBETSU_CODE_1_DISPLAY_NAME);
//		jComboBox_SyubetuCode.addItem(SYUBETSU_CODE_6_DISPLAY_NAME);
//
//		/* ボタンの状態を変更する。 */
//		this.buttonCtrl();
//
//		/* メッセージダイアログを初期化する。ｌ */
//		try {
//			messageDialog = DialogFactory.getInstance().createDialog(this, new JButton());
//			messageDialog.setShowCancelButton(false);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		this.focusTraversalPolicy = new JFocusTraversalPolicy();
//		this.setFocusTraversalPolicy(this.focusTraversalPolicy);
//		this.focusTraversalPolicy.setDefaultComponent(jTextField_JyushinKenID);
//		this.focusTraversalPolicy.addComponent(this.jTextField_JyushinKenID);
//		this.focusTraversalPolicy.addComponent(this.jTextField_Name);
//		this.focusTraversalPolicy.addComponent(this.jTextField_Hihokenjyasyo_kigou);
//		this.focusTraversalPolicy.addComponent(this.jTextField_Hihokenjyasyo_Number);
//		this.focusTraversalPolicy.addComponent(this.jComboBox_HokenjyaNumber);
//		this.focusTraversalPolicy.addComponent(this.jComboBox_SeikyuKikanNumber);
//		this.focusTraversalPolicy.addComponent(this.jTextField_JissiBeginDate);
//		this.focusTraversalPolicy.addComponent(this.jTextField_JissiEndDate);
//		this.focusTraversalPolicy.addComponent(this.jTextField_HL7BeginDate);
//		this.focusTraversalPolicy.addComponent(this.jTextField_HL7EndDate);
//		this.focusTraversalPolicy.addComponent(this.model);
//		this.focusTraversalPolicy.addComponent(this.jButton_AllSelect);
//		this.focusTraversalPolicy.addComponent(this.jCheckBox_IsPermitHL7);
//		this.focusTraversalPolicy.addComponent(this.jButton_Search);
//		this.focusTraversalPolicy.addComponent(this.jButton_Seikyu);
//		this.focusTraversalPolicy.addComponent(this.jButton_SeikyuEdit);
//		this.focusTraversalPolicy.addComponent(this.jButton_HL7Output);
//		this.focusTraversalPolicy.addComponent(this.jButton_HL7Copy);
//		this.focusTraversalPolicy.addComponent(this.jButton_End);
//
//	}
//
//	/**
//	 * テーブルを初期化する。
//	 */
//	private void initializeTable() {
//		/* 列幅の設定 */
//		// edit ver2 s.inoue 2009/06/23
//		model.setPreferedColumnWidths(new int[]{50, 50, 120, 150, 150, 50, 100, 100, 100, 100, 100, 100});
//
//		// edit ver2 s.inoue 2009/06/23
//		/* セルの編集可否を設定する。 */
//		JSimpleTableCellPosition[] forbitCells = {
//				new JSimpleTableCellPosition(-1,1),
//				new JSimpleTableCellPosition(-1,2),
//				new JSimpleTableCellPosition(-1,3),
//				new JSimpleTableCellPosition(-1,4),
//				new JSimpleTableCellPosition(-1,5),
//				new JSimpleTableCellPosition(-1,6),
//				new JSimpleTableCellPosition(-1,7),
//				new JSimpleTableCellPosition(-1,8),
//				new JSimpleTableCellPosition(-1,9),
//				new JSimpleTableCellPosition(-1,10),
//				new JSimpleTableCellPosition(-1,11),
//				new JSimpleTableCellPosition(-1,12),
//				};
//		this.model.setCellEditForbid(forbitCells);
//
//		model.setCellEditForbid(forbitCells);
//		model.addHeader("");
//		model.addHeader("年度");
//		model.addHeader("受診券整理番号");
//
//		model.addHeader("氏名（カナ）");
//		model.addHeader("氏名（漢字）");
//		model.addHeader("性別");
//		model.addHeader("生年月日");
//		model.addHeader("健診実施日");
//		model.addHeader("HL7出力日");
//		model.addHeader("保険者番号");
//		model.addHeader("被保険者証等記号");
//		model.addHeader("被保険者証等番号");
//		model.setCellCheckBoxEditor(new JCheckBox(), 0);
//
//		/* カラムヘッダのクリックによる並び替えを可能にする。 */
//		model.setAutoCreateRowSorter(true);
//
//		// add ver2 s.inoue 2009/05/08
//		TableColumnModel columns = model.getColumnModel();
//        for(int i=1;i<columns.getColumnCount();i++) {
//
//            columns.getColumn(i).setCellRenderer(new JSimpleTableCellRowRenderer(
//                (DefaultTableCellRenderer)model.getDefaultRenderer(model.getColumnClass(i))));
//        }
//
//        searchCondition(true);
//	}
//
//	/**
//	 * 支払代行機関コンボボックスを初期化する
//	 */
//	private void initializeSeikyukikanNumberComboBox() {
//		ArrayList<Hashtable<String, String>> result = null;
//
//		String query = new String("SELECT SHIHARAI_DAIKO_NO,SHIHARAI_DAIKO_NAME FROM T_SHIHARAI ORDER BY SHIHARAI_DAIKO_NO");
//		try{
//			result = JApplication.kikanDatabase.sendExecuteQuery(query);
//		}catch(SQLException e){
//			e.printStackTrace();
//		}
//
//		jComboBox_SeikyuKikanNumber.addItem("");
//
//		for(int i = 0;i < result.size();i++ )
//		{
//			Hashtable<String,String> ResultItem = result.get(i);
//			String num = ResultItem.get("SHIHARAI_DAIKO_NO");
//			String name = ResultItem.get("SHIHARAI_DAIKO_NAME");
//
//			StringBuffer buffer = new StringBuffer();
//			buffer.append(num);
//			if (name != null && ! num.isEmpty()) {
//				buffer.append(COMBOBOX_NUMBER_NAME_SEPARATOR);
//				buffer.append(name);
//			}
//
//			jComboBox_SeikyuKikanNumber.addItem(buffer.toString());
//		}
//	}
//
//	/**
//	 * 保険者番号、名称の一覧を取得する。
//	 */
//	private ArrayList<Hashtable<String, String>> getHokenjaNumAndNames() {
//		ArrayList<Hashtable<String, String>> result = null;
//		String query = new String("SELECT HKNJANUM,HKNJANAME FROM T_HOKENJYA ORDER BY HKNJANUM");
//		try{
//			result = JApplication.kikanDatabase.sendExecuteQuery(query);
//		}catch(SQLException e){
//			e.printStackTrace();
//		}
//		return result;
//	}
//
//	/**
//	 * 保険者番号コンボボックスの初期化
//	 */
//	private void initializeHokenjaNumComboBox() {
//		ArrayList<Hashtable<String, String>> result = this.getHokenjaNumAndNames();
//
//		jComboBox_HokenjyaNumber.addItem("");
//
//		for(int i = 0;i < result.size();i++ )
//		{
//			Hashtable<String,String> resultItem = result.get(i);
//			String num = resultItem.get("HKNJANUM");
//			String name = resultItem.get("HKNJANAME");
//
//			StringBuffer buffer = new StringBuffer();
//			buffer.append(num);
//			if (name != null && ! num.isEmpty()) {
//				buffer.append(COMBOBOX_NUMBER_NAME_SEPARATOR);
//				buffer.append(name);
//			}
//
//			jComboBox_HokenjyaNumber.addItem(buffer.toString());
//		}
//	}
//
//	/**
//	 * 終了ボタンが押された場合 ５
//	 */
//	public void pushedEndButton( ActionEvent e )
//	{
//		dispose();
//	}
//
//	/**
//	 * ＨＬ７外部出力ボタンが押された場合 ４
//	 */
//	public void pushedHL7CopyButton( ActionEvent e )
//	{
//		JDirChooser dirSelect = new JDirChooser();
//
//		switch(state)
//		{
//		case STATUS_AFTER_HL7:
//			JFileChooser Chooser = new JFileChooser(JPath.ZIP_OUTPUT_DIRECTORY_PATH);
//
//			// ファイル選択ダイアログの表示
//			// TODO 出力するファイルを選択してください。
//			if( Chooser.showOpenDialog( this ) == JFileChooser.APPROVE_OPTION )
//			{
//				// TODO 出力先のフォルダを選択してください。
//				if( dirSelect.showSaveDialog( this ) == JFileChooser.APPROVE_OPTION )
//				{
//					try {
//						if( JFileCopy.copyFile(
//								Chooser.getSelectedFile().getAbsolutePath(),			// コピー元
//								dirSelect.getSelectedFile().getPath() + File.separator +
//								Chooser.getSelectedFile().getName() ) != true )
//																						// コピー先
//						{
//							JErrorMessage.show("M4720", this);
//						}else{
//							JErrorMessage.show("M4721", this);
//						}
//					} catch (IOException e1) {
//						e1.printStackTrace();
//						JErrorMessage.show("M4720", this);
//					}
//				}
//			}
//
//			state = JOutputHL7FrameState.STATUS_AFTER_HL7;
//		}
//
//		buttonCtrl();
//	}
//
//	/**
//	 * ＨＬ７出力ボタンが押された場合 ３
//	 */
//	public void pushedHL7OutputButton( ActionEvent e )
//	{
//		switch(state)
//		{
//			case STATUS_AFTER_SEIKYU:
//				/* datas は、HL7 出力時に必要な情報 */
//				if( JOutputHL7.RunProcess(datas) )
//				{
//					// 正常に終了した場合
//					state = JOutputHL7FrameState.STATUS_AFTER_HL7;
//					tableRefresh();
//				}
//
//				break;
//		}
//		buttonCtrl();
//
//		// HL7出力処理終了時にメッセージボックスを表示
//		messageDialog.setMessageTitle("ＨＬ７出力");
//
//		String message = "";
//		if (state == JOutputHL7FrameState.STATUS_AFTER_HL7) {
//			message = "HL7出力処理が終了しました";
//		}
//		else {
//			message = "HL7出力処理に失敗しました";
//		}
//		messageDialog.setText(message);
//		messageDialog.setVisible(true);
//	}
//
//	/**
//	 * 請求処理ボタンが押された場合
//	 *
//	 * Modified 2008/04/01 若月 可読性が低く、障害の調査が困難なため、全面的に書き直す。
//	 */
//	public void pushedSeikyuButton( ActionEvent e )
//	{
//		switch(state)
//		{
//		case STATUS_AFTER_SEARCH:
//			int count = 0;
//
//			boolean notExtMessage = false;
//
//			datas = new Vector<JKessaiProcessData>();
//
//			for( int i = 0;i < result.size();i++ )
//			{
//				if( Boolean.TRUE == (Boolean)model.getData(i,0)  )
//				{
//					count++;
//
//					Hashtable<String, String> item = result.get(i);
//
//					JKessaiProcessData dataItem = new JKessaiProcessData();
//					/* 受付ID */
//					dataItem.uketukeId = item.get("UKETUKE_ID");
//					/* 被保険者証等記号 */
//					dataItem.hiHokenjyaKigo = item.get("HIHOKENJYASYO_KIGOU");
//					/* 被保険者証等番号 */
//					dataItem.hiHokenjyaNumber = item.get("HIHOKENJYASYO_NO");
//					/* 保険者番号（個人） */
//					dataItem.hokenjyaNumber = item.get("HKNJANUM");
//					/* 検査実施日 */
//					dataItem.KensaDate = item.get("KENSA_NENGAPI");
//					/* 支払代行機関番号 */
//					dataItem.daikouKikanNumber = item.get("SIHARAI_DAIKOU_BANGO");
//
//					/* カナ氏名 */
//					dataItem.kanaName = item.get("KANANAME");
//					/* 性別 */
//					dataItem.sex = item.get("SEX");
//					/* 生年月日 */
//					dataItem.birthday = item.get("BIRTHDAY");
//
//					if (dataItem.hiHokenjyaNumber == null || dataItem.hiHokenjyaNumber.isEmpty()) {
//						/* 未入力エラー,被保険者証等番号が入力されていません。一覧で被保険者証等番号を確認してください。
//						 * [改行]　氏名（カナ）[%s]、性別[%s]、生年月日[%s] */
//						String sexName = dataItem.sex.equals("1") ? "男性" : "女性";
//						String[] params = { dataItem.kanaName, sexName, dataItem.birthday, };
//
//						JErrorMessage.show("M4751", this, params);
//						return;
//					}
//
//					if (dataItem.KensaDate == null || dataItem.KensaDate.isEmpty()) {
//						/* 入力エラー,健診結果データが存在しません。[改行]　氏名（カナ）[%s]、性別[%s]、生年月日[%s] */
//						String sexName = dataItem.sex.equals("1") ? "男性" : "女性";
//						String[] params = { dataItem.kanaName, sexName, dataItem.birthday, };
//
//						JErrorMessage.show("M4753", this, params);
//						return;
//					}
//
//					/*
//					 * 種別コードを格納する
//					 */
//					if( dataItem.daikouKikanNumber != null && ! dataItem.daikouKikanNumber.isEmpty() )
//					{
//						dataItem.syubetuCode = "1";
//					}else{
//						dataItem.syubetuCode = "6";
//					}
//
//					// 実施区分を格納する（特定健診は 1 固定）
//					dataItem.jissiKubun = JISSIKUBUN_TOKUTEIKENSHIN;
//
//					/* 請求区分を格納する。 */
//					String seikyuKubun = getSeikyuKubun(i);
//
//					if (seikyuKubun == null || seikyuKubun.isEmpty()) {
//						/* M4732：エラー,請求区分の取得に失敗しました。,0,0 */
//						JErrorMessage.show("M4733", this);
//						return;
//					}
//
//					dataItem.seikyuKubun = seikyuKubun;
//
//					String[] registKensa= JInputKessaiDataFrameCtrl.isNotExistKensaKoumoku(
//							dataItem.hokenjyaNumber,
//							dataItem.uketukeId,
//							dataItem.KensaDate,
//							dataItem.seikyuKubun);
//
//					if (registKensa != null){
//						// edit s.inoue 20081119
//						if (!notExtMessage){
//							JErrorMessage.show("M3635", this);
//							notExtMessage = true;
//						}
//					}
//
//					String[] existKensa= JInputKessaiDataFrameCtrl.isExistKensaKoumoku(
//							dataItem.hokenjyaNumber,
//							dataItem.uketukeId,
//							dataItem.KensaDate,
//							dataItem.seikyuKubun);
//
//					if (existKensa != null){
//						if (!notExtMessage){
//							JErrorMessage.show("M3636", this );
//							notExtMessage = true;
//						}
//					}
//
//					datas.add(dataItem);
//				}
//			}
//
//			if( count != 0 )
//			{
//				try{
//
//					// 決済、集計総tran開始
//					JApplication.kikanDatabase.Transaction();
//
//					JOutputHL7FrameData validatedData = new JOutputHL7FrameData();
//
//					if( validatedData.setSyubetuCode((String)jComboBox_SyubetuCode.getSelectedItem()))
//					{
//						try {
//							/* 決済処理 */
//							JKessaiProcess.runProcess(datas, JApplication.kikanNumber);
//
//						} catch (Exception e1) {
//							e1.printStackTrace();
//							JErrorMessage.show("M4730", this);
//							break;
//						}
//
//						/* 集計処理 */
//						if(JSyuukeiProcess.RunProcess(datas) == false)
//						{
//							JErrorMessage.show("M4731", this);
//							break;
//						}
//
//						state = JOutputHL7FrameState.STATUS_AFTER_SEIKYU;
//
//						messageDialog.setMessageTitle("請求処理");
//						messageDialog.setText("請求処理が終了しました");
//						messageDialog.setVisible(true);
//
//						// 決済、集計tran終了
//						JApplication.kikanDatabase.Commit();
//					}
//				}catch(Exception ex){
//					logger.error(ex.getMessage());
//				}
//			}
//		}
//		buttonCtrl();
//	}
//
//	private String getSeikyuKubun(int i) {
//		// 請求区分を格納する
//		String query = "SELECT * FROM T_KENSAKEKA_TOKUTEI WHERE " +
//				"UKETUKE_ID = " + JQueryConvert.queryConvert(result.get(i).get("UKETUKE_ID")) + " AND " +
//				"KENSA_NENGAPI = " + JQueryConvert.queryConvert(result.get(i).get("KENSA_NENGAPI"));
//
//		ArrayList<Hashtable<String, String>> tbl = null;
//		try{
//			tbl = JApplication.kikanDatabase.sendExecuteQuery(query);
//		}catch(SQLException f){
//			f.printStackTrace();
//		}
//
//		String seikyuKubun = null;
//		if (tbl != null && tbl.size() == 1) {
//			seikyuKubun = tbl.get(0).get("SEIKYU_KBN");
//		}
//
//		return seikyuKubun;
//	}
//
//	/**
//	 * 支払代行機関が設定されているかを調べる。
//	 */
//	private boolean existsShiharaiDaikoKikan(int i) {
//
//		/* 支払代行機関番号の有無を調べる */
//		String Query = "select * from T_KOJIN " +
//									" where " +
//									" UKETUKE_ID = " + JQueryConvert.queryConvert(result.get(i).get("UKETUKE_ID")) + " AND " +
//									" SIHARAI_DAIKOU_BANGO IS NULL";
//
//		ArrayList<Hashtable<String, String>> tbl = null;
//		try{
//			tbl = JApplication.kikanDatabase.sendExecuteQuery(Query);
//		}catch(SQLException f){
//			f.printStackTrace();
//		}
//
//		if( ! tbl.isEmpty() ){
//			return true;
//		}
//
//		return false;
//	}
//
//	/**
//	 * 請求データ編集処理ボタンが押された場合 2
//	 */
//	public void pushedSeikyuEditButton( ActionEvent e )
//	{
//		switch(state)
//		{
//		case STATUS_AFTER_SEIKYU:
//			// 一つだけ選択されている場合のみ
//			if( model.getSelectedRowCount() == 1 )
//			{
//				int[] selection = model.getSelectedRows();
//				int modelRow=0;
//	            for (int i = 0; i < selection.length; i++) {
//
//	                // テーブルモデルの行数に変換
//	                modelRow = model.convertRowIndexToModel(selection[i]);
//	                System.out.println("View Row: " + selection[i] + " Model Row: " + modelRow);
//	            }
//
//	            Hashtable<String, String> resultItem = result.get(modelRow);
//
//				String uketukeId = resultItem.get("UKETUKE_ID");
//				String hihokenjyasyoKigou = resultItem.get("HIHOKENJYASYO_KIGOU");
//				String hihokenjyasyoNo = resultItem.get("HIHOKENJYASYO_NO");
//				String kensaNengapi = resultItem.get("KENSA_NENGAPI");
//
//				JInputKessaiDataFrameCtrl ctrl = new JInputKessaiDataFrameCtrl(
//						uketukeId,
//						hihokenjyasyoKigou,
//						hihokenjyasyoNo,
//						kensaNengapi,
//						datas);
//
//				jp.or.med.orca.jma_tokutei.common.scene.JScene.CreateDialog(this,ctrl);
//
//				state = JOutputHL7FrameState.STATUS_AFTER_SEIKYU;
//				break;
//			}
//		}
//		buttonCtrl();
//	}
//
//	/**
//	 * 全て選択ボタンが押された場合 7
//	 */
//	public void pushedAllSelectButton( ActionEvent e )
//	{
//		switch(state)
//		{
//		case STATUS_AFTER_SEARCH:
//			for(int i = 0;i < model.getRowCount();i++ )
//			{
//				if( isAllSelect )
//					model.setData(true, i, 0);
//				else
//					model.setData(false, i, 0);
//			}
//
//			if( isAllSelect )
//			{
//				isAllSelect = false;
//			}else{
//				isAllSelect = true;
//			}
//
//			break;
//		}
//		buttonCtrl();
//	}
//
//	/**
//	 * 検索ボタンが押された場合 ６
//	 */
//	public void pushedSearchButton( ActionEvent e )
//	{
//		searchCondition(false);
//	}
//
//	private void searchCondition(boolean initialize){
//		int rowCount;
//
//		buttonCtrl();
//
//		switch(state)
//		{
//		/* FALLTHROUGH */
//		case STATUS_INITIALIZED:
//		case STATUS_AFTER_SEARCH:
//		case STATUS_AFTER_SEIKYU:
//		case STATUS_AFTER_HL7:
//			rowCount = tableRefresh();
//			if( rowCount == 0 )
//			{
//				state = JOutputHL7FrameState.STATUS_INITIALIZED;
//			}else{
//				// add ver2 s.inoue 2009/06/24
//				if (!initialize){
//					// 検索結果にＨＬ７変換済みの受診者がいるかどうかチェックする
//					for(int i = 0; i < model.getRowCount();i++ )
//					{
//						// edit ver2 s.inoue 2009/06/23
//						//Object tutiNengapi = model.getData(i, 7);
//						Object tutiNengapi = model.getData(i, 8);
//						if(tutiNengapi != null && ! tutiNengapi.equals("") )
//						{
//							RETURN_VALUE Ret = JErrorMessage.show("M4740", this);
//							if(Ret == RETURN_VALUE.NO)
//							{
//								state = JOutputHL7FrameState.STATUS_INITIALIZED;
//								buttonCtrl();
//								model.clearAllRow();
//								return;
//							}else{
//								break;
//							}
//						}
//					}
//				}
//				state = JOutputHL7FrameState.STATUS_AFTER_SEARCH;
//			}
//			break;
//		}
//		buttonCtrl();
//	}
//
//	public void itemStateChanged( ItemEvent e )
//	{
//	}
//
//	public void actionPerformed( ActionEvent e )
//	{
//		Object source = e.getSource();
//		// 5
//		if( source == jButton_End			)
//		{
//			pushedEndButton( e );
//		}
//
//		// 3
//		else if( source == jButton_HL7Copy		)
//		{
//			pushedHL7CopyButton( e );
//		}
//
//		// HL7 出力ボタン
//		else if( source == jButton_HL7Output	)
//		{
//			pushedHL7OutputButton( e );
//		}
//
//		// 請求処理ボタン
//		else if( source == jButton_Seikyu		)
//		{
//			pushedSeikyuButton( e );
//		}
//
//		// 2
//		else if( source == jButton_SeikyuEdit		)
//		{
//			pushedSeikyuEditButton( e );
//		}
//
//		// 7
//		else if( source == jButton_AllSelect	)
//		{
//			pushedAllSelectButton( e );
//		}
//
//		// 6
//		else if( source == jButton_Search		)
//		{
//			pushedSearchButton( e );
//		}
//	}
//}
