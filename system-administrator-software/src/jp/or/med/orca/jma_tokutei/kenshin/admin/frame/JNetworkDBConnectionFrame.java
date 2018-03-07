package jp.or.med.orca.jma_tokutei.kenshin.admin.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedButton;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedImageIcon;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedLabel;
import jp.or.med.orca.jma_tokutei.common.component.TitleLabel;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenTextControl;

import org.apache.log4j.Logger;

/**
 * DB情報接続画面のViewクラス
 */
public class JNetworkDBConnectionFrame extends JFrame {
	private static final long serialVersionUID = 8106105722854070685L;
	private static Logger logger = Logger.getLogger(JNetworkDBConnectionFrame.class);

	//GridBagConstraintsに設定する余白
	private static final Insets INSETS_BLANK_BOTTOM = new Insets(0, 0, 30, 10);	//下にブランク　＋　右にブランク1文字分
	private static final Insets INSETS_BLANK_BOTTOM_AND_LEFT = new Insets(0, 20, 30, 10);	//下にブランク　＋　右にブランク1文字分　＋　左にブランク2文字分
	private static final Insets INSETS_BLANK_NOTHING = new Insets(0, 0, 0, 0);	//ブランク無し
	
	//テキストフィールドのサイズ
	private static final Dimension DEFAULT_SIZE = new Dimension(200, 20);
	
	//ビジネスロジック
	private JNetworkDBConnectionFrameCtrl networkDBConnectionFrameCtrl = null;
	//画面情報格納Bean
	private JNetworkDBConnectionFrameData bean = null;
	
	
	//戻るボタン
	private ExtendedButton jButton_End = null;
	//登録ボタン
	private ExtendedButton jButton_Register = null;
	//接続テストボタン
	private ExtendedButton jButton_ConnectTestFDB = null;
	//参照ボタン
	private ExtendedButton jButton_Browse = null;


	//画面全体
	private JPanel jPanel_Content = null;
	//ボタン表示エリア
	private JPanel jPanel_ButtonArea = null;
	//設定画面の表示エリア
	private JPanel jPanel_InfoArea = null;
	//FDBの表示エリア
	private JPanel jPanel_info_fdb = null;

	//画面のタイトル
	private JPanel jPanel_TitleArea = null;
	
	//画面の項目（接続先DB情報）
	private ExtendedLabel jLabel_fdbFolderPath = null;	//FDBフォルダのパスラベル
	private ExtendedOpenTextControl jTextField_fdbFolderPath = null;	//FDBフォルダのパステキストボックス

	private ExtendedLabel jLabel_fdbIPAddress = null;	//FDBのホスト名 or IPアドレスラベル
	private ExtendedOpenTextControl jTextField_fdbIPAddress = null;	//FDBのホスト名 or IPアドレステキストボックス
	private ExtendedLabel jLabel_fdbIPAddressInputExplanation = null;	//FDBのホスト名 or IPアドレステキストボックス入力値説明

	private ExtendedLabel jLabel_fdbPortNumber = null;	//FDBのポート番号ラベル
	private ExtendedOpenTextControl jTextField_fdbPortNumber = null;	//FDBのポート番号テキストボックス
	private ExtendedLabel jLabel_fdbPortNumberInputExplanation = null;	//FDBのポート番号テキストボックス入力値説明

	private ExtendedLabel jLabel_fdbUserName = null;	//FDBのユーザ名ラベル
	private ExtendedOpenTextControl jTextField_fdbUserName = null;	//FDBのユーザ名テキストボックス
	private ExtendedLabel jLabel_fdbUserNameInputExplanation = null;	//FDBのユーザ名テキストボックス入力値説明

	private ExtendedLabel jLabel_fdbUserPass = null;	//FDBのパスワードラベル
	private ExtendedOpenTextControl jTextField_fdbUserPass = null;	//FDBのパスワードテキストボックス
	private ExtendedLabel jLabel_fdbUserPassInputExplanation = null;	//FDBのパスワードテキストボックス入力値説明

