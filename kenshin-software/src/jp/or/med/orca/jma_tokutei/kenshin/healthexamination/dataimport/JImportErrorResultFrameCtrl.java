package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.dataimport;

import javax.swing.JFrame;
import java.awt.event.*;
import java.util.Vector;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.table.JSimpleTable;
import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableCellPosition;
import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableScrollPanel;

/**
 * edit s.inoue アンマッチエラー一覧画面 
 * 検査結果データ取り込み時に、受診者が複数見つかったときに選択させるためのフレーム。
 */
public class JImportErrorResultFrameCtrl extends JImportErrorResultFrame
{
	
	private JSimpleTable model = new JSimpleTable();
	private Vector<JImportErrorResultFrameData> KojinData;
	private JImportErrorResultFrameData SelectedData = null;  //  @jve:decl-index=0:
	
	public JImportErrorResultFrameCtrl(Vector<JImportErrorResultFrameData> Data,JFrame ParentFrame)
	{
		super();
		
		/* 列幅の設定 */
		// edit s.inoue 20080924
		//model.setPreferedColumnWidths(new int[]{55, 120, 120, 120, 120, 120, 120, 120, 55, 110, 110, 130, 55, 110 ,120, 160});
		model.setPreferedColumnWidths(new int[]{55, 120, 120, 120, 120, 120, 120, 120, 55, 110, 110, 130, 55,160});
		
		// テーブルのヘッダの追加
		JSimpleTableScrollPanel scroll = new JSimpleTableScrollPanel(model);
		jPanel_Table.add(scroll);
		// oms s.inoue
		// model.addHeader("");
		model.addHeader("行番号");
		model.addHeader("エラー項目名");
		model.addHeader("エラー種別");
		model.addHeader("健診実施機関番号");
		model.addHeader("受診券整理番号");
		model.addHeader("検査実施年月日");
		model.addHeader("氏名");
		model.addHeader("生年月日");
		model.addHeader("性別");
		model.addHeader("乳び・溶血");
		model.addHeader("食前/食後");
		model.addHeader("検査項目コード");
		model.addHeader("実施区分");
		// edit s.inoue 20080924
		//model.addHeader("異常区分");
		//model.addHeader("結果値形態");
		model.addHeader("結果値");
		
		String prerowNo ="";
		String errDigit ="";
		
		for(int i = 0 ; i < Data.size() ; i++)
		{
			JImportErrorResultFrameData ErrorResultFrameData = Data.get(i);
			
			switch(ErrorResultFrameData.errCase){
				case 0: errDigit ="型不正";break;
				case 1: errDigit ="日付不正";break;
				case 2: errDigit ="桁数不正";break;
				case 3: errDigit ="氏名不一致";break;
				case 4: errDigit ="誕生日不一致";break;
				case 5: errDigit ="性別不一致";break;			
				case 6: errDigit ="未存在";break;
				case 7: errDigit ="キー無";break;
				case 8: errDigit ="キー重複";break;
				case 9: errDigit ="データ属性不正";break;
				case 10: errDigit ="必須項目不正";break;
				case 11: errDigit ="その他";break;
				case 12: errDigit ="データ値不正";break;
				default:
			}
			
			// 行でマージする
			if (String.valueOf(ErrorResultFrameData.rowNo).equals(prerowNo)){
				//同行のエラーの場合、表示しない
				//int rowCnt = model.getRowCount();
				//String errCase=model.getData(rowCnt, 3).toString() + "," + errDigit;
				//model.setData(errCase, rowCnt, 3);
				
			}else{
				
				String[] AddItem = {String.valueOf(ErrorResultFrameData.rowNo),
						ErrorResultFrameData.errField,errDigit,
						ErrorResultFrameData.jisiKikanNo,ErrorResultFrameData.jusinSeiriNo,
						ErrorResultFrameData.jishiDate,ErrorResultFrameData.kanaShimei,
						ErrorResultFrameData.seiNenGapi,ErrorResultFrameData.seiBetu,
						ErrorResultFrameData.nyuBi,ErrorResultFrameData.shokuZenGo,
						ErrorResultFrameData.koumokuCd,ErrorResultFrameData.jisiKbn,
						// edit s.inoue 20080924
						//ErrorResultFrameData.ijyoKbn,ErrorResultFrameData.kekkaTiKeitai,
						ErrorResultFrameData.kekkaTi};
	
				model.addData(AddItem);
				prerowNo = String.valueOf(ErrorResultFrameData.rowNo);
			}
		}
		
		// oms s.inoue
		//		JSimpleTableCellPosition[] forbitCells = {
		//				new JSimpleTableCellPosition(-1,1),
		//				new JSimpleTableCellPosition(-1,2),
		//				new JSimpleTableCellPosition(-1,3),
		//				new JSimpleTableCellPosition(-1,4),
		//				new JSimpleTableCellPosition(-1,5),
		//				new JSimpleTableCellPosition(-1,6),
		//				new JSimpleTableCellPosition(-1,7),
		//				new JSimpleTableCellPosition(-1,8),
		//				new JSimpleTableCellPosition(-1,9),
		//				new JSimpleTableCellPosition(-1,10),
		//				new JSimpleTableCellPosition(-1,11),
		//				new JSimpleTableCellPosition(-1,12),				
		//				};
		// model.setCellEditForbid(forbitCells);
		jp.or.med.orca.jma_tokutei.common.table.JSimpleTableCellPosition iSetColumnList[] = {new JSimpleTableCellPosition(-1,-1)};
		model.setCellEditForbid(iSetColumnList);
		
		// oms s.inoue
		// model.setCellCheckBoxEditor(new JCheckBox(), 0);
		
		KojinData = Data;
		
		// エンターキーの処理
		jButton_End.addKeyListener(new jp.or.med.orca.jma_tokutei.common.event.JEnterEvent(this));
		// del s.inoue
		//jButton_Select.addKeyListener(new jp.or.med.orca.jma_tokutei.common.event.JEnterEvent(this));
		model.addKeyListener(new jp.or.med.orca.jma_tokutei.common.event.JEnterEvent(this,jButton_Select));
		
		// ダブルクリックの処理
		// del s.inoue
		//model.addMouseListener(new jp.or.med.orca.jma_tokutei.common.event.JDoubleClickEvent(this,jButton_Select));
		
		setVisible(true);
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
	 * ed s.inoue
	 * 選択ボタン→強制取込ボタン
	 */
	public void pushedSelectButton( ActionEvent e )
	{
		if(model.getSelectedRow() < 0)
		{
			return;
		}
		
		SelectedData = KojinData.get(model.getSelectedRow());
		dispose();
	}
	
	/**
	 * @return nullだった場合、何も選ばなかった。
	 */
	public JImportErrorResultFrameData GetSelectedData()
	{
		return SelectedData;
	}

	public void actionPerformed( ActionEvent e )
	{
		if( e.getSource() == jButton_End )
		{
			pushedEndButton( e );
		}
		
		if( e.getSource() == jButton_Select )
		{
			pushedSelectButton( e );
		}
	}
}  //  @jve:decl-index=0:visual-constraint="9,12"


//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}


