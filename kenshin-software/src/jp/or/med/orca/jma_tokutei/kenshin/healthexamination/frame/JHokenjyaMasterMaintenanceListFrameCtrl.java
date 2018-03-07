//package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;
//
//import java.awt.Component;
//import java.awt.event.*;
//
//import java.io.File;
//import java.io.IOException;
//import java.lang.reflect.InvocationTargetException;
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.util.*;
//
//import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
//import jp.or.med.orca.jma_tokutei.common.convert.JZenkakuKatakanaToHankakuKatakana;
//import jp.or.med.orca.jma_tokutei.common.csv.JCSVReaderStream;
//import jp.or.med.orca.jma_tokutei.common.csv.JCSVWriterStream;
//import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
//import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
//import jp.or.med.orca.jma_tokutei.common.event.JSingleDoubleClickEvent;
//import jp.or.med.orca.jma_tokutei.common.event.JEnterEvent;
//import jp.or.med.orca.jma_tokutei.common.focus.JFocusTraversalPolicy;
//import jp.or.med.orca.jma_tokutei.common.frame.dialog.DialogFactory;
//import jp.or.med.orca.jma_tokutei.common.frame.dialog.FileSelectDialog;
//import jp.or.med.orca.jma_tokutei.common.frame.dialog.IDialog;
//import jp.or.med.orca.jma_tokutei.common.scene.JScene;
//import jp.or.med.orca.jma_tokutei.common.sql.dao.DaoFactory;
//import jp.or.med.orca.jma_tokutei.common.sql.dao.THokenjyaDao;
//import jp.or.med.orca.jma_tokutei.common.sql.dao.TKensakekaSonotaDao;
//import jp.or.med.orca.jma_tokutei.common.sql.dao.TKensakekaTokuteiDao;
//import jp.or.med.orca.jma_tokutei.common.sql.model.THokenjya;
//import jp.or.med.orca.jma_tokutei.common.sql.model.TKensakekaSonota;
//import jp.or.med.orca.jma_tokutei.common.sql.model.TKensakekaTokutei;
//import jp.or.med.orca.jma_tokutei.common.table.JSimpleTable;
//import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableCellPosition;
//import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableScrollPanel;
//import jp.or.med.orca.jma_tokutei.common.app.JApplication;
//import jp.or.med.orca.jma_tokutei.common.app.JPath;
//
//import javax.swing.JButton;
//import javax.swing.table.DefaultTableCellRenderer;
//import javax.swing.table.TableColumnModel;
//
//import org.apache.log4j.Logger;
//
//import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableCellRowRenderer;
//import jp.or.med.orca.jma_tokutei.common.validate.JValidate;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.dataimport.JImportErrorResultFrameData;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.dataimport.JImportMasterErrorHokenjyaResultFrameData;
//
///**
// * 保険者マスタメンテナンスの一覧のコントロール
// */
//public class JHokenjyaMasterMaintenanceListFrameCtrl extends JHokenjyaMasterMaintenanceListFrame
//{
//	private JHokenjyaMasterMaintenanceEditFrameData validatedData = new JHokenjyaMasterMaintenanceEditFrameData();  //  @jve:decl-index=0:
//
//	private JSimpleTable model = new JSimpleTable();
//	/* フォーカス移動制御 */
//	private JFocusTraversalPolicy focusTraversalPolicy = null;
//
//	private static org.apache.log4j.Logger logger =
//		Logger.getLogger(JHokenjyaMasterMaintenanceListFrameCtrl.class);
//	private IDialog filePathDialog;
//
//	private static jp.or.med.orca.jma_tokutei.common.csv.JCSVReaderStream reader = null;
//	private static jp.or.med.orca.jma_tokutei.common.csv.JCSVWriterStream writer = null;
//
//	private static Vector<Vector<String>> CSVItems = null;
//
//	private static String saveTitle= "csvファイル名を指定して保存";
//	private static String selectTitle= "csvファイル選択";
//	private static String savaDialogTitle= "csvファイルの保存先を選択、ファイル名を指定してください";
//	private static String selectDialogTitle= "csvファイルを選択してください";
//
//	private THokenjyaDao hokenjyaDao = null;
//	private THokenjya hokenjya = new THokenjya();
//
//	private static final String[] TABLE_COLUMNS = {
//		"HKNJANUM", "HKNJANAME", "POST", "ADRS", "BANTI", "TEL", "KIGO", "HON_GAIKYURATE", "HON_NYUKYURATE", "KZK_GAIKYURATE", "KZK_NYUKYURATE",
//		"ITAKU_KBN", "TANKA_KIHON", "HINKETU_CD", "TANKA_HINKETU", "SINDENZU_CD", "TANKA_SINDENZU", "GANTEI_CD", "TANKA_GANTEI",
//		"TANKA_NINGENDOC", "TANKA_HANTEI", "HKNJYA_HISTORY_NO", "HKNJYA_LIMITDATE_START", "HKNJYA_LIMITDATE_END", "YUKOU_FLG"
//		 };
//
//	/**
//	 * デフォルトコンストラクタ
//	 */
//	public JHokenjyaMasterMaintenanceListFrameCtrl()
//	{
//		jPanel_MainArea.add(new JSimpleTableScrollPanel(model));
//		initTable();
//
//		model.addKeyListener(new JEnterEvent(this,jButton_Change));
//
//		// ダブルクリックの処理
//		model.addMouseListener(new JSingleDoubleClickEvent(this,jButton_Change));
//
//		focusInitialize();
//		functionListner();
//	}
//
//	private void focusInitialize(){
//		// focus制御
//		this.focusTraversalPolicy = new JFocusTraversalPolicy();
//		this.setFocusTraversalPolicy(this.focusTraversalPolicy);
//		this.focusTraversalPolicy.setDefaultComponent(model);
//		this.focusTraversalPolicy.addComponent(model);
//		// add s.inoue 2010/02/25
//		this.focusTraversalPolicy.addComponent(this.jButton_Import);
//		this.focusTraversalPolicy.addComponent(this.jButton_Export);
//
//		this.focusTraversalPolicy.addComponent(this.jButton_Add);
//		this.focusTraversalPolicy.addComponent(this.jButton_Change);
//		this.focusTraversalPolicy.addComponent(this.jButton_Delete);
//		this.focusTraversalPolicy.addComponent(jButton_End);
//	}
//
//	// edit s.inoue 2009/12/02
//	private void functionListner(){
//		for (int i = 0; i < focusTraversalPolicy.getComponentSize(); i++) {
//			Component comp = focusTraversalPolicy.getComponent(i);
//			comp.addKeyListener(this);
//		}
//	}
//	/**
//	 * 終了ボタン
//	 */
//	public void pushedEndButton( ActionEvent e )
//	{
//		dispose();
//	}
//
//	// add s.inoue 2010/02/25
//	/**ImportStart↓↓↓↓↓**********************************************************/
//	/**
//	 * 取込ボタン
//	 */
//	public void pushedImportButton( ActionEvent e )
//	{
//		try {
//			filePathDialog = DialogFactory.getInstance().createDialog(this, null);
//
//			filePathDialog.setMessageTitle(selectTitle);
//			filePathDialog.setEnabled(false);
//			filePathDialog.setDialogSelect(true);
//			filePathDialog.setDialogTitle(selectDialogTitle);
//			filePathDialog.setVisible(true);
//
//			// edit s.inoue 2010/03/25
//			if (filePathDialog.getStatus().equals(RETURN_VALUE.CANCEL))
//				return;
//
//			String filePath = filePathDialog.getFilePath();
//			// add s.inoue 2010/03/05
//			File fileImport = new File(filePath);
//			if(!fileImport.exists())
//				return;
//			importCsvData(filePath);
//			initTable();
//		} catch (Exception ex) {
//			JErrorMessage.show("M3207",this);
//			logger.error(ex.getMessage());
//		}
//	}
//
//	// edit s.inoue 2010/03/04
//	/* 取込処理 */
//	private void importCsvData(String filePath){
//
//		RETURN_VALUE retValue = JErrorMessage.show("M3205", this);
//
//		// cancel時
//		if (retValue == RETURN_VALUE.NO){
//			return;
//		}else if (retValue == RETURN_VALUE.YES){
//
//			JImportMasterErrorHokenjyaResultFrameData data = new JImportMasterErrorHokenjyaResultFrameData();
//
//			// CSV読込処理
//			reader = new JCSVReaderStream();
//
//			try {
//				reader.openCSV(filePath,JApplication.CSV_CHARSET);
//			} catch (IOException e) {
//				JErrorMessage.show("M3207",this);
//				logger.error(e.getMessage());
//			}
//
//			CSVItems = reader.readAllTable();
//
//			// db接続
//			getconnectDao();
//
//			try {
//				JApplication.kikanDatabase.Transaction();
//
//				int csvCount = CSVItems.size();
//
//				if(csvCount == 0){
//					JErrorMessage.show("M3211",this);
//					return;
//				}
//
//				// 既存保険者-健診マスタ全削除
//				hokenjyaMasterDelete();
//
//				HashMap hmMap =  new HashMap<String, String>();
//
//				// 検査結果データ取込処理
//				for (int i = 1; i < csvCount; i++) {
//
//					Vector<String> insertRow = new Vector<String>();
//
//					insertRow =CSVItems.get(i);
//
//					// 属性情報取得 CSVデータをローカル変数にセット(「"」を除去したもの)
//					setCSVItemsToDB(data,insertRow);
//
//					// validate処理
//					if (!validateData(data))
//						return;
//
//					hokenjyaMasterRegister(data);
//					if (!hmMap.containsKey(data.CSV_COLUMN_HKNJANUM))
//						kenshinMasterRegister(data);
//					hmMap.put(data.CSV_COLUMN_HKNJANUM,data.CSV_COLUMN_HKNJANUM);
//				}
//
//				JApplication.kikanDatabase.Commit();
//
//				String[] messageParams = {String.valueOf(csvCount-1)};
//				JErrorMessage.show("M3209",this,messageParams);
//			} catch (SQLException e) {
//				try {
//					JApplication.kikanDatabase.rollback();
//				} catch (SQLException e1) {}
//				JErrorMessage.show("M3207",this);
//				logger.error(e.getMessage());
//			}
//		}
//	}
//
//	// edit s.inoue 2010/03/04
//	/* csvデータ取込 */
//	private void setCSVItemsToDB(JImportMasterErrorHokenjyaResultFrameData data,
//			Vector<String> insertRow){
//
//		data.CSV_COLUMN_HKNJANUM = reader.rmQuart(insertRow.get(0));
//		data.CSV_COLUMN_HKNJANAME = reader.rmQuart(insertRow.get(1));
//		data.CSV_COLUMN_POST = reader.rmQuart(insertRow.get(2));
//		data.CSV_COLUMN_ADRS = reader.rmQuart(insertRow.get(3));
//		data.CSV_COLUMN_BANTI = reader.rmQuart(insertRow.get(4));
//		data.CSV_COLUMN_TEL = reader.rmQuart(insertRow.get(5));
//		data.CSV_COLUMN_KIGO = reader.rmQuart(insertRow.get(6));
//		data.CSV_COLUMN_HON_GAIKYURATE = reader.rmQuart(insertRow.get(7));
//		data.CSV_COLUMN_HON_NYUKYURATE = reader.rmQuart(insertRow.get(8));
//		data.CSV_COLUMN_KZK_GAIKYURATE = reader.rmQuart(insertRow.get(9));
//		data.CSV_COLUMN_KZK_NYUKYURATE = reader.rmQuart(insertRow.get(10));
//		data.CSV_COLUMN_ITAKU_KBN = reader.rmQuart(insertRow.get(11));
//		data.CSV_COLUMN_TANKA_KIHON = reader.rmQuart(insertRow.get(12));
//		data.CSV_COLUMN_HINKETU_CD = reader.rmQuart(insertRow.get(13));
//		data.CSV_COLUMN_TANKA_HINKETU = reader.rmQuart(insertRow.get(14));
//		data.CSV_COLUMN_SINDENZU_CD = reader.rmQuart(insertRow.get(15));
//		data.CSV_COLUMN_TANKA_SINDENZU = reader.rmQuart(insertRow.get(16));
//		data.CSV_COLUMN_GANTEI_CD = reader.rmQuart(insertRow.get(17));
//		data.CSV_COLUMN_TANKA_GANTEI = reader.rmQuart(insertRow.get(18));
//		data.CSV_COLUMN_TANKA_NINGENDOC = reader.rmQuart(insertRow.get(19));
//		data.CSV_COLUMN_TANKA_HANTEI = reader.rmQuart(insertRow.get(20));
//		data.CSV_COLUMN_HKNJYA_HISTORY_NO = reader.rmQuart(insertRow.get(21));
//		data.CSV_COLUMN_HKNJYA_LIMITDATE_START = reader.rmQuart(insertRow.get(22));
//		data.CSV_COLUMN_HKNJYA_LIMITDATE_END = reader.rmQuart(insertRow.get(23));
//		data.CSV_COLUMN_YUKOU_FLG = reader.rmQuart(insertRow.get(24));
//	}
//
//	// edit s.inoue 2010/03/04
//	/* validate処理 */
//	private boolean validateData(JImportMasterErrorHokenjyaResultFrameData data) {
//
//		boolean rettanka = false;
//
//		// edit s.inoue 2010/04/27
//		String address = data.CSV_COLUMN_ADRS;
//		if (!JValidate.isAllZenkaku(address)){
//			address = JZenkakuKatakanaToHankakuKatakana.eisukigoHanToZen(address);
//		}
//		address = JValidate.encodeUNICODEtoConvert(address);
//
//		rettanka= validatedData.setHokenjyaName(data.CSV_COLUMN_HKNJANAME)
//			&& validatedData.setHokenjyaNumber(data.CSV_COLUMN_HKNJANUM)
//			&& validatedData.setHonninGairai(data.CSV_COLUMN_HON_GAIKYURATE)
//			// edit s.inoue 2010/08/30
//			&& validatedData.setHinketsuCode(data.CSV_COLUMN_HINKETU_CD)
//			// && validatedData.setHinketsuCode(data.CSV_COLUMN_HON_NYUKYURATE)
//			&& validatedData.setShindenzuCode(data.CSV_COLUMN_SINDENZU_CD)
//			&& validatedData.setGanteiCode(data.CSV_COLUMN_GANTEI_CD)
//			&& validatedData.setTEL(data.CSV_COLUMN_TEL)
//			&& validatedData.setZipcode(data.CSV_COLUMN_POST)
//			&& validatedData.setAddress(address)
//			&& validatedData.setChiban(data.CSV_COLUMN_BANTI)
//			&& validatedData.setKigo(data.CSV_COLUMN_KIGO)
//			&& validatedData.setHonninNyuin(data.CSV_COLUMN_HON_NYUKYURATE)
//			&& validatedData.setKazokuGairai(data.CSV_COLUMN_HON_GAIKYURATE)
//			&& validatedData.setKazokuNyuin(data.CSV_COLUMN_KZK_NYUKYURATE)
//			&& validatedData.setItakuKubunCode(data.CSV_COLUMN_ITAKU_KBN)
//			&& validatedData.setKihonTanka(data.CSV_COLUMN_TANKA_KIHON)
//			&& validatedData.setGanteiTanka(data.CSV_COLUMN_TANKA_GANTEI)
//			&& validatedData.setHinketsuTanka(data.CSV_COLUMN_TANKA_HINKETU)
//			&& validatedData.setNingenDocTanka(data.CSV_COLUMN_TANKA_NINGENDOC)
//			&& validatedData.setSindenzuTanka(data.CSV_COLUMN_TANKA_SINDENZU)
//			&& validatedData.setTankaHantei(data.CSV_COLUMN_TANKA_HANTEI)
//			&& validatedData.setHknjyaHistoryNumber(data.CSV_COLUMN_HKNJYA_HISTORY_NO)
//			&& validatedData.setYukouKigenFrom(data.CSV_COLUMN_HKNJYA_LIMITDATE_START)
//			&& validatedData.setYukouKigenTo(data.CSV_COLUMN_HKNJYA_LIMITDATE_END)
//			&& validatedData.setYukouFlg(data.CSV_COLUMN_YUKOU_FLG);
//		return rettanka;
//	}
//	/*
//	 * 接続処理
//	 */
//	private void getconnectDao(){
//		try {
//			Connection connection = JApplication.kikanDatabase.getMConnection();
//
//			hokenjyaDao = (THokenjyaDao) DaoFactory.createDao(
//					connection,
//					hokenjya);
//
//		} catch (Exception e) {
//			logger.error("機関FDBへの接続処理に失敗しました。");
//			JErrorMessage.show("M3326",this);
//			return;
//		}
//	}
//
//	/**
//	 * 既存保険者,健診マスタ削除
//	 * @throws SQLException
//	 */
//	private void hokenjyaMasterDelete(){
//		// hknjyanumによる削除※履歴を含め削除
//		try {
//			// T_HOKENJA削除
//			hokenjyaDao.delete();
//
//			// T_KENSHINMASTER削除
//			String query = new String("DELETE FROM T_KENSHINMASTER WHERE HKNJANUM <> '99999999'");
//			JApplication.kikanDatabase.sendNoResultQuery(query);
//
//		} catch (SQLException e) {
//			JErrorMessage.show("M3208",this);
//			e.printStackTrace();
//			logger.error(e.getMessage());
//		}
//	}
//
//	private void kenshinMasterRegister(JImportMasterErrorHokenjyaResultFrameData data)
//		throws SQLException
//	{
//
//		try {
//				// 新規追加の場合はさらに健診項目マスタへの追加も行う
//				ArrayList<Hashtable<String, String>> resultKenshin = new ArrayList<Hashtable<String, String>>();
//				Hashtable<String, String> resultItemKenshin = new Hashtable<String, String>();
//				StringBuffer buffer = new StringBuffer(
//						"SELECT * FROM T_KENSHINMASTER WHERE HKNJANUM = "
//								+ JQueryConvert.queryConvert("99999999"));
//
//				resultKenshin = JApplication.kikanDatabase.sendExecuteQuery(buffer.toString());
//
//				for (int i = 0; i < resultKenshin.size(); i++) {
//					resultItemKenshin = resultKenshin.get(i);
//
//					buffer = new StringBuffer("INSERT INTO T_KENSHINMASTER ( "
//							+ "HKNJANUM,KOUMOKU_CD,KOUMOKU_NAME,DATA_TYPE,MAX_BYTE_LENGTH,XML_PATTERN,"
//							+ "XML_DATA_TYPE,XML_KENSA_CD,OBSERVATION,AUTHOR,AUTHOR_KOUMOKU_CD,"
//							+ "KENSA_GROUP,KENSA_GROUP_CD,KEKKA_OID,KOUMOKU_OID,HISU_FLG,KAGEN,"
//							+ "JYOUGEN,DS_JYOUGEN,JS_JYOUGEN,DS_KAGEN,JS_KAGEN,KIJYUNTI_HANI,"
//							+ "TANI,KENSA_HOUHOU,TANKA_KENSIN,BIKOU,XMLITEM_SEQNO"
//							+ " )VALUES( "
//							+ JQueryConvert.queryConvertAppendComma(validatedData.getHokenjyaNumber())
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("KOUMOKU_CD"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("KOUMOKU_NAME"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("DATA_TYPE"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("MAX_BYTE_LENGTH"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("XML_PATTERN"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("XML_DATA_TYPE"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("XML_KENSA_CD"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("OBSERVATION"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("AUTHOR"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("AUTHOR_KOUMOKU_CD"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("KENSA_GROUP"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("KENSA_GROUP_CD"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("KEKKA_OID"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("KOUMOKU_OID"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("HISU_FLG"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("KAGEN"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("JYOUGEN"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("DS_JYOUGEN"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("JS_JYOUGEN"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("DS_KAGEN"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("JS_KAGEN"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("KIJYUNTI_HANI"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("TANI"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("KENSA_HOUHOU"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("TANKA_KENSIN"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("BIKOU"))
//							+ JQueryConvert.queryConvert(resultItemKenshin.get("XMLITEM_SEQNO")) +
//							")");
//
//					JApplication.kikanDatabase.sendNoResultQuery(buffer.toString());
//				}
//		} catch (SQLException f) {
//			f.printStackTrace();
//			JErrorMessage.show("M3212", this);
//			try {
//				JApplication.kikanDatabase.rollback();
//				return;
//			} catch (SQLException g) {
//				g.printStackTrace();
//				JErrorMessage.show("M0000", this);
//				System.exit(1);
//			}
//		}
//	}
//
//	/**
//	 * CSVデータ登録
//	 * @throws SQLException
//	 */
//	private void hokenjyaMasterRegister(JImportMasterErrorHokenjyaResultFrameData data)
//		throws SQLException
//	{
//		StringBuffer buffer = new StringBuffer();
//
//		String address = JValidate.encodeUNICODEtoConvert(validatedData.getAddress());
//		String tiban = JValidate.encodeUNICODEtoConvert(validatedData.getChiban());
//
//		buffer.append("INSERT INTO T_HOKENJYA (");
//		buffer.append("HKNJANUM,HKNJANAME,POST,ADRS,BANTI,TEL,");
//		buffer.append("KIGO,HON_GAIKYURATE,HON_NYUKYURATE,");
//		buffer.append("KZK_GAIKYURATE,KZK_NYUKYURATE,ITAKU_KBN,");
//		buffer.append("TANKA_KIHON,HINKETU_CD,TANKA_HINKETU,");
//		buffer.append("SINDENZU_CD,TANKA_SINDENZU,GANTEI_CD,");
//		buffer.append("TANKA_GANTEI,TANKA_NINGENDOC,TANKA_HANTEI,");
//		buffer.append("HKNJYA_HISTORY_NO,");
//		buffer.append("HKNJYA_LIMITDATE_START,HKNJYA_LIMITDATE_END,");
//		buffer.append("YUKOU_FLG)");
//		buffer.append("VALUES ( "
//			+ JQueryConvert.queryConvertAppendComma(validatedData.getHokenjyaNumber())
//			+ JQueryConvert.queryConvertAppendBlankAndComma(validatedData.getHokenjyaName())
//			+ JQueryConvert.queryConvertAppendBlankAndComma(validatedData.getZipcode())
//			+ JQueryConvert.queryConvertAppendBlankAndComma(address)
//			+ JQueryConvert.queryConvertAppendBlankAndComma(tiban)
//			+ JQueryConvert.queryConvertAppendBlankAndComma(validatedData.getTEL())
//			+ JQueryConvert.queryConvertAppendComma(validatedData.getCode())
//			+ JQueryConvert.queryConvertAppendComma(validatedData.getHonninGairai())
//			+ JQueryConvert.queryConvertAppendComma(validatedData.getHonninNyuin())
//			+ JQueryConvert.queryConvertAppendComma(validatedData.getKazokuGairai())
//			+ JQueryConvert.queryConvertAppendComma(validatedData.getKazokuNyuin())
//			+ JQueryConvert.queryConvertAppendComma(validatedData.getItakuKubun())
//			+ JQueryConvert.queryConvertAppendComma(validatedData.getKihonTanka())
//			+ JQueryConvert.queryConvertAppendComma(validatedData.getHinketsuCode())
//			+ JQueryConvert.queryConvertAppendComma(validatedData.getHinketsuTanka())
//			+ JQueryConvert.queryConvertAppendComma(validatedData.getShindenzuCode())
//			+ JQueryConvert.queryConvertAppendComma(validatedData.getSindenzuTanka())
//			+ JQueryConvert.queryConvertAppendComma(validatedData.getGanteiCode())
//			+ JQueryConvert.queryConvertAppendComma(validatedData.getGanteiTanka())
//			+ JQueryConvert.queryConvertAppendComma(validatedData.getNingenDocTanka())
//			+ JQueryConvert.queryConvertAppendComma(validatedData.getTankaHantei())
//			+ JQueryConvert.queryConvertAppendComma(validatedData.getHknjyaHistoryNumber())
//			+ JQueryConvert.queryConvertAppendComma(validatedData.getYukouKigenFrom())
//			+ JQueryConvert.queryConvertAppendComma(validatedData.getYukouKigenTo())
//			+ JQueryConvert.queryConvert(validatedData.getYukouFlg())
//			+ ")");
//
//		JApplication.kikanDatabase.sendNoResultQuery(buffer.toString());
//
//	}
//	/**ImportEnd↑↑↑↑↑**********************************************************/
//
//	/**ExportStart↓↓↓↓↓**********************************************************/
//	// add s.inoue 2010/02/25
//	/**
//	 * 書出ボタン
//	 */
//	public void pushedExportButton( ActionEvent e )
//	{
//		try {
//			String saveFileName = JPath.createDirectoryUniqueName("HokenjyaMaster");
//
//			String defaltPath = JPath.getDesktopPath() +
//			File.separator +
//			saveFileName;
//
//			filePathDialog = DialogFactory.getInstance().createDialog(this, defaltPath);
//			filePathDialog.setMessageTitle(saveTitle);
//			filePathDialog.setEnabled(false);
//			filePathDialog.setDialogSelect(false);
//			filePathDialog.setDialogTitle(savaDialogTitle);
//			filePathDialog.setVisible(true);
//
//			// edit s.inoue 2010/03/25
//			if (filePathDialog.getStatus().equals(RETURN_VALUE.CANCEL))
//				return;
//
//			String filePath = filePathDialog.getFilePath();
//			if (filePath.equals(""))
//				return;
//			exportCsvData(filePath);
//
//		} catch (Exception ex) {
//			JErrorMessage.show("M3206", this);
//			logger.error(ex.getMessage());
//		}
//	}
//
//	/* export処理 */
//	private void exportCsvData(String filePath){
//		JImportMasterErrorHokenjyaResultFrameData data = new JImportMasterErrorHokenjyaResultFrameData();
//
//		// CSV読込処理
//		writer = new JCSVWriterStream();
//
//		try {
//			writer.writeTable(getExportData());
//			writer.saveCSV(filePath,JApplication.CSV_CHARSET);
//		} catch (IOException e) {
//			JErrorMessage.show("M3206", this);
//			logger.error(e.getMessage());
//		}
//	}
//
//	/* DBよりデータ取得*/
//	private Vector<Vector<String>> getExportData(){
//
//		Vector<Vector<String>> newTable = new Vector<Vector<String>>();
//
//		ArrayList<Hashtable<String, String>> result
//			= new ArrayList<Hashtable<String, String>>();
//
//		// add s.inoue 2010/03/04
//		StringBuilder sb = new StringBuilder();
//		sb.append(" SELECT HKNJANUM, HKNJANAME, POST, ADRS, BANTI, TEL, KIGO, HON_GAIKYURATE, HON_NYUKYURATE,");
//		sb.append(" KZK_GAIKYURATE, KZK_NYUKYURATE, ITAKU_KBN, TANKA_KIHON, HINKETU_CD, TANKA_HINKETU, SINDENZU_CD,");
//		sb.append(" TANKA_SINDENZU, GANTEI_CD, TANKA_GANTEI, TANKA_NINGENDOC, TANKA_HANTEI, HKNJYA_HISTORY_NO, HKNJYA_LIMITDATE_START, HKNJYA_LIMITDATE_END, YUKOU_FLG ");
//		sb.append(" FROM T_HOKENJYA ");
//		sb.append(" ORDER BY HKNJANUM,HKNJYA_HISTORY_NO DESC");
//
//		try {
//			result = JApplication.kikanDatabase.sendExecuteQuery(sb.toString());
//		} catch (SQLException e) {
//			e.printStackTrace();
//			return null;
//		}
//
//		Hashtable<String, String> resultItem = new Hashtable<String, String>();
//		for( int i=0; i<result.size(); ++i )
//		{
//			resultItem = result.get(i);
//
//			Vector<String> data = new Vector<String>();
//
//			for (int j=0; j<resultItem.size(); ++j){
//				// 空要素
//				String exportItem =resultItem.get(TABLE_COLUMNS[j]).trim();
//				if (TABLE_COLUMNS[j].equals("ADRS") ||
//						TABLE_COLUMNS[j].equals("BANTI"))
//					exportItem = JValidate.encodeUNICODEtoConvert(exportItem).trim();
//
//				data.add(exportItem);
//			}
//
//			// header設定
//			if (i==0){
//				Vector colmn = new Vector<String>();
//				List l = java.util.Arrays.asList(TABLE_COLUMNS);
//				for (Iterator item = l.iterator(); item.hasNext();) {
//					colmn.add((String)item.next());
//				}
//				newTable.add(colmn);
//			}
//			newTable.add(data);
//		}
//		return newTable;
//	}
//	/**ExportEnd↑↑↑↑↑**********************************************************/
//
////	/**
////	 * マスタ存在判定
////	 */
////	private boolean existsMasterData(JImportMasterErrorResultFrameData Data) {
////
////		boolean ret = false;
////		int resultCount = 0;
////
////		try {
////			resultCount = hokenjyaDao.selectByCountUniqueKey(
////						Data.CSV_COLUMN_CSV_COLUMN_HKNJANUM);
////		} catch (Exception e) {
////			e.printStackTrace();
////			logger.error(e.getMessage());
////		}
////
////		if (resultCount > 0) {
////			ret=true;
////		}
////
////		return ret;
////	}
//
//	/**
//	 * 追加ボタン
//	 */
//	public void pushedAddButton( ActionEvent e )
//	{
//		JScene.CreateDialog(this, new JHokenjyaMasterMaintenanceEditFrameCtrl(),new WindowRefreshEvent());
//	}
//
//	/**
//	 * 変更ボタン
//	 */
//	public void pushedChangeButton( ActionEvent e )
//	{
//		if( model.getSelectedRowCount() > 0 )
//		{
//			JScene.CreateDialog(this, new JHokenjyaMasterMaintenanceEditFrameCtrl(
//					// edit s.inoue 2010/07/07
//					(String)model.getData(model.getDoubleClickedSelectedRow(), 0)),new WindowRefreshEvent());
//		}
//	}
//
//	/**
//	 * 削除ボタン
//	 */
//	public void pushedDeleteButton( ActionEvent e )
//	{
//		if ( model.getSelectedRowCount() <= 0 )
//		{
//			return;
//		}
//
//		RETURN_VALUE Ret = jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage.show("M3201", this);
//
//		if(Ret == RETURN_VALUE.YES)
//		{
//			int[] selectedRow = model.getSelectedRows();
//
//			for( int i = 0;i < model.getSelectedRowCount();i++)
//			{
//				try{
//					JApplication.kikanDatabase.Transaction();
//
//					//T_HOKENJYAテーブルから削除
//					String Query = new String("DELETE FROM T_HOKENJYA WHERE HKNJANUM = " +
//							JQueryConvert.queryConvert((String)model.getData(selectedRow[i], 0)));
//					JApplication.kikanDatabase.sendNoResultQuery(Query);
//
//					//T_KENSHINMASTERテーブルから削除
//					Query = new String("DELETE FROM T_KENSHINMASTER WHERE HKNJANUM = " +
//							JQueryConvert.queryConvert((String)model.getData(selectedRow[i], 0)));
//					JApplication.kikanDatabase.sendNoResultQuery(Query);
//
//					JApplication.kikanDatabase.Commit();
//				}catch(SQLException f){
//					f.printStackTrace();
//					jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage.show("M3202", this);
//					try{
//						JApplication.kikanDatabase.rollback();
//					}catch(SQLException g){
//						jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage.show("M0000", this);
//						g.printStackTrace();
//						System.exit(1);
//					}
//					return;
//				}
//			}
//
//			initTable();
//		}
//	}
//
//	/**
//	 * テーブルの初期化を行う
//	 */
//	public void initTable()
//	{
//		ArrayList<Hashtable<String,String>> Result = new ArrayList<Hashtable<String,String>>();
//		Hashtable<String,String> ResultItem = new Hashtable<String,String>();
//		String query
//			= new String(
//				"SELECT DISTINCT HKNJANUM,HKNJANAME,POST,ADRS,BANTI,TEL FROM T_HOKENJYA ORDER BY HKNJANUM DESC");
//				// ",KIGO,HON_GAIKYURATE,HON_NYUKYURATE,KZK_GAIKYURATE,KZK_NYUKYURATE,ITAKU_KBN,TANKA_KIHON," +
//				// "HINKETU_CD,TANKA_HINKETU,SINDENZU_CD,TANKA_SINDENZU,GANTEI_CD,TANKA_GANTEI FROM T_HOKENJYA ORDER BY HKNJANUM DESC ");
//
//		String[] row = new String[5];
//
//		model.setPreferedColumnWidths(new int[]{100, 200, 100, 300, 150});
//
//		// ヘッダの設定
//		model.clearAllColumn();
//		model.clearAllRow();
//
//		model.addHeader("保険者番号");
//		model.addHeader("保険者名称");
//		model.addHeader("郵便番号");
//		model.addHeader("所在地");
//		model.addHeader("電話番号");
//
//		// セルの編集禁止
//		jp.or.med.orca.jma_tokutei.common.table.JSimpleTableCellPosition iSetColumnList[] = {
//				new JSimpleTableCellPosition(-1,-1)
//				};
//
//		model.setCellEditForbid(iSetColumnList);
//		// edit s.inoue 2009/10/31
//		model.setAutoCreateRowSorter(true);
//		model.refreshTable();
//
//		try{
//			Result = JApplication.kikanDatabase.sendExecuteQuery(query);
//			// edit s.inoue 2010/02/25
//			String hknjyaNum = "";
//			String prehknjyaNum = "";
//
//			for( int i = 0;i < Result.size();i++ )
//			{
//				ResultItem = Result.get(i);
//				// edit s.inoue 2010/02/25
//				hknjyaNum = ResultItem.get("HKNJANUM");
//
//				if (!hknjyaNum.equals(prehknjyaNum)){
//					row[0] = new String(hknjyaNum);
//					row[1] = new String(ResultItem.get("HKNJANAME"));
//					row[2] = new String(ResultItem.get("POST"));
//					row[3] = new String(ResultItem.get("ADRS"));
//					row[4] = new String(ResultItem.get("TEL"));
//					model.addData(row);
//				}
//
//				// edit s.inoue 2010/02/25
//				prehknjyaNum = hknjyaNum;
//			}
//
//		}catch(SQLException f){
//			f.printStackTrace();
//
//			JErrorMessage.show("M3203", this);
//			dispose();
//			return;
//		}
//
//		for( int i = 0;i < Result.size();i++ )
//		{
//			ResultItem = Result.get(i);
//		}
//
//		// add ver2 s.inoue 2009/05/08
//		TableColumnModel columns = model.getColumnModel();
//        for(int i=0;i<columns.getColumnCount();i++) {
//
//            columns.getColumn(i).setCellRenderer(new JSimpleTableCellRowRenderer(
//                (DefaultTableCellRenderer)model.getDefaultRenderer(model.getColumnClass(i))));
//        }
//
//        // edit s.inoue 2009/10/20 move
//        // 初期選択
//		if(model.getRowCount() > 0)
//		{
//			model.setRowSelectionInterval(0, 0);
//		}else{
//			jButton_Add.requestFocus();
//		}
//	}
//
//	public void actionPerformed( ActionEvent e )
//	{
//		Object source = e.getSource();
//		if( source == jButton_End )
//		{
//			logger.info(jButton_End.getText());
//			pushedEndButton( e );
//		}
//		else if (source == jButton_Import)
//		{
//			logger.info(jButton_Import.getText());
//			pushedImportButton(e);
//		}
//		else if (source == jButton_Export)
//		{
//			logger.info(jButton_Export.getText());
//			pushedExportButton(e);
//		}
//		else if( source == jButton_Add )
//		{
//			logger.info(jButton_Add.getText());
//			pushedAddButton( e );
//		}
//		else if( source == jButton_Delete )
//		{
//			logger.info(jButton_Delete.getText());
//			pushedDeleteButton( e );
//		}
//		else if( source == jButton_Change )
//		{
//			logger.info(jButton_Change.getText());
//			pushedChangeButton( e );
//		}
//	}
//
//	@Override
//	public void keyPressed(KeyEvent keyEvent) {
//		switch(keyEvent.getKeyCode()){
//		case KeyEvent.VK_F1:
//			logger.info(jButton_End.getText());
//			pushedEndButton( null );break;
//			// edit s.inoue 2010/03/24
//		case KeyEvent.VK_F4:
//			logger.info(jButton_Import.getText());
//			pushedImportButton(null);break;
//		case KeyEvent.VK_F5:
//			logger.info(jButton_Export.getText());
//			pushedExportButton(null);break;
//
//		case KeyEvent.VK_F9:
//			logger.info(jButton_Add.getText());
//			pushedAddButton( null );break;
//		case KeyEvent.VK_F11:
//			logger.info(jButton_Change.getText());
//			pushedChangeButton(null);break;
//		case KeyEvent.VK_F12:
//			logger.info(jButton_Delete.getText());
//			pushedDeleteButton(null);break;
//		}
//	}
//	/**
//	 * 遷移先の画面から戻ってきた場合
//	 */
//	public class WindowRefreshEvent extends WindowAdapter
//	{
//		public void windowClosed(WindowEvent e)
//		{
//			//テーブルの再読み込みを行う
//			initTable();
//		}
//	}
//}
//
//
////Source Code Version Tag System v1.00  -- Do not remove --
////Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}
//

