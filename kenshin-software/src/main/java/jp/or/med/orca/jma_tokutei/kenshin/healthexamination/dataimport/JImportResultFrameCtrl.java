package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.dataimport;

import java.awt.event.ActionEvent;
import javax.swing.JFrame;

/**
 * 外部検査結果データ取り込み時に結果を表示するフレームのコントロール
 */
public class JImportResultFrameCtrl extends JImportResultFrame
{
	/**
	 * @param Result 結果
	 * @param Parent 呼び出しもとのフレーム
	 */
	public JImportResultFrameCtrl(String Result,JFrame Parent)
	{
		super(Parent);
		
		jTextArea_Message.setText(Result);
		
		// エンターキーの処理
		jButton_End.addKeyListener(new jp.or.med.orca.jma_tokutei.common.event.JEnterEvent(this));
		
		setVisible(true);
	}
	
	/**
	 * 終了ボタン
	 */
	public void pushedEndButton( ActionEvent e )
	{
		dispose();
	}
	
	public void actionPerformed( ActionEvent e )
	{
		if( e.getSource() == jButton_End )
		{
			pushedEndButton( e );
		}
	}
}



//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

