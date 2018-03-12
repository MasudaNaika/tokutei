package jp.or.med.orca.jma_tokutei.kenshin.admin.frame;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableColumnModel;

import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedButton;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedImageIcon;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;


/**
 * 健診機関の一覧表示クラス
 * ※DB接続情報メンテナンスで、複数の健診機関が日レセ連携している場合、この画面で一覧を表示し選択させる
 */
public class JKikanListDialog extends JDialog {
	private static final long serialVersionUID = -550558357451615057L;

	//テーブルの列名
	private static final String[] COLUMN_NAMES = {"健診機関番号", "健診機関名称", "日医標準レセプトソフト連携"};

	//選択ボタン
	private ExtendedButton jButton_select;

	//画面全体
	private JPanel jPanel_content = null;
	//ボタン表示エリア
	private JPanel jPanel_buttonArea = null;
	//一覧表示エリア
	private JPanel jPanel_listArea = null;
	
	//一覧表示用テーブル
	private JTable table = null;
	
	//一覧表示データ
	private String[][] listData = null;
	
	//選択した健診機関番号を返却する
	private String[] resultValues = null;
	public String[] getResultVal() {
		return resultValues;
	}
	
	/**
	 * コンストラクタ
	 * 
	 * @param listData	一覧に表示する健診機関のデータ（"健診機関番号","健診機関名称","日医標準レセプトソフト連携"の2次元配列）
	 */
	public JKikanListDialog(String[][] listData) {
		
		//一応チェック
		if ((listData == null) || (listData.length == 0)){
			this.listData = new String[0][0];
		} else {
			this.listData = listData;
		}
		
		//画面初期表示処理
		initialize();
	}
	

	/**
	 * 画面の初期表示
	 */
	private void initialize() {
		
		//画面サイズの設定
		Dimension size = ViewSettings.getFrameCommonSize();
		this.setSize(size);
		this.setPreferredSize(size);
		this.setMinimumSize(size);
		this.setModal(true);
		
		//表示位置の設定
		this.setLocationRelativeTo(null);

		//ウィンドウの枠に表示する値（バージョン情報等）を設定
		String title = ViewSettings.getTokuteFrameTitleWithKikanInfomation();
		if (title == null || title.isEmpty()) {
			title  = ViewSettings.getAdminCommonTitleWithVersion();
		}
		this.setTitle(title);
		
		//画面のベースを設定
		setContentPane(getJPanel_Content());
		
		//ボタンエリアを設定
		jPanel_content.add(getJPanel_ButtonArea(), BorderLayout.NORTH);
		
		//一覧エリアを設定
		jPanel_content.add(getJPanel_listArea(), BorderLayout.CENTER);
		
		
		//表示
		this.setVisible(true);
	}
	
