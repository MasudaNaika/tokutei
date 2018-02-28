package jp.or.med.orca.jma_tokutei.common.frame.dialog;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedButton;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedComboBox;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedImageIcon;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedLabel;
import jp.or.med.orca.jma_tokutei.common.component.TitleLabel;
import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
import jp.or.med.orca.jma_tokutei.common.event.JEnterEvent;
import jp.or.med.orca.jma_tokutei.common.focus.JFocusTraversalPolicy;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettingsKey;
import jp.or.med.orca.jma_tokutei.common.util.PropertyUtil;

import com.birosoft.liquid.LiquidLookAndFeel;
import com.jgoodies.looks.plastic.Plastic3DLookAndFeel;
import com.jgoodies.looks.windows.WindowsLookAndFeel;

/**
 * 環境設定ダイアログ画面
 */
public class SettingDialog extends JDialog
	implements ActionListener, KeyListener,ItemListener,IDialog {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JPanel jPanelNaviArea = null;
	private JPanel jPanel_MainArea = null;
	private JPanel jPanelButtonArea = null;
	private JPanel jPanel_LookFeel = null;
	protected ExtendedButton jButtonOK = null;
	protected ExtendedButton jButtonCancel = null;
	protected RETURN_VALUE ReturnValue;
	private Font guidanceFont;
	private Font defaultFont;
	private JPanel jPanelLabel = null;
	private JLabel jLabelTitle = null;
	private JLabel jLabelGuidance = null;
	private ExtendedLabel jLabelLookFeel = null;
	// add s.inoue 2009/11/25
	protected ExtendedComboBox jCombobox_LookFeel = null;

	// Possible Look & Feels
	private UIManager.LookAndFeelInfo looks[] = UIManager.getInstalledLookAndFeels();
    private static final String mac     = "com.sun.java.swing.plaf.mac.MacLookAndFeel";
    private static final String metal   = "javax.swing.plaf.metal.MetalLookAndFeel";
    private static final String windows = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
    private static final String gtk     = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
    private static final String nimbus  = "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";
    // add s.inoue 2010/11/05
    private static final String liquid  = "com.birosoft.liquid.LiquidLookAndFeel";  //  @jve:decl-index=0:
    private static final String goodiesWin = "com.jgoodies.looks.windows.WindowsLookAndFeel";  //  @jve:decl-index=0:
    private static final String goodies = "com.jgoodies.looks.plastic.Plastic3DLookAndFeel";  //  @jve:decl-index=0:

    // edit s.inoue 2010/06/04
    private static final String macStr       = "Mac";
    private static final String metalStr     = "Metal";
    private static final String motifStr     = "Motif";
    private static final String windowsStr   = "Windows";
    private static final String gtkStr       = "GTK+";
    private static final String liquidStr    = "Liquid";  //  @jve:decl-index=0:
    // add s.inoue 2010/11/05
    private static final String goodiesStr	 = "Goodies3D";
    private static final String goodiesWinStr	 = "GoodiesWindows";

    // font問題にて保留 s.inoue 2010/11/05
    // private static final String nimbusStr    = "Nimbus";
    // private static final String motif   = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";

	private LiquidLookAndFeel liguidLooks = null;
	private com.jgoodies.looks.plastic.Plastic3DLookAndFeel goodiesLooks = null;
	private com.jgoodies.looks.windows.WindowsLookAndFeel gooiesWindowLooks = null;

    private UIManager.LookAndFeelInfo looksinfo = null;
	/* フォーカス移動制御 */
	private JFocusTraversalPolicy focusTraversalPolicy = null;
	private boolean flgOS =false;

	// add s.inoue 2010/11/05
	public SettingDialog(){
		// add s.inoue 2013/03/09
		String osname = System.getProperty("os.name");
		if(osname.indexOf("Windows")>=0){
			flgOS = true;
		}
	}
	/**
	 * @param owner
	 */
	public SettingDialog(Frame owner) {
		super(owner);

		// add s.inoue 2013/03/09
		String osname = System.getProperty("os.name");
		if(osname.indexOf("Windows")>=0){
			flgOS = true;
		}

		initialize();
		initializecomboSetting();
	}

	// add s.inoue 2010/08/02
	private void initializeFrameTitle() {
		StringBuffer buffer = new StringBuffer();

		buffer.append(ViewSettings.getTokuteFrameTitle());
		buffer.append(" (Version ");
		buffer.append(JApplication.versionNumber);
		buffer.append(")");

		String title = buffer.toString();
		this.setTitle(title);
	}

	// String title, String guidance, Component component
	protected void initialize() {
		// edit s.inoue 2010/08/02
		// this.setTitle(ViewSettings.getTokuteFrameTitleWithKikanInfomation());
		initializeFrameTitle();

		this.setSize(420, 200);
		this.setContentPane(getJContentPane());
		// this.setText(ViewSettings.getTokuteFrameTitleWithKikanInfomation());
		this.setLocationRelativeTo( null );

		jButtonOK.addKeyListener(new JEnterEvent(this));
		jButtonCancel.addKeyListener(new JEnterEvent(this));
//		jLabelTitle.setText(title);
//		jLabelGuidance.setText(guidance);
//		jButtonOK.setSelected(true);
		setModal(true);
		this.focusTraversalPolicy = new JFocusTraversalPolicy();
		this.setFocusTraversalPolicy(this.focusTraversalPolicy);
		this.focusTraversalPolicy.setDefaultComponent(jCombobox_LookFeel);
		this.focusTraversalPolicy.addComponent(jButtonOK);
		this.focusTraversalPolicy.addComponent(jButtonCancel);
		liguidLooks = new LiquidLookAndFeel();
		goodiesLooks = new Plastic3DLookAndFeel();
		gooiesWindowLooks = new WindowsLookAndFeel();
	}

	/* combo設定 */
	private void initializecomboSetting(){

		if (isAvailableLookAndFeel(mac))
			jCombobox_LookFeel.addItem(macStr);
		if (isAvailableLookAndFeel(metal))
			jCombobox_LookFeel.addItem(metalStr);
		// edit s.inoue 2009/12/16
		// if (isAvailableLookAndFeel(motif))
		//	jCombobox_LookFeel.addItem("motif");
		if (isAvailableLookAndFeel(windows))
			jCombobox_LookFeel.addItem(windowsStr);
		if (isAvailableLookAndFeel(gtk))
			jCombobox_LookFeel.addItem(gtkStr);
		if (isAvailableLookAndFeel(liquid))
			jCombobox_LookFeel.addItem(liquidStr);
		// del s.inoue 2010/11/05
//		if (isAvailableLookAndFeel(nimbus))
//			jCombobox_LookFeel.addItem(nimbusStr);
		// add s.inoue 2013/03/09
		if (isAvailableLookAndFeel(goodies) && flgOS)
			jCombobox_LookFeel.addItem(goodiesStr);
		if (isAvailableLookAndFeel(goodiesWin) && flgOS)
			jCombobox_LookFeel.addItem(goodiesWinStr);

		String lookAndFeel = getAppSettings();

		if (!lookAndFeel.equals("")){
			String look = getLookFeelString(lookAndFeel);
			jCombobox_LookFeel.setSelectedItem(look);
		}
	}

	// add s.inoue 2010/06/04
	private String getLookFeelString(String selectLookFeel){
		String retStr = "";

		if (selectLookFeel.equals(liquid)){
			retStr = liquidStr;
		}else if (selectLookFeel.equals(metal)){
			retStr = metal;
		}else if (selectLookFeel.equals(gtk)){
			retStr = gtkStr;
		}else if (selectLookFeel.equals(windows)){
			retStr = windowsStr;

		// add s.inoue 2013/03/09
		} else if (selectLookFeel.equals(goodies) && flgOS){
			retStr = goodiesStr;

		} else if (selectLookFeel.equals(goodiesWin) && flgOS){
			retStr = goodiesWinStr;
		// del s.inoue 2010/11/05
//		}else if (selectLookFeel.equals(nimbus)){
//			retStr = nimbusStr;
		}

		return retStr;
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
			// eidt s.inoue 2011/04/08
//			jContentPane.add(getJPanelNaviArea(), BorderLayout.NORTH);
			jContentPane.add(getJPanelMainArea(), BorderLayout.CENTER);
			jContentPane.add(getJPanelButtonArea(), BorderLayout.NORTH);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanelMainArea
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelMainArea() {
		if (jPanel_MainArea == null) {
			jPanel_MainArea = new JPanel();
			jPanel_MainArea.setLayout(new BorderLayout());
			jPanel_MainArea.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
			jPanel_MainArea.setOpaque(false);

			jPanel_MainArea.add(getJPanelLookFeel(),BorderLayout.NORTH);

		}
		return jPanel_MainArea;
	}
	/**
	 * This method initializes jPanel9
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelLookFeel() {
		if (jPanel_LookFeel == null) {
			jLabelLookFeel = new ExtendedLabel();
			jLabelLookFeel.setText("");

			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints.insets = new Insets(0,5,10,0);
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridy = 0;

			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints1.insets = new Insets(0,5,0,0);
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.gridy = 0;
			gridBagConstraints1.weightx = 1.0D;

			jPanel_LookFeel = new JPanel();
			jPanel_LookFeel.setLayout(new GridBagLayout());
			// edit s.inoue 2009/12/18
			jPanel_LookFeel.add(jLabelLookFeel,gridBagConstraints);
			jPanel_LookFeel.add(getJComboBox_LookFeel(), gridBagConstraints1);
		}
		return jPanel_LookFeel;
	}

	/**
	 * This method initializes jPanelButtonArea
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelButtonArea() {
		if (jPanelButtonArea == null) {
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.gridy = 0;

			// edit s.inoue 2009/12/15
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridx = 1;
			gridBagConstraints3.gridy = 0;
			gridBagConstraints3.weightx = 1.0D;
			gridBagConstraints3.anchor = GridBagConstraints.WEST;
			gridBagConstraints3.insets = new Insets(0, 5, 0, 0);
			jPanelButtonArea = new JPanel();
			jPanelButtonArea.setLayout(new GridBagLayout());
			jPanelButtonArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			jPanelButtonArea.add(getJButtonCancel(), gridBagConstraints2);
			jPanelButtonArea.add(getJButtonOK(), gridBagConstraints3);
		}
		return jPanelButtonArea;
	}

	// add s.inoue 2009/11/25
	/**
	 * This method initializes jCombobox_LookFeel
	 *
	 * @return javax.swing.ExtendedComboBox
	 */
	private ExtendedComboBox getJComboBox_LookFeel() {
		if (jCombobox_LookFeel == null) {
			jCombobox_LookFeel = new ExtendedComboBox();
			jCombobox_LookFeel.setPreferredSize(new Dimension(260, 20));
			jCombobox_LookFeel.addItemListener(this);
			jCombobox_LookFeel.addKeyListener(this);
		}
		return jCombobox_LookFeel;
	}

	/**
	 * This method initializes jButtonOK
	 *
	 * @return javax.swing.JButton
	 */
	private ExtendedButton getJButtonOK() {
		if (jButtonOK == null) {
			// eidt s.inoue 2011/04/08
//			jButtonOK = new ExtendedButton();
//			jButtonOK.setText("確定(F12)");
//			jButtonOK.setActionCommand("終了");
//			jButtonOK.addActionListener(this);
//			jButtonOK.addKeyListener(this);
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Decide);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);
			jButtonOK= new ExtendedButton(
					"Decide","確定(D)","確定(ALT+D)",KeyEvent.VK_D,icon);
			jButtonOK.addActionListener(this);
		}
		return jButtonOK;
	}

	/**
	 * This method initializes jButtonCancel
	 *
	 * @return javax.swing.JButton
	 */
	private ExtendedButton getJButtonCancel() {
		if (jButtonCancel == null) {
//			jButtonCancel = new ExtendedButton();
//			jButtonCancel.setText("キャンセル(F1)");
//			jButtonCancel.addActionListener(this);
//			// edit s.inoue 2009/12/11
//			jButtonCancel.addKeyListener(this);
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Back);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);
			jButtonCancel= new ExtendedButton(
					"End","戻る(R)","戻る(ALT+R)",KeyEvent.VK_R,icon);
			jButtonCancel.addActionListener(this);
		}
		return jButtonCancel;
	}

	@Override
	public void keyPressed(KeyEvent keyEvent) {
		switch(keyEvent.getKeyCode()){
		case KeyEvent.VK_F1:
			changeTheLookAndFeel(getAppSettings(),false);dispose();break;
		case KeyEvent.VK_F12:
			registerSettings();dispose();break;
		}
	}

	public void keyReleased(KeyEvent arg0) {
	}
	public void keyTyped(KeyEvent arg0) {
	}
	public void mousePressed(MouseEvent e) {
	}
	public void mouseReleased(MouseEvent e) {
	}

	/**
	 * 戻り値を取得する
	 * @return 戻り値
	 */
	public RETURN_VALUE getStatus() {
		return ReturnValue;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == jButtonOK) {
			registerSettings();
		}else if(e.getSource() == jButtonCancel) {
			changeTheLookAndFeel(getAppSettings(),false);
		}
		// モーダルダイアログの制御解除。
		setVisible(false);
	}

	// add s.inoue 2009/12/18
	private String getAppSettings(){
		return PropertyUtil.getProperty( "setting.lookAndFeel");
	}

	/* Look & Feel 設定*/
	protected boolean isAvailableLookAndFeel(String laf) {
		try{
		    Class lnfClass = Class.forName(laf);
		    LookAndFeel newLAF = (LookAndFeel)(lnfClass.newInstance());

		    return newLAF.isSupportedLookAndFeel();
		}catch(Exception e) {
		    return false;
		}
	}

    /* comboイベント */
	public void itemStateChanged(ItemEvent e) {
		try {
			if (e.getStateChange()==ItemEvent.SELECTED){
				String selectItem = jCombobox_LookFeel.getSelectedItem().toString();
				changeTheLookAndFeel(selectItem,false);
			}
		}catch(Exception ex){
		}
	}

	/* comboイベント */
	public void changeTheLookAndFeel(String value,boolean chgFlg) {
	    try {
	    	if (!chgFlg)
	    		return;

	    	if( liguidLooks ==null || goodiesLooks == null || gooiesWindowLooks == null){
				liguidLooks = new LiquidLookAndFeel();
		    	// eidt s.inoue 2013/03/09
				if (flgOS){
					goodiesLooks = new Plastic3DLookAndFeel();
					gooiesWindowLooks = new WindowsLookAndFeel();
				}
	    	}

	    	if (value.equals("com.birosoft.liquid.LiquidLookAndFeel")){
	    		UIManager.setLookAndFeel(liguidLooks);
	    		// add s.inoue 2013/03/12
//	    		JApplication.blnLookFeelMetal =false;
	    	// edit s.inoue 2010/11/05
	    	}else if (value.equals("com.jgoodies.looks.plastic.Plastic3DLookAndFeel")){
	    		System.out.println("Plastic3DLookAndFeel");
	    		// add s.inoue 2013/03/12
//	    		JApplication.blnLookFeelMetal =false;

	    		UIManager.setLookAndFeel(goodiesLooks);
	    	}else if (value.equals("com.jgoodies.looks.windows.WindowsLookAndFeel")){
	    		System.out.println("WindowsLookAndFeel");
	    		// add s.inoue 2013/03/12
//	    		JApplication.blnLookFeelMetal =false;

	    		UIManager.setLookAndFeel(gooiesWindowLooks);
	    	}else{

	    		// add s.inoue 2013/03/12
//	    		if (value.equals("javax.swing.plaf.metal.MetalLookAndFeel")){
//	    			JApplication.blnLookFeelMetal =true;
//	    		}else{
//		    		// add s.inoue 2013/03/12
//		    		JApplication.blnLookFeelMetal =false;
//	    		}

	    		// eidt s.inoue 2013/03/09
	    		// UIManager.setLookAndFeel(looksinfo.getClassName());
	    		UIManager.setLookAndFeel(value);
	    		SwingUtilities.updateComponentTreeUI(this);
	    	}

			// eidt s.inoue 2013/03/12
			JApplication.FONT_COMMON_BUTTON = new Font("ＭＳ ゴシック", 0, 12);
			JApplication.FONT_COMMON_MENU_BUTTON = new Font("ＭＳ ゴシック", 0, 13);
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	}

	// edit s.inoue 2010/06/04
	private String getLookandFeelName(String value){

		String retStr = "";
		try {
			for (int i = 0; i < looks.length; i++) {
				if (looks[i].getName().equals(value)){
					looksinfo = looks[i];
					break;
				}
			}

	    	// edit s.inoue 2010/06/03
	    	if (value.equals(liquidStr)){
	    		retStr = liguidLooks.getClass().getName().toString();
	    	// add s.inoue 2010/11/05
	    	}else if (value.equals(goodiesStr)){
	    		retStr = goodiesLooks.getClass().getName().toString();
	    	}else if (value.equals(goodiesWinStr)){
	    		retStr = gooiesWindowLooks.getClass().getName().toString();
	    	}else{
	    		retStr = looksinfo.getClassName();
	    	}
	    } catch (Exception e) {
			e.printStackTrace();
		}

	    return retStr;
	}

	// add s.inoue 2010/06/04
	private void applyAppSettings(String name){
		// edit s.inoue 2010/06/03
		// PropertyUtil.setProperty( "setting.lookAndFeel", looksinfo.getName() );
		PropertyUtil.setProperty( "setting.lookAndFeel", name );
	}

	/* 登録 */
	private void registerSettings(){
		// applicationへ書込
		String selectItem = jCombobox_LookFeel.getSelectedItem().toString();
		String value = getLookandFeelName(selectItem);
		changeTheLookAndFeel(value,true);
		// edit s.inoue 2010/06/04
		applyAppSettings(value);
	}

	@Override
	public String getKenshinDate() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
	@Override
	public void setMessageTitle(String title) {
		// TODO 自動生成されたメソッド・スタブ

	}
	@Override
	public void setShowCancelButton(boolean isShowCancel) {
		// TODO 自動生成されたメソッド・スタブ

	}
	@Override
	public void setText(String text) {
		// TODO 自動生成されたメソッド・スタブ

	}
	@Override
	public Integer getPrintSelect() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
	@Override
	public String getFilePath() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
	@Override
	public void setDialogTitle(String title) {
		// TODO 自動生成されたメソッド・スタブ

	}
	@Override
	public void setDialogSelect(boolean enabled) {
		// TODO 自動生成されたメソッド・スタブ

	}
	@Override
	public void setSaveFileName(String title) {
		// TODO 自動生成されたメソッド・スタブ

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

}
