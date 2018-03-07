package jp.or.med.orca.jma_tokutei.common.table;

import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JPanel;

import jp.or.med.orca.jma_tokutei.common.tree.JSimpleTree;

/**
 * スクロールするテーブルを配置したパネル
 *
 *　ソースコードの視認性向上のため、改行位置などを修正。
 *　処理内容の変更は無し。 　
 */
public class JSimpleTableScrollPanel extends JPanel
{
	/**
	 *  コンストラクタ
	 *
	 *    @param  設定テーブル
	 *
	 *    @return none
	 */
	public JSimpleTableScrollPanel( JSimpleTable table )
	{
		this.setLayout( new BorderLayout() );

		// パネル生成
		JScrollPane scrollPane = new JScrollPane(
					JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
				);

		// パネル設定
		this.add( scrollPane, BorderLayout.CENTER );

		// 描画オブジェクト設定
		scrollPane.setViewportView( table );
	}

	// add s.inoue 2012/07/04
    public JSimpleTableScrollPanel(JSimpleColorTable jsimplecolortable)
    {
        setLayout(new BorderLayout());
        JScrollPane jscrollpane = new JScrollPane(20, 30);
        add(jscrollpane, "Center");
        jscrollpane.setViewportView(jsimplecolortable);
    }
}

//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

