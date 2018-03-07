package jp.or.med.orca.jma_tokutei.common.document;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class JSyousuDocument extends PlainDocument {
	public void insertString(int offset, String text, AttributeSet attributes)
		throws BadLocationException
	{
		for(int i = 0 ; i < text.length() ; i++)
		{
			char c = text.charAt(i);
			
			if(('0' <= c && c <= '9') || c == '.')
			{
				super.insertString(offset + i,String.valueOf(c),attributes);
			}
		}
	}
}


//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

