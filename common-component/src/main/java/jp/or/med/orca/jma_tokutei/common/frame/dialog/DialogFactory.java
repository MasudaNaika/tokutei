package jp.or.med.orca.jma_tokutei.common.frame.dialog;

import java.awt.event.ActionEvent;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;


/**
 * ダイアログ生成クラス
 * @author nishiyama
 */
public class DialogFactory {
	private static final String JKENSHINPATTERN = "jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.kenshinpattern.JKenshinpatternMasterMaintenanceListFrame";
	private static final String JLOGINFLAME = "jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.menu.JLoginFrameCtrl";
	private static final String JKEKKALISTFRAME = "jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.kenshin.JKenshinKekkaSearchListFrame";

	private static final String JKOJINFRAME = "jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.kenshin.JKojinRegisterFrameCtrl";
	private static final String JOUTPUTGETUJIFRAME = "jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.getuji.JOutputGetujiSearchListFrame";
	private static final String JOUTPUTNITIJIFRAME = "jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.nitiji.JOutputNitijiSearchListFrame";
	private static final String JSEARCHRESULTLISTFRAME = "jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.hantei.JHanteiSearchResultListFrame";
	private static final String JHOKENJYAMASTERMAINTENANCE = "jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.hokenja.JHokenjyaMasterMaintenanceListFrame";
	private static final String JTEIKEIMAINTENANCE= "jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.shoken.JShokenMasterMaintenanceListFrame";
	private static final String JSHIHARAIMASTERMAINTENANCE = "jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.shiharai.JShiharaiMasterMaintenanceListFrame";
	private static final String JKENSHINPATTERNMASTERMAINTENANCE = "jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.kenshinpattern.JKenshinpatternMasterMaintenanceListFrame";
	private static final String JKENSHINMAINTENANCE= "jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.kenshin.JKenshinMasterMaintenanceListFrame";

	// 現状のまま
	private static final String JSHOWRESULTFRAME = "jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.hantei.JShowResultFrameCtrl";
	private static final String JKENSHINPATTERNLISTFRAME = "jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.JImportDataFrameCtrl";
//	private static final String JKENSHINOUTPUTGETUJI= "jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.JOutputGetujiFrameCtrl";
	// add s.inoue 2012/07/05
	private static final String JHOKENJYAMENTENANCEEDIT = "jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.hokenja.JHokenjyaMasterMaintenanceEditFrameCtrl";
	private static final String JIMPORTDATAFROMECTRL = "jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.JImportDataFrameCtrl";
	private static final String JPATTERNMENTENANCEFROMECTRL = "jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.kenshinpattern.JKenshinPatternMaintenanceEditFrameCtrl";
	// add s.inoue 2012/07/04
	private static final String JSELECTGRAPH = "jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.hantei.JHanteiSearchResultListFrame";

	private static DialogFactory instance;
	private Map<Object, IDialog> dialogEventSrcMap;

	private static String settingVal = "環境設定(S)";
	private static String infomationVal = "更新情報(I)";


	/**
	 * コンストラクタ
	 */
	private DialogFactory() {
		dialogEventSrcMap = new HashMap<Object, IDialog>();
		registDialogToMap();
	}

