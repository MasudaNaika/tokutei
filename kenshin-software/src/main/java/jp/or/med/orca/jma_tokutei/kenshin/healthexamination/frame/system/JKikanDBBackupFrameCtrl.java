package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.system;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
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
import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
import jp.or.med.orca.jma_tokutei.common.filectrl.JFile;
import jp.or.med.orca.jma_tokutei.common.filectrl.JFileCopy;
import jp.or.med.orca.jma_tokutei.common.focus.JFocusTraversalPolicy;
import jp.or.med.orca.jma_tokutei.common.sql.JConnection;
import jp.or.med.orca.jma_tokutei.common.table.JSimpleTable;
import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableCellPosition;
import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableCellRowRenderer;
import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableScrollPanel;
import jp.or.med.orca.jma_tokutei.db.DBYearAdjuster;
import jp.or.med.orca.jma_tokutei.dbfix.ShcDBAdjust;

import org.apache.log4j.Logger;

/**
 * 機関データベースバックアップフレームのコントロール
 */
public class JKikanDBBackupFrameCtrl extends JKikanDBBackupFrame {

	private static final long serialVersionUID = 7977639730666699320L;	// edit n.ohkubo 2014/10/01　追加

	private JSimpleTable m_model = new JSimpleTable();

	// edit h.yoshitama 2009/11/13
	private SortedMap<String,File> m_fileList = null;

	/* フォーカス移動制御 */
	private JFocusTraversalPolicy focusTraversalPolicy = null;
	private static Logger logger = Logger.getLogger(JKikanDBBackupFrameCtrl.class);

	/**
	 *  コンストラクタ
	 *
	 *    @param  none
	 *
	 *    @return none
	 */
	public JKikanDBBackupFrameCtrl() {
		m_model.addHeader("バックアップ日付");
		m_model.addHeader("バックアップサイズ");
		m_model.addHeader("バージョン");	// edit n.ohkubo 2014/10/01　追加

		// edit h.yoshitama 2009/11/13
		m_fileList = new TreeMap<String,File>();


		// テーブルパネル生成
		JSimpleTableScrollPanel tablePanel = new JSimpleTableScrollPanel(
				m_model);

		// テーブルパネル設定
		JSimpleTableCellPosition[] forbidList = { new JSimpleTableCellPosition(
				-1, -1) };

		m_model.setCellEditForbid(forbidList);

		m_model.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		jPanel_Table.add(tablePanel, BorderLayout.CENTER);

		reloadDBFileList();

		// add ver2 s.inoue 2009/05/08
		TableColumnModel columns = m_model.getColumnModel();
        for(int i=0;i<columns.getColumnCount();i++) {

            columns.getColumn(i).setCellRenderer(new JSimpleTableCellRowRenderer(
                (DefaultTableCellRenderer)m_model.getDefaultRenderer(m_model.getColumnClass(i))));
        }

		// focus制御
		// edit s.inoue 2009/10/07
		this.focusTraversalPolicy = new JFocusTraversalPolicy();
		this.setFocusTraversalPolicy(this.focusTraversalPolicy);
		this.focusTraversalPolicy.setDefaultComponent(this.m_model);
		this.focusTraversalPolicy.addComponent(this.m_model);
//		this.focusTraversalPolicy.addComponent(this.jButton_Reload);
		this.focusTraversalPolicy.addComponent(this.jButton_Backup);
		this.focusTraversalPolicy.addComponent(this.jButton_Restore);
		this.focusTraversalPolicy.addComponent(this.jButton_Delete);
		this.focusTraversalPolicy.addComponent(jButton_End);

		// add s.inoue 2009/12/02
		for (int i = 0; i < focusTraversalPolicy.getComponentSize(); i++) {
			Component comp = focusTraversalPolicy.getComponent(i);
			comp.addKeyListener(this);
		}
	}

