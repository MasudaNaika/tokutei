package jp.or.med.orca.jma_tokutei.common.document;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class JNumberDocument extends PlainDocument {
	public void insertString(int offset, String text, AttributeSet attributes)
		throws BadLocationException
	{
		try
		{
			Integer.parseInt(text);
		}catch(Exception e)
		{
			return;
		}
		
		super.insertString(offset,text,attributes);
	}
}



//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

