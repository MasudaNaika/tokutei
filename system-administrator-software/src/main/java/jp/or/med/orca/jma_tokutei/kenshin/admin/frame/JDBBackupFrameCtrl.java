package jp.or.med.orca.jma_tokutei.kenshin.admin.frame;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
import jp.or.med.orca.jma_tokutei.common.filectrl.JFile;
import jp.or.med.orca.jma_tokutei.common.filectrl.JFileCopy;
import jp.or.med.orca.jma_tokutei.common.focus.JFocusTraversalPolicy;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import jp.or.med.orca.jma_tokutei.common.sql.JConnection;
import jp.or.med.orca.jma_tokutei.common.table.JSimpleTable;
import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableCellPosition;
import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableCellRowRenderer;
import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableScrollPanel;
import jp.or.med.orca.jma_tokutei.dbfix.ShcDBAdjust;

import org.apache.log4j.Logger;

// import com.l2fprod.common.swing.JDirectoryChooser;

/**
 * データベースバックアップ
 */
public class JDBBackupFrameCtrl extends JDBBackupFrame {
	private JSimpleTable m_model = new JSimpleTable();

	/* フォーカス移動制御 */
	private JFocusTraversalPolicy focusTraversalPolicy = null;

	// edit h.yoshitama 2009/11/13
	// private Vector<File> m_dirList = new Vector<File>();
	private SortedMap<String,File> m_fileList = null;

	// s.inoue 20080807
    private static org.apache.log4j.Logger logger = Logger
    .getLogger(JDBBackupFrameCtrl.class);

	/**
	 *  コンストラクタ
	 *
	 *    @param  none
	 *
	 *    @return none
	 */
	public JDBBackupFrameCtrl() {
		m_model.addHeader("バックアップ日付");
		m_model.addHeader("バックアップサイズ");

		// edit h.yoshitama 2009/11/13
		m_fileList = new TreeMap<String,File>();

		// テーブルパネル生成
		JSimpleTableScrollPanel tablePanel = new JSimpleTableScrollPanel(m_model);

		// テーブルパネル設定
		JSimpleTableCellPosition[] forbidList = { new JSimpleTableCellPosition(-1, -1) };

		m_model.setCellEditForbid(forbidList);
		m_model.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		jPanel_Table.add(tablePanel, BorderLayout.CENTER);

		reloadDBFileList();

		this.setTitle(ViewSettings.getAdminCommonTitleWithVersion());

		// focus制御
		// edit s.inoue 2009/10/07
		this.focusTraversalPolicy = new JFocusTraversalPolicy();
		this.setFocusTraversalPolicy(this.focusTraversalPolicy);
		this.focusTraversalPolicy.setDefaultComponent(this.m_model);
		this.focusTraversalPolicy.addComponent(this.m_model);
		this.focusTraversalPolicy.addComponent(this.jButton_Reload);
		this.focusTraversalPolicy.addComponent(this.jButton_Backup);
		this.focusTraversalPolicy.addComponent(this.jButton_Restore);
		this.focusTraversalPolicy.addComponent(this.jButton_Delete);
		this.focusTraversalPolicy.addComponent(jButton_End);

		// add s.inoue 2009/12/03
		for (int i = 0; i < focusTraversalPolicy.getComponentSize(); i++) {
			Component comp = focusTraversalPolicy.getComponent(i);
			if (comp != null)
			comp.addKeyListener(this);
		}

		// add ver2 s.inoue 2009/05/08
		TableColumnModel columns = m_model.getColumnModel();
        for(int i=0;i<columns.getColumnCount();i++) {

            columns.getColumn(i).setCellRenderer(new JSimpleTableCellRowRenderer(
                (DefaultTableCellRenderer)m_model.getDefaultRenderer(m_model.getColumnClass(i))));
        }
	}

	/**
	 *  データベース接続
	 *     @param  none
	 *     @return none
	 */
	private void connectDataBase() throws SQLException {
		try {
			JApplication.systemDatabase = JConnection.ConnectSystemDatabase();
		} catch (SQLException err) {
			err.printStackTrace();

			JErrorMessage.show(
					"M7501", this);

			throw new SQLException();
		}
	}

