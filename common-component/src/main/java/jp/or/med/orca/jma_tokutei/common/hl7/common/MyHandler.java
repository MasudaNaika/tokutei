// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) ansi

package jp.or.med.orca.jma_tokutei.common.hl7.common;

import java.io.PrintStream;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;

/**
 * スキーマ検証時のエラーハンドラ
 */
class MyHandler implements ErrorHandler
{
	public MyHandler(String FileName)
	{
		this.FileName = FileName;
	}

	private String FileName = null;

    public void warning(SAXParseException e)
    {
        System.out.println("警告 : " + FileName + " " + e.getLineNumber() +"行目 " + e.getColumnNumber());
        System.out.println(e.getLocalizedMessage());
       // e.printStackTrace();
    }
    public void error(SAXParseException e)
    {
        System.out.println("エラー : " + FileName + " " + e.getLineNumber() +"行目 " + e.getColumnNumber());
        System.out.println(e.getLocalizedMessage());
       // e.printStackTrace();
    }
    public void fatalError(SAXParseException e)
    {
        System.out.println("深刻なエラー : " + FileName + " " + e.getLineNumber() +"行目 " + e.getColumnNumber());
        System.out.println(e.getLocalizedMessage());
       // e.printStackTrace();
    }
}