package jp.or.med.orca.jma_tokutei.common.convert;

public class JEraseSpace {
	public static String EraceSpace(String str)
	{
		String OutputString = "";
		for(int i = 0 ; i < str.length() ; i++)
		{
			if(str.charAt(i) != ' ' && str.charAt(i) != '　')
			{
				OutputString += str.charAt(i);
			}
		}
		return OutputString;
	}
}



//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

