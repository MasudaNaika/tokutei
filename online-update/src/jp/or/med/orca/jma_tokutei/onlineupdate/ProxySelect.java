package jp.or.med.orca.jma_tokutei.onlineupdate;
import javax.swing.*;
import java .awt  .*;
import java .awt  .event.*;

import jp.or.med.orca.jma_tokutei.common.component.ExtendedButton;
import jp.or.med.orca.jma_tokutei.onlineupdate.util.*;
import org.apache.log4j.Logger;

/**
 *  ProxySelect
 *
 *   プロキシサーバ設定画面
 */
public class ProxySelect  extends JDialog implements ActionListener {

    private static final long serialVersionUID = 1L;

    private static org.apache.log4j.Logger logger = Logger
    .getLogger(ProxySelect.class);

	JTextField m_guiServer = new JTextField( 25 );
	JTextField m_guiPort   = new JTextField( 5  );

	/**
	 *  コンストラクタ
	 *
	 *    @param  none
	 *
	 *    @return none
	 * @throws Exception
	 */
	public ProxySelect( JFrame frame ) throws Exception
	{
		super( frame, true );

		try{

			setTitle("プロキシサーバ設定");

			// 実行ボタン
			// eidt s.inoue 2011/12/19 F12 → A,キャンセル → C
			ExtendedButton commit = new ExtendedButton("適用(A)");
			commit.setActionCommand ("commit");
			commit.addActionListener(this);
			commit.setMnemonic(KeyEvent.VK_A);

			// 作成ボタン
			ExtendedButton cancel = new ExtendedButton("キャンセル(C)");
			cancel.setActionCommand ("cancel");
			cancel.addActionListener(this);
			cancel.setMnemonic(KeyEvent.VK_C);

			// フォント設定
			commit.setFont( new Font("Dialog", Font.PLAIN, 12) );
			cancel.setFont( new Font("Dialog", Font.PLAIN, 12) );

			// add s.inoue 2009/12/03
//			m_guiServer.addKeyListener(this);
//			m_guiPort.addKeyListener(this);
//			commit.addKeyListener(this);
//			cancel.addKeyListener(this);

			// フレームパネル作成
			JPanel panelText1  = new JPanel( new FlowLayout(FlowLayout.LEFT ) );
			JPanel panelText2  = new JPanel( new FlowLayout(FlowLayout.LEFT ) );
			JPanel panelButton = new JPanel( new FlowLayout(FlowLayout.RIGHT) );

			JLabel label1 = new JLabel("設定アドレス");

			panelText1.add( label1 );
			panelText1.add( m_guiServer );

			JLabel label2 = new JLabel("設定ポート　");

			panelText2.add( label2 );
			panelText2.add( m_guiPort   );

			label1.setFont( new Font("Dialog", Font.PLAIN, 12) );
			label2.setFont( new Font("Dialog", Font.PLAIN, 12) );

			// 配置換え
			panelButton.add(commit);
			panelButton.add(cancel);

			// eidt s.inoue 2011/12/19
			// getContentPane().setLayout( new GridLayout(3, 1) );
			getContentPane().setLayout(new GridBagLayout());

			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.fill = GridBagConstraints.BOTH;
			gridBagConstraints1.gridy = 0;
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.weightx = 1.0D;

			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.fill = GridBagConstraints.BOTH;
			gridBagConstraints2.gridy = 1;
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.weightx = 1.0D;

			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.fill = GridBagConstraints.BOTH;
			gridBagConstraints3.gridy = 2;
			gridBagConstraints3.gridx = 0;
			gridBagConstraints3.weightx = 1.0D;

			getContentPane().add( panelText1,gridBagConstraints1);
			getContentPane().add( panelText2,gridBagConstraints2);
			getContentPane().add( panelButton,gridBagConstraints3);

			m_guiServer.setText( PropertyUtil.getProperty("http.proxyHost") );
			m_guiPort  .setText( PropertyUtil.getProperty("http.proxyPort") );

			// フレーム作成
			setSize( new Dimension( 380, 150 ) );

			setLocationRelativeTo( null );

			setVisible           ( true );
		}catch(Exception err){
			logger.error("プロキシサーバ設定初期処理に失敗しました");
			throw err;
		}
	}

	/**
	 *  アクションイベント
	 *
	 *    @param  none
	 *
	 *    @return none
	 */
	public void actionPerformed( ActionEvent e )
	{
		if( "commit".equals( e.getActionCommand() ) )
		{
			applySettings();
		}

		dispose();
	}

	// add s.inoue 2009/12/03
	private void applySettings(){
		PropertyUtil.setProperty( "http.proxyHost", m_guiServer.getText().trim() );
		PropertyUtil.setProperty( "http.proxyPort", m_guiPort  .getText().trim() );

		HttpConnecter.setProxyHost( m_guiServer.getText().trim() );
		HttpConnecter.setProxyPort( m_guiPort  .getText().trim() );
	}

//	// add s.inoue 2009/12/03
//	@Override
//	public void keyPressed(KeyEvent keyEvent) {
//		try {
//			switch(keyEvent.getKeyCode()){
//				case KeyEvent.VK_F3:
//					dispose();break;
//				case KeyEvent.VK_F12:
//					applySettings();dispose();break;
//			}
//			}catch (Exception err){
//				logger.error(err.getMessage());
//			}
//
//	}

//	@Override
//	public void keyReleased(KeyEvent e) {
//		// TODO 自動生成されたメソッド・スタブ
//
//	}
//
//	@Override
//	public void keyTyped(KeyEvent e) {
//		// TODO 自動生成されたメソッド・スタブ
//
//	}
}