	/**
	 * ダイアログ生成
	 * @param parent ダイアログ表示元親ウインドウ
	 * @param eventSrc イベントソース
	 * @return
	 */
	public IDialog createDialog(JFrame parent, Object eventSrc, ArrayList<Hashtable<String, String>> selectedRow)
		throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {

		String parentFlame = parent.getClass().getName();

		if (parentFlame.equals(JOUTPUTGETUJIFRAME) ||
				parentFlame.equals(JOUTPUTNITIJIFRAME)){
			return new MessageDialog(parent);
		}else if (parentFlame.equals(JKEKKALISTFRAME) ||
				parentFlame.equals(JKOJINFRAME)){
			return new DateSelectDialog(parent,selectedRow);
		}else if (parentFlame.equals(JLOGINFLAME)){

			// edit s.inoue 2010/08/02
			ActionEvent e = (ActionEvent)eventSrc;
			if (e.getActionCommand().equals(settingVal)){
				return new SettingDialog(parent);
			}else if (e.getActionCommand().equals(infomationVal)){
				return new DngPreviewHtml(parent);
			}else{
				return null;
			}
		// add s.inoue 2012/07/04
		}else if (parentFlame.equals(JSELECTGRAPH)){
            SelectSelectableGraphDialog selectselectablegraphdialog = new SelectSelectableGraphDialog(selectedRow);
            selectselectablegraphdialog.setVisible(true);
            return (IDialog)selectselectablegraphdialog;
		}else{
			return null;
		}
	}

	/**
	 * ダイアログ生成
	 * @param parent ダイアログ表示元親ウインドウ
	 * @param eventSrc イベントソース
	 * @return
	 */
	public IDialog createDialog(JFrame parent, Object eventSrc, int selectedRow, int newPattern)
		throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {

		String parentFlame = parent.getClass().getName();

		if (parentFlame.equals(JKENSHINPATTERN)){
			return new AddKenshinPatternDialog(parent,selectedRow,newPattern);
		}else{
			return null;
		}
	}
	/**
	 * ダイアログ生成
	 * @param parent ダイアログ表示元親ウインドウ
	 * @param eventSrc イベントソース
	 * @return
	 */
	public IDialog createDialog(JFrame parent, String defaultPath)
	throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
		// edit s.inoue 2009/10/06
		String parentFlame = parent.getClass().getName();

		// edit s.inoue 2010/02/26
		if (parentFlame.equals(JHOKENJYAMASTERMAINTENANCE) ||
			parentFlame.equals(JSHIHARAIMASTERMAINTENANCE) ||
			parentFlame.equals(JKENSHINPATTERNMASTERMAINTENANCE) ||
			parentFlame.equals(JTEIKEIMAINTENANCE) ||
			parentFlame.equals(JKENSHINMAINTENANCE)
			// del s.inoue 2011/04/07
//			parentFlame.equals(JKENSHINOUTPUTGETUJI)
			){
			return new FileSelectDialog(parent,defaultPath);
		// add s.inoue 2010/10/20 呼び出し元対応時動作可能
		}else if(parentFlame.equals(JHOKENJYAMENTENANCEEDIT)){
	        return new SelectOrcaHokenjyaDialog(parent, defaultPath, "外部※ORCA保険者情報参照");
		}else if(parentFlame.equals(JIMPORTDATAFROMECTRL)){
	        return new SelectKenshinPatternDialog(parent,defaultPath, "取込データに適用する健診パターンを選択して下さい。");
		}else if(parentFlame.equals(JPATTERNMENTENANCEFROMECTRL)){
	        return new SelectKenshinPatternDialog(parent,defaultPath, "複合する健診パターンを選択して下さい。");
		}else{
			return null;
		}
	}

	/*
	 * default構成
	 */
	public IDialog createDialog(JFrame parent){
		String parentFlame = parent.getClass().getName();

		if (parentFlame.equals(JSEARCHRESULTLISTFRAME) ||
				parentFlame.equals(JSHOWRESULTFRAME)){
			return new SelectPageDialog(parent);
		}else{
			return null;
		}
	}

	/**
	 * インスタンス生成
	 * @return DialogFactoryインスタンス
	 */
	public static DialogFactory getInstance() {
		if (instance == null) {
			instance = new DialogFactory();
		}
		return instance;
	}

	/**
	 * ダイアログクラスをマップに登録する
	 * 将来的にはダイアログ親ウインドウ、ダイアログ表示トリガーイベント、
	 * イベントソースの定義ファイルを読み込み、登録する。
	 */
	private void registDialogToMap() {
	}
}
