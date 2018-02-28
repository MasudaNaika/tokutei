package jp.or.med.orca.jma_tokutei.common.zip;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import jp.or.med.orca.jma_tokutei.common.app.JPath;

/**
 * Zip圧縮を行うためのクラス
 * 
 * Modified 2008/05/03 若月
 * 圧縮時に最上位のフォルダが設定されていないバグを修正。
 * その他、Java コーディング規約に合わせてリファクタリング。
 */
public class JZip {

	private static final int CRC_BUFFER_SIZE = 1024;

	/**
	 * 圧縮元ディレクトリのベース
	 */
	private static String basePath;

	private static final int COMPRESS_BUFFER_SIZE = 1024;
	private final static int COMPRESS_LEVEL = 5;

	/**
	 * @param srcFile
	 *            圧縮元のファイル・フォルダ
	 * @param destFile
	 *            圧縮先のファイル・フォルダ
	 */
	public static void Zip(File srcFile, File destFile) throws IOException {
		ZipOutputStream output = new ZipOutputStream(new FileOutputStream(
				destFile));
		output.setLevel(COMPRESS_LEVEL);

		basePath = srcFile.getCanonicalPath();
		compress(srcFile, output);

		output.close();
	}

	/**
	 * @param srcFile
	 *            圧縮元のファイル・フォルダ
	 * @param output
	 *            出力先のZipStream
	 */
	private static void compress(File srcFile, ZipOutputStream output)
			throws IOException {
		
		/* Modified 2008/05/14 若月  */
		/* --------------------------------------------------- */
		/* パスがディレクトリを指す場合は、メソッドの再帰呼び出しを行なう。 */
		if (srcFile.isDirectory() == true) {
			File[] fileList = srcFile.listFiles();
			for (int i = 0; i < fileList.length; i++) {
				compress(fileList[i], output);
			}

		/* パスがファイルを指す場合は、エントリとして追加する。 */
		}else {
			addEntryToZip(srcFile, output);
		}
		/* --------------------------------------------------- */
//		addEntryToZip(srcFile, output);
//		
//		/* パスがディレクトリを指す場合は、メソッドの再帰呼び出しを行なう。 */
//		if (srcFile.isDirectory()) {
//			File[] fileList = srcFile.listFiles();
//			for (int i = 0; i < fileList.length; i++) {
//				compress(fileList[i], output);
//			}
//		}
		/* --------------------------------------------------- */
	}

	/**
	 * ZipEntry にファイルを追加する。  
	 */
//	private static void addEntryToZip(File srcFile, ZipOutputStream output) 
//		throws FileNotFoundException, IOException {
//		
//		BufferedInputStream input = new BufferedInputStream(
//				new FileInputStream(srcFile));
//
//		long crc = getCRC(srcFile);
//		long size = getCompressedSize(srcFile);
//
//		// Zipファイル内でのファイル名を決める
//		String zipFilePath = srcFile.getCanonicalPath();
//
//		/* Modified 2008/05/14 若月  */
//		/* --------------------------------------------------- */
////		/* Modified 2008/05/03 若月  */
////		/* --------------------------------------------------- */
//////			zipFilePath = zipFilePath.substring(basePath.length());
////		/* --------------------------------------------------- */
////		File zipParentDirectory = 
////			new File(JPath.TEMP_OUTPUT_HL7_DIRECOTRY_PATH);
////		String parentPath = zipParentDirectory.getCanonicalPath();
////		zipFilePath = zipFilePath.substring(parentPath.length());
////		/* --------------------------------------------------- */
//		/* --------------------------------------------------- */
//		File zipParentDirectory = 
//			new File(JPath.TEMP_OUTPUT_HL7_DIRECOTRY_PATH);
//		String parentPath = zipParentDirectory.getCanonicalPath();
//		zipFilePath = zipFilePath.substring(parentPath.length() + 1);
//		/* --------------------------------------------------- */
//
//		// Windowsの場合、パスの区切り文字が違うので/に変える
//		zipFilePath = zipFilePath.replaceAll("\\\\", "/");
//
//		ZipEntry entry = new ZipEntry(zipFilePath);
//
//		entry.setCompressedSize(size);
//		entry.setCrc(crc);
//		entry.setMethod(ZipEntry.DEFLATED);
//		entry.setSize(srcFile.length());
//		entry.setTime(srcFile.lastModified());
//
//		output.putNextEntry(entry);
//
//		byte buffer[] = new byte[1024];
//		int count = 0;
//		while ((count = input.read(buffer)) != -1) {
//			output.write(buffer, 0, count);
//		}
//
//		input.close();
//		output.closeEntry();
//	}

