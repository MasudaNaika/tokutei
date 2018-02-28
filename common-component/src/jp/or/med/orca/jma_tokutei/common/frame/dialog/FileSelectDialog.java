package jp.or.med.orca.jma_tokutei.common.frame.dialog;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedButton;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedEditorPane;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedLabel;
import jp.or.med.orca.jma_tokutei.common.component.TitleLabel;
import jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode;
import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
import jp.or.med.orca.jma_tokutei.common.filectrl.DirectoryChooser;
import jp.or.med.orca.jma_tokutei.common.focus.JFocusTraversalPolicy;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import jp.or.med.orca.jma_tokutei.common.validate.JValidate;

// add s.inoue 2010/02/26
/**
 * ファイル選択ダイアログ画面
 */
public class FileSelectDialog extends JDialog
	implements ActionListener, KeyListener,ItemListener, IDialog {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	private JPanel jPanelNaviArea = null;
	private JPanel jPanelButtonArea = null;

	protected ExtendedButton jButtonOK = null;
	protected ExtendedButton jButtonCancel = null;
	private ExtendedLabel jLabelTitle = null;
	private String dialogTitle = null;  //  @jve:decl-index=0:
	protected RETURN_VALUE ReturnValue;  //  @jve:decl-index=0:
	protected int returnPageSelect;
	private JPanel jPanel = null;
	private JPanel JPanelPrint = null;
	private JPanel jPanel_FileInput = null;
	// edit s.inoue 2010/03/25
//	private ExtendedTextField jTextField_FileName = null;
	private ExtendedEditorPane jTextField_FileName = null;

	private ExtendedButton jButton_OpenFile = null;
	private ExtendedLabel jLabel_FileName = null;

	private Font defaultFont;
	private int fileDialog = 0;
	private ExtendedLabel jLabel_pageSelect = null;
    private static org.apache.log4j.Logger logger = Logger.getLogger(FileSelectDialog.class);

	/* フォーカス移動制御 */
	private JFocusTraversalPolicy focusTraversalPolicy = null;  //  @jve:decl-index=0:
	// edit s.inoue 2010/05/10
	private static String strFolderName = "<html>フォルダ名</html>";

	/**
	 * @param owner
	 */
	public FileSelectDialog(Frame owner,String defalutPath) {
		super(owner);
		defaultFont = ViewSettings.getCommonUserInputFont();
		initialize();
		this.setLocationRelativeTo(null);
		this.jTextField_FileName.setText(defalutPath);
	}

	private void initialize() {
		this.setTitle(ViewSettings.getTokuteFrameTitleWithKikanInfomation());
		this.setSize(376, 193);
		this.setResizable(false);
		this.setContentPane(getJContentPane());
		this.jButtonOK.grabFocus();

		focusInitialize();
		functionListner();

		setModal(true);
	}

	private void focusInitialize(){
		// focus制御
		this.focusTraversalPolicy = new JFocusTraversalPolicy();
		this.setFocusTraversalPolicy(this.focusTraversalPolicy);
		this.focusTraversalPolicy.setDefaultComponent(jButton_OpenFile);
		this.focusTraversalPolicy.addComponent(jButton_OpenFile);
		this.focusTraversalPolicy.addComponent(jButtonOK);
		this.focusTraversalPolicy.addComponent(jButtonCancel);
	}

	private void functionListner(){
		for (int i = 0; i < focusTraversalPolicy.getComponentSize(); i++) {
			Component comp = focusTraversalPolicy.getComponent(i);
			comp.addKeyListener(this);
		}
	}

	/**
	 * This method initializes jContentPane
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJPanelNaviArea(), BorderLayout.NORTH);
			jContentPane.add(getJPanelButtonArea(), BorderLayout.SOUTH);
			jContentPane.add(getJPanel(),BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanelNaviArea
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelNaviArea() {
		if (jPanelNaviArea == null) {
			CardLayout cardLayout = new CardLayout();
			cardLayout.setHgap(5);
			cardLayout.setVgap(5);
			jPanelNaviArea = new JPanel();
			jPanelNaviArea.setLayout(cardLayout);
			jLabelTitle = new TitleLabel("");
			jLabelTitle.setText("");
			jLabelTitle.setName("jLabelTitle");
			jPanelNaviArea.add(jLabelTitle, jLabelTitle.getName());
		}
		return jPanelNaviArea;
	}

	/**
	 * This method initializes jPanelButtonArea
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelButtonArea() {
		if (jPanelButtonArea == null) {
			GridBagConstraints gridBagConstraintsOkBtn = new GridBagConstraints();
			gridBagConstraintsOkBtn.gridx = 1;
			gridBagConstraintsOkBtn.gridy = 0;
			gridBagConstraintsOkBtn.fill = GridBagConstraints.BOTH;

			GridBagConstraints gridBagConstraintsCancelBtn = new GridBagConstraints();
			gridBagConstraintsCancelBtn.gridx = 2;
			gridBagConstraintsCancelBtn.gridy = 0;
			gridBagConstraintsCancelBtn.insets = new Insets(0,5,0,0);

			jPanelButtonArea = new JPanel();
			jPanelButtonArea.setLayout(new GridBagLayout());
			jPanelButtonArea.add(getJButtonOK(), gridBagConstraintsOkBtn);
			jPanelButtonArea.add(getJButtonCancel(), gridBagConstraintsCancelBtn);
		}
		return jPanelButtonArea;
	}

	private JPanel getJPanel() {
		if (jPanel == null) {
			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
			gridBagConstraints9.fill = GridBagConstraints.BOTH;
			gridBagConstraints9.gridy = 1;
			gridBagConstraints9.weightx = 1.0D;
			gridBagConstraints9.gridx = 1;
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.gridy = 1;
			gridBagConstraints6.anchor = GridBagConstraints.WEST;
			gridBagConstraints6.insets = new Insets(5, 0, 0, 0);
			gridBagConstraints6.gridx = 0;
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.anchor = GridBagConstraints.WEST;
			gridBagConstraints5.gridx = 1;
			gridBagConstraints5.gridy = 0;
			gridBagConstraints5.fill = GridBagConstraints.BOTH;
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.gridx = 0;
			gridBagConstraints7.gridy = 0;
			gridBagConstraints7.anchor = GridBagConstraints.WEST;
			gridBagConstraints7.insets = new Insets(5, 0, 0, 0);
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.gridx = 1;
			gridBagConstraints8.gridy = 0;
			gridBagConstraints8.anchor = GridBagConstraints.WEST;
			gridBagConstraints8.insets = new Insets(5, 0, 0, 0);

			jLabel_FileName = new ExtendedLabel();
			jLabel_FileName.setText("ファイル名");

			jPanel = new JPanel();
			jPanel.setLayout(new GridBagLayout());
			jPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.gray, 1), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
//			jPanel.add(getJPanel_Format(), gridBagConstraints5);
			jPanel.add(getJPanel_FileInput(), gridBagConstraints9);
			jPanel.add(jLabel_FileName, gridBagConstraints6);
		}
		return jPanel;
	}

	/**
	 * This method initializes jPanel_FileInput
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_FileInput() {
		if (jPanel_FileInput == null) {
			GridBagConstraints gridBagConstraints15 = new GridBagConstraints();
			gridBagConstraints15.insets = new Insets(0, 5, 0, 0);
			gridBagConstraints15.gridy = 0;
			gridBagConstraints15.weightx = 1.0D;
			gridBagConstraints15.anchor = GridBagConstraints.WEST;
			gridBagConstraints15.gridx = 1;
			GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
			gridBagConstraints14.gridx = 0;
			gridBagConstraints14.gridy = 0;
			gridBagConstraints14.insets = new Insets(5, 0, 0, 0);
			gridBagConstraints14.anchor = GridBagConstraints.WEST;
			jPanel_FileInput = new JPanel();
			jPanel_FileInput.setLayout(new GridBagLayout());
			jPanel_FileInput.add(getJTextField_FileName(), gridBagConstraints14);
			jPanel_FileInput.add(getJButton_OpenFile(), gridBagConstraints15);
		}
		return jPanel_FileInput;
	}

	/**
	 * This method initializes jButtonOK
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButtonOK() {
		if (jButtonOK == null) {
			jButtonOK = new ExtendedButton();
			jButtonOK.setText("OK(Y)");
			jButtonOK.setActionCommand("終了");
			jButtonOK.addActionListener(this);
			jButtonOK.setMnemonic(KeyEvent.VK_Y);
		}
		return jButtonOK;
	}

	/**
	 * This method initializes jButtonCancel
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButtonCancel() {
		if (jButtonCancel == null) {
			jButtonCancel = new ExtendedButton();
			jButtonCancel.setText("キャンセル[C]");
			jButtonCancel.addActionListener(this);
			jButtonCancel.setMnemonic(KeyEvent.VK_C);
		}
		return jButtonCancel;
	}

//	public void keyPressed(KeyEvent keyEvent) {
//		switch(keyEvent.getKeyCode()){
//		case KeyEvent.VK_R:
//			pushedOpenFileButton(null);break;
//		case KeyEvent.VK_Y:
//			ReturnValue = RETURN_VALUE.YES;
//			this.setVisible(false);break;
//		case KeyEvent.VK_C:
//			ReturnValue = RETURN_VALUE.CANCEL;
//			this.setVisible(false);break;
//		}
//
//	}
	public void keyReleased(KeyEvent arg0) {
	}
	public void keyTyped(KeyEvent arg0) {
	}

	/**
	 * 戻り値を取得する
	 * @return 戻り値
	 */
	public RETURN_VALUE getStatus() {
		return ReturnValue;
	}

	/**
	 * 戻り値を取得する
	 * @return 戻り値
	 */
	public Integer getPrintSelect() {
		return returnPageSelect;
	}

	/**
	 * 戻り値を格納
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == jButtonOK) {
			ReturnValue = RETURN_VALUE.YES;
		}
		else if(e.getSource() == jButtonCancel) {
			ReturnValue = RETURN_VALUE.CANCEL;
		}

		if(e.getSource() == jButton_OpenFile) {
			pushedOpenFileButton(e);
		}else{
			// モーダルダイアログの制御解除。
			setVisible(false);
		}
	}

	/**
	 * 参照ボタン
	 */
	public void pushedOpenFileButton(ActionEvent e)
	{
		// edit s.inoue 2010/05/10
		if (this.jLabel_FileName.getText().equals(strFolderName)){
			File files =	new File(JPath.getDesktopPath());
			DirectoryChooser dir = new DirectoryChooser(this,this.dialogTitle,files);
			dir.selectDirectory(files);
			dir.setLocationRelativeTo(null);
			dir.setVisible(true);
			if(dir.getState().equals(RETURN_VALUE.OK))
			{
				jTextField_FileName.setText(dir.getDirectoryPath());
			}
		}else{
			// edit s.inoue 2010/05/10
			FileDialog fd = new FileDialog(this,this.dialogTitle,this.fileDialog);
			fd.setVisible(true);

			if(fd.getDirectory() != null && fd.getFile() != null)
			{
				jTextField_FileName.setText(fd.getDirectory() + fd.getFile());
			}
		}
	}

	public void setMessageTitle(String messageTitle) {
		jLabelTitle.setText(messageTitle);
	}

	public void setDialogTitle(String dialogTitle) {
		this.dialogTitle = dialogTitle;
	}
	/**
	 * This method initializes jButton_OpenFile
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_OpenFile() {
		if (jButton_OpenFile == null) {
			jButton_OpenFile = new ExtendedButton();
			jButton_OpenFile.setText("参照[R]");
			jButton_OpenFile.setPreferredSize(new Dimension(80, 25));
//			jButton_OpenFile.setFont(new Font("Dialog", Font.PLAIN, 12));
			jButton_OpenFile.addActionListener(this);
			jButton_OpenFile.setMnemonic(KeyEvent.VK_R);
		}
		return jButton_OpenFile;
	}

	/**
	 * This method initializes jTextField_FileName
	 *
	 * @return javax.swing.ExtendedTextField
	 */
	private ExtendedEditorPane getJTextField_FileName() {
		if (jTextField_FileName == null) {
			jTextField_FileName = new ExtendedEditorPane();
			jTextField_FileName.setPreferredSize(new Dimension(200,75));
		}
		return jTextField_FileName;
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public String getKenshinDate() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public void setText(String text) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void setShowCancelButton(boolean isShowCancel) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public String getFilePath() {
		return this.jTextField_FileName.getText();
	}

	@Override
	public void setEnabled(boolean enabled) {
		this.jTextField_FileName.setEditable(enabled);
	}

	@Override
	public void setDialogSelect(boolean enabled) {
		if (enabled){
			this.fileDialog = FileDialog.LOAD;
		}else{
			this.fileDialog = FileDialog.SAVE;
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	// add s.inoue 2010/04/26
	@Override
	public void setSaveFileName(String title) {
		// TODO 自動生成されたメソッド・スタブ
		this.jLabel_FileName.setText(title);
	}

	@Override
	public String getK_PNo() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public String getK_PName() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public String getK_PNote() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public Vector getDataSelect() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