	/**
	 * コンストラクタ
	 */
	public JNetworkDBConnectionFrame() {
		
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
		jPanel_Content.add(getJPanel_ButtonArea(), BorderLayout.NORTH);
		
		//情報設定エリアを設定
		jPanel_Content.add(getJPanel_InfoArea(), BorderLayout.CENTER);
		
		//初期フォーカスの設定
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e){
				jTextField_fdbFolderPath.requestFocus();
			}
		});
		
		//ビジネスロジック
		networkDBConnectionFrameCtrl = new JNetworkDBConnectionFrameCtrl();
		//表示情報の取得
		bean = networkDBConnectionFrameCtrl.getFrameData();
		
		//表示エリアのデータを設定
		setData();
	}

	/**
	 * This method initializes jPanel_Content
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_Content() {
		if (jPanel_Content == null) {
			BorderLayout borderLayout = new BorderLayout();
			borderLayout.setVgap(2);

			jPanel_Content = new JPanel();
			jPanel_Content.setLayout(borderLayout);
		}
		return jPanel_Content;
	}
	

	/**
	 * This method initializes jPanel_ButtonArea
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_ButtonArea() {
		
		if (jPanel_ButtonArea == null) {
			
			jPanel_ButtonArea = new JPanel();
			
			//レイアウト
			GridBagLayout layout = new GridBagLayout();
			jPanel_ButtonArea.setLayout(layout);
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.anchor = GridBagConstraints.WEST;
			
			//表示位置
			int gridX = 0;	//x座標
			int gridY = 0;	//y座標
			
			//タイトル
			jPanel_TitleArea = getJPanel_TitleArea();
			gridBagConstraints.insets = new Insets(0, 0, 5, 0);
			gridBagConstraints.gridwidth = 2;
			setGridBagConstraints(layout, gridBagConstraints, gridX++, gridY, jPanel_TitleArea);
			jPanel_ButtonArea.add(jPanel_TitleArea);

			gridX = 0;	//横座標を初期化
			gridY++;	//縦座標を次へ
			gridBagConstraints.gridwidth = 1;	//戻す
			
			//戻るボタン
			jButton_End = getJButton_End();
			gridBagConstraints.insets = new Insets(0, 5, 0, 5);
			setGridBagConstraints(layout, gridBagConstraints, gridX++, gridY, jButton_End);
			jPanel_ButtonArea.add(jButton_End);

			//登録ボタン
			jButton_Register = getJButton_Register();
			gridBagConstraints.weightx = 1.0d;
			setGridBagConstraints(layout, gridBagConstraints, gridX++, gridY, jButton_Register);
			jPanel_ButtonArea.add(jButton_Register);
		}
		return jPanel_ButtonArea;
	}
	
	/**
	 * 登録ボタン
	 */
	private ExtendedButton getJButton_Register() {
		if (jButton_Register == null) {
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Register);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);
			jButton_Register= new ExtendedButton("Save", "登録(S)", "登録(ALT+S)", KeyEvent.VK_S, icon);
			jButton_Register.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					logger.info(jButton_Register.getText());
					
					//確認メッセージ
					RETURN_VALUE retVal = JErrorMessage.show("M10106", JNetworkDBConnectionFrame.this);
					if(retVal == RETURN_VALUE.YES) {
						
						//画面情報をBeanへ設定
						setBean();
						
						//登録処理
						boolean isRegisterOK = networkDBConnectionFrameCtrl.register(bean);
						if (isRegisterOK) {
							JErrorMessage.show("M10107", JNetworkDBConnectionFrame.this);
							dispose();
						}
					}
				}
			});
		}
		return jButton_Register;
	}
	
	/**
	 * 戻るボタン
	 */
	private ExtendedButton getJButton_End() {
		if (jButton_End == null) {
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Back);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);
			jButton_End= new ExtendedButton("Return", "戻る(R)", "戻る(ALT+R)", KeyEvent.VK_R, icon);
			jButton_End.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					logger.info(jButton_End.getText());
					dispose();
				}
			});
		}
		return jButton_End;
	}
	
	/**
	 * 接続テストボタン
	 */
	private ExtendedButton getJButton_ConnectTestFDB() {
		if (jButton_ConnectTestFDB == null) {
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Register);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);
			jButton_ConnectTestFDB= new ExtendedButton("Test","接続テスト(T)", "接続テスト(ALT+T)", KeyEvent.VK_T, icon);
			jButton_ConnectTestFDB.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					logger.info(jButton_ConnectTestFDB.getText());
					
					//接続先DBの接続テスト実行
					boolean isConnectOK = networkDBConnectionFrameCtrl.connectTestFDB(
														jTextField_fdbUserName.getText(),
														jTextField_fdbUserPass.getText(),
														jTextField_fdbFolderPath.getText(),
														jTextField_fdbIPAddress.getText(),
														jTextField_fdbPortNumber.getText()
					);
					if (isConnectOK) {
						JErrorMessage.show("M10101", JNetworkDBConnectionFrame.this);
					} else {
						JErrorMessage.show("M10102", JNetworkDBConnectionFrame.this);
					}
				}
			});
		}
		return jButton_ConnectTestFDB;
	}
	
	/**
	 * 参照ボタン
	 */
	private ExtendedButton getJButton_Browse() {
		if (jButton_Browse == null) {
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Kekka_InputJusinken);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);
			jButton_Browse= new ExtendedButton("Reference","参照(O)", "参照(ALT+O)", KeyEvent.VK_O, icon);
			jButton_Browse.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					logger.info(jButton_Browse.getText());

					//現在の画面情報をBeanへ設定
					setBean();

					JFileChooser fileChooser = new JFileChooser(bean.getFdbFolderPath());
					fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					fileChooser.setDialogTitle("DBフォルダを指定してください");
					
					int selected = fileChooser.showOpenDialog(JNetworkDBConnectionFrame.this);
					if (selected == JFileChooser.APPROVE_OPTION) {
						
						//選択した値でフォルダパスを上書き
						bean.setFdbFolderPath(fileChooser.getSelectedFile().getAbsolutePath());
						
						//画面の再表示
						setData();
					}
				}
			});
		}
		return jButton_Browse;
	}
	
	
	/**
	 * This method initializes jPanel_TitleArea
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_TitleArea() {
		if (jPanel_TitleArea == null) {
			
			jPanel_TitleArea = new JPanel();
			
			//レイアウト
			GridBagLayout layout = new GridBagLayout();
			jPanel_TitleArea.setLayout(layout);
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.anchor = GridBagConstraints.WEST;
			
			//表示位置
			int gridX = 0;	//x座標
			int gridY = 0;	//y座標
			
			//タイトル
			TitleLabel jLabel_Title = new TitleLabel("admin.network-db-connection-edit.frame.title", "admin.network-db-connection-edit.frame.guidance");
			jLabel_Title.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 14));
			gridBagConstraints.insets = new Insets(5, 5, 0, 0);
			gridBagConstraints.weightx = 1.0d;
			setGridBagConstraints(layout, gridBagConstraints, gridX++, gridY, jLabel_Title);
			jPanel_TitleArea.add(jLabel_Title);
		}
		return jPanel_TitleArea;
	}
	
	/**
	 * 情報設定画面
	 * 
	 * @return
	 */
	private JPanel getJPanel_InfoArea() {
		if (jPanel_InfoArea == null) {
		
			jPanel_InfoArea = new JPanel();

			GridBagLayout layout = new GridBagLayout();
			jPanel_InfoArea.setLayout(layout);
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;	//表示位置は左上
			gridBagConstraints.weighty = 1.0d;	//上積めで表示
			gridBagConstraints.weightx = 1.0d;	//左積めで表示
			
			//表示位置
			int gridX = 0;	//x座標
			int gridY = 0;	//y座標
			
			jPanel_info_fdb = getJPanel_Info_fdb();
			gridBagConstraints.insets = new Insets(5, 10, 0, 0);
			setGridBagConstraints(layout, gridBagConstraints, gridX++, gridY, jPanel_info_fdb);
			jPanel_InfoArea.add(jPanel_info_fdb);
		}
		return jPanel_InfoArea;
	}
	
	private JPanel getJPanel_Info_fdb() {
		if (jPanel_info_fdb == null) {
			
			jPanel_info_fdb = new JPanel();

			//設定画面内の一番外の枠線
			jPanel_info_fdb.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.gray, 1), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
			
			//レイアウト
			GridBagLayout layout = new GridBagLayout();
			jPanel_info_fdb.setLayout(layout);
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.anchor = GridBagConstraints.WEST;
			
			//表示位置
			int gridX = 0;	//x座標
			int gridY = 0;	//y座標
			
			//タイトル
			ExtendedLabel jLabel_fdbTitle = new ExtendedLabel();
			jLabel_fdbTitle.setOpaque(true);
			jLabel_fdbTitle.setText("接続先DB情報");
			Font font = jLabel_fdbTitle.getFont();
			Font font1 = new Font(font.getFontName(), 1, font.getSize());
			jLabel_fdbTitle.setFont(font1);
			setGridBagConstraints(layout, gridBagConstraints, gridX++, gridY, jLabel_fdbTitle);
			jPanel_info_fdb.add(jLabel_fdbTitle);

			gridX = 0;	//横座標を初期化
			gridY++;	//縦座標を次へ
			
			//FDBのインフォメーションの行
			ExtendedLabel jLabel_fdbInfo = new ExtendedLabel();
			jLabel_fdbInfo.setText("※全ての健診機関で同じ値を使用します");
			gridBagConstraints.gridwidth = 3;	//横3セル分使用
			gridBagConstraints.insets = INSETS_BLANK_BOTTOM;	//下ブランク
			setGridBagConstraints(layout, gridBagConstraints, gridX++, gridY, jLabel_fdbInfo);
			jPanel_info_fdb.add(jLabel_fdbInfo);

			gridX = 0;	//横座標を初期化
			gridY++;	//縦座標を次へ
			gridBagConstraints.gridwidth = 1;	//元に戻す
			
			//FDBフォルダのパスの行
			jLabel_fdbFolderPath = new ExtendedLabel();
			jLabel_fdbFolderPath.setText("FDBフォルダのパス");
			jLabel_fdbFolderPath.setForeground(ViewSettings.getRequiedItemFrColor());
			gridBagConstraints.insets = INSETS_BLANK_BOTTOM_AND_LEFT;	//2文字分インデント
			setGridBagConstraints(layout, gridBagConstraints, gridX++, gridY, jLabel_fdbFolderPath);
			jPanel_info_fdb.add(jLabel_fdbFolderPath);
			
			jTextField_fdbFolderPath = new ExtendedOpenTextControl();
			jTextField_fdbFolderPath.setPreferredSize(DEFAULT_SIZE);
			gridBagConstraints.insets = INSETS_BLANK_BOTTOM;	//戻す
			setGridBagConstraints(layout, gridBagConstraints, gridX++, gridY, jTextField_fdbFolderPath);
			jPanel_info_fdb.add(jTextField_fdbFolderPath);

			jButton_Browse = getJButton_Browse();
			gridBagConstraints.insets = new Insets(-30, 0, 0, 0);	//参照ボタンを真ん中に表示
			setGridBagConstraints(layout, gridBagConstraints, gridX++, gridY, jButton_Browse);
			jPanel_info_fdb.add(jButton_Browse);

			gridX = 0;	//横座標を初期化
			gridY++;	//縦座標を次へ

			//ホスト名 or IPアドレスの行
			jLabel_fdbIPAddress = new ExtendedLabel();
			jLabel_fdbIPAddress.setText("FDBのホスト名 or IPアドレス");
			jLabel_fdbIPAddress.setForeground(ViewSettings.getRequiedItemFrColor());
			gridBagConstraints.insets = INSETS_BLANK_BOTTOM_AND_LEFT;	//2文字分インデント
			setGridBagConstraints(layout, gridBagConstraints, gridX++, gridY, jLabel_fdbIPAddress);
			jPanel_info_fdb.add(jLabel_fdbIPAddress);
			
			jTextField_fdbIPAddress = new ExtendedOpenTextControl();
			jTextField_fdbIPAddress.setPreferredSize(DEFAULT_SIZE);
			gridBagConstraints.insets = INSETS_BLANK_BOTTOM;	//戻す
			setGridBagConstraints(layout, gridBagConstraints, gridX++, gridY, jTextField_fdbIPAddress);
			jPanel_info_fdb.add(jTextField_fdbIPAddress);

			jLabel_fdbIPAddressInputExplanation = new ExtendedLabel();
			jLabel_fdbIPAddressInputExplanation.setText("（IPの場合IPv4形式）");
			setGridBagConstraints(layout, gridBagConstraints, gridX++, gridY, jLabel_fdbIPAddressInputExplanation);
			jPanel_info_fdb.add(jLabel_fdbIPAddressInputExplanation);

			gridX = 0;	//横座標を初期化
			gridY++;	//縦座標を次へ

			//FDBのポート番号の行
			jLabel_fdbPortNumber = new ExtendedLabel();
			jLabel_fdbPortNumber.setText("FDBのポート番号");
			jLabel_fdbPortNumber.setForeground(ViewSettings.getRequiedItemFrColor());
			gridBagConstraints.insets = INSETS_BLANK_BOTTOM_AND_LEFT;	//2文字分インデント
			setGridBagConstraints(layout, gridBagConstraints, gridX++, gridY, jLabel_fdbPortNumber);
			jPanel_info_fdb.add(jLabel_fdbPortNumber);
			
			jTextField_fdbPortNumber = new ExtendedOpenTextControl();
			jTextField_fdbPortNumber.setPreferredSize(DEFAULT_SIZE);
			jTextField_fdbPortNumber.setMaxCharacters(5);
			gridBagConstraints.insets = INSETS_BLANK_BOTTOM;	//戻す
			setGridBagConstraints(layout, gridBagConstraints, gridX++, gridY, jTextField_fdbPortNumber);
			jPanel_info_fdb.add(jTextField_fdbPortNumber);

			jLabel_fdbPortNumberInputExplanation = new ExtendedLabel();
			jLabel_fdbPortNumberInputExplanation.setText("（半角数字5桁以下）");
			setGridBagConstraints(layout, gridBagConstraints, gridX++, gridY, jLabel_fdbPortNumberInputExplanation);
			jPanel_info_fdb.add(jLabel_fdbPortNumberInputExplanation);

			gridX = 0;	//横座標を初期化
			gridY++;	//縦座標を次へ
			
			//FDBのユーザ名の行
			jLabel_fdbUserName = new ExtendedLabel();
			jLabel_fdbUserName.setText("FDBのユーザ名");
			jLabel_fdbUserName.setForeground(ViewSettings.getRequiedItemFrColor());
			gridBagConstraints.insets = INSETS_BLANK_BOTTOM_AND_LEFT;	//2文字分インデント
			setGridBagConstraints(layout, gridBagConstraints, gridX++, gridY, jLabel_fdbUserName);
			jPanel_info_fdb.add(jLabel_fdbUserName);
			
			jTextField_fdbUserName = new ExtendedOpenTextControl();
			jTextField_fdbUserName.setPreferredSize(DEFAULT_SIZE);
			gridBagConstraints.insets = INSETS_BLANK_BOTTOM;	//戻す
			setGridBagConstraints(layout, gridBagConstraints, gridX++, gridY, jTextField_fdbUserName);
			jPanel_info_fdb.add(jTextField_fdbUserName);
			
			jLabel_fdbUserNameInputExplanation = new ExtendedLabel();
			jLabel_fdbUserNameInputExplanation.setText("（半角英数字のみ）");
			setGridBagConstraints(layout, gridBagConstraints, gridX++, gridY, jLabel_fdbUserNameInputExplanation);
			jPanel_info_fdb.add(jLabel_fdbUserNameInputExplanation);
			
			gridX = 0;	//横座標を初期化
			gridY++;	//縦座標を次へ

			//FDBのパスワードの行
			jLabel_fdbUserPass = new ExtendedLabel();
			jLabel_fdbUserPass.setText("FDBのパスワード");
			jLabel_fdbUserPass.setForeground(ViewSettings.getRequiedItemFrColor());
			gridBagConstraints.insets = INSETS_BLANK_BOTTOM_AND_LEFT;	//2文字分インデント
			setGridBagConstraints(layout, gridBagConstraints, gridX++, gridY, jLabel_fdbUserPass);
			jPanel_info_fdb.add(jLabel_fdbUserPass);
			
			jTextField_fdbUserPass = new ExtendedOpenTextControl();
			jTextField_fdbUserPass.setPreferredSize(DEFAULT_SIZE);
			gridBagConstraints.insets = INSETS_BLANK_BOTTOM;	//戻す
			setGridBagConstraints(layout, gridBagConstraints, gridX++, gridY, jTextField_fdbUserPass);
			jPanel_info_fdb.add(jTextField_fdbUserPass);

			jLabel_fdbUserPassInputExplanation = new ExtendedLabel();
			jLabel_fdbUserPassInputExplanation.setText("（半角英数字のみ）");
			setGridBagConstraints(layout, gridBagConstraints, gridX++, gridY, jLabel_fdbUserPassInputExplanation);
			jPanel_info_fdb.add(jLabel_fdbUserPassInputExplanation);
			
			gridX = 0;	//横座標を初期化
			gridY++;	//縦座標を次へ
			gridBagConstraints.insets = INSETS_BLANK_NOTHING;	//ブランクの解除

			//接続テストボタンの行
			gridX++;
			gridX++;
			jButton_ConnectTestFDB = getJButton_ConnectTestFDB();
			gridBagConstraints.anchor = GridBagConstraints.EAST;
			setGridBagConstraints(layout, gridBagConstraints, gridX++, gridY, jButton_ConnectTestFDB);
			jPanel_info_fdb.add(jButton_ConnectTestFDB);
		}
		return jPanel_info_fdb;
	}
	
	
	/**
	 * GridBagLayoutの簡易設定（x座標とy座標を++して渡せば、画面上の表示通りに設定できる）
	 * 
	 * @param layout
	 * @param gridBagConstraints
	 * @param x
	 * @param y
	 * @param component
	 */
	private void setGridBagConstraints(GridBagLayout layout, GridBagConstraints gridBagConstraints, int x, int y, Component component) {
		gridBagConstraints.gridx = x;
		gridBagConstraints.gridy = y;
		layout.setConstraints(component, gridBagConstraints);
	}
	

	/**
	 * 画面に設定する情報を取得し、値を設定する
	 */
	private void setData() {
		
		jTextField_fdbUserName.setText(bean.getFdbUserName());
		jTextField_fdbUserPass.setText(bean.getFdbPass());
		jTextField_fdbFolderPath.setText(bean.getFdbFolderPath());
		jTextField_fdbIPAddress.setText(bean.getFdbIPAddress());
		jTextField_fdbPortNumber.setText(bean.getFdbPortNumber());
	}

	/**
	 * 画面情報をBeanへ設定する
	 */
	private void setBean() {
		
		bean.setFdbUserName(jTextField_fdbUserName.getText());
		bean.setFdbPass(jTextField_fdbUserPass.getText());
		bean.setFdbFolderPath(jTextField_fdbFolderPath.getText());
		bean.setFdbIPAddress(jTextField_fdbIPAddress.getText());
		bean.setFdbPortNumber(jTextField_fdbPortNumber.getText());
	}
}
