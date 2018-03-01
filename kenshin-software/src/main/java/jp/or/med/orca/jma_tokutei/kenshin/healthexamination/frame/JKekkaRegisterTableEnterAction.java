package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.InputMap;
import javax.swing.JTable;
import javax.swing.KeyStroke;

/**
 * 結果入力画面のテーブルにて
 * Enterキー押下時にフォーカスマップで指定された
 * セルにフォーカス移動するリスナクラス
 * @author nishiyama
 *
 */
public class JKekkaRegisterTableEnterAction {

	/**
	 * フォーカスマップ
	 * int[2]のリスト
	 * int[0] - 列インデックス
	 * int[1] - 行インデックス
	 */
	private List<int[]> focusMapList;
	private JTable table;
	/**
	 * 前回移動したセルのポジション
	 * int[0] - 列インデックス
	 * int[1] - 行インデックス
	 */
	private int[] lastPosition;
	
	/**
	 * コンストラクタ
	 * @param focusMapList フォーカスマップ
	 * @param table リスナ設定対象テーブル
	 */
	public JKekkaRegisterTableEnterAction(List<int[]> focusMapList, JTable table) {
		
		this.table = table;
		lastPosition = new int[2];
		setFocusMapList(focusMapList);
	}
	
	/**
	 * インプットマップオブジェクトを取得する
	 * @return
	 */
	public Object getInputMapObject() {
        KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
        InputMap im = table.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        return im.get(enter);
	}
	
	/**
	 * Enterキー押下時アクションイベントハンドラ
	 * @return Action
	 */
	public Action getEnterAction() {
		
        final Action oldEnterAction = table.getActionMap().get(getInputMapObject());
        Action enterAction = new AbstractAction() {
        	
        	public void actionPerformed(ActionEvent e) {
                JTable table = (JTable)e.getSource();
                int x = table.getSelectedColumn();
                int y = table.getSelectedRow();
                int nextIndex = -1;
                nextIndex = getNextIndex(x, y);
                if (nextIndex == 0) {
                	nextIndex = getNextIndex(lastPosition[0], lastPosition[1]);
                	if (nextIndex > 0) nextIndex--;
                }
                int[] nextPosition = focusMapList.get(nextIndex);
                lastPosition[0] = nextPosition[0];
                lastPosition[1] = nextPosition[1];
                table.changeSelection(nextPosition[1], nextPosition[0], false, false);
            }
        };
        return enterAction;
	}
	
	/**
	 * 初期化
	 * @param focusMapList フォーカスマップ
	 */
	public void setFocusMapList(List<int[]> focusMapList) {
		this.focusMapList = focusMapList;
//		lastPosition[0] = -1;
//		lastPosition[1] = -1;
		lastPosition[0] = 0;
		lastPosition[1] = 0;
	}
	
	/**
	 * フォーカスマップから
	 * 次に移動するセル座標が格納されているインデックスを取得する
	 * @param x 現在選択している列インデックス
	 * @param y 現在選択している行インデックス
	 * @return 次移動対象セル座標が格納されているマップ中のインデックス
	 */
	private int getNextIndex(int x, int y) {
		int size = focusMapList.size();
		int nextIndex = -1;
		for (int i = 0 ; i < size ; i++) {
			int[] position = focusMapList.get(i);
			if ((position[0] == x) && (position[1] == y)) {
				nextIndex = i + 1;
				break;
			}
		}
        if ((nextIndex < 0) || (nextIndex == size)) {
        	nextIndex = 0;
        }
		return nextIndex;
	}
}