	/**
	 *  データベース接続
	 *
	 *    @param  none
	 *
	 *    @return none
	 *
	 *    @exception SQLException
	 */
	private void connectDataBase() throws SQLException {
		JApplication.kikanDatabase = JConnection
				.ConnectKikanDatabase(JApplication.kikanNumber);

		try {
			JApplication.kikanDatabase
					.sendExecuteQuery("SELECT * FROM T_USER WHERE USER_NAME = "
							+ JQueryConvert.queryConvert(JApplication.userID));
		} catch (SQLException e) {
			e.printStackTrace();

			shutdownDataBase();

			throw new SQLException();
		}
	}

	/**
	 *  データベース切断
	 *
	 *    @param  none
	 *
	 *    @return none
	 *
	 *    @exception SQLException
	 */
	private void shutdownDataBase() throws SQLException {
		try {
			JApplication.kikanDatabase.Shutdown();
		} catch (SQLException e) {
			e.printStackTrace();

			throw new SQLException();
		}
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

		File dirRoot = new File(JPath.BackupKikanDataBaseFolder
				+ JApplication.kikanNumber + JApplication.FILE_SEPARATOR);

		// ディレクトリチェック
		if (!dirRoot.exists()) {
			return;
		}

		// ファイル一覧作成
		File[] fileList = dirRoot.listFiles();

		// edit s.inoue 2009/10/31
		for( int i = fileList.length - 1; i >= 0; --i )
		{
			if (fileList[i].isFile()) {
				// edit h.yoshitama 2009/11/13
				m_fileList.put(String.valueOf(fileList.length-i-1),fileList[i]);
			}
		}

		// ファイル一覧整理
		// del h.yoshitama 2009/11/13
//		 for (int i = 0; i < m_fileList.size(); ++i) {
//			 for (int j = i+1; j < m_fileList.size(); ++j) {
//				if (m_fileList.get(String.valueOf(i)).lastModified() > m_fileList.get(String.valueOf(j))
//						.lastModified()) {
//					File fileTmp1 = m_fileList.get(String.valueOf(i));
//					File fileTmp2 = m_fileList.get(String.valueOf(j));
//
//					// edit h.yoshitama 2009/11/13
//					m_fileList.put(String.valueOf(j), fileTmp1);
//					m_fileList.put(String.valueOf(i), fileTmp2);
//				}
//			}
//		}

		// ファイル列挙
		// edit s.inoue 2009/10/31
		// for( int i = fileList.length - 1; i >= 0; --i ){
		for (int i = 0; i < m_fileList.size(); ++i) {

			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			long lSize = m_fileList.get(String.valueOf(i)).length() / 1000;

			String[] aryData = {
					dateFormat.format(new Date(m_fileList.get(String.valueOf(i)).lastModified())), // ファイル日付
					Long.toString(lSize) + " KB" // ファイル容量
					, m_fileList.get(String.valueOf(i)).getName().substring(0, m_fileList.get(String.valueOf(i)).getName().indexOf("_"))	// edit n.ohkubo 2014/10/01　追加		バージョンを表示
			};

			m_model.addData(aryData);
		}

		// edit s.inoue 2009/11/17
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
	 *
	 *    @exception Exception
	 */
	public boolean beginBackup(){

		boolean retflg = false;

		try {

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

			String stringTimeStamp = dateFormat.format(Calendar.getInstance().getTime());

			// ディレクトリ作成
			File dirSave = new File(JPath.BackupKikanDataBaseFolder
					+ JApplication.kikanNumber + JApplication.FILE_SEPARATOR);

			if (! dirSave.exists()) {
				if (! dirSave.mkdirs()) {
					JErrorMessage.show("M4201", this);
					return retflg;
				}
			}

			String savePath = JPath.CURRENT_DIR_PATH + JApplication.FILE_SEPARATOR + JPath.BackupKikanDataBaseFolder + JApplication.kikanNumber
			+ JApplication.FILE_SEPARATOR + JApplication.versionNumber + "_" + stringTimeStamp + JPath.DATA_BASE_EXTENSION;

			JFileChooser dirSelect = new JFileChooser(savePath);
			// eidt s.inoue 2013/05/23
			// dirSelect.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			dirSelect.setFileSelectionMode(JFileChooser.FILES_ONLY);

			dirSelect.setApproveButtonText("保存");
			dirSelect.setSelectedFile(new File(savePath));
			// UIManager.put("FileChooser.readOnly", Boolean.FALSE);

			// 出力先のフォルダを選択してください。
			if( dirSelect.showSaveDialog( this ) == JFileChooser.APPROVE_OPTION ){
				// ＤＢファイルコピー
				if (JFileCopy.copyFile(
						JPath.GetKikanDatabaseFilePath(JApplication.kikanNumber),
						dirSelect.getSelectedFile().getPath()) ==false)
				{
					JErrorMessage.show("M4201", this);
					return retflg;
				}
			}else{
				return retflg;
			}

			// add s.inoue 2013/02/28
			// ＤＢファイルコピー
			if (JFileCopy.copyFile(JPath
							.GetKikanDatabaseFilePath(JApplication.kikanNumber),
							JPath.BackupKikanDataBaseFolder + JApplication.kikanNumber
							+ JApplication.FILE_SEPARATOR + JApplication.versionNumber
							+ "_" + stringTimeStamp + JPath.DATA_BASE_EXTENSION) ==false)
			{
				JErrorMessage.show("M4201", this);

				throw new Exception();
			}

// eidt s.inoue 2012/01/12
//			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
//
//			String stringTimeStamp = dateFormat.format(Calendar.getInstance()
//					.getTime());
//
//			// ディレクトリ作成
//			File dirSave = new File(JPath.BackupKikanDataBaseFolder
//					+ JApplication.kikanNumber + JApplication.FILE_SEPARATOR);
//
//			if (! dirSave.exists()) {
//				if (! dirSave.mkdirs()) {
//					JErrorMessage.show("M4201", this);
//
//					return;
//				}
//			}
//
//			// ＤＢファイルコピー
//			if (JFileCopy.copyFile(JPath
//							.GetKikanDatabaseFilePath(JApplication.kikanNumber),
//							JPath.BackupKikanDataBaseFolder + JApplication.kikanNumber
//							+ JApplication.FILE_SEPARATOR + JApplication.versionNumber
//							+ "_" + stringTimeStamp + JPath.DATA_BASE_EXTENSION) ==false)
//			{
//				JErrorMessage.show("M4201", this);
//
//				throw new Exception();
//			}

		} catch (Exception err) {
			JErrorMessage.show("M4201", this);
		}
		return true;
	}

	/*
	 *  バックアップ終了
	 *
	 *  	@param  イベント
	 *
	 *  	@return none
	 */
	public void pushedEndButton(ActionEvent e) {
		dispose();
	}

	/**
	 *  バックアップ削除
	 *
	 *    @param  イベント
	 *
	 *    @return none
	 */
	public void pushedDeleteButton(ActionEvent e) {
		try {
			int iSelect[] = m_model.getSelectedRows();

			if (iSelect.length > 0) {
				// データベース切断
				shutdownDataBase();

				// 削除確認
				RETURN_VALUE iResult = JErrorMessage.show("M4202", this);
				// edit h.yoshitama 2009/11/13
				if (iResult == RETURN_VALUE.YES) {
					// for (int i = iSelect.length-1; i >= 0; --i) {
					for (int i = 0; i < iSelect.length; ++i) {
						if (!m_fileList.get(String.valueOf(iSelect[i])).delete()) {
							JErrorMessage.show("M4203", this);

							return;
						}
					}

					JErrorMessage.show("M4204", this);

					reloadDBFileList();
				}
			}
		} catch (Exception err) {
			err.printStackTrace();

			JErrorMessage.show("M4203", this);
		} finally {
			try {
				// データベース再接続
				connectDataBase();
			} catch (SQLException err) {
				err.printStackTrace();

				JErrorMessage.show("M0000", this);

				System.exit(0);
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

		// add s.inoue 2013/02/28
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String stringTimeStamp = dateFormat.format(Calendar.getInstance().getTime());

		try {
			// データベース切断
			shutdownDataBase();

			if (iSelect != -1) {
				// 保存確認
				RETURN_VALUE iResult = JErrorMessage.show("M4205", this);

				if (iResult == RETURN_VALUE.YES) {
					try {
						beginBackup();

						reloadDBFileList();

						JErrorMessage.show("M4206", this);
					} catch (Exception err) {
						err.printStackTrace();

						JErrorMessage.show("M4207", this);

						return;
					}
				}

				// 復元確認
				iResult = JErrorMessage.show("M4208", this);

				if (iResult == RETURN_VALUE.YES) {

					String savePath = JPath.CURRENT_DIR_PATH + JApplication.FILE_SEPARATOR + JPath.BackupKikanDataBaseFolder + JApplication.kikanNumber
					+ JApplication.FILE_SEPARATOR + JApplication.versionNumber + "_" + stringTimeStamp + JPath.DATA_BASE_EXTENSION;

					JFileChooser dirSelect = new JFileChooser(savePath);
					dirSelect.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
					dirSelect.setApproveButtonText("選択");
					dirSelect.setSelectedFile(new File(savePath));
					// UIManager.put("FileChooser.readOnly", Boolean.FALSE);

					// 出力先のフォルダを選択してください。
					if( dirSelect.showOpenDialog( this ) == JFileChooser.APPROVE_OPTION ){

						// add s.inoue 2013/03/01
						// version比較
						int nowVer = 0;
						try {
							nowVer = Integer.parseInt(JApplication.versionNumber.replace(".", ""));
							
							// edit n.ohkubo 2015/03/01　追加　start　存在しないファイルの場合、エラーメッセージを表示し、正常に終了させる（Exceptionにしない）
							if (!dirSelect.getSelectedFile().isFile()) {
								JErrorMessage.show("M4215", this);
								return;
							}
							// edit n.ohkubo 2015/03/01　追加　end　存在しないファイルの場合、エラーメッセージを表示し、正常に終了させる（Exceptionにしない）

							// C:\Users\asc\Desktop\1.4.0_20130301101211.fdb
							if (dirSelect.getSelectedFile().getPath().indexOf(".fdb")<=0){
								JErrorMessage.show("M4209", this);
								return;
							}
							String selectVer = dirSelect.getSelectedFile().getPath().replace(".fdb", "");
							// eidt s.inoue 2013/03/09
							// selectVer = selectVer.substring(selectVer.lastIndexOf("\\")+1,selectVer.lastIndexOf("\\")+6).replace(".", "");
							selectVer = selectVer.substring(selectVer.lastIndexOf(JApplication.FILE_SEPARATOR)+1,selectVer.lastIndexOf(JApplication.FILE_SEPARATOR)+6).replace(".", "");

							if (Integer.parseInt(selectVer) < nowVer){
								JErrorMessage.show("M4213", this);
								return;
							}
						} catch (Exception e2) {
							JErrorMessage.show("M4214", this);
							return;
						}

						// ＤＢファイルコピー
						if (JFileCopy.copyFile(
								dirSelect.getSelectedFile().getPath(),
								JPath.GetKikanDatabaseFilePath(JApplication.kikanNumber)) ==false)
						{
							JErrorMessage.show("M4209", this);
							return;
						}
					}else{
						return;
					}

// del s.inoue 2013/02/28
//					// 一時ファイル作成
//					if (!JFileCopy
//							.copyFile(
//									JPath.GetKikanDatabaseFilePath(
//											JApplication.kikanNumber),
//									JPath.TEMP_SYSTEM_DATABASEFILE_PATH
//											+ JApplication.kikanNumber
//											+ JPath.DATA_BASE_EXTENSION)) {
//
//						JErrorMessage.show("M4209", this);
//
//						return;
//					}

// del s.inoue 2013/02/28
//					// ファイル削除
//					File removeFile = new File(JPath
//							.GetKikanDatabaseFilePath(JApplication.kikanNumber));
//
//					if (!removeFile.delete()) {
//						JErrorMessage.show("M4209", this);
//
//						return;
//					}

// del s.inoue 2013/02/28
//					// ファイル復元
//					if (!JFileCopy
//							.copyFile(
//									m_fileList.get(String.valueOf(iSelect)).getAbsolutePath(),
//									JPath
//											.GetKikanDatabaseFilePath(JApplication.kikanNumber))) {
//						JErrorMessage.show("M4209", this);
//
//						return;
//					}

// del s.inoue 2013/02/28
//					// 復元処理
//					if (!JFile.deleteDirectory(new File(
//							JPath.TEMP_SYSTEM_DATABASEFILE_PATH), false)) {
//						JErrorMessage.show("M4209", this);
//
//						return;
//					}

					// yoshida 20090105 s.inoue
					// 機関番号.fdbを対象 kikanNumber は String
					ShcDBAdjust adjuster = new ShcDBAdjust();
					adjuster.call(JApplication.kikanNumber);

					// add ver2 s.inoue 2009/07/06
					DBYearAdjuster yajuster = new DBYearAdjuster();
					yajuster.call(JApplication.kikanNumber);

					JErrorMessage.show("M4210", this);

					reloadDBFileList();
				}
			}
		} catch (Exception err1) {
			err1.printStackTrace();

			JErrorMessage.show("M4211", this);

			try {
				// 修復処理
				if (!JFileCopy.copyFile(JPath.TEMP_SYSTEM_DATABASEFILE_PATH
						+ JApplication.kikanNumber + JPath.DATA_BASE_EXTENSION,
						JPath.BaseKikanDatabaseFilePath)) {
					JErrorMessage.show("M0000", this);

					return;
				}

				// 復元処理終了
				if (!JFile.deleteDirectory(new File(
						JPath.TEMP_SYSTEM_DATABASEFILE_PATH), false)) {
					JErrorMessage.show("M0000", this);

					return;
				}
			} catch (Exception err2) {
				err2.printStackTrace();

				JErrorMessage.show("M0000", this);

				System.exit(0);
			}
		} finally {
			try {
				// データベース再接続
				connectDataBase();

			} catch (SQLException err) {
				err.printStackTrace();

				JErrorMessage.show("M0000", this);

				System.exit(0);
			}
		}
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
			this.shutdownDataBase();

			RETURN_VALUE iResult = JErrorMessage.show("M4212", this);

			if (iResult == RETURN_VALUE.YES) {
				// eidt s.inoue 2012/01/12
				if (beginBackup())
					JErrorMessage.show("M4206", this);

				this.reloadDBFileList();
			}
		} catch (Exception err) {
			err.printStackTrace();

			JErrorMessage.show("M4207", this);
		} finally {
			try {
				// データベース再接続
				this.connectDataBase();
			} catch (SQLException err) {
				err.printStackTrace();

				JErrorMessage.show("M0000", this);

//				System.exit(0);
				JApplication.exit(0);
			}
		}
	}

	/**
	 *  バックアップファイル再表示
	 *
	 *    @param  イベント
	 *
	 *    @return none
	 */
	public void pushedReloadButton(ActionEvent e) {
		reloadDBFileList();
	}

	/**
	 *  アクションイベント
	 *
	 *    @param  イベント
	 *
	 *    @return none
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == jButton_End) {
			logger.info(jButton_End.getText());
			pushedEndButton(e);
		}
		else if (source == jButton_Delete) {
			logger.info(jButton_Delete.getText());
			pushedDeleteButton(e);
		}
		else if (source == jButton_Restore) {
			logger.info(jButton_Restore.getText());
			pushedRestoreButton(e);
		}
		else if (source == jButton_Backup) {
			logger.info(jButton_Backup.getText());
			pushedBackupButton(e);
		}
//		else if (source == jButton_Reload) {
//			logger.info(jButton_Reload.getText());
//			pushedReloadButton(e);
//		}
	}

	@Override
	public void keyPressed(KeyEvent keyEvent) {
		switch(keyEvent.getKeyCode()){
		case KeyEvent.VK_F1:
			logger.info(jButton_End.getText());
			pushedEndButton(null);break;
//		case KeyEvent.VK_F7:
//			logger.info(jButton_Reload.getText());
//			pushedReloadButton(null);break;
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

//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

