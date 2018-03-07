//package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.dataimport;
//
//import javax.swing.JFrame;
//
//import com.sun.corba.se.impl.orbutil.closure.Constant;
//
//import java.awt.Component;
//import java.awt.event.*;
//import java.util.Vector;
//
//import jp.or.med.orca.jma_tokutei.common.focus.JFocusTraversalPolicy;
//import jp.or.med.orca.jma_tokutei.common.table.JSimpleTable;
//import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableCellPosition;
//import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableScrollPanel;
//import javax.swing.table.DefaultTableCellRenderer;
//import javax.swing.table.TableColumnModel;
//
//import org.apache.log4j.Logger;
//
//import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableCellRowRenderer;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.JHokenjyaMasterMaintenanceEditFrameCtrl;
//
///**
// * 検査結果データ取り込み時に、受診者が複数見つかったときに選択させるためのフレーム。
// */
//public class JSelectKojinFrameCtrl extends JSelectKojinFrame
//{
//	private JSimpleTable model = new JSimpleTable();
//	private Vector<JSelectKojinFrameData> KojinData;
//	private JSelectKojinFrameData SelectedData = null;  //  @jve:decl-index=0:
//	private JFocusTraversalPolicy focusTraversalPolicy = null;
//	private static org.apache.log4j.Logger logger =	Logger.getLogger(JSelectKojinFrameCtrl.class);
//
//	// edit s.inoue 2010/09/06 constractor変更
//	public JSelectKojinFrameCtrl(Vector<JSelectKojinFrameData> Data,JFrame ParentFrame,String flameTitle)
//	{
//		/* ed 2008/07/23 井上 */
//		//super(ParentFrame);
//		super(flameTitle);
//
//		initilizeSettings(Data);
//	}
//
//	// add s.inoue 2010/09/06
//	public JSelectKojinFrameCtrl(Vector<JSelectKojinFrameData> Data,String flameTitle)
//	{
//		/* ed 2008/07/23 井上 */
//		//super(ParentFrame);
//		super(flameTitle);
//
//		initilizeSettings(Data);
//	}
//
//	/* 初期化処理 */
//	private void initilizeSettings(Vector<JSelectKojinFrameData> Data){
//
//		/* Edit 2008/07/23 井上 */
//		/* --------------------------------------------------- */
//		/* 列幅の設定 */
//
//		// edit ver2 s.inoue 2009/08/19
//		//model.setPreferedColumnWidths(new int[]{120, 120, 120, 120, 120, 55});
//		model.setPreferedColumnWidths(new int[] { 100, 100, 150, 150, 80, 50, 120, 100, 120, 120, 180 });
//		// テーブルのヘッダの追加
//		JSimpleTableScrollPanel scroll = new JSimpleTableScrollPanel(model);
//		jPanel_Table.add(scroll);
//
//		// edit ver2 s.inoue 2009/08/19
//		model.addHeader("受診者紐付けID");
//		model.addHeader("受診券整理番号");
//		model.addHeader("氏名(漢字)");
//		model.addHeader("氏名(カナ)");
//		model.addHeader("生年月日");
//		model.addHeader("性別");
//		model.addHeader("住所");
//		model.addHeader("被保険者証記号");
//		model.addHeader("被保険者証番号");
//
//		// edit s.inoue 2009/10/23
//		this.focusTraversalPolicy = new JFocusTraversalPolicy();
//		this.setFocusTraversalPolicy(this.focusTraversalPolicy);
//		this.focusTraversalPolicy.setDefaultComponent(this.model);
//		this.focusTraversalPolicy.addComponent(this.model);
//		this.focusTraversalPolicy.addComponent(this.jButton_Select);
//		this.focusTraversalPolicy.addComponent(this.jButton_End);
//		// add s.inoue 2010/01/15
//		functionListner();
//		for(int i = 0 ; i < Data.size() ; i++)
//		{
//			JSelectKojinFrameData SelectKojinFrameData = Data.get(i);
//			/* ed 2008/07/23 井上 */
//			// String[] AddItem = {SelectKojinFrameData.kanaShimei,SelectKojinFrameData.HiHokenjyasyoKigo,
//			// 		SelectKojinFrameData.HiHokenjyasyoNumber};
//			String[] AddItem = {
//					SelectKojinFrameData.nayoseNo,
//					SelectKojinFrameData.jusinSeiNo,
//					SelectKojinFrameData.name,SelectKojinFrameData.kanaShimei,
//					SelectKojinFrameData.seiNenGapi,
//					(SelectKojinFrameData.seiBetu.equals("1")) ? "男性" : "女性",
//					// edit ver2 s.inoue 2009/08/19
//					SelectKojinFrameData.jyusho,
//					SelectKojinFrameData.hihokenshaKigo,
//					SelectKojinFrameData.hihokenshaNo};
//
//			model.addData(AddItem);
//		}
//		/* --------------------------------------------------- */
//
//		jp.or.med.orca.jma_tokutei.common.table.JSimpleTableCellPosition iSetColumnList[] = {new JSimpleTableCellPosition(-1,-1)};
//		model.setCellEditForbid(iSetColumnList);
//
//		KojinData = Data;
//
//		// エンターキーの処理
//		jButton_End.addKeyListener(new jp.or.med.orca.jma_tokutei.common.event.JEnterEvent(this));
//		jButton_Select.addKeyListener(new jp.or.med.orca.jma_tokutei.common.event.JEnterEvent(this));
//		model.addKeyListener(new jp.or.med.orca.jma_tokutei.common.event.JEnterEvent(this,jButton_Select));
//
//		// ダブルクリックの処理
//		model.addMouseListener(new jp.or.med.orca.jma_tokutei.common.event.JSingleDoubleClickEvent(this,jButton_Select));
//
//		// add ver2 s.inoue 2009/05/08
//		TableColumnModel columns = model.getColumnModel();
//        for(int i=0;i<columns.getColumnCount();i++) {
//
//            columns.getColumn(i).setCellRenderer(new JSimpleTableCellRowRenderer(
//                (DefaultTableCellRenderer)model.getDefaultRenderer(model.getColumnClass(i))));
//        }
//
//        // edit s.inoue 2009/11/09
//    	// 初期選択
//		if (model.getRowCount() > 0) {
//			model.setRowSelectionInterval(0, 0);
//		} else {
//			jButton_End.requestFocus();
//		}
//		setVisible(true);
//	}
//
//	// add s.inoue 2010/01/15
//	private void functionListner(){
//		for (int i = 0; i < focusTraversalPolicy.getComponentSize(); i++) {
//			Component comp = focusTraversalPolicy.getComponent(i);
//			comp.addKeyListener(this);
//		}
//	}
//	/**
//	 * 終了ボタン
//	 */
//	public void pushedEndButton( ActionEvent e )
//	{
//		SelectedData = null;
//		dispose();
//	}
//
//	/**
//	 * 選択ボタン
//	 */
//	public void pushedSelectButton( ActionEvent e )
//	{
//		if(model.getSelectedRow() < 0)
//		{
//			return;
//		}
//
//		SelectedData = KojinData.get(model.getSelectedRow());
//		dispose();
//	}
//
//	/**
//	 * @return nullだった場合、何も選ばなかった。
//	 */
//	public JSelectKojinFrameData GetSelectedData()
//	{
//		return SelectedData;
//	}
//
//	public void actionPerformed( ActionEvent e )
//	{
//		if( e.getSource() == jButton_End )
//		{
//			logger.info(jButton_End.getText());
//			pushedEndButton( e );
//		}
//
//		if( e.getSource() == jButton_Select )
//		{
//			logger.info(jButton_Select.getText());
//			pushedSelectButton( e );
//		}
//	}
//
//	// edit s.inoue 2010/01/15
//	@Override
//	public void keyPressed(KeyEvent keyEvent) {
//		switch(keyEvent.getKeyCode()){
//		case KeyEvent.VK_F1:
//			logger.info(jButton_End.getText());
//			pushedEndButton(null);break;
//		case KeyEvent.VK_F12:
//			logger.info(jButton_Select.getText());
//			pushedSelectButton(null);break;
//		}
//	}
//
//}
//
//
////Source Code Version Tag System v1.00  -- Do not remove --
////Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}