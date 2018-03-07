package jp.or.med.orca.jma_tokutei.common.scene;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import jp.or.med.orca.jma_tokutei.common.app.JPath;

/**
 * 遷移の管理やダイアログの表示を行うクラス。
 */
public class JScene  {

	/**
	 * フレームの遷移を行う。
	 * @param NextFrame 遷移先のフレーム
	 */
	public static void ChangeScene(JFrame NextFrame)
	{
		if(mCurrentForm != null)
		{
			mCurrentForm.setVisible(false);
			mCurrentForm.dispose();
		}

		NextFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		NextFrame.setVisible(true);
		// add s.inoue 2012/07/06
        java.awt.Image image = (new ImageIcon(JPath.Ico_Common_METABO)).getImage();
        NextFrame.setIconImage(image);

		mCurrentForm = NextFrame;
	}

	// openswing s.inoue 2011/02/09
	/**
	 * フレームの遷移を行う。(openswing用カスタマイズ遷移)
	 * @param NextFrame 遷移先のフレーム
	 */
	public static void ChangeScene(JFrame grid,JFrame NextFrame,WindowListener Window)
	{
		if(mCurrentForm != null)
		{
			mCurrentForm.setVisible(false);
//			mCurrentForm.dispose();
		}

		NextFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		NextFrame.setVisible(true);
		// add s.inoue 2012/07/06
        java.awt.Image image = (new ImageIcon(JPath.Ico_Common_METABO)).getImage();
        NextFrame.setIconImage(image);

		// 呼び出し先を常に上
        // del s.inoue 2013/02/13
		// NextFrame.setAlwaysOnTop(true);

		// 呼び出し元を非活性
		grid.setEnabled(false);

		NextFrame.addWindowListener(Window);
		mCurrentForm = NextFrame;
	}

	/**
	 * フレームの遷移を行う。(openswing用カスタマイズ遷移)
	 * @param NextFrame 遷移先のフレーム
	 */
	public static void ChangeScene(JFrame grid,JFrame NextFrame)
	{
		if(mCurrentForm != null)
		{
			mCurrentForm.setVisible(false);
//			mCurrentForm.dispose();
		}

		NextFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		NextFrame.setVisible(true);
		// add s.inoue 2012/07/06
        java.awt.Image image = (new ImageIcon(JPath.Ico_Common_METABO)).getImage();
        NextFrame.setIconImage(image);

		// 呼び出し先を常に上
		NextFrame.setAlwaysOnTop(true);

		// 呼び出し元を非活性
		grid.setEnabled(false);

		mCurrentForm = NextFrame;
	}

	/**
	 * モーダルダイアログを作成する。
	 * @param ParentFrame 作成元のダイアログ。
	 * @param NewFrame 新しいダイアログ。
	 */
	public static void CreateDialog(JFrame ParentFrame,JFrame NewFrame)
	{
		ParentFrame.setEnabled(false);
		NewFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		NewFrame.setVisible(true);
		// add s.inoue 2012/07/06
        java.awt.Image image = (new ImageIcon(JPath.Ico_Common_METABO)).getImage();
        NewFrame.setIconImage(image);

		NewFrame.addWindowListener(new JModalDialogWindowAdapter(ParentFrame));
	}

	/**
	 * モーダルダイアログを作成する。
	 * @param ParentFrame 作成元のダイアログ。
	 * @param NewFrame 新しいダイアログ。
	 * @param Window モーダルダイアログの状態を通知するWindow。
	 */
	public static void CreateDialog(JFrame ParentFrame,JFrame NewFrame,WindowListener Window)
	{
		ParentFrame.setEnabled(false);
		NewFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		NewFrame.setVisible(true);
		// add s.inoue 2012/07/06
        java.awt.Image image = (new ImageIcon(JPath.Ico_Common_METABO)).getImage();
        NewFrame.setIconImage(image);

		NewFrame.addWindowListener(new JModalDialogWindowAdapter(ParentFrame));
		NewFrame.addWindowListener(Window);
	}

	private static JFrame mCurrentForm = null;
}

//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

