package jp.or.med.orca.jma_tokutei.common.errormessage;

import java.awt.Component;
import java.awt.event.*;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTextArea;

import jp.or.med.orca.jma_tokutei.common.focus.JFocusTraversalPolicy;

public class JErrorMessageDialogFrameCtrl extends JErrorMessageDialogFrame {
	/**
	 * エラーダイアログを表示する
	 * @param ParentFrame 親フレーム
	 * @param ErrorTitle タイトル
	 * @param ErrorMessage メッセージ本文
	 * @param ButtonType ボタン種別
	 * @param DefaultButton デフォルト選択ボタン
	 * @param ID メッセージID
	 */
	private static String LINE_SEPARATOR = System.getProperty("line.separator");
	private JFocusTraversalPolicy focusTraversalPolicy = null;
	public JErrorMessageDialogFrameCtrl(
			JFrame ParentFrame,
			String ErrorTitle,
			String ErrorMessage,
			int ButtonType,
			int DefaultButton,
			String ID) {

		super(ParentFrame);

		this.showDialog(ErrorTitle, ErrorMessage, ButtonType, DefaultButton, ID);
	}

	private void showDialog(
			String errorTitle,
			String errorMessage,
			int buttonType,
			int defaultButton,
			String ID) {

		jLabel_Title.setText(errorTitle);

		String displayMessage = errorMessage.replaceAll("\\[改行\\]", LINE_SEPARATOR);

		jTextArea_ErrorMessage.setText(displayMessage + LINE_SEPARATOR
				+ "(メッセージID：" + ID + ")");

		// add ver2 s.inoue 2009/04/15
		// スクロール場所初期値を設定
		jTextArea_ErrorMessage.setCaretPosition(0);

		switch (buttonType) {
		case 0:
			// OKボタンのみ
			jButton_OK.setVisible(true);
			jButton_Yes.setVisible(false);
			jButton_No.setVisible(false);
			jButton_Cancel.setVisible(false);
			jButton_ALL.setVisible(false);

			// del s.inoue 2009/12/21
			// if (defaultButton == 0) {
			// 	jButton_OK.setSelected(true);
			// }
			break;
		case 1:
			// はい、いいえボタン
			jButton_OK.setVisible(false);
			jButton_Yes.setVisible(true);
			jButton_No.setVisible(true);
			jButton_Cancel.setVisible(false);
			jButton_ALL.setVisible(false);

			if (defaultButton == 0) {
				jButton_Yes.setVisible(true);
				// del s.inoue 2009/12/21
				// jButton_Yes.setSelected(true);
			}
			if (defaultButton == 1) {
				jButton_No.setVisible(true);
			}

			break;
		case 2:
			// はい、いいえ、キャンセルボタン
			jButton_OK.setVisible(false);
			jButton_Yes.setVisible(true);
			jButton_No.setVisible(true);
			jButton_Cancel.setVisible(true);
			jButton_ALL.setVisible(false);

			if (defaultButton == 0) {
				jButton_Yes.setVisible(true);
				// del s.inoue 2009/12/21
				// jButton_Yes.setSelected(true);
			}
			if (defaultButton == 1) {
				jButton_No.setVisible(true);
			}
			if (defaultButton == 2) {
				jButton_Cancel.setVisible(true);
			}

			break;
		// add s.inoue 20080922
		case 3:
			// はい、いいえ、全て上書きボタン
			jButton_OK.setVisible(false);
			jButton_Yes.setVisible(true);
			jButton_No.setVisible(true);
			jButton_Cancel.setVisible(true);
			jButton_ALL.setVisible(true);

			if (defaultButton == 0) {
				jButton_Yes.setVisible(true);
				// del s.inoue 2009/12/21
				// jButton_Yes.setSelected(true);
			}
			if (defaultButton == 1) {
				jButton_No.setVisible(true);
			}
			if (defaultButton == 3) {
				jButton_ALL.setVisible(true);
			}

			break;
		// add s.inoue 2010/02/09
		case 4:
			// はい、いいえ、キャンセルボタン
			jButton_OK.setVisible(false);
			jButton_Yes.setVisible(true);
			jButton_No.setVisible(true);
			// edit s.inoue 2010/04/21
			jButton_Yes.setText("追加(A)");
			jButton_Yes.setMnemonic(KeyEvent.VK_A);
			jButton_No.setText("更新(E)");
			jButton_No.setMnemonic(KeyEvent.VK_E);

			jButton_Cancel.setVisible(true);
			jButton_ALL.setVisible(false);

			if (defaultButton == 0) {
				jButton_Yes.setVisible(true);
			}
			if (defaultButton == 1) {
				jButton_No.setVisible(true);
			}
			if (defaultButton == 2) {
				jButton_Cancel.setVisible(true);
			}

			break;
		}

		this.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyTyped(java.awt.event.KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					ReturnValue = RETURN_VALUE.CANCEL;
					JErrorMessageDialogFrameCtrl.this.setVisible(false);
				}
			}
		});

		// 真ん中に表示する
		setLocationRelativeTo(null);

		// モーダルダイアログになり、制御がここで止まる。
		setVisible(true);
	}

	public JErrorMessageDialogFrameCtrl(
			JFrame ParentFrame,
			String ErrorTitle,
			String ErrorMessage,
			Object[] messageParams,
			int ButtonType,
			int DefaultButton,
			String ID) {

		super(ParentFrame);

		String message = String.format(ErrorMessage, messageParams);

		this.showDialog(
				ErrorTitle,
				message,
				ButtonType,
				DefaultButton,
				ID
			);
	}


	/**
	 * 戻り値を取得する
	 * @return 戻り値
	 */
	public RETURN_VALUE getStatus() {
		return ReturnValue;
	}

	/**
	 * 戻り値を格納
	 */
	private RETURN_VALUE ReturnValue;

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		// edit s.inoue 2009/12/06
//		if (source == jButton_OK) {
//			ReturnValue = RETURN_VALUE.OK;
		if (source == jButton_Yes || source == jButton_OK) {
			ReturnValue = RETURN_VALUE.YES;
		} else if (source == jButton_No) {
			ReturnValue = RETURN_VALUE.NO;
		} else if (source == jButton_Cancel) {
			ReturnValue = RETURN_VALUE.CANCEL;
		} else if (source == jButton_ALL) {
			ReturnValue = RETURN_VALUE.ALL;
		}

		// モーダルダイアログの制御解除。
		setVisible(false);
	}
	// add s.inoue 2009/12/01
	@Override
	public void keyPressed(KeyEvent keyEvent) {
		switch(keyEvent.getKeyCode()){
//		case KeyEvent.VK_Y:
//			ReturnValue = RETURN_VALUE.OK;break;
		// edit s.inoue 2009/12/15

		case KeyEvent.VK_ENTER:
			if(jButton_ALL.isFocusOwner()){
				ReturnValue = RETURN_VALUE.ALL;setVisible(false);break;
			}else if(jButton_Cancel.isFocusOwner()){
				ReturnValue = RETURN_VALUE.CANCEL;setVisible(false);break;
			}else if(jButton_No.isFocusOwner()){
				ReturnValue = RETURN_VALUE.NO;setVisible(false);break;
			}else if(jButton_Yes.isFocusOwner() ||
					jButton_OK.isFocusOwner()){
				ReturnValue = RETURN_VALUE.YES;setVisible(false);break;
			}
			// edit s.inoue 2010/04/21
//		case KeyEvent.VK_Y:
//			ReturnValue = RETURN_VALUE.YES;break;
//		case KeyEvent.VK_N:
//			ReturnValue = RETURN_VALUE.NO;break;
//		case KeyEvent.VK_C:
//			ReturnValue = RETURN_VALUE.CANCEL;break;
//		case KeyEvent.VK_A:
//			ReturnValue = RETURN_VALUE.ALL;break;
		}
		// モーダルダイアログの制御解除。
		// setVisible(false);
	}
	// add s.inoue 2009/12/07
	// マウスリスナのメソッドを定義
	@Override
	public void mousePressed(MouseEvent e) {
		mousePopup(e);
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		mousePopup(e);
	}

	// add s.inoue 2009/11/11
	private void mousePopup(MouseEvent e) {
		if (e.isPopupTrigger()) {
			// ポップアップメニューを表示する
			JComponent c = (JComponent)e.getSource();
			jTextArea_ErrorMessage.showPopup(c, e.getX(), e.getY());
			e.consume();
		}
	}

}

//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

