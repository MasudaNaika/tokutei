package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.select;

import javax.swing.JFrame;

import com.sun.corba.se.impl.orbutil.closure.Constant;

import java.awt.Component;
import java.awt.event.*;
import java.util.Vector;

import jp.or.med.orca.jma_tokutei.common.focus.JFocusTraversalPolicy;
import jp.or.med.orca.jma_tokutei.common.table.JSimpleTable;
import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableCellPosition;
import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableScrollPanel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import org.apache.log4j.Logger;
import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableCellRowRenderer;

/**
 * 検査結果データ取り込み時に、受診者が複数見つかったときに選択させるためのフレーム。
 */
public class JSelectHokenjyaFrameCtrl extends JSelectHokenjyaFrame
{
	private JSimpleTable model = new JSimpleTable();
	private Vector<JSelectHokenjyaFrameData> HokenjyaData;
	private JSelectHokenjyaFrameData SelectedData = null;  //  @jve:decl-index=0:
	private JFocusTraversalPolicy focusTraversalPolicy = null;
	private static org.apache.log4j.Logger logger =	Logger.getLogger(JSelectHokenjyaFrameCtrl.class);

	public JSelectHokenjyaFrameCtrl(Vector<JSelectHokenjyaFrameData> Data,JFrame ParentFrame,String flameTitle)
	{
		super(flameTitle);
		initilizeSettings(Data);
	}

	public JSelectHokenjyaFrameCtrl(Vector<JSelectHokenjyaFrameData> Data,String flameTitle)
	{
		super(flameTitle);
		initilizeSettings(Data);
	}

	/* 初期化処理 */
	private void initilizeSettings(Vector<JSelectHokenjyaFrameData> Data){

		/* 列幅の設定 */
		model.setPreferedColumnWidths(new int[] {100, 200, 100, 300, 150});
		// テーブルのヘッダの追加
		JSimpleTableScrollPanel scroll = new JSimpleTableScrollPanel(model);
		jPanel_Table.add(scroll);

		model.addHeader("保険者番号");
		model.addHeader("保険者名称");
		model.addHeader("郵便番号");
		model.addHeader("所在地");
		model.addHeader("電話番号");

		this.focusTraversalPolicy = new JFocusTraversalPolicy();
		this.setFocusTraversalPolicy(this.focusTraversalPolicy);
		this.focusTraversalPolicy.setDefaultComponent(this.model);
		this.focusTraversalPolicy.addComponent(this.model);
		this.focusTraversalPolicy.addComponent(this.jButton_Select);
		this.focusTraversalPolicy.addComponent(this.jButton_End);

		functionListner();
		for(int i = 0 ; i < Data.size() ; i++)
		{
			JSelectHokenjyaFrameData SelectHokenjyaFrameData = Data.get(i);
			String[] AddItem = {SelectHokenjyaFrameData.hknjanum,
					SelectHokenjyaFrameData.hknjaname,
					SelectHokenjyaFrameData.post,
					SelectHokenjyaFrameData.adress,
					SelectHokenjyaFrameData.tel};

			model.addData(AddItem);
		}

		jp.or.med.orca.jma_tokutei.common.table.JSimpleTableCellPosition iSetColumnList[] = {new JSimpleTableCellPosition(-1,-1)};
		model.setCellEditForbid(iSetColumnList);

		HokenjyaData = Data;

		// エンターキーの処理
		jButton_End.addKeyListener(new jp.or.med.orca.jma_tokutei.common.event.JEnterEvent(this));
		jButton_Select.addKeyListener(new jp.or.med.orca.jma_tokutei.common.event.JEnterEvent(this));
		model.addKeyListener(new jp.or.med.orca.jma_tokutei.common.event.JEnterEvent(this,jButton_Select));

		// ダブルクリックの処理
		model.addMouseListener(new jp.or.med.orca.jma_tokutei.common.event.JSingleDoubleClickEvent(this,jButton_Select));

		TableColumnModel columns = model.getColumnModel();
        for(int i=0;i<columns.getColumnCount();i++) {

            columns.getColumn(i).setCellRenderer(new JSimpleTableCellRowRenderer(
                (DefaultTableCellRenderer)model.getDefaultRenderer(model.getColumnClass(i))));
        }

    	// 初期選択
		if (model.getRowCount() > 0) {
			model.setRowSelectionInterval(0, 0);
		} else {
			jButton_End.requestFocus();
		}
		setVisible(true);
	}

	private void functionListner(){
		for (int i = 0; i < focusTraversalPolicy.getComponentSize(); i++) {
			Component comp = focusTraversalPolicy.getComponent(i);
			comp.addKeyListener(this);
		}
	}
	/**
	 * 終了ボタン
	 */
	public void pushedEndButton( ActionEvent e )
	{
		SelectedData = null;
		dispose();
	}

	/**
	 * 選択ボタン
	 */
	public void pushedSelectButton( ActionEvent e )
	{
		if(model.getSelectedRow() < 0)
		{
			return;
		}

		SelectedData = HokenjyaData.get(model.getSelectedRow());
		dispose();
	}

	/**
	 * @return nullだった場合、何も選ばなかった。
	 */
	public JSelectHokenjyaFrameData GetSelectedData()
	{
		return SelectedData;
	}

	public void actionPerformed( ActionEvent e )
	{
		if( e.getSource() == jButton_End )
		{
			logger.info(jButton_End.getText());
			pushedEndButton( e );
		}

		if( e.getSource() == jButton_Select )
		{
			logger.info(jButton_Select.getText());
			pushedSelectButton( e );
		}
	}

	// edit s.inoue 2010/01/15
	@Override
	public void keyPressed(KeyEvent keyEvent) {
		switch(keyEvent.getKeyCode()){
		case KeyEvent.VK_F1:
			logger.info(jButton_End.getText());
			pushedEndButton(null);break;
		case KeyEvent.VK_F12:
			logger.info(jButton_Select.getText());
			pushedSelectButton(null);break;
		}
	}

}
