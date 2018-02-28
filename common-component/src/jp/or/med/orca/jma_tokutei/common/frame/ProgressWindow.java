package jp.or.med.orca.jma_tokutei.common.frame;

import javax.swing.JPanel;
import java.awt.Frame;
import java.awt.GridBagLayout;
import javax.swing.JProgressBar;
import java.awt.GridBagConstraints;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;

import jp.or.med.orca.jma_tokutei.common.component.ExtendedLabel;
import jp.or.med.orca.jma_tokutei.common.component.TitleLabel;
import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.WindowConstants;

/**
 * 処理時間が長い場合に、そのことをユーザに通知する画面 
 */
public class ProgressWindow extends JDialog {

	private static final long serialVersionUID = 1L;

	private static ProgressWindow window = null;
	
	private JPanel jContentPane = null;

	private JProgressBar jProgressBar = null;

	private JPanel jPanel = null;

	private TitleLabel jLabel_title = null;

	private JPanel jPanel1 = null;

	private ExtendedLabel jLabel = null;

	private JLabel jLabel1 = null;

	private JPanel jPanel_progressBar = null;

	public void setProgressMinValue(int n){
		this.jProgressBar.setMinimum(n);
	}
	
	public void setProgressMaxValue(int n){
		this.jProgressBar.setMaximum(n);
	}

	public void setProgressValue(int n){
		this.jProgressBar.setValue(n);
	}
	
//	public static void main(String[] args) {
//		ProgressWindow frame = new ProgressWindow(null);
//		frame.setProgressMaxValue(10);
//		frame.setProgressMinValue(0);
//		frame.setProgressValue(85);
//		frame.setVisible(true);
//	}
	
//	public static ProgressWindow getProgressWindow(){
//		if (ProgressWindow.window == null) {
//			ProgressWindow.window = new ProgressWindow(null);
//		}
//		
//		return ProgressWindow.window;
//	}
	
	/**
	 * This method initializes jPanel_progressBar	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel_progressBar() {
		if (jPanel_progressBar == null) {
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.BOTH;
			gridBagConstraints.gridy = 0;
			gridBagConstraints.weightx = 1.0D;
			gridBagConstraints.weighty = 1.0D;
			gridBagConstraints.gridx = 0;
			jPanel_progressBar = new JPanel();
			jPanel_progressBar.setLayout(new GridBagLayout());
			jPanel_progressBar.add(getJProgressBar(), gridBagConstraints);
		}
		return jPanel_progressBar;
	}

	public void setGuidanceByKey(String key){
		this.jLabel.setTextFromKey(key);
	}

	/*
	 * コンストラクタ 
	 */
	/**
	 * @param owner
	 */
	public ProgressWindow(Frame owner, boolean displayProgressBar, boolean displayFrameDecoration) {
		super(owner);
		this.initialize();
		
		this.setUndecorated(! displayFrameDecoration);
		this.jPanel_progressBar.setVisible(displayProgressBar);
		
		this.initializeFrame();
	}
	
	/**
	 * @param owner
	 */
	public ProgressWindow(Frame owner) {
		super(owner);
		this.initialize();
		this.initializeFrame();
	}
	
	/**
	 * @param owner
	 */
	public ProgressWindow() {
		super((Frame)null);
		this.initialize();
		this.initializeFrame();
	}

	/**
	 * Frame に関する部分を初期化する。 
	 */
	private void initializeFrame() {
		this.pack();
		this.setLocationRelativeTo(null);
		this.setAlwaysOnTop(true);
	}
		
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			gridBagConstraints12.gridx = 0;
			gridBagConstraints12.fill = GridBagConstraints.BOTH;
			gridBagConstraints12.weightx = 1.0D;
			gridBagConstraints12.weighty = 1.0D;
			gridBagConstraints12.gridy = 2;
			GridBagConstraints gridBagConstraints41 = new GridBagConstraints();
			gridBagConstraints41.gridx = 0;
			gridBagConstraints41.gridy = 5;
			jLabel1 = new JLabel();
			jLabel1.setText("");
			jLabel = new ExtendedLabel("common.frame.progress.guidance.main");
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.gridx = 0;
			gridBagConstraints11.ipady = 10;
			gridBagConstraints11.insets = new Insets(0, 10, 0, 10);
			gridBagConstraints11.fill = GridBagConstraints.BOTH;
			gridBagConstraints11.gridy = 1;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.fill = GridBagConstraints.BOTH;
			gridBagConstraints1.weightx = 1.0D;
			gridBagConstraints1.gridy = 0;
			jContentPane = new JPanel();
			jContentPane.setLayout(new GridBagLayout());
			jContentPane.add(getJPanel(), gridBagConstraints1);
			jContentPane.add(getJPanel1(), gridBagConstraints11);
			jContentPane.add(getJPanel_progressBar(), gridBagConstraints12);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jProgressBar	
	 * 	
	 * @return javax.swing.JProgressBar	
	 */
	private JProgressBar getJProgressBar() {
		if (jProgressBar == null) {
			jProgressBar = new JProgressBar();
			jProgressBar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			jProgressBar.setStringPainted(true);
		}
		return jProgressBar;
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.fill = GridBagConstraints.BOTH;
			gridBagConstraints2.weightx = 1.0D;
			gridBagConstraints2.gridy = 0;
			jLabel_title = new TitleLabel("common.frame.progress.title");
			jPanel = new JPanel();
			jPanel.setLayout(new GridBagLayout());
			jPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));
			jPanel.add(jLabel_title, gridBagConstraints2);
		}
		return jPanel;
	}

	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridx = 0;
			gridBagConstraints3.fill = GridBagConstraints.BOTH;
			gridBagConstraints3.weightx = 1.0D;
			gridBagConstraints3.gridy = 1;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 0;
			gridBagConstraints4.fill = GridBagConstraints.BOTH;
			gridBagConstraints4.weightx = 1.0D;
			gridBagConstraints4.gridy = 0;
			jPanel1 = new JPanel();
			jPanel1.setLayout(new GridBagLayout());
			jPanel1.add(jLabel, gridBagConstraints4);
			jPanel1.add(jLabel1, gridBagConstraints3);
		}
		return jPanel1;
	}
}

