package jp.or.med.orca.jma_tokutei.common.filter;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;

import org.openswing.swing.client.GenericButton;
import org.openswing.swing.table.filter.client.FilterDialog;
import org.openswing.swing.table.filter.client.FilterPanel;

/**
 * コンストラクタで指定されたコンポーネントを、フィルターパネルへ追加する
 * あわせて、フィルターパネルの「検索」と「固定」ボタンの大きさも変更する
 */
public class SpecialFilterPanel extends WindowAdapter {
	
	/**
	 * 追加するコンポーネント
	 */
	private JComponent jComponent;
	
	/**
	 * 表示している画面のコンポーネント群（GridControl#getParent().getComponents()）
	 */
	private Component[] componentArgs;
	
	/**
	 * コンストラクタ
	 * 
	 * @param jComponent		フィルターパネルに追加するコンポーネント（追加しない（ボタンの大きさのみ変更する）場合はnullを指定）
	 * @param componentArgs		表示している画面（GridControl#getParent().getComponents()）
	 */
	public SpecialFilterPanel(JComponent jComponent, Component[] componentArgs) {
		this.jComponent = jComponent;
		this.componentArgs = componentArgs;
	}
	
	
	/**
	 * ウィンドウがアクティブ化されると呼び出されます。 
	 * ※画面オープン時や、ポップアップの検索ウィンドウがクローズしたときに呼び出される
	 * 
	 * @param e
	 */
	@Override
	public void windowActivated(WindowEvent e) {
		
		//画面の右に表示している「FilterPanel」を取得
		FilterPanel filterPanel = getFilterPanel(componentArgs, null);
		if (filterPanel != null) {
			setSpecialFilterPanel(filterPanel);
		}
	}

	/**
	 * ウィンドウが非アクティブ化されると呼び出されます。 
	 * ※ポップアップの検索ウィンドウがオープンしたときに呼び出される
	 * 
	 * @param e
	 */
	@Override
	public void windowDeactivated(WindowEvent e) {
		
		//開いている「FilterDialog」を取得
		List<FilterDialog> filterDialogList = getFilterDialog();
		if (filterDialogList.size() != 0) {
			
			//取得した全「FilterDialog」に対して設定する
			for (int i = 0; i < filterDialogList.size(); i++) {
				
				FilterPanel filterPanel = getFilterPanel(filterDialogList.get(i).getComponents(), null);
				if (filterPanel != null) {
					
					setSpecialFilterPanel(filterPanel);
				}
			}
		}
	}
	
	/**
	 * 開いているウィンドウに「FilterDialog」があった場合それを返却する
	 * 
	 * @return
	 */
	private List<FilterDialog> getFilterDialog() {
		
		//開いている全ウィンドウを取得
		Window[] windowArgs = Window.getWindows();
		
		//検索画面を閉じてもWindowが残っている場合があるので、取りあえず全てのFilterDialogを取得する
		List<FilterDialog> resultList = new ArrayList<FilterDialog>();
		if (windowArgs != null) {
			for (int i = 0; i < windowArgs.length; i++) {
				if (windowArgs[i] instanceof FilterDialog) {
					resultList.add((FilterDialog)windowArgs[i]);
				}
			}
		}
		return resultList;
	}
	
	/**
	 * Component[]内のFilterPanelを取得する
	 * 
	 * @param componentArgs
	 * @param filterPanel
	 * @return
	 */
	private FilterPanel getFilterPanel(Component[] componentArgs, FilterPanel filterPanel) {
		
		if (filterPanel != null) {
			return filterPanel;
		}
		
		FilterPanel result = null;
		for (int i = 0; i < componentArgs.length; i++) {
			Container container = (Container)componentArgs[i];
			if (container instanceof FilterPanel) {
				result = (FilterPanel)container;
				break;
			} else if (container.getComponents().length != 0) {
				result = getFilterPanel(container.getComponents(), result);
			}
		}
		return result;
	}

	/**
	 * フィルターパネルへ、コンポーネントを追加する
	 * ※ボタンの大きさも変更する
	 * 
	 * @param filterPanel
	 */
	private void setSpecialFilterPanel(FilterPanel filterPanel) {
		
		//FilterPanelの「mainPanel」を取得
		JPanel mainPanel = (JPanel)filterPanel.getComponent(0);

		//mainPanelから「topPanel」を取得
		JPanel topPanel = (JPanel)mainPanel.getComponent(1);
		
		//「topPanel」のコンポーネント群を取得
		Component[] topPanelComponents = topPanel.getComponents();

		//コンポーネントが未追加の場合
		if (jComponent != null && !jComponent.equals(topPanelComponents[0])) {
			
			//先頭にコンポーネントを追加
			topPanel.add(jComponent, 0);
			
			//フォーカスは無し
			jComponent.setFocusable(false);
			
			//チェックボックスの場合、テキストを設定する
			if (jComponent instanceof JCheckBox) {
				
				//未設定の場合のみ設定
				if ("".equals(((JCheckBox)jComponent).getText())) {
					((JCheckBox)jComponent).setText("条件を保存する");
				}
			}
		}

		//ボタンの大きさを変更
		for (int i = 0; i < topPanelComponents.length; i++) {
			if (topPanelComponents[i] instanceof GenericButton) {
				GenericButton button = (GenericButton)topPanelComponents[i];
				button.setPreferredSize(new Dimension(35, button.getFontMetrics(button.getFont()).getHeight() + button.getMargin().top + button.getMargin().bottom + 16));
			}
		}
		
		//変更を反映
		topPanel.validate();
		mainPanel.validate();
	}
}
