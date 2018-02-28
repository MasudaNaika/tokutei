package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.outputhl7;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

//import sun.management.counter.Units;

import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.filectrl.JFile;
import jp.or.med.orca.jma_tokutei.common.filectrl.JFileCopy;
import jp.or.med.orca.jma_tokutei.common.hl7.common.Utility;
import jp.or.med.orca.jma_tokutei.common.zip.JZip;

/**
 * HL7用のディレクトリを管理する
 */
public class JOutputHL7Directory {
	private String Souhumoto;
	private String Souhusaki;
	private String uniqueName = null;
	private String getUniqueName(int count){
			this.uniqueName = Souhumoto + "_" + Souhusaki + "_" +
			CreateDate + String.valueOf(count) + "_" + "1";

		return this.uniqueName;
	}

	/**
	 * 変換をした日のうちでの変換回数を保持
	 */
	private String AllocDirectoryNumber;

	/**
	 * 変換したデータが出力されるディレクトリ
	 */
	private String RootDirectory;

	/**
	 * 変換日時
	 */
	private String CreateDate;

	/**
	 * Zipファイルの出力先。
	 */
	private String zipOutputFilePath;

	private int ClinicalDocumentCount = 0;
	private int CheckupClaimCount = 0;

	public final static String SEPARATOR = System.getProperty("file.separator");
	public final static String XSDFolder = "XSD" + SEPARATOR;

	/**
	 * @param Souhumoto 送付元機関の番号
	 * @param Souhusaki 送付先機関の番号
	 */
	public JOutputHL7Directory(String Souhumoto,String Souhusaki) {
		this.Souhumoto = Souhumoto;
		this.Souhusaki = Souhusaki;
	}
	// add s.inoue 2009/09/24
	public String GetRootDirectoryPath(){
		return this.RootDirectory;
	}

	// add s.inoue 2009/09/24
	public String GetUniqueName(int count){
		this.uniqueName = Souhumoto + "_" + Souhusaki + "_" +
		CreateDate + String.valueOf(count) + "_" + "1";
	return this.uniqueName;
	}
	// add s.inoue 2009/10/18
	public String GetUniqueName(){
		this.uniqueName = Souhumoto + "_" + Souhusaki + "_" +
		CreateDate + String.valueOf(this.AllocDirectoryNumber) + "_" + "1";
	return this.uniqueName;
	}

	public String GetIndexPath()
	{
		return RootDirectory + "ix08_V08.xml";
	}

	public String GetSummaryPath()
	{
		return RootDirectory + "su08_V08.xml";
	}

	public String GetClinicalDocumentPath()
	{
		String Ret =  RootDirectory + "DATA" + SEPARATOR + "h" + Souhumoto + CreateDate + AllocDirectoryNumber +
					"1" + Utility.FillZero(ClinicalDocumentCount, 6) + ".xml";
		ClinicalDocumentCount++;
		return Ret;
	}

	public String GetCheckupClaimPath()
	{
		String Ret =  RootDirectory + "CLAIMS" + SEPARATOR + "c" + Souhumoto + CreateDate + AllocDirectoryNumber +
					"1" +  Utility.FillZero(CheckupClaimCount, 6) + ".xml";
		CheckupClaimCount++;
		return Ret;
	}

	/**
	 * 出力用のフォルダを確保する。
	 * @return エラーコード
	 * ret = 0 -> 正常終了
	 * ret = 1 -> フォルダの作成に失敗
	 * ret = 2 -> フォルダ作成の上限に達した
	 * ret = 4 -> スキーマのファイルコピーに失敗
	 */
	public int AllocOutputDirectory()
	{
		// 変換日時
		DateFormat format = new SimpleDateFormat("yyyyMMdd");
		CreateDate = format.format(new Date());

		// 一番最初にベースのディレクトリが作られているのかを確認
		File f = new File(JPath.TEMP_OUTPUT_HL7_DIRECOTRY_PATH);

		if(! f.exists())
		{
			f.mkdirs();
		}

		int count;

		// 一時的に出力を行うフォルダ
		File tempOutputFolder = null;

		for(count = 0 ; count < 10 ; count++)
		{
			String uniqueName = this.getUniqueName(count);
			String zipFilePath = JPath.ZIP_OUTPUT_DIRECTORY_PATH
				+ uniqueName
				+ ".zip";

			File zipFile = new File(zipFilePath);

			/* 同名のファイルが存在する場合は、カウンタをインクリメントする。 */
			if(zipFile.exists() == false)
			{
				try{
					zipOutputFilePath = zipFile.getPath();

					// 最後の1は実施区分
					String tempFolderPath = JPath.TEMP_OUTPUT_HL7_DIRECOTRY_PATH + uniqueName + SEPARATOR;
					tempOutputFolder = new File(tempFolderPath);

					/* フォルダが存在する場合は、削除する。 */
					if (tempOutputFolder.exists()) {
						JFile.deleteDirectory(tempOutputFolder, true);
					}

					if(tempOutputFolder.mkdirs() == false)
					{
						return 1;
					}
					Thread.sleep( 1000 );
					// 他のディレクトリも作成
					File dataFolder = new File(tempOutputFolder.getPath() + SEPARATOR + "DATA");
					if(dataFolder.mkdirs() == false)
					{
						return 1;
					}

					File claimsFolder = new File(tempOutputFolder.getPath() + SEPARATOR + "CLAIMS");
					if(claimsFolder.mkdirs() == false)
					{
						return 1;
					}

					break;
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
		}

		// 空きのフォルダが見つからなかったとき
		// count == → => に修正
		if(count >= 10)
		{
			// edit s.inoue 2009/10/02
			// 上限(10件以上)に達したらフォルダ全体を削除する
			// return 2;
			try{
				String zipFolderPath = JPath.ZIP_OUTPUT_DIRECTORY_PATH;
				tempOutputFolder = new File(zipFolderPath);

				/* フォルダが存在する場合は、削除する。 */
				if (tempOutputFolder.exists()) {
					JFile.deleteDirectory(tempOutputFolder, true);
				}
				File tempDir = new File(JPath.ZIP_OUTPUT_DIRECTORY_PATH);
				Thread.sleep( 1000 );
				if(! tempDir.exists())
					tempDir.mkdir();
			}catch(Exception ex){
				ex.printStackTrace();
			}
			return 2;
		}

		AllocDirectoryNumber = String.valueOf(count);

		RootDirectory = tempOutputFolder.getPath() + SEPARATOR;

		// XSDファイルをコピーする
		try {
			CopyXSDFiles();
		} catch (Exception e) {
			e.printStackTrace();
			return 4;
		}

		return 0;
	}

	/**
	 * ファイルを圧縮する
	 */
	public void CompressFile() throws IOException
	{
		File f1 = new File(RootDirectory);
		File f2 = new File(zipOutputFilePath);

		JZip.Zip(f1, f2);
	}

	/**
	 * 一時フォルダを削除する
	 */
	public void DeleteFolder()
	{
		File f1 = new File(RootDirectory);
		JFile.deleteDirectory(f1, true);
	}

	/**
	 * XSDファイルのコピー
	 */
	private void CopyXSDFiles() throws Exception
	{
		JFileCopy.xcopyFile(XSDFolder, RootDirectory + XSDFolder);
	}
}
