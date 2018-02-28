package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.kenshin;

import javax.swing.AbstractCellEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyAdapter;
import java.awt.event.InputEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.List;
import java.util.Calendar;
import java.util.Vector;
import java.util.regex.Pattern;
import java.math.BigDecimal;
import java.awt.Dimension;
import org.apache.log4j.Logger;

import com.lowagie.text.pdf.TextField;

import jp.or.med.orca.jma_tokutei.common.component.ExtendedComboBox;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedEditorPane;
//import jp.or.med.orca.jma_tokutei.common.component.ExtendedTextArea;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedTextField;
import jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode;
import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.convert.JYearAge;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
import jp.or.med.orca.jma_tokutei.common.event.JEnterEvent;
import jp.or.med.orca.jma_tokutei.common.focus.JFocusTraversalPolicy;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import jp.or.med.orca.jma_tokutei.common.frame.dialog.IKekkaRegisterInputDialog;
import jp.or.med.orca.jma_tokutei.common.frame.dialog.JKekkaRegisterInputDialogFactory;
import jp.or.med.orca.jma_tokutei.common.sql.dao.DaoFactory;
import jp.or.med.orca.jma_tokutei.common.sql.dao.TKensakekaSonotaDao;
import jp.or.med.orca.jma_tokutei.common.sql.dao.TKensakekaTokuteiDao;
import jp.or.med.orca.jma_tokutei.common.sql.dao.TKojinDao;
import jp.or.med.orca.jma_tokutei.common.sql.dao.TNayoseDao;
import jp.or.med.orca.jma_tokutei.common.sql.model.TKensakekaSonota;
import jp.or.med.orca.jma_tokutei.common.sql.model.TKensakekaTokutei;
import jp.or.med.orca.jma_tokutei.common.sql.model.TKojin;
import jp.or.med.orca.jma_tokutei.common.sql.model.TNayose;
import jp.or.med.orca.jma_tokutei.common.table.JComboCellEditor;
import jp.or.med.orca.jma_tokutei.common.table.JSimpleTable;
import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableCellPosition;
import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableCellRendererData;
import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableScrollPanel;
import jp.or.med.orca.jma_tokutei.common.util.FiscalYearUtil;
import jp.or.med.orca.jma_tokutei.common.validate.JValidate;
import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.component.ImeController;

/**
 * 結果登録
 */
public class JKekkaRegisterFrameCtrl extends JKekkaRegisterFrame implements ItemListener,KeyListener {

	private static final int[] DEFAULT_COLUMNS_WIDTH = new int[] {
		0, 140, 140, 80, 120, 120, 80,
				80, 80, 80, 80, 50, 120 };
	private static final String COLUMN_NAME_HISU_FLG = "HISU_FLG";
	private static final String COLUMN_NAME_JYOUGEN = "JYOUGEN";
	private static final String COLUMN_NAME_KAGEN = "KAGEN";
	private static final String COLUMN_NAME_KOMENTO = "KOMENTO";
	private static final String COLUMN_NAME_HANTEI = "HANTEI";
	private static final String COLUMN_NAME_H_L = "H_L";
	private static final String COLUMN_NAME_JISI_KBN = "JISI_KBN";
	private static final String COLUMN_NAME_JS_JYOUGEN = "JS_JYOUGEN";
	private static final String COLUMN_NAME_JS_KAGEN = "JS_KAGEN";
	private static final String COLUMN_NAME_DS_JYOUGEN = "DS_JYOUGEN";
	private static final String COLUMN_NAME_DS_KAGEN = "DS_KAGEN";
	private static final String COLUMN_NAME_CODE_NUM = "CODE_NUM";
	private static final String COLUMN_NAME_CODE_NAME = "CODE_NAME";
	private static final String COLUMN_NAME_KEKA_TI = "KEKA_TI";
	private static final String COLUMN_NAME_MAX_BYTE_LENGTH = "MAX_BYTE_LENGTH";
	private static final String COLUMN_NAME_DATA_TYPE = "DATA_TYPE";
	private static final String COLUMN_NAME_KENSA_HOUHOU = "KENSA_HOUHOU";
	private static final String COLUMN_NAME_KOUMOKUNAME = "KOUMOKUNAME";
	private static final String COLUMN_NAME_KOUMOKUCD = "KOUMOKUCD";

	// add s.inoue 2009/11/11
	private static final int COLUMN_IDX_KOUMOKUCD = 0;
	private static final int COLUMN_IDX_CODE_NAME = 1;
	private static final int COLUMN_IDX_CODE_KENSA_HOUHOU = 2;
	// edit s.inoue 2009/11/11
//	private static final int COLUMN_IDX_KEKKA_VALUE = 3;
	private static final int COLUMN_IDX_KEKKA_NUM = 3;
	private static final int COLUMN_IDX_KEKKA_CODE = 4;
	private static final int COLUMN_IDX_KEKKA_STR = 5;

	private static final int COLUMN_IDX_JISIKBN = 6;
	private static final int COLUMN_IDX_DS_KAGEN = 7;
	private static final int COLUMN_IDX_DS_JYOUGEN = 8;
	private static final int COLUMN_IDX_JS_KAGEN = 9;
	private static final int COLUMN_IDX_JS_JYOUGEN = 10;
	private static final int COLUMN_IDX_HL = 11;
	private static final int COLUMN_IDX_HANTEI = 12;
	private static final int COLUMN_IDX_KOMENTO = 13;

	private static final String CODE_SEIKATU_KAIZEN = "9N801000000000011";
	private static final String CODE_HOKEN_SHIDOU = "9N806000000000011";
	private static final String CODE_SAIKETSU_ZIKAN = "9N141000000000011";
	private static final String CODE_ISHINOHANDAN_1 = "9N501000000000011";
	private static final String CODE_ISHINOHANDAN_2 = "9N506000000000011";
	private static final String CODE_ISHINOHANDAN_3 = "9N511000000000049";
	private static final String CODE_ISHINOHANDAN_4 = "9N516000000000049";
	private static final String CODE_TAIJU = "9N006000000000001";
	private static final String CODE_SHINCHOU = "9N001000000000001";
	private static final String CODE_BMI = "9N011000000000001";

	public final static int INSERT_MODE = 1;
	public final static int UPDATE_MODE = 2;
	private static final String strHighMessageValue = "上限値";
	private static final String strLowMessageValue = "下限値";

	private static Logger logger = Logger.getLogger(JKekkaRegisterFrameCtrl.class);
	// eidt s.inoue 2011/08/01
	private boolean yearOld_flg = false;

	/**
	 * 入力ダイアログ
	 */
	private IKekkaRegisterInputDialog dialogs;

	/**
	 * テーブルの"結果(文字列)"欄にて 編集終了時に入力用ダイアログを表示するための DefaultCellEditorクラス
	 */
	private class PopupWindowShowCellEditor extends DefaultCellEditor {
		/**
		 * DefaultCellEditor セルエディタリスナ
		 */
		private PopupWindowCellEditorListener cellEditorListener;
		private ImeController imeController = null;
		/**
		 * コンストラクタ
		 * @param parent 健診結果データ入力画面
		 * @param textField 結果(文字列)セルのエディタ
		 * @param 選択中セルのカラムインデックス
		 */
		public PopupWindowShowCellEditor(JKekkaRegisterFrameCtrl parent,
				JTextField textField,int index) {
			super(textField);

			// add s.inoue 2009/10/14
			imeController = new ImeController();
			imeController.addFocusListenerForCharcterSubsets(textField, ImeMode.IME_ZENKAKU);
			cellEditorListener = new PopupWindowCellEditorListener(parent,index);
			setClickCountToStart(1);
		}
	}

	// add s.inoue 2009/12/22
	// textfieldからtextareaへと移行
	public class PopupWindowShowTextAreaCellEditor extends AbstractCellEditor implements
	TableCellEditor, TableCellRenderer {
		  private PopupWindowCellEditorListener cellEditorListener;
		  private ImeController imeController = null;

		  private JTextArea textArea;
		  private JScrollPane scroll;

		  public PopupWindowShowTextAreaCellEditor(
				JKekkaRegisterFrameCtrl parent,
				ExtendedEditorPane textAreas,
					int index) {
			// edit s.inoue 2009/12/24
			// textArea = new JTextArea();
			scroll = new JScrollPane(textAreas,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

			// add s.inoue 2009/10/14
			imeController = new ImeController();
			imeController.addFocusListenerForCharcterSubsets(scroll, ImeMode.IME_ZENKAKU);
			cellEditorListener = new PopupWindowCellEditorListener(parent,index);
		  }

		  public JTextArea getTextArea(){
		    return textArea;
		  }

		  public Object getCellEditorValue() {
		    return textArea.getText();
		  }

		  public boolean shouldSelectCell(EventObject anEvent) {
		    return true;
		  }

		  public boolean stopCellEditing() {
		    fireEditingStopped();
		    return true;
		  }

		  public void cancelCellEditing() {
		    fireEditingCanceled();
		  }

		  public Component getTableCellEditorComponent(JTable table, Object value,
		      boolean isSelected, int row, int column) {
			if (textArea == null)
				return null;
		    textArea.setText((value != null) ? value.toString() : "");
		    textArea.setCaretPosition(textArea.getText().length());
		    return scroll;
		  }

		  public Component getTableCellRendererComponent(JTable table,
		   Object value, boolean isSelected, boolean hasFocus, int row, int column){
		    textArea.setText((value != null) ? value.toString() : "");
		    textArea.setCaretPosition(textArea.getText().length());
		    return scroll;
		  }
	}

	/**
	 * テーブルの"結果(文字列)"欄DefaultCellEditorの 編集完了/ｷｬﾝｾﾙリスナークラス
	 */
	private class PopupWindowCellEditorListener implements CellEditorListener {

		/**
		 * 健診結果データ入力画面
		 */
		private JKekkaRegisterFrameCtrl parent;

		/**
		 * 入力ダイアログ
		 */
		private IKekkaRegisterInputDialog dialog;

		/**
		 * コンストラクタ
		 * @param parent 健診結果データ入力画面
		 * @param index 選択中セルのカラムインデックス
		 */
		public PopupWindowCellEditorListener(JKekkaRegisterFrameCtrl parent,int index) {
			this.parent = parent;
			dialogs = JKekkaRegisterInputDialogFactory.createDialog(parent,index,"");
		}

		/**
		 * 編集キャンセルイベントコールバック
		 * @param changeEvent 変更イベント
		 */
		public void editingCanceled(ChangeEvent changeEvent) {
		}

		/**
		 * 編集完了イベントコールバック
		 * @param changeEvent 変更イベント
		 */
		public void editingStopped(ChangeEvent changeEvent) {

			// 健診結果データ入力画面のテーブルから現在選択されているセルのデータを取得
			int columnIndex = parent.model.getSelectedColumn();
			int rowIndex = parent.model.getSelectedRow();
			String kekkaMojiretsu = (String) parent.model.getData(rowIndex,columnIndex);

			// "結果(文字列)"入力ダイアログのテキストエリアに選択セルデータをセット
			dialog.setText(kekkaMojiretsu);
			// "結果(文字列)"入力ダイアログ表示
			dialog.setVisible(true);

			// edit s.inoue 2009/12/06
			// if (dialog.getStatus() != RETURN_VALUE.OK)
			if (dialog.getStatus() != RETURN_VALUE.YES)
				return;
			// "結果(文字列)"入力ダイアログから入力された 文字列を選択セルにセット
			parent.model.setData(dialog.getText(), rowIndex, columnIndex);
		}
	}

	/**
	 * 総合コメント欄キーリスナクラス
	 */
	private class SougouCommentKeyListener implements KeyListener {

		/**
		 * 健診結果データ入力画面
		 */
		private JKekkaRegisterFrameCtrl parent;

		/**
		 * 入力ダイアログ
		 */
		private IKekkaRegisterInputDialog dialog;

		/**
		 * コンストラクタ
		 * @param parent 健診結果データ入力画面
		 */
		public SougouCommentKeyListener(JKekkaRegisterFrameCtrl parent) {
			this.parent = parent;
			// ダイアログを生成
			// edit s.inoue 2010/06/10
			String comment = jEditorPane_Comment.getText();
			dialog = JKekkaRegisterInputDialogFactory.createDialog(parent, 12, comment);
		}

		public void keyPressed(KeyEvent keyEvent) {
			// Enterキー押下時
			if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {

				String comment = jEditorPane_Comment.getText();
				dialog = JKekkaRegisterInputDialogFactory.createDialog(parent, 12, comment);
				// Shiftキーが一緒に押されたか?
				int modfiersKey = keyEvent.getModifiersEx();
				if ((modfiersKey & InputEvent.CTRL_DOWN_MASK) == 0)
					return;

				// "結果(文字列)"入力ダイアログのテキストエリアに選択セルデータをセット
				dialog.setText(comment);
				// "結果(文字列)"入力ダイアログ表示
				dialog.setVisible(true);
				if (dialog.getStatus() != RETURN_VALUE.YES)
					return;
				// "結果(文字列)"入力ダイアログから入力された
				// 文字列を選択セルにセット

				/*
				 * Modified 2008/03/13 若月 取得したテキストを、現在の入力内容に追加 するよう変更。
				 */
				// edit s.inoue 2010/06/10
				// parent.jEditorPane_Comment.setText(comment + dialog.getText());
				parent.jEditorPane_Comment.setText(dialog.getText());
			}

			// del s.inoue 2010/04/27
//			else if(keyEvent.getKeyCode() == KeyEvent.VK_F1){
//				pushedEndButton(null);
//			}
//			else if(keyEvent.getKeyCode() == KeyEvent.VK_F2){
//				pushedClearButton(null);
//			}
//			else if(keyEvent.getKeyCode() == KeyEvent.VK_F12){
//				pushedRegisterButton(null);
//			}

//			// Enterキー押下時
//			// if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
//			// add s.inoue 2009/12/03
//			switch(keyEvent.getKeyCode()){
//				case KeyEvent.VK_ENTER:
//					// Shiftキーが一緒に押されたか?
//					int modfiersKey = keyEvent.getModifiersEx();
//					if ((modfiersKey & InputEvent.CTRL_DOWN_MASK) == 0)
//						return;
//					String comment = jEditorPane_Comment.getText();
//					// "結果(文字列)"入力ダイアログのテキストエリアに選択セルデータをセット
//					dialog.setText(comment);
//					// "結果(文字列)"入力ダイアログ表示
//					dialog.setVisible(true);
//					// edit s.inoue 2009/12/06
//					if (dialog.getStatus() != RETURN_VALUE.YES)
//					// if (dialog.getStatus() != RETURN_VALUE.OK)
//						return;
//					/*
//					 * 取得したテキストを、現在の入力内容に追加 するよう変更。
//					 */
//					parent.jEditorPane_Comment.setText(comment + dialog.getText());
//					break;
//// del s.inoue 2009/12/21
////				case KeyEvent.VK_F1:
////					pushedEndButton(null);break;
////				case KeyEvent.VK_F2:
////					pushedClearButton(null);break;
////				case KeyEvent.VK_F12:
////					pushedRegisterButton(null);break;
//				}
		}

		public void keyReleased(KeyEvent keyEvent) {
		}
		public void keyTyped(KeyEvent keyEvent) {
		}
	}

	/**
	 * 結果(コード)欄のDefaultCellEditorクラスを追加
	 */
	private class InputDigitHalfUpCellEditor extends DefaultCellEditor {
		private ImeController imeController = null;
		/**
		 * コンストラクタ
		 * @param textField セルのDefaultEditor
		 */
		public InputDigitHalfUpCellEditor(final JTextField textField) {
			super(textField);

			// add s.inoue 2009/10/14
			imeController = new ImeController();
			imeController.addFocusListenerForCharcterSubsets(textField, ImeMode.IME_OFF);

			// 編集リスナを追加
			addCellEditorListener(new CellEditorListener() {

				private final String DIGIT_FORMAT_REGEXP = "[#\\.0]+";

				/**
				 * 編集キャンセルイベントコールバック
				 * @param changeEvent 変更イベント
				 */
				public void editingCanceled(ChangeEvent changeEvent) {
				}

				/**
				 * 編集完了イベントコールバック
				 * @param changeEvent 変更イベント
				 */
				public void editingStopped(ChangeEvent changeEvent) {

					String textValue = textField.getText();
					if ((textValue == null) || (textValue.length() == 0))
						return;

					// 現在選択されているセルの行・列インデックスを取得
					int columnIndex = model.getSelectedColumn();
					int rowIndex = model.getSelectedRow();

					// フォーマット文字列を取得する
					Map<String, String> recordMap = tableResult.get(rowIndex);
					String format = recordMap.get(COLUMN_NAME_MAX_BYTE_LENGTH);

					// フォーマット文字列の妥当性を検証
					if (!isValidFormat(format))
						return;

					// add s.inoue 2012/07/02
					// 対応検査項目
					// 撮影年月日
					boolean dayformat = false;
					if (recordMap.get(COLUMN_NAME_KOUMOKUCD).equals("9N211161100000049") ||
							recordMap.get(COLUMN_NAME_KOUMOKUCD).equals("9N226161100000049") ||
							recordMap.get(COLUMN_NAME_KOUMOKUCD).equals("9N251161100000049") ||
							recordMap.get(COLUMN_NAME_KOUMOKUCD).equals("9N256161100000049") ||
							recordMap.get(COLUMN_NAME_KOUMOKUCD).equals("9N261161100000049") ||
							recordMap.get(COLUMN_NAME_KOUMOKUCD).equals("Z9N21161100000049") ||
							recordMap.get(COLUMN_NAME_KOUMOKUCD).equals("Z9N22161100000049") ||
							recordMap.get(COLUMN_NAME_KOUMOKUCD).equals("Z9N26161100000049") ||
							recordMap.get(COLUMN_NAME_KOUMOKUCD).equals("ZI210161100000049"))
							dayformat = true;

					// フォーマット文字列の作成
					int width = getWidth(format);
					int scale = getScale(format);
					String formatStr = "%." + scale + "f";

					String roundUpValue = "";
					try {

// eidt s.inoue 2012/07/02
//						double value = new Double(textField.getText())
//								.doubleValue();
//						// 小数点第2位以下を四捨五入し小数点第1位までを表示
//						roundUpValue = String.format(formatStr, new BigDecimal(
//								value).setScale(scale + 1,
//								BigDecimal.ROUND_HALF_UP).floatValue());

						// 小数点第2位以下を四捨五入し小数点第1位までを表示
						if (dayformat){
							String value = new String(textField.getText());
							roundUpValue = String.format(formatStr, new BigDecimal(
									value));
						}else{
							double value = new Double(textField.getText()).doubleValue();
							roundUpValue = String.format(formatStr, new BigDecimal(
									value).setScale(scale + 1,
											BigDecimal.ROUND_HALF_UP).floatValue());
						}

						// 整数部・小数部が指定桁数をオーバーしている場合はクリア
						if ((getWidth(roundUpValue) > width)
								|| (getScale(roundUpValue) > scale))
							roundUpValue = "";
					} catch (NumberFormatException e) {
					}
					// 四捨五入値をセルにセット
					model.setData(roundUpValue, rowIndex, columnIndex);

					// add ver2 s.inoue 2009/06/24
					int  selectedColumnIndex=model.getSelectedColumn();
					int  selectedRowIndex=model.getSelectedRow();

					if ((selectedColumnIndex == 3) &&
							(selectedRowIndex== 0 || selectedRowIndex== 1)) {
						actionBMICalc();
					}
				}

				/**
				 * 妥当なフォーマット文字列か検証する
				 * @param rowIndex
				 */
				private boolean isValidFormat(String format) {

					if ((format == null) || (format.length() < 1))
						return false;

					Pattern pattern = Pattern.compile(DIGIT_FORMAT_REGEXP);
					if (!pattern.matcher(format).matches())
						return false;
					int pos = format.indexOf(".");
					if (pos == (format.length() - 1))
						return false;

					// 小数点の指定が無い場合
					if (pos < 0) {
						pattern = Pattern.compile("#+");
						return pattern.matcher(format).matches();
					}

					pos++;
					pattern = Pattern.compile("0+");
					return pattern.matcher(
							format.substring(pos, format.length())).matches();
				}

				/**
				 * 整数部の桁数を取得する
				 *
				 * @param format
				 * @return
				 */
				private int getWidth(String format) {
					if ((format == null) || (format.length() == 0))
						return -1;
					int pos = format.indexOf(".");
					if (pos < 0)
						return format.length();
					if ((pos == 0) || (pos == (format.length() - 1)))
						return -1;
					return format.substring(0, pos).length();
				}

				/**
				 * 小数部の桁数を取得する
				 *
				 * @param format
				 * @return
				 */
				private int getScale(String format) {
					int pos = format.indexOf(".");
					if (pos < 0)
						return 0;
					if ((pos == 0) || (pos == (format.length() - 1)))
						return -1;
					pos++;
					return format.substring(pos, format.length()).length();
				}
			});
		}
	}

	private int mode;
	private JSimpleTableScrollPanel scroll;
	private JSimpleTable model;
	private JKekkaRegisterFrameData validatedData = new JKekkaRegisterFrameData();  //  @jve:decl-index=0:
	// edit s.inoue 2009/10/19
	private ExtendedComboBox kekkaCombo = new ExtendedComboBox();
	private ExtendedComboBox hanteiCombo = new ExtendedComboBox();
	private ExtendedComboBox hlCombo = new ExtendedComboBox();
	private ExtendedComboBox jisiCombo = new ExtendedComboBox();

	private JComboCellEditor kekkaComboEditor = new JComboCellEditor(kekkaCombo);
	private JComboCellEditor hanteiComboEditor = new JComboCellEditor(hanteiCombo);
	private JComboCellEditor hlComboEditor = new JComboCellEditor(hlCombo);
	private JComboCellEditor jisiComboEditor = new JComboCellEditor(jisiCombo);

	private Vector<JSimpleTableCellRendererData> cellColors = new Vector<JSimpleTableCellRendererData>();
	private String BMIFormat = null;
	private JSimpleTableCellPosition BMICellPosition = null;
	private JSimpleTableCellPosition heightCellPosition = null;
	private JSimpleTableCellPosition weightCellPosition = null;
	private Vector<JSimpleTableCellPosition> forbitCells = new Vector<JSimpleTableCellPosition>();
	private final Color COLOR_HISU = ViewSettings.getRequiedItemBgColor();
	private final Color COLOR_DISABLE = ViewSettings.getDisableItemBgColor();
	// add h.yoshitama 2009/09/18
	private final Color COLOR_ABLE = ViewSettings.getAbleItemBgColor();

	/* フォーカス移動制御 */
	private JFocusTraversalPolicy focusTraversalPolicy = null;

	/* Enter キー押下でフォーカスを移動するコンポーネント */
	private List<Component> enterkeyFocusComponents = null;

	// Proxyとして管理する
	private static jp.or.med.orca.jma_tokutei.common.csv.JCSVReaderStream Reader = null;
	private int kenshinPatternNumber = -1;
	private int prekenshinPatternNumber = -1;
	private ArrayList<Hashtable<String, String>> tableResult = null;
	private String[] hanteiCodeTable = { "未判定", "異常なし", "軽度異常", "要経過観察", "異常","要精検" };
	private String[] hlCodeTable = { "", "L", "H" };
	private boolean isInit = false;
	private static final int HISU_FLG_HISU = 1;

	// T_KOJIN,T_KENSAKEKA_TOKUTEIにUKETUKE_ID,NENDO追加対応
	private TKensakekaTokutei kensakekaTokutei = new TKensakekaTokutei();
	private TKensakekaSonota kensakekaSonota = new TKensakekaSonota();
	private TKensakekaTokuteiDao kensakekaTokuteiDao = null;
	private TKensakekaSonotaDao kensakekaSonotaDao = null;
	private String initialDate = null;
	private boolean isCopy = false;

	// edit s.inoue 2009/12/10
	private ExtendedTextField textAreaField = null;

	/**
	 * コンストラクタ
	 * @param srcData このフレームのデータ
	 * @param kensaJissiDate 健診実施日
	 */
	public JKekkaRegisterFrameCtrl(
			JKenshinKekkaSearchListFrameData srcData,
			String kensaJissiDate,
			boolean isCopy
			) {

		this.initialDate = kensaJissiDate;
		this.isCopy = isCopy;

		/* コンボボックスを初期化する。 */
		this.initializeComboBox();

		/* テーブルを初期化する。 */
		this.initializeTable();

		/* 列の幅を初期化する。 */
		this.initializeColumnWidth();
		this.model.setPreferedColumnWidths(DEFAULT_COLUMNS_WIDTH);
		this.model.refreshTable();

		/* コンポーネントを初期化する。 */
		// eidt s.inoue 2011/08/02
		initilazeFunctionSetting();
		this.initializeComponents(srcData, kensaJissiDate, isCopy);
		this.model.addKeyListener(new JEnterEvent(this));

		// 総合コメント欄にキーリスナ追加
		jEditorPane_Comment.addKeyListener(new SougouCommentKeyListener(this));
// del s.inoue 2010/05/24
//		this.jEditorPane_Comment.addFocusListener(new FocusAdapter(){
//			@Override
//			public void focusLost(FocusEvent arg0) {
//				jEditorPane_Comment.getInputContext().setCompositionEnabled(false);
//				jEditorPane_Comment.getInputContext().setCharacterSubsets(null);
//			}
//		});

		jEditorPane_Comment.getInputContext().setCharacterSubsets(null);

		// add s.inoue 2011/07/28
		this.validatedData.setUketuke_id(srcData.getUKETUKE_ID());

		this.changeInputDate(this.validatedData.getKensaJissiDate(), false);

		/* フォーカス遷移を初期化する。 */
		this.initializeFocusTraversal();
		// add s.inoue 2009/12/03
		functionListner();

		/* Added 2008/03/12 若月 必須項目を指定の背景色に設定する */
		this.jTextField_Date.setBackground(ViewSettings.getRequiedItemBgColor());
		this.jComboBox_Pattern.setBackground(ViewSettings.getRequiedItemBgColor());
		this.jComboBox_SeikyuKubun.setBackground(ViewSettings.getRequiedItemBgColor());
		// add h.yoshitama 2009/09/24
		jLabel_requiredExample.setBackground(ViewSettings.getRequiedItemBgColor());
		jLabel_ableExample.setBackground(ViewSettings.getAbleItemBgColor());
		jLabel_disableExample.setBackground(ViewSettings.getDisableItemBgColor());

		if (isCopy) {
			this.jTextField_Date.grabFocus();
			// add ver2 s.inoue 2009/06/29
			jTextField_SeiriNo.setVisible(true);
			jLabelSeirino.setVisible(true);
		}
	}

	// add s.inoue 2009/11/11
	// マウスリスナのメソッドを定義
	@Override
	public void mousePressed(MouseEvent e) {
		mousePopup(e);
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		mousePopup(e);
	}
	// add s.inoue 2009/11/11
	private void mousePopup(MouseEvent e) {
		if (e.isPopupTrigger()) {
			// ポップアップメニューを表示する
			JComponent c = (JComponent)e.getSource();
			jEditorPane_Comment.showPopup(c, e.getX(), e.getY());
			e.consume();
		}
	}
	private boolean isNewKekkaData = false;

	// add s.inoue 2011/08/02
	private void initilazeFunctionSetting(){
		ArrayList<Hashtable<String, String>> result = null;

		try{
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT FUNCTION_CD,FUNCTION_FLG");
			sb.append(" FROM T_SCREENFUNCTION ");
			sb.append(" WHERE SCREEN_CD = ");
			sb.append(JQueryConvert.queryConvert("002"));

			result = JApplication.kikanDatabase.sendExecuteQuery(sb.toString());
		}catch(Exception ex){
			logger.error(ex.getMessage());
		}

		for (int i = 0; i < result.size(); i++) {
			Hashtable<String, String> item = result.get(i);

			String functionCd = item.get("FUNCTION_CD");

			// eidt s.inoue 2011/08/01
			if (JApplication.func_yearOldCode.equals(functionCd)){
				yearOld_flg =  item.get("FUNCTION_FLG").equals("1")?true:false;
			}
		}

	}
	private void initializeComponents(JKenshinKekkaSearchListFrameData srcData, String kensaJissiDate, boolean isCopy) {

		Dimension size =ViewSettings.getFrameCommonSize();

		this.setSize(size);
		this.setPreferredSize(size);
		this.setMinimumSize(size);
		this.setLocationRelativeTo( null );

		// 前フレームから受け継いだデータをセット
		// jTextField_HokenjyaCode.setText(srcData.getHihokenjyaCode());
		// jTextField_HokenjyaNumber.setText(srcData.getHihokenjyaNumber());
		// jTextField_Name.setText(srcData.getName());
		// edit s.inoue 20110325
		jTextField_HokenjyaCode.setText(srcData.getHIHOKENJYASYO_KIGOU());
		jTextField_HokenjyaNumber.setText(srcData.getHIHOKENJYASYO_NO());
		// eidt s.inoue 2011/06/07
		jTextField_Name.setText(srcData.getKANANAME());

		/* 性別名の表示 */
		// edit s.inoue 20110325
		// jTextFieldsexName.setText(srcData.getSexName());
		jTextFieldsexName.setText(srcData.getSEX());

		jTextField_HokenjyaCode.setEditable(false);
		jTextField_HokenjyaNumber.setEditable(false);
		jTextField_Name.setEditable(false);

		// edit s.inoue 20110325
		validatedData.setHokenjyaNumber(srcData.getHKNJANUM());

		// edit s.inoue 20110325
		validatedData.setHihokenjyaCode(jTextField_HokenjyaCode.getText(),false);
		validatedData.setHihokenjyaNumber(jTextField_HokenjyaNumber.getText(),false);

		// eidt s.inoue 2011/04/25
		jTextFieldBirthDay.setText(String.valueOf(srcData.getBIRTHDAY()));


// eidt s.inoue 2011/08/02
//		int age =0;
//		if (!kensaJissiDate.equals("")){
//			age = FiscalYearUtil.getFiscalYear(String.valueOf(srcData.getBIRTHDAY()),kensaJissiDate);
//		}else{
//			age = FiscalYearUtil.getFiscalYear(String.valueOf(srcData.getBIRTHDAY()));
//		}
//		jTextFieldNenrei.setText(String.valueOf(age));


		// add s.inoue 2011/08/01
		String selectedItem = String.valueOf(srcData.getBIRTHDAY());

		int age =0;
		if (yearOld_flg){
			if (!kensaJissiDate.equals("")){
				age = FiscalYearUtil.getFiscalYear(selectedItem,kensaJissiDate);
			}else{
				age = FiscalYearUtil.getFiscalYear(selectedItem);
			}
		}else{
			if (!kensaJissiDate.equals("")){
				age = Integer.parseInt(JYearAge.getAge(selectedItem,kensaJissiDate));
			}else{
				age = Integer.parseInt(JYearAge.getAge(selectedItem));
			}
		}
		System.out.println("age:" + age);
		jTextFieldNenrei.setText(String.valueOf(age));


		// edit s.inoue 20110325
		// validatedData.setUketuke_id(srcData.getUketuke_id());
		// validatedData.setUketukePre_id(srcData.getUketukePre_id());

		// eidt s.inoue 2011/07/21
//		validatedData.setUketuke_id(srcData.getUKETUKE_ID());
		validatedData.setUketukePre_id(srcData.getUKETUKEPRE_ID());

		try {
			Connection connection = JApplication.kikanDatabase.getMConnection();

			kensakekaTokuteiDao = (TKensakekaTokuteiDao) DaoFactory.createDao(
					connection,
					kensakekaTokutei);

			kensakekaSonotaDao = (TKensakekaSonotaDao) DaoFactory.createDao(
					connection,
					kensakekaSonota);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}

		this.isNewKekkaData = false;

		/* 結果データ複製以外の場合 */
		if (! isCopy) {
			/* 健診実施日が空の場合 */
			if (kensaJissiDate.equals("")) {
				this.isNewKekkaData = true;

				/* システム日時を入力する。 */
				Calendar cal = Calendar.getInstance();

				String year = JValidate.fillZero(String.valueOf(cal.get(Calendar.YEAR)), 4);
				String month = JValidate.fillZero(String.valueOf(cal.get(Calendar.MONTH) + 1), 2);
				String date = JValidate.fillZero(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)), 2);

				String jissiDateString = year + month + date;

				jTextField_Date.setText(jissiDateString);
				this.validatedData.setKensaJissiDate(jissiDateString,false);

			/* 健診実施日が指定されている場合 */
			} else {
				validatedData.setKensaJissiDate(kensaJissiDate,false);
				jTextField_Date.setText(validatedData.getKensaJissiDate());
			}
		}
		/* 結果データ複製の場合 */
		else {
			validatedData.setKensaJissiDate(kensaJissiDate,false);
			this.isNewKekkaData = true;
		}
	}

	private void initializeEnterkeyFocusComponents() {
		/* Enter キー押下でフォーカスを移動するコンポーネントを登録する。 */
		this.enterkeyFocusComponents = new ArrayList<Component>();

		this.enterkeyFocusComponents.add(jComboBox_Pattern);
		this.enterkeyFocusComponents.add(jTextField_Date);
		this.enterkeyFocusComponents.add(jComboBox_MetaboHantei);
		this.enterkeyFocusComponents.add(jComboBox_HokenSidouLevel);
		this.enterkeyFocusComponents.add(jComboBox_SeikyuKubun);
		// add ver2 s.inoue 2009/06/29
		if (isCopy){
			this.enterkeyFocusComponents.add(jTextField_SeiriNo);
		}
		this.enterkeyFocusComponents.add(jEditorPane_Comment);
	}

	private void initializeFocusTraversal() {
		this.focusTraversalPolicy = new JFocusTraversalPolicy();
		this.setFocusTraversalPolicy(this.focusTraversalPolicy);

		this.focusTraversalPolicy.setDefaultComponent(jComboBox_Pattern);

		this.focusTraversalPolicy.addComponent(jComboBox_Pattern);
		this.focusTraversalPolicy.addComponent(jTextField_Date);
		this.focusTraversalPolicy.addComponent(jComboBox_SeikyuKubun);
		// add ver2 s.inoue 2009/06/29
		if (isCopy){
			this.focusTraversalPolicy.addComponent(jTextField_SeiriNo);
		}
		this.focusTraversalPolicy.addComponent(jComboBox_MetaboHantei);
		this.focusTraversalPolicy.addComponent(jComboBox_HokenSidouLevel);
		this.focusTraversalPolicy.addComponent(jEditorPane_Comment);
		this.focusTraversalPolicy.addComponent(model);

		this.focusTraversalPolicy.addComponent(jButton_Register);
		this.focusTraversalPolicy.addComponent(jButton_Clear);
		this.focusTraversalPolicy.addComponent(jButton_End);
	}

	// add s.inoue 2009/12/03
	private void functionListner(){
		for (int i = 0; i < focusTraversalPolicy.getComponentSize(); i++) {
			Component comp = focusTraversalPolicy.getComponent(i);
			comp.addKeyListener(this);
		}
	}

	private void initializeTable() {

		this.model = new JSimpleTable();
		scroll = new JSimpleTableScrollPanel(model);
		jPanel_Table.add(scroll);

		this.model.setCellEditForbid(forbitCells);
		this.model.setCellColor(cellColors);

		this.model.addHeader("項目コード");
		this.model.addHeader("項目名");
		this.model.addHeader("検査方法");
		this.model.addHeader("結果（数値）");
		this.model.addHeader("結果（コード）");
		this.model.addHeader("結果（文字列）");
		/* 基準値下限(男性),基準値上限(男性),基準値下限(女性),基準値上限(女性)を */
		/* テーブルに表示 */
		this.model.addHeader("実施区分");
		this.model.addHeader("基準値下限（男性）");
		this.model.addHeader("基準値上限（男性）");
		this.model.addHeader("基準値下限（女性）");
		this.model.addHeader("基準値上限（女性）");
		this.model.addHeader("H/L");
		this.model.addHeader("判定");
		this.model.addHeader("コメント");

		// 入力下限/上限値を追加
		// del h.yoshitama 2009/11/05

//		this.model.addHeader("入力下限値");
//		this.model.addHeader("入力上限値");

		// 結果コード値のカラムをコンボボックスに
		this.model.setCellComboBoxEditor(kekkaComboEditor, 4);

		// del s.inoue 2009/10/19
//		kekkaCombo.addItemListener(new ItemListener(){
//			@Override
//			public void itemStateChanged(ItemEvent e) {
//				if (e.getSource() == kekkaCombo) {
//				}
//			}
//
//		});

		// add s.inoue 2009/10/19
		kekkaCombo.addKeyListener(new KeyListener(){
			@Override
			public void keyPressed(KeyEvent e) {
				// edit s.inoue 2009/12/21
				switch(e.getKeyCode()){
				case KeyEvent.VK_ENTER:
					kekkaCombo.setSelectedIndex(kekkaCombo.getSelectedIndex());break;
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {}
			@Override
			public void keyTyped(KeyEvent e) {}
		});

		// add s.inoue 2009/10/19
		hanteiCombo.addKeyListener(new KeyListener(){
			@Override
			public void keyPressed(KeyEvent e) {
				// edit s.inoue 2009/12/21
				switch(e.getKeyCode()){
				case KeyEvent.VK_ENTER:
					hanteiCombo.setSelectedIndex(hanteiCombo.getSelectedIndex());break;
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {}
			@Override
			public void keyTyped(KeyEvent e) {}
		});

		// add s.inoue 2009/10/19
		hlCombo.addKeyListener(new KeyListener(){
			@Override
			public void keyPressed(KeyEvent e) {
				// edit s.inoue 2009/12/21
				switch(e.getKeyCode()){
				case KeyEvent.VK_ENTER:
					hlCombo.setSelectedIndex(hlCombo.getSelectedIndex());break;
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {}
			@Override
			public void keyTyped(KeyEvent e) {}
		});

		// add s.inoue 2009/10/19
		jisiCombo.addKeyListener(new KeyListener(){
			@Override
			public void keyPressed(KeyEvent e) {
				// edit s.inoue 2009/12/21
				switch(e.getKeyCode()){
				case KeyEvent.VK_ENTER:
					jisiCombo.setSelectedIndex(jisiCombo.getSelectedIndex());break;
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {}
			@Override
			public void keyTyped(KeyEvent e) {}
		});

		// edit s.inoue 2009/11/11
		/* 基準値下限(男性),基準値上限(男性),基準値下限(女性),基準値上限(女性)を */
		/* テーブルに表示 */
		// 実施区分
		this.model.setCellComboBoxEditor(jisiComboEditor, COLUMN_IDX_JISIKBN);

		// 判定結果のカラムをコンボボックスに
		this.model.setCellComboBoxEditor(hanteiComboEditor, COLUMN_IDX_HANTEI);

		// HLのカラムをコンボボックスに
		this.model.setCellComboBoxEditor(hlComboEditor, COLUMN_IDX_HL);
	}

	private void initializeComboBox() {
		// 保健指導レベルコンボボックスの初期設定
		jComboBox_HokenSidouLevel.addItem("未判定");
		jComboBox_HokenSidouLevel.addItem("積極的支援");

		jComboBox_HokenSidouLevel.addItem("動機づけ支援");
		jComboBox_HokenSidouLevel.addItem("なし（情報提供）");
		jComboBox_HokenSidouLevel.addItem("判定不能");

		// メタボリックシンドローム判定コンボボックスの初期設定
		jComboBox_MetaboHantei.addItem("未判定");
		jComboBox_MetaboHantei.addItem("基準該当");

		jComboBox_MetaboHantei.addItem("予備群該当");

		jComboBox_MetaboHantei.addItem("非該当");
		jComboBox_MetaboHantei.addItem("判定不能");

		// 請求区分コンボボックスの初期設定
		jComboBox_SeikyuKubun.addItem("基本的な健診");
		jComboBox_SeikyuKubun.addItem("基本的な健診＋詳細な健診");

		jComboBox_SeikyuKubun.addItem("基本的な健診＋追加健診項目");
		jComboBox_SeikyuKubun.addItem("基本的な健診＋詳細な健診＋追加健診項目");

		jComboBox_SeikyuKubun.addItem("人間ドック");
	}

	/**
	 * 列サイズを初期化する。
	 */
	private void initializeColumnWidth() {
		this.model.setAutoResizeMode(JSimpleTable.AUTO_RESIZE_OFF);

		DefaultTableColumnModel columnModel = (DefaultTableColumnModel) this.model.getColumnModel();
		// edit s.inoue 2009/11/11
		columnModel.getColumn(COLUMN_IDX_KOUMOKUCD).setMinWidth(0);
		columnModel.getColumn(COLUMN_IDX_KOUMOKUCD).setPreferredWidth(0);
		columnModel.getColumn(COLUMN_IDX_KOUMOKUCD).setMaxWidth(0);
		columnModel.getColumn(COLUMN_IDX_KOUMOKUCD).setWidth(0);
	}

	/** 20081224 s.inoue add CSV⇒DBへ変更
	 * 上限値下限値の項目をチェックを行う
	 */
	public static boolean CheckHighLawItem(String koumokuCode,String hknjaNm,String value){
		ArrayList<Hashtable<String, String>> result = null;

		try{
			String[] params = { hknjaNm,koumokuCode };

			String sql = "SELECT KOUMOKU_CD,KAGEN,JYOUGEN " +
			"FROM T_KENSHINMASTER WHERE HKNJANUM = ? AND KOUMOKU_CD = ? ";

			// 空値の場合はチェックを行わない
			if (!value.isEmpty() ||
					!hknjaNm.isEmpty()) {
				String strKagen = "";
				String strJyogen = "";

				result = JApplication.kikanDatabase.sendExecuteQuery(sql, params);

				for (int i = 0; i < result.size(); i++) {

					strKagen = result.get(i).get("KAGEN");
					strJyogen = result.get(i).get("JYOUGEN");

					// 項目コードの結果判定の部分を除いて比較を行う
					// 範囲内の場合 true
					if (result.get(i).get("KOUMOKU_CD").equals(koumokuCode)) {
						if (value.isEmpty())
							break;
						if (strKagen.isEmpty() && strJyogen.isEmpty()){
							break;
						}else if (strKagen.isEmpty()){
							return Double.valueOf(strJyogen) >= Double.valueOf(value);
						}else if(strJyogen.isEmpty()){
							return Double.valueOf(strKagen) <= Double.valueOf(value);
						}else if (!strKagen.isEmpty() && !strJyogen.isEmpty()){
							return (Double.valueOf(strKagen) <= Double.valueOf(value)
									&& Double.valueOf(value) <= Double.valueOf(strJyogen));
						}
					}
				}
			}
		}catch(Exception ex){
			logger.warn(ex.getMessage());
		}
		// 見つからなかった場合
		return true;
	}

	/**
	 * 上限値下限値チェックによるメッセージ取得を行う
	 */
	public String getHighLawMessage(String koumokuCode,String koumokuName,String hknjaNm,String value){

		ArrayList<Hashtable<String, String>> result = null;
		String retMessage = "";
		String kekkaMessage ="";

		try{
			String[] params = { hknjaNm,koumokuCode };

			String sql = "SELECT KOUMOKU_CD,KAGEN,JYOUGEN " +
			"FROM T_KENSHINMASTER WHERE HKNJANUM = ? AND KOUMOKU_CD = ? ";

			// 空値の場合はチェックを行わない
			if (!value.isEmpty() ||
					!hknjaNm.isEmpty()) {
				String strKagen = "";
				String strJyogen = "";

				result = JApplication.kikanDatabase.sendExecuteQuery(sql, params);

				for (int i = 0; i < result.size(); i++) {

					strKagen = result.get(i).get("KAGEN");
					strJyogen = result.get(i).get("JYOUGEN");

					// 項目コードの結果判定の部分を除いて比較を行う
					// 範囲内の場合 true
					if (result.get(i).get("KOUMOKU_CD").equals(koumokuCode)) {
						// add ver2 s.inoue 2009/04/02
						if (value.isEmpty())
							break;
						if (strKagen.isEmpty() && strJyogen.isEmpty()){
							break;
						}else if (strKagen.isEmpty()){
							// edit ver2 s.inoue 2009/04/16
							//return Double.valueOf(strJyogen) <= Double.valueOf(value);
							if (Double.valueOf(strJyogen) < Double.valueOf(value)){
								kekkaMessage= strHighMessageValue + "["+ Double.valueOf(strJyogen) + "]" +"を超えています。";
							}
						}else if(strJyogen.isEmpty()){
							// edit ver2 s.inoue 2009/04/16
							//return Double.valueOf(strKagen) <= Double.valueOf(value);
							if (Double.valueOf(strKagen) > Double.valueOf(value)){
								kekkaMessage= strLowMessageValue + "["+ Double.valueOf(strKagen) + "]" +"を下回っています。";
							}
						}else if (!strKagen.isEmpty() && !strJyogen.isEmpty()){
							// edit ver2 s.inoue 2009/04/16
							//return (Double.valueOf(strKagen) <= Double.valueOf(value)
							//		&& Double.valueOf(value) <= Double.valueOf(strJyogen));
							if (Double.valueOf(strKagen) > Double.valueOf(value)){
								kekkaMessage= strLowMessageValue + "["+ Double.valueOf(strKagen) + "]" +"を下回っています。";
							}else if(Double.valueOf(strJyogen) < Double.valueOf(value)){
								kekkaMessage= strHighMessageValue + "["+ Double.valueOf(strJyogen) + "]" +"を超えています。";
							}
						}
					}
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}

		if (!kekkaMessage.isEmpty()){
			retMessage = "[改行]項目コード[" + koumokuCode
			 + "] 項目名[" + koumokuName
			 + "][改行]の結果値[" + validatedData.getKekka()
			 + "]が" + kekkaMessage;
		}

		return retMessage;
	}

	/**
	 * 登録ボタンを押した際の必須項目等を検証する
	 */
	public boolean validateAsRegisterPushed(JKekkaRegisterFrameData data) {
		if (kenshinPatternNumber == -1 || data.getKensaJissiDate().equals("")
				|| data.getSeikyuKubun().equals("")) {
			jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage.show(
					"M3610", this);
			return false;
		}

		data.setValidateAsDataSet();
		return true;
	}

	/**
	 * 終了ボタン
	 */
	public void pushedEndButton(ActionEvent e) {
		dispose();
	}

	/**
	 * キャンセルボタン
	 */
	public void pushedCancelButton(ActionEvent e) {
		RETURN_VALUE Ret = JErrorMessage.show("M3621", this);
		if (Ret == RETURN_VALUE.YES) {
			dispose();
		}
	}

	/**
	 * クリアボタン
	 */
	public void pushedClearButton(ActionEvent e) {
		RETURN_VALUE Ret = JErrorMessage.show("M3640", this);
		if (Ret == RETURN_VALUE.YES) {
			refreshTable(jTextField_Date.getText(),false,true,false);
		}
	}

	/**
	 * 登録ボタン
	 */
	public void pushedRegisterButton(ActionEvent e) {

		String jissiDate = jTextField_Date.getText();
		if (jissiDate == null || jissiDate.isEmpty()) {
			JErrorMessage.show("M3633", this);
			this.jTextField_Date.grabFocus();
			return;
		}
		/* 健診実施日のフォーマットチェック */
		if (! validatedData.setKensaJissiDate(jissiDate,false)) {
			// edit 20081216 s.inoue
			JErrorMessage.show("M3605", this);
			this.jTextField_Date.grabFocus();
			return;
		}
		if (isCopy){

// eidt s.inoue 2011/07/21
			TKojinDao tKojinDao = null;
			// eidt s.inoue 2011/07/19
			/* T_KOJIN Dao を作成する。 */
			try {
				tKojinDao = (TKojinDao) DaoFactory.createDao(
						JApplication.kikanDatabase.getMConnection(), new TKojin());
			} catch (Exception ex) {
				logger.error(ex.getMessage());
			}

			// 複製コピー時、受付ID採番
			long uketukeId = -1L;
			try {
				uketukeId = tKojinDao.selectNewUketukeId();
			} catch (Exception exx) {
				logger.error(exx.getMessage());
			}
			validatedData.setUketuke_id(new Long(uketukeId).toString());


			String copySeiriNo = jTextField_SeiriNo.getText();
			// add ver2 s.inoue 2009/06/29
			/* 受診券整理番号のフォーマットチェック */
			if (! validatedData.setcopyJyushinkenID(copySeiriNo)) {
				// edit 20081216 s.inoue
				JErrorMessage.show("M3606", this);
				this.jTextField_SeiriNo.grabFocus();
				return;
			}
		}
		RETURN_VALUE Ret = JErrorMessage.show("M3622", this);
		if (Ret == RETURN_VALUE.YES) {
			// edit s.inoue 2010/04/21
			if(this.register())
			dispose();
		}
	}

	/**
	 * 検査センター名称のコンボボックスが変更された場合に呼ばれる
	 */
	public void stateChangedKensaKikanName() {

		String Query = new String(
				"SELECT DISTINCT KENSA_CENTER_CD,CENTER_NAME FROM T_KENSACENTER_MASTER");
		ArrayList<Hashtable<String, String>> Result = null;
		Hashtable<String, String> ResultItem;

		try {
			Result = JApplication.kikanDatabase.sendExecuteQuery(Query);
		} catch (SQLException f) {
			f.printStackTrace();
		}

		// コンボボックスに表示されている健診機関名称から検査センターコードを引く
		// 検査センターが未選択の場合でも検査センターコンボボックスで最初の項目が選択されてしまう障害の修正
		String selectedItem = (String) jComboBox_KensaKikanName
				.getSelectedItem();
		if ((jComboBox_KensaKikanName.getItemCount() != 0)
				&& (selectedItem != null)) {
			for (int i = 0; i < Result.size(); i++) {
				ResultItem = Result.get(i);
				if (ResultItem.get("CENTER_NAME").equals(selectedItem)) {
					// 以前と同じものが選択されていた場合はリフレッシュしない（編集していたものが消えてしまうため）
					if (validatedData.getKensaCenterCode().equals(
							ResultItem.get("KENSA_CENTER_CD"))) {
						break;
					} else {
						validatedData.setKensaCenterCode(ResultItem
								.get("KENSA_CENTER_CD"));
						refreshTable(jTextField_Date.getText(), true,false,false);
						break;
					}
				}
			}
		}
	}

	/**
	 * 初期化用
	 */
	public void stateInitKenshinPatternNumber(String jissiDate) {
		String kenshinPatternName = null;
		String query = "SELECT K_P_NO,K_P_NAME FROM T_KENSHIN_P_M WHERE K_P_NO >= 0 ";
		ArrayList<Hashtable<String, String>> result = null;
		Hashtable<String, String> ResultItem;

		try {
			result = JApplication.kikanDatabase.sendExecuteQuery(query);
		} catch (SQLException f) {
			f.printStackTrace();
		}
		Object selectedItem = jComboBox_Pattern.getSelectedItem();
		if (selectedItem != null)
			kenshinPatternName = selectedItem.toString();

		for (int i = 0; i < result.size(); i++) {
			ResultItem = result.get(i);
			if (ResultItem.get("K_P_NAME").equals(kenshinPatternName)) {
				if (kenshinPatternNumber == Integer.valueOf(ResultItem.get("K_P_NO"))) {
					break;
				} else {
					kenshinPatternNumber = Integer.valueOf(ResultItem.get("K_P_NO"));
					this.refreshTable(jissiDate, true,false,false);
					break;
				}
			}
		}
	}

	// edit ver2 s.inoue 2009/08/27
	// blnKekkaは登録時のみ関係あり
	/**
	 * 健診パターンのコンボボックスが変更された場合に呼ばれる
	 */
	public boolean stateChangedKenshinPatternNumber(String uketukeId,String jissiDate,boolean blnKensaRegist,boolean blnKekka) {
		String kenshinPatternName = null;

		boolean retPattan = false;
		// edit ver2 s.inoue 2009/08/25
		boolean blnNoEditPattarn = false;

		String query = "SELECT K_P_NO,K_P_NAME FROM T_KENSHIN_P_M WHERE K_P_NO >= 0 ";

		ArrayList<Hashtable<String, String>> result = null;
		Hashtable<String, String> ResultItem;

		try {
			result = JApplication.kikanDatabase.sendExecuteQuery(query);
		} catch (SQLException f) {
			f.printStackTrace();
		}

		Object selectedItem = jComboBox_Pattern.getSelectedItem();
		if (selectedItem != null)
			kenshinPatternName = selectedItem.toString();

		// 登録時、変更前の健診パターンを使用し、
		// コンボボックスのパターン変更時健診パターンのバックアップする
		if (blnKensaRegist){
			// edit ver2 s.inoue 2009/08/25
			if (prekenshinPatternNumber == -1 || blnKekka){
				blnNoEditPattarn = true;
			}
			kenshinPatternNumber = prekenshinPatternNumber;
		}else{
			prekenshinPatternNumber = kenshinPatternNumber;
		}

		for (int i = 0; i < result.size(); i++) {
			ResultItem = result.get(i);
			if (ResultItem.get("K_P_NAME").equals(kenshinPatternName)) {

				// edit 20081201 s.inoue デフォルト値の場合処理をパターン削除行わない
				if (kenshinPatternNumber == Integer.valueOf(ResultItem.get("K_P_NO"))) {
					this.refreshTable(jissiDate, true,false,false);
					break;
				} else {

					// add s.inoue 20081125 新規作成のときはチェック処理は行わない
					Long resultCnt = 0L;
					try {
						resultCnt = kensakekaTokuteiDao.selectByCountPrimaryKey(Long.valueOf(
								uketukeId),Integer.valueOf(jissiDate));
					} catch (Exception e) {
						e.printStackTrace();
					}

					if (resultCnt <= 0) {
						kenshinPatternNumber = Integer.valueOf(ResultItem.get("K_P_NO"));
						this.refreshTable(jissiDate, true,false,false);
						break;
					}

					// edit s.inoue 20081128 コンボ選択時、登録時でメッセージを変更する
					RETURN_VALUE retValue = null;

					if (blnKensaRegist){ //登録実行時
						kenshinPatternNumber = Integer.valueOf(ResultItem.get("K_P_NO"));
						// edit ver2 s.inoue 2009/08/25
						// 削除実行
						if (!blnNoEditPattarn){
							if(pattarnKensaKoumoku()){
								this.refreshTable(jissiDate, true,false,true);
								retPattan= true;
							}else{
								// edit ver2 s.inoue 2009/08/25
								this.refreshTable(jissiDate, true,false,false);
								retPattan= false;
							}
						}
					}else{ //コンボ選択時
						retValue = JErrorMessage.show("M3638", this);

						if (retValue == RETURN_VALUE.YES) {
							kenshinPatternNumber = Integer.valueOf(ResultItem.get("K_P_NO"));
							this.refreshTable(jissiDate, true,false,true);
							retPattan= true;
						}else if(retValue == RETURN_VALUE.NO) {
							jComboBox_Pattern.setSelectedIndex(kenshinPatternNumber - 1);
						}
					}

					break;
				}
			}
		}
		return retPattan;
	}

	/**
	 * アクションの更新
	 */
	private void refreshCellAction(){
		// edit s.inoue 2009/12/24
		textAreaField = new ExtendedTextField("", 0, ImeMode.IME_ZENKAKU,false);
		TableColumn column = this.model.getColumnModel().getColumn(COLUMN_IDX_KEKKA_STR);

// edit s.inoue 2009/12/24
		column.setCellEditor(
				new PopupWindowShowCellEditor(
						this,
						textAreaField,
						COLUMN_IDX_KEKKA_STR));

// 改行複数モードだが、enter処理に困惑
//		column.setCellEditor(
//		new PopupWindowShowTextAreaCellEditor(
//				this,
//				textAreaField,
//				COLUMN_IDX_KEKKA_STR));


//		textField.addMouseListener(new MouseListener(){
//			// add s.inoue 2009/11/11
//			public void mousePressed(MouseEvent e) {
//				mousePopup(e);
//			}
//			public void mouseReleased(MouseEvent e) {
//				mousePopup(e);
//			}
//			// add s.inoue 2009/11/11
//			private void mousePopup(MouseEvent e) {
//				if (e.isPopupTrigger()) {
//					// ポップアップメニューを表示する
//					JComponent c = (JComponent)e.getSource();
//					jKekkaMojiretsuTextArea.showPopup(c, e.getX(), e.getY());
//					e.consume();
//				}
//			}
//			@Override
//			public void mouseClicked(MouseEvent e) {
//			}
//			@Override
//			public void mouseEntered(MouseEvent e) {
//			}
//			@Override
//			public void mouseExited(MouseEvent e) {
//			}
//		});

		// edit s.inoue 2010/06/10
		String comment = jEditorPane_Comment.getText();
		// del s.inoue 2010/06/11
//		final IKekkaRegisterInputDialog dialogs =
//			JKekkaRegisterInputDialogFactory.createDialog(
//					JKekkaRegisterFrameCtrl.this,5,comment);

		// edit s.inoue 2009/12/22
		// COLUMN_IDX_KEKKA_STR→12
		textAreaField.addKeyListener(new KeyAdapter(){
		// move s.inoue 2010/04/27
//			IKekkaRegisterInputDialog dialog =
//				JKekkaRegisterInputDialogFactory.createDialog(
//						JKekkaRegisterFrameCtrl.this,12);

			@Override
			public void keyPressed(KeyEvent keyEvent) {
				if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {

					// Shiftキーが一緒に押されたか?
					int modfiersKey = keyEvent.getModifiersEx();

					if ((modfiersKey & InputEvent.CTRL_DOWN_MASK) == 0){
						return;
					}else{
						// 健診結果データ入力画面のテーブルから現在選択されているセルのデータを取得
						int columnIndex = model.getSelectedColumn();
						int rowIndex = model.getSelectedRow();
						String kekkaMojiretsu = (String) model.getData(rowIndex,columnIndex);

						// edit s.inoue 2010/06/11
						if (dialogs == null)
							dialogs = JKekkaRegisterInputDialogFactory.createDialog(JKekkaRegisterFrameCtrl.this,columnIndex,kekkaMojiretsu);

						// "結果(文字列)"入力ダイアログのテキストエリアに選択セルデータをセット
						dialogs.setCommentText(kekkaMojiretsu);
						// "結果(文字列)"入力ダイアログ表示
						dialogs.setVisible(true);
						// edit s.inoue 2009/12/06
						// if (dialog.getStatus() == RETURN_VALUE.OK){
						if (dialogs.getStatus() == RETURN_VALUE.YES){
							// "結果(文字列)"入力ダイアログから入力された
							// 文字列を選択セルにセット
							// edit s.inoue 2009/12/22
							// String dialogtxt = dialog.getText().replaceAll("\n", "\r");
							// textField.setText(dialogtxt);
							textAreaField.setText(dialogs.getText());
						}
					}
				}
			}
		});

	}

	/**
	 * テーブルの更新
	 */
	public void refreshTable(String jissiDate, boolean saveColumnWidth, boolean blnClearData, boolean preview) {

		if (saveColumnWidth) {
			/* 列幅を保存しておく */
			this.saveCurrentColumnsWidth();
		}

		this.model.clearAllRow();

		Vector<Hashtable<String, String>> comboData = new Vector<Hashtable<String, String>>();

		// 最低限必要なデータを格納
		validatedData.setKensaJissiDate(jissiDate,false);
		validatedData.setHihokenjyaCode(jTextField_HokenjyaCode.getText(),false);
		validatedData.setHihokenjyaNumber(jTextField_HokenjyaNumber.getText(),false);

		String query = this.createCellDataSql(preview);
		try {
			tableResult = JApplication.kikanDatabase.sendExecuteQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

		/* セルの色を更新する。 */
		this.refreshTableCellColor();

		/* 基準値下限(男性),基準値上限(男性),基準値下限(女性),基準値上限(女性) */
		this.model.setCellComboBoxEditor(hlComboEditor, 11);
		this.model.setCellComboBoxEditor(hanteiComboEditor, 12);

		// 結果(文字列)・コメント欄にてEnterキー押下時に入力用ダイアログ表示用
		TableColumn column = this.model.getColumnModel().getColumn(5);

		// edit s.inoue 2009/10/23 move
		// final JTextField textField = new JTextField();

		// edit s.inoue 2009/10/28
		// 毎回newで呼び出し
		refreshCellAction();

		// 実施区分
		this.model.setCellComboBoxEditor(jisiComboEditor, 6);

		// 表中コメント欄
		column = this.model.getColumnModel().getColumn(13);
		column.setCellEditor(new PopupWindowShowCellEditor(
				this,new JTextField(), 13));

		// 結果(数値)欄の入力値を小数点第2位以下四捨五入して表示
		column = this.model.getColumnModel().getColumn(3);
		column.setCellEditor(new InputDigitHalfUpCellEditor(new JTextField()));

		/* 身長、体重、BMIのセル位置をクリアする。 */
		BMICellPosition = null;
		heightCellPosition = null;
		weightCellPosition = null;

		for (int i = 0; i < tableResult.size(); i++) {
			Vector<String> insertItem = new Vector<String>();
			Hashtable<String, String> databaseItem = tableResult.get(i);

			insertItem.add(databaseItem.get(COLUMN_NAME_KOUMOKUCD));
			insertItem.add(databaseItem.get(COLUMN_NAME_KOUMOKUNAME));
			insertItem.add(databaseItem.get(COLUMN_NAME_KENSA_HOUHOU));

			// 既存のデータの読み込みを行う
			switch (Integer.valueOf(databaseItem.get(COLUMN_NAME_DATA_TYPE))) {
			case 1:

				// 数値の場合
				// add ver2 s.inoue 2009/06/02
				if (blnClearData){
					insertItem.add("");
				}else{
					insertItem.add(JValidate.validateKensaKekkaDecimal(databaseItem.get(COLUMN_NAME_KEKA_TI),
							databaseItem.get(COLUMN_NAME_MAX_BYTE_LENGTH)));
				}
				insertItem.add(null);
				insertItem.add("");

				// ＢＭＩ値の場合は編集できないようにする
				if (databaseItem.get(COLUMN_NAME_KOUMOKUCD).equals(CODE_BMI)) {
					forbitCells.add(new JSimpleTableCellPosition(i, 3));

					BMICellPosition = new JSimpleTableCellPosition(i, 3);
					BMIFormat = new String(databaseItem.get(COLUMN_NAME_MAX_BYTE_LENGTH));
				}

				// 身長及び体重のデータはＢＭＩ計算に必要なためセルの位置を記憶しておく
				// 身長について
				if (databaseItem.get(COLUMN_NAME_KOUMOKUCD).equals(CODE_SHINCHOU)) {
					heightCellPosition = new JSimpleTableCellPosition(i, 3);
				}

				// 体重について
				if (databaseItem.get(COLUMN_NAME_KOUMOKUCD).equals(CODE_TAIJU)) {
					weightCellPosition = new JSimpleTableCellPosition(i, 3);
				}
				break;

			case 2:
				// コードの場合
				insertItem.add("");

				ArrayList<Hashtable<String, String>> codeResult = null;
				Hashtable<String, String> codeResultItem;
				// edit s.inoue 2009/09/29
				query = new String("SELECT KOUMOKU_CD, CODE_NUM, CODE_NAME FROM T_DATA_TYPE_CODE WHERE KOUMOKU_CD = "
						+ JQueryConvert.queryConvert(databaseItem.get(COLUMN_NAME_KOUMOKUCD)));
				try {
					codeResult = JApplication.kikanDatabase.sendExecuteQuery(query);
				} catch (SQLException e) {
					e.printStackTrace();
					logger.error(e.getMessage());
				}

				kekkaComboEditor.delItems(i);

				// 未入力も選べるようにする
				kekkaComboEditor.addItem(i, "");
				for (int j = 0; j < codeResult.size(); j++) {
					codeResultItem = codeResult.get(j);
					kekkaComboEditor.addItem(i, codeResultItem.get(COLUMN_NAME_CODE_NAME));

					// 既存のDBの項目をコンボボックスに表示させる
					if (databaseItem.get(COLUMN_NAME_KEKA_TI).equals(
							codeResultItem.get(COLUMN_NAME_CODE_NUM))) {
						Hashtable<String, String> dataSet = new Hashtable<String, String>();
						// add ver2 s.inoue 2009/06/02
						if (blnClearData){
							dataSet.put("DATA", "");
						}else{
							dataSet.put("DATA", codeResultItem.get(COLUMN_NAME_CODE_NAME));
						}

						dataSet.put("ROW", String.valueOf(i));
						dataSet.put("COLUMN", String.valueOf(4));

						comboData.add(dataSet);
					}
				}

				insertItem.add(null);
				insertItem.add("");
				break;

			case 3:
				// 文字列の場合
				insertItem.add("");
				insertItem.add(null);
				// add ver2 s.inoue 2009/06/02
				if (blnClearData){
					insertItem.add("");
				}else{
					insertItem.add(JValidate.validateKensaKekkaText(databaseItem
							.get(COLUMN_NAME_KEKA_TI), databaseItem.get(COLUMN_NAME_MAX_BYTE_LENGTH)));
				}
				break;
			}

			/*
			 * 既存の実施区分をコンボボックスにコード名として表示する
			 */
			insertItem.add(null);
			jisiComboEditor.delItems(i);
			for (int k = 0; k < JApplication.jishiKBNTable.length; k++) {
				jisiComboEditor.addItem(i, JApplication.jishiKBNTable[k]);
			}
			if (databaseItem.get(COLUMN_NAME_JISI_KBN).isEmpty()) {
				Hashtable<String, String> dataSet = new Hashtable<String, String>();
				dataSet.put("DATA", JApplication.jishiKBNTable[1]);
				dataSet.put("ROW", String.valueOf(i));
				dataSet.put("COLUMN", String.valueOf(6));
				comboData.add(dataSet);
			} else {
				Hashtable<String, String> dataSet = new Hashtable<String, String>();
				// add ver2 s.inoue 2009/06/02
				if (blnClearData){
					dataSet.put("DATA", JApplication.jishiKBNTable[1]);
				}else{
					dataSet.put("DATA", JApplication.jishiKBNTable[Integer.valueOf(databaseItem.get(COLUMN_NAME_JISI_KBN))]);
				}
				dataSet.put("ROW", String.valueOf(i));
				dataSet.put("COLUMN", String.valueOf(6));
				comboData.add(dataSet);
			}

			/* 基準値下限(男性),基準値上限(男性),基準値下限(女性),基準値上限(女性)を */
			/* テーブルに表示 */
			// 既存のデータの読み込みを行う
			// edit ver2 s.inoue 2009/09/07
			// insertItem.add(databaseItem.get(COLUMN_NAME_DS_KAGEN));
			// insertItem.add(databaseItem.get(COLUMN_NAME_DS_JYOUGEN));
			// insertItem.add(databaseItem.get(COLUMN_NAME_JS_KAGEN));
			// insertItem.add(databaseItem.get(COLUMN_NAME_JS_JYOUGEN));
			String LimitFormat = databaseItem.get(COLUMN_NAME_MAX_BYTE_LENGTH);
			String limitValeDsKagen = databaseItem.get(COLUMN_NAME_DS_KAGEN);
			String limitValeDsJyougen = databaseItem.get(COLUMN_NAME_DS_JYOUGEN);
			String limitValeJsKagen = databaseItem.get(COLUMN_NAME_JS_KAGEN);
			String limitValeJsJyougen = databaseItem.get(COLUMN_NAME_JS_JYOUGEN);
			// edit s.inoue 2009/09/28
			// 数値項目のみ基準値を設定する
			switch (Integer.valueOf(databaseItem.get(COLUMN_NAME_DATA_TYPE))) {
			case 1:
				if (!limitValeDsKagen.equals("")){
					insertItem.add(JValidate.DecimalFormatValue(limitValeDsKagen,LimitFormat));
				}else{
					insertItem.add("");
				}
				if (!limitValeDsJyougen.equals("")){
					insertItem.add(JValidate.DecimalFormatValue(limitValeDsJyougen,LimitFormat));
				}else{
					insertItem.add("");
				}
				if (!limitValeJsKagen.equals("")){
					insertItem.add(JValidate.DecimalFormatValue(limitValeJsKagen,LimitFormat));
				}else{
					insertItem.add("");
				}
				if (!limitValeJsJyougen.equals("")){
					insertItem.add(JValidate.DecimalFormatValue(limitValeJsJyougen,LimitFormat));
				}else{
					insertItem.add("");
				}
				break;
			case 2:
			case 3:
				// edit s.inoue 2009/09/29 1項目多い
				insertItem.add("");
				insertItem.add("");
				insertItem.add("");
				insertItem.add("");
				break;
			}

			/*
			 * 既存のHLコード値をコンボボックスにコード名として表示する
			 */
			insertItem.add(null);
			hlComboEditor.delItems(i);
			for (int k = 0; k < hlCodeTable.length; k++) {
				hlComboEditor.addItem(i, hlCodeTable[k]);
			}
			if (databaseItem.get(COLUMN_NAME_H_L).isEmpty()) {
				Hashtable<String, String> dataSet = new Hashtable<String, String>();
				dataSet.put("DATA", hlCodeTable[0]);
				dataSet.put("ROW", String.valueOf(i));
				/* 基準値下限(男性),基準値上限(男性),基準値下限(女性),基準値上限(女性)を */
				/* テーブルに表示 */
				dataSet.put("COLUMN", String.valueOf(11));
				comboData.add(dataSet);
			} else {
				Hashtable<String, String> dataSet = new Hashtable<String, String>();
				// add ver2 s.inoue 2009/06/02
				if (blnClearData){
					dataSet.put("DATA", "");
				}else{
					dataSet.put("DATA", hlCodeTable[Integer.valueOf(databaseItem.get(COLUMN_NAME_H_L))]);
				}
				dataSet.put("ROW", String.valueOf(i));
				/* 基準値下限(男性),基準値上限(男性),基準値下限(女性),基準値上限(女性)を */
				/* テーブルに表示 */
				dataSet.put("COLUMN", String.valueOf(11));
				comboData.add(dataSet);
			}

			/*
			 * 既存の判定区分のコード値をコンボボックスにコード名として表示する
			 */
			insertItem.add(null);
			hanteiComboEditor.delItems(i);
			for (int k = 0; k < hanteiCodeTable.length; k++) {
				hanteiComboEditor.addItem(i, hanteiCodeTable[k]);
			}
			if (databaseItem.get(COLUMN_NAME_HANTEI).isEmpty()) {
				Hashtable<String, String> dataSet = new Hashtable<String, String>();
				dataSet.put("DATA", hanteiCodeTable[0]);
				dataSet.put("ROW", String.valueOf(i));
				/* 基準値下限(男性),基準値上限(男性),基準値下限(女性),基準値上限(女性)を */
				/* テーブルに表示 */
				dataSet.put("COLUMN", String.valueOf(12));
				comboData.add(dataSet);
			} else {
				Hashtable<String, String> dataSet = new Hashtable<String, String>();

				// add ver2 s.inoue 2009/06/02
				if (blnClearData){
					dataSet.put("DATA", "");
				}else{
					dataSet.put("DATA", hanteiCodeTable[Integer.valueOf(databaseItem.get(COLUMN_NAME_HANTEI))]);
				}
				dataSet.put("ROW", String.valueOf(i));
				// dataSet.put("COLUMN", String.valueOf(9));
				/* 基準値下限(男性),基準値上限(男性),基準値下限(女性),基準値上限(女性)を */
				/* テーブルに表示 */
				dataSet.put("COLUMN", String.valueOf(12));
				comboData.add(dataSet);
			}
			// add ver2 s.inoue 2009/06/02
			if (blnClearData){
				insertItem.add("");
			}else{
				insertItem.add(databaseItem.get(COLUMN_NAME_KOMENTO));
			}

			// edit ver2 s.inoue 2009/09/07
			// insertItem.add(databaseItem.get(COLUMN_NAME_KAGEN));
			// insertItem.add(databaseItem.get(COLUMN_NAME_JYOUGEN));

			String maxValeKagen = databaseItem.get(COLUMN_NAME_KAGEN);
			String maxValeJyougen = databaseItem.get(COLUMN_NAME_JYOUGEN);

			if (!maxValeKagen.equals("")){
				insertItem.add(JValidate.DecimalFormatValue(maxValeKagen,LimitFormat));
			}else{
				insertItem.add("");
			}

			// edit s.inoue 2009/09/14
			if (!maxValeJyougen.equals("")){
				if (databaseItem.get(COLUMN_NAME_KOUMOKUCD).equals("2A040000001930102")){
					insertItem.add("99.9");
				}else{
					insertItem.add(JValidate.DecimalFormatValue(maxValeJyougen,LimitFormat));
				}
			}else{
				insertItem.add("");
			}

			// 一行ごとにデータを挿入していく
			this.model.addData(insertItem);
		}

		for (int i = 0; i < comboData.size(); i++) {
			this.model.setData(comboData.get(i).get("DATA"), Integer
					.valueOf(comboData.get(i).get("ROW")), Integer
					.valueOf(comboData.get(i).get("COLUMN")));
		}

		// フレーム上部のコンボボックスに関して既存データの読み込みを行う

		// 受付ID,年度の追加対応
		// 検査結果データ特定テーブルから指定受付ID,検査実施年月日のレコードを1件取得する
		TKensakekaTokutei kensaTokutei = null;
		try {

			// add ver2 s.inoue 2009/06/25
			String uketuke_id = "";
			if (isCopy && !preview){
				uketuke_id = validatedData.getUketukePre_id();
			}else{
				uketuke_id = validatedData.getUketuke_id();
			}

			kensaTokutei = kensakekaTokuteiDao.selectByPrimaryKey(
					new Long(uketuke_id),
					new Integer(jissiDate));

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (kensaTokutei != null) {
			// 総合コメントに関して表示を行う
			jEditorPane_Comment.setText(kensaTokutei.getKOMENTO());

			// 請求区分に関して表示を行う
			Integer seikyuKbn = kensaTokutei.getSEIKYU_KBN();
			if (seikyuKbn == null) {
				// 必須項目のため未入力の場合は一番上のものを表示させておく
				jComboBox_SeikyuKubun.setSelectedIndex(0);
			} else {
				// 請求区分値に合致する項目を選択状態にする
				// 1: 基本的な健診
				// 2: 基本的な健診＋詳細な健診の場合
				// 3: 基本的な健診＋追加健診項目健診
				// 4: 基本的な健診＋詳細な健診+追加健診項目
				// 5: 人間ドック
				jComboBox_SeikyuKubun.setSelectedIndex(seikyuKbn.intValue() - 1);
			}
		}

		// add ver2 s.inoue 2009/07/09
		if (blnClearData){
			jEditorPane_Comment.setText("");
		}

		/* メタボリックシンドローム判定の結果を表示する。 */
		// add ver2 s.inoue 2009/07/09
		if (blnClearData){
			jComboBox_MetaboHantei.setSelectedIndex(0);
		}else{
			this.showMetablicResult(preview);
		}

		/* 階層化の結果を表示する。 */
		// add ver2 s.inoue 2009/07/09
		if (blnClearData){
			jComboBox_HokenSidouLevel.setSelectedIndex(0);
		}else{
			this.showKaisoukaResult(preview);
		}

		/* 列の幅を初期化または復元する。 */
		this.initializeColumnWidth();
		this.model.refreshTable();

//		/* 行のチェック状態を復元する。 */
//		this.recoverRowCheckStatus();

		this.setTableFocusMap();
	}

	private void saveCurrentColumnsWidth() {
		/* 列幅を保存しておく */
		int columnCount = this.model.getColumnCount();

		int[] columnWidths = new int[columnCount];
		for (int i = 0; i < columnCount; i++) {
			int width = this.model.getColumnModel().getColumn(i).getWidth();
			columnWidths[i] = width;
		}

		this.model.setPreferedColumnWidths(columnWidths);
	}

	// eidt s.inoue 2011/07/29
	private int inthisu_count = 0;

	/**
	 * セルの色に最新の状態を反映する。
	 */
	private void refreshTableCellColor() {
		cellColors.clear();
		forbitCells.clear();

		// テーブルの仕様上、セルの禁止設定のみを先に行う
		for (int i = 0; i < tableResult.size(); i++) {
			Hashtable<String, String> databaseItem = tableResult.get(i);

			int dataType = Integer.valueOf(databaseItem.get(COLUMN_NAME_DATA_TYPE));
			int hisuFlg = Integer.valueOf(databaseItem.get(COLUMN_NAME_HISU_FLG));
			// eidt s.inoue 2011/07/29
			if (i <= 6)
				inthisu_count +=1;

			switch (dataType)
			{
			case 1:
				// 数値の場合
				// edit s.inoue 2009/11/11
				forbitCells.add(new JSimpleTableCellPosition(i, COLUMN_IDX_KOUMOKUCD));
				forbitCells.add(new JSimpleTableCellPosition(i, COLUMN_IDX_CODE_NAME));
				forbitCells.add(new JSimpleTableCellPosition(i, COLUMN_IDX_CODE_KENSA_HOUHOU));

				// edit h.yoshitama 2009/09/18
				if (hisuFlg == HISU_FLG_HISU) {
					cellColors.add(new JSimpleTableCellRendererData(COLOR_HISU,new JSimpleTableCellPosition(i, COLUMN_IDX_KEKKA_NUM)));
				}else{
					cellColors.add(new JSimpleTableCellRendererData(COLOR_ABLE,new JSimpleTableCellPosition(i, COLUMN_IDX_KEKKA_NUM)));
				}

				cellColors.add(new JSimpleTableCellRendererData(COLOR_DISABLE,new JSimpleTableCellPosition(i, COLUMN_IDX_KEKKA_CODE)));
				cellColors.add(new JSimpleTableCellRendererData(COLOR_DISABLE,new JSimpleTableCellPosition(i, COLUMN_IDX_KEKKA_STR)));

				forbitCells.add(new JSimpleTableCellPosition(i, COLUMN_IDX_KEKKA_CODE));
				forbitCells.add(new JSimpleTableCellPosition(i, COLUMN_IDX_KEKKA_STR));

				cellColors.add(new JSimpleTableCellRendererData(COLOR_HISU,new JSimpleTableCellPosition(i, COLUMN_IDX_JISIKBN)));

				forbitCells.add(new JSimpleTableCellPosition(i, COLUMN_IDX_DS_KAGEN));
				forbitCells.add(new JSimpleTableCellPosition(i, COLUMN_IDX_DS_JYOUGEN));
				forbitCells.add(new JSimpleTableCellPosition(i, COLUMN_IDX_JS_KAGEN));
				forbitCells.add(new JSimpleTableCellPosition(i, COLUMN_IDX_JS_JYOUGEN));
				forbitCells.add(new JSimpleTableCellPosition(i, COLUMN_IDX_HL));
				// edit s.inoue 20080908 6-11⇒7-12
				forbitCells.add(new JSimpleTableCellPosition(i, COLUMN_IDX_HANTEI));
				cellColors.add(new JSimpleTableCellRendererData(COLOR_ABLE,new JSimpleTableCellPosition(i, COLUMN_IDX_KOMENTO)));
				break;

			case 2:
				// コードの場合
				forbitCells.add(new JSimpleTableCellPosition(i, COLUMN_IDX_KOUMOKUCD));
				forbitCells.add(new JSimpleTableCellPosition(i, COLUMN_IDX_CODE_NAME));
				forbitCells.add(new JSimpleTableCellPosition(i, COLUMN_IDX_CODE_KENSA_HOUHOU));

				forbitCells.add(new JSimpleTableCellPosition(i, COLUMN_IDX_KEKKA_NUM));

				cellColors.add(new JSimpleTableCellRendererData(COLOR_DISABLE,
						new JSimpleTableCellPosition(i, COLUMN_IDX_KEKKA_NUM)));

				// edit h.yoshitama 2009/09/18
				if (hisuFlg == HISU_FLG_HISU) {
					cellColors.add(new JSimpleTableCellRendererData(COLOR_HISU,
							new JSimpleTableCellPosition(i, COLUMN_IDX_KEKKA_CODE)));
				}else{
					cellColors.add(new JSimpleTableCellRendererData(COLOR_ABLE,new JSimpleTableCellPosition(i, COLUMN_IDX_KEKKA_CODE)));
				}

				cellColors.add(new JSimpleTableCellRendererData(COLOR_DISABLE,new JSimpleTableCellPosition(i, COLUMN_IDX_KEKKA_STR)));

				forbitCells.add(new JSimpleTableCellPosition(i, COLUMN_IDX_KEKKA_STR));

				cellColors.add(new JSimpleTableCellRendererData(COLOR_HISU,new JSimpleTableCellPosition(i, COLUMN_IDX_JISIKBN)));

				forbitCells.add(new JSimpleTableCellPosition(i, COLUMN_IDX_DS_KAGEN));
				forbitCells.add(new JSimpleTableCellPosition(i, COLUMN_IDX_DS_JYOUGEN));
				forbitCells.add(new JSimpleTableCellPosition(i, COLUMN_IDX_JS_KAGEN));
				forbitCells.add(new JSimpleTableCellPosition(i, COLUMN_IDX_JS_JYOUGEN));
				forbitCells.add(new JSimpleTableCellPosition(i, COLUMN_IDX_HL));
				// edit s.inoue 20080908 6-11⇒7-12
				forbitCells.add(new JSimpleTableCellPosition(i, COLUMN_IDX_HANTEI));
				// add h.yoshitama 2009/09/18
				cellColors.add(new JSimpleTableCellRendererData(COLOR_ABLE,new JSimpleTableCellPosition(i, COLUMN_IDX_KOMENTO)));
				break;

			case 3:
				// 文字列の場合
				forbitCells.add(new JSimpleTableCellPosition(i, COLUMN_IDX_KOUMOKUCD));
				forbitCells.add(new JSimpleTableCellPosition(i, COLUMN_IDX_CODE_NAME));
				forbitCells.add(new JSimpleTableCellPosition(i, COLUMN_IDX_CODE_KENSA_HOUHOU));

				forbitCells.add(new JSimpleTableCellPosition(i, COLUMN_IDX_KEKKA_NUM));
				forbitCells.add(new JSimpleTableCellPosition(i, COLUMN_IDX_KEKKA_CODE));

				cellColors.add(new JSimpleTableCellRendererData(COLOR_DISABLE,
						new JSimpleTableCellPosition(i, COLUMN_IDX_KEKKA_NUM)));
				cellColors.add(new JSimpleTableCellRendererData(COLOR_DISABLE,
						new JSimpleTableCellPosition(i, COLUMN_IDX_KEKKA_CODE)));

				// edit h.yoshitama 2009/09/18
				if (hisuFlg == HISU_FLG_HISU) {
					cellColors.add(new JSimpleTableCellRendererData(COLOR_HISU,
							new JSimpleTableCellPosition(i, COLUMN_IDX_KEKKA_STR)));

				}else{
					cellColors.add(new JSimpleTableCellRendererData(COLOR_ABLE,new JSimpleTableCellPosition(i, COLUMN_IDX_KEKKA_STR)));
				}

				cellColors.add(new JSimpleTableCellRendererData(COLOR_HISU,
						new JSimpleTableCellPosition(i, COLUMN_IDX_JISIKBN)));

				forbitCells.add(new JSimpleTableCellPosition(i, COLUMN_IDX_DS_KAGEN));
				forbitCells.add(new JSimpleTableCellPosition(i, COLUMN_IDX_DS_JYOUGEN));
				forbitCells.add(new JSimpleTableCellPosition(i, COLUMN_IDX_JS_KAGEN));
				forbitCells.add(new JSimpleTableCellPosition(i, COLUMN_IDX_JS_JYOUGEN));
				forbitCells.add(new JSimpleTableCellPosition(i, COLUMN_IDX_HL));
				// edit s.inoue 20080908 6-11⇒7-12
				forbitCells.add(new JSimpleTableCellPosition(i, COLUMN_IDX_HANTEI));
				// add h.yoshitama 2009/09/18
				cellColors.add(new JSimpleTableCellRendererData(COLOR_ABLE,new JSimpleTableCellPosition(i, COLUMN_IDX_KOMENTO)));
				break;
			}
			// del s.inoue 2009/11/11
			// forbitCells.add(new JSimpleTableCellPosition(i, 14));
			// forbitCells.add(new JSimpleTableCellPosition(i, 15));

		}

		/*
		 * セルの編集禁止を設定した後はコンボボックスの設定がクリアされるため再度設定を行いその後データをセットする
		 * 順番を入れ替えると正常に動作しない可能性があるため注意
		 */
		this.model.setCellEditForbid(forbitCells);
		// edit s.inoue 2009/11/11
		this.model.setCellComboBoxEditor(kekkaComboEditor, COLUMN_IDX_KEKKA_CODE);
		this.model.setCellComboBoxEditor(jisiComboEditor, COLUMN_IDX_JISIKBN);
	}

	private void showKaisoukaResult(boolean preview) {
		String query;
		/* 階層化の結果を表示する。 */
		List<Hashtable<String, String>> textFieldResult = new ArrayList<Hashtable<String, String>>();
		query = this.createKaisoukaSql(preview);
		try {
			textFieldResult = JApplication.kikanDatabase.sendExecuteQuery(query);
		} catch (SQLException f) {
			f.printStackTrace();
		}

		if (textFieldResult.size() != 0) {
			if (textFieldResult.get(0).get(COLUMN_NAME_KEKA_TI).equals("")) {
				jComboBox_HokenSidouLevel.setSelectedIndex(0);
			} else {
				switch (Integer.valueOf(textFieldResult.get(0).get(COLUMN_NAME_KEKA_TI))) {
				// 積極的支援が選択されている場合
				case 1:
					jComboBox_HokenSidouLevel.setSelectedIndex(1);
					break;

				// 動機づけ支援が選択されている場合
				case 2:
					jComboBox_HokenSidouLevel.setSelectedIndex(2);
					break;

				// なし
				case 3:
					jComboBox_HokenSidouLevel.setSelectedIndex(3);
					break;

				// 判定不能
				case 4:
					jComboBox_HokenSidouLevel.setSelectedIndex(4);
					break;
				}
			}
		}
	}

	// add ver2 s.inoue 2009/06/25
	private void showMetablicResult(boolean preview) {
		String query;
		/* メタボリックシンドローム判定の結果を表示する。 */
		query = this.createMetabolicSql(preview);

		List<Hashtable<String, String>> textFieldResult = new ArrayList<Hashtable<String, String>>();

		try {
			textFieldResult = JApplication.kikanDatabase.sendExecuteQuery(query);
		} catch (SQLException f) {
			f.printStackTrace();
		}

		if (textFieldResult.size() != 0) {
			if (textFieldResult.get(0).get(COLUMN_NAME_KEKA_TI).equals("")) {
				// 未判定
				jComboBox_MetaboHantei.setSelectedIndex(0);
			} else {
				switch (Integer.valueOf(textFieldResult.get(0).get(COLUMN_NAME_KEKA_TI))) {
				// 基準該当の場合
				case 1:
					jComboBox_MetaboHantei.setSelectedIndex(1);
					break;

				// 予備軍該当の場合
				case 2:
					jComboBox_MetaboHantei.setSelectedIndex(2);
					break;

				// 非該当の場合
				case 3:
					jComboBox_MetaboHantei.setSelectedIndex(3);
					break;

				// 判定不能の場合
				case 4:
					jComboBox_MetaboHantei.setSelectedIndex(4);
					break;
				}
			}
		}
	}

	private String createKaisoukaSql(boolean preview) {
		String query;
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT master.KOUMOKU_CD AS KOUMOKUCD,");
		buffer.append("master.KOUMOKU_NAME AS KOUMOKUNAME,");
		buffer.append("master.KENSA_HOUHOU AS KENSA_HOUHOU,");
		buffer.append("sonota.KEKA_TI,");
		buffer.append("master.DS_KAGEN,");
		buffer.append("master.DS_JYOUGEN,");
		buffer.append("master.JS_KAGEN,");
		buffer.append("master.JS_JYOUGEN,");
		buffer.append("master.DATA_TYPE,");
		buffer.append("master.MAX_BYTE_LENGTH,");
		buffer.append("sonota.KOMENTO,");
		buffer.append("sonota.H_L,");
		buffer.append("sonota.HANTEI ");
		buffer.append("FROM ");
		buffer.append("( ");
		buffer.append("T_KENSHINMASTER master ");
		buffer.append("LEFT JOIN T_KENSACENTER_MASTER kensa ");
		buffer.append("ON kensa.KOUMOKU_CD = master.KOUMOKU_CD ");
		buffer.append(" AND ");
		buffer.append("kensa.KENSA_CENTER_CD = ");
		buffer.append(JQueryConvert
						.queryConvert(validatedData.getKensaCenterCode()));
		buffer.append(") ");
		buffer.append("LEFT JOIN T_KENSAKEKA_SONOTA sonota ");
		buffer.append("ON sonota.KOUMOKU_CD = master.KOUMOKU_CD ");
		buffer.append(" AND ");
		buffer.append("sonota.KENSA_NENGAPI = ");
		buffer.append(JQueryConvert.queryConvert(validatedData.getKensaJissiDate()));
		buffer.append(" AND ");
		buffer.append("sonota.UKETUKE_ID = ");
		// add ver2 s.inoue 2009/06/25
		if (isCopy && !preview){
			buffer.append(JQueryConvert.queryConvert(validatedData.getUketukePre_id()));
		}else{
			buffer.append(JQueryConvert.queryConvert(validatedData.getUketuke_id()));
		}

		buffer.append(" ");
		buffer.append("WHERE master.KOUMOKU_CD IN ");
		buffer.append("( ");
		buffer.append("SELECT KOUMOKU_CD ");
		buffer.append("FROM T_KENSHIN_P_S ");
		buffer.append("WHERE K_P_NO = ");
		buffer.append(JQueryConvert.queryConvert(String
						.valueOf(kenshinPatternNumber)) + " AND ");
		buffer.append("KOUMOKU_CD IN ");
		buffer.append("( ");

		// 保健指導レベルのみを対象とする
		buffer.append(JQueryConvert.queryConvert("9N506000000000011") + ") " + ") ");
		buffer.append("AND " + "master.HKNJANUM = ");
		buffer.append(JQueryConvert.queryConvert(
				validatedData.getHokenjyaNumber()));
		query = buffer.toString();
		return query;
	}

	private String createMetabolicSql(boolean preview) {
		String query;
		// メタボリックシンドローム判定に関する表示を行う
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT master.KOUMOKU_CD AS KOUMOKUCD,");
		buffer.append("master.KOUMOKU_NAME AS KOUMOKUNAME,");
		buffer.append("master.KENSA_HOUHOU AS KENSA_HOUHOU,");
		buffer.append("sonota.KEKA_TI,");
		buffer.append("master.DS_KAGEN,");
		buffer.append("master.DS_JYOUGEN,");
		buffer.append("master.JS_KAGEN,");
		buffer.append("master.JS_JYOUGEN,");
		buffer.append("master.DATA_TYPE,");
		buffer.append("master.MAX_BYTE_LENGTH,");
		buffer.append("sonota.KOMENTO,");
		buffer.append("sonota.H_L,");
		buffer.append("sonota.HANTEI ");
		buffer.append("FROM ");
		buffer.append("( ");
		buffer.append("T_KENSHINMASTER master ");
		buffer.append("LEFT JOIN T_KENSACENTER_MASTER kensa ");
		buffer.append("ON kensa.KOUMOKU_CD = master.KOUMOKU_CD ");
		buffer.append(" AND ");
		buffer.append("kensa.KENSA_CENTER_CD = ");
		buffer.append(JQueryConvert
								.queryConvert(validatedData.getKensaCenterCode()));
		buffer.append(") ");
		buffer.append("LEFT JOIN T_KENSAKEKA_SONOTA sonota ");
		buffer.append("ON sonota.KOUMOKU_CD = master.KOUMOKU_CD ");
		buffer.append(" AND ");
		buffer.append("sonota.KENSA_NENGAPI = ");
		buffer.append(JQueryConvert.queryConvert(validatedData.getKensaJissiDate()));
		buffer.append(" AND ");
		buffer.append("sonota.UKETUKE_ID = ");
		// add ver2 s.inoue 2009/06/25
		if (isCopy && !preview){
			buffer.append(JQueryConvert.queryConvert(validatedData.getUketukePre_id()));
		}else{
			buffer.append(JQueryConvert.queryConvert(validatedData.getUketuke_id()));
		}

		buffer.append(" ");
		buffer.append("WHERE master.KOUMOKU_CD IN ");
		buffer.append("( ");
		buffer.append("SELECT KOUMOKU_CD ");
		buffer.append("FROM T_KENSHIN_P_S ");
		buffer.append("WHERE K_P_NO = ");
		buffer.append(JQueryConvert.queryConvert(
				String.valueOf(kenshinPatternNumber)));
		buffer.append(" AND ");
		buffer.append("KOUMOKU_CD IN ");
		buffer.append("( ");
		//		 メタボリック診断のみを対象とする
		buffer.append(JQueryConvert.queryConvert("9N501000000000011"));
		buffer.append(") ");
		buffer.append(") ");
		buffer.append("AND ");
		buffer.append("master.HKNJANUM = ");
		buffer.append(JQueryConvert.queryConvert(
				validatedData.getHokenjyaNumber()));
		query = buffer.toString();
		return query;
	}

	private String createCellDataSql(boolean preview) {
		StringBuffer buffer = new StringBuffer();

		buffer.append("SELECT master.KOUMOKU_CD AS KOUMOKUCD,");
		// edit ver2 s.inoue 2009/07/23
		//buffer.append("master.KOUMOKU_NAME AS KOUMOKUNAME,");
		buffer.append("  case when master.KOUMOKU_NAME ='生活機能問診1' then '質問1-1' ");
		buffer.append("  when master.KOUMOKU_NAME ='生活機能問診2' then '質問1-2' ");
		buffer.append("  when master.KOUMOKU_NAME ='生活機能問診3' then '質問1-3' ");
		buffer.append("  when master.KOUMOKU_NAME ='生活機能問診4' then '質問4' ");
		buffer.append("  when master.KOUMOKU_NAME ='生活機能問診5' then '質問5' ");
		buffer.append("  when master.KOUMOKU_NAME ='生活機能問診6' then '質問6' ");
		buffer.append("  when master.KOUMOKU_NAME ='生活機能問診7' then '質問7' ");
		buffer.append("  when master.KOUMOKU_NAME ='生活機能問診8' then '質問8' ");
		buffer.append("  when master.KOUMOKU_NAME ='生活機能問診9' then '質問9' ");
		buffer.append("  when master.KOUMOKU_NAME ='生活機能問診10' then '質問10' ");
		buffer.append("  when master.KOUMOKU_NAME ='生活機能問診11' then '質問11' ");
		buffer.append("  when master.KOUMOKU_NAME ='生活機能問診12' then '質問12' ");
		buffer.append("  when master.KOUMOKU_NAME ='生活機能問診13' then '質問13' ");
		buffer.append("  when master.KOUMOKU_NAME ='生活機能問診14' then '質問14' ");
		buffer.append("  when master.KOUMOKU_NAME ='生活機能問診15' then '質問15' ");
		buffer.append("  when master.KOUMOKU_NAME ='生活機能問診16' then '質問16' ");
		buffer.append("  when master.KOUMOKU_NAME ='生活機能問診17' then '質問17' ");
		buffer.append("  when master.KOUMOKU_NAME ='生活機能問診18' then '質問18' ");
		buffer.append("  when master.KOUMOKU_NAME ='生活機能問診19' then '質問19' ");
		buffer.append("  when master.KOUMOKU_NAME ='生活機能問診20' then '質問20' ");
		buffer.append("  when master.KOUMOKU_NAME ='生活機能問診21' then '質問21' ");
		buffer.append("  when master.KOUMOKU_NAME ='生活機能問診22' then '質問22' ");
		buffer.append("  when master.KOUMOKU_NAME ='生活機能問診23' then '質問23' ");
		buffer.append("  when master.KOUMOKU_NAME ='生活機能問診24' then '質問24' ");
		buffer.append("  when master.KOUMOKU_NAME ='生活機能問診25' then '質問25' ");
		buffer.append("  else master.KOUMOKU_NAME end KOUMOKUNAME, ");

		buffer.append("master.KENSA_HOUHOU AS KENSA_HOUHOU,");
		buffer.append("sonota.JISI_KBN,");
		buffer.append("sonota.KEKA_TI,");
		buffer.append("master.KAGEN,");
		buffer.append("master.DS_KAGEN,");
		buffer.append("master.DS_JYOUGEN,");
		buffer.append("master.JS_KAGEN,");
		buffer.append("master.JS_JYOUGEN,");
		buffer.append("master.KAGEN,");
		buffer.append("master.JYOUGEN,");
		buffer.append("master.DATA_TYPE,");
		buffer.append("master.MAX_BYTE_LENGTH,");
		buffer.append("sonota.KOMENTO,");
		buffer.append("sonota.H_L,");
		buffer.append("sonota.HANTEI, ");
		buffer.append("master.HISU_FLG as HISU_FLG ");
		buffer.append("FROM ");
		buffer.append("( ");
		buffer.append("T_KENSHINMASTER master ");
		buffer.append("LEFT JOIN T_KENSACENTER_MASTER kensa ");
		buffer.append("ON kensa.KOUMOKU_CD = master.KOUMOKU_CD ");
		buffer.append(" AND ");
		buffer.append("kensa.KENSA_CENTER_CD = ");
		buffer.append(JQueryConvert
								.queryConvert(validatedData.getKensaCenterCode()));
		buffer.append(") ");
		buffer.append("LEFT JOIN T_KENSAKEKA_SONOTA sonota ");
		buffer.append("ON sonota.KOUMOKU_CD = master.KOUMOKU_CD ");
		buffer.append(" AND ");
		buffer.append("sonota.KENSA_NENGAPI = ");
		buffer.append(JQueryConvert.queryConvert(validatedData.getKensaJissiDate()));
		buffer.append(" AND ");
		buffer.append("sonota.UKETUKE_ID = ");
		// add ver2 s.inoue 2009/06/25
		if (isCopy && !preview){
			buffer.append(JQueryConvert.queryConvert(validatedData.getUketukePre_id()));
		}else{
			buffer.append(JQueryConvert.queryConvert(validatedData.getUketuke_id()));
		}
		buffer.append(" ");

		String patternNumber = "";
		patternNumber = JQueryConvert.queryConvert(String.valueOf(kenshinPatternNumber));

		buffer.append(" LEFT JOIN T_KENSHIN_P_S ps ");
		buffer.append(" ON ps.KOUMOKU_CD = master.KOUMOKU_CD   ");
		buffer.append(" AND ps.K_P_NO =    ");
		buffer.append(patternNumber);

		buffer.append("WHERE master.KOUMOKU_CD IN ");
		buffer.append("( ");
		buffer.append("SELECT KOUMOKU_CD ");
		buffer.append("FROM T_KENSHIN_P_S ");
		buffer.append("WHERE K_P_NO = ");
		buffer.append(patternNumber);
		buffer.append(" AND ");
		buffer.append("KOUMOKU_CD NOT IN ");
		buffer.append("( ");
		//メタボリック診断、保健指導レベルを除く
		buffer.append(JQueryConvert.queryConvertAppendComma("9N501000000000011"));
		buffer.append(JQueryConvert.queryConvert("9N506000000000011") + ") ");

		buffer.append(") ");
		buffer.append("AND " + "master.HKNJANUM = ");
		buffer.append(JQueryConvert.queryConvert(validatedData.getHokenjyaNumber()));
		buffer.append(" order by ps.LOW_ID ");

		String query = buffer.toString();
		return query;
	}

	// add s.inoue 20081119
	/**
	 * 健診パターンに健診項目が存在するかどうか
	 */
	public static boolean existPattarnKensaKoumoku(
			int patternNumber,
			String koumokuCd
			)
	{
			boolean retChk = false;

			ArrayList<Hashtable<String, String>> result
				= new ArrayList<Hashtable<String, String>>();

			try{

				// 変更後のパターンに健診項目が含まれるかどうか
				StringBuffer buffer = new StringBuffer();
				buffer.append(" SELECT KOUMOKU_CD FROM T_KENSHIN_P_S ");
				buffer.append(" where K_P_NO = ?");
				buffer.append(" and KOUMOKU_CD = ?");
				String query = buffer.toString();

				String[] params = {String.valueOf(patternNumber),koumokuCd };

				result = JApplication.kikanDatabase.sendExecuteQuery(query, params);

			}catch(SQLException ex){
				ex.printStackTrace();
			}

			if (result.size()==0)
				retChk=true;

		return retChk;
	}

	/**
	 * 請求区分による検査項目チェック
	 */
	public static boolean isNotExistKensaKoumoku(
			String hokenjyaNumber,
			String uketukeId,
			String kensaDate,
			String seikyuKbn,
			String koumokuCd,
			String kekkati,
			String jisiKbn
			)
	{
			boolean retChk = false;
			boolean hanteiHisu = false;

			// 例外の項目(質問票に関わる項目)
			String KoumokuHd = koumokuCd.substring(0, 3);
			if(KoumokuHd.equals("9N7") ||
					koumokuCd.equals(CODE_HOKEN_SHIDOU) ||
					koumokuCd.equals(CODE_SEIKATU_KAIZEN) ||
					koumokuCd.equals(CODE_SAIKETSU_ZIKAN) ||
					koumokuCd.equals(CODE_ISHINOHANDAN_1) ||
					koumokuCd.equals(CODE_ISHINOHANDAN_2) ||
					koumokuCd.equals(CODE_ISHINOHANDAN_3) ||
					koumokuCd.equals(CODE_ISHINOHANDAN_4)){
				return false;
			}

			// 基本的な健診,詳細な健診,追加健診項目,人間ドック
			StringBuffer buffer = new StringBuffer();
			String[] params =null;

			// edit s.inoue 20081126 新規登録時のチェックが効かない為

			buffer.append(" select master.HISU_FLG ");
			buffer.append(" from T_KENSHINMASTER master ");
			buffer.append(" where master.HKNJANUM = ?");
			buffer.append(" and KOUMOKU_CD = ?");

			params = new String []{ hokenjyaNumber,koumokuCd  };

			String query = buffer.toString();

			ArrayList<Hashtable<String, String>> result = null;
			try{
				result = JApplication.kikanDatabase.sendExecuteQuery(query, params);
			}catch(SQLException ex){
				ex.printStackTrace();
			}

			for (Hashtable<String, String> item : result) {
				String hisuFlg = item.get("HISU_FLG");
				//String tanka = item.get("TANKA_KENSIN");
				// 登録対象のフラグチェック
				if (jisiKbn.equals("1")){
					if (!kekkati.equals("")){
						hanteiHisu = true;
					}
				}else if (jisiKbn.equals("2") ||
						jisiKbn.equals("0")){
					if (kekkati.equals("")){
						hanteiHisu = true;
					}
				}

				if (hanteiHisu){
					// 整合性確認用
					if (seikyuKbn.equals(JApplication.SEIKYU_KBN_BASE)){
						if (!hisuFlg.equals(JApplication.HISU_FLG_BASE)){
							retChk = true;break;
						}
					}else if (seikyuKbn.equals(JApplication.SEIKYU_KBN_SYOSAI)){
						if (!(hisuFlg.equals(JApplication.HISU_FLG_BASE) ||
								hisuFlg.equals(JApplication.HISU_FLG_SYOSAI))){
							retChk = true;break;
						}
					}else if (seikyuKbn.equals(JApplication.SEIKYU_KBN_TUIKA)){
						if (!(hisuFlg.equals(JApplication.HISU_FLG_BASE) ||
								hisuFlg.equals(JApplication.HISU_FLG_TUIKA))){
							retChk = true;break;
						}
					}
				}

			}
		return retChk;
	}
	// add s.inoue 20081118
	/**
	 * 登録
	 * @return boolean 健診パターン変更による請求区分の該当検査項目の削除
	 */
	public boolean pattarnKensaKoumoku() {

		boolean patarnHanbetu = false;
		cellColors.clear();

		if (tableResult != null) {

			try{

				if (validatedData.setHihokenjyaNumber(jTextField_HokenjyaNumber.getText(),false)
						&& validatedData.setSeikyuKubun((String) jComboBox_SeikyuKubun.getSelectedItem())){

					// del s.inoue 2009/08/13
					// JApplication.kikanDatabase.Transaction();

					ArrayList<Hashtable<String, String>> sonotaResults = null;

					int tmpKenshinPatternNumber=kenshinPatternNumber;

					// edit ver2 s.inoue 2009/04/24
					if (prekenshinPatternNumber != -1){
						kenshinPatternNumber=prekenshinPatternNumber;
					}
					// edit ver2 s.inoue 2009/06/25
					String crequery = this.createCellDataSql(false);
					try {
						sonotaResults = JApplication.kikanDatabase.sendExecuteQuery(crequery);
					} catch (SQLException f) {
						f.printStackTrace();
					}
					kenshinPatternNumber=tmpKenshinPatternNumber;

					// 検査結果その他テーブルに対してパターン外の結果を削除する
					for (int i = 0; i < sonotaResults.size(); i++) {

						Hashtable<String, String> databaseItem = sonotaResults.get(i);

//						// add ver2 s.inoue 2009/04/24
//						String kensakekka = validatedData.getKekka();

						boolean isOtherValidated = (
								 validatedData.setKensaKoumokuCode(databaseItem.get(COLUMN_NAME_KOUMOKUCD)));

						if (isOtherValidated){

							// 検査項目整合性チェック
							boolean validatePattarn = existPattarnKensaKoumoku(
									kenshinPatternNumber,validatedData.getKensaKoumokuCode());

							if (validatePattarn){
								// edit s.inoue 20081202 1回目のみ表示
								if (!patarnHanbetu){
									RETURN_VALUE retValue = null;
									retValue = JErrorMessage.show("M3637", this);
									if(retValue == RETURN_VALUE.NO) {
										break;
									}
									patarnHanbetu = true;
								}

								// deleteByUketukeIdKensaNengapi⇒delete
								if (! this.isCopy && ! this.isNewKekkaData) {
									kensakekaSonotaDao.delete(
											new Long(validatedData.getUketuke_id()),
											new Integer(this.initialDate),
											new String(validatedData.getKensaKoumokuCode()));
								}
							}
						}
					}

					// del s.inoue 2009/08/13
					// JApplication.kikanDatabase.Commit();

				}
			} catch (SQLException ex1) {
				ex1.printStackTrace();
				try{
					JApplication.kikanDatabase.rollback();
				}catch (SQLException ex2) {}
				return false;
			}
		}

		return patarnHanbetu;
	}

	/**
	 * 複製処理の個人情報登録
	 *
	 * @return boolean 登録の有無
	 */
	private String registerKojinCopy(){

		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO T_KOJIN ( PTNUM, JYUSHIN_SEIRI_NO, YUKOU_KIGEN, HKNJANUM, HIHOKENJYASYO_KIGOU,");
		query.append(" HIHOKENJYASYO_NO, NAME, KANANAME, NICKNAME, BIRTHDAY, SEX,HOME_POST, HOME_ADRS, HOME_BANTI, HOME_TEL1, KEITAI_TEL, ");
		query.append(" FAX, EMAIL, KEITAI_EMAIL, KEIYAKU_TORIMATOME, KOUHUBI, SIHARAI_DAIKOU_BANGO, MADO_FUTAN_K_SYUBETU, MADO_FUTAN_KIHON, ");
		query.append(" MADO_FUTAN_S_SYUBETU, MADO_FUTAN_SYOUSAI, MADO_FUTAN_T_SYUBETU, MADO_FUTAN_TSUIKA, MADO_FUTAN_D_SYUBETU, MADO_FUTAN_DOC, ");
		query.append(" NENDO, UKETUKE_ID, MADO_FUTAN_SONOTA, HOKENJYA_FUTAN_KIHON, HOKENJYA_FUTAN_SYOUSAI, HOKENJYA_FUTAN_TSUIKA, HOKENJYA_FUTAN_DOC ) ");
		query.append(" SELECT ");
		query.append(" PTNUM, ");
		//JYUSHIN_SEIRI_NOを指定
		query.append(JQueryConvert.queryConvert(validatedData.getcopyJyushinkenID()));
		query.append(" ,YUKOU_KIGEN, HKNJANUM, HIHOKENJYASYO_KIGOU, HIHOKENJYASYO_NO, NAME, KANANAME, ");

		query.append(" NICKNAME, BIRTHDAY, SEX, HOME_POST, HOME_ADRS, HOME_BANTI, HOME_TEL1, KEITAI_TEL, FAX, EMAIL, KEITAI_EMAIL, KEIYAKU_TORIMATOME, ");
		query.append(" KOUHUBI, SIHARAI_DAIKOU_BANGO, MADO_FUTAN_K_SYUBETU, MADO_FUTAN_KIHON, MADO_FUTAN_S_SYUBETU, MADO_FUTAN_SYOUSAI, MADO_FUTAN_T_SYUBETU, ");
		query.append(" MADO_FUTAN_TSUIKA, MADO_FUTAN_D_SYUBETU, MADO_FUTAN_DOC, NENDO, ");

		query.append(JQueryConvert.queryConvert(validatedData.getUketuke_id()));
		query.append(" ,MADO_FUTAN_SONOTA, HOKENJYA_FUTAN_KIHON, HOKENJYA_FUTAN_SYOUSAI, HOKENJYA_FUTAN_TSUIKA, HOKENJYA_FUTAN_DOC ");
		query.append(" FROM T_KOJIN WHERE UKETUKE_ID = ");
		query.append(JQueryConvert.queryConvert(validatedData.getUketukePre_id()));

		return query.toString();

	}
	/**
	 * 履歴,名寄せテーブルへ登録処理を実施する。
	 */
	private void registerHistory(){

		TNayoseDao tNayoseDao;

		StringBuilder nayoseQuery = null;
		StringBuilder newNayoseQuery = null;

			// 名寄せNO採番
			long nayoseNo = -1L;
			long retTNayoseNo = 0L;

			// 現時刻を取得
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String stringTimeStamp = dateFormat.format(Calendar.getInstance().getTime());

			try {
				/* T_NAYOSE Dao を作成する。 */
				tNayoseDao = (TNayoseDao) DaoFactory.createDao(
						JApplication.kikanDatabase.getMConnection(), new TNayose());

				retTNayoseNo = tNayoseDao.selectOldNayoseNo(Long.parseLong(validatedData.getUketukePre_id()));
				// 既に履歴がある場合、その名寄せNoを使用する
				if (retTNayoseNo > 0) {
					nayoseNo = retTNayoseNo;
				}else{
					nayoseNo = tNayoseDao.selectNewNayoseNo();

					// 名寄せテーブル登録処理
					// 受付IDの紐付け処理:1、2をセット
					// 1.受診券情報で氏名かなで紐付けた受付IDを登録
					// 2.受診券登録後に新しい受付番号を登録

					// 1.処理:履歴がない場合
					nayoseQuery = new StringBuilder();
					nayoseQuery.append("INSERT INTO T_NAYOSE (NAYOSE_NO,UKETUKE_ID,UPDATE_TIMESTAMP)");
					nayoseQuery.append("VALUES (");
					nayoseQuery.append(JQueryConvert.queryConvertAppendComma(String.valueOf(nayoseNo)));
					nayoseQuery.append(JQueryConvert.queryConvertAppendComma(validatedData.getUketukePre_id()));
					nayoseQuery.append(JQueryConvert.queryConvert(stringTimeStamp));
					nayoseQuery.append(") ");

					try {
						JApplication.kikanDatabase.sendNoResultQuery(nayoseQuery.toString());
					} catch (SQLException e) {
						logger.error(e.getMessage());

						try {
							JApplication.kikanDatabase.rollback();
						} catch (SQLException g) {
							logger.error(g.getMessage());
						}
					}

				}

				// 2.処理:採番した追加用レコード共通処理
				newNayoseQuery = new StringBuilder();
				newNayoseQuery.append("INSERT INTO T_NAYOSE (NAYOSE_NO,UKETUKE_ID,UPDATE_TIMESTAMP)");
				newNayoseQuery.append("VALUES (");
				newNayoseQuery.append(JQueryConvert.queryConvertAppendComma(String.valueOf(nayoseNo)));
				newNayoseQuery.append(JQueryConvert.queryConvertAppendComma(validatedData.getUketuke_id()));
				newNayoseQuery.append(JQueryConvert.queryConvert(stringTimeStamp));
				newNayoseQuery.append(") ");

				try {
					JApplication.kikanDatabase.sendNoResultQuery(newNayoseQuery.toString());
				} catch (SQLException e) {
					logger.error(e.getMessage());
					try {
						JApplication.kikanDatabase.rollback();
					} catch (SQLException g) {
						logger.error(g.getMessage());
					}
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
	}

	/**
	 * 登録
	 *
	 * @return boolean 登録の有無
	 */
	public boolean register() {
		String query = null;
		Hashtable<String, String> ResultItem = null;
		cellColors.clear();

		String kensaJissiDate = validatedData.getKensaJissiDate();

		if (tableResult != null) {
			/* 健診実施日 */
			String jissiDate = jTextField_Date.getText();

			/*
			 *  初期表示の日付から変更があった場合は、警告を表示する。
			 */
			boolean hasJissiDateChanged = false;
			if (! jissiDate.equals(this.initialDate)) {
				hasJissiDateChanged = true;
			}

			if (hasJissiDateChanged && ! this.isNewKekkaData) {

				/* M3543=確認,健診実施日を[%s]から[%s]に変更して登録してよろしいですか？,1,0 */
				String[] messageParams = {this.initialDate, jissiDate};
				RETURN_VALUE retValue = JErrorMessage.show("M3543", this, messageParams);
				if (retValue != RETURN_VALUE.YES) {
					return false;
				}
			}

			/*
			 * 既に健診結果データが存在し、かつ初期表示の日付から変更があった場合は、
			 * 警告メッセージを表示する。
			 */
			boolean existsKensaKekkaData = existsKensaKekkaData(jissiDate);
			if (existsKensaKekkaData) {
				if (hasJissiDateChanged) {
					/* M3544=確認,[%s]には、すでに結果データが存在します。登録した場合、
					 * 以前に入力した結果データは消えてしまいます。登録してよろしいですか？,1,0 */
					String[] messageParams = {jissiDate};
					RETURN_VALUE retValue = JErrorMessage.show("M3544", this, messageParams);
					if (retValue != RETURN_VALUE.YES) {
						return false;
					}
				}

				mode = UPDATE_MODE;
			}
			else {
				mode = INSERT_MODE;
			}

			// フレームのテキストフィールドなどに関して検証を行う
			if (validatedData.setHihokenjyaCode(jTextField_HokenjyaCode.getText(),false)
					&& validatedData.setHihokenjyaNumber(jTextField_HokenjyaNumber.getText(),false)
					&& validatedData.setNameKana(jTextField_Name.getText())
					&& validatedData.setKensaJissiDate(jissiDate,false)
					&& validatedData.setSougouComment(jEditorPane_Comment.getText())
					&& validatedData.setMetaboHantei((String) jComboBox_MetaboHantei.getSelectedItem())
					&& validatedData.setHokenSidouLevel((String) jComboBox_HokenSidouLevel.getSelectedItem())
					&& validatedData.setSeikyuKubun((String) jComboBox_SeikyuKubun.getSelectedItem())) {

				boolean validateKensa = false;
				boolean hanteiBase = false;
				boolean hanteiSyosai = false;
				boolean hanteiTuika = false;
				boolean notExtMessage = false;
				boolean validateInput = false;

				int intBaseCnt = 0;
				int intSyosaiCnt = 0;
				int intTuikaCnt = 0;
				int intAllCnt = 0;
				// add ver2 s.inoue 2009/06/29
				TKojinDao tKojinDao;

				if (validateAsRegisterPushed(validatedData)) {
					if (validatedData.isValidateAsDataSet()) {
						try {

							JApplication.kikanDatabase.Transaction();

							// add ver2 s.inoue 2009/06/02
							if (isCopy){

								// add ver2 s.inoue 2009/06/29
								try {
									/* T_KOJIN Dao を作成する。 */
									tKojinDao = (TKojinDao) DaoFactory.createDao(
											JApplication.kikanDatabase.getMConnection(), new TKojin());

									// add ver2 s.inoue 2009/06/29
									// 整理番号重複チェック処理
									Long resultCnt = tKojinDao.selectByCountUniqueKey(validatedData.getcopyJyushinkenID());
									if (resultCnt > 0) {
										JErrorMessage.show("M3607", this);
										// edit s.inoue 2009/10/20
										// 受診券重複を許可して、警告レベルへ変更する。
										// JApplication.kikanDatabase.rollback();
										// this.refreshTableCellColor();
										// this.initializeColumnWidth();
										// return false;
									}
								}catch (Exception ex) {
									logger.error(ex.getMessage());
								}

								String queryKojin = registerKojinCopy();
								registerHistory();
								try {
									JApplication.kikanDatabase.sendNoResultQuery(queryKojin);
								} catch (SQLException e) {
									logger.error(e.getMessage());
									// eidt s.inoue 2013/02/20
									// JApplication.kikanDatabase.rollback();
										JApplication.kikanDatabase.getMConnection().rollback();
									// add ver2 s.inoue 2009/06/29
									this.refreshTableCellColor();
									this.initializeColumnWidth();
									return false;
								}
							}

							boolean isOtherValidated = false;
							boolean isKekkaValidated = false;
							// add s.inoue 2009/10/17
							HashMap<Integer, String> kekkaValidated = new HashMap<Integer, String>();

							boolean isCheckCSV = false;
							String errMessage = "";

							int i = 0;

							// 検査結果その他テーブルに関して登録処理を行う
							for (; i < this.model.getRowCount(); i++) {

								// 結果値以外に関しての検証
								isOtherValidated = (validatedData.setHihokenjyaCode(jTextField_HokenjyaCode.getText(),false)
										&& validatedData.setHihokenjyaNumber(jTextField_HokenjyaNumber.getText(),false)
										&& validatedData.setKensaJissiDate(jissiDate,false)
										// edit s.inoue 2009/11/11
										&& validatedData.setKensaKoumokuCode((String) this.model.getData(i, COLUMN_IDX_KOUMOKUCD))
										&& validatedData.setComment((String) this.model.getData(i, COLUMN_IDX_KOMENTO))
										&& validatedData.setHL((String) this.model.getData(i, COLUMN_IDX_HL))
										&& validatedData.setHantei((String) this.model.getData(i, COLUMN_IDX_HANTEI))
										&& validatedData.setChkText((String) this.model.getData(i, COLUMN_IDX_JISIKBN)));

								// 結果値に関する検証
								ResultItem = tableResult.get(i);

								String koumokuCode = ResultItem.get(COLUMN_NAME_KOUMOKUCD);
								String koumokuName = ResultItem.get(COLUMN_NAME_KOUMOKUNAME);

								switch (Integer.valueOf(ResultItem.get(COLUMN_NAME_DATA_TYPE))) {
								case 1:
									// 数値の場合
									// edit s.inoue 2009/11/11
									isKekkaValidated
										= validatedData.setKekkaDecimal((String) this.model.getData(i, COLUMN_IDX_KEKKA_NUM),
										ResultItem.get(COLUMN_NAME_MAX_BYTE_LENGTH));

									if (isKekkaValidated) {
										isCheckCSV = true;
										String retMessage = getHighLawMessage(
											(String)this.model.getData(i,COLUMN_IDX_KOUMOKUCD),
											koumokuName,
											validatedData.getHokenjyaNumber(),
											validatedData.getKekka());

										if (!retMessage.isEmpty()){
											validateInput = true;
											errMessage +=retMessage;
										}
									}
									break;

								case 2:
									// コードの場合
									ArrayList<Hashtable<String, String>> codeResult = null;
									Hashtable<String, String> codeResultItem;
									// edit s.inoue 2009/09/29
									query = new String("SELECT KOUMOKU_CD, CODE_NUM, CODE_NAME FROM T_DATA_TYPE_CODE WHERE KOUMOKU_CD = "
											+ JQueryConvert.queryConvert(koumokuCode));
									try {
										codeResult = JApplication.kikanDatabase.sendExecuteQuery(query);
									} catch (SQLException e) {
										logger.warn(e.getMessage());
									}
									// edit s.inoue 2009/11/11
									if (this.model.getData(i).get(COLUMN_IDX_KEKKA_CODE).equals("")) {
										isKekkaValidated = validatedData.setKekkaCode("");
										isCheckCSV = true;
										break;
									}

									for (int j = 0; j < codeResult.size(); j++) {
										codeResultItem = codeResult.get(j);
										// edit s.inoue 2009/11/11
										if (this.model.getData(i, COLUMN_IDX_KEKKA_CODE)
												.equals(codeResultItem.get(COLUMN_NAME_CODE_NAME))) {

											isCheckCSV = CheckHighLawItem(
													(String)this.model.getData(i,COLUMN_IDX_KOUMOKUCD),
													validatedData.getHokenjyaNumber(),
													codeResultItem.get(COLUMN_NAME_CODE_NUM));

											isKekkaValidated = validatedData.setKekkaCode(codeResultItem.get(COLUMN_NAME_CODE_NUM));
											break;
										}
									}
									break;

								case 3:
									// 文字列の場合
									isCheckCSV = true;
									// edit s.inoue 2009/11/11
									isKekkaValidated
										= validatedData.setKekkaText((String) this.model
										.getData(i, COLUMN_IDX_KEKKA_STR), ResultItem.get(COLUMN_NAME_MAX_BYTE_LENGTH));
									break;
								}
								// add s.inoue 2009/10/17
								if (!isKekkaValidated) {
									kekkaValidated.put(i,"項目コード:" + koumokuCode + "  項目名:" + koumokuName);
								}

								if (isKekkaValidated && isOtherValidated && isCheckCSV) {
									// 受付ID,年度の追加対応
									// 検査結果特定、その他テーブルへ新規レコード挿入
									kensakekaSonota.setUKETUKE_ID(new Long(validatedData.getUketuke_id()));
									kensakekaSonota.setHIHOKENJYASYO_KIGOU(validatedData.getHihokenjyaCode());
									kensakekaSonota.setHIHOKENJYASYO_NO(validatedData.getHihokenjyaNumber());
									try {
										kensakekaSonota.setKENSA_NENGAPI(new Integer(kensaJissiDate));
										kensakekaSonota.setNENDO(FiscalYearUtil.getThisYear(kensaJissiDate));
									} catch (NumberFormatException e) {
										logger.error(e.getMessage());
									}
									kensakekaSonota.setKOUMOKU_CD(validatedData.getKensaKoumokuCode());
									kensakekaSonota.setKEKA_TI(validatedData.getKekka());
									kensakekaSonota.setKOMENTO(validatedData.getComment());

									try {
										Integer jisiInteger = null;
										String strJisi = validatedData.getJisi_KBN();
										if (strJisi != null
												&& !strJisi.isEmpty()) {
											jisiInteger = new Integer(strJisi);
										}
										kensakekaSonota.setJISI_KBN(jisiInteger);

									} catch (NumberFormatException e) {
										/* 何もしない */
									}

									try {
										Integer hlInteger = null;
										String hlString = validatedData.getHL();
										if (hlString != null
												&& !hlString.isEmpty()) {
											hlInteger = new Integer(hlString);
										}
										kensakekaSonota.setH_L(hlInteger);

									} catch (NumberFormatException e) {
										/* 何もしない */
									}

									try {
										kensakekaSonota.setHANTEI(new Integer(
												validatedData.getHantei()));
									} catch (NumberFormatException e) {
										/* 何もしない */
									}

									// add s.inoue 20080911 結果値(未実施,測定不可能)判定
									if ((validatedData.getJisi_KBN().equals("0") ||
											validatedData.getJisi_KBN().equals("2")) &&
											!validatedData.getKekka().equals("")){
										String[] params = { koumokuCode, koumokuName };
										JErrorMessage.show("M3634", this, params);
										// eidt s.inoue 2013/02/20
										// JApplication.kikanDatabase.rollback();
										try {
											JApplication.kikanDatabase.getMConnection().rollback();
										} catch (SQLException e) {
											logger.warn(e.getMessage());
										}
										this.refreshTableCellColor();
										this.initializeColumnWidth();
										return false;
									}

									// 登録対象のフラグチェック

									// 例外の項目(質問票に関わる項目)
									String KoumokuHd = koumokuCode.substring(0, 3);
									if(!KoumokuHd.equals("9N7")&&
											!koumokuCode.equals(CODE_HOKEN_SHIDOU) &&
											!koumokuCode.equals(CODE_SEIKATU_KAIZEN) &&
											!koumokuCode.equals(CODE_SAIKETSU_ZIKAN) &&
											!koumokuCode.equals(CODE_ISHINOHANDAN_1) &&
											!koumokuCode.equals(CODE_ISHINOHANDAN_2) &&
											!koumokuCode.equals(CODE_ISHINOHANDAN_3) &&
											!koumokuCode.equals(CODE_ISHINOHANDAN_4)){

										String hisuFlg = ResultItem.get(COLUMN_NAME_HISU_FLG);

										if ((validatedData.getJisi_KBN().equals("1") && !validatedData.getKekka().equals("")) ||
											((validatedData.getJisi_KBN().equals("2") || validatedData.getJisi_KBN().equals("0"))
													&& validatedData.getKekka().equals(""))){

											if (validatedData.getSeikyuKubun().equals(JApplication.SEIKYU_KBN_BASE)){
												if (hisuFlg.equals(JApplication.HISU_FLG_BASE)){
													hanteiBase = true;intBaseCnt +=1;
												}
											}else if (validatedData.getSeikyuKubun().equals(JApplication.SEIKYU_KBN_SYOSAI)){
												if (hisuFlg.equals(JApplication.HISU_FLG_BASE)){
													hanteiBase = true;
												}else
												if (hisuFlg.equals(JApplication.HISU_FLG_SYOSAI)){
													hanteiSyosai = true;
												}
												if (hanteiBase && hanteiSyosai){
													intSyosaiCnt +=1;
												}
											}else if (validatedData.getSeikyuKubun().equals(JApplication.SEIKYU_KBN_TUIKA)){
												if (hisuFlg.equals(JApplication.HISU_FLG_BASE)){
													hanteiBase = true;
												}else
												if (hisuFlg.equals(JApplication.HISU_FLG_TUIKA)){
													hanteiTuika = true;
												}
												if (hanteiBase && hanteiTuika){
													intTuikaCnt +=1;
												}
											}else if (validatedData.getSeikyuKubun().equals(JApplication.SEIKYU_KBN_SYOSAI_TUIKA) ||
													validatedData.getSeikyuKubun().equals(JApplication.SEIKYU_KBN_DOC)){
												if (hisuFlg.equals(JApplication.HISU_FLG_BASE)){
													hanteiBase = true;
												}else
												if (hisuFlg.equals(JApplication.HISU_FLG_SYOSAI)){
													hanteiSyosai = true;
												}else
												if (hisuFlg.equals(JApplication.HISU_FLG_TUIKA)){
													hanteiTuika = true;
												}
												if (hanteiBase && hanteiSyosai && hanteiTuika){
													intAllCnt +=1;
												}
											}
										}
									}

									// add s.inoue 20080911 結果値判定(未実施,測定不可能)判定
									// 検査項目整合性チェック
									validateKensa= isNotExistKensaKoumoku(
											    validatedData.getHokenjyaNumber(),
												validatedData.getUketuke_id(),
												validatedData.getKensaJissiDate(),
												validatedData.getSeikyuKubun(),
												validatedData.getKensaKoumokuCode(),
												validatedData.getKekka(),
												validatedData.getJisi_KBN());

									if (validateKensa){

										if (!notExtMessage){
											JErrorMessage.show("M3635", this);
											notExtMessage = true;
										}
									}

									if (! this.isCopy && ! this.isNewKekkaData) {
										kensakekaSonotaDao.delete(
												new Long(validatedData.getUketuke_id()),
												new Integer(this.initialDate),
												new String(validatedData.getKensaKoumokuCode()));
									}

									kensakekaSonotaDao.insert(kensakekaSonota);

								} else {
									if (!isKekkaValidated) {
										// edit s.inoue 2009/10/17
										// JErrorMessage.show("M3601", this);
										String[] params = { kekkaValidated.get(i) };
										JErrorMessage.show("M3601", this, params);
									} else if (!isCheckCSV) {
										String[] params = { koumokuCode, koumokuName };
										JErrorMessage.show("M3602", this, params);
									}
									// 登録を終了にする
									System.out.println("RollBack");
									// eidt s.inoue 2013/02/20
									// JApplication.kikanDatabase.rollback();
									try {
										JApplication.kikanDatabase.getMConnection().rollback();
									} catch (SQLException e) {
										logger.warn(e.getMessage());
									}

									this.refreshTableCellColor();
									this.initializeColumnWidth();
									// edit s.inoue 2009/10/21
									refreshCellAction();
									return false;
								}
							}

							// add ver2 s.inoue 2009/04/14
							if (validateInput){
								String[] params = { errMessage };
								JErrorMessage.show("M3602", this, params);
							}

//							// add s.inoue 2011/07/29 inbaseCnt:入力
// 							if (intBaseCnt != inthisu_count){
// 								JErrorMessage.show("M3641", this);
// 							}

							// add s.inoue 20081008
							if ((intBaseCnt == 0) &&(intSyosaiCnt == 0) &&(intTuikaCnt == 0) && (intAllCnt == 0)){
								// メッセージは一回きりにする
								if (!notExtMessage && isCheckCSV){
									JErrorMessage.show("M3636", this);
									notExtMessage = true;
								}
							}

							// テーブルの最後の行をinsertする前にbreakされた場合
							if (i != this.model.getRowCount()) {
								System.out.println("RollBack");
								// eidt s.inoue 2013/02/20
								// JApplication.kikanDatabase.rollback();
								try {
									JApplication.kikanDatabase.getMConnection().rollback();
								} catch (SQLException e) {
									logger.warn(e.getMessage());
								}

								this.refreshTableCellColor();
								this.initializeColumnWidth();

								return false;

							} else {
								// 次にメタボリックシンドローム判定と保健指導レベルに関しての処理を行う
								try {
									// 受付ID,年度の追加対応
									// 検査結果データその他レコード削除・挿入

									kensakekaSonota = new TKensakekaSonota();
									kensakekaSonota.setUKETUKE_ID(new Long(validatedData.getUketuke_id()));
									kensakekaSonota.setHIHOKENJYASYO_KIGOU(validatedData.getHihokenjyaCode());
									kensakekaSonota.setHIHOKENJYASYO_NO(validatedData.getHihokenjyaNumber());
									try {
										// edit ver2 s.inoue 2009/07/13
										kensakekaTokutei.setNENDO(FiscalYearUtil.getThisYear(kensaJissiDate));

										kensakekaSonota.setKENSA_NENGAPI(new Integer(kensaJissiDate));
									} catch (NumberFormatException e) {
									}

									// まずメタボリックシンドロームに関する処理を行う
									// edit s.inoue 2009/10/26 更新前のものを消す！！
									// kensakekaSonotaDao.delete(new Long(validatedData.getUketuke_id()),new Integer(kensaJissiDate),"9N501000000000011");
									kensakekaSonotaDao.delete(new Long(validatedData.getUketuke_id()),
											this.initialDate.equals("")?new Integer(kensaJissiDate):new Integer(this.initialDate),"9N501000000000011");

									kensakekaSonota.setKOUMOKU_CD("9N501000000000011");
									kensakekaSonota.setKEKA_TI(validatedData.getMetaboHantei());
									kensakekaSonotaDao.insert(kensakekaSonota);

									// 保健指導レベルに関する処理を行う
									// edit s.inoue 2009/10/26 更新前のものを消す！！
									// kensakekaSonotaDao.delete(new Long(validatedData.getUketuke_id()),new Integer(kensaJissiDate),"9N506000000000011");
									kensakekaSonotaDao.delete(new Long(validatedData.getUketuke_id()),
											this.initialDate.equals("")?new Integer(kensaJissiDate):new Integer(this.initialDate),"9N506000000000011");
									kensakekaSonota.setKOUMOKU_CD("9N506000000000011");
									kensakekaSonota.setKEKA_TI(validatedData.getHokenSidouLevel());
									kensakekaSonotaDao.insert(kensakekaSonota);

								} catch (SQLException f) {
									f.printStackTrace();
									// eidt s.inoue 2013/02/20
									// JApplication.kikanDatabase.rollback();
									try {
										JApplication.kikanDatabase.getMConnection().rollback();
									} catch (SQLException e) {
										logger.warn(e.getMessage());
									}
									return false;
								}

								// 検査結果特定テーブルに対して登録処理を行う
								try {
									if (mode == UPDATE_MODE) {
										// 受付ID,年度の追加対応
										// 受付IDを指定して検査結果データ特定を更新

										query = "UPDATE T_KENSAKEKA_TOKUTEI SET "
												+ "KENSA_CENTER_CD = "
												+ JQueryConvert.queryConvertAppendComma(validatedData.getKensaCenterCode())
												+ "K_P_NO = "
												+ JQueryConvert.queryConvertAppendComma(String.valueOf(kenshinPatternNumber))
												+ "KOMENTO = "
												+ JQueryConvert.queryConvertAppendComma(validatedData.getSougouComment())
												// add ver2 s.inoue 2009/07/13
												+ "NENDO = "
												+ JQueryConvert.queryConvertAppendComma(String.valueOf(kensakekaTokutei.getNENDO()))

												+ "SEIKYU_KBN = "
												+ JQueryConvert.queryConvert(validatedData.getSeikyuKubun())
												+ " WHERE "
												+ "UKETUKE_ID = "
												+ JQueryConvert.queryConvert(validatedData.getUketuke_id())
												+ " AND "
												+ "	KENSA_NENGAPI = "
												+ JQueryConvert.queryConvert(kensaJissiDate);

										JApplication.kikanDatabase.sendNoResultQuery(query);

									} else if (mode == INSERT_MODE) {

										// 受付ID,年度の追加対応
										// 検査結果データ特定レコード挿入
										kensakekaTokutei = new TKensakekaTokutei();
										kensakekaTokutei.setUKETUKE_ID(new Long(validatedData.getUketuke_id()));
										kensakekaTokutei.setHIHOKENJYASYO_KIGOU(validatedData.getHihokenjyaCode());
										kensakekaTokutei.setHIHOKENJYASYO_NO(validatedData.getHihokenjyaNumber());
										kensakekaTokutei.setKENSA_CENTER_CD(validatedData.getKensaCenterCode());

										try {
											kensakekaTokutei.setK_P_NO(new Integer(kenshinPatternNumber));
										} catch (NumberFormatException e) {
										}
										kensakekaTokutei.setKOMENTO(validatedData.getSougouComment());
										try {

											kensakekaTokutei.setKENSA_NENGAPI(new Integer(kensaJissiDate));
										} catch (NumberFormatException e) {
										}

										// add ver2 s.inoue 2009/07/13
										try {
											kensakekaTokutei.setNENDO(FiscalYearUtil.getThisYear(kensaJissiDate));
										} catch (NumberFormatException e) {
										}

										// このあとUPDATEを行うため一時的にデータを入れる
										kensakekaTokutei.setKEKA_INPUT_FLG(new Integer(1));
										try {
											kensakekaTokutei.setSEIKYU_KBN(new Integer(validatedData.getSeikyuKubun()));
										} catch (NumberFormatException e) {
										}
										kensakekaTokuteiDao.insert(kensakekaTokutei);
									}

									/* 複製ではなく、かつ日付変更である場合は、元のデータを削除する。 */
									if (! this.isCopy && hasJissiDateChanged && ! this.isNewKekkaData) {
										kensakekaTokuteiDao.delete(validatedData.getUketuke_id(),this.initialDate);
									}
								} catch (SQLException f) {
									f.printStackTrace();
									try {
										// eidt s.inoue 2013/02/20
										// JApplication.kikanDatabase.rollback();
										JApplication.kikanDatabase.getMConnection().rollback();
										return false;
									} catch (SQLException g) {
										g.printStackTrace();
										JErrorMessage
												.show("M0000", this);
										System.exit(1);
									}
								}

								// 検査結果の未、済を判定し、UPDATEをかける
								int nullCount, existCount;
								try {

									ArrayList<Hashtable<String, String>> rs;
									// T_KOJIN,T_KENSAKEKA_TOKUTEIにUKETUKE_ID,NENDO追加対応
									query = " SELECT"
											+ "	 KEKA_TI"
											+ " FROM"
											+ "	 T_KENSAKEKA_SONOTA "
											+ " WHERE "
											+ "	UKETUKE_ID = "
											+ JQueryConvert
													.queryConvert(validatedData
															.getUketuke_id())
											+ " AND "
											+ "	KENSA_NENGAPI = "
											+ JQueryConvert
													.queryConvert(kensaJissiDate)
											+ " AND "
											+ "	KOUMOKU_CD NOT IN ( "
											+ JQueryConvert
													.queryConvertAppendComma("2A020000001930101")
											+ JQueryConvert
													.queryConvertAppendComma("2A030000001930101")
											+ JQueryConvert
													.queryConvertAppendComma("2A040000001930102")
											+ JQueryConvert
													.queryConvertAppendComma("9A110160700000011")
											+ JQueryConvert
													.queryConvertAppendComma("9A110160800000049")
											+ JQueryConvert
													.queryConvertAppendComma("9A110161000000049")
											+ JQueryConvert
													.queryConvertAppendComma("9E100166000000011")
											+ JQueryConvert
													.queryConvertAppendComma("9E100166100000011")
											+ JQueryConvert
													.queryConvertAppendComma("9E100166200000011")
											+ JQueryConvert
													.queryConvertAppendComma("9E100166300000011")
											+ JQueryConvert
													.queryConvertAppendComma("9E100160900000049")
											+ JQueryConvert
													.queryConvertAppendComma("9E100161000000049")
											+ JQueryConvert
													.queryConvertAppendComma("9N501000000000011")
											+ JQueryConvert
													.queryConvertAppendComma("9N506000000000011")
											+ JQueryConvert
													.queryConvertAppendComma("9N511000000000049")
											+ JQueryConvert
													.queryConvert("9N516000000000049")
											+ " )";


									rs = JApplication.kikanDatabase
											.sendExecuteQuery(query);

									/* 全項目数 */
									existCount = rs.size();

									// 検査結果が未入力のレコードを除いた場合
									query = " SELECT "
											+ "	KEKA_TI "
											+ " FROM "
											+ "	T_KENSAKEKA_SONOTA sonota, "
											+ "	T_KENSHINMASTER master "
											+ " WHERE "
											+ " sonota.KOUMOKU_CD = master.KOUMOKU_CD "
											+ " 	AND "
											+ " sonota.UKETUKE_ID = "
											+ JQueryConvert
													.queryConvert(validatedData
															.getUketuke_id())
											+ " 	AND "
											+ " sonota.KENSA_NENGAPI = "
											+ JQueryConvert
													.queryConvert(kensaJissiDate)
											+ " 	AND "
											+ " sonota.KEKA_TI is null "
											+ " 	AND "
											+ " sonota.KOUMOKU_CD NOT IN ( "
											+ JQueryConvert
													.queryConvertAppendComma("2A020000001930101")
											+ JQueryConvert
													.queryConvertAppendComma("2A030000001930101")
											+ JQueryConvert
													.queryConvertAppendComma("2A040000001930102")
											+ JQueryConvert
													.queryConvertAppendComma("9A110160700000011")
											+ JQueryConvert
													.queryConvertAppendComma("9A110160800000049")
											+ JQueryConvert
													.queryConvertAppendComma("9A110161000000049")
											+ JQueryConvert
													.queryConvertAppendComma("9E100166000000011")
											+ JQueryConvert
													.queryConvertAppendComma("9E100166100000011")
											+ JQueryConvert
													.queryConvertAppendComma("9E100166200000011")
											+ JQueryConvert
													.queryConvertAppendComma("9E100166300000011")
											+ JQueryConvert
													.queryConvertAppendComma("9E100160900000049")
											+ JQueryConvert
													.queryConvertAppendComma("9E100161000000049")
											+ JQueryConvert
													.queryConvertAppendComma("9N501000000000011")
											+ JQueryConvert
													.queryConvertAppendComma("9N506000000000011")
											+ JQueryConvert
													.queryConvertAppendComma("9N511000000000049")
											+ JQueryConvert
													.queryConvert("9N516000000000049")
											+ " ) " + " 	AND "
											+ " master.HISU_FLG = 1";
									rs = JApplication.kikanDatabase
											.sendExecuteQuery(query);

									/* 入力数 */
									nullCount = rs.size();

									// T_KOJIN,T_KENSAKEKA_TOKUTEIにUKETUKE_ID,NENDO追加対応
									// 全ての項目について入力がなされていた場合
									String kensakekaInputFlg = (nullCount == 0) ? "2"
											: "1";
									query = String.format("UPDATE "
											+ "T_KENSAKEKA_TOKUTEI " + "SET "
											+ "KEKA_INPUT_FLG = '%s' "
											+ "WHERE " + "UKETUKE_ID = %s "
											+ "AND " + "KENSA_NENGAPI = %s",
											kensakekaInputFlg, validatedData
													.getUketuke_id(),
											kensaJissiDate);
									JApplication.kikanDatabase
											.sendNoResultQuery(query);
					System.out.println(query);
									// edit ver2 s.inoue 2009/08/25
									// パターン登録でエラーメッセージを回避する必要
									// 結果があるかどうか登録処理時に、コミット前の状態のフラグを渡す
									JApplication.kikanDatabase.Commit();
									stateChangedKenshinPatternNumber(validatedData.getUketuke_id(),jissiDate,true,isNewKekkaData);

									this.initialDate = this.validatedData.getKensaJissiDate();
									this.isNewKekkaData = false;

								} catch (SQLException f) {
									f.printStackTrace();

									try {
										// eidt s.inoue 2013/02/20
										// JApplication.kikanDatabase.rollback();
										JApplication.kikanDatabase.getMConnection().rollback();
										return false;
									} catch (SQLException g) {
										g.printStackTrace();
										JErrorMessage
												.show("M0000", this);
										System.exit(1);
									}
								}
							}
						} catch (SQLException f) {
							f.printStackTrace();

							try {
								System.out.println("Rollback!");
								// eidt s.inoue 2013/02/20
								// JApplication.kikanDatabase.rollback();
								JApplication.kikanDatabase.getMConnection().rollback();
								return false;
							} catch (SQLException g) {
								g.printStackTrace();
								JErrorMessage.show("M0000", this);
								System.exit(1);
							}
						}
					}
				}
			}
		}
		if (mode == INSERT_MODE) {
			mode = UPDATE_MODE;
			// add ver2 s.inoue 2009/06/29
			jTextField_SeiriNo.setVisible(false);
			jLabelSeirino.setVisible(false);
			isCopy = false;
		}
		refreshTable(kensaJissiDate, true,false,true);
		return true;
	}

	/**
	 * 実施日確定ボタン
	 */
	public void changeInputDate(String kensaJissiDate, boolean saveColumnWidth) {

//		if (validatedData.setKensaJissiDate(jTextField_Date.getText())) {
//			String kensaJissiDate = validatedData.getKensaJissiDate();

			if (!kensaJissiDate.isEmpty()) {
				// 健診パターンコンボボックスに表示するパターン名を取得しセットする
				// //すでに登録されている年月日かどうかをチェックする
				boolean existsKensaKekkaData = existsKensaKekkaData(kensaJissiDate);

				/*  */
				boolean isChangeMode = false;
				if (existsKensaKekkaData) {
					isChangeMode = true;
					mode = UPDATE_MODE;
				}
				else {
					mode = INSERT_MODE;
				}

				/* 健診パターン選択欄を初期化する。 */
				this.initializeKenshinPatternComboBox(kensaJissiDate, isChangeMode,false);

				/* 検査センター名称選択欄を初期化する。 */
				this.initializeKensaCenterComboBox(kensaJissiDate, isChangeMode);
				this.refreshTable(kensaJissiDate, true,false,false);
			}
//		}
	}

	private boolean existsKensaKekkaData(String kensaJissiDate) {
		Integer kensanengapi = null;
		try {
			kensanengapi = new Integer(kensaJissiDate);
		} catch (NumberFormatException e) {
		}

		List<TKensakekaSonota> resultList = null;
		try {
			String uketuke_id = validatedData.getUketuke_id();
			resultList = kensakekaSonotaDao.selectByUketukeIdKensaNengapi(
					new Long(uketuke_id),
					kensanengapi);
		} catch (Exception e) {
			e.printStackTrace();
		}

		boolean exsistsKensaKekkaData = false;
		if (resultList != null &&  resultList.size() > 0) {
			exsistsKensaKekkaData = true;
		}

		return exsistsKensaKekkaData;
	}

	/**
	 * 実施を日変更する。
	 */
	public void changeJissibi() {
		if (validatedData.setKensaJissiDate(jTextField_Date.getText(),false)) {
			if (!validatedData.getKensaJissiDate().isEmpty()) {
				// 健診パターンコンボボックスに表示するパターン名を取得しセットする
				// //すでに登録されている年月日かどうかをチェックする
				Integer kensanengapi = null;
				try {
					kensanengapi = new Integer(validatedData
							.getKensaJissiDate());
				} catch (NumberFormatException e) {
				}

				List<TKensakekaSonota> resultList = null;
				try {
					String uketuke_id = validatedData.getUketuke_id();
					resultList = kensakekaSonotaDao.selectByUketukeIdKensaNengapi(
							new Long(uketuke_id),kensanengapi);
				} catch (Exception e) {
					e.printStackTrace();
				}

				/*
				 * 変更しようとしている日に、検査結果が既に存在する場合は、処理を中断する。
				 */
				if (resultList == null) {
					/* 指定した日[%s]には、すでに健診結果データが存在するため、変更できません。
					 * 変更する場合は、先に[%s]の健診結果データを削除する必要があります。 */

					String kensaNengapiString = String.valueOf(kensanengapi);
					String[] messageParams = { kensaNengapiString, kensaNengapiString };

					JErrorMessage.show("M3631", this, messageParams);

					return;
				}

				/*
				 * 実施日を変更する。
				 */
				System.out.println("変更する。");
			}
		}
	}

	/**
	 * 検査センター名称選択欄を初期化する。
	 */
	private void initializeKensaCenterComboBox(String jissiDate, boolean isChangeMode) {
		// 健診センター項目コンボボックスに表示するセンター名を取得しセットする
		/* Added 2008/03/18 若月 項目を追加する前に、全て削除しておく必要がある。 */
		/* --------------------------------------------------- */
		jComboBox_KensaKikanName.removeAllItems();
		/* --------------------------------------------------- */

		// 健診センター項目コンボボックスに表示するセンター名を取得しセットする
		ArrayList<Hashtable<String, String>> items = null;
		try {
			items = JApplication.kikanDatabase
					.sendExecuteQuery("SELECT DISTINCT KENSA_CENTER_CD,CENTER_NAME FROM T_KENSACENTER_MASTER");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		jComboBox_KensaKikanName.addItem(null);
		for (int i = 0; i < items.size(); i++) {
			Hashtable<String, String> Item;
			Item = items.get(i);
			jComboBox_KensaKikanName.addItem(Item.get("CENTER_NAME"));
		}
		// 変更の場合は以前選択されていたものを初期値として表示する
		if (isChangeMode) {
			// T_KOJIN,T_KENSAKEKA_TOKUTEIにUKETUKE_ID,NENDO追加対応
			String query = "SELECT CENTER.CENTER_NAME "
					+ "FROM T_KENSAKEKA_TOKUTEI AS TOKUTEI "
					+ "LEFT JOIN T_KENSACENTER_MASTER AS CENTER "
					+ "ON "
					+ "TOKUTEI.KENSA_CENTER_CD = CENTER.KENSA_CENTER_CD "
					+ "WHERE "
					+ "UKETUKE_ID = "
					+ JQueryConvert.queryConvert(validatedData
							.getUketuke_id())
					+ " "
					+ "AND "
					+ "TOKUTEI.KENSA_NENGAPI = "
					+ JQueryConvert.queryConvert(jissiDate);

			ArrayList<Hashtable<String, String>> items2 = null;
			try {
				items2 = JApplication.kikanDatabase.sendExecuteQuery(query);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			for (int i = 0; i < jComboBox_KensaKikanName.getItemCount(); i++) {
				// 検査センターが未選択の場合でも検査センターコンボボックスで最初の項目が選択されてしまう障害の修正
				String selectedItem = (String) jComboBox_KensaKikanName
						.getItemAt(i);
				if ((selectedItem != null)
						&& (selectedItem.equals(items2.get(0).get(
								"CENTER_NAME")))) {
					jComboBox_KensaKikanName.setSelectedIndex(i);
					break;
				}
			}
		}

		isInit = true;
		stateChangedKensaKikanName();
		stateInitKenshinPatternNumber(jissiDate);
	}


	/**
	 * 健診パターン選択欄を初期化する。
	 */
	private void initializeKenshinPatternComboBox(String jissiDate, boolean isChangeMode, boolean preview) {
		jComboBox_Pattern.removeAllItems();

		ArrayList<Hashtable<String, String>> items = null;
		try {
			items = JApplication.kikanDatabase
			.sendExecuteQuery("SELECT * FROM T_KENSHIN_P_M WHERE K_P_NO >= 0 ORDER BY K_P_NO");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < items.size(); i++) {
			Hashtable<String, String> Item;
			Item = items.get(i);
			jComboBox_Pattern.addItem(Item.get("K_P_NAME"));
		}

		// add ver2 s.inoue 2009/06/25
		String uketukeID = "";

		if (isCopy && !preview ){
			uketukeID = validatedData.getUketukePre_id();
		}else{
			uketukeID = validatedData.getUketuke_id();
		}

		// 変更の場合は以前選択されていたものを初期値として表示する
		if (isChangeMode || (isCopy && !preview )) {
			String Query = "SELECT PM.K_P_NAME "
					+ "FROM T_KENSAKEKA_TOKUTEI AS TOKUTEI "
					+ "LEFT JOIN T_KENSHIN_P_M AS PM "
					+ "ON TOKUTEI.K_P_NO = PM.K_P_NO "
					+ " WHERE "
					+ "UKETUKE_ID = "
					+ uketukeID
					+ " "
					+ "AND "
					+ "TOKUTEI.KENSA_NENGAPI = "
					+ JQueryConvert.queryConvert(jissiDate);
			try {
				items = JApplication.kikanDatabase.sendExecuteQuery(Query);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (!items.isEmpty()) {
				for (int i = 0; i < jComboBox_Pattern.getItemCount(); i++) {
					if (((String) (jComboBox_Pattern.getItemAt(i)))
							.equals(items.get(0).get("K_P_NAME"))) {
						jComboBox_Pattern.setSelectedIndex(i);
						break;
					}
				}
			}
		}
	}

	// add ver2 s.inoue 2009/06/24
	/**
	 * BMI計算ActionEvent
	 */
	public void actionBMICalc(){
		if (
				heightCellPosition != null
				&& weightCellPosition != null
				&& BMICellPosition != null
			) {

			String height = (String)this.model.getData(
					heightCellPosition.getRow(),
					heightCellPosition.getColumn()
				);

			String weight = (String) this.model.getData(
					weightCellPosition.getRow(),
					weightCellPosition.getColumn()
				);

			String bmi = calcBMI(height, weight);

			String kensaKekka = JValidate.validateKensaKekkaDecimal(
											bmi,BMIFormat);
			this.model.setData(
					kensaKekka,
					BMICellPosition.getRow(),
					BMICellPosition.getColumn()
				);
		}
	}

	// add ver2 s.inoue 2009/06/24
	/**
	 * BMI値を計算する
	 */
	public String calcBMI(String height, String weight) {
		// どちらかが空値の場合は空値を返す
		if (height.isEmpty() || weight.isEmpty()) {
			return "";
		}

		// ０除算エラーを防ぐ
		if (Double.valueOf(height) == 0) {
			return "";
		}

		BigDecimal bmi = new BigDecimal(
				String
						.valueOf((Double.valueOf(weight) / ((Double
								.valueOf(height) / 100) * (Double
								.valueOf(height) / 100)))));
		return String.valueOf(bmi.setScale(1, BigDecimal.ROUND_HALF_UP)
				.doubleValue());
	}

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		/* 終了ボタン */
		if (source == jButton_End) {
			logger.info(jButton_End.getText());
			pushedEndButton(e);
		}
		/* 登録ボタン */
		else if (source == jButton_Register) {
			logger.info(jButton_Register.getText());
			pushedRegisterButton(e);
		}
		/* キャンセルボタン */
		else if (source == jButton_Cancel) {
			logger.info(jButton_Cancel.getText());
			pushedCancelButton(e);
		}
		// add ver2 s.inoue 2009/06/02
		/* クリアボタン */
		else if (source == jButton_Clear) {
			logger.info(jButton_Clear.getText());
			pushedClearButton(e);
		}
		/*
		 * BMI計算のためのキーリスナー
		 */
		else if (source == this.model) {
			// add ver2 s.inoue 2009/06/24
			actionBMICalc();
		}

		/* Added 2008/03/12 若月 Enterキーによるフォーカス移動機能追加 */
		if (this.enterkeyFocusComponents != null
				&& this.enterkeyFocusComponents.contains(source)) {
			if (source instanceof Component) {
				((Component) source).transferFocus();
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent keyEvent) {
		switch(keyEvent.getKeyCode()){
		case KeyEvent.VK_F1:
			logger.info(jButton_End.getText());
			pushedEndButton(null);break;
		case KeyEvent.VK_F2:
			logger.info(jButton_Clear.getText());
			pushedClearButton(null);break;
		case KeyEvent.VK_F12:
			logger.info(jButton_Register.getText());
			pushedRegisterButton(null);break;
		}
	}

	public void itemStateChanged(ItemEvent e) {

		if (isInit) {
			if (e.getSource() == jComboBox_KensaKikanName) {
				stateChangedKensaKikanName();
			}

			if (e.getSource() == jComboBox_Pattern) {
				if (e.getStateChange() == ItemEvent.SELECTED){
					// edit ver2 s.inoue 2009/08/26
					stateChangedKenshinPatternNumber(validatedData.getUketuke_id(),validatedData.getKensaJissiDate(),false,isNewKekkaData);
				}
			}
		}
	}

	/**
	 * 入力必須のセルを取得しフォーカスマップを作成 テーブルにフォーカスリスナを設定
	 * Enterキー押下でフォーカスマップに登録されたセル座標にフォーカスを移動する
	 */
	private void setTableFocusMap() {
		// ref s.inoue 20080912 実施区分フォーカス 3⇒4
		final int KEKA_TI_COLUMN_COUNT = 3;
		List<int[]> focusMap = new ArrayList<int[]>();
		boolean[][] onOff = new boolean[model.getRowCount()][KEKA_TI_COLUMN_COUNT];

		for (int i = 0; i < onOff.length; i++) {
			for (int j = 0; j < KEKA_TI_COLUMN_COUNT; j++) {
				onOff[i][j] = true;
			}
		}

		for (JSimpleTableCellRendererData rendererData : cellColors) {
			JSimpleTableCellPosition cellPosition = rendererData
					.getCellPosition();
			if (rendererData.getColor().equals(COLOR_DISABLE)) {
				// ref s.inoue 20080912 実施区分フォーカス
				onOff[cellPosition.getRow()][cellPosition.getColumn()  - KEKA_TI_COLUMN_COUNT] = false;
				// onOff[cellPosition.getRow()][cellPosition.getColumn() + 1  - KEKA_TI_COLUMN_COUNT] = false;
			}
		}

		for (int i = 0; i < onOff.length; i++) {
			for (int j = 0; j < onOff[i].length; j++) {
				if (onOff[i][j]) {
					int[] position = new int[2];
					// edit s.inoue 20080912 実施区分フォーカス
					position[0] = j + KEKA_TI_COLUMN_COUNT;
					//position[0] = j + KEKA_TI_COLUMN_COUNT -1;
					position[1] = i;
					focusMap.add(position);
				}
			}
		}

		JKekkaRegisterTableEnterAction enterAction =
				new JKekkaRegisterTableEnterAction(
				focusMap,
				model
			);

		model.getActionMap().put(
				enterAction.getInputMapObject(),
				enterAction.getEnterAction()
		);
	}
}
//Source Code Version Tag System v1.00 -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

//edit s.inoue 2010/02/25
//一列化するように試み中のソース

//package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;
//
//import javax.swing.AbstractCellEditor;
//import javax.swing.DefaultCellEditor;
//import javax.swing.JComponent;
//import javax.swing.JScrollPane;
//import javax.swing.JTable;
//import javax.swing.JTextArea;
//import javax.swing.JTextField;
//import javax.swing.JViewport;
//import javax.swing.ListSelectionModel;
//import javax.swing.event.CellEditorListener;
//import javax.swing.event.ChangeEvent;
//import javax.swing.event.ChangeListener;
//import javax.swing.table.DefaultTableCellRenderer;
//import javax.swing.table.DefaultTableColumnModel;
//import javax.swing.table.DefaultTableModel;
//import javax.swing.table.TableCellEditor;
//import javax.swing.table.TableCellRenderer;
//import javax.swing.table.TableColumn;
//import javax.swing.table.TableColumnModel;
//import javax.swing.table.TableModel;
//import javax.swing.table.TableRowSorter;
//
//import java.awt.Color;
//import java.awt.Component;
//import java.awt.event.FocusAdapter;
//import java.awt.event.FocusEvent;
//import java.awt.event.ItemListener;
//import java.awt.event.KeyEvent;
//import java.awt.event.KeyListener;
//import java.awt.event.KeyAdapter;
//import java.awt.event.InputEvent;
//import java.awt.event.ActionEvent;
//import java.awt.event.ItemEvent;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.EventObject;
//import java.util.HashMap;
//import java.util.Hashtable;
//import java.util.Map;
//import java.util.List;
//import java.util.Calendar;
//import java.util.Vector;
//import java.util.regex.Pattern;
//import java.math.BigDecimal;
//import java.awt.Dimension;
//import org.apache.log4j.Logger;
//
//import com.lowagie.text.pdf.TextField;
//
//import jp.or.med.orca.jma_tokutei.common.component.ExtendedComboBox;
//import jp.or.med.orca.jma_tokutei.common.component.ExtendedTextArea;
//import jp.or.med.orca.jma_tokutei.common.component.ExtendedTextField;
//import jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode;
//import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
//import jp.or.med.orca.jma_tokutei.common.convert.JYearAge;
//import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
//import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
//import jp.or.med.orca.jma_tokutei.common.event.JEnterEvent;
//import jp.or.med.orca.jma_tokutei.common.focus.JFocusTraversalPolicy;
//import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
//import jp.or.med.orca.jma_tokutei.common.sql.dao.DaoFactory;
//import jp.or.med.orca.jma_tokutei.common.sql.dao.TKensakekaSonotaDao;
//import jp.or.med.orca.jma_tokutei.common.sql.dao.TKensakekaTokuteiDao;
//import jp.or.med.orca.jma_tokutei.common.sql.dao.TKojinDao;
//import jp.or.med.orca.jma_tokutei.common.sql.dao.TNayoseDao;
//import jp.or.med.orca.jma_tokutei.common.sql.model.TKensakekaSonota;
//import jp.or.med.orca.jma_tokutei.common.sql.model.TKensakekaTokutei;
//import jp.or.med.orca.jma_tokutei.common.sql.model.TKojin;
//import jp.or.med.orca.jma_tokutei.common.sql.model.TNayose;
//import jp.or.med.orca.jma_tokutei.common.table.EachRowEditor;
//import jp.or.med.orca.jma_tokutei.common.table.JComboCellEditor;
//import jp.or.med.orca.jma_tokutei.common.table.JSimpleTable;
//import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableCellPosition;
//import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableCellRendererData;
//import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableCellRowRenderer;
//import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableScrollPanel;
//import jp.or.med.orca.jma_tokutei.common.util.FiscalYearUtil;
//import jp.or.med.orca.jma_tokutei.common.validate.JValidate;
//import jp.or.med.orca.jma_tokutei.common.app.JApplication;
//import jp.or.med.orca.jma_tokutei.common.component.ImeController;
//
///**
//* 結果登録
//*/
//public class JKekkaRegisterFrameCtrl extends JKekkaRegisterFrame implements ItemListener,KeyListener {
//
//	private static final int[] DEFAULT_COLUMNS_WIDTH = new int[] {
//		0, 140, 140, 80, 120, 120, 80,
//				80, 80, 80, 80, 50, 120 };
//	private static final String COLUMN_NAME_HISU_FLG = "HISU_FLG";
//	private static final String COLUMN_NAME_JYOUGEN = "JYOUGEN";
//	private static final String COLUMN_NAME_KAGEN = "KAGEN";
//	private static final String COLUMN_NAME_KOMENTO = "KOMENTO";
//	private static final String COLUMN_NAME_HANTEI = "HANTEI";
//	private static final String COLUMN_NAME_H_L = "H_L";
//	private static final String COLUMN_NAME_JISI_KBN = "JISI_KBN";
//	private static final String COLUMN_NAME_JS_JYOUGEN = "JS_JYOUGEN";
//	private static final String COLUMN_NAME_JS_KAGEN = "JS_KAGEN";
//	private static final String COLUMN_NAME_DS_JYOUGEN = "DS_JYOUGEN";
//	private static final String COLUMN_NAME_DS_KAGEN = "DS_KAGEN";
//	private static final String COLUMN_NAME_CODE_NUM = "CODE_NUM";
//	private static final String COLUMN_NAME_CODE_NAME = "CODE_NAME";
//	private static final String COLUMN_NAME_KEKA_TI = "KEKA_TI";
//	private static final String COLUMN_NAME_MAX_BYTE_LENGTH = "MAX_BYTE_LENGTH";
//	private static final String COLUMN_NAME_DATA_TYPE = "DATA_TYPE";
//	private static final String COLUMN_NAME_KENSA_HOUHOU = "KENSA_HOUHOU";
//	private static final String COLUMN_NAME_KOUMOKUNAME = "KOUMOKUNAME";
//	private static final String COLUMN_NAME_KOUMOKUCD = "KOUMOKUCD";
//
//	// add s.inoue 2009/11/11
//	private static final int COLUMN_IDX_KOUMOKUCD = 0;
//	private static final int COLUMN_IDX_CODE_NAME = 1;
//	private static final int COLUMN_IDX_CODE_KENSA_HOUHOU = 2;
//	// edit s.inoue 2009/11/11
////	private static final int COLUMN_IDX_KEKKA_VALUE = 3;
//	private static final int COLUMN_IDX_KEKKA_NUM = 3;
//	private static final int COLUMN_IDX_KEKKA_CODE = 4;
//	private static final int COLUMN_IDX_KEKKA_STR = 5;
//
//	private static final int COLUMN_IDX_JISIKBN = 6;
//	private static final int COLUMN_IDX_DS_KAGEN = 7;
//	private static final int COLUMN_IDX_DS_JYOUGEN = 8;
//	private static final int COLUMN_IDX_JS_KAGEN = 9;
//	private static final int COLUMN_IDX_JS_JYOUGEN = 10;
//	private static final int COLUMN_IDX_HL = 11;
//	private static final int COLUMN_IDX_HANTEI = 12;
//	private static final int COLUMN_IDX_KOMENTO = 13;
//	private static final int COLUMN_IDX_HISUFLG = 14;
//
//	private static final String CODE_SEIKATU_KAIZEN = "9N801000000000011";
//	private static final String CODE_HOKEN_SHIDOU = "9N806000000000011";
//	private static final String CODE_SAIKETSU_ZIKAN = "9N141000000000011";
//	private static final String CODE_ISHINOHANDAN_1 = "9N501000000000011";
//	private static final String CODE_ISHINOHANDAN_2 = "9N506000000000011";
//	private static final String CODE_ISHINOHANDAN_3 = "9N511000000000049";
//	private static final String CODE_ISHINOHANDAN_4 = "9N516000000000049";
//	private static final String CODE_TAIJU = "9N006000000000001";
//	private static final String CODE_SHINCHOU = "9N001000000000001";
//	private static final String CODE_BMI = "9N011000000000001";
//
//	public final static int INSERT_MODE = 1;
//	public final static int UPDATE_MODE = 2;
//	private static final String strHighMessageValue = "上限値";
//	private static final String strLowMessageValue = "下限値";
//
//	private static Logger logger = Logger.getLogger(JKekkaRegisterFrameCtrl.class);
//
//	/**
//	 * テーブルの"結果(文字列)"欄にて 編集終了時に入力用ダイアログを表示するための DefaultCellEditorクラス
//	 */
//	private class PopupWindowShowCellEditor extends DefaultCellEditor {
//		/**
//		 * DefaultCellEditor セルエディタリスナ
//		 */
//		private PopupWindowCellEditorListener cellEditorListener;
//		private ImeController imeController = null;
//		/**
//		 * コンストラクタ
//		 * @param parent 健診結果データ入力画面
//		 * @param textField 結果(文字列)セルのエディタ
//		 * @param 選択中セルのカラムインデックス
//		 */
//		public PopupWindowShowCellEditor(JKekkaRegisterFrameCtrl parent,
//				JTextField textField,int index) {
//			super(textField);
//
//			// add s.inoue 2009/10/14
//			imeController = new ImeController();
//			imeController.addFocusListenerForCharcterSubsets(textField, ImeMode.IME_ZENKAKU);
//			cellEditorListener = new PopupWindowCellEditorListener(parent,index);
//			setClickCountToStart(1);
//		}
//	}
//
//	// add s.inoue 2009/12/22
//	// textfieldからtextareaへと移行
//	public class PopupWindowShowTextAreaCellEditor extends AbstractCellEditor implements
//	TableCellEditor, TableCellRenderer {
//		  private PopupWindowCellEditorListener cellEditorListener;
//		  private ImeController imeController = null;
//
//		  private JTextArea textArea;
//		  private JScrollPane scroll;
//
//		  public PopupWindowShowTextAreaCellEditor(
//				JKekkaRegisterFrameCtrl parent,
//					ExtendedTextArea textAreas,
//					int index) {
//			// edit s.inoue 2009/12/24
//			// textArea = new JTextArea();
//			scroll = new JScrollPane(textAreas,
//				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
//				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//
//			// add s.inoue 2009/10/14
//			imeController = new ImeController();
//			imeController.addFocusListenerForCharcterSubsets(scroll, ImeMode.IME_ZENKAKU);
//			cellEditorListener = new PopupWindowCellEditorListener(parent,index);
//		  }
//
//		  public JTextArea getTextArea(){
//		    return textArea;
//		  }
//
//		  public Object getCellEditorValue() {
//		    return textArea.getText();
//		  }
//
//		  public boolean shouldSelectCell(EventObject anEvent) {
//		    return true;
//		  }
//
//		  public boolean stopCellEditing() {
//		    fireEditingStopped();
//		    return true;
//		  }
//
//		  public void cancelCellEditing() {
//		    fireEditingCanceled();
//		  }
//
//		  public Component getTableCellEditorComponent(JTable table, Object value,
//		      boolean isSelected, int row, int column) {
//			if (textArea == null)
//				return null;
//		    textArea.setText((value != null) ? value.toString() : "");
//		    textArea.setCaretPosition(textArea.getText().length());
//		    return scroll;
//		  }
//
//		  public Component getTableCellRendererComponent(JTable table,
//		   Object value, boolean isSelected, boolean hasFocus, int row, int column){
//		    textArea.setText((value != null) ? value.toString() : "");
//		    textArea.setCaretPosition(textArea.getText().length());
//		    return scroll;
//		  }
//	}
//
//	/**
//	 * テーブルの"結果(文字列)"欄DefaultCellEditorの 編集完了/ｷｬﾝｾﾙリスナークラス
//	 */
//	private class PopupWindowCellEditorListener implements CellEditorListener {
//
//		/**
//		 * 健診結果データ入力画面
//		 */
//		private JKekkaRegisterFrameCtrl parent;
//
//		/**
//		 * 入力ダイアログ
//		 */
//		private IKekkaRegisterInputDialog dialog;
//
//		/**
//		 * コンストラクタ
//		 * @param parent 健診結果データ入力画面
//		 * @param index 選択中セルのカラムインデックス
//		 */
//		public PopupWindowCellEditorListener(JKekkaRegisterFrameCtrl parent,int index) {
//			this.parent = parent;
//			dialog = JKekkaRegisterInputDialogFactory.createDialog(parent,index);
//		}
//
//		/**
//		 * 編集キャンセルイベントコールバック
//		 * @param changeEvent 変更イベント
//		 */
//		public void editingCanceled(ChangeEvent changeEvent) {
//		}
//
//		/**
//		 * 編集完了イベントコールバック
//		 * @param changeEvent 変更イベント
//		 */
//		public void editingStopped(ChangeEvent changeEvent) {
//
//			// 健診結果データ入力画面のテーブルから現在選択されているセルのデータを取得
//			int columnIndex = parent.model.getSelectedColumn();
//			int rowIndex = parent.model.getSelectedRow();
//			String kekkaMojiretsu = (String) parent.model.getData(rowIndex,columnIndex);
//
//			// "結果(文字列)"入力ダイアログのテキストエリアに選択セルデータをセット
//			dialog.setText(kekkaMojiretsu);
//			// "結果(文字列)"入力ダイアログ表示
//			dialog.setVisible(true);
//
//			// edit s.inoue 2009/12/06
//			// if (dialog.getStatus() != RETURN_VALUE.OK)
//			if (dialog.getStatus() != RETURN_VALUE.YES)
//				return;
//			// "結果(文字列)"入力ダイアログから入力された 文字列を選択セルにセット
//			parent.model.setData(dialog.getText(), rowIndex, columnIndex);
//		}
//	}
//
//	/**
//	 * 総合コメント欄キーリスナクラス
//	 */
//	private class SougouCommentKeyListener implements KeyListener {
//
//		/**
//		 * 健診結果データ入力画面
//		 */
//		private JKekkaRegisterFrameCtrl parent;
//
//		/**
//		 * 入力ダイアログ
//		 */
//		private IKekkaRegisterInputDialog dialog;
//
//		/**
//		 * コンストラクタ
//		 * @param parent 健診結果データ入力画面
//		 */
//		public SougouCommentKeyListener(JKekkaRegisterFrameCtrl parent) {
//			this.parent = parent;
//			// ダイアログを生成
//			dialog = JKekkaRegisterInputDialogFactory.createDialog(parent, 12);
//		}
//
//		public void keyPressed(KeyEvent keyEvent) {
//			// Enterキー押下時
//			if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
//				// Shiftキーが一緒に押されたか?
//				int modfiersKey = keyEvent.getModifiersEx();
//				if ((modfiersKey & InputEvent.CTRL_DOWN_MASK) == 0)
//					return;
//				String comment = jEditorPane_Comment.getText();
//				// "結果(文字列)"入力ダイアログのテキストエリアに選択セルデータをセット
//				dialog.setText(comment);
//				// "結果(文字列)"入力ダイアログ表示
//				dialog.setVisible(true);
//				if (dialog.getStatus() != RETURN_VALUE.OK)
//					return;
//				/*
//				 * 取得したテキストを、現在の入力内容に追加 するよう変更。
//				 */
//				parent.jEditorPane_Comment.setText(comment + dialog.getText());
//			}
////			// Enterキー押下時
////			// if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
////			// add s.inoue 2009/12/03
////			switch(keyEvent.getKeyCode()){
////				case KeyEvent.VK_ENTER:
////					// Shiftキーが一緒に押されたか?
////					int modfiersKey = keyEvent.getModifiersEx();
////					if ((modfiersKey & InputEvent.CTRL_DOWN_MASK) == 0)
////						return;
////					String comment = jEditorPane_Comment.getText();
////					// "結果(文字列)"入力ダイアログのテキストエリアに選択セルデータをセット
////					dialog.setText(comment);
////					// "結果(文字列)"入力ダイアログ表示
////					dialog.setVisible(true);
////					// edit s.inoue 2009/12/06
////					if (dialog.getStatus() != RETURN_VALUE.YES)
////					// if (dialog.getStatus() != RETURN_VALUE.OK)
////						return;
////					/*
////					 * 取得したテキストを、現在の入力内容に追加 するよう変更。
////					 */
////					parent.jEditorPane_Comment.setText(comment + dialog.getText());
////					break;
//////del s.inoue 2009/12/21
//////				case KeyEvent.VK_F1:
//////					pushedEndButton(null);break;
//////				case KeyEvent.VK_F2:
//////					pushedClearButton(null);break;
//////				case KeyEvent.VK_F12:
//////					pushedRegisterButton(null);break;
////				}
//		}
//
//		public void keyReleased(KeyEvent keyEvent) {
//		}
//
//		public void keyTyped(KeyEvent keyEvent) {
//		}
//
//	}
//
//	/**
//	 * 結果(コード)欄のDefaultCellEditorクラスを追加
//	 */
//	private class InputDigitHalfUpCellEditor extends DefaultCellEditor {
//		private ImeController imeController = null;
//		/**
//		 * コンストラクタ
//		 * @param textField セルのDefaultEditor
//		 */
//		public InputDigitHalfUpCellEditor(final JTextField textField) {
//			super(textField);
//
//			// add s.inoue 2009/10/14
//			imeController = new ImeController();
//			imeController.addFocusListenerForCharcterSubsets(textField, ImeMode.IME_OFF);
//
//			// 編集リスナを追加
//			addCellEditorListener(new CellEditorListener() {
//
//				private final String DIGIT_FORMAT_REGEXP = "[#\\.0]+";
//
//				/**
//				 * 編集キャンセルイベントコールバック
//				 * @param changeEvent 変更イベント
//				 */
//				public void editingCanceled(ChangeEvent changeEvent) {
//				}
//
//				/**
//				 * 編集完了イベントコールバック
//				 * @param changeEvent 変更イベント
//				 */
//				public void editingStopped(ChangeEvent changeEvent) {
//
//					String textValue = textField.getText();
//					if ((textValue == null) || (textValue.length() == 0))
//						return;
//
//					// 現在選択されているセルの行・列インデックスを取得
//					int columnIndex = model.getSelectedColumn();
//					int rowIndex = model.getSelectedRow();
//
//					// フォーマット文字列を取得する
//					Map<String, String> recordMap = tableResult.get(rowIndex);
//					String format = recordMap.get(COLUMN_NAME_MAX_BYTE_LENGTH);
//
//					// フォーマット文字列の妥当性を検証
//					if (!isValidFormat(format))
//						return;
//
//					// フォーマット文字列の作成
//					int width = getWidth(format);
//					int scale = getScale(format);
//					String formatStr = "%." + scale + "f";
//
//					String roundUpValue = "";
//					try {
//						double value = new Double(textField.getText())
//								.doubleValue();
//						// 小数点第2位以下を四捨五入し小数点第1位までを表示
//						roundUpValue = String.format(formatStr, new BigDecimal(
//								value).setScale(scale + 1,
//								BigDecimal.ROUND_HALF_UP).floatValue());
//						// 整数部・小数部が指定桁数をオーバーしている場合はクリア
//						if ((getWidth(roundUpValue) > width)
//								|| (getScale(roundUpValue) > scale))
//							roundUpValue = "";
//					} catch (NumberFormatException e) {
//					}
//					// 四捨五入値をセルにセット
//					model.setData(roundUpValue, rowIndex, columnIndex);
//
//					// add ver2 s.inoue 2009/06/24
//					int  selectedColumnIndex=model.getSelectedColumn();
//					int  selectedRowIndex=model.getSelectedRow();
//
//					if ((selectedColumnIndex == 3) &&
//							(selectedRowIndex== 0 || selectedRowIndex== 1)) {
//						actionBMICalc();
//					}
//
//				}
//
//				/**
//				 * 妥当なフォーマット文字列か検証する
//				 * @param rowIndex
//				 */
//				private boolean isValidFormat(String format) {
//
//					if ((format == null) || (format.length() < 1))
//						return false;
//
//					Pattern pattern = Pattern.compile(DIGIT_FORMAT_REGEXP);
//					if (!pattern.matcher(format).matches())
//						return false;
//					int pos = format.indexOf(".");
//					if (pos == (format.length() - 1))
//						return false;
//
//					// 小数点の指定が無い場合
//					if (pos < 0) {
//						pattern = Pattern.compile("#+");
//						return pattern.matcher(format).matches();
//					}
//
//					pos++;
//					pattern = Pattern.compile("0+");
//					return pattern.matcher(
//							format.substring(pos, format.length())).matches();
//				}
//
//				/**
//				 * 整数部の桁数を取得する
//				 *
//				 * @param format
//				 * @return
//				 */
//				private int getWidth(String format) {
//					if ((format == null) || (format.length() == 0))
//						return -1;
//					int pos = format.indexOf(".");
//					if (pos < 0)
//						return format.length();
//					if ((pos == 0) || (pos == (format.length() - 1)))
//						return -1;
//					return format.substring(0, pos).length();
//				}
//
//				/**
//				 * 小数部の桁数を取得する
//				 *
//				 * @param format
//				 * @return
//				 */
//				private int getScale(String format) {
//					int pos = format.indexOf(".");
//					if (pos < 0)
//						return 0;
//					if ((pos == 0) || (pos == (format.length() - 1)))
//						return -1;
//					pos++;
//					return format.substring(pos, format.length()).length();
//				}
//			});
//		}
//	}
//
////	private JSimpleTableScrollPanel scroll;
////	private JSimpleTable table;
//	private JSimpleTable model=null;
//	private JSimpleTable modelfixed= null;
//	private DefaultTableModel dmodel = null;
//	private TableRowSorter<TableModel> sorter =null;
//	Object[][] rowcolumn = null;
//
//	private String[] header = { "","項目名","検査方法","結果（数値）[入力]","結果（コード）[入力]","結果（文字列）[入力]","実施区分[入力]","基準値下限（男性）","基準値上限（男性）","基準値下限（女性）","基準値上限（女性）","H/L[入力]","判定","コメント[入力]" };
//
//	private int mode;
//	private JKekkaRegisterFrameData validatedData = new JKekkaRegisterFrameData();  //  @jve:decl-index=0:
//	// edit s.inoue 2010/02/19
//	private ExtendedComboBox kekkaCombo = null;
//	private ExtendedComboBox hanteiCombo = new ExtendedComboBox();
//	private ExtendedComboBox hlCombo = new ExtendedComboBox();
//	private ExtendedComboBox jisiCombo = new ExtendedComboBox();
//	private EachRowEditor rowEditor = null;
//	private Vector<Hashtable<String, String>> comboData = null;
//	private JComboCellEditor kekkaComboEditor = null;
//	// del s.inoue 2009/11/23
////	private JComboCellEditor hanteiComboEditor = new JComboCellEditor(hanteiCombo);
////	private JComboCellEditor hlComboEditor = new JComboCellEditor(hlCombo);
////	private JComboCellEditor jisiComboEditor = new JComboCellEditor(jisiCombo);
//
//	private Vector<JSimpleTableCellRendererData> cellColors = new Vector<JSimpleTableCellRendererData>();
//	private String BMIFormat = null;
//	private JSimpleTableCellPosition BMICellPosition = null;
//	private JSimpleTableCellPosition heightCellPosition = null;
//	private JSimpleTableCellPosition weightCellPosition = null;
//	private Vector<JSimpleTableCellPosition> forbitCells = new Vector<JSimpleTableCellPosition>();
//	private final Color COLOR_HISU = ViewSettings.getRequiedItemBgColor();
//	private final Color COLOR_DISABLE = ViewSettings.getDisableItemBgColor();
//	// add h.yoshitama 2009/09/18
//	private final Color COLOR_ABLE = ViewSettings.getAbleItemBgColor();
//
//	/* フォーカス移動制御 */
//	private JFocusTraversalPolicy focusTraversalPolicy = null;
//
//	/* Enter キー押下でフォーカスを移動するコンポーネント */
//	private List<Component> enterkeyFocusComponents = null;
//
//	// Proxyとして管理する
//	private static jp.or.med.orca.jma_tokutei.common.csv.JCSVReaderStream Reader = null;
//	private int kenshinPatternNumber = -1;
//	private int prekenshinPatternNumber = -1;
//	private ArrayList<Hashtable<String, String>> tableResult = null;
//	private String[] hanteiCodeTable = { "未判定", "異常なし", "軽度異常", "要経過観察", "異常","要精検" };
//	private String[] hlCodeTable = { "", "L", "H" };
//	private boolean isInit = false;
//	private static final int HISU_FLG_HISU = 1;
//
//	// T_KOJIN,T_KENSAKEKA_TOKUTEIにUKETUKE_ID,NENDO追加対応
//	private TKensakekaTokutei kensakekaTokutei = new TKensakekaTokutei();
//	private TKensakekaSonota kensakekaSonota = new TKensakekaSonota();
//	private TKensakekaTokuteiDao kensakekaTokuteiDao = null;
//	private TKensakekaSonotaDao kensakekaSonotaDao = null;
//	private String initialDate = null;
//	private boolean isCopy = false;
//	// edit s.inoue 2009/11/24
//	private Hashtable<String, String> kekkaCmbdataSet = null;
//
//	// edit s.inoue 2009/12/10
//	private ExtendedTextField textAreaField = null;
//
//	/**
//	 * コンストラクタ
//	 * @param srcData このフレームのデータ
//	 * @param kensaJissiDate 健診実施日
//	 */
//	public JKekkaRegisterFrameCtrl(
//			JKekkaListFrameData srcData,
//			String kensaJissiDate,
//			boolean isCopy
//			) {
//		// edit s.inoue 2009/11/20
//		initializeSetting(
//				srcData,
//				kensaJissiDate,
//				isCopy);
//	}
//
//	// edit s.inoue 2009/11/20
//	private void initializeSetting(
//			JKekkaListFrameData srcData,
//			String kensaJissiDate,
//			boolean isCopy){
//
//		this.initialDate = kensaJissiDate;
//		this.isCopy = isCopy;
//
//		/* コンボボックスを初期化する。 */
//		this.initializeComboBox();
//
//		/* 入力コントロールを初期化する。 */
//		this.initializeInputComponents(srcData, kensaJissiDate, isCopy);
//
//		dmodel = new DefaultTableModel(refreshTable(kensaJissiDate, true,false,true),header){
//	      public boolean isCellEditable(int row, int column) {
//	    	boolean retflg = false;
//	    	if (column == 3 || column == 4 || column == 5 || column == 6|| column == 11 || column > 14){
//	    		retflg = true;
//	      	}else{
//	      		retflg = false;
//	      	}
//	        return retflg;
//	      }
//	    };
//
//		sorter = new TableRowSorter<TableModel>(dmodel);
//		model = new JSimpleTable(dmodel);
//
//		// add s.inoue 2010/02/19
//		this.setTableFocusMap();
//
//		modelfixed = new JSimpleTable(dmodel);
//
//		// edit s.inoue 2009/11/20
//		modelfixed.setPreferedColumnWidths(new int[]{0,150, 150});
//		model.setPreferedColumnWidths(new int[] {  80, 120, 120, 80,80, 80, 80, 80, 50, 120  ,120});
//
//		modelfixed.setSelectionModel(model.getSelectionModel());
//
//		for(int i=dmodel.getColumnCount()-1;i>=0;i--) {
//         if(i<4) {
//         }else{
//             modelfixed.removeColumn(modelfixed.getColumnModel().getColumn(i));
//         }
//     }
//
//	    model.setRowSorter(sorter);
//	    model.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
//	    model.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//
//	    modelfixed.setRowSorter(sorter);
//	    modelfixed.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
//	    modelfixed.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//
//	    initilizeFocus();
//
//     final JScrollPane scroll = new JScrollPane(model);
//     JViewport viewport = new JViewport();
//     viewport.setView(modelfixed);
//     viewport.setPreferredSize(modelfixed.getPreferredSize());
//     scroll.setRowHeader(viewport);
//
//     modelfixed.setPreferredScrollableViewportSize(modelfixed.getPreferredSize());
//     scroll.setRowHeaderView(modelfixed);
//     scroll.setCorner(JScrollPane.UPPER_LEFT_CORNER, modelfixed.getTableHeader());
//
//     scroll.getRowHeader().addChangeListener(new ChangeListener() {
//         public void stateChanged(ChangeEvent e) {
//             JViewport viewport = (JViewport) e.getSource();
//             scroll.getVerticalScrollBar().setValue(viewport.getViewPosition().y);
//         }
//     });
//
//     jPanel_Table.add(scroll);
//
//		dmodel.setDataVector(refreshTable(kensaJissiDate, true,false,true),header);
//
//		// 4列以下は固定、以降は変動
//		for(int i=dmodel.getColumnCount()-1;i>=0;i--) {
//         if(i<3) {
//             model.removeColumn(model.getColumnModel().getColumn(i));
//             modelfixed.getColumnModel().getColumn(i).setResizable(false);
//         }
//     }
//
//	   TableColumnModel columnsfix = modelfixed.getColumnModel();
//	   for(int i=0;i<columnsfix.getColumnCount();i++) {
//
//		   columnsfix.getColumn(i).setCellRenderer(new JSimpleTableCellRowRenderer(
//	   			(DefaultTableCellRenderer)modelfixed.getDefaultRenderer(modelfixed.getColumnClass(i))));
//	   }
//
//	   TableColumnModel columns = model.getColumnModel();
//	   for(int i=0;i<columns.getColumnCount();i++) {
//
//		   columns.getColumn(i).setCellRenderer(new JSimpleTableCellRowRenderer(
//	   			(DefaultTableCellRenderer)model.getDefaultRenderer(model.getColumnClass(i))));
//	   }
//
//		/**** 入力系コントロール初期化 ****/
//		this.model.addKeyListener(new JEnterEvent(this));
//
//		// move s.inoue 2010/02/19
//		/* テーブル項目を初期化する */
//		// this.initializeTableColumn();
//
//		/* テーブルをイベントを初期化する。 */
//		this.initializeTableListner();
//		/* セルの色を更新する。 */
//		this.refreshTableCellColor();
//		/* 列の幅を初期化する。 */
//		this.initializeColumnWidth();
//
////		this.table.setPreferedColumnWidths(DEFAULT_COLUMNS_WIDTH);
//		// add s.inoue 2009/11/12
//		model.addHeader(this.header);
//		model.refreshTable(); modelfixed.refreshTable();
//
//		// move s.inoue 2010/02/19
//		/* テーブル項目を初期化する */
//		this.initializeTableColumn();
//		this.refreshTableCellColor();
//		this.initializeColumnWidth();
//		this.setTableFocusMap();
//		// 初期選択
//		if (model.getRowCount() > 0) {
//			model.setRowSelectionInterval(0, 0);
//		}
//	}
//
//	private void initilizeFocus(){
//		/* フォーカス遷移を初期化する。 */
//		this.initializeFocusTraversal();
//		// add s.inoue 2009/12/03
//		functionListner();
//
//		/* Added 2008/03/12 若月 必須項目を指定の背景色に設定する */
//		this.jTextField_Date.setBackground(ViewSettings.getRequiedItemBgColor());
//		this.jComboBox_Pattern.setBackground(ViewSettings.getRequiedItemBgColor());
//		this.jComboBox_SeikyuKubun.setBackground(ViewSettings.getRequiedItemBgColor());
//		// add h.yoshitama 2009/09/24
//		jLabel_requiredExample.setBackground(ViewSettings.getRequiedItemBgColor());
//		jLabel_ableExample.setBackground(ViewSettings.getAbleItemBgColor());
//		jLabel_disableExample.setBackground(ViewSettings.getDisableItemBgColor());
//
//		if (isCopy) {
//			this.jTextField_Date.grabFocus();
//			// add ver2 s.inoue 2009/06/29
//			jTextField_SeiriNo.setVisible(true);
//			jLabelSeirino.setVisible(true);
//		}
//	}
//
//	private boolean isNewKekkaData = false;
//
//	private void initializeInputComponents(JKekkaListFrameData srcData, String kensaJissiDate, boolean isCopy) {
//
//		Dimension size =ViewSettings.getFrameCommonSize();
//
//		this.setSize(size);
//		this.setPreferredSize(size);
//		this.setMinimumSize(size);
//		this.setLocationRelativeTo( null );
//
//		// 前フレームから受け継いだデータをセット
//		jTextField_HokenjyaCode.setText(srcData.getHihokenjyaCode());
//		jTextField_HokenjyaNumber.setText(srcData.getHihokenjyaNumber());
//		jTextField_Name.setText(srcData.getName());
//
//		/* 性別名の表示 */
//		validatedData.setHokenjyaNumber(srcData.getHokenjyaNumber());
//		validatedData.setHihokenjyaCode(jTextField_HokenjyaCode.getText());
//		validatedData.setHihokenjyaNumber(jTextField_HokenjyaNumber.getText());
//		validatedData.setUketuke_id(srcData.getUketuke_id());
//		validatedData.setUketukePre_id(srcData.getUketukePre_id());
//
//		jTextFieldsexName.setText(srcData.getSexName());
//		jTextField_HokenjyaCode.setEditable(false);
//		jTextField_HokenjyaNumber.setEditable(false);
//		jTextField_Name.setEditable(false);
//		jTextFieldBirthDay.setText(srcData.getBirthDay());
//		jTextFieldNenrei.setText(JYearAge.getAge(jTextFieldBirthDay.getText()));
//
//		try {
//			Connection connection = JApplication.kikanDatabase.getMConnection();
//
//			kensakekaTokuteiDao = (TKensakekaTokuteiDao) DaoFactory.createDao(
//					connection,
//					kensakekaTokutei);
//
//			kensakekaSonotaDao = (TKensakekaSonotaDao) DaoFactory.createDao(
//					connection,
//					kensakekaSonota);
//		} catch (Exception e) {
//			logger.error(e.getMessage());
//			e.printStackTrace();
//		}
//
//		this.isNewKekkaData = false;
//
//		/* 結果データ複製以外の場合 */
//		if (! isCopy) {
//			/* 健診実施日が空の場合 */
//			if (kensaJissiDate.equals("")) {
//				this.isNewKekkaData = true;
//
//				/* システム日時を入力する。 */
//				Calendar cal = Calendar.getInstance();
//
//				String year = JValidate.fillZero(String.valueOf(cal.get(Calendar.YEAR)), 4);
//				String month = JValidate.fillZero(String.valueOf(cal.get(Calendar.MONTH) + 1), 2);
//				String date = JValidate.fillZero(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)), 2);
//
//				String jissiDateString = year + month + date;
//
//				jTextField_Date.setText(jissiDateString);
//				this.validatedData.setKensaJissiDate(jissiDateString);
//
//			/* 健診実施日が指定されている場合 */
//			} else {
//				validatedData.setKensaJissiDate(kensaJissiDate);
//				jTextField_Date.setText(validatedData.getKensaJissiDate());
//			}
//		} else {
//			/* 結果データ複製の場合 */
//			validatedData.setKensaJissiDate(kensaJissiDate);
//			this.isNewKekkaData = true;
//		}
//
//		// 総合コメント欄にキーリスナ追加
//		jEditorPane_Comment.addKeyListener(new SougouCommentKeyListener(this));
//
//		this.jEditorPane_Comment.addFocusListener(new FocusAdapter(){
//			@Override
//			public void focusLost(FocusEvent arg0) {
//				jEditorPane_Comment.getInputContext().setCompositionEnabled(false);
//				jEditorPane_Comment.getInputContext().setCharacterSubsets(null);
//			}
//		});
//		jEditorPane_Comment.getInputContext().setCharacterSubsets(null);
//		/* 実施日設定 */
//		this.changeInputDate(this.validatedData.getKensaJissiDate(), false);
//
//	}
//
//	private void initializeEnterkeyFocusComponents() {
//		/* Enter キー押下でフォーカスを移動するコンポーネントを登録する。 */
//		this.enterkeyFocusComponents = new ArrayList<Component>();
//
//		this.enterkeyFocusComponents.add(jComboBox_Pattern);
//		this.enterkeyFocusComponents.add(jTextField_Date);
//		this.enterkeyFocusComponents.add(jComboBox_MetaboHantei);
//		this.enterkeyFocusComponents.add(jComboBox_HokenSidouLevel);
//		this.enterkeyFocusComponents.add(jComboBox_SeikyuKubun);
//		// add ver2 s.inoue 2009/06/29
//		if (isCopy){
//			this.enterkeyFocusComponents.add(jTextField_SeiriNo);
//		}
//		this.enterkeyFocusComponents.add(jEditorPane_Comment);
//	}
//
//	/* フォーカス初期化 */
//	private void initializeFocusTraversal() {
//		this.focusTraversalPolicy = new JFocusTraversalPolicy();
//		this.setFocusTraversalPolicy(this.focusTraversalPolicy);
//
//		this.focusTraversalPolicy.setDefaultComponent(jComboBox_Pattern);
//
//		this.focusTraversalPolicy.addComponent(jComboBox_Pattern);
//		this.focusTraversalPolicy.addComponent(jTextField_Date);
//		this.focusTraversalPolicy.addComponent(jComboBox_SeikyuKubun);
//		if (isCopy){
//			this.focusTraversalPolicy.addComponent(jTextField_SeiriNo);
//		}
//		this.focusTraversalPolicy.addComponent(jComboBox_MetaboHantei);
//		this.focusTraversalPolicy.addComponent(jComboBox_HokenSidouLevel);
//		this.focusTraversalPolicy.addComponent(jEditorPane_Comment);
//		this.focusTraversalPolicy.addComponent(model);
//
//		this.focusTraversalPolicy.addComponent(jButton_Register);
//		this.focusTraversalPolicy.addComponent(jButton_Clear);
//		this.focusTraversalPolicy.addComponent(jButton_End);
//	}
//
//	// add s.inoue 2009/12/03
//	private void functionListner(){
//		for (int i = 0; i < focusTraversalPolicy.getComponentSize(); i++) {
//			Component comp = focusTraversalPolicy.getComponent(i);
//			comp.addKeyListener(this);
//		}
//	}
//
//	/* テーブル項目登録 */
//	private void initializeTableColumn(){
////del s.inoue 2009/11/23
////		   EachRowEditor rowEditor = new EachRowEditor(table);
////		    rowEditor.setEditorAt(1, new JComboCellEditor(kekkaCombo));
////		    table.getColumn("結果（数値）[入力]").setCellEditor(rowEditor);
//
//
////		for (int i = 0; i < comboData.size(); i++) {
////			this.model.setData(comboData.get(i).get("DATA"), Integer
////					.valueOf(comboData.get(i).get("ROW")), Integer
////					.valueOf(comboData.get(i).get("COLUMN")));
////			}
//
//		EachRowEditor rowEditor = new EachRowEditor(model);
//
//		// 結果(コード)
//		for (int i = 0; i < comboData.size(); i++) {
////				if (comboData.get(i).get("COLUMN").equals(String.valueOf(COLUMN_IDX_KEKKA_CODE))){
//					System.out.println(comboData.get(i).get("ROW")+ "行");
//					kekkaCombo.addItem(comboData.get(i).get("ROW") + i);
//
//					// JComboCellEditor → DefaultCellEditor
//					rowEditor.setEditorAt(
//							Integer.valueOf(comboData.get(i).get("ROW")), new DefaultCellEditor(kekkaCombo));
////				}
//		}
//		model.getColumn("結果（コード）[入力]").setCellEditor(rowEditor);
//
////		for (int i = 0; i < comboData.size(); i++) {
////			int rownon = Integer.valueOf(comboData.get(i).get("COLUMN"));
////			if (rownon != temprow){
////				System.out.println(rownon + "行");
////				rowEditor.setEditorAt(
////						Integer.valueOf(comboData.get(i).get("COLUMN")), new JComboCellEditor(kekkaCombo));
////			}
////			temprow = rownon;
////		}
////		table.getColumn("結果（数値）[入力]").setCellEditor(rowEditor);
//
//
//		// rowcolumn[Integer.valueOf(comboData.get(i).get("ROW"))]
//			//           [Integer.valueOf(comboData.get(i).get("COLUMN"))] = comboData.get(i).get("DATA");
////		kekkaComboEditor = new JComboCellEditor(kekkaCombo);
////		table.setCellComboBoxEditor(kekkaComboEditor, COLUMN_IDX_KEKKA_CODE);
////		table.getColumn("結果（数値）[入力]").setCellEditor(kekkaComboEditor);
//
////		/* 基準値下限(男性),基準値上限(男性),基準値下限(女性),基準値上限(女性)を */
////		// 結果コード値のカラムをコンボボックスに
////		this.table.setCellComboBoxEditor(kekkaComboEditor, COLUMN_IDX_KEKKA_CODE);
////		// 実施区分
////		this.table.setCellComboBoxEditor(jisiComboEditor, COLUMN_IDX_JISIKBN);
////		// 判定結果のカラムをコンボボックスに
////		this.table.setCellComboBoxEditor(hanteiComboEditor, COLUMN_IDX_HANTEI);
////		// HLのカラムをコンボボックスに
////		this.table.setCellComboBoxEditor(hlComboEditor, COLUMN_IDX_HL);
//	}
//
//	/* テーブルListner登録 */
//	private void initializeTableListner() {
//
//		kekkaCombo.addKeyListener(new KeyListener(){
//			@Override
//			public void keyPressed(KeyEvent e) {
//				if(e.getKeyCode() == KeyEvent.VK_ENTER){
//					kekkaCombo.setSelectedIndex(kekkaCombo.getSelectedIndex());
//				 }
//			}
//			@Override
//			public void keyReleased(KeyEvent e) {}
//			@Override
//			public void keyTyped(KeyEvent e) {}
//		});
//
//
//		hanteiCombo.addKeyListener(new KeyListener(){
//			@Override
//			public void keyPressed(KeyEvent e) {
//				if(e.getKeyCode() == KeyEvent.VK_ENTER){
//					hanteiCombo.setSelectedIndex(hanteiCombo.getSelectedIndex());
//				 }
//			}
//			@Override
//			public void keyReleased(KeyEvent e) {}
//			@Override
//			public void keyTyped(KeyEvent e) {}
//		});
//
//		hlCombo.addKeyListener(new KeyListener(){
//			@Override
//			public void keyPressed(KeyEvent e) {
//				if(e.getKeyCode() == KeyEvent.VK_ENTER){
//					hlCombo.setSelectedIndex(hlCombo.getSelectedIndex());
//				 }
//			}
//			@Override
//			public void keyReleased(KeyEvent e) {}
//			@Override
//			public void keyTyped(KeyEvent e) {}
//		});
//
//		jisiCombo.addKeyListener(new KeyListener(){
//			@Override
//			public void keyPressed(KeyEvent e) {
//				if(e.getKeyCode() == KeyEvent.VK_ENTER){
//					jisiCombo.setSelectedIndex(jisiCombo.getSelectedIndex());
//				 }
//			}
//			@Override
//			public void keyReleased(KeyEvent e) {}
//			@Override
//			public void keyTyped(KeyEvent e) {}
//		});
//
//	}
//
//	private void initializeComboBox() {
//		// 保健指導レベルコンボボックスの初期設定
//		jComboBox_HokenSidouLevel.addItem("未判定");
//		jComboBox_HokenSidouLevel.addItem("積極的支援");
//
//		jComboBox_HokenSidouLevel.addItem("動機づけ支援");
//		jComboBox_HokenSidouLevel.addItem("なし（情報提供）");
//		jComboBox_HokenSidouLevel.addItem("判定不能");
//
//		// メタボリックシンドローム判定コンボボックスの初期設定
//		jComboBox_MetaboHantei.addItem("未判定");
//		jComboBox_MetaboHantei.addItem("基準該当");
//
//		jComboBox_MetaboHantei.addItem("予備群該当");
//
//		jComboBox_MetaboHantei.addItem("非該当");
//		jComboBox_MetaboHantei.addItem("判定不能");
//
//		// 請求区分コンボボックスの初期設定
//		jComboBox_SeikyuKubun.addItem("基本的な健診");
//		jComboBox_SeikyuKubun.addItem("基本的な健診＋詳細な健診");
//
//		jComboBox_SeikyuKubun.addItem("基本的な健診＋追加健診項目");
//		jComboBox_SeikyuKubun.addItem("基本的な健診＋詳細な健診＋追加健診項目");
//
//		jComboBox_SeikyuKubun.addItem("人間ドック");
//	}
//
//	/**
//	 * 列サイズを初期化する。
//	 */
//	private void initializeColumnWidth() {
//		this.model.setAutoResizeMode(JSimpleTable.AUTO_RESIZE_OFF);
//
//		DefaultTableColumnModel columnModel = (DefaultTableColumnModel) this.modelfixed.getColumnModel();
//		columnModel.getColumn(COLUMN_IDX_KOUMOKUCD).setMinWidth(0);
//		columnModel.getColumn(COLUMN_IDX_KOUMOKUCD).setPreferredWidth(0);
//		columnModel.getColumn(COLUMN_IDX_KOUMOKUCD).setMaxWidth(0);
//		columnModel.getColumn(COLUMN_IDX_KOUMOKUCD).setWidth(0);
//	}
//
//	/**
//	 * 上限値下限値の項目をチェックを行う(CSV⇒DBへ変更)
//	 */
//	public static boolean CheckHighLawItem(String koumokuCode,String hknjaNm,String value){
//		ArrayList<Hashtable<String, String>> result = null;
//
//		try{
//			String[] params = { hknjaNm,koumokuCode };
//
//			String sql = "SELECT KOUMOKU_CD,KAGEN,JYOUGEN " +
//			"FROM T_KENSHINMASTER WHERE HKNJANUM = ? AND KOUMOKU_CD = ? ";
//
//			// 空値の場合はチェックを行わない
//			if (!value.isEmpty() ||
//					!hknjaNm.isEmpty()) {
//				String strKagen = "";
//				String strJyogen = "";
//
//				result = JApplication.kikanDatabase.sendExecuteQuery(sql, params);
//
//				for (int i = 0; i < result.size(); i++) {
//
//					strKagen = result.get(i).get("KAGEN");
//					strJyogen = result.get(i).get("JYOUGEN");
//
//					// 項目コードの結果判定の部分を除いて比較を行う
//					// 範囲内の場合 true
//					if (result.get(i).get("KOUMOKU_CD").equals(koumokuCode)) {
//						if (value.isEmpty())
//							break;
//						if (strKagen.isEmpty() && strJyogen.isEmpty()){
//							break;
//						}else if (strKagen.isEmpty()){
//							return Double.valueOf(strJyogen) >= Double.valueOf(value);
//						}else if(strJyogen.isEmpty()){
//							return Double.valueOf(strKagen) <= Double.valueOf(value);
//						}else if (!strKagen.isEmpty() && !strJyogen.isEmpty()){
//							return (Double.valueOf(strKagen) <= Double.valueOf(value)
//									&& Double.valueOf(value) <= Double.valueOf(strJyogen));
//						}
//					}
//				}
//			}
//		}catch(Exception ex){
//			ex.printStackTrace();
//			logger.warn(ex.getMessage());
//		}
//		// 見つからなかった場合
//		return true;
//	}
//
//	/**
//	 * 上限値下限値チェックによるメッセージ取得を行う
//	 */
//	public String getHighLawMessage(String koumokuCode,String koumokuName,String hknjaNm,String value){
//
//		ArrayList<Hashtable<String, String>> result = null;
//		String retMessage = "";
//		String kekkaMessage ="";
//
//		try{
//			String[] params = { hknjaNm,koumokuCode };
//
//			String sql = "SELECT KOUMOKU_CD,KAGEN,JYOUGEN " +
//			"FROM T_KENSHINMASTER WHERE HKNJANUM = ? AND KOUMOKU_CD = ? ";
//
//			// 空値の場合はチェックを行わない
//			if (!value.isEmpty() ||
//					!hknjaNm.isEmpty()) {
//				String strKagen = "";
//				String strJyogen = "";
//
//				result = JApplication.kikanDatabase.sendExecuteQuery(sql, params);
//
//				for (int i = 0; i < result.size(); i++) {
//
//					strKagen = result.get(i).get("KAGEN");
//					strJyogen = result.get(i).get("JYOUGEN");
//
//					// 項目コードの結果判定の部分を除いて比較を行う
//					// 範囲内の場合 true
//					if (result.get(i).get("KOUMOKU_CD").equals(koumokuCode)) {
//						if (value.isEmpty())
//							break;
//						if (strKagen.isEmpty() && strJyogen.isEmpty()){
//							break;
//						}else if (strKagen.isEmpty()){
//							if (Double.valueOf(strJyogen) < Double.valueOf(value)){
//								kekkaMessage= strHighMessageValue + "["+ Double.valueOf(strJyogen) + "]" +"を超えています。";
//							}
//						}else if(strJyogen.isEmpty()){
//							if (Double.valueOf(strKagen) > Double.valueOf(value)){
//								kekkaMessage= strLowMessageValue + "["+ Double.valueOf(strKagen) + "]" +"を下回っています。";
//							}
//						}else if (!strKagen.isEmpty() && !strJyogen.isEmpty()){
//							if (Double.valueOf(strKagen) > Double.valueOf(value)){
//								kekkaMessage= strLowMessageValue + "["+ Double.valueOf(strKagen) + "]" +"を下回っています。";
//							}else if(Double.valueOf(strJyogen) < Double.valueOf(value)){
//								kekkaMessage= strHighMessageValue + "["+ Double.valueOf(strJyogen) + "]" +"を超えています。";
//							}
//						}
//					}
//				}
//			}
//		}catch(Exception ex){
//			ex.printStackTrace();
//			logger.error(ex.getMessage());
//		}
//
//		if (!kekkaMessage.isEmpty()){
//			retMessage = "[改行]項目コード[" + koumokuCode
//			 + "] 項目名[" + koumokuName
//			 + "][改行]の結果値[" + validatedData.getKekka()
//			 + "]が" + kekkaMessage;
//		}
//
//		return retMessage;
//	}
//
//	/**
//	 * 登録ボタンを押した際の必須項目等を検証する
//	 */
//	public boolean validateAsRegisterPushed(JKekkaRegisterFrameData data) {
//		if (kenshinPatternNumber == -1 || data.getKensaJissiDate().equals("")
//				|| data.getSeikyuKubun().equals("")) {
//			JErrorMessage.show("M3610", this);
//			return false;
//		}
//
//		data.setValidateAsDataSet();
//		return true;
//	}
//
//
//	/**
//	 * 検査センター名称のコンボボックスが変更された場合に呼ばれる
//	 */
//	public void stateChangedKensaKikanName() {
//
//		String Query = new String(
//				"SELECT DISTINCT KENSA_CENTER_CD,CENTER_NAME FROM T_KENSACENTER_MASTER");
//		ArrayList<Hashtable<String, String>> Result = null;
//		Hashtable<String, String> ResultItem;
//
//		try {
//			Result = JApplication.kikanDatabase.sendExecuteQuery(Query);
//		} catch (SQLException f) {
//			f.printStackTrace();
//		}
//
//		// コンボボックスに表示されている健診機関名称から検査センターコードを引く
//		// 検査センターが未選択の場合でも検査センターコンボボックスで最初の項目が選択されてしまう障害の修正
//		String selectedItem = (String) jComboBox_KensaKikanName
//				.getSelectedItem();
//		if ((jComboBox_KensaKikanName.getItemCount() != 0)
//				&& (selectedItem != null)) {
//			for (int i = 0; i < Result.size(); i++) {
//				ResultItem = Result.get(i);
//				if (ResultItem.get("CENTER_NAME").equals(selectedItem)) {
//					// 以前と同じものが選択されていた場合はリフレッシュしない（編集していたものが消えてしまうため）
//					if (validatedData.getKensaCenterCode().equals(
//							ResultItem.get("KENSA_CENTER_CD"))) {
//						break;
//					} else {
//						validatedData.setKensaCenterCode(ResultItem
//								.get("KENSA_CENTER_CD"));
//						refreshTable(jTextField_Date.getText(), true,false,false);
//						refreshInputControl(jTextField_Date.getText(),false,false);
//						break;
//					}
//				}
//			}
//		}
//	}
//
//	/**
//	 * 健診パターンデータ初期化
//	 */
//	public void stateInitKenshinPatternNumber(String jissiDate) {
//		String kenshinPatternName = null;
//		String query = "SELECT K_P_NO,K_P_NAME FROM T_KENSHIN_P_M WHERE K_P_NO >= 0 ";
//		ArrayList<Hashtable<String, String>> result = null;
//		Hashtable<String, String> ResultItem;
//
//		try {
//			result = JApplication.kikanDatabase.sendExecuteQuery(query);
//		} catch (SQLException f) {
//			f.printStackTrace();
//		}
//		Object selectedItem = jComboBox_Pattern.getSelectedItem();
//		if (selectedItem != null)
//			kenshinPatternName = selectedItem.toString();
//
//		for (int i = 0; i < result.size(); i++) {
//			ResultItem = result.get(i);
//			if (ResultItem.get("K_P_NAME").equals(kenshinPatternName)) {
//				if (kenshinPatternNumber == Integer.valueOf(ResultItem.get("K_P_NO"))) {
//					break;
//				} else {
//					kenshinPatternNumber = Integer.valueOf(ResultItem.get("K_P_NO"));
//					refreshTable(jissiDate, true,false,false);
//					refreshInputControl(jTextField_Date.getText(),false,false);
//					break;
//				}
//			}
//		}
//	}
//
//	/**
//	 * 健診パターンのコンボボックスが変更された場合に呼ばれる blnKekkaは登録時のみ
//	 */
//	public boolean stateChangedKenshinPatternNumber(String uketukeId,String jissiDate,boolean blnKensaRegist,boolean blnKekka) {
//		String kenshinPatternName = null;
//		boolean retPattan = false;
//		boolean blnNoEditPattarn = false;
//
//		String query = "SELECT K_P_NO,K_P_NAME FROM T_KENSHIN_P_M WHERE K_P_NO >= 0 ";
//
//		ArrayList<Hashtable<String, String>> result = null;
//		Hashtable<String, String> ResultItem;
//
//		try {
//			result = JApplication.kikanDatabase.sendExecuteQuery(query);
//		} catch (SQLException f) {
//			f.printStackTrace();
//		}
//
//		Object selectedItem = jComboBox_Pattern.getSelectedItem();
//		if (selectedItem != null)
//			kenshinPatternName = selectedItem.toString();
//
//		// 登録時、変更前の健診パターンを使用し、
//		// コンボボックスのパターン変更時健診パターンのバックアップする
//		if (blnKensaRegist){
//			// edit ver2 s.inoue 2009/08/25
//			if (prekenshinPatternNumber == -1 || blnKekka){
//				blnNoEditPattarn = true;
//			}
//			kenshinPatternNumber = prekenshinPatternNumber;
//		}else{
//			prekenshinPatternNumber = kenshinPatternNumber;
//		}
//
//		for (int i = 0; i < result.size(); i++) {
//			ResultItem = result.get(i);
//			if (ResultItem.get("K_P_NAME").equals(kenshinPatternName)) {
//
//				// edit 20081201 s.inoue デフォルト値の場合処理をパターン削除行わない
//				if (kenshinPatternNumber == Integer.valueOf(ResultItem.get("K_P_NO"))) {
//					refreshTable(jissiDate, true,false,false);
//					refreshInputControl(jTextField_Date.getText(),false,false);
//					break;
//				} else {
//
//					// add s.inoue 20081125 新規作成のときはチェック処理は行わない
//					Long resultCnt = 0L;
//					try {
//						resultCnt = kensakekaTokuteiDao.selectByCountPrimaryKey(Long.valueOf(
//								uketukeId),Integer.valueOf(jissiDate));
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//
//					if (resultCnt <= 0) {
//						kenshinPatternNumber = Integer.valueOf(ResultItem.get("K_P_NO"));
//						refreshTable(jissiDate, true,false,false);
//						refreshInputControl(jTextField_Date.getText(),false,false);
//						break;
//					}
//
//					// edit s.inoue 20081128 コンボ選択時、登録時でメッセージを変更する
//					RETURN_VALUE retValue = null;
//
//					if (blnKensaRegist){ //登録実行時
//						kenshinPatternNumber = Integer.valueOf(ResultItem.get("K_P_NO"));
//						// edit ver2 s.inoue 2009/08/25
//						// 削除実行
//						if (!blnNoEditPattarn){
//							if(pattarnKensaKoumoku()){
//								refreshTable(jissiDate, true,false,true);
//								refreshInputControl(jTextField_Date.getText(),false,false);
//								retPattan= true;
//							}else{
//								refreshTable(jissiDate, true,false,false);
//								refreshInputControl(jTextField_Date.getText(),false,false);
//								retPattan= false;
//							}
//						}
//					}else{ //コンボ選択時
//						retValue = JErrorMessage.show("M3638", this);
//
//						if (retValue == RETURN_VALUE.YES) {
//							kenshinPatternNumber = Integer.valueOf(ResultItem.get("K_P_NO"));
//							refreshTable(jissiDate, true,false,true);
//							refreshInputControl(jTextField_Date.getText(),false,false);
//							retPattan= true;
//						}else if(retValue == RETURN_VALUE.NO) {
//							jComboBox_Pattern.setSelectedIndex(kenshinPatternNumber - 1);
//						}
//					}
//
//					break;
//				}
//			}
//		}
//		return retPattan;
//	}
//
//	/**
//	 * アクションの更新
//	 */
//	private void refreshCellAction(){
//		// edit s.inoue 2009/12/24
//		textAreaField = new ExtendedTextField("", 0, ImeMode.IME_ZENKAKU,false);
//		TableColumn column = this.model.getColumnModel().getColumn(COLUMN_IDX_KEKKA_STR);
//
////edit s.inoue 2009/12/24
//		column.setCellEditor(
//				new PopupWindowShowCellEditor(
//						this,
//						textAreaField,
//						COLUMN_IDX_KEKKA_STR));
//
////改行複数モードだが、enter処理に困惑
////		column.setCellEditor(
////		new PopupWindowShowTextAreaCellEditor(
////				this,
////				textAreaField,
////				COLUMN_IDX_KEKKA_STR));
//
//
////		textField.addMouseListener(new MouseListener(){
////			// add s.inoue 2009/11/11
////			public void mousePressed(MouseEvent e) {
////				mousePopup(e);
////			}
////			public void mouseReleased(MouseEvent e) {
////				mousePopup(e);
////			}
////			// add s.inoue 2009/11/11
////			private void mousePopup(MouseEvent e) {
////				if (e.isPopupTrigger()) {
////					// ポップアップメニューを表示する
////					JComponent c = (JComponent)e.getSource();
////					jKekkaMojiretsuTextArea.showPopup(c, e.getX(), e.getY());
////					e.consume();
////				}
////			}
////			@Override
////			public void mouseClicked(MouseEvent e) {
////			}
////			@Override
////			public void mouseEntered(MouseEvent e) {
////			}
////			@Override
////			public void mouseExited(MouseEvent e) {
////			}
////		});
//
//		// edit s.inoue 2009/12/22
//		// COLUMN_IDX_KEKKA_STR→12
//		textAreaField.addKeyListener(new KeyAdapter(){
//			IKekkaRegisterInputDialog dialog =
//				JKekkaRegisterInputDialogFactory.createDialog(
//						JKekkaRegisterFrameCtrl.this,COLUMN_IDX_KEKKA_STR);
//
//			@Override
//			public void keyPressed(KeyEvent keyEvent) {
//
//				if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
//
//					// Shiftキーが一緒に押されたか?
//					int modfiersKey = keyEvent.getModifiersEx();
//
//					if ((modfiersKey & InputEvent.CTRL_DOWN_MASK) == 0){
//						return;
//					}else{
//						// 健診結果データ入力画面のテーブルから現在選択されているセルのデータを取得
//						int columnIndex = model.getSelectedColumn();
//						int rowIndex = model.getSelectedRow();
//						String kekkaMojiretsu = (String) model.getData(rowIndex,columnIndex);
//
//						// "結果(文字列)"入力ダイアログのテキストエリアに選択セルデータをセット
//						dialog.setText(kekkaMojiretsu);
//						// "結果(文字列)"入力ダイアログ表示
//						dialog.setVisible(true);
//						// edit s.inoue 2009/12/06
//						// if (dialog.getStatus() == RETURN_VALUE.OK){
//						if (dialog.getStatus() == RETURN_VALUE.YES){
//							// "結果(文字列)"入力ダイアログから入力された
//							// 文字列を選択セルにセット
//							textAreaField.setText(dialog.getText());
//						}
//					}
//				}
//			}
//		});
//
//	}
//
//	/* フレーム上部のコンボボックスに関して既存データの読み込みを行う	*/
//	public void refreshInputControl(String jissiDate,boolean blnClearData, boolean preview) {
//
//		// 受付ID,年度の追加対応
//		// 検査結果データ特定テーブルから指定受付ID,検査実施年月日のレコードを1件取得する
//		TKensakekaTokutei kensaTokutei = null;
//		try {
//
//			String uketuke_id = "";
//			if (isCopy && !preview){
//				uketuke_id = validatedData.getUketukePre_id();
//			}else{
//				uketuke_id = validatedData.getUketuke_id();
//			}
//
//			kensaTokutei = kensakekaTokuteiDao.selectByPrimaryKey(
//					new Long(uketuke_id),
//					new Integer(jissiDate));
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error(e.getMessage());
//		}
//
//		if (kensaTokutei != null) {
//			// 総合コメントに関して表示を行う
//			jEditorPane_Comment.setText(kensaTokutei.getKOMENTO());
//
//			// 請求区分に関して表示を行う
//			Integer seikyuKbn = kensaTokutei.getSEIKYU_KBN();
//			if (seikyuKbn == null) {
//				// 必須項目のため未入力の場合は一番上のものを表示させておく
//				jComboBox_SeikyuKubun.setSelectedIndex(0);
//			} else {
//				// 請求区分値に合致する項目を選択状態にする
//				// 1: 基本的な健診
//				// 2: 基本的な健診＋詳細な健診の場合
//				// 3: 基本的な健診＋追加健診項目健診
//				// 4: 基本的な健診＋詳細な健診+追加健診項目
//				// 5: 人間ドック
//				jComboBox_SeikyuKubun.setSelectedIndex(seikyuKbn.intValue() - 1);
//			}
//		}
//
//		if (blnClearData){
//			jEditorPane_Comment.setText("");
//		}
//
//		/* メタボリックシンドローム判定の結果を表示する。 */
//		if (blnClearData){
//			jComboBox_MetaboHantei.setSelectedIndex(0);
//		}else{
//			this.showMetablicResult(preview);
//		}
//
//		/* 階層化の結果を表示する。 */
//		if (blnClearData){
//			jComboBox_HokenSidouLevel.setSelectedIndex(0);
//		}else{
//			this.showKaisoukaResult(preview);
//		}
//	}
//
//	/**
//	 * テーブルの更新
//	 */
//	public Object[][] refreshTable(String jissiDate, boolean saveColumnWidth, boolean blnClearData, boolean preview) {
////del s.inoue 2009/11/20
////		if (saveColumnWidth) {
////			/* 列幅を保存しておく */
////			this.saveCurrentColumnsWidth();
////		}
////		this.table.clearAllRow();
//
//		comboData = new Vector<Hashtable<String, String>>();
//
//		// 最低限必要なデータを格納
//		validatedData.setKensaJissiDate(jissiDate);
//		validatedData.setHihokenjyaCode(jTextField_HokenjyaCode.getText());
//		validatedData.setHihokenjyaNumber(jTextField_HokenjyaNumber.getText());
//
//		String query = this.createCellDataSql(preview);
//		try {
//			tableResult = JApplication.kikanDatabase.sendExecuteQuery(query);
//		} catch (SQLException e) {
//			e.printStackTrace();
//			logger.error(e.getMessage());
//		}
//
//		/* セルの色を更新する。 */
//		// del s.inoue 2009/11/20
////		this.refreshTableCellColor();
//
//		// del s.inoue 2009/11/20
////		/* HL7、判定 */
////		this.table.setCellComboBoxEditor(hlComboEditor, COLUMN_IDX_HL);
////		this.table.setCellComboBoxEditor(hanteiComboEditor, COLUMN_IDX_HANTEI);
////
////		// 結果(文字列)・コメント欄にてEnterキー押下時に入力用ダイアログ表示用
////		TableColumn column = this.table.getColumnModel().getColumn(COLUMN_IDX_KEKKA_STR);
////
////		// 毎回newで呼び出し
////		refreshCellAction();
////
////		// 実施区分
////		this.table.setCellComboBoxEditor(jisiComboEditor, COLUMN_IDX_JISIKBN);
////
////		// 表中コメント欄
////		column = this.table.getColumnModel().getColumn(COLUMN_IDX_KOMENTO);
////		column.setCellEditor(new PopupWindowShowCellEditor(
////				this,new JTextField(), COLUMN_IDX_KOMENTO));
////
////		// 結果(数値)欄の入力値を小数点第2位以下四捨五入して表示
////		column = this.table.getColumnModel().getColumn(COLUMN_IDX_KEKKA_NUM);
////		column.setCellEditor(new InputDigitHalfUpCellEditor(new JTextField()));
//
//		/* 身長、体重、BMIのセル位置をクリアする。 */
//		BMICellPosition = null;
//		heightCellPosition = null;
//		weightCellPosition = null;
//
//		rowcolumn = new Object[tableResult.size()][15];
//
//		for (int i = 0; i < tableResult.size(); i++) {
//			Vector<String> insertItem = new Vector<String>();
//			Hashtable<String, String> databaseItem = tableResult.get(i);
//
//			insertItem.add(databaseItem.get(COLUMN_NAME_KOUMOKUCD));
//			insertItem.add(databaseItem.get(COLUMN_NAME_KOUMOKUNAME));
//			insertItem.add(databaseItem.get(COLUMN_NAME_KENSA_HOUHOU));
//
//			rowcolumn[i][COLUMN_IDX_KOUMOKUCD] = databaseItem.get(COLUMN_NAME_KOUMOKUCD);
//			rowcolumn[i][COLUMN_IDX_CODE_NAME] = databaseItem.get(COLUMN_NAME_KOUMOKUNAME);
//			rowcolumn[i][COLUMN_IDX_CODE_KENSA_HOUHOU] = databaseItem.get(COLUMN_NAME_KENSA_HOUHOU);
//
//			// 既存のデータの読み込みを行う
//			switch (Integer.valueOf(databaseItem.get(COLUMN_NAME_DATA_TYPE))) {
//			case 1:
//
//				// 数値の場合
//				// add ver2 s.inoue 2009/06/02
//				if (blnClearData){
//					insertItem.add("");
//					rowcolumn[i][COLUMN_IDX_KEKKA_NUM] = "";
//				}else{
//					insertItem.add(JValidate.validateKensaKekkaDecimal(databaseItem.get(COLUMN_NAME_KEKA_TI),
//							databaseItem.get(COLUMN_NAME_MAX_BYTE_LENGTH)));
//					rowcolumn[i][COLUMN_IDX_KEKKA_NUM] = JValidate.validateKensaKekkaDecimal(databaseItem.get(COLUMN_NAME_KEKA_TI),
//							databaseItem.get(COLUMN_NAME_MAX_BYTE_LENGTH));
//				}
//				insertItem.add(null);
//				insertItem.add("");
//				rowcolumn[i][COLUMN_IDX_KEKKA_CODE] = null;
//				rowcolumn[i][COLUMN_IDX_KEKKA_STR] = "";
//
//				// ＢＭＩ値の場合は編集できないようにする
//				if (databaseItem.get(COLUMN_NAME_KOUMOKUCD).equals(CODE_BMI)) {
//					forbitCells.add(new JSimpleTableCellPosition(i, COLUMN_IDX_KEKKA_NUM));
//
//					BMICellPosition = new JSimpleTableCellPosition(i, COLUMN_IDX_KEKKA_NUM);
//					BMIFormat = new String(databaseItem.get(COLUMN_NAME_MAX_BYTE_LENGTH));
//				}
//
//				// 身長及び体重のデータはＢＭＩ計算に必要なためセルの位置を記憶しておく
//				// 身長
//				if (databaseItem.get(COLUMN_NAME_KOUMOKUCD).equals(CODE_SHINCHOU)) {
//					heightCellPosition = new JSimpleTableCellPosition(i, COLUMN_IDX_KEKKA_NUM);
//				}
//
//				// 体重
//				if (databaseItem.get(COLUMN_NAME_KOUMOKUCD).equals(CODE_TAIJU)) {
//					weightCellPosition = new JSimpleTableCellPosition(i, COLUMN_IDX_KEKKA_NUM);
//				}
//				break;
//
//			case 2:
//				/* コードの場合 */
//				insertItem.add("");
//				rowcolumn[i][COLUMN_IDX_KEKKA_NUM] = "";
//
//				ArrayList<Hashtable<String, String>> codeResult = null;
//				Hashtable<String, String> codeResultItem;
//				query = new String("SELECT KOUMOKU_CD, CODE_NUM, CODE_NAME FROM T_DATA_TYPE_CODE WHERE KOUMOKU_CD = "
//						+ JQueryConvert.queryConvert(databaseItem.get(COLUMN_NAME_KOUMOKUCD)));
//				try {
//					codeResult = JApplication.kikanDatabase.sendExecuteQuery(query);
//				} catch (SQLException e) {
//					e.printStackTrace();
//					logger.error(e.getMessage());
//				}
//				// add s.inoue 2010/02/19
//				kekkaCombo = new ExtendedComboBox();
//
//				kekkaComboEditor = new JComboCellEditor(kekkaCombo);
//				kekkaComboEditor.delItems(i);
//
//				// 未入力も選べるようにする
//				kekkaComboEditor.addItem(i, "");
//				for (int j = 0; j < codeResult.size(); j++) {
//					codeResultItem = codeResult.get(j);
//					kekkaComboEditor.addItem(i, codeResultItem.get(COLUMN_NAME_CODE_NAME));
//
//					// 既存のDBの項目をコンボボックスに表示させる
//					System.out.println(databaseItem.get(COLUMN_NAME_KEKA_TI) +
//							codeResultItem.get(COLUMN_NAME_CODE_NUM));
////del s.inoue 2010/02/19
//					if (databaseItem.get(COLUMN_NAME_KEKA_TI).equals(
//							codeResultItem.get(COLUMN_NAME_CODE_NUM))) {
//						kekkaCmbdataSet = new Hashtable<String, String>();
//						if (blnClearData){
//							kekkaCmbdataSet.put("DATA", "");
//						}else{
//							// kekkaCmbdataSet.put("DATA", codeResultItem.get(COLUMN_NAME_CODE_NAME));
//							kekkaCmbdataSet.put("DATA", codeResultItem.get(COLUMN_NAME_CODE_NAME) + "あ" + i);
//							kekkaCmbdataSet.put("DATA", codeResultItem.get(COLUMN_NAME_CODE_NAME) + "い" + i);
//						}
//
//						kekkaCmbdataSet.put("ROW", String.valueOf(i));
//						kekkaCmbdataSet.put("COLUMN", String.valueOf(COLUMN_IDX_KEKKA_CODE));
//
//						comboData.add(kekkaCmbdataSet);
//					}
//				}
//
//				insertItem.add(null);
//				insertItem.add("");
//
//				rowcolumn[i][COLUMN_IDX_KEKKA_CODE] = null;
//				rowcolumn[i][COLUMN_IDX_KEKKA_STR] = "";
//				break;
//
//			case 3:
//				/* 文字列の場合 */
//				insertItem.add("");
//				insertItem.add(null);
//				rowcolumn[i][COLUMN_IDX_KEKKA_NUM] = "";
//				rowcolumn[i][COLUMN_IDX_KEKKA_CODE] = null;
//
//				if (blnClearData){
//					insertItem.add("");
//					rowcolumn[i][COLUMN_IDX_KEKKA_STR] = "";
//				}else{
//					insertItem.add(JValidate.validateKensaKekkaText(databaseItem
//							.get(COLUMN_NAME_KEKA_TI), databaseItem.get(COLUMN_NAME_MAX_BYTE_LENGTH)));
//					rowcolumn[i][COLUMN_IDX_KEKKA_STR] = JValidate.validateKensaKekkaText(databaseItem
//							.get(COLUMN_NAME_KEKA_TI), databaseItem.get(COLUMN_NAME_MAX_BYTE_LENGTH));
//				}
//
//				break;
//			}
//
//			/*
//			 * 既存の実施区分をコンボボックスにコード名として表示する
//			 */
//			insertItem.add(null);
//			rowcolumn[i][COLUMN_IDX_KEKKA_STR] = null;
////del s.inoue 2009/11/23
////			jisiComboEditor.delItems(i);
////			for (int k = 0; k < JApplication.jishiKBNTable.length; k++) {
////				jisiComboEditor.addItem(i, JApplication.jishiKBNTable[k]);
////			}
//			if (databaseItem.get(COLUMN_NAME_JISI_KBN).isEmpty()) {
//				Hashtable<String, String> dataSet = new Hashtable<String, String>();
//				dataSet.put("DATA", JApplication.jishiKBNTable[1]);
//				dataSet.put("ROW", String.valueOf(i));
//				dataSet.put("COLUMN", String.valueOf(COLUMN_IDX_JISIKBN));
//				comboData.add(dataSet);
//			} else {
//				Hashtable<String, String> dataSet = new Hashtable<String, String>();
//				if (blnClearData){
//					dataSet.put("DATA", JApplication.jishiKBNTable[1]);
//				}else{
//					dataSet.put("DATA", JApplication.jishiKBNTable[Integer.valueOf(databaseItem.get(COLUMN_NAME_JISI_KBN))]);
//				}
//				dataSet.put("ROW", String.valueOf(i));
//				dataSet.put("COLUMN", String.valueOf(COLUMN_IDX_JISIKBN));
//				comboData.add(dataSet);
//			}
//
//			/* 基準値下限(男性),基準値上限(男性),基準値下限(女性),基準値上限(女性)を */
//			/* テーブルに表示 */
//			// 既存のデータの読み込みを行う
//			String LimitFormat = databaseItem.get(COLUMN_NAME_MAX_BYTE_LENGTH);
//			String limitValeDsKagen = databaseItem.get(COLUMN_NAME_DS_KAGEN);
//			String limitValeDsJyougen = databaseItem.get(COLUMN_NAME_DS_JYOUGEN);
//			String limitValeJsKagen = databaseItem.get(COLUMN_NAME_JS_KAGEN);
//			String limitValeJsJyougen = databaseItem.get(COLUMN_NAME_JS_JYOUGEN);
//
//			// 数値項目のみ基準値を設定する
//			switch (Integer.valueOf(databaseItem.get(COLUMN_NAME_DATA_TYPE))) {
//			case 1:
//				if (!limitValeDsKagen.equals("")){
//					insertItem.add(JValidate.DecimalFormatValue(limitValeDsKagen,LimitFormat));
//					rowcolumn[i][COLUMN_IDX_DS_KAGEN] = JValidate.DecimalFormatValue(limitValeDsKagen,LimitFormat);
//				}else{
//					insertItem.add("");
//					rowcolumn[i][COLUMN_IDX_DS_KAGEN] = "";
//				}
//				if (!limitValeDsJyougen.equals("")){
//					insertItem.add(JValidate.DecimalFormatValue(limitValeDsJyougen,LimitFormat));
//					rowcolumn[i][COLUMN_IDX_DS_JYOUGEN] = JValidate.DecimalFormatValue(limitValeDsJyougen,LimitFormat);
//				}else{
//					insertItem.add("");
//					rowcolumn[i][COLUMN_IDX_DS_JYOUGEN] = "";
//				}
//				if (!limitValeJsKagen.equals("")){
//					insertItem.add(JValidate.DecimalFormatValue(limitValeJsKagen,LimitFormat));
//					rowcolumn[i][COLUMN_IDX_JS_KAGEN] = JValidate.DecimalFormatValue(limitValeJsKagen,LimitFormat);
//				}else{
//					insertItem.add("");
//					rowcolumn[i][COLUMN_IDX_JS_KAGEN] = "";
//				}
//				if (!limitValeJsJyougen.equals("")){
//					insertItem.add(JValidate.DecimalFormatValue(limitValeJsJyougen,LimitFormat));
//					rowcolumn[i][COLUMN_IDX_JS_JYOUGEN] = JValidate.DecimalFormatValue(limitValeJsJyougen,LimitFormat);
//				}else{
//					insertItem.add("");
//					rowcolumn[i][COLUMN_IDX_JS_JYOUGEN] = "";
//				}
//				break;
//			case 2:
//			case 3:
//				// edit s.inoue 2009/09/29 1項目多い
//				insertItem.add("");
//				insertItem.add("");
//				insertItem.add("");
//				insertItem.add("");
//				rowcolumn[i][COLUMN_IDX_DS_KAGEN] = "";
//				rowcolumn[i][COLUMN_IDX_DS_JYOUGEN] = "";
//				rowcolumn[i][COLUMN_IDX_JS_KAGEN] = "";
//				rowcolumn[i][COLUMN_IDX_JS_JYOUGEN] = "";
//				break;
//			}
//
//			/*
//			 * 既存のHLコード値をコンボボックスにコード名として表示する
//			 */
//			insertItem.add(null);
//			rowcolumn[i][COLUMN_IDX_HL] = null;
//			// del s.inoue 2009/11/23
////			hlComboEditor.delItems(i);
////			for (int k = 0; k < hlCodeTable.length; k++) {
////				hlComboEditor.addItem(i, hlCodeTable[k]);
////			}
//			if (databaseItem.get(COLUMN_NAME_H_L).isEmpty()) {
//				Hashtable<String, String> dataSet = new Hashtable<String, String>();
//				dataSet.put("DATA", hlCodeTable[0]);
//				dataSet.put("ROW", String.valueOf(i));
//				/* 基準値下限(男性),基準値上限(男性),基準値下限(女性),基準値上限(女性)を */
//				/* テーブルに表示 */
//				dataSet.put("COLUMN", String.valueOf(COLUMN_IDX_HL));
//				comboData.add(dataSet);
//			} else {
//				Hashtable<String, String> dataSet = new Hashtable<String, String>();
//				// add ver2 s.inoue 2009/06/02
//				if (blnClearData){
//					dataSet.put("DATA", "");
//				}else{
//					dataSet.put("DATA", hlCodeTable[Integer.valueOf(databaseItem.get(COLUMN_NAME_H_L))]);
//				}
//				dataSet.put("ROW", String.valueOf(i));
//				/* 基準値下限(男性),基準値上限(男性),基準値下限(女性),基準値上限(女性)を */
//				/* テーブルに表示 */
//				dataSet.put("COLUMN", String.valueOf(COLUMN_IDX_HL));
//				comboData.add(dataSet);
//			}
//
//			/*
//			 * 既存の判定区分のコード値をコンボボックスにコード名として表示する
//			 */
//			insertItem.add(null);
//			rowcolumn[i][COLUMN_IDX_HANTEI] = null;
//			// del s.inoue 2009/11/23
////			hanteiComboEditor.delItems(i);
////			for (int k = 0; k < hanteiCodeTable.length; k++) {
////				hanteiComboEditor.addItem(i, hanteiCodeTable[k]);
////			}
//			if (databaseItem.get(COLUMN_NAME_HANTEI).isEmpty()) {
//				Hashtable<String, String> dataSet = new Hashtable<String, String>();
//				dataSet.put("DATA", hanteiCodeTable[0]);
//				dataSet.put("ROW", String.valueOf(i));
//				dataSet.put("COLUMN", String.valueOf(COLUMN_IDX_HANTEI));
//				comboData.add(dataSet);
//			} else {
//				Hashtable<String, String> dataSet = new Hashtable<String, String>();
//
//				if (blnClearData){
//					dataSet.put("DATA", "");
//				}else{
//					dataSet.put("DATA", hanteiCodeTable[Integer.valueOf(databaseItem.get(COLUMN_NAME_HANTEI))]);
//				}
//				dataSet.put("ROW", String.valueOf(i));
//				dataSet.put("COLUMN", String.valueOf(COLUMN_IDX_HANTEI));
//				comboData.add(dataSet);
//			}
//
//			/* コメント */
//			if (blnClearData){
//				insertItem.add("");
//				rowcolumn[i][COLUMN_IDX_KOMENTO] = "";
//			}else{
//				insertItem.add(databaseItem.get(COLUMN_NAME_KOMENTO));
//				rowcolumn[i][COLUMN_IDX_KOMENTO] = databaseItem.get(COLUMN_NAME_KOMENTO);
//			}
//
//
//			// edit s.inoue 2009/11/22
//			rowcolumn[i][COLUMN_IDX_HISUFLG] = databaseItem.get(COLUMN_NAME_HISU_FLG);
////del s.inoue 2009/11/20
////			/* 入力上限、下限値 */
////			String maxValeKagen = databaseItem.get(COLUMN_NAME_KAGEN);
////			String maxValeJyougen = databaseItem.get(COLUMN_NAME_JYOUGEN);
////
////			if (!maxValeKagen.equals("")){
////				insertItem.add(JValidate.DecimalFormatValue(maxValeKagen,LimitFormat));
////			}else{
////				insertItem.add("");
////			}
////
////			if (!maxValeJyougen.equals("")){
////				if (databaseItem.get(COLUMN_NAME_KOUMOKUCD).equals("2A040000001930102")){
////					insertItem.add("99.9");
////				}else{
////					insertItem.add(JValidate.DecimalFormatValue(maxValeJyougen,LimitFormat));
////				}
////			}else{
////				insertItem.add("");
////			}
//
//			// del s.inoue 2010/02/18
//			// 一行ごとにデータを挿入していく
//			// this.model.addData(insertItem);
//		}
//
////del s.inoue 2010/02/19
////		for (int i = 0; i < comboData.size(); i++) {
////			this.model.setData(comboData.get(i).get("DATA"), Integer
////					.valueOf(comboData.get(i).get("ROW")), Integer
////					.valueOf(comboData.get(i).get("COLUMN")));
////		}
//
//		for (int i = 0; i < comboData.size(); i++) {
//			rowcolumn[Integer.valueOf(comboData.get(i).get("ROW"))]
//			          [Integer.valueOf(comboData.get(i).get("COLUMN"))] = comboData.get(i).get("DATA");
//		}
//
//		return rowcolumn;
//		// move s.inoue 2009/11/20
//		/* 列の幅を初期化または復元する。 */
////		this.initializeColumnWidth();
////		this.table.refreshTable();
////		this.setTableFocusMap();
//	}
//
//	/* 列幅保存処理 */
//	private void saveCurrentColumnsWidth() {
//		/* 列幅を保存しておく */
//		int columnCount = this.model.getColumnCount();
//
//		int[] columnWidths = new int[columnCount];
//		for (int i = 0; i < columnCount; i++) {
//			int width = this.model.getColumnModel().getColumn(i).getWidth();
//			columnWidths[i] = width;
//		}
//
//		this.model.setPreferedColumnWidths(columnWidths);
//	}
//
//	/**
//	 * セルの色に最新の状態を反映する。
//	 */
//	private void refreshTableCellColor() {
//		cellColors.clear();
//		// del s.inoue 2009/11/20
//		// forbitCells.clear();
//
//		// テーブルの仕様上、セルの禁止設定のみを先に行う
//		for (int i = 0; i < tableResult.size(); i++) {
//			Hashtable<String, String> databaseItem = tableResult.get(i);
//
//			int dataType = Integer.valueOf(databaseItem.get(COLUMN_NAME_DATA_TYPE));
//			int hisuFlg = Integer.valueOf(databaseItem.get(COLUMN_NAME_HISU_FLG));
//
//			switch (dataType)
//			{
//			case 1:
//				/* 数値の場合 */
////del s.inoue
////				forbitCells.add(new JSimpleTableCellPosition(i, COLUMN_IDX_KOUMOKUCD));
////				forbitCells.add(new JSimpleTableCellPosition(i, COLUMN_IDX_CODE_NAME));
////				forbitCells.add(new JSimpleTableCellPosition(i, COLUMN_IDX_CODE_KENSA_HOUHOU));
//
//				if (hisuFlg == HISU_FLG_HISU) {
//					cellColors.add(new JSimpleTableCellRendererData(COLOR_HISU,new JSimpleTableCellPosition(i, COLUMN_IDX_KEKKA_NUM)));
//				}else{
//					cellColors.add(new JSimpleTableCellRendererData(COLOR_ABLE,new JSimpleTableCellPosition(i, COLUMN_IDX_KEKKA_NUM)));
//				}
//
//				cellColors.add(new JSimpleTableCellRendererData(COLOR_DISABLE,new JSimpleTableCellPosition(i, COLUMN_IDX_KEKKA_CODE)));
//				cellColors.add(new JSimpleTableCellRendererData(COLOR_DISABLE,new JSimpleTableCellPosition(i, COLUMN_IDX_KEKKA_STR)));
////del s.inoue
////				forbitCells.add(new JSimpleTableCellPosition(i, COLUMN_IDX_KEKKA_CODE));
////				forbitCells.add(new JSimpleTableCellPosition(i, COLUMN_IDX_KEKKA_STR));
//
//				cellColors.add(new JSimpleTableCellRendererData(COLOR_HISU,new JSimpleTableCellPosition(i, COLUMN_IDX_JISIKBN)));
////del s.inoue
////				forbitCells.add(new JSimpleTableCellPosition(i, COLUMN_IDX_DS_KAGEN));
////				forbitCells.add(new JSimpleTableCellPosition(i, COLUMN_IDX_DS_JYOUGEN));
////				forbitCells.add(new JSimpleTableCellPosition(i, COLUMN_IDX_JS_KAGEN));
////				forbitCells.add(new JSimpleTableCellPosition(i, COLUMN_IDX_JS_JYOUGEN));
////				forbitCells.add(new JSimpleTableCellPosition(i, COLUMN_IDX_HL));
////				forbitCells.add(new JSimpleTableCellPosition(i, COLUMN_IDX_HANTEI));
//
//				cellColors.add(new JSimpleTableCellRendererData(COLOR_ABLE,new JSimpleTableCellPosition(i, COLUMN_IDX_KOMENTO)));
//				break;
//
//			case 2:
//				/* コードの場合 */
////del s.inoue
////				forbitCells.add(new JSimpleTableCellPosition(i, COLUMN_IDX_KOUMOKUCD));
////				forbitCells.add(new JSimpleTableCellPosition(i, COLUMN_IDX_CODE_NAME));
////				forbitCells.add(new JSimpleTableCellPosition(i, COLUMN_IDX_CODE_KENSA_HOUHOU));
////				forbitCells.add(new JSimpleTableCellPosition(i, COLUMN_IDX_KEKKA_NUM));
//
//				cellColors.add(new JSimpleTableCellRendererData(COLOR_DISABLE,
//						new JSimpleTableCellPosition(i, COLUMN_IDX_KEKKA_NUM)));
//
//				if (hisuFlg == HISU_FLG_HISU) {
//					cellColors.add(new JSimpleTableCellRendererData(COLOR_HISU,
//							new JSimpleTableCellPosition(i, COLUMN_IDX_KEKKA_CODE)));
//				}else{
//					cellColors.add(new JSimpleTableCellRendererData(COLOR_ABLE,new JSimpleTableCellPosition(i, COLUMN_IDX_KEKKA_CODE)));
//				}
//
//				cellColors.add(new JSimpleTableCellRendererData(COLOR_DISABLE,new JSimpleTableCellPosition(i, COLUMN_IDX_KEKKA_STR)));
//
//				forbitCells.add(new JSimpleTableCellPosition(i, COLUMN_IDX_KEKKA_STR));
//
//				cellColors.add(new JSimpleTableCellRendererData(COLOR_HISU,new JSimpleTableCellPosition(i, COLUMN_IDX_JISIKBN)));
//
////del s.inoue
////				forbitCells.add(new JSimpleTableCellPosition(i, COLUMN_IDX_DS_KAGEN));
////				forbitCells.add(new JSimpleTableCellPosition(i, COLUMN_IDX_DS_JYOUGEN));
////				forbitCells.add(new JSimpleTableCellPosition(i, COLUMN_IDX_JS_KAGEN));
////				forbitCells.add(new JSimpleTableCellPosition(i, COLUMN_IDX_JS_JYOUGEN));
////				forbitCells.add(new JSimpleTableCellPosition(i, COLUMN_IDX_HL));
////				forbitCells.add(new JSimpleTableCellPosition(i, COLUMN_IDX_HANTEI));
//
//				cellColors.add(new JSimpleTableCellRendererData(COLOR_ABLE,new JSimpleTableCellPosition(i, COLUMN_IDX_KOMENTO)));
//				break;
//
//			case 3:
//				/* 文字列の場合 */
////del s.inoue
////				forbitCells.add(new JSimpleTableCellPosition(i, COLUMN_IDX_KOUMOKUCD));
////				forbitCells.add(new JSimpleTableCellPosition(i, COLUMN_IDX_CODE_NAME));
////				forbitCells.add(new JSimpleTableCellPosition(i, COLUMN_IDX_CODE_KENSA_HOUHOU));
////				forbitCells.add(new JSimpleTableCellPosition(i, COLUMN_IDX_KEKKA_NUM));
////				forbitCells.add(new JSimpleTableCellPosition(i, COLUMN_IDX_KEKKA_CODE));
//
//				cellColors.add(new JSimpleTableCellRendererData(COLOR_DISABLE,
//						new JSimpleTableCellPosition(i, COLUMN_IDX_KEKKA_NUM)));
//				cellColors.add(new JSimpleTableCellRendererData(COLOR_DISABLE,
//						new JSimpleTableCellPosition(i, COLUMN_IDX_KEKKA_CODE)));
//
//				if (hisuFlg == HISU_FLG_HISU) {
//					cellColors.add(new JSimpleTableCellRendererData(COLOR_HISU,
//							new JSimpleTableCellPosition(i, COLUMN_IDX_KEKKA_STR)));
//				}else{
//					cellColors.add(new JSimpleTableCellRendererData(COLOR_ABLE,new JSimpleTableCellPosition(i, COLUMN_IDX_KEKKA_STR)));
//				}
//
//				cellColors.add(new JSimpleTableCellRendererData(COLOR_HISU,
//						new JSimpleTableCellPosition(i, COLUMN_IDX_JISIKBN)));
////del s.inoue
////				forbitCells.add(new JSimpleTableCellPosition(i, COLUMN_IDX_DS_KAGEN));
////				forbitCells.add(new JSimpleTableCellPosition(i, COLUMN_IDX_DS_JYOUGEN));
////				forbitCells.add(new JSimpleTableCellPosition(i, COLUMN_IDX_JS_KAGEN));
////				forbitCells.add(new JSimpleTableCellPosition(i, COLUMN_IDX_JS_JYOUGEN));
////				forbitCells.add(new JSimpleTableCellPosition(i, COLUMN_IDX_HL));
////				forbitCells.add(new JSimpleTableCellPosition(i, COLUMN_IDX_HANTEI));
//
//				cellColors.add(new JSimpleTableCellRendererData(COLOR_ABLE,new JSimpleTableCellPosition(i, COLUMN_IDX_KOMENTO)));
//				break;
//			}
//		}
//
//		/*
//		 * セルの編集禁止を設定した後はコンボボックスの設定がクリアされるため再度設定を行いその後データをセットする
//		 * 順番を入れ替えると正常に動作しない可能性があるため注意
//		 */
////		this.table.setCellEditForbid(forbitCells);
//		// del s.inoue 2010/02/19
////		this.model.setCellComboBoxEditor(kekkaComboEditor, COLUMN_IDX_KEKKA_CODE);
//
////		this.table.setCellComboBoxEditor(jisiComboEditor, COLUMN_IDX_JISIKBN);
//
////		rowEditor = new EachRowEditor(table);
////		rowEditor.setEditorAt(3, new DefaultCellEditor(kekkaCombo));
////		table.getColumn("結果（数値）[入力]").setCellEditor(kekkaComboEditor);
//
//	}
//
//	/* 階層化データ表示 */
//	private void showKaisoukaResult(boolean preview) {
//		String query;
//		/* 階層化の結果を表示する。 */
//		List<Hashtable<String, String>> textFieldResult = new ArrayList<Hashtable<String, String>>();
//		query = this.createKaisoukaSql(preview);
//		try {
//			textFieldResult = JApplication.kikanDatabase.sendExecuteQuery(query);
//		} catch (SQLException f) {
//			f.printStackTrace();
//		}
//
//		if (textFieldResult.size() != 0) {
//			if (textFieldResult.get(0).get(COLUMN_NAME_KEKA_TI).equals("")) {
//				jComboBox_HokenSidouLevel.setSelectedIndex(0);
//			} else {
//				switch (Integer.valueOf(textFieldResult.get(0).get(COLUMN_NAME_KEKA_TI))) {
//				// 積極的支援が選択されている場合
//				case 1:
//					jComboBox_HokenSidouLevel.setSelectedIndex(1);
//					break;
//
//				// 動機づけ支援が選択されている場合
//				case 2:
//					jComboBox_HokenSidouLevel.setSelectedIndex(2);
//					break;
//
//				// なし
//				case 3:
//					jComboBox_HokenSidouLevel.setSelectedIndex(3);
//					break;
//
//				// 判定不能
//				case 4:
//					jComboBox_HokenSidouLevel.setSelectedIndex(4);
//					break;
//				}
//			}
//		}
//	}
//
//	/* メタボリックシンドローム判定表示 */
//	private void showMetablicResult(boolean preview) {
//		String query;
//		/* メタボリックシンドローム判定の結果を表示する。 */
//		query = this.createMetabolicSql(preview);
//
//		List<Hashtable<String, String>> textFieldResult = new ArrayList<Hashtable<String, String>>();
//
//		try {
//			textFieldResult = JApplication.kikanDatabase.sendExecuteQuery(query);
//		} catch (SQLException e) {
//			logger.error(e.getMessage());
//			e.printStackTrace();
//		}
//
//		if (textFieldResult.size() != 0) {
//			if (textFieldResult.get(0).get(COLUMN_NAME_KEKA_TI).equals("")) {
//				// 未判定
//				jComboBox_MetaboHantei.setSelectedIndex(0);
//			} else {
//				switch (Integer.valueOf(textFieldResult.get(0).get(COLUMN_NAME_KEKA_TI))) {
//				// 基準該当の場合
//				case 1:
//					jComboBox_MetaboHantei.setSelectedIndex(1);
//					break;
//
//				// 予備軍該当の場合
//				case 2:
//					jComboBox_MetaboHantei.setSelectedIndex(2);
//					break;
//
//				// 非該当の場合
//				case 3:
//					jComboBox_MetaboHantei.setSelectedIndex(3);
//					break;
//
//				// 判定不能の場合
//				case 4:
//					jComboBox_MetaboHantei.setSelectedIndex(4);
//					break;
//				}
//			}
//		}
//	}
//
//	/* 階層化データ取得 */
//	private String createKaisoukaSql(boolean preview) {
//		String query;
//		StringBuffer buffer = new StringBuffer();
//		buffer.append("SELECT master.KOUMOKU_CD AS KOUMOKUCD,");
//		buffer.append("master.KOUMOKU_NAME AS KOUMOKUNAME,");
//		buffer.append("master.KENSA_HOUHOU AS KENSA_HOUHOU,");
//		buffer.append("sonota.KEKA_TI,");
//		buffer.append("master.DS_KAGEN,");
//		buffer.append("master.DS_JYOUGEN,");
//		buffer.append("master.JS_KAGEN,");
//		buffer.append("master.JS_JYOUGEN,");
//		buffer.append("master.DATA_TYPE,");
//		buffer.append("master.MAX_BYTE_LENGTH,");
//		buffer.append("sonota.KOMENTO,");
//		buffer.append("sonota.H_L,");
//		buffer.append("sonota.HANTEI ");
//		buffer.append("FROM ");
//		buffer.append(" (T_KENSHINMASTER master ");
//		buffer.append("LEFT JOIN T_KENSACENTER_MASTER kensa ");
//		buffer.append("ON kensa.KOUMOKU_CD = master.KOUMOKU_CD ");
//		buffer.append(" AND kensa.KENSA_CENTER_CD = ");
//		buffer.append(JQueryConvert.queryConvert(validatedData.getKensaCenterCode()));
//		buffer.append(" )LEFT JOIN T_KENSAKEKA_SONOTA sonota ");
//		buffer.append("ON sonota.KOUMOKU_CD = master.KOUMOKU_CD ");
//		buffer.append(" AND sonota.KENSA_NENGAPI = ");
//		buffer.append(JQueryConvert.queryConvert(validatedData.getKensaJissiDate()));
//		buffer.append(" AND sonota.UKETUKE_ID = ");
//		// add ver2 s.inoue 2009/06/25
//		if (isCopy && !preview){
//			buffer.append(JQueryConvert.queryConvert(validatedData.getUketukePre_id()));
//		}else{
//			buffer.append(JQueryConvert.queryConvert(validatedData.getUketuke_id()));
//		}
//
//		buffer.append(" WHERE master.KOUMOKU_CD IN ");
//		buffer.append("( ");
//		buffer.append("SELECT KOUMOKU_CD ");
//		buffer.append("FROM T_KENSHIN_P_S ");
//		buffer.append("WHERE K_P_NO = ");
//		buffer.append(JQueryConvert.queryConvert(String.valueOf(kenshinPatternNumber)) + " AND ");
//		buffer.append("KOUMOKU_CD IN ");
//		buffer.append("( ");
//
//		// 保健指導レベルのみを対象とする
//		buffer.append(JQueryConvert.queryConvert("9N506000000000011") + ") " + ") ");
//		buffer.append("AND " + "master.HKNJANUM = ");
//		buffer.append(JQueryConvert.queryConvert(validatedData.getHokenjyaNumber()));
//		query = buffer.toString();
//		return query;
//	}
//
//	/* メタボリックシンドローム判定表示 */
//	private String createMetabolicSql(boolean preview) {
//		String query;
//		// メタボリックシンドローム判定に関する表示を行う
//		StringBuffer buffer = new StringBuffer();
//		buffer.append("SELECT master.KOUMOKU_CD AS KOUMOKUCD,");
//		buffer.append("master.KOUMOKU_NAME AS KOUMOKUNAME,");
//		buffer.append("master.KENSA_HOUHOU AS KENSA_HOUHOU,");
//		buffer.append("sonota.KEKA_TI,");
//		buffer.append("master.DS_KAGEN,");
//		buffer.append("master.DS_JYOUGEN,");
//		buffer.append("master.JS_KAGEN,");
//		buffer.append("master.JS_JYOUGEN,");
//		buffer.append("master.DATA_TYPE,");
//		buffer.append("master.MAX_BYTE_LENGTH,");
//		buffer.append("sonota.KOMENTO,");
//		buffer.append("sonota.H_L,");
//		buffer.append("sonota.HANTEI ");
//		buffer.append("FROM ");
//		buffer.append("( ");
//		buffer.append("T_KENSHINMASTER master ");
//		buffer.append("LEFT JOIN T_KENSACENTER_MASTER kensa ");
//		buffer.append("ON kensa.KOUMOKU_CD = master.KOUMOKU_CD ");
//		buffer.append(" AND ");
//		buffer.append("kensa.KENSA_CENTER_CD = ");
//		buffer.append(JQueryConvert
//								.queryConvert(validatedData.getKensaCenterCode()));
//		buffer.append(") ");
//		buffer.append("LEFT JOIN T_KENSAKEKA_SONOTA sonota ");
//		buffer.append("ON sonota.KOUMOKU_CD = master.KOUMOKU_CD ");
//		buffer.append(" AND ");
//		buffer.append("sonota.KENSA_NENGAPI = ");
//		buffer.append(JQueryConvert.queryConvert(validatedData.getKensaJissiDate()));
//		buffer.append(" AND ");
//		buffer.append("sonota.UKETUKE_ID = ");
//		// add ver2 s.inoue 2009/06/25
//		if (isCopy && !preview){
//			buffer.append(JQueryConvert.queryConvert(validatedData.getUketukePre_id()));
//		}else{
//			buffer.append(JQueryConvert.queryConvert(validatedData.getUketuke_id()));
//		}
//
//		buffer.append(" ");
//		buffer.append("WHERE master.KOUMOKU_CD IN ");
//		buffer.append("( ");
//		buffer.append("SELECT KOUMOKU_CD ");
//		buffer.append("FROM T_KENSHIN_P_S ");
//		buffer.append("WHERE K_P_NO = ");
//		buffer.append(JQueryConvert.queryConvert(
//				String.valueOf(kenshinPatternNumber)));
//		buffer.append(" AND ");
//		buffer.append("KOUMOKU_CD IN ");
//		buffer.append("( ");
//		//		 メタボリック診断のみを対象とする
//		buffer.append(JQueryConvert.queryConvert("9N501000000000011"));
//		buffer.append(") ");
//		buffer.append(") ");
//		buffer.append("AND ");
//		buffer.append("master.HKNJANUM = ");
//		buffer.append(JQueryConvert.queryConvert(
//				validatedData.getHokenjyaNumber()));
//		query = buffer.toString();
//		return query;
//	}
//
//	/* テーブルデータ表示データ */
//	private String createCellDataSql(boolean preview) {
//		StringBuffer buffer = new StringBuffer();
//
//		buffer.append("SELECT master.KOUMOKU_CD AS KOUMOKUCD,");
//		// edit ver2 s.inoue 2009/07/23
//		//buffer.append("master.KOUMOKU_NAME AS KOUMOKUNAME,");
//		buffer.append("  case when master.KOUMOKU_NAME ='生活機能問診1' then '質問1-1' ");
//		buffer.append("  when master.KOUMOKU_NAME ='生活機能問診2' then '質問1-2' ");
//		buffer.append("  when master.KOUMOKU_NAME ='生活機能問診3' then '質問1-3' ");
//		buffer.append("  when master.KOUMOKU_NAME ='生活機能問診4' then '質問4' ");
//		buffer.append("  when master.KOUMOKU_NAME ='生活機能問診5' then '質問5' ");
//		buffer.append("  when master.KOUMOKU_NAME ='生活機能問診6' then '質問6' ");
//		buffer.append("  when master.KOUMOKU_NAME ='生活機能問診7' then '質問7' ");
//		buffer.append("  when master.KOUMOKU_NAME ='生活機能問診8' then '質問8' ");
//		buffer.append("  when master.KOUMOKU_NAME ='生活機能問診9' then '質問9' ");
//		buffer.append("  when master.KOUMOKU_NAME ='生活機能問診10' then '質問10' ");
//		buffer.append("  when master.KOUMOKU_NAME ='生活機能問診11' then '質問11' ");
//		buffer.append("  when master.KOUMOKU_NAME ='生活機能問診12' then '質問12' ");
//		buffer.append("  when master.KOUMOKU_NAME ='生活機能問診13' then '質問13' ");
//		buffer.append("  when master.KOUMOKU_NAME ='生活機能問診14' then '質問14' ");
//		buffer.append("  when master.KOUMOKU_NAME ='生活機能問診15' then '質問15' ");
//		buffer.append("  when master.KOUMOKU_NAME ='生活機能問診16' then '質問16' ");
//		buffer.append("  when master.KOUMOKU_NAME ='生活機能問診17' then '質問17' ");
//		buffer.append("  when master.KOUMOKU_NAME ='生活機能問診18' then '質問18' ");
//		buffer.append("  when master.KOUMOKU_NAME ='生活機能問診19' then '質問19' ");
//		buffer.append("  when master.KOUMOKU_NAME ='生活機能問診20' then '質問20' ");
//		buffer.append("  when master.KOUMOKU_NAME ='生活機能問診21' then '質問21' ");
//		buffer.append("  when master.KOUMOKU_NAME ='生活機能問診22' then '質問22' ");
//		buffer.append("  when master.KOUMOKU_NAME ='生活機能問診23' then '質問23' ");
//		buffer.append("  when master.KOUMOKU_NAME ='生活機能問診24' then '質問24' ");
//		buffer.append("  when master.KOUMOKU_NAME ='生活機能問診25' then '質問25' ");
//		buffer.append("  else master.KOUMOKU_NAME end KOUMOKUNAME, ");
//
//		buffer.append("master.KENSA_HOUHOU AS KENSA_HOUHOU,");
//		buffer.append("sonota.JISI_KBN,");
//		buffer.append("sonota.KEKA_TI,");
//		buffer.append("master.KAGEN,");
//		buffer.append("master.DS_KAGEN,");
//		buffer.append("master.DS_JYOUGEN,");
//		buffer.append("master.JS_KAGEN,");
//		buffer.append("master.JS_JYOUGEN,");
//		buffer.append("master.KAGEN,");
//		buffer.append("master.JYOUGEN,");
//		buffer.append("master.DATA_TYPE,");
//		buffer.append("master.MAX_BYTE_LENGTH,");
//		buffer.append("sonota.KOMENTO,");
//		buffer.append("sonota.H_L,");
//		buffer.append("sonota.HANTEI, ");
//		buffer.append("master.HISU_FLG as HISU_FLG ");
//		buffer.append("FROM ");
//		buffer.append("( ");
//		buffer.append("T_KENSHINMASTER master ");
//		buffer.append("LEFT JOIN T_KENSACENTER_MASTER kensa ");
//		buffer.append("ON kensa.KOUMOKU_CD = master.KOUMOKU_CD ");
//		buffer.append(" AND ");
//		buffer.append("kensa.KENSA_CENTER_CD = ");
//		buffer.append(JQueryConvert
//								.queryConvert(validatedData.getKensaCenterCode()));
//		buffer.append(") ");
//		buffer.append("LEFT JOIN T_KENSAKEKA_SONOTA sonota ");
//		buffer.append("ON sonota.KOUMOKU_CD = master.KOUMOKU_CD ");
//		buffer.append(" AND ");
//		buffer.append("sonota.KENSA_NENGAPI = ");
//		buffer.append(JQueryConvert.queryConvert(validatedData.getKensaJissiDate()));
//		buffer.append(" AND ");
//		buffer.append("sonota.UKETUKE_ID = ");
//		// add ver2 s.inoue 2009/06/25
//		if (isCopy && !preview){
//			buffer.append(JQueryConvert.queryConvert(validatedData.getUketukePre_id()));
//		}else{
//			buffer.append(JQueryConvert.queryConvert(validatedData.getUketuke_id()));
//		}
//		buffer.append(" ");
//
//		String patternNumber = "";
//		patternNumber = JQueryConvert.queryConvert(String.valueOf(kenshinPatternNumber));
//
//		buffer.append(" LEFT JOIN T_KENSHIN_P_S ps ");
//		buffer.append(" ON ps.KOUMOKU_CD = master.KOUMOKU_CD   ");
//		buffer.append(" AND ps.K_P_NO =    ");
//		buffer.append(patternNumber);
//
//		buffer.append("WHERE master.KOUMOKU_CD IN ");
//		buffer.append("( ");
//		buffer.append("SELECT KOUMOKU_CD ");
//		buffer.append("FROM T_KENSHIN_P_S ");
//		buffer.append("WHERE K_P_NO = ");
//		buffer.append(patternNumber);
//		buffer.append(" AND ");
//		buffer.append("KOUMOKU_CD NOT IN ");
//		buffer.append("( ");
//		//メタボリック診断、保健指導レベルを除く
//		buffer.append(JQueryConvert.queryConvertAppendComma("9N501000000000011"));
//		buffer.append(JQueryConvert.queryConvert("9N506000000000011") + ") ");
//
//		buffer.append(") ");
//		buffer.append("AND " + "master.HKNJANUM = ");
//		buffer.append(JQueryConvert.queryConvert(validatedData.getHokenjyaNumber()));
//		buffer.append(" order by ps.LOW_ID ");
//
//		String query = buffer.toString();
//		return query;
//	}
//
//	/**
//	 * 健診パターンに健診項目が存在するかどうか
//	 */
//	public static boolean existPattarnKensaKoumoku(
//			int patternNumber,
//			String koumokuCd){
//			boolean retChk = false;
//
//			ArrayList<Hashtable<String, String>> result
//				= new ArrayList<Hashtable<String, String>>();
//
//			try{
//
//				// 変更後のパターンに健診項目が含まれるかどうか
//				StringBuffer buffer = new StringBuffer();
//				buffer.append(" SELECT KOUMOKU_CD FROM T_KENSHIN_P_S ");
//				buffer.append(" where K_P_NO = ?");
//				buffer.append(" and KOUMOKU_CD = ?");
//				String query = buffer.toString();
//
//				String[] params = {String.valueOf(patternNumber),koumokuCd };
//
//				result = JApplication.kikanDatabase.sendExecuteQuery(query, params);
//
//			}catch(SQLException ex){
//				ex.printStackTrace();
//			}
//
//			if (result.size()==0)
//				retChk=true;
//
//		return retChk;
//	}
//
//	/**
//	 * 請求区分による検査項目チェック
//	 */
//	public static boolean isNotExistKensaKoumoku(
//			String hokenjyaNumber,
//			String uketukeId,
//			String kensaDate,
//			String seikyuKbn,
//			String koumokuCd,
//			String kekkati,
//			String jisiKbn
//			)
//	{
//			boolean retChk = false;
//			boolean hanteiHisu = false;
//
//			// 例外の項目(質問票に関わる項目)
//			String KoumokuHd = koumokuCd.substring(0, 3);
//			if(KoumokuHd.equals("9N7") ||
//					koumokuCd.equals(CODE_HOKEN_SHIDOU) ||
//					koumokuCd.equals(CODE_SEIKATU_KAIZEN) ||
//					koumokuCd.equals(CODE_SAIKETSU_ZIKAN) ||
//					koumokuCd.equals(CODE_ISHINOHANDAN_1) ||
//					koumokuCd.equals(CODE_ISHINOHANDAN_2) ||
//					koumokuCd.equals(CODE_ISHINOHANDAN_3) ||
//					koumokuCd.equals(CODE_ISHINOHANDAN_4)){
//				return false;
//			}
//
//			// 基本的な健診,詳細な健診,追加健診項目,人間ドック
//			StringBuffer buffer = new StringBuffer();
//			String[] params =null;
//
//			// edit s.inoue 20081126 新規登録時のチェックが効かない為
//
//			buffer.append(" select master.HISU_FLG ");
//			buffer.append(" from T_KENSHINMASTER master ");
//			buffer.append(" where master.HKNJANUM = ?");
//			buffer.append(" and KOUMOKU_CD = ?");
//
//			params = new String []{ hokenjyaNumber,koumokuCd  };
//
//			String query = buffer.toString();
//
//			ArrayList<Hashtable<String, String>> result = null;
//			try{
//				result = JApplication.kikanDatabase.sendExecuteQuery(query, params);
//			}catch(SQLException ex){
//				ex.printStackTrace();
//			}
//
//			for (Hashtable<String, String> item : result) {
//				String hisuFlg = item.get("HISU_FLG");
//				//String tanka = item.get("TANKA_KENSIN");
//				// 登録対象のフラグチェック
//				if (jisiKbn.equals("1")){
//					if (!kekkati.equals("")){
//						hanteiHisu = true;
//					}
//				}else if (jisiKbn.equals("2") ||
//						jisiKbn.equals("0")){
//					if (kekkati.equals("")){
//						hanteiHisu = true;
//					}
//				}
//
//				if (hanteiHisu){
//					// 整合性確認用
//					if (seikyuKbn.equals(JApplication.SEIKYU_KBN_BASE)){
//						if (!hisuFlg.equals(JApplication.HISU_FLG_BASE)){
//							retChk = true;break;
//						}
//					}else if (seikyuKbn.equals(JApplication.SEIKYU_KBN_SYOSAI)){
//						if (!(hisuFlg.equals(JApplication.HISU_FLG_BASE) ||
//								hisuFlg.equals(JApplication.HISU_FLG_SYOSAI))){
//							retChk = true;break;
//						}
//					}else if (seikyuKbn.equals(JApplication.SEIKYU_KBN_TUIKA)){
//						if (!(hisuFlg.equals(JApplication.HISU_FLG_BASE) ||
//								hisuFlg.equals(JApplication.HISU_FLG_TUIKA))){
//							retChk = true;break;
//						}
//					}
//				}
//
//			}
//		return retChk;
//	}
//
//	/**
//	 * 登録
//	 * @return boolean 健診パターン変更による請求区分の該当検査項目の削除
//	 */
//	public boolean pattarnKensaKoumoku() {
//
//		boolean patarnHanbetu = false;
//		cellColors.clear();
//
//		if (tableResult != null) {
//
//			try{
//
//				if (validatedData.setHihokenjyaNumber(jTextField_HokenjyaNumber.getText())
//						&& validatedData.setSeikyuKubun((String) jComboBox_SeikyuKubun.getSelectedItem())){
//
//					// del s.inoue 2009/08/13
//					// JApplication.kikanDatabase.Transaction();
//
//					ArrayList<Hashtable<String, String>> sonotaResults = null;
//
//					int tmpKenshinPatternNumber=kenshinPatternNumber;
//
//					// edit ver2 s.inoue 2009/04/24
//					if (prekenshinPatternNumber != -1){
//						kenshinPatternNumber=prekenshinPatternNumber;
//					}
//					// edit ver2 s.inoue 2009/06/25
//					String crequery = this.createCellDataSql(false);
//					try {
//						sonotaResults = JApplication.kikanDatabase.sendExecuteQuery(crequery);
//					} catch (SQLException f) {
//						f.printStackTrace();
//					}
//					kenshinPatternNumber=tmpKenshinPatternNumber;
//
//					// 検査結果その他テーブルに対してパターン外の結果を削除する
//					for (int i = 0; i < sonotaResults.size(); i++) {
//
//						Hashtable<String, String> databaseItem = sonotaResults.get(i);
//
////						// add ver2 s.inoue 2009/04/24
////						String kensakekka = validatedData.getKekka();
//
//						boolean isOtherValidated = (
//								 validatedData.setKensaKoumokuCode(databaseItem.get(COLUMN_NAME_KOUMOKUCD)));
//
//						if (isOtherValidated){
//
//							// 検査項目整合性チェック
//							boolean validatePattarn = existPattarnKensaKoumoku(
//									kenshinPatternNumber,validatedData.getKensaKoumokuCode());
//
//							if (validatePattarn){
//								// edit s.inoue 20081202 1回目のみ表示
//								if (!patarnHanbetu){
//									RETURN_VALUE retValue = null;
//									retValue = JErrorMessage.show("M3637", this);
//									if(retValue == RETURN_VALUE.NO) {
//										break;
//									}
//									patarnHanbetu = true;
//								}
//
//								// deleteByUketukeIdKensaNengapi⇒delete
//								if (! this.isCopy && ! this.isNewKekkaData) {
//									kensakekaSonotaDao.delete(
//											new Long(validatedData.getUketuke_id()),
//											new Integer(this.initialDate),
//											new String(validatedData.getKensaKoumokuCode()));
//								}
//							}
//						}
//					}
//
//					// del s.inoue 2009/08/13
//					// JApplication.kikanDatabase.Commit();
//
//				}
//			} catch (SQLException ex1) {
//				ex1.printStackTrace();
//				try{
//					JApplication.kikanDatabase.rollback();
//				}catch (SQLException ex2) {}
//				return false;
//			}
//		}
//
//		return patarnHanbetu;
//	}
//
//	/**
//	 * 複製処理の個人情報登録
//	 *
//	 * @return boolean 登録の有無
//	 */
//	private String registerKojinCopy(){
//
//		StringBuilder query = new StringBuilder();
//		query.append("INSERT INTO T_KOJIN ( PTNUM, JYUSHIN_SEIRI_NO, YUKOU_KIGEN, HKNJANUM, HIHOKENJYASYO_KIGOU,");
//		query.append(" HIHOKENJYASYO_NO, NAME, KANANAME, NICKNAME, BIRTHDAY, SEX,HOME_POST, HOME_ADRS, HOME_BANTI, HOME_TEL1, KEITAI_TEL, ");
//		query.append(" FAX, EMAIL, KEITAI_EMAIL, KEIYAKU_TORIMATOME, KOUHUBI, SIHARAI_DAIKOU_BANGO, MADO_FUTAN_K_SYUBETU, MADO_FUTAN_KIHON, ");
//		query.append(" MADO_FUTAN_S_SYUBETU, MADO_FUTAN_SYOUSAI, MADO_FUTAN_T_SYUBETU, MADO_FUTAN_TSUIKA, MADO_FUTAN_D_SYUBETU, MADO_FUTAN_DOC, ");
//		query.append(" NENDO, UKETUKE_ID, MADO_FUTAN_SONOTA, HOKENJYA_FUTAN_KIHON, HOKENJYA_FUTAN_SYOUSAI, HOKENJYA_FUTAN_TSUIKA, HOKENJYA_FUTAN_DOC ) ");
//		query.append(" SELECT ");
//		query.append(" PTNUM, ");
//		//JYUSHIN_SEIRI_NOを指定
//		query.append(JQueryConvert.queryConvert(validatedData.getcopyJyushinkenID()));
//		query.append(" ,YUKOU_KIGEN, HKNJANUM, HIHOKENJYASYO_KIGOU, HIHOKENJYASYO_NO, NAME, KANANAME, ");
//
//		query.append(" NICKNAME, BIRTHDAY, SEX, HOME_POST, HOME_ADRS, HOME_BANTI, HOME_TEL1, KEITAI_TEL, FAX, EMAIL, KEITAI_EMAIL, KEIYAKU_TORIMATOME, ");
//		query.append(" KOUHUBI, SIHARAI_DAIKOU_BANGO, MADO_FUTAN_K_SYUBETU, MADO_FUTAN_KIHON, MADO_FUTAN_S_SYUBETU, MADO_FUTAN_SYOUSAI, MADO_FUTAN_T_SYUBETU, ");
//		query.append(" MADO_FUTAN_TSUIKA, MADO_FUTAN_D_SYUBETU, MADO_FUTAN_DOC, NENDO, ");
//
//		query.append(JQueryConvert.queryConvert(validatedData.getUketuke_id()));
//		query.append(" ,MADO_FUTAN_SONOTA, HOKENJYA_FUTAN_KIHON, HOKENJYA_FUTAN_SYOUSAI, HOKENJYA_FUTAN_TSUIKA, HOKENJYA_FUTAN_DOC ");
//		query.append(" FROM T_KOJIN WHERE UKETUKE_ID = ");
//		query.append(JQueryConvert.queryConvert(validatedData.getUketukePre_id()));
//
//		return query.toString();
//
//	}
//	/**
//	 * 履歴,名寄せテーブルへ登録処理を実施する。
//	 */
//	private void registerHistory(){
//
//		TNayoseDao tNayoseDao;
//
//		StringBuilder nayoseQuery = null;
//		StringBuilder newNayoseQuery = null;
//
//			// 名寄せNO採番
//			long nayoseNo = -1L;
//			long retTNayoseNo = 0L;
//
//			// 現時刻を取得
//			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//			String stringTimeStamp = dateFormat.format(Calendar.getInstance().getTime());
//
//			try {
//				/* T_NAYOSE Dao を作成する。 */
//				tNayoseDao = (TNayoseDao) DaoFactory.createDao(
//						JApplication.kikanDatabase.getMConnection(), new TNayose());
//
//				retTNayoseNo = tNayoseDao.selectOldNayoseNo(Long.parseLong(validatedData.getUketukePre_id()));
//				// 既に履歴がある場合、その名寄せNoを使用する
//				if (retTNayoseNo > 0) {
//					nayoseNo = retTNayoseNo;
//				}else{
//					nayoseNo = tNayoseDao.selectNewNayoseNo();
//
//					// 名寄せテーブル登録処理
//					// 受付IDの紐付け処理:1、2をセット
//					// 1.受診券情報で氏名かなで紐付けた受付IDを登録
//					// 2.受診券登録後に新しい受付番号を登録
//
//					// 1.処理:履歴がない場合
//					nayoseQuery = new StringBuilder();
//					nayoseQuery.append("INSERT INTO T_NAYOSE (NAYOSE_NO,UKETUKE_ID,UPDATE_TIMESTAMP)");
//					nayoseQuery.append("VALUES (");
//					nayoseQuery.append(JQueryConvert.queryConvertAppendComma(String.valueOf(nayoseNo)));
//					nayoseQuery.append(JQueryConvert.queryConvertAppendComma(validatedData.getUketukePre_id()));
//					nayoseQuery.append(JQueryConvert.queryConvert(stringTimeStamp));
//					nayoseQuery.append(") ");
//
//					try {
//						JApplication.kikanDatabase.sendNoResultQuery(nayoseQuery.toString());
//					} catch (SQLException e) {
//						logger.error(e.getMessage());
//
//						try {
//							JApplication.kikanDatabase.rollback();
//						} catch (SQLException g) {
//							logger.error(g.getMessage());
//						}
//					}
//
//				}
//
//				// 2.処理:採番した追加用レコード共通処理
//				newNayoseQuery = new StringBuilder();
//				newNayoseQuery.append("INSERT INTO T_NAYOSE (NAYOSE_NO,UKETUKE_ID,UPDATE_TIMESTAMP)");
//				newNayoseQuery.append("VALUES (");
//				newNayoseQuery.append(JQueryConvert.queryConvertAppendComma(String.valueOf(nayoseNo)));
//				newNayoseQuery.append(JQueryConvert.queryConvertAppendComma(validatedData.getUketuke_id()));
//				newNayoseQuery.append(JQueryConvert.queryConvert(stringTimeStamp));
//				newNayoseQuery.append(") ");
//
//				try {
//					JApplication.kikanDatabase.sendNoResultQuery(newNayoseQuery.toString());
//				} catch (SQLException e) {
//					logger.error(e.getMessage());
//					try {
//						JApplication.kikanDatabase.rollback();
//					} catch (SQLException g) {
//						logger.error(g.getMessage());
//					}
//				}
//			} catch (Exception e) {
//				logger.error(e.getMessage());
//			}
//	}
//
//	/**
//	 * 結果登録処理
//	 * @return boolean 登録の有無
//	 */
//	public boolean register() {
//		String query = null;
//		Hashtable<String, String> ResultItem = null;
//		cellColors.clear();
//
//		String kensaJissiDate = validatedData.getKensaJissiDate();
//
//		if (tableResult != null) {
//			/* 健診実施日 */
//			String jissiDate = jTextField_Date.getText();
//
//			/*
//			 *  初期表示の日付から変更があった場合は、警告を表示する。
//			 */
//			boolean hasJissiDateChanged = false;
//			if (! jissiDate.equals(this.initialDate)) {
//				hasJissiDateChanged = true;
//			}
//
//			if (hasJissiDateChanged && ! this.isNewKekkaData) {
//
//				/* M3543=確認,健診実施日を[%s]から[%s]に変更して登録してよろしいですか？,1,0 */
//				String[] messageParams = {this.initialDate, jissiDate};
//				RETURN_VALUE retValue = JErrorMessage.show("M3543", this, messageParams);
//				if (retValue != RETURN_VALUE.YES) {
//					return false;
//				}
//			}
//
//			/*
//			 * 既に健診結果データが存在し、かつ初期表示の日付から変更があった場合は、
//			 * 警告メッセージを表示する。
//			 */
//			boolean existsKensaKekkaData = existsKensaKekkaData(jissiDate);
//			if (existsKensaKekkaData) {
//				if (hasJissiDateChanged) {
//					/* M3544=確認,[%s]には、すでに結果データが存在します。登録した場合、
//					 * 以前に入力した結果データは消えてしまいます。登録してよろしいですか？,1,0 */
//					String[] messageParams = {jissiDate};
//					RETURN_VALUE retValue = JErrorMessage.show("M3544", this, messageParams);
//					if (retValue != RETURN_VALUE.YES) {
//						return false;
//					}
//				}
//
//				mode = UPDATE_MODE;
//			}
//			else {
//				mode = INSERT_MODE;
//			}
//
//			// フレームのテキストフィールドなどに関して検証を行う
//			if (validatedData.setHihokenjyaCode(jTextField_HokenjyaCode.getText())
//					&& validatedData.setHihokenjyaNumber(jTextField_HokenjyaNumber.getText())
//					&& validatedData.setNameKana(jTextField_Name.getText())
//					&& validatedData.setKensaJissiDate(jissiDate)
//					&& validatedData.setSougouComment(jEditorPane_Comment.getText())
//					&& validatedData.setMetaboHantei((String) jComboBox_MetaboHantei.getSelectedItem())
//					&& validatedData.setHokenSidouLevel((String) jComboBox_HokenSidouLevel.getSelectedItem())
//					&& validatedData.setSeikyuKubun((String) jComboBox_SeikyuKubun.getSelectedItem())) {
//
//				boolean validateKensa = false;
//				boolean hanteiBase = false;
//				boolean hanteiSyosai = false;
//				boolean hanteiTuika = false;
//				boolean notExtMessage = false;
//				boolean validateInput = false;
//
//				int intBaseCnt = 0;
//				int intSyosaiCnt = 0;
//				int intTuikaCnt = 0;
//				int intAllCnt = 0;
//				// add ver2 s.inoue 2009/06/29
//				TKojinDao tKojinDao;
//
//				if (validateAsRegisterPushed(validatedData)) {
//					if (validatedData.isValidateAsDataSet()) {
//						try {
//
//							JApplication.kikanDatabase.Transaction();
//
//							// add ver2 s.inoue 2009/06/02
//							if (isCopy){
//
//								// add ver2 s.inoue 2009/06/29
//								try {
//									/* T_KOJIN Dao を作成する。 */
//									tKojinDao = (TKojinDao) DaoFactory.createDao(
//											JApplication.kikanDatabase.getMConnection(), new TKojin());
//
//									// add ver2 s.inoue 2009/06/29
//									// 整理番号重複チェック処理
//									Long resultCnt = tKojinDao.selectByCountUniqueKey(validatedData.getcopyJyushinkenID());
//									if (resultCnt > 0) {
//										JErrorMessage.show("M3607", this);
//										// edit s.inoue 2009/10/20
//										// 受診券重複を許可して、警告レベルへ変更する。
//										// JApplication.kikanDatabase.rollback();
//										// this.refreshTableCellColor();
//										// this.initializeColumnWidth();
//										// return false;
//									}
//								}catch (Exception ex) {
//									logger.error(ex.getMessage());
//								}
//
//								String queryKojin = registerKojinCopy();
//								registerHistory();
//								try {
//									JApplication.kikanDatabase.sendNoResultQuery(queryKojin);
//								} catch (SQLException e) {
//									logger.error(e.getMessage());
//									JApplication.kikanDatabase.rollback();
//									// add ver2 s.inoue 2009/06/29
//									this.refreshTableCellColor();
//									this.initializeColumnWidth();
//									return false;
//								}
//							}
//
//							boolean isOtherValidated = false;
//							boolean isKekkaValidated = false;
//							// add s.inoue 2009/10/17
//							HashMap<Integer, String> kekkaValidated = new HashMap<Integer, String>();
//
//							boolean isCheckCSV = false;
//							String errMessage = "";
//
//							int i = 0;
//
//							// 検査結果その他テーブルに関して登録処理を行う
//							for (; i < this.model.getRowCount(); i++) {
//
//								// 結果値以外に関しての検証
//								isOtherValidated = (validatedData
//										.setHihokenjyaCode(jTextField_HokenjyaCode.getText())
//										&& validatedData.setHihokenjyaNumber(jTextField_HokenjyaNumber.getText())
//										&& validatedData.setKensaJissiDate(jissiDate)
//										// edit s.inoue 2009/11/11
//										&& validatedData.setKensaKoumokuCode((String) this.model.getData(i, COLUMN_IDX_KOUMOKUCD))
//										&& validatedData.setComment((String) this.model.getData(i, COLUMN_IDX_KOMENTO))
//										&& validatedData.setHL((String) this.model.getData(i, COLUMN_IDX_HL))
//										&& validatedData.setHantei((String) this.model.getData(i, COLUMN_IDX_HANTEI))
//										&& validatedData.setChkText((String) this.model.getData(i, COLUMN_IDX_JISIKBN)));
//
//								// 結果値に関する検証
//								ResultItem = tableResult.get(i);
//
//								String koumokuCode = ResultItem.get(COLUMN_NAME_KOUMOKUCD);
//								String koumokuName = ResultItem.get(COLUMN_NAME_KOUMOKUNAME);
//
//								switch (Integer.valueOf(ResultItem.get(COLUMN_NAME_DATA_TYPE))) {
//								case 1:
//									// 数値の場合
//									// edit s.inoue 2009/11/11
//									isKekkaValidated
//										= validatedData.setKekkaDecimal((String) this.model.getData(i, COLUMN_IDX_KEKKA_NUM),
//										ResultItem.get(COLUMN_NAME_MAX_BYTE_LENGTH));
//
//									if (isKekkaValidated) {
//										isCheckCSV = true;
//										String retMessage = getHighLawMessage(
//											(String)this.model.getData(i,COLUMN_IDX_KOUMOKUCD),
//											koumokuName,
//											validatedData.getHokenjyaNumber(),
//											validatedData.getKekka());
//
//										if (!retMessage.isEmpty()){
//											validateInput = true;
//											errMessage +=retMessage;
//										}
//									}
//									break;
//
//								case 2:
//									// コードの場合
//									ArrayList<Hashtable<String, String>> codeResult = null;
//									Hashtable<String, String> codeResultItem;
//									// edit s.inoue 2009/09/29
//									query = new String("SELECT KOUMOKU_CD, CODE_NUM, CODE_NAME FROM T_DATA_TYPE_CODE WHERE KOUMOKU_CD = "
//											+ JQueryConvert.queryConvert(koumokuCode));
//									try {
//										codeResult = JApplication.kikanDatabase.sendExecuteQuery(query);
//									} catch (SQLException e) {
//										logger.warn(e.getMessage());
//									}
//									// edit s.inoue 2009/11/11
//									if (this.model.getData(i).get(COLUMN_IDX_KEKKA_CODE).equals("")) {
//										isKekkaValidated = validatedData.setKekkaCode("");
//										isCheckCSV = true;
//										break;
//									}
//
//									for (int j = 0; j < codeResult.size(); j++) {
//										codeResultItem = codeResult.get(j);
//										// edit s.inoue 2009/11/11
//										if (this.model.getData(i, COLUMN_IDX_KEKKA_CODE)
//												.equals(codeResultItem.get(COLUMN_NAME_CODE_NAME))) {
//
//											isCheckCSV = CheckHighLawItem(
//													(String)this.model.getData(i,COLUMN_IDX_KOUMOKUCD),
//													validatedData.getHokenjyaNumber(),
//													codeResultItem.get(COLUMN_NAME_CODE_NUM));
//
//											isKekkaValidated = validatedData.setKekkaCode(codeResultItem.get(COLUMN_NAME_CODE_NUM));
//											break;
//										}
//									}
//									break;
//
//								case 3:
//									// 文字列の場合
//									isCheckCSV = true;
//									// edit s.inoue 2009/11/11
//									isKekkaValidated
//										= validatedData.setKekkaText((String) this.model
//										.getData(i, COLUMN_IDX_KEKKA_STR), ResultItem.get(COLUMN_NAME_MAX_BYTE_LENGTH));
//									break;
//								}
//								// add s.inoue 2009/10/17
//								if (!isKekkaValidated) {
//									kekkaValidated.put(i,"項目コード:" + koumokuCode + "  項目名:" + koumokuName);
//								}
//
//								if (isKekkaValidated && isOtherValidated && isCheckCSV) {
//									// 受付ID,年度の追加対応
//									// 検査結果特定、その他テーブルへ新規レコード挿入
//									kensakekaSonota.setUKETUKE_ID(new Long(validatedData.getUketuke_id()));
//									kensakekaSonota.setHIHOKENJYASYO_KIGOU(validatedData.getHihokenjyaCode());
//									kensakekaSonota.setHIHOKENJYASYO_NO(validatedData.getHihokenjyaNumber());
//									try {
//										kensakekaSonota.setKENSA_NENGAPI(new Integer(kensaJissiDate));
//										kensakekaSonota.setNENDO(FiscalYearUtil.getThisYear(kensaJissiDate));
//									} catch (NumberFormatException e) {
//										logger.error(e.getMessage());
//									}
//									kensakekaSonota.setKOUMOKU_CD(validatedData.getKensaKoumokuCode());
//									kensakekaSonota.setKEKA_TI(validatedData.getKekka());
//									kensakekaSonota.setKOMENTO(validatedData.getComment());
//
//									try {
//										Integer jisiInteger = null;
//										String strJisi = validatedData.getJisi_KBN();
//										if (strJisi != null
//												&& !strJisi.isEmpty()) {
//											jisiInteger = new Integer(strJisi);
//										}
//										kensakekaSonota.setJISI_KBN(jisiInteger);
//
//									} catch (NumberFormatException e) {
//										/* 何もしない */
//									}
//
//									try {
//										Integer hlInteger = null;
//										String hlString = validatedData.getHL();
//										if (hlString != null
//												&& !hlString.isEmpty()) {
//											hlInteger = new Integer(hlString);
//										}
//										kensakekaSonota.setH_L(hlInteger);
//
//									} catch (NumberFormatException e) {
//										/* 何もしない */
//									}
//
//									try {
//										kensakekaSonota.setHANTEI(new Integer(
//												validatedData.getHantei()));
//									} catch (NumberFormatException e) {
//										/* 何もしない */
//									}
//
//									// add s.inoue 20080911 結果値(未実施,測定不可能)判定
//									if ((validatedData.getJisi_KBN().equals("0") ||
//											validatedData.getJisi_KBN().equals("2")) &&
//											!validatedData.getKekka().equals("")){
//										String[] params = { koumokuCode, koumokuName };
//										JErrorMessage.show("M3634", this, params);
//										JApplication.kikanDatabase.rollback();
//										this.refreshTableCellColor();
//										this.initializeColumnWidth();
//										return false;
//									}
//
//									// 登録対象のフラグチェック
//
//									// 例外の項目(質問票に関わる項目)
//									String KoumokuHd = koumokuCode.substring(0, 3);
//									if(!KoumokuHd.equals("9N7")&&
//											!koumokuCode.equals(CODE_HOKEN_SHIDOU) &&
//											!koumokuCode.equals(CODE_SEIKATU_KAIZEN) &&
//											!koumokuCode.equals(CODE_SAIKETSU_ZIKAN) &&
//											!koumokuCode.equals(CODE_ISHINOHANDAN_1) &&
//											!koumokuCode.equals(CODE_ISHINOHANDAN_2) &&
//											!koumokuCode.equals(CODE_ISHINOHANDAN_3) &&
//											!koumokuCode.equals(CODE_ISHINOHANDAN_4)){
//
//										String hisuFlg = ResultItem.get(COLUMN_NAME_HISU_FLG);
//
//										if ((validatedData.getJisi_KBN().equals("1") && !validatedData.getKekka().equals("")) ||
//											((validatedData.getJisi_KBN().equals("2") || validatedData.getJisi_KBN().equals("0"))
//													&& validatedData.getKekka().equals(""))){
//
//											if (validatedData.getSeikyuKubun().equals(JApplication.SEIKYU_KBN_BASE)){
//												if (hisuFlg.equals(JApplication.HISU_FLG_BASE)){
//													hanteiBase = true;intBaseCnt +=1;
//												}
//											}else if (validatedData.getSeikyuKubun().equals(JApplication.SEIKYU_KBN_SYOSAI)){
//												if (hisuFlg.equals(JApplication.HISU_FLG_BASE)){
//													hanteiBase = true;
//												}else
//												if (hisuFlg.equals(JApplication.HISU_FLG_SYOSAI)){
//													hanteiSyosai = true;
//												}
//												if (hanteiBase && hanteiSyosai){
//													intSyosaiCnt +=1;
//												}
//											}else if (validatedData.getSeikyuKubun().equals(JApplication.SEIKYU_KBN_TUIKA)){
//												if (hisuFlg.equals(JApplication.HISU_FLG_BASE)){
//													hanteiBase = true;
//												}else
//												if (hisuFlg.equals(JApplication.HISU_FLG_TUIKA)){
//													hanteiTuika = true;
//												}
//												if (hanteiBase && hanteiTuika){
//													intTuikaCnt +=1;
//												}
//											}else if (validatedData.getSeikyuKubun().equals(JApplication.SEIKYU_KBN_SYOSAI_TUIKA) ||
//													validatedData.getSeikyuKubun().equals(JApplication.SEIKYU_KBN_DOC)){
//												if (hisuFlg.equals(JApplication.HISU_FLG_BASE)){
//													hanteiBase = true;
//												}else
//												if (hisuFlg.equals(JApplication.HISU_FLG_SYOSAI)){
//													hanteiSyosai = true;
//												}else
//												if (hisuFlg.equals(JApplication.HISU_FLG_TUIKA)){
//													hanteiTuika = true;
//												}
//												if (hanteiBase && hanteiSyosai && hanteiTuika){
//													intAllCnt +=1;
//												}
//											}
//										}
//									}
//
//									// add s.inoue 20080911 結果値判定(未実施,測定不可能)判定
//									// 検査項目整合性チェック
//									validateKensa= isNotExistKensaKoumoku(
//											    validatedData.getHokenjyaNumber(),
//												validatedData.getUketuke_id(),
//												validatedData.getKensaJissiDate(),
//												validatedData.getSeikyuKubun(),
//												validatedData.getKensaKoumokuCode(),
//												validatedData.getKekka(),
//												validatedData.getJisi_KBN());
//
//									if (validateKensa){
//
//										if (!notExtMessage){
//											JErrorMessage.show("M3635", this);
//											notExtMessage = true;
//										}
//									}
//
//									if (! this.isCopy && ! this.isNewKekkaData) {
//										kensakekaSonotaDao.delete(
//												new Long(validatedData.getUketuke_id()),
//												new Integer(this.initialDate),
//												new String(validatedData.getKensaKoumokuCode()));
//									}
//
//									kensakekaSonotaDao.insert(kensakekaSonota);
//
//								} else {
//									if (!isKekkaValidated) {
//										// edit s.inoue 2009/10/17
//										// JErrorMessage.show("M3601", this);
//										String[] params = { kekkaValidated.get(i) };
//										JErrorMessage.show("M3601", this, params);
//									} else if (!isCheckCSV) {
//										String[] params = { koumokuCode, koumokuName };
//										JErrorMessage.show("M3602", this, params);
//									}
//									// 登録を終了にする
//									System.out.println("RollBack");
//									JApplication.kikanDatabase.rollback();
//
//									this.refreshTableCellColor();
//									this.initializeColumnWidth();
//									// edit s.inoue 2009/10/21
//									refreshCellAction();
//									return false;
//								}
//							}
//
//							// add ver2 s.inoue 2009/04/14
//							if (validateInput){
//								String[] params = { errMessage };
//								JErrorMessage.show("M3602", this, params);
//							}
//
//							// add s.inoue 20081008
//							if ((intBaseCnt == 0) &&(intSyosaiCnt == 0) &&(intTuikaCnt == 0) && (intAllCnt == 0)){
//								// メッセージは一回きりにする
//								if (!notExtMessage && isCheckCSV){
//									JErrorMessage.show("M3636", this);
//									notExtMessage = true;
//								}
//							}
//
//							// テーブルの最後の行をinsertする前にbreakされた場合
//							if (i != this.model.getRowCount()) {
//								System.out.println("RollBack");
//								JApplication.kikanDatabase.rollback();
//
//								this.refreshTableCellColor();
//								this.initializeColumnWidth();
//
//								return false;
//
//							} else {
//								// 次にメタボリックシンドローム判定と保健指導レベルに関しての処理を行う
//								try {
//									// 受付ID,年度の追加対応
//									// 検査結果データその他レコード削除・挿入
//
//									kensakekaSonota = new TKensakekaSonota();
//									kensakekaSonota.setUKETUKE_ID(new Long(validatedData.getUketuke_id()));
//									kensakekaSonota.setHIHOKENJYASYO_KIGOU(validatedData.getHihokenjyaCode());
//									kensakekaSonota.setHIHOKENJYASYO_NO(validatedData.getHihokenjyaNumber());
//									try {
//										// edit ver2 s.inoue 2009/07/13
//										kensakekaTokutei.setNENDO(FiscalYearUtil.getThisYear(kensaJissiDate));
//
//										kensakekaSonota.setKENSA_NENGAPI(new Integer(kensaJissiDate));
//									} catch (NumberFormatException e) {
//									}
//
//									// まずメタボリックシンドロームに関する処理を行う
//									// edit s.inoue 2009/10/26 更新前のものを消す！！
//									// kensakekaSonotaDao.delete(new Long(validatedData.getUketuke_id()),new Integer(kensaJissiDate),"9N501000000000011");
//									kensakekaSonotaDao.delete(new Long(validatedData.getUketuke_id()),
//											this.initialDate.equals("")?new Integer(kensaJissiDate):new Integer(this.initialDate),"9N501000000000011");
//
//									kensakekaSonota.setKOUMOKU_CD("9N501000000000011");
//									kensakekaSonota.setKEKA_TI(validatedData.getMetaboHantei());
//									kensakekaSonotaDao.insert(kensakekaSonota);
//
//									// 保健指導レベルに関する処理を行う
//									// edit s.inoue 2009/10/26 更新前のものを消す！！
//									// kensakekaSonotaDao.delete(new Long(validatedData.getUketuke_id()),new Integer(kensaJissiDate),"9N506000000000011");
//									kensakekaSonotaDao.delete(new Long(validatedData.getUketuke_id()),
//											this.initialDate.equals("")?new Integer(kensaJissiDate):new Integer(this.initialDate),"9N506000000000011");
//									kensakekaSonota.setKOUMOKU_CD("9N506000000000011");
//									kensakekaSonota.setKEKA_TI(validatedData.getHokenSidouLevel());
//									kensakekaSonotaDao.insert(kensakekaSonota);
//
//								} catch (SQLException f) {
//									f.printStackTrace();
//									JApplication.kikanDatabase.rollback();
//									return false;
//								}
//
//								// 検査結果特定テーブルに対して登録処理を行う
//								try {
//									if (mode == UPDATE_MODE) {
//										// 受付ID,年度の追加対応
//										// 受付IDを指定して検査結果データ特定を更新
//
//										query = "UPDATE T_KENSAKEKA_TOKUTEI SET "
//												+ "KENSA_CENTER_CD = "
//												+ JQueryConvert.queryConvertAppendComma(validatedData.getKensaCenterCode())
//												+ "K_P_NO = "
//												+ JQueryConvert.queryConvertAppendComma(String.valueOf(kenshinPatternNumber))
//												+ "KOMENTO = "
//												+ JQueryConvert.queryConvertAppendComma(validatedData.getSougouComment())
//												// add ver2 s.inoue 2009/07/13
//												+ "NENDO = "
//												+ JQueryConvert.queryConvertAppendComma(String.valueOf(kensakekaTokutei.getNENDO()))
//
//												+ "SEIKYU_KBN = "
//												+ JQueryConvert.queryConvert(validatedData.getSeikyuKubun())
//												+ " WHERE "
//												+ "UKETUKE_ID = "
//												+ JQueryConvert.queryConvert(validatedData.getUketuke_id())
//												+ " AND "
//												+ "	KENSA_NENGAPI = "
//												+ JQueryConvert.queryConvert(kensaJissiDate);
//
//										JApplication.kikanDatabase.sendNoResultQuery(query);
//
//									} else if (mode == INSERT_MODE) {
//
//										// 受付ID,年度の追加対応
//										// 検査結果データ特定レコード挿入
//										kensakekaTokutei = new TKensakekaTokutei();
//										kensakekaTokutei.setUKETUKE_ID(new Long(validatedData.getUketuke_id()));
//										kensakekaTokutei.setHIHOKENJYASYO_KIGOU(validatedData.getHihokenjyaCode());
//										kensakekaTokutei.setHIHOKENJYASYO_NO(validatedData.getHihokenjyaNumber());
//										kensakekaTokutei.setKENSA_CENTER_CD(validatedData.getKensaCenterCode());
//
//										try {
//											kensakekaTokutei.setK_P_NO(new Integer(kenshinPatternNumber));
//										} catch (NumberFormatException e) {
//										}
//										kensakekaTokutei.setKOMENTO(validatedData.getSougouComment());
//										try {
//
//											kensakekaTokutei.setKENSA_NENGAPI(new Integer(kensaJissiDate));
//										} catch (NumberFormatException e) {
//										}
//
//										// add ver2 s.inoue 2009/07/13
//										try {
//											kensakekaTokutei.setNENDO(FiscalYearUtil.getThisYear(kensaJissiDate));
//										} catch (NumberFormatException e) {
//										}
//
//										// このあとUPDATEを行うため一時的にデータを入れる
//										kensakekaTokutei.setKEKA_INPUT_FLG(new Integer(1));
//										try {
//											kensakekaTokutei.setSEIKYU_KBN(new Integer(validatedData.getSeikyuKubun()));
//										} catch (NumberFormatException e) {
//										}
//										kensakekaTokuteiDao.insert(kensakekaTokutei);
//									}
//
//									/* 複製ではなく、かつ日付変更である場合は、元のデータを削除する。 */
//									if (! this.isCopy && hasJissiDateChanged && ! this.isNewKekkaData) {
//										kensakekaTokuteiDao.delete(validatedData.getUketuke_id(),this.initialDate);
//									}
//								} catch (SQLException f) {
//									f.printStackTrace();
//									try {
//										JApplication.kikanDatabase.rollback();
//										return false;
//									} catch (SQLException g) {
//										g.printStackTrace();
//										JErrorMessage
//												.show("M0000", this);
//										System.exit(1);
//									}
//								}
//
//								// 検査結果の未、済を判定し、UPDATEをかける
//								int nullCount =0;
//								int existCount = 0;
//
//								try {
//
//									ArrayList<Hashtable<String, String>> rs;
//									// T_KOJIN,T_KENSAKEKA_TOKUTEIにUKETUKE_ID,NENDO追加対応
//									query = " SELECT"
//											+ "	 KEKA_TI"
//											+ " FROM"
//											+ "	 T_KENSAKEKA_SONOTA "
//											+ " WHERE "
//											+ "	UKETUKE_ID = "
//											+ JQueryConvert
//													.queryConvert(validatedData
//															.getUketuke_id())
//											+ " AND "
//											+ "	KENSA_NENGAPI = "
//											+ JQueryConvert
//													.queryConvert(kensaJissiDate)
//											+ " AND "
//											+ "	KOUMOKU_CD NOT IN ( "
//											+ JQueryConvert
//													.queryConvertAppendComma("2A020000001930101")
//											+ JQueryConvert
//													.queryConvertAppendComma("2A030000001930101")
//											+ JQueryConvert
//													.queryConvertAppendComma("2A040000001930102")
//											+ JQueryConvert
//													.queryConvertAppendComma("9A110160700000011")
//											+ JQueryConvert
//													.queryConvertAppendComma("9A110160800000049")
//											+ JQueryConvert
//													.queryConvertAppendComma("9A110161000000049")
//											+ JQueryConvert
//													.queryConvertAppendComma("9E100166000000011")
//											+ JQueryConvert
//													.queryConvertAppendComma("9E100166100000011")
//											+ JQueryConvert
//													.queryConvertAppendComma("9E100166200000011")
//											+ JQueryConvert
//													.queryConvertAppendComma("9E100166300000011")
//											+ JQueryConvert
//													.queryConvertAppendComma("9E100160900000049")
//											+ JQueryConvert
//													.queryConvertAppendComma("9E100161000000049")
//											+ JQueryConvert
//													.queryConvertAppendComma("9N501000000000011")
//											+ JQueryConvert
//													.queryConvertAppendComma("9N506000000000011")
//											+ JQueryConvert
//													.queryConvertAppendComma("9N511000000000049")
//											+ JQueryConvert
//													.queryConvert("9N516000000000049")
//											+ " )";
//
//
//									rs = JApplication.kikanDatabase
//											.sendExecuteQuery(query);
//
//									/* 全項目数 */
//									existCount = rs.size();
//
//									// 検査結果が未入力のレコードを除いた場合
//									query = " SELECT "
//											+ "	KEKA_TI "
//											+ " FROM "
//											+ "	T_KENSAKEKA_SONOTA sonota, "
//											+ "	T_KENSHINMASTER master "
//											+ " WHERE "
//											+ " sonota.KOUMOKU_CD = master.KOUMOKU_CD "
//											+ " 	AND "
//											+ " sonota.UKETUKE_ID = "
//											+ JQueryConvert
//													.queryConvert(validatedData
//															.getUketuke_id())
//											+ " 	AND "
//											+ " sonota.KENSA_NENGAPI = "
//											+ JQueryConvert
//													.queryConvert(kensaJissiDate)
//											+ " 	AND "
//											+ " sonota.KEKA_TI is null "
//											+ " 	AND "
//											+ " sonota.KOUMOKU_CD NOT IN ( "
//											+ JQueryConvert
//													.queryConvertAppendComma("2A020000001930101")
//											+ JQueryConvert
//													.queryConvertAppendComma("2A030000001930101")
//											+ JQueryConvert
//													.queryConvertAppendComma("2A040000001930102")
//											+ JQueryConvert
//													.queryConvertAppendComma("9A110160700000011")
//											+ JQueryConvert
//													.queryConvertAppendComma("9A110160800000049")
//											+ JQueryConvert
//													.queryConvertAppendComma("9A110161000000049")
//											+ JQueryConvert
//													.queryConvertAppendComma("9E100166000000011")
//											+ JQueryConvert
//													.queryConvertAppendComma("9E100166100000011")
//											+ JQueryConvert
//													.queryConvertAppendComma("9E100166200000011")
//											+ JQueryConvert
//													.queryConvertAppendComma("9E100166300000011")
//											+ JQueryConvert
//													.queryConvertAppendComma("9E100160900000049")
//											+ JQueryConvert
//													.queryConvertAppendComma("9E100161000000049")
//											+ JQueryConvert
//													.queryConvertAppendComma("9N501000000000011")
//											+ JQueryConvert
//													.queryConvertAppendComma("9N506000000000011")
//											+ JQueryConvert
//													.queryConvertAppendComma("9N511000000000049")
//											+ JQueryConvert
//													.queryConvert("9N516000000000049")
//											+ " ) " + " 	AND "
//											+ " master.HISU_FLG = 1";
//									rs = JApplication.kikanDatabase
//											.sendExecuteQuery(query);
//
//									/* 入力数 */
//									nullCount = rs.size();
//
//									// T_KOJIN,T_KENSAKEKA_TOKUTEIにUKETUKE_ID,NENDO追加対応
//									// 全ての項目について入力がなされていた場合
//									String kensakekaInputFlg = (nullCount == 0) ? "2"
//											: "1";
//									query = String.format("UPDATE "
//											+ "T_KENSAKEKA_TOKUTEI " + "SET "
//											+ "KEKA_INPUT_FLG = '%s' "
//											+ "WHERE " + "UKETUKE_ID = %s "
//											+ "AND " + "KENSA_NENGAPI = %s",
//											kensakekaInputFlg, validatedData
//													.getUketuke_id(),
//											kensaJissiDate);
//									JApplication.kikanDatabase
//											.sendNoResultQuery(query);
//
//									// edit ver2 s.inoue 2009/08/25
//									// パターン登録でエラーメッセージを回避する必要
//									// 結果があるかどうか登録処理時に、コミット前の状態のフラグを渡す
//									JApplication.kikanDatabase.Commit();
//									stateChangedKenshinPatternNumber(validatedData.getUketuke_id(),jissiDate,true,isNewKekkaData);
//
//									this.initialDate = this.validatedData.getKensaJissiDate();
//									this.isNewKekkaData = false;
//
//								} catch (SQLException f) {
//									f.printStackTrace();
//
//									try {
//										JApplication.kikanDatabase.rollback();
//										return false;
//									} catch (SQLException g) {
//										g.printStackTrace();
//										JErrorMessage
//												.show("M0000", this);
//										System.exit(1);
//									}
//								}
//							}
//						} catch (SQLException f) {
//							f.printStackTrace();
//
//							try {
//								System.out.println("Rollback!");
//								JApplication.kikanDatabase.rollback();
//								return false;
//							} catch (SQLException g) {
//								g.printStackTrace();
//								JErrorMessage.show("M0000", this);
//								System.exit(1);
//							}
//						}
//					}
//				}
//			}
//		}
//		if (mode == INSERT_MODE) {
//			mode = UPDATE_MODE;
//			jTextField_SeiriNo.setVisible(false);
//			jLabelSeirino.setVisible(false);
//			isCopy = false;
//		}
//		refreshTable(kensaJissiDate, true,false,true);
//		// edit s.inoue
//		refreshInputControl(jTextField_Date.getText(),false,false);
//		return true;
//	}
//
//	/**
//	 * 実施日確定ボタン
//	 */
//	public void changeInputDate(String kensaJissiDate, boolean saveColumnWidth) {
//
//			if (!kensaJissiDate.isEmpty()) {
//				// 健診パターンコンボボックスに表示するパターン名を取得しセットする
//				// //すでに登録されている年月日かどうかをチェックする
//				boolean existsKensaKekkaData = existsKensaKekkaData(kensaJissiDate);
//
//				boolean isChangeMode = false;
//				if (existsKensaKekkaData) {
//					isChangeMode = true;
//					mode = UPDATE_MODE;
//				}
//				else {
//					mode = INSERT_MODE;
//				}
//
//				/* 健診パターン選択欄を初期化する。 */
//				this.initializeKenshinPatternComboBox(kensaJissiDate, isChangeMode,false);
//
//				/* 検査センター名称選択欄を初期化する。 */
//				this.initializeKensaCenterComboBox(kensaJissiDate, isChangeMode);
//				refreshTable(kensaJissiDate, true,false,false);
//				// edit s.inoue
//				refreshInputControl(jTextField_Date.getText(),false,false);
//			}
//	}
//
//	/**
//	 * 結果データが存在するかどうか確認
//	 */
//	private boolean existsKensaKekkaData(String kensaJissiDate) {
//		Integer kensanengapi = null;
//		try {
//			kensanengapi = new Integer(kensaJissiDate);
//		} catch (NumberFormatException e) {
//		}
//
//		List<TKensakekaSonota> resultList = null;
//		try {
//			String uketuke_id = validatedData.getUketuke_id();
//			resultList = kensakekaSonotaDao.selectByUketukeIdKensaNengapi(
//					new Long(uketuke_id),
//					kensanengapi);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		boolean exsistsKensaKekkaData = false;
//		if (resultList != null &&  resultList.size() > 0) {
//			exsistsKensaKekkaData = true;
//		}
//
//		return exsistsKensaKekkaData;
//	}
//
//	/**
//	 * 実施を日変更する。
//	 */
//	public void changeJissibi() {
//		if (validatedData.setKensaJissiDate(jTextField_Date.getText())) {
//			if (!validatedData.getKensaJissiDate().isEmpty()) {
//				// 健診パターンコンボボックスに表示するパターン名を取得しセットする
//				// //すでに登録されている年月日かどうかをチェックする
//				Integer kensanengapi = null;
//				try {
//					kensanengapi = new Integer(validatedData
//							.getKensaJissiDate());
//				} catch (NumberFormatException e) {
//				}
//
//				List<TKensakekaSonota> resultList = null;
//				try {
//					String uketuke_id = validatedData.getUketuke_id();
//					resultList = kensakekaSonotaDao.selectByUketukeIdKensaNengapi(
//							new Long(uketuke_id),kensanengapi);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//
//				/*
//				 * 変更しようとしている日に、検査結果が既に存在する場合は、処理を中断する。
//				 */
//				if (resultList == null) {
//					/* 指定した日[%s]には、すでに健診結果データが存在するため、変更できません。
//					 * 変更する場合は、先に[%s]の健診結果データを削除する必要があります。 */
//
//					String kensaNengapiString = String.valueOf(kensanengapi);
//					String[] messageParams = { kensaNengapiString, kensaNengapiString };
//
//					JErrorMessage.show("M3631", this, messageParams);
//
//					return;
//				}
//			}
//		}
//	}
//
//	/**
//	 * 検査センター名称選択欄を初期化する。
//	 */
//	private void initializeKensaCenterComboBox(String jissiDate, boolean isChangeMode) {
//		/* 健診センター項目コンボボックスに表示するセンター名を取得しセットする
//		 * 項目を追加する前に、全て削除しておく必要がある。
//		 */
//		jComboBox_KensaKikanName.removeAllItems();
//
//		// 健診センター項目コンボボックスに表示するセンター名を取得しセットする
//		ArrayList<Hashtable<String, String>> items = null;
//		try {
//			items = JApplication.kikanDatabase
//					.sendExecuteQuery("SELECT DISTINCT KENSA_CENTER_CD,CENTER_NAME FROM T_KENSACENTER_MASTER");
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		jComboBox_KensaKikanName.addItem(null);
//		for (int i = 0; i < items.size(); i++) {
//			Hashtable<String, String> Item;
//			Item = items.get(i);
//			jComboBox_KensaKikanName.addItem(Item.get("CENTER_NAME"));
//		}
//		// 変更の場合は以前選択されていたものを初期値として表示する
//		if (isChangeMode) {
//			// T_KOJIN,T_KENSAKEKA_TOKUTEIにUKETUKE_ID,NENDO追加対応
//			String query = "SELECT CENTER.CENTER_NAME "
//					+ "FROM T_KENSAKEKA_TOKUTEI AS TOKUTEI "
//					+ "LEFT JOIN T_KENSACENTER_MASTER AS CENTER "
//					+ "ON "
//					+ "TOKUTEI.KENSA_CENTER_CD = CENTER.KENSA_CENTER_CD "
//					+ "WHERE "
//					+ "UKETUKE_ID = "
//					+ JQueryConvert.queryConvert(validatedData
//							.getUketuke_id())
//					+ " "
//					+ "AND "
//					+ "TOKUTEI.KENSA_NENGAPI = "
//					+ JQueryConvert.queryConvert(jissiDate);
//
//			ArrayList<Hashtable<String, String>> items2 = null;
//			try {
//				items2 = JApplication.kikanDatabase.sendExecuteQuery(query);
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//
//			for (int i = 0; i < jComboBox_KensaKikanName.getItemCount(); i++) {
//				// 検査センターが未選択の場合でも検査センターコンボボックスで最初の項目が選択されてしまう障害の修正
//				String selectedItem = (String) jComboBox_KensaKikanName
//						.getItemAt(i);
//				if ((selectedItem != null)
//						&& (selectedItem.equals(items2.get(0).get(
//								"CENTER_NAME")))) {
//					jComboBox_KensaKikanName.setSelectedIndex(i);
//					break;
//				}
//			}
//		}
//
//		isInit = true;
//		stateChangedKensaKikanName();
//		stateInitKenshinPatternNumber(jissiDate);
//	}
//
//	/**
//	 * 健診パターン選択欄を初期化する。
//	 */
//	private void initializeKenshinPatternComboBox(String jissiDate, boolean isChangeMode, boolean preview) {
//		jComboBox_Pattern.removeAllItems();
//
//		ArrayList<Hashtable<String, String>> items = null;
//		try {
//			items = JApplication.kikanDatabase
//			.sendExecuteQuery("SELECT * FROM T_KENSHIN_P_M WHERE K_P_NO >= 0 ORDER BY K_P_NO");
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		for (int i = 0; i < items.size(); i++) {
//			Hashtable<String, String> Item;
//			Item = items.get(i);
//			jComboBox_Pattern.addItem(Item.get("K_P_NAME"));
//		}
//
//		// add ver2 s.inoue 2009/06/25
//		String uketukeID = "";
//
//		if (isCopy && !preview ){
//			uketukeID = validatedData.getUketukePre_id();
//		}else{
//			uketukeID = validatedData.getUketuke_id();
//		}
//
//		// 変更の場合は以前選択されていたものを初期値として表示する
//		if (isChangeMode || (isCopy && !preview )) {
//			String Query = "SELECT PM.K_P_NAME "
//					+ "FROM T_KENSAKEKA_TOKUTEI AS TOKUTEI "
//					+ "LEFT JOIN T_KENSHIN_P_M AS PM "
//					+ "ON TOKUTEI.K_P_NO = PM.K_P_NO "
//					+ " WHERE "
//					+ "UKETUKE_ID = "
//					+ uketukeID
//					+ " "
//					+ "AND "
//					+ "TOKUTEI.KENSA_NENGAPI = "
//					+ JQueryConvert.queryConvert(jissiDate);
//			try {
//				items = JApplication.kikanDatabase.sendExecuteQuery(Query);
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//			if (!items.isEmpty()) {
//				for (int i = 0; i < jComboBox_Pattern.getItemCount(); i++) {
//					if (((String) (jComboBox_Pattern.getItemAt(i)))
//							.equals(items.get(0).get("K_P_NAME"))) {
//						jComboBox_Pattern.setSelectedIndex(i);
//						break;
//					}
//				}
//			}
//		}
//	}
//
//	/**
//	 * BMI計算ActionEvent
//	 */
//	public void actionBMICalc(){
//		if (
//				heightCellPosition != null
//				&& weightCellPosition != null
//				&& BMICellPosition != null
//			) {
//
//			String height = (String)this.model.getData(
//					heightCellPosition.getRow(),
//					heightCellPosition.getColumn()
//				);
//
//			String weight = (String) this.model.getData(
//					weightCellPosition.getRow(),
//					weightCellPosition.getColumn()
//				);
//
//			String bmi = calcBMI(height, weight);
//
//			String kensaKekka = JValidate.validateKensaKekkaDecimal(
//											bmi,BMIFormat);
//			this.model.setData(
//					kensaKekka,
//					BMICellPosition.getRow(),
//					BMICellPosition.getColumn()
//				);
//		}
//	}
//
//	/**
//	 * BMI値を計算する
//	 */
//	public String calcBMI(String height, String weight) {
//		// どちらかが空値の場合は空値を返す
//		if (height.isEmpty() || weight.isEmpty()) {
//			return "";
//		}
//
//		// ０除算エラーを防ぐ
//		if (Double.valueOf(height) == 0) {
//			return "";
//		}
//
//		BigDecimal bmi = new BigDecimal(
//				String
//						.valueOf((Double.valueOf(weight) / ((Double
//								.valueOf(height) / 100) * (Double
//								.valueOf(height) / 100)))));
//		return String.valueOf(bmi.setScale(1, BigDecimal.ROUND_HALF_UP)
//				.doubleValue());
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
//	 * キャンセルボタン
//	 */
//	public void pushedCancelButton(ActionEvent e) {
//		RETURN_VALUE Ret = JErrorMessage.show("M3621", this);
//		if (Ret == RETURN_VALUE.YES) {
//			dispose();
//		}
//	}
//
//	/**
//	 * クリアボタン
//	 */
//	public void pushedClearButton(ActionEvent e) {
//		RETURN_VALUE Ret = JErrorMessage.show("M3640", this);
//		if (Ret == RETURN_VALUE.YES) {
//			refreshTable(jTextField_Date.getText(),false,true,false);
//			refreshInputControl(jTextField_Date.getText(),true,false);
//		}
//	}
//
//	/**
//	 * 登録ボタン
//	 */
//	public void pushedRegisterButton(ActionEvent e) {
//
//		String jissiDate = jTextField_Date.getText();
//		if (jissiDate == null || jissiDate.isEmpty()) {
//			JErrorMessage.show("M3633", this);
//			this.jTextField_Date.grabFocus();
//			return;
//		}
//		/* 健診実施日のフォーマットチェック */
//		if (! validatedData.setKensaJissiDate(jissiDate)) {
//			JErrorMessage.show("M3605", this);
//			this.jTextField_Date.grabFocus();
//			return;
//		}
//		if (isCopy){
//			String copySeiriNo = jTextField_SeiriNo.getText();
//			/* 受診券整理番号のフォーマットチェック */
//			if (! validatedData.setcopyJyushinkenID(copySeiriNo)) {
//				JErrorMessage.show("M3606", this);
//				this.jTextField_SeiriNo.grabFocus();
//				return;
//			}
//		}
//		RETURN_VALUE Ret = JErrorMessage.show("M3622", this);
//		if (Ret == RETURN_VALUE.YES) {
//			this.register();
//		}
//	}
//
//	/* アクションイベント */
//	public void actionPerformed(ActionEvent e) {
//		Object source = e.getSource();
//
//		/* 終了ボタン */
//		if (source == jButton_End) {
//			logger.info(jButton_End.getText());
//			pushedEndButton(e);
//		}
//		/* 登録ボタン */
//		else if (source == jButton_Register) {
//			logger.info(jButton_Register.getText());
//			pushedRegisterButton(e);
//		}
//		/* キャンセルボタン */
//		else if (source == jButton_Cancel) {
//			logger.info(jButton_Cancel.getText());
//			pushedCancelButton(e);
//		}
//		// add ver2 s.inoue 2009/06/02
//		/* クリアボタン */
//		else if (source == jButton_Clear) {
//			logger.info(jButton_Clear.getText());
//			pushedClearButton(e);
//		}
//		/* BMI計算のためのキーリスナー */
//		else if (source == this.model) {
//			actionBMICalc();
//		}
//
//		/* Enterキーによるフォーカス移動機能追加 */
//		if (this.enterkeyFocusComponents != null
//				&& this.enterkeyFocusComponents.contains(source)) {
//			if (source instanceof Component) {
//				((Component) source).transferFocus();
//			}
//		}
//	}
//
////del s.inoue 2009/12/22
////	@Override
////	public void keyPressed(KeyEvent keyEvent) {
////		switch(keyEvent.getKeyCode()){
////		case KeyEvent.VK_F1:
////			pushedEndButton(null);break;
////		case KeyEvent.VK_F2:
////			pushedClearButton(null);break;
////		case KeyEvent.VK_F12:
////			pushedRegisterButton(null);break;
////		}
////	}
//	/* mouse選択イベント */
//	@Override
//	public void mousePressed(MouseEvent e) {
//		mousePopup(e);
//	}
//	@Override
//	public void mouseReleased(MouseEvent e) {
//		mousePopup(e);
//	}
//
//	private void mousePopup(MouseEvent e) {
//		if (e.isPopupTrigger()) {
//			// ポップアップメニューを表示する
//			JComponent c = (JComponent)e.getSource();
//			jEditorPane_Comment.showPopup(c, e.getX(), e.getY());
//			e.consume();
//		}
//	}
//
//	/* combo選択イベント */
//	public void itemStateChanged(ItemEvent e) {
//
//		if (isInit) {
//			if (e.getSource() == jComboBox_KensaKikanName) {
//				stateChangedKensaKikanName();
//			}
//
//			if (e.getSource() == jComboBox_Pattern) {
//				if (e.getStateChange() == ItemEvent.SELECTED){
//					stateChangedKenshinPatternNumber(validatedData.getUketuke_id(),validatedData.getKensaJissiDate(),false,isNewKekkaData);
//				}
//			}
//		}
//	}
//
//	/**
//	 * 入力必須のセルを取得しフォーカスマップを作成 テーブルにフォーカスリスナを設定
//	 * Enterキー押下でフォーカスマップに登録されたセル座標にフォーカスを移動する
//	 */
//	private void setTableFocusMap() {
//		// ref s.inoue 20080912 実施区分フォーカス 3⇒4
//		final int KEKA_TI_COLUMN_COUNT = 3;
//		List<int[]> focusMap = new ArrayList<int[]>();
//		boolean[][] onOff = new boolean[model.getRowCount()][KEKA_TI_COLUMN_COUNT];
//
//		for (int i = 0; i < onOff.length; i++) {
//			for (int j = 0; j < KEKA_TI_COLUMN_COUNT; j++) {
//				onOff[i][j] = true;
//			}
//		}
//
//		for (JSimpleTableCellRendererData rendererData : cellColors) {
//			JSimpleTableCellPosition cellPosition = rendererData
//					.getCellPosition();
//			if (rendererData.getColor().equals(COLOR_DISABLE)) {
//				onOff[cellPosition.getRow()][cellPosition.getColumn()  - KEKA_TI_COLUMN_COUNT] = false;
//			}
//		}
//
//		for (int i = 0; i < onOff.length; i++) {
//			for (int j = 0; j < onOff[i].length; j++) {
//				if (onOff[i][j]) {
//					int[] position = new int[2];
//					position[0] = j + KEKA_TI_COLUMN_COUNT;
//					position[1] = i;
//					focusMap.add(position);
//				}
//			}
//		}
//
//		JKekkaRegisterTableEnterAction enterAction =
//				new JKekkaRegisterTableEnterAction(
//				focusMap,
//				model
//			);
//
//		model.getActionMap().put(
//				enterAction.getInputMapObject(),
//				enterAction.getEnterAction()
//		);
//	}
//}