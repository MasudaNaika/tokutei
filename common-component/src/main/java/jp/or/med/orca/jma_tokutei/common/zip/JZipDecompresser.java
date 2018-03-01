package jp.or.med.orca.jma_tokutei.common.zip;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

/**
 * Zip ファイルを解凍するサンプルです。
 * ※日本語ファイル名、ディレクトリ名に未対応
 */
public class JZipDecompresser {

//    public static void main(String[] args) {
//        JZipDecompresser za = new JZipDecompresser();
//        try {
//            File file = new File("D:/conf.zip");
//            za.unzip(file);
//        } catch (ZipException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * Zip ファイルの解凍メソッド
     * @param file 対象のZIPファイル
     * @throws ZipException
     * @throws IOException
     */
    public String unzip(File file) throws ZipException, IOException {

        // 対象のZipファイルと同名のディレクトリを作成する。
        String fileName = file.getName();
        int exindex = fileName.lastIndexOf(".");
        String baseDirName = fileName.substring(0, exindex);

        // ロジック修正
        // File baseDir = new File(file.getParent(), baseDirName);
        // if (!baseDir.mkdir()) {
        // throw new FileNotFoundException(baseDir + "の生成に失敗しました。");
        // }
    	int ii = 0;
        String retDirName = "";

        retDirName =baseDirName +"_" + ii;
    	File baseDir = new File(file.getParent(),retDirName);

    	while (!baseDir.mkdir()){
    		ii++;
    		retDirName = baseDirName +"_" + ii;
    		baseDir = new File(file.getParent(), retDirName);
    	}

        // Zip ファイルから ZipEntry を一つずつ取り出し、ファイルに保存していく。
        ZipFile zipFile = new ZipFile(file);
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        while (entries.hasMoreElements()) {
            ZipEntry ze = entries.nextElement();

            // 出力先ファイル
            File outFile = new File(baseDir, ze.getName());
            if (ze.isDirectory()) {
                // ZipEntry がディレクトリの場合はディレクトリを作成。
                outFile.mkdirs();
            } else {

                BufferedInputStream bis = null;
                BufferedOutputStream bos = null;
                try {
                    // ZipFile から 対象ZipEntry の InputStream を取り出す。
                    InputStream is = zipFile.getInputStream(ze);
                    // 効率よく読み込むため BufferedInputStream でラップする。
                    bis = new BufferedInputStream(is);

                    if (!outFile.getParentFile().exists()) {
                        // 出力先ファイルの保存先ディレクトリが存在しない場合は、
                        // ディレクトリを作成しておく。
                        outFile.getParentFile().mkdirs();
                    }

                    // 出力先 OutputStream を作成。
                    bos =
                            new BufferedOutputStream(new FileOutputStream(
                                    outFile));

                    // 入力ストリームから読み込み、出力ストリームへ書き込む。
                    int ava;
                    while ((ava = bis.available()) > 0) {
                        byte[] bs = new byte[ava];
                        // 入力
                        bis.read(bs);

                        // 出力
                        bos.write(bs);
                    }
                } catch (FileNotFoundException e) {
                    throw e;
                } catch (IOException e) {
                    throw e;
                } finally {
                    try {
                        if (bis != null)
                            // ストリームは必ず close する。
                            bis.close();
                    } catch (IOException e) {
                    }
                    try {
                        if (bos != null)
                            // ストリームは必ず close する。
                            bos.close();
                    } catch (IOException e) {
                    }
                }
            }
        }
        return retDirName;
    }
}