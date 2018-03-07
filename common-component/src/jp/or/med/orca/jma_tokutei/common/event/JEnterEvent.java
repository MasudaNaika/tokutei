package jp.or.med.orca.jma_tokutei.common.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JComponent;

/**
 * エンターボタンを押したときにActionListenerにイベントを通知するためのイベント
 * 想定している使用方法としては、ボタンのキーリスナーに追加して、エンターキーを
 * 押されたことを検知する。
 */
public class JEnterEvent extends KeyAdapter {
	private ActionListener ActionFrame;
	private JComponent TargetComponent;
	
	/**
	 * @param ActionFrame ActionListenerが登録されているフレーム
	 * @param TargetComponent エンターキーを押されたと通知するボタン
	 */
	public JEnterEvent(ActionListener ActionFrame, JComponent TargetComponent)
	{
		this.ActionFrame = ActionFrame;
		this.TargetComponent = TargetComponent;
	}
	
	/**
	 * エンターキーを押されたと通知するボタンについて、全てのものを対象として通知する。
	 * 想定している使用方法としては、ボタンのキーリスナーに追加して、エンターキーを
	 * 押されたことを検知する。
	 * ただし、このコンストラクタを呼び出した場合、ActionEventのソースオブジェクトとして
	 * イベントのソースオブジェクトをそのまま流すようにする。
	 * このため、ボタンのキーリスナーに追加された先がそのまま呼び出される。
	 * @param ActionFrame ActionListenerが登録されているフレーム
	 */
	public JEnterEvent(ActionListener ActionFrame)
	{
		this.ActionFrame = ActionFrame;
		TargetComponent = null;
	}
	
	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			if(TargetComponent == null)
			{
				ActionFrame.actionPerformed(new ActionEvent(e.getSource(),0,""));
			}else
			{
				ActionFrame.actionPerformed(new ActionEvent(TargetComponent,0,""));
			}
		}
	}
}



//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