	/**
	 * This method initializes jPanel_Content
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_Content() {
		if (jPanel_content == null) {
			BorderLayout borderLayout = new BorderLayout();
			borderLayout.setVgap(2);

			jPanel_content = new JPanel();
			jPanel_content.setLayout(borderLayout);
		}
		return jPanel_content;
	}
	
	/**
	 * This method initializes jPanel_ButtonArea
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_ButtonArea() {
		
		if (jPanel_buttonArea == null) {
			
			jPanel_buttonArea = new JPanel();
			
			//レイアウト
			GridBagLayout layout = new GridBagLayout();
			jPanel_buttonArea.setLayout(layout);
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.anchor = GridBagConstraints.WEST;
			
			//表示位置
			int gridX = 0;	//x座標
			int gridY = 0;	//y座標
			
			//選択ボタン
			jButton_select = getJButton_select();
			gridBagConstraints.weightx = 1.0d;
			gridBagConstraints.insets = new Insets(10, 10, 0, 0);
			setGridBagConstraints(layout, gridBagConstraints, gridX++, gridY, jButton_select);
			jPanel_buttonArea.add(jButton_select);
			
		}
		return jPanel_buttonArea;
	}
	
	
	private ExtendedButton getJButton_select() {
		if (jButton_select == null) {
			ExtendedImageIcon extendedimageicon = new ExtendedImageIcon(JPath.Ico_Common_Select);
			javax.swing.ImageIcon imageicon = extendedimageicon.setStrechIcon(this, 0.80000000000000004D);
			jButton_select = new ExtendedButton("Select", "選択(S)", "選択(ALT+S)", KeyEvent.VK_S, imageicon);
			
			//リストの内容が無い場合、押下不可にする
			if (listData.length == 0) {
				jButton_select.setEnabled(false);
			}
			
			jButton_select.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					pushedBtnSelect();
				}
			});
		}
		return jButton_select;
	}
	
	/**
	 * 選択ボタン押下処理
	 */
	private void pushedBtnSelect() {
//		System.out.println("選択データ:[" + listData[table.getSelectedRow()][0] + "][" + listData[table.getSelectedRow()][1] + "][" + listData[table.getSelectedRow()][2] + "]");
		resultValues = new String[3];
		resultValues[0] = listData[table.getSelectedRow()][0];
		resultValues[1] = listData[table.getSelectedRow()][1];
		resultValues[2] = listData[table.getSelectedRow()][2];
		dispose();
	}

	/**
	 * 情報設定画面
	 * 
	 * @return
	 */
	private JPanel getJPanel_listArea() {
		if (jPanel_listArea == null) {
		
			jPanel_listArea = new JPanel();
			
			//表示する一覧
			table = new JTable(listData, COLUMN_NAMES) {
				private static final long serialVersionUID = 5014173363615825539L;
				@Override
				public boolean isCellEditable(int row, int column) {
					//セル内の編集は不可
					return false;
				}
			};
			
			//ダブルクリックでも"選択"ボタンと同様の動き
			table.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					super.mouseClicked(e);
					if (e.getClickCount() == 2) {
//						System.out.println("ダブルクリック");
						pushedBtnSelect();
					}
				}
			});
			
			//複数行の選択は不可
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

			//一番上の行を選択状態にする
			table.changeSelection(0, 0, false, false);

			//行の高さ
			table.setRowHeight(20);
			
			//列の幅
			DefaultTableColumnModel columnModel = (DefaultTableColumnModel)table.getColumnModel();
			columnModel.getColumn(0).setPreferredWidth(280);	//健診機関番号
			columnModel.getColumn(1).setPreferredWidth(500);	//健診機関名称
			columnModel.getColumn(2).setPreferredWidth(190);	//日医標準レセプトソフト連携
			
			//列幅の変更は不可
			table.getTableHeader().setResizingAllowed(false);
			
			//列の順番変更は不可
			table.getTableHeader().setReorderingAllowed(false);
			
			//スクロール有り
			JScrollPane jScrollPane = new JScrollPane(table);
			jScrollPane.setPreferredSize(new Dimension(970, 550));
			
			//レイアウト
			GridBagLayout layout = new GridBagLayout();
			jPanel_listArea.setLayout(layout);
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			
			//表示位置
			int gridX = 0;	//x座標
			int gridY = 0;	//y座標
			
			gridBagConstraints.insets = new Insets(-5, 0, 0, 0);
			setGridBagConstraints(layout, gridBagConstraints, gridX++, gridY, jScrollPane);
			jPanel_listArea.add(jScrollPane);
		}
		return jPanel_listArea;
	}
	
	/**
	 * GridBagLayoutの簡易設定（x座標とy座標を++して渡せば、画面上の表示通りに設定できる）
	 * 
	 * @param layout
	 * @param gridBagConstraints
	 * @param x
	 * @param y
	 * @param insets
	 */
	private void setGridBagConstraints(GridBagLayout layout, GridBagConstraints gridBagConstraints, int x, int y, Component component) {
		gridBagConstraints.gridx = x;
		gridBagConstraints.gridy = y;
		layout.setConstraints(component, gridBagConstraints);
	}
}
