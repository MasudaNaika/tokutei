package jp.or.med.orca.jma_tokutei.common.convert;

public class JInteger {
	public static int valueOf(String Value)
	{
		try
		{
			return Integer.valueOf(Value);
		}catch(Exception e)
		{
			return 0;
		}
	}
}



//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