	/**
	 *  データベース切断
	 *
	 *    @param  none
	 *
	 *    @return none
	 */
	private void shutdownDataBase() throws SQLException {
		try {
			JApplication.systemDatabase.Shutdown();
		} catch (SQLException err) {
			err.printStackTrace();

			JErrorMessage.show(
					"M7500", this);

			throw new SQLException();
		}
	}

	/**
	 *  ディレクトリサイズ計算
	 *
	 *     @param  対象ディレクトリ
	 *
	 *     @return long
	 */
	private long calcDirectorySize(File dir) {
		long lTotalSize = 0;

		File[] fileList = dir.listFiles();

		if (fileList != null) {
			for (int i = 0; i < fileList.length; ++i) {
				lTotalSize += fileList[i].length();
			}
		}

		return lTotalSize;
	}

	/**
	 *  バックアップリスト読込
	 *
	 *     @param  none
	 *
	 *     @return none
	 */
	private void reloadDBFileList() {
		m_model.clearAllRow();
		m_fileList.clear();

		File dirRoot = new File(JPath.BackupSystemDatabaseFolder);

		// ディレクトリチェック
		if (!dirRoot.exists()) {
			return;
		}

		// ディレクトリ一覧作成
		File[] dirList = dirRoot.listFiles();

		// edit s.inoue 2009/10/31
		for( int i = dirList.length - 1; i >= 0; --i )
		{
			if (dirList[i].isDirectory()) {
				// edit h.yoshitama 2009/11/13
				// m_fileList.add(dirList[i]);
				m_fileList.put(String.valueOf(dirList.length-i-1),dirList[i]);
			}
		}

		// ディレクトリ一覧整理
		// del h.yoshitama 2009/11/13
//		for (int i = 0; i < m_fileList.size(); ++i) {
//			for (int j = i + 1; j < m_fileList.size(); ++j) {
//				if (m_fileList.get(i).lastModified() > m_fileList.get(j)
//						.lastModified()) {
//					File fileTmp1 = m_fileList.get(i);
//					File fileTmp2 = m_fileList.get(j);
//
//					m_fileList.set(i, fileTmp2);
//					m_fileList.set(j, fileTmp1);
//				}
//			}
//		}

		// ファイル列挙
		// for( int i = fileList.length - 1; i >= 0; --i ){
		for (int i = 0; i < m_fileList.size(); ++i) {
			// add s.inoue 2012/01/13
			if (m_fileList.get(String.valueOf(i))== null)
				continue;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

			long lSize = calcDirectorySize(m_fileList.get(String.valueOf(i))) / 1000;

			String[] aryData = {
					dateFormat
							.format(new Date(m_fileList.get(String.valueOf(i)).lastModified())), // ファイル日付
					Long.toString(lSize) + " KB" // ファイル容量
			};

			m_model.addData(aryData);
		}

		// add s.inoue 2009/11/16
		m_model.setAutoCreateRowSorter(false);
		m_model.refreshTable();

		// 最初に選択する項目の設定
		if (m_model.getRowCount() > 0) {
			m_model.setRowSelectionInterval(0, 0);
		} else {
//			jButton_Reload.requestFocus();
		}
	}