	private static void addEntryToZip(File srcFile, ZipOutputStream output)
			throws FileNotFoundException, IOException {

		BufferedInputStream input = null;
		long crc = 0L;
		long size = 0L;

		if (srcFile.isFile()) {
			input = new BufferedInputStream(new FileInputStream(srcFile));

			crc = getCRC(srcFile);
			size = getCompressedSize(srcFile);
		}

		// Zipファイル内でのファイル名を決める
		String zipFilePath = srcFile.getCanonicalPath();

		File zipParentDirectory = new File(JPath.TEMP_OUTPUT_HL7_DIRECOTRY_PATH);
		String parentPath = zipParentDirectory.getCanonicalPath();
		zipFilePath = zipFilePath.substring(parentPath.length() + 1);

		// Windowsの場合、パスの区切り文字が違うので/に変える
		zipFilePath = zipFilePath.replaceAll("\\\\", "/");

		if (srcFile.isDirectory()) {
			zipFilePath += "/";
		}
		
		ZipEntry entry = new ZipEntry(zipFilePath);

		if (srcFile.isFile()) {
			entry.setCompressedSize(size);
			entry.setCrc(crc);
			entry.setMethod(ZipEntry.DEFLATED);
			entry.setSize(srcFile.length());
			entry.setTime(srcFile.lastModified());
		}
		else {
			entry.setSize(0);
		}

		output.putNextEntry(entry);

		if (srcFile.isFile()) {
			byte buffer[] = new byte[1024];
			int count = 0;
			while ((count = input.read(buffer)) != -1) {
				output.write(buffer, 0, count);
			}
			
			input.close();
			output.closeEntry();
		}
//		
//		output.flush();
//		output.closeEntry();
//		output.close();
	}
	
	
	/**
	 * CRC32の値を求める
	 * 
	 * @param targetFile
	 *            ファイルのパス
	 */
	private static long getCRC(File targetFile) throws IOException {
		BufferedInputStream input = new BufferedInputStream(
				new FileInputStream(targetFile));
		
		CRC32 crc = new CRC32();

		byte buffer[] = new byte[CRC_BUFFER_SIZE];
		int count = 0;
		while ((count = input.read(buffer)) != -1) {
			crc.update(buffer, 0, count);
		}

		input.close();
		return crc.getValue();
	}

	/**
	 * 圧縮後のサイズを求める
	 * 
	 * @param TargetFile
	 *            対象のファイル
	 */
	private static long getCompressedSize(File TargetFile) throws IOException {
		ZipEntry entry = new ZipEntry(TargetFile.getName());
		entry.setMethod(ZipEntry.DEFLATED);

		ZipOutputStream output = new ZipOutputStream(new DummyOutputStream());
		output.setLevel(COMPRESS_LEVEL);
		output.putNextEntry(entry);

		BufferedInputStream input = new BufferedInputStream(
				new FileInputStream(TargetFile));

		byte buffer[] = new byte[COMPRESS_BUFFER_SIZE];
		int count = 0;
		while ((count = input.read(buffer)) != -1) {
			output.write(buffer, 0, count);
		}

		input.close();
		output.closeEntry();
		output.close();
		return entry.getCompressedSize();
	}

	/**
	 * 圧縮時のダミー
	 */
	private static class DummyOutputStream extends OutputStream {
		public void write(int buffer) throws IOException {

		}
	}

}

// Source Code Version Tag System v1.00 -- Do not remove --
// Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

