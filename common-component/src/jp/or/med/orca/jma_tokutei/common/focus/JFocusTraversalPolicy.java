package jp.or.med.orca.jma_tokutei.common.focus;

import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.text.JTextComponent;

import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenTextControl;

/**
 * フォーカス移動制御
 */
public class JFocusTraversalPolicy extends FocusTraversalPolicy {

	private List<Component> componentList = null;

	private Component defaultComponent = null;

	/**
	 * コンストラクタ
	 */
	public JFocusTraversalPolicy() {
		super();
		this.initialize();
	}

	/**
	 * 初期化
	 */
	private void initialize(){
		boolean ret = false;

		this.componentList = new ArrayList<Component>();
	}

	/**
	 * 制御対象のコンポーネントを追加する。
	 */
	public void addComponent(Component c){
		this.componentList.add(c);
	}

	/* Added 2008/03/12 若月  */
	/* --------------------------------------------------- */
	/**
	 * 指定した位置に、制御対象のコンポーネントを追加する。
	 */
	public void addComponent(Component c, int index){
		this.componentList.add(index, c);
	}
	/* --------------------------------------------------- */

	/**
	 * コンポーネントの取得
	 */
	public Component getComponent(int index){
		return this.componentList.get(index);
	}

	/**
	 * コンポーネントのサイズ
	 */
	public int getComponentSize(){
		return this.componentList.size();
	}

	/**
	 * 制御対象クラスから指定したコンポーネントを削除する。
	 */
	public void removeComponent(Component c){
		for (Iterator<Component> iter = this.componentList.iterator(); iter.hasNext();) {

			Component listComponent = iter.next();
			if (listComponent == c) {
				this.componentList.remove(listComponent);
				return;
			}
		}
	}

	/**
	 * 次のコンポーネントを取得する。
	 */
	@Override
	public Component getComponentAfter(Container container, Component component) {

		Component ret = null;

		/* リスト上、次のコンポーネントを取得する。 */
//		Component c = this.getTargetComponent(component, 1);

		/* 有効なコンポーネントのみを返す。 */
		/* 全てのコンポーネントが無効である場合を考慮し、1 ループしか有効状態の調査をしない。 */
		int count = this.componentList.size();

		Component c = component;
		for (int i = 0; i < count; i++) {
			c = this.getTargetComponent(c, 1);

			if (c.isEnabled()) {
				if (c instanceof JTextComponent) {
					if (((JTextComponent)c).isEditable()) {
						ret = c;
						break;
					}
				}else if (c.getClass() == jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenTextControl.class){
					if (((ExtendedOpenTextControl)c).isEnabled()) {
						ret = c;
						break;
					}
				}
				else {
					ret = c;
					break;
				}
			}
		}

		return ret;
	}

	/**
	 * 前のコンポーネントを取得する。
	 */
	@Override
	public Component getComponentBefore(Container container, Component component) {

		Component ret = null;

		/* リスト上、次のコンポーネントを取得する。 */
//		Component c = this.getTargetComponent(component, -1);

		/* 有効なコンポーネントのみを返す。 */
		/* 全てのコンポーネントが無効である場合を考慮し、1 ループしか有効状態の調査をしない。 */
		int count = this.componentList.size();

		Component c = component;

		try {
				for (int i = 0; i < count; i++) {
					c = this.getTargetComponent(c, -1);

					/* Modified 2008/05/01 若月  */
					/* --------------------------------------------------- */
		//			if (c.isEnabled()) {
		//				ret = c;
		//				break;
		//			}
					/* --------------------------------------------------- */
					if (c.isEnabled()) {
						if (c instanceof JTextComponent) {
						if (((JTextComponent)c).isEditable()) {
							ret = c;
							break;
						}
					}
				}else if (c.getClass() == jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenTextControl.class){
					if (((ExtendedOpenTextControl)c).isEnabled()) {
						ret = c;
						break;
					}
					else {
						ret = c;
						break;
					}
				}
				/* --------------------------------------------------- */
			}
		}catch (Exception ex){
			System.out.println(ex.getMessage());
		}
		return ret;

//		int index = ( this.componentList.indexOf(component) - 1 + this.componentList.size() ) % this.componentList.size();
//
//		return this.componentList.get(index);
	}

	/**
	 * リスト上、相対順序を指定してコンポーネントを取得する。
	 */
	private Component getTargetComponent(Component component, int count) {
		int size = this.componentList.size();

		int index = this.componentList.indexOf(component) + count;
		index %= this.componentList.size();

		while(index < 0){
			index += size;
		}

		Component c = this.componentList.get(index);
		return c;
	}

	@Override
	public Component getDefaultComponent(Container container) {
		return this.defaultComponent;
	}

	@Override
	public Component getFirstComponent(Container container) {
		return this.componentList.get(0);
	}

	@Override
	public Component getLastComponent(Container container) {
		return this.componentList.get(this.componentList.size() - 1);
	}

	public void setDefaultComponent(Component defaultComponent) {
		this.defaultComponent = defaultComponent;
	}
}