	/**
	 *  バックアップ処理
	 *
	 *    @param  none
	 *
	 *    @return none
	 */
	public boolean beginBackup(){

		boolean retflg = false;

// del s.inoue 2012/07/06
//		try {
//			// ディレクトリを選択出来るようにする
//			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
//
//			String savePath = JPath.CURRENT_DIR_PATH + JApplication.FILE_SEPARATOR
//								+ JPath.BackupSystemDatabaseFolder
//								+ JApplication.versionNumber + "_"
//								+ dateFormat.format(Calendar.getInstance().getTime());
//
//			File dirfile = new File(savePath);
//			if (!dirfile.mkdirs()) {
//				// throw new Exception();
//				JErrorMessage.show("M4201", this);
//				return retflg;
//			}
//
//			JDirectoryChooser chooser = new JDirectoryChooser();
//			chooser.setApproveButtonText("保存");
//
//			File cfile = new File(savePath);
//			// eidt s.inoue 2012/01/13
//			chooser.setCurrentDirectory(cfile);
//			int choice = chooser.showOpenDialog(this);
//
//			if( choice == JFileChooser.APPROVE_OPTION ){
//				// eidt s.inoue 2012/03/08
//				if (JFileCopy.xcopyFile(JPath.DatabaseFolder, chooser.getSelectedFile().getAbsolutePath()) > 0) {
//				// if (JFileCopy.xcopyFile(JPath.DatabaseFolder, dirfile.getAbsolutePath()) > 0) {
//					// throw new Exception();
//					JErrorMessage.show("M4201", this);
//					return retflg;
//				}
//			}else{
//				dirfile.delete();
//				return retflg;
//			}
//
//		} catch (Exception err) {
//			logger.error("バックアップ処理及びロールバック処理のファイルコピー時、エラーとなりました。");
//		}
// del s.inoue 2012/01/12
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

			String path = JPath.BackupSystemDatabaseFolder
								+ JApplication.FILE_SEPARATOR + JApplication.versionNumber + "_"
								+ dateFormat.format(Calendar.getInstance().getTime());

			File directory = new File(path);

			if (!directory.mkdirs()) {
				throw new Exception();
			}

			if (JFileCopy.xcopyFile(JPath.DatabaseFolder, directory.getAbsolutePath()) > 0) {
				throw new Exception();
			}
		} catch (Exception err) {
			err.printStackTrace();
			logger.error("バックアップ処理及びロールバック処理のファイルコピー時、エラーとなりました。");
		}
		return true;
	}

	/**
	 *  バックアップ終了
	 *
	 *    @param  イベント
	 *
	 *    @return none
	 */
	public void pushedEndButton(ActionEvent e) {
		dispose();
	}

	/**
	 *  バックアップ表示
	 *
	 *    @param  イベント
	 *
	 *    @return none
	 */
	public void pushedReloadButton(ActionEvent e) {
		reloadDBFileList();
	}

	/**
	 *  バックアップ処理
	 *
	 *    @param  イベント
	 *
	 *    @return none
	 */
	public void pushedBackupButton(ActionEvent e) {
		try {
			// データベース切断
			shutdownDataBase();

			// バックアップ確認
			RETURN_VALUE iResult = JErrorMessage.show("M7502", this);

			if (iResult == RETURN_VALUE.YES) {
				// eidt s.inoue 2012/01/13
				if (beginBackup()){
					reloadDBFileList();
					JErrorMessage.show("M7504", this);
				}
			}
		} catch (Exception err) {
			JErrorMessage.show("M7503", this);
		} finally {
			try {
				// データベース再接続
				connectDataBase();
			} catch (SQLException err) {
				err.printStackTrace();

				JErrorMessage.show("M0000", this);
				System.exit(1);
			}
		}
	}

	/**
	 *  バックアップ復元
	 *
	 *    @param  イベント
	 *
	 *    @return none
	 */
	public void pushedRestoreButton(ActionEvent e) {
		int iSelect = m_model.getSelectedRow();

		try {
			// データベース切断
			shutdownDataBase();

			if (iSelect != -1) {
				// バックアップ確認
				RETURN_VALUE iResult = JErrorMessage.show("M7505", this);

				if (iResult == RETURN_VALUE.YES) {
					try {
						// eidt s.inoue 2012/01/13
						if (beginBackup()){
							reloadDBFileList();
							JErrorMessage.show("M7504", this);
						}
					} catch (Exception err) {
						JErrorMessage.show("M7503", this);
						return;
					}
				}

				// バックアップ復元確認
				iResult = JErrorMessage.show("M7506", this);

				if (iResult == RETURN_VALUE.YES) {
					// 一時ファイル作成
					if (JFileCopy.xcopyFile(JPath.DatabaseFolder,
							JPath.TEMP_SYSTEM_DATABASEFILE_PATH) > 0) {
						JErrorMessage.show("M7509", this);

						return;
					}

					// ファイル削除
					if (!JFile.deleteDirectory(new File(JPath.DatabaseFolder),false)) {
						JErrorMessage.show("M7509", this);
						return;
					}

					// ファイル復元
					if (JFileCopy.xcopyFile(m_fileList.get(String.valueOf(iSelect)).getAbsolutePath(), JPath.DatabaseFolder) > 0) {
						JErrorMessage.show("M7509", this);
						return;
					}

					// 復元処理終了
					if (!JFile.deleteDirectory(new File(JPath.TEMP_SYSTEM_DATABASEFILE_PATH), false)) {
						JErrorMessage.show("M7509", this);
						return;
					}

					// yoshida 20090105 s.inoue
					ShcDBAdjust adjuster = new ShcDBAdjust();
					adjuster.call("System");    //System.fdbを対象
					adjuster.call("Kikan");     //Kikan.fdbを対象

					JErrorMessage.show("M7510", this);

					reloadDBFileList();
				}
			}
		} catch (Exception err1) {
			JErrorMessage.show("M7508", this);

			try {
				// 復旧復元
				if (JFileCopy.xcopyFile(JPath.TEMP_SYSTEM_DATABASEFILE_PATH,
						JPath.DatabaseFolder) > 0) {
					JErrorMessage
							.show("M0000", this);

					System.exit(1);
				}

				// 復元処理終了
				if (!JFile.deleteDirectory(new File(
						JPath.TEMP_SYSTEM_DATABASEFILE_PATH), false)) {
					JErrorMessage
							.show("M0000", this);

					return;
				}
			} catch (Exception err2) {
				err2.printStackTrace();

				JErrorMessage.show("M0000", this);

				System.exit(1);
			}
		} finally {
			try {
				// データベース再接続
				connectDataBase();

			} catch (SQLException err) {
				err.printStackTrace();

				JErrorMessage.show("M0000", this);

				System.exit(1);
			}
		}
	}

	/**
	 *  バックアップ削除
	 *
	 *    @param  イベント
	 *
	 *    @return none
	 */
	public void pushedDeleteButton(ActionEvent e) {
		int iSelect[] = m_model.getSelectedRows();

		try {
			// データベース切断
			shutdownDataBase();

			if (iSelect.length > 0) {
				RETURN_VALUE iResult = JErrorMessage
						.show("M7511", this);

				if (iResult == RETURN_VALUE.YES) {
					for (int i = 0; i < iSelect.length; ++i) {
						if (!JFile.deleteDirectory(m_fileList.get(String.valueOf(iSelect[i]))
								.getAbsoluteFile(), true)) {
							JErrorMessage.show("M7512", this);

							return;
						}
					}

					reloadDBFileList();

					JErrorMessage.show("M7513", this);
				}
			}
		} catch (Exception err) {
			err.printStackTrace();

			JErrorMessage.show("M7512", this);
		} finally {
			try {
				// データベース再接続
				connectDataBase();
			} catch (SQLException err) {
				err.printStackTrace();

				JErrorMessage.show("M0000", this);

				System.exit(1);
			}
		}
	}

	/**
	 *  アクションイベント
	 *
	 *    @param  イベント
	 *
	 *    @return none
	 */
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		/* 終了ボタン */
		if (source == jButton_End) {
			logger.info(jButton_End.getText());
			pushedEndButton(e);
		}
		/* 再表示ボタン */
		else if (source == jButton_Reload) {
			logger.info(jButton_Reload.getText());
			pushedReloadButton(e);
		}
		/* バックアップボタン */
		else if (source == jButton_Backup) {
			logger.info(jButton_Backup.getText());
			pushedBackupButton(e);
		}
		/* 復元ボタン */
		else if (source == jButton_Restore) {
			logger.info(jButton_Restore.getText());
			pushedRestoreButton(e);
		}
		/* 削除ボタン */
		else if (source == jButton_Delete) {
			logger.info(jButton_Delete.getText());
			pushedDeleteButton(e);
		}
	}

	// add s.inoue 2009/12/03
	@Override
	public void keyPressed(KeyEvent keyEvent) {
		switch(keyEvent.getKeyCode()){
		case KeyEvent.VK_F1:
			logger.info(jButton_End.getText());
			pushedEndButton(null);break;
		case KeyEvent.VK_F7:
			logger.info(jButton_Reload.getText());
			pushedReloadButton(null);break;
		case KeyEvent.VK_F9:
			logger.info(jButton_Backup.getText());
			pushedBackupButton(null);break;
		case KeyEvent.VK_F11:
			logger.info(jButton_Restore.getText());
			pushedRestoreButton(null);break;
		case KeyEvent.VK_F12:
			logger.info(jButton_Delete.getText());
			pushedDeleteButton(null);break;
		}
	}
}

//Source Code Version Tag System v1.00
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

