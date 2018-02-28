//package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;
//
//import javax.swing.JFrame;
//
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.kenshin.JKekkaRegisterCommentInputDialog;
//
//public class JKekkaRegisterInputDialogFactory {
//
//	private static final int KEKKA_MOJIRETSU_INDEX = 5;
//	private static final int SOUGOU_COMMENT__INDEX = 12;
//
//	/**
//	 * ダイアログ生成
//	 * @param parent 親ウインドウ
//	 * @param index テーブルセルインデックス
//	 * @return 入力用ダイアログ
//	 */
//	public static IKekkaRegisterInputDialog createDialog(
//										JFrame parent, int index, String comment) {
//		// 総合コメント入力用ダイアログ生成
//		if (index == KEKKA_MOJIRETSU_INDEX) {
//			return new JKekkaRegisterKekkaMojiretsuInputDialog(parent,comment);
//		}
//		else if (index == SOUGOU_COMMENT__INDEX) {
//			return new JKekkaRegisterCommentInputDialog(parent,comment);
//		}
//		return null;
//	}
//}